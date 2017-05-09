package bsr.project.checkers.client;

import bsr.project.checkers.game.GameSession;

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
	
	public ClientConnectionThread getClientConnection() {
		return clientConnection;
	}
	
	public boolean isConnected() {
		return clientConnection.isActive();
	}
	
	public String getHostname() {
		return clientConnection.getHostname();
	}
	
	/**
	 * @return czy gracz jest gotowy na nową grę
	 */
	public boolean isReadyForNewGame() {
		return isConnected() && state == ClientState.LOGGED_IN;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	@Override
	public String toString() {
		if (login != null)
			return login;
		return getHostname();
	}
}