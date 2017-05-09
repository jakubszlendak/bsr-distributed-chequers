package bsr.project.checkers.controller;

import java.text.ParseException;
import java.util.List;

import bsr.project.checkers.client.ClientConnectionThread;
import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.client.ClientState;
import bsr.project.checkers.dispatcher.AbstractEvent;
import bsr.project.checkers.dispatcher.EventDispatcher;
import bsr.project.checkers.dispatcher.IEventObserver;
import bsr.project.checkers.events.PacketReceivedEvent;
import bsr.project.checkers.game.Board;
import bsr.project.checkers.game.GameInvitation;
import bsr.project.checkers.game.GameSession;
import bsr.project.checkers.game.Point;
import bsr.project.checkers.game.validator.InvalidMoveException;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.protocol.BoardSymbols;
import bsr.project.checkers.protocol.PacketsBuilder;
import bsr.project.checkers.protocol.PacketsParser;
import bsr.project.checkers.protocol.ProtocolPacket;
import bsr.project.checkers.server.ServerData;
import bsr.project.checkers.users.UsersDatabase;

public class PacketsController implements IEventObserver {
	
	private final boolean DEBUG_BOARD = true;
	
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
		try {
			ClientConnectionThread clientConnection = client.getClientConnection();
			clientConnection.sendLine(packetStr);
		} catch (IllegalStateException e) {
			Logs.error(e.getMessage());
		}
	}
	
	private void packetReceived(ClientData client, String received) {
		
		// Logs.debug("packet received from " + client.getHostname() + ": " + received);
		
		try {
			ProtocolPacket packet = parser.parsePacket(received);
			switch (packet.getType()) {
				case LOG_IN: {
					checkState(client, ClientState.NOT_LOGGED_IN);
					boolean result = tryLogIn(client, packet);
					sendPacket(client, builder.responseLogin(result));
				}
				break;
				case CREATE_ACCOUNT: {
					checkState(client, ClientState.NOT_LOGGED_IN);
					boolean result = createAccount(client, packet);
					sendPacket(client, builder.responseCreateAccount(result));
				}
				break;
				case LOG_OUT: {
					checkState(client, ClientState.LOGGED_IN, ClientState.WAITING_FOR_ACCEPT, ClientState.GAME_REQUEST);
					logOut(client);
				}
				break;
				case LIST_PLAYERS: {
					checkState(client, ClientState.LOGGED_IN);
					listPlayers(client);
				}
				break;
				case CREATE_REQUEST_FOR_GAME: {
					checkState(client, ClientState.LOGGED_IN);
					createInvitation(client, packet);
				}
				break;
				case INVITATION_FOR_GAME: {
					checkState(client, ClientState.GAME_REQUEST);
					receivedInvitationResponse(client, packet);
				}
				break;
				case GIVE_UP: {
					checkState(client, ClientState.PLAYING_GAME);
					giveUpGame(client);
				}
				break;
				case MAKE_MOVE: {
					checkState(client, ClientState.PLAYING_GAME);
					makeMove(client, packet);
				}
				break;
				case ERROR: {
					String message = packet.getParameter(0, String.class);
					Logs.error("Protocol error received: " + message);
				}
				break;
				case INVALID_STATE: {
					String message = packet.getParameter(0, String.class);
					Logs.error("State error received: " + message);
				}
				break;
			}
		} catch (InvalidClientStateException e) {
			// client state is invalid
			Logs.error(e.getMessage());
			sendPacket(client, builder.requestInvalidState(e.getMessage()));
		} catch (ProtocolErrorException e) {
			Logs.error("Protocol error: " + e.getMessage());
			sendError(client, e.getMessage());
		} catch (ParseException | IllegalArgumentException e) {
			// invalid format
			Logs.error("Invalid received packet format: " + e.getMessage());
			sendError(client, e.getMessage());
		} catch (Throwable e) {
			Logs.error(e);
		}
	}
	
	
	private void sendError(ClientData client, String message) {
		sendPacket(client, builder.requestProtocolError(message));
	}
	
	private void checkState(ClientData client, ClientState... allowedStates) throws InvalidClientStateException {
		for (ClientState allowedState : allowedStates) {
			if (client.getState() == allowedState) // client state is correct
				return;
		}
		throw new InvalidClientStateException("Invalid client state: " + client.getState().name());
	}
	
	
	private boolean createAccount(ClientData client, ProtocolPacket packet) {
		String login = packet.getParameter(0, String.class);
		String password = packet.getParameter(1, String.class);
		
		UsersDatabase userDb = serverData.getUsersDatabase();
		
		if (login.contains("#") || login.isEmpty()) {
			Logs.warn("invalid login");
			return false;
		}
		if (userDb.userExists(login)) {
			Logs.warn("User " + login + " already exists");
			return false;
		}
		if (password.contains("#") || password.isEmpty()) {
			Logs.warn("invalid password");
			return false;
		}
		
		userDb.addUser(login, password);
		return true;
	}
	
	private boolean tryLogIn(ClientData client, ProtocolPacket packet) {
		String login = packet.getParameter(0, String.class);
		String password = packet.getParameter(1, String.class);
		
		UsersDatabase userDb = serverData.getUsersDatabase();
		
		if (!userDb.userExists(login)) {
			Logs.warn("User " + login + " does not exist");
			return false;
		}
		if (!userDb.passwordValid(login, password)) {
			Logs.warn("Given password is not correct");
			return false;
		}
		// check if already logged in
		if (serverData.findLoggedClient(login) != null) {
			Logs.warn("User " + login + " is already logged in");
			return false;
		}
		
		client.setState(ClientState.LOGGED_IN);
		client.setLogin(login);
		
		Logs.info("User " + login + " logged in.");
		return true;
	}
	
	private void logOut(ClientData client) {
		Logs.info("User " + client.getLogin() + " logged out.");
		client.setState(ClientState.NOT_LOGGED_IN);
		client.setLogin(null);
	}
	
	private void listPlayers(ClientData client) {
		List<ClientData> clients = serverData.getClients();
		sendPacket(client, builder.responseListPlayers(clients));
	}
	
	private void createInvitation(ClientData client, ProtocolPacket packet) throws ProtocolErrorException {
		String foreignLogin = packet.getParameter(0, String.class);
		
		ClientData foreignClient = serverData.findLoggedClient(foreignLogin);
		
		if (foreignClient == null) {
			Logs.warn("User " + foreignLogin + " does not exist");
			sendPacket(client, builder.responseCreateRequestForGame(false));
			return;
		}
		
		if (foreignClient == client)
			throw new ProtocolErrorException("Cannot invite yourself!");
		
		if (foreignClient.getState() != ClientState.LOGGED_IN)
			throw new ProtocolErrorException("Requested client is not waiting for invitations");
		
		if (serverData.findInvitation(foreignClient) != null)
			throw new ProtocolErrorException("Requested client is already invited");
		
		// remember new invitation
		serverData.getInvitations().add(new GameInvitation(client, foreignClient));
		// send result to inviting user
		sendPacket(client, builder.responseCreateRequestForGame(true));
		// send INVITATION_FOR_GAME to another user
		sendPacket(foreignClient, builder.requestInvitationForGame(client.getLogin()));
		// change players states
		client.setState(ClientState.WAITING_FOR_ACCEPT);
		foreignClient.setState(ClientState.GAME_REQUEST);
		
		Logs.info("Invitation has been created: " + client.getLogin() + " -> " + foreignClient.getLogin());
	}
	
	private void receivedInvitationResponse(ClientData client, ProtocolPacket packet) throws ProtocolErrorException {
		Boolean agreed = packet.getParameter(0, Boolean.class);
		
		GameInvitation invitation = serverData.findInvitation(client);
		
		if (invitation == null)
			throw new ProtocolErrorException("Player is not invited");
		
		// remove invitation from list
		serverData.removeInvitation(invitation);
		
		// sending response for inviting player
		sendPacket(invitation.sender, builder.requestResponseForInvitation(agreed));
		
		Logs.info("Received response for invitation from player " + client + ": " + agreed);
		
		if (agreed) {
			newGame(invitation.sender, invitation.receiver);
		} else {
			invitation.sender.setState(ClientState.LOGGED_IN);
			invitation.receiver.setState(ClientState.LOGGED_IN);
		}
	}
	
	private void newGame(ClientData player1, ClientData player2) {
		Logs.info("Creating new game session: " + player1 + " vs " + player2);
		// create new game
		GameSession game = new GameSession(player1, player2);
		serverData.addGame(game);
		// change players states
		player1.setState(ClientState.PLAYING_GAME);
		player2.setState(ClientState.PLAYING_GAME);
		// send new game messages
		sendPacket(player1, builder.requestNewGame(BoardSymbols.WHITE_PAWN));
		sendPacket(player2, builder.requestNewGame(BoardSymbols.BLACK_PAWN));
		// send board to players
		sendBoards(game);
		// send your move message to White player
		sendPacket(game.getCurrentPlayer(), builder.requestYourMove());
	}
	
	private void makeMove(ClientData player, ProtocolPacket packet) throws ProtocolErrorException {
		GameSession game = serverData.findGame(player);
		if (game == null)
			throw new ProtocolErrorException("game session with player was not found");
		
		if (game.getCurrentPlayer() != player)
			throw new ProtocolErrorException("wait for your turn!");
		
		Point from = packet.getParameter(0, Point.class);
		Point to = packet.getParameter(1, Point.class);
		
		// validate move
		try {
			// if valid - make move
			game.executeMove(player, from, to);
		} catch (InvalidMoveException e) {
			// if move is not valid - send response with false
			sendPacket(player, builder.responseMakeMove(false));
			Logs.warn("Invalid move: " + e.getMessage());
			return;
		}
		// move has been executed successfully
		sendPacket(player, builder.responseMakeMove(true));
		// update new board state
		sendBoards(game);
		
		// check if game is over
		ClientData winner = game.getWinner();
		if (winner != null) {
			// send gameover message and exit
			gameOver(game, winner, "Player " + winner + " beat all opponent's pawns");
			return;
		}
		
		// check whose move is next, send Your Move message
		ClientData currentPlayer = game.getCurrentPlayer();
		sendPacket(currentPlayer, builder.requestYourMove());
	}
	
	private void sendBoards(GameSession game) {
		sendPacket(game.getPlayer1(), builder.requestChangedBoard(game.getBoard()));
		sendPacket(game.getPlayer2(), builder.requestChangedBoard(game.getBoard()));
		
		if (DEBUG_BOARD) {
			Logs.debug("Current board:");
			char[][] map = game.getBoard().getMap();
			for (int y = 0; y < Board.BOARD_SIZE; y++) {
				StringBuilder sb = new StringBuilder();
				for (int x = 0; x < Board.BOARD_SIZE; x++) {
					sb.append(map[x][y]);
				}
				Logs.debug(sb.toString());
			}
		}
	}
	
	private void giveUpGame(ClientData client) throws ProtocolErrorException {
		GameSession game = serverData.findGame(client);
		if (game == null)
			throw new ProtocolErrorException("game session with player was not found");
		
		ClientData opponent = game.getOpponent(client);
		gameOver(game, opponent, "Player " + client + " gave up");
	}
	
	private void gameOver(GameSession game, ClientData winner, String reason) {
		ClientData player1 = game.getPlayer1();
		ClientData player2 = game.getPlayer2();
		
		player1.setState(ClientState.LOGGED_IN);
		player2.setState(ClientState.LOGGED_IN);
		
		sendPacket(player1, builder.requestGameOver(winner.getLogin(), reason));
		sendPacket(player2, builder.requestGameOver(winner.getLogin(), reason));
		
		serverData.removeGame(game);
		Logs.info("Game session " + player1 + " vs " + player2 + " has been terminated, winner: " + winner + ", reason: " + reason);
	}
}
