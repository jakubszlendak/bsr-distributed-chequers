
public class SessionsContainer {

	// TODO czy w ogóle jest potrzebny? dowiązanie do gry przechowywane w referencji u klienta
	// TODO lista gier i klientów do debuggingu

	private List<Clientnfo> clients = new ArrayList<>();
	private List<GameSession> games = new ArrayList<>();

	public SessionsContainer(){
	}

	public GameSession createGame(ClientInfo player1, ClientInfo player2){
		GameSession game = new GameSession(player1, player2);
		games.add(game);
		return game;
	}
}