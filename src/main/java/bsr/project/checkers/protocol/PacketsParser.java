package bsr.project.checkers.protocol;

import java.text.ParseException;

import bsr.project.checkers.logger.Logs;

/**
 * narzędzie do parsowania odebranych pakietów
 */
public class PacketsParser {
	
	private static final String SEPARATOR = "#";
	
	private String getPart(String[] parts, int index) throws ParseException {
		if (index >= parts.length)
			throw new ParseException("not enough parameters in received packet", 1);
		return parts[index];
	}
	
	public ProtocolPacket parsePacket(String packet) throws ParseException {
		
		String[] parts = packet.split(SEPARATOR);
		
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
			break;
			case CREATE_ACCOUNT: { // 2. Rejestracja - Utworzenie konta w systemie
				String login = getPart(parts, 1);
				String passwd = getPart(parts, 2);
				return new ProtocolPacket(packetType, login, passwd);
				// TODO jeśli nie istnieje użytkownik, dodanie do bazy
				// TODO odesłanie odpowiedzi : CRA 1 w przypadku powodzenia
			}
			break;
			case LIST_PLAYERS: { // 3. Lista - Pobranie listy graczy
				return new ProtocolPacket(packetType);
			}
			break;
			case CREATE_REQUEST_FOR_GAME: { // 4. Nowa gra - Prośba o rozpoczęcie nowej gry
				// nazwa graca z którym chcemy się zmierzyć
				String login = getPart(parts, 1);
				return new ProtocolPacket(packetType, login);
			}
			break;
			case GIVE_UP: { // 10. Poddaj - zakoncz grę - poddaj się
				return new ProtocolPacket(packetType);
			}
			break;
			case LOG_OUT: { // 12. Wylogowanie - koniec komunikacji
				return new ProtocolPacket(packetType);
			}
			break;
			// odpowiedzi na pakiety z kierunku Server -> Client
			case INVITATION_FOR_GAME: { // 5. Pytanie o grę - przekazanie prośby o rozpoczęcie nowej gry
				// 1 – zgoda, 0 – brak zgody
				String decision = getPart(parts, 1);
				boolean agreed = decision.equals("1");
				
				return new ProtocolPacket(packetType, agreed);
			}
			break;
			case ERROR: { // 13. Błąd protokołu
				String message = getPart(parts, 1);
				return new ProtocolPacket(packetType, message);
				//TODO Logs.error("Błąd protokołu: " + message);
			}
			break;
			case INVALID_STATE: { // 14. Niespójność - błąd stanu
				String message = getPart(parts, 1);
				return new ProtocolPacket(packetType, message);
				//TODO Logs.error("Błąd stanu: " + message);
			}
			break;
			
			default:
				throw new ParseException("Invalid packet type received by server: " + code, 1);
				// TODO send protocol error
		}
		
	}
	
}