package bsr.project.checkers.events;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.dispatcher.AbstractEvent;
import bsr.project.checkers.server.ServerData;

public class PacketReceivedEvent extends AbstractEvent {
	
	private ServerData serverData;
	private ClientData clientData;
	private String received;
	
	public PacketReceivedEvent(ServerData serverData, ClientData clientData, String received) {
		this.serverData = serverData;
		this.clientData = clientData;
		this.received = received;
	}
	
	public ServerData getServerData() {
		return serverData;
	}
	
	public ClientData getClientData() {
		return clientData;
	}
	
	public String getReceived() {
		return received;
	}
}
