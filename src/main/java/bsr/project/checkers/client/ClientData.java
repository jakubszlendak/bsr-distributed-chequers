package bsr.project.checkers.client;

import bsr.project.checkers.game.GameSession;
import bsr.project.checkers.network.ClientConnectionThread;

public class ClientData {
	
	private ClientConnectionThread clientConnection;
	private ClientState state;
	private String login = null;
	private GameSession gameSession = null;
	
	public ClientData(ClientConnectionThread clientConnection) {
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