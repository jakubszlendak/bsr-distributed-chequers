package bsr.project.checkers.protocol;

import java.util.List;
import java.util.ArrayList;

public class ProtocolPacket {

	private PacketType type;
	private List<Object> parameters;

	public ProtocolPacket(PacketType type, Object... parameters){
		this.type = type;
		this.parameters = new ArrayList<>();
		for (Object parameter : parameters){
			this.parameters.add(parameter);
		}
	}

	public PacketType getType(){
		return type;
	}

	public List<Object> getParameters(){
		return parameters;
	}
}