package bsr.project.checkers.controller;

import java.text.ParseException;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.dispatcher.AbstractEvent;
import bsr.project.checkers.dispatcher.EventDispatcher;
import bsr.project.checkers.dispatcher.IEventObserver;
import bsr.project.checkers.events.PacketReceivedEvent;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.protocol.PacketsParser;
import bsr.project.checkers.protocol.ProtocolPacket;
import bsr.project.checkers.server.ServerData;
import bsr.project.checkers.users.UsersDatabase;
import bsr.project.checkers.protocol.PacketsBuilder;
import bsr.project.checkers.client.ClientConnectionThread;
import bsr.project.checkers.client.ClientState;

public class PacketsController implements IEventObserver {
	
	private ServerData serverData;
	private PacketsParser parser;
	private PacketsBuilder builder;
	
	public PacketsController(ServerData serverData) {
		this.serverData = serverData;
		parser = new PacketsParser();
		builder = new PacketsBuilder();
		registerEvents();
	}
	
	@Override
	public void registerEvents() {
		EventDispatcher.registerEventObserver(PacketReceivedEvent.class, this);
	}
	
	@Override
	public void onEvent(AbstractEvent event) {
		
		event.bind(PacketReceivedEvent.class, e -> packetReceived(e.getClientData(), e.getReceived()));
	}

	private void sendPacket(ClientData client, String packetStr) {
		try{
			ClientConnectionThread clientConnection = client.getClientConnection();
			clientConnection.sendLine(packetStr);
		}catch(IllegalStateException e){
			Logs.error(e.getMessage());
		}
	}
	
	private void packetReceived(ClientData client, String received) {
		
		Logs.debug("packet received from " + client.getHostname() + ": " + received);
		
		try {
			ProtocolPacket packet = parser.parsePacket(received);
			switch(packet.getType()){
				case CREATE_ACCOUNT:{
					checkState(client, ClientState.NOT_LOGGED_IN);
					boolean result = createAccount(client, packet);
					sendPacket(client, builder.responseCreateAccount(result));
				}
				break;
				case LOG_IN:{
					checkState(client, ClientState.NOT_LOGGED_IN);
					boolean result = tryLogIn(client, packet);
					sendPacket(client, builder.responseLogin(result));
				}
				break;
				case LOG_OUT:{
					checkState(client, ClientState.LOGGED_IN, ClientState.WAITING_FOR_ACCEPT, ClientState.GAME_REQUEST);
					logOut(client);
				}
				break;
			}
		} catch (InvalidClientStateException e){
			// client state is invalid
			Logs.error(e.getMessage());
			sendPacket(client, builder.requestInvalidState(e.getMessage()));
		} catch (ParseException | IllegalArgumentException e) {
			Logs.error("Received packet is invalid: " + e.getMessage());
			sendError(client, e.getMessage());
		}
	}


	private void sendError(ClientData client, String message){
		sendPacket(client, builder.requestProtocolError(message));
	}

	private void checkState(ClientData client, ClientState... allowedStates) throws InvalidClientStateException {
		for (ClientState allowedState : allowedStates){
			if (client.getState() == allowedState) // client state is correct
				return;
		}
		throw new InvalidClientStateException("Invalid client state: " + client.getState().name());
	}


	private boolean createAccount(ClientData client, ProtocolPacket packet){
		String login = packet.getParameter(0, String.class);
		String password = packet.getParameter(1, String.class);
		
		UsersDatabase userDb = serverData.getUsersDatabase();

		if (login.contains("#") || login.isEmpty()){
			Logs.warn("invalid login");
			return false;
		}
		if (userDb.userExists(login)){
			Logs.warn("User " + login + " already exists");
			return false;
		}
		if (password.contains("#") || password.isEmpty()){
			Logs.warn("invalid password");
			return false;
		}

		userDb.addUser(login, password);
		return true;
	}

	private boolean tryLogIn(ClientData client, ProtocolPacket packet){
		String login = packet.getParameter(0, String.class);
		String password = packet.getParameter(1, String.class);
		
		UsersDatabase userDb = serverData.getUsersDatabase();

		if (!userDb.userExists(login)){
			Logs.warn("User " + login + " does not exist");
			return false;
		}
		if (!userDb.passwordValid(login, password)){
			Logs.warn("Given password is not correct");
			return false;
		}
		
		client.setState(ClientState.LOGGED_IN);
		client.setLogin(login);

		Logs.info("User " + login + " logged in.");
		return true;
	}

	private void logOut(ClientData client){
		Logs.info("User " + client.getLogin() + " logged out.");
		client.setState(ClientState.NOT_LOGGED_IN);
		client.setLogin(null);
	}
}
