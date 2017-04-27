package bsr.project.checkers.protocol;

import java.text.ParseException;

public class PacketsParser {
	
	private static final String SEPARATOR = "#";
	
	public static void parsePacket(String packet) throws ParseException {
		
		String[] parts = packet.split(SEPARATOR);
		
		if (parts.length > 0) {
			
			String code = parts[0];
			
			if (code.length() != 3) throw new ParseException("invalid code: " + code, 0);
			
			try {
				switch (code) {
					// żądania w kierunku Client -> Server
					case "LGN": { // 1. Login - Autoryzacja w systemie
						String login = parts[1];
						String passwd = parts[2];
						// TODO
					}
					break;
					case "CRA": {
						String login = parts[1];
						String passwd = parts[2];
						// TODO
					}
					break;
					case "LSP": {
						// TODO
						
					}
					break;
					case "RFP": {
						// nazwa graca z którym chcemy się zmierzyć
						String login = parts[1];
						
					}
					break;
					case "GVU": {
						
					}
					break;
					case "LGO": {
						
					}
					break;
					// odpowiedzi na pakiety w kierunku Client -> Server
					case "RP1": {
						// 1 – zgoda, 0 – brak zgody
						String decision = parts[1];
						
					}
					break;
					case "ERR": {
						String message = parts[1];
					}
					break;
					case "ERS": {
						String message = parts[1];
						
					}
					break;
					
					default:
						throw new ParseException("unknown code: " + code, 2);
				}
				
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new ParseException("not enough parameters", 1);
			}
			
			
		}
		
		
	}
	
}