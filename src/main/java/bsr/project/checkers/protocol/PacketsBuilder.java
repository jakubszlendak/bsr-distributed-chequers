package bsr.project.checkers.protocol;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * narzędzie do budowania pakietów do wysłania
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

	public String buildPacket(ProtocolPacket packet){
		return join(packet.getType().getCode(), packet.getParameters());
	}
	
}