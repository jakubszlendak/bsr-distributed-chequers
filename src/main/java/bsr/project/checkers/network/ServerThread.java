package bsr.project.checkers.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import bsr.project.checkers.dispatcher.AbstractEvent;
import bsr.project.checkers.dispatcher.EventDispatcher;
import bsr.project.checkers.dispatcher.IEventObserver;
import bsr.project.checkers.events.ServerCloseEvent;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.server.ServerData;

public class ServerThread extends Thread implements IEventObserver {
	
	private ServerSocket serverSocket;
	private volatile boolean active = true;
	
	private ServerData serverData;
	
	public ServerThread(ServerData serverData) {
		this.serverData = serverData;
		registerEvents();
	}
	
	@Override
	public void registerEvents() {
		EventDispatcher.registerEventObserver(ServerCloseEvent.class, this);
	}
	
	@Override
	public void run() {
		
		int port = serverData.getConfiguration().getPort();
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			Logs.fatal("Could not create server socket on port " + port, e);
		}
		
		Logs.info("Server listening on port " + port);
		
		try {
			while (active) {
				Socket clientSocket = serverSocket.accept();
				ClientConnectionThread clientThread = new ClientConnectionThread(serverData, clientSocket);
				clientThread.start();
			}
		} catch (SocketException e) {
			Logs.info("Server connection closed");
		} catch (IOException e) {
			Logs.error(e);
		}
		
		EventDispatcher.unregisterEventObserver(this);
		Logs.debug("Server thread has finished");
	}
	
	public synchronized void close() {
		if (active) {
			try {
				serverSocket.close();
				active = false;
			} catch (Exception e) {
				Logs.error(e);
			}
		}
	}
	
	@Override
	public void onEvent(AbstractEvent event) {
		
		event.bind(ServerCloseEvent.class, e -> close());
		
	}
}