package bsr.project.checkers.protocol;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * narzędzie do budowania pakietów do wysłania do klienta,
 * typu response (w odpowiedzi na żądanie klienta) lub request (inicjujące komunikację z klientem)
 */
public class PacketsBuilder {
	
	private static final String SEPARATOR = "#";
	
	/**
	 * @param objs
	 * @return obiekty zrzutowane na String połączone znakiem separatora
	 */
	private static String join(Object... objs) {
		return Arrays.stream(objs).map(Object::toString).collect(Collectors.joining(SEPARATOR));
	}

	private String buildPacket(PacketType type, Object... parameters){
		return join(type.getCode(), parameters);
	}
	
	/**
	 * 1. Login - Autoryzacja w systemie
	 * @param result 1 w przypadku powodzenia, 0 w przypadku niepowodzenia
	 */
	public String responseLogin(boolean result){
		buildPacket(LOG_IN, result ? "1" : "0");
	}

	/**
	 * 2. Rejestracja - Utworzenie konta w systemie
	 * @param result 1 w przypadku powodzenia, 0 w przypadku niepowodzenia
	 */
	public String resonseCreateAccount(boolean result){
		buildPacket(CREATE_ACCOUNT, result ? "1" : "0");
	}

	/**
	 * 3. Lista - Pobranie listy graczy
	 * @param playersList lista graczy (klientów)
	 */
	public String responseListPlayers(List<ClientData> clients){
		List<String> params = new ArrayList<>();
		// dla każdego gracza kolejno zwracana para nazwa / status, gdzie status równy A – oznacza gracza gotowego na grę, status B – oznacza gracza zajętego / w trakcie negocjacji / ogólnie nie gotowego na przyjęcie zaproszenia
		for (ClientData client : clients){
			params.add(client.getLogin()); // nazwa
			// status równy A – oznacza gracza gotowego na grę, status B – oznacza gracza zajętego
			Stirng status = client.isReadyForNewGame() ? "A" : "B";
			params.add(status); 
		}
		buildPacket(LIST_PLAYERS, params);
	}

	/**
	 * 4. Nowa gra - Prośba o rozpoczęcie nowej gry
	 * @param result 1 w przypadku gdy prośba została przekazana, 0 – jeśli wystąpił błąd (gracz o podanej nazwie nie istnieje)
	 */
	public String responseCreateRequestForGame(boolean result){
		buildPacket(CREATE_REQUEST_FOR_GAME, result ? "1" : "0");
	}

	/**
	 * 5. Pytanie o grę - przekazanie prośby o rozpoczęcie nowej gry
	 * @param requestLogin nazwa gracza który nas zaprasza
	 */
	public String requestInvitationForGame(String requestLogin){
		buildPacket(INVITATION_FOR_GAME, requestLogin);
	}

	/**
	 * 6. Przekazanie odpowiedzi - przekazanie odpowiedzi na prośbę o nową grę
	 * @param agreed 1 - zgoda, 0 - brak zgody
	 */
	public String requestResponseForInvitation(boolean agreed){
		buildPacket(RESPONSE_FOR_INVITATION, agreed ? "1" : "0");
	}

	/**
	 * 7. Inicjacja gry - rozpoczęcie nowej rozgrywki
	 * @param yourColor kolor, którymi będzie grał dany zawodnik {@see BoardSymbols}
	 */
	public String requestNewGame(char yourColor){
		buildPacket(NEW_GAME, yourColor);
	}

	/**
	 * 8. Plansza - przesłanie planszy
	 * @param board plansza
	 */
	public String requestChangedBoard(Board board){
		// plansza - 64 znakowy łańcuch tekstowy składający się ze znaków BCDEO
		buildPacket(CHANGED_BOARD, board.toString());
	}

	/**
	 * 9. Twój ruch - Informacja dla gracza o zmianie stanu – czy może wykonać ruch
	 */
	public String requestYourMove(){
		buildPacket(YOUR_MOVE);
	}

	/**
	 * 11. Koniec - Koniec gry - albo poprzez poddanie się albo zwycięstwo / porażkę
	 * @param winner wygrany - login
	 * @param reason przyczyna
	 */
	public String requestGameOver(String winner, String reason) {
		buildPacket(GAME_OVER, winner, reason);
	}

	/**
	 * 13. Błąd protokołu
	 * @param message komunikat
	 */
	public String requestProtocolError(String message){
		buildPacket(ERROR, message);
	}

	/**
	 * 14. Niespójność - błąd stanu
	 * @param message komunikat
	 */
	public String requestInvalidState(String message){
		buildPacket(INVALID_STATE, message);
	}

}