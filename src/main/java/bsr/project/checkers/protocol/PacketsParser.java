package bsr.project.checkers.protocol;

import java.text.ParseException;

import bsr.project.checkers.game.Point;

/**
 * narzędzie do parsowania odebranych pakietów
 */
public class PacketsParser {
	
	private static final String SEPARATOR = "#";
	
	private String getPart(String[] parts, int index) throws ParseException {
		if (index >= parts.length)
			throw new ParseException("not enough parameters", 0);
		return parts[index];
	}
	
	private int getPartInt(String[] parts, int index) throws ParseException {
		String numberStr = getPart(parts, index);
		try {
			return Integer.parseInt(numberStr);
		} catch (NumberFormatException e) {
			throw new ParseException("invalid number format: " + numberStr, 0);
		}
	}
	
	public ProtocolPacket parsePacket(String packetStr) throws ParseException {
		
		String[] parts = packetStr.split(SEPARATOR);
		
		if (parts.length == 0)
			throw new ParseException("Empty packet", 0);
		
		String code = getPart(parts, 0);
		
		PacketType packetType = PacketType.parseByCode(code);
		if (packetType == null)
			throw new ParseException("Unknown packet code: " + code, 0);
		
		switch (packetType) {
			// żądania w kierunku Client -> Server
			case LOG_IN: { // 1. Login - Autoryzacja w systemie
				String login = getPart(parts, 1);
				String passwd = getPart(parts, 2);
				return new ProtocolPacket(packetType, login, passwd);
				// TODO próba zalogowania, weryfikacja hasła z bazą użytkowników, zmiana stanu na LOGGED_IN
				// TODO odesłanie odpowiedzi : LGN 1 w przypadku powodzenia
			}
			case CREATE_ACCOUNT: { // 2. Rejestracja - Utworzenie konta w systemie
				String login = getPart(parts, 1);
				String passwd = getPart(parts, 2);
				return new ProtocolPacket(packetType, login, passwd);
				// TODO jeśli nie istnieje użytkownik, dodanie do bazy
				// TODO odesłanie odpowiedzi : CRA 1 w przypadku powodzenia
			}
			case LIST_PLAYERS: { // 3. Lista - Pobranie listy graczy
				return new ProtocolPacket(packetType);
			}
			case CREATE_REQUEST_FOR_GAME: { // 4. Nowa gra - Prośba o rozpoczęcie nowej gry
				// nazwa graca z którym chcemy się zmierzyć
				String login = getPart(parts, 1);
				return new ProtocolPacket(packetType, login);
			}
			case GIVE_UP: { // 10. Poddaj - zakoncz grę - poddaj się
				return new ProtocolPacket(packetType);
			}
			case LOG_OUT: { // 12. Wylogowanie - koniec komunikacji
				return new ProtocolPacket(packetType);
			}
			case MAKE_MOVE: { // 15. Wykonanie ruchu
				int xFrom = getPartInt(parts, 1);
				int yFrom = getPartInt(parts, 2);
				int xTo = getPartInt(parts, 3);
				int yTo = getPartInt(parts, 4);
				return new ProtocolPacket(packetType, new Point(xFrom, yFrom), new Point(xTo, yTo));
			}
			// odpowiedzi na pakiety z kierunku Server -> Client
			case INVITATION_FOR_GAME: { // 5. Pytanie o grę - przekazanie prośby o rozpoczęcie nowej gry
				// 1 – zgoda, 0 – brak zgody
				String decision = getPart(parts, 1);
				if (!decision.equals("1") && !decision.equals("0"))
					throw new ParseException("Invalid decision format: " + decision, 0);
				Boolean agreed = decision.equals("1");
				return new ProtocolPacket(packetType, agreed);
			}
			case ERROR: { // 13. Błąd protokołu
				String message = getPart(parts, 1);
				return new ProtocolPacket(packetType, message);
			}
			case INVALID_STATE: { // 14. Niespójność - błąd stanu
				String message = getPart(parts, 1);
				return new ProtocolPacket(packetType, message);
			}
			
			default:
				throw new ParseException("Invalid packet type: " + code, 0);
		}
		
	}
	
}