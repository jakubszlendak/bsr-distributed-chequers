public enum PacketType {

	/**
	 * 1. Login - Autoryzacja w systemie
	 * Request:  C -> S: login + hasło
	 * Response: S -> C: result (1 w przypadku powodzenia, 0 w przypadku niepowodzenia)
	 */
	LOG_IN(1, "LGN"),

	/**
	 * 2. Rejestracja - Utworzenie konta w systemie
	 * Request:  C -> S: login + hasło
	 * Response: S -> C: result (1 w przypadku powodzenia, 0 w przypadku niepowodzenia)
	 */
	CREATE_ACCOUNT(2, "CRA"),

	/**
	 * 3. Lista - Pobranie listy graczy
	 * Request:  C -> S
	 * Response: S -> C: playersList (dla każdego gracza kolejno zwracana para nazwa / status, gdzie status równy A – oznacza gracza gotowego na grę, status B – oznacza gracza zajętego / w trakcie negocjacji / ogólnie nie gotowego na przyjęcie zaproszenia)
	 */
	LIST_PLAYERS(3, "LSP"),

	/**
	 * 4. Nowa gra - Prośba o rozpoczęcie nowej gry
	 * Request:  C -> S: login (gracza, z którym chce się zmierzyć)
	 * Response: S -> C: result (1 w przypadku gdy prośba została przekazana, 0 – jeśli wystąpił błąd (gracz o podanej nazwie nie istnieje))
	 */
	CREATE_REQUEST_FOR_GAME(4, "RFP"),

	/**
	 * 5. Pytanie o grę - przekazanie prośby o rozpoczęcie nowej gry
	 * Request:  S -> C: requestLogin (nazwa gracza który nas zaprasza)
	 * Response: C -> S: 1 – zgoda, 0 – brak zgody
	 */
	INVITATION_FOR_GAME(5, "RP1"),

	/**
	 * 6. Przekazanie odpowiedzi - przekazanie odpowiedzi na prośbę o nową grę
	 * Request:  S -> C: decision (1 - zgoda, 0 - brak zgody)
	 */
	RESPONSE_FOR_INVITATION(6, "RP2"),

	/**
	 * 7. Inicjacja gry - rozpoczęcie nowej rozgrywki
	 * Request:  S -> C: yourColor (kolor, którymi będzie grał dany zawodnik)
	 */
	NEW_GAME(7, "INI"),

	/**
	 * 8. Plansza - przesłanie planszy
	 * Request:  S -> C: board (64 znakowy łańcuch tekstowy składający się ze znaków BCDEO)
	 */
	CHANGED_BOARD(8, "CHB"),

	/**
	 * 9. Twój ruch - Informacja dla gracza o zmianie stanu – czy może wykonać ruch
	 * Request:  S -> C
	 */
	YOUR_MOVE(9, "YMV"),

	/**
	 * 10. Poddaj - zakoncz grę - poddaj się
	 * Request:  C -> S
	 */
	GIVE_UP(10, "GVU"),

	/**
	 * 11. Koniec - Koniec gry - albo poprzez poddanie się albo zwycięstwo / porażkę
	 * Request:  S -> C: winner (wygrany) + reason (przyczyna)
	 */
	GAME_OVER(11, "EOG"),

	/**
	 * 12. Wylogowanie - koniec komunikacji
	 * Request:  C -> S
	 */
	LOG_OUT(12, "LGO"),

	/**
	 * 13. Błąd protokołu
	 * Request:  S -> C: message
	 */
	ERROR(13, "ERR"),

	/**
	 * 14. Niespójność - błąd stanu
	 * Request:  S -> C: message
	 */
	INVALID_STATE(14, "ERS");


	private int id;
	private String code;

	PacketType(int id, String code){
		this.id = id;
		this.code = code;
	}

	public int getId(){
		return id;
	}

	public String getCode(){
		return code;
	}

	/**
	 * @param code
	 * @return PacketType matching to the given code or null
	 */
	public static PacketType parseByCode(String code){
		for(PacketType type : values()){
			if(type.getCode().equals(code)){
				return type;
			}
		}
		return null;
	}
}