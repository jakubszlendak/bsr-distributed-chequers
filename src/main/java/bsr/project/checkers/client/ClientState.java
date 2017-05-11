package bsr.project.checkers.client;

public enum ClientState {
	
	/** niezalogowany */
	NOT_LOGGED_IN,
	
	/** zalogowany - brak oczekujących zaproszeń */
	LOGGED_IN,
	
	/** czeka na zaakceptowanie zaproszenia do gry */
	WAITING_FOR_ACCEPT,
	
	/** zaproszenie do gry (w trakcie odpowiadania na zaproszenie) */
	GAME_REQUEST,
	
	/** gra rozpoczęta */
	PLAYING_GAME;
	
}