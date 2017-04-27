package bsr.project.checkers.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.dispatcher.EventDispatcher;
import bsr.project.checkers.events.PacketReceivedEvent;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.server.ServerData;

public class ClientConnectionThread extends Thread {
	
	private Socket clientSocket;
	private volatile boolean active = true;
	BufferedReader in = null;
	PrintWriter out = null;
	
	private ServerData serverData;
	private ClientData clientData;
	
	public ClientConnectionThread(ServerData serverData, Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.serverData = serverData;
		clientData = new ClientData(this);
	}
	
	@Override
	public void run() {
		
		// adding new client
		serverData.addClient(clientData);
		
		Logs.info("New client connected to server: " + getHostname());
		
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			
			while (active) {
				String received = in.readLine();
				
				if (received == null) {
					//end of the stream has been reached - client disconnected
					close();
					break;
				} else {
					
					// split received message by line feeds
					for (String line : received.split("\\r|\\n")) {
						if (!line.isEmpty()) {
							EventDispatcher.sendEvent(new PacketReceivedEvent(serverData, clientData, line));
						}
					}
					
				}
				
			}
			
		} catch (Exception e) {
			Logs.error(e);
		}
		
		// remove client from clients list
		serverData.removeClient(clientData);
	}
	
	public synchronized boolean isActive() {
		return active;
	}
	
	public synchronized void close() {
		if (active) {
			try {
				in.close();
				out.close();
				clientSocket.close();
				active = false;
				Logs.info("Client disconnected");
			} catch (IOException e) {
				Logs.error(e);
			}
		}
	}
	
	public synchronized void sendLine(String line) {
		if (!active) throw new IllegalStateException("Client connection is not active");
		if (out == null) throw new IllegalStateException("No output stream");
		out.println(line);
		out.flush();
	}
	
	public String getHostname() {
		return clientSocket.getInetAddress().getHostName();
	}
}