package bsr.project.checkers.events;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.dispatcher.AbstractEvent;

public class PacketReceivedEvent extends AbstractEvent {
	
	private ClientData clientData;
	private String received;
	
	public PacketReceivedEvent(ClientData clientData, String received) {
		this.clientData = clientData;
		this.received = received;
	}
	
	public ClientData getClientData() {
		return clientData;
	}
	
	public String getReceived() {
		return received;
	}
}
