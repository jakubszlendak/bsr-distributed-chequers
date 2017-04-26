package bsr.project.checkers.session.client;

public enum ClientState {
	
	/** niezalogowany */
	NOT_LOGGED_IN(0);
	
	private int id;
	
	ClientState(int id) {
		this.id = id;
	}
}