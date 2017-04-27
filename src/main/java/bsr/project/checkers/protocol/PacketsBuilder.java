package bsr.project.checkers.protocol;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PacketsBuilder {
	
	private static final String JOIN_CHAR = "#";
	
	private static String join(Object... objs) {
		return Arrays.stream(objs).map(Object::toString).collect(Collectors.joining(JOIN_CHAR));
	}
	
	// pakiety w kierunku Server -> Client
	
	/**
	 * 5. Pytanie o grę - przekazanie prośby o rozpoczęcie nowej gry
	 * @param requestLogin nazwa gracza który nas zaprasza
	 * @return packet
	 */
	public static String requestForAGame(String requestLogin) {
		return join("RP1", requestLogin);
	}
	
	/**
	 * 6. Przekazanie odpowiedzi - przekazanie odpowiedzi na prośbę o nową grę
	 * @param decision 1 - zgoda, 0 - brak zgody
	 * @return packet
	 */
	public static String sendInvitationResponse(String decision) {
		return join("RP2", decision);
	}
	
	/**
	 * 7. Inicjacja gry - rozpoczęcie nowej rozgrywki
	 * @param yourColor kolor, którymi będzie grał dany zawodnik
	 * @return packet
	 */
	public static String initGame(char yourColor) {
		return join("INI", yourColor);
	}
	
	/**
	 * 8. Plansza - przesłanie planszy
	 * @param board 64 znakowy łańcuch tekstowy składający się ze znaków BCDEO
	 * @return packet
	 */
	public static String sendBoard(String board) {
		return join("CHB", board);
	}
	
	/**
	 * 9. Twój ruch - Informacja dla gracza o zmianie stanu – czy może wykonać ruch
	 * @return packet
	 */
	public static String yourMove() {
		return join("YMV");
	}
	
	/**
	 * 11. Koniec - Koniec gry - albo poprzez poddanie się albo zwycięstwo / porażkę
	 * @param winner wygrany
	 * @param reason przyczyna
	 * @return packet
	 */
	public static String gameOver(String winner, String reason) {
		return join("EOG", winner, reason);
	}
	
	/**
	 * 13. Błąd - Błąd protokołu
	 * @param message komunikat
	 * @return packet
	 */
	public static String error(String message) {
		return join("ERR", message);
	}
	
	/**
	 * 14. Niespójność - Błąd stanu
	 * @param message komunikat
	 * @return packet
	 */
	public static String invalidState(String message) {
		return join("ERS", message);
	}
	
	// odpowiedzi na pakiety w kierunku Server -> Client
	
	/**
	 * Odpowiedź 1. Login - Autoryzacja w systemie
	 * @param result 1 w przypadku powodzenia, 0 w przypadku niepowodzenia
	 * @return packet
	 */
	public static String responseLogin(String result) {
		return join("LGN", result);
	}
	
	/**
	 * Odpowiedź 2. Rejestracja - Utworzenie konta w systemie
	 * @param result 1 w przypadku powodzenia, 0 w przypadku niepowodzenia
	 * @return packet
	 */
	public static String responseRegister(String result) {
		return join("CRA", result);
	}
	
	/**
	 * Odpowiedź 3. Lista - Pobranie listy graczy
	 * @param playersList dla każdego gracza kolejno zwracana para nazwa / status, gdzie status równy A – oznacza gracza gotowego na grę, status B – oznacza gracza zajętego / w trakcie negocjacji / ogólnie nie gotowego na przyjęcie zaproszenia.
	 * @return packet
	 */
	public static String responsePlayersList(List<String> playersList) {
		return join("LSP", playersList);
	}
	
	/**
	 * Odpowiedź 4. Nowa gra - Prośba o rozpoczęcie nowej gry
	 * @param result 1 w przypadku gdy prośba została przekazana, 0 – jeśli wystąpił błąd (gracz o podanej nazwie nie istnieje).
	 * @return packet
	 */
	public static String responseNewGame(String result) {
		return join("RFP", result);
	}
	
}