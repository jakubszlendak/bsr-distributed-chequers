package bsr.project.checkers.protocol;

import java.text.ParseException;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.server.ServerData;

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
	
	public void parsePacket(String packet, ServerData serverData, ClientData clientData) throws ParseException {
		
		String[] parts = packet.split(SEPARATOR);
		
		if (parts.length > 0) {
			
			String code = getPart(parts, 0);
			
			switch (code) {
				// żądania w kierunku Client -> Server
				case "LGN": { // 1. Login - Autoryzacja w systemie
					String login = getPart(parts, 1);
					String passwd = getPart(parts, 2);
					// TODO próba zalogowania, weryfikacja hasła z bazą użytkowników, zmiana stanu na LOGGED_IN
					// TODO odesłanie odpowiedzi : LGN 1 w przypadku powodzenia
				}
				break;
				case "CRA": { // 2. Rejestracja - Utworzenie konta w systemie
					String login = getPart(parts, 1);
					String passwd = getPart(parts, 2);
					// TODO jeśli nie istnieje użytkownik, dodanie do bazy
					// TODO odesłanie odpowiedzi : CRA 1 w przypadku powodzenia
					
				}
				break;
				case "LSP": { // 3. Lista - Pobranie listy graczy
					// TODO
					
				}
				break;
				case "RFP": { // 4. Nowa gra - Prośba o rozpoczęcie nowej gry
					// nazwa graca z którym chcemy się zmierzyć
					String login = getPart(parts, 1);
					
				}
				break;
				case "GVU": { // 10. Poddaj - zakoncz grę - poddaj się
				
				}
				break;
				case "LGO": { // 12. Wylogowanie - koniec komunikacji
				
				}
				break;
				// odpowiedzi na pakiety z kierunku Server -> Client
				case "RP1": {
					// 1 – zgoda, 0 – brak zgody
					String decision = getPart(parts, 1);
					boolean agreed = decision.equals("1");
					
				}
				break;
				case "ERR": { // 13. Błąd protokołu
					String message = getPart(parts, 1);
					Logs.error("Błąd protokołu: " + message);
				}
				break;
				case "ERS": { // 14. Niespójność - błąd stanu
					String message = getPart(parts, 1);
					Logs.error("Błąd stanu: " + message);
				}
				break;
				
				default:
					throw new ParseException("Unknown packet code: " + code, 0);
			}
			
			
		}
		
		
	}
	
}