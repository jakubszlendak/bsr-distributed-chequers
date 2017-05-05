package bsr.project.checkers.protocol;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.game.Board;

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
		// prepend type code to parameters array
		List<Object> objs = new ArrayList<>();
		objs.add(type.getCode());
		for(Object obj : parameters){
			objs.add(obj);
		}
		return join(objs.toArray(new Object[objs.size()]));
	}
	
	/**
	 * 1. Login - Autoryzacja w systemie
	 * @param result 1 w przypadku powodzenia, 0 w przypadku niepowodzenia
	 */
	public String responseLogin(boolean result){
		return buildPacket(PacketType.LOG_IN, result ? "1" : "0");
	}

	/**
	 * 2. Rejestracja - Utworzenie konta w systemie
	 * @param result 1 w przypadku powodzenia, 0 w przypadku niepowodzenia
	 */
	public String responseCreateAccount(boolean result){
		return buildPacket(PacketType.CREATE_ACCOUNT, result ? "1" : "0");
	}

	/**
	 * 3. Lista - Pobranie listy graczy
	 * @param playersList lista graczy (klientów)
	 */
	public String responseListPlayers(List<ClientData> clients){
		List<String> params = new ArrayList<>();
		// dla każdego gracza kolejno zwracana para nazwa / status, gdzie status równy A – oznacza gracza gotowego na grę, status B – oznacza gracza zajętego / w trakcie negocjacji / ogólnie nie gotowego na przyjęcie zaproszenia
		for (ClientData client : clients){
			if (client.getLogin() != null){
				params.add(client.getLogin()); // nazwa
				// status równy A – oznacza gracza gotowego na grę, status B – oznacza gracza zajętego
				String status = client.isReadyForNewGame() ? "A" : "B";
				params.add(status);
			}
		}
		return buildPacket(PacketType.LIST_PLAYERS, params.toArray(new String[params.size()]));
	}

	/**
	 * 4. Nowa gra - Prośba o rozpoczęcie nowej gry
	 * @param result 1 w przypadku gdy prośba została przekazana, 0 – jeśli wystąpił błąd (gracz o podanej nazwie nie istnieje)
	 */
	public String responseCreateRequestForGame(boolean result){
		return buildPacket(PacketType.CREATE_REQUEST_FOR_GAME, result ? "1" : "0");
	}

	/**
	 * 5. Pytanie o grę - przekazanie prośby o rozpoczęcie nowej gry
	 * @param requestLogin nazwa gracza który nas zaprasza
	 */
	public String requestInvitationForGame(String requestLogin){
		return buildPacket(PacketType.INVITATION_FOR_GAME, requestLogin);
	}

	/**
	 * 6. Przekazanie odpowiedzi - przekazanie odpowiedzi na prośbę o nową grę
	 * @param agreed 1 - zgoda, 0 - brak zgody
	 */
	public String requestResponseForInvitation(boolean agreed){
		return buildPacket(PacketType.RESPONSE_FOR_INVITATION, agreed ? "1" : "0");
	}

	/**
	 * 7. Inicjacja gry - rozpoczęcie nowej rozgrywki
	 * @param yourColor kolor, którymi będzie grał dany zawodnik {@see BoardSymbols}
	 */
	public String requestNewGame(char yourColor){
		return buildPacket(PacketType.NEW_GAME, yourColor);
	}

	/**
	 * 8. Plansza - przesłanie planszy
	 * @param board plansza
	 */
	public String requestChangedBoard(Board board){
		// plansza - 64 znakowy łańcuch tekstowy składający się ze znaków BCDEO
		return buildPacket(PacketType.CHANGED_BOARD, board.toString());
	}

	/**
	 * 9. Twój ruch - Informacja dla gracza o zmianie stanu – czy może wykonać ruch
	 */
	public String requestYourMove(){
		return buildPacket(PacketType.YOUR_MOVE);
	}

	/**
	 * 11. Koniec - Koniec gry - albo poprzez poddanie się albo zwycięstwo / porażkę
	 * @param winner wygrany - login
	 * @param reason przyczyna
	 */
	public String requestGameOver(String winner, String reason) {
		return buildPacket(PacketType.GAME_OVER, winner, reason);
	}

	/**
	 * 13. Błąd protokołu
	 * @param message komunikat
	 */
	public String requestProtocolError(String message){
		return buildPacket(PacketType.ERROR, message);
	}

	/**
	 * 14. Niespójność - błąd stanu
	 * @param message komunikat
	 */
	public String requestInvalidState(String message){
		return buildPacket(PacketType.INVALID_STATE, message);
	}

	/**
	 * 15. Wykonanie ruchu
	 * @param result 1 – prawidłowy ruch, 0 – nieprawidłowy ruch
	 */
	public String responseMakeMove(boolean result){
		return buildPacket(PacketType.MAKE_MOVE, result ? "1" : "0");
	}

}