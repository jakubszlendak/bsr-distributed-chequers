package bsr.project.checkers.server;

import bsr.project.checkers.console.ConsoleReader;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.network.ServerThread;

public class ServerApplication {
	
	private ServerThread serverThread;
	
	public ServerApplication(String[] args) {
	}
	
	public void run() {
		try {
			Logs.info("Starting server application...");
			
			ServerData serverData = new ServerData();
			
			serverThread = new ServerThread(serverData);
			serverThread.start();
			
			new ConsoleReader().readContinuously();
			
			serverThread.join();
			Logs.info("Server closed");
			
		} catch (Throwable e) {
			Logs.error(e);
		}
	}
}
