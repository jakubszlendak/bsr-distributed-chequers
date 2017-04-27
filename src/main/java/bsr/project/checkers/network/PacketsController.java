package bsr.project.checkers.network;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.dispatcher.AbstractEvent;
import bsr.project.checkers.dispatcher.EventDispatcher;
import bsr.project.checkers.dispatcher.IEventObserver;
import bsr.project.checkers.events.PacketReceivedEvent;
import bsr.project.checkers.server.ServerData;

public class PacketsController implements IEventObserver {
	
	private ServerData serverData;
	
	public PacketsController(ServerData serverData) {
		this.serverData = serverData;
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
	
	}
}
