package bsr.project.checkers.easter;

import java.text.ParseException;
import java.nio.charset.Charset;
import java.util.Base64;

public class Ea5t3r3gg {

	public static void validatePacketCode(String code) throws ParseException {
		if ("ZHVwYQ==".equals(base64Encode(code.toLowerCase()))) {
			throw new ParseException(base64Decode("Q29uZ3JhdHVsYXRpb25zLCB5b3UgaGF2ZSBkaXNjb3ZlcmVkIGFuIGVhc3RlciBlZ2ch"), 0);
		}
	}

	private static String base64Encode(String token) {
		byte[] encodedBytes = Base64.getEncoder().encode(token.getBytes());
		return new String(encodedBytes, Charset.forName("UTF-8"));
	}


	private static String base64Decode(String token) {
		byte[] decodedBytes = Base64.getDecoder().decode(token.getBytes());
		return new String(decodedBytes, Charset.forName("UTF-8"));
	}
}