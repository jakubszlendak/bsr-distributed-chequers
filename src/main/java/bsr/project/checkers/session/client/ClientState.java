package bsr.project.checkers.session.client;

public enum ClientState {
	
	/** niezalogowany */
	NOT_LOGGED_IN(0),
	
	/** zalogowany */
	LOGGED_IN(1),
	
	/** czeka na zaakceptowanie zaproszenia do gry */
	WAITING_FOR_ACCEPT(2),
	
	/** zaproszenie do gry */
	GAME_REQUEST(3),
	
	/** gra rozpoczęta */
	PLAYING_GAME(4);
	
	private int id;
	
	ClientState(int id) {
		this.id = id;
	}
}