package bsr.project.checkers.protocol;

import java.util.ArrayList;
import java.util.List;

public class ProtocolPacket {
	
	private PacketType type;
	private List<Object> parameters;
	
	public ProtocolPacket(PacketType type, Object... parameters) {
		this.type = type;
		this.parameters = new ArrayList<>();
		for (Object parameter : parameters) {
			this.parameters.add(parameter);
		}
	}
	
	public PacketType getType() {
		return type;
	}
	
	public List<Object> getParameters() {
		return parameters;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getParameter(int index, Class<T> clazz) {
		if (index < 0 || index >= parameters.size())
			throw new IllegalArgumentException("invalid parameter index");
		Object param = parameters.get(index);
		if (!clazz.isInstance(param))
			throw new IllegalArgumentException("invalid parameter type");
		return (T) param;
	}
}