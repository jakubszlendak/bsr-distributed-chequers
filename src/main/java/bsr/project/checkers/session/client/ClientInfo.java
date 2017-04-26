package bsr.project.checkers.session.client;

import bsr.project.checkers.network.ClientConnectionThread;
import bsr.project.checkers.session.game.GameSession;

public class ClientInfo {
	
	private ClientConnectionThread clientConnection;
	private ClientState state;
	private GameSession gameSession = null;
	private String login = null;
	
	public ClientInfo(ClientConnectionThread clientConnection) {
		this.clientConnection = clientConnection;
		state = ClientState.NOT_LOGGED_IN;
	}
	
	public synchronized void setState(ClientState state) {
		this.state = state;
	}
	
	public synchronized ClientState getState() {
		return state;
	}
	
}