package bsr.project.checkers.session;

import java.util.ArrayList;
import java.util.List;

import bsr.project.checkers.session.client.ClientInfo;
import bsr.project.checkers.session.game.GameSession;

public class SessionsContainer {
	
	//TODO lista gracy jest potrzebna
	
	// TODO czy w ogóle jest potrzebny? dowiązanie do gry przechowywane w referencji u klienta
	// TODO lista gier i klientów do debuggingu
	
	private List<ClientInfo> clients = new ArrayList<>();
	private List<GameSession> games = new ArrayList<>();
	
	public SessionsContainer() {
	}
	
	public GameSession createGame(ClientInfo player1, ClientInfo player2) {
		GameSession game = new GameSession(player1, player2);
		games.add(game);
		return game;
	}
}