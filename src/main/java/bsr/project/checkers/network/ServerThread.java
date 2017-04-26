package bsr.project.checkers.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import bsr.project.checkers.logger.Logs;

public class ServerThread extends Thread {
	
	private int port;
	private ServerSocket serverSocket;
	private volatile boolean active = true;
	
	public ServerThread(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			Logs.fatal("Could not create server socket on port " + port, e);
		}
		
		Logs.info("Server listening on port " + port);
		
		try {
			while (active) {
				Socket clientSocket = serverSocket.accept();
				ClientConnectionThread clientThread = new ClientConnectionThread(clientSocket);
				clientThread.start();
			}
		} catch (IOException e) {
			Logs.error(e);
		}
		
		Logs.debug("Server thread has stopped");
	}
	
	public synchronized void close() {
		if (active) {
			try {
				serverSocket.close();
				active = false;
				Logs.debug("Server Stopped");
			} catch (Exception e) {
				Logs.error(e);
			}
		}
	}
}