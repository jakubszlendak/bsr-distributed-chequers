package bsr.project.checkers.game;

import bsr.project.checkers.client.ClientData;

public class GameInvitation {
	
	public ClientData sender;
	
	public ClientData receiver;
	
	public GameInvitation(ClientData sender, ClientData receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}
}