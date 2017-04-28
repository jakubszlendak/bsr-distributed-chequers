package bsr.project.checkers.network;

import java.text.ParseException;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.dispatcher.AbstractEvent;
import bsr.project.checkers.dispatcher.EventDispatcher;
import bsr.project.checkers.dispatcher.IEventObserver;
import bsr.project.checkers.events.PacketReceivedEvent;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.protocol.PacketsParser;
import bsr.project.checkers.protocol.ProtocolPacket;
import bsr.project.checkers.server.ServerData;

public class PacketsController implements IEventObserver {
	
	private ServerData serverData;
	private PacketsParser packetsParser;
	
	public PacketsController(ServerData serverData) {
		this.serverData = serverData;
		packetsParser = new PacketsParser();
		registerEvents();
	}
	
	@Override
	public void registerEvents() {
		EventDispatcher.registerEventObserver(PacketReceivedEvent.class, this);
	}
	
	@Override
	public void onEvent(AbstractEvent event) {
		
		event.bind(PacketReceivedEvent.class, e -> packetReceived(e.getServerData(), e.getClientData(), e.getReceived()));
	}
	
	private void packetReceived(ServerData serverData, ClientData clientData, String received) {
		
		Logs.info("packet received from " + clientData.getHostname() + ": " + received);
		
		try {
			ProtocolPacket packet = packetsParser.parsePacket(received);
		} catch (ParseException e) {
			Logs.error("Received packet is invalid", e);
			//TODO send message - error or invalid state
		}
	}
}
