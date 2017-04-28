package bsr.project.checkers.network;

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
import bsr.project.checkers.db.users.UsersDatabase;
import bsr.project.checkers.protocol.PacketsBuilder;

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

	}
	
	private void packetReceived(ClientData client, String received) {
		
		Logs.debug("packet received from " + client.getHostname() + ": " + received);
		
		try {
			ProtocolPacket packet = parser.parsePacket(received);
			switch(packet.getType()){
				case CREATE_ACCOUNT:{
					if (createAccount(client, packet)){
						sendPacket(client, builder.responseLogin(true)); // success
					}else{
						sendPacket(client, builder.responseLogin(false)); // failure
					}
				}
				break;
			}
		} catch (ParseException | IllegalArgumentException e) {
			Logs.error("Received packet is invalid: " + e.getMessage());
			//TODO send message - error or invalid state
		}
	}


	private boolean createAccount(ClientData client, ProtocolPacket packet){
		String login = packet.getParameter(0, String.class);
		String password = packet.getParameter(0, String.class);
		
		UsersDatabase userDb = serverData.getUsersDatabase();

		if (login.contains("#") || login.isEmpty()){
			Logs.error("invalid login");
			return false;
		}
		if (userDb.userExists(login)){
			Logs.error("User " + login + " already exists");
			return false;
		}
		if (password.contains("#") || password.isEmpty()){
			Logs.error("invalid password");
			return false;
		}

		userDb.addUser(login, password);
		return true;
	}
}
