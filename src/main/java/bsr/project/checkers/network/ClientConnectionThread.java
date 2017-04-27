package bsr.project.checkers.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import bsr.project.checkers.client.ClientData;
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
		
		Logs.info("Client connected - " + getHostname());
		
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			
			while (active) {
				String received = in.readLine();
				
				if (received == null) {
					
					//end of the stream has been reached
					// TODO send event disconnection
					
					close();
					break;
					
				} else {
					
					System.out.println("Client Says :" + received);
					//TODO send event: received bytes from client, send to parser
					
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
				Logs.info("Connection to closed");
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