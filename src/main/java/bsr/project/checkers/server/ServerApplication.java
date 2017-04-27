package bsr.project.checkers.server;

import bsr.project.checkers.console.ConsoleReader;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.network.PacketsController;
import bsr.project.checkers.network.ServerThread;

public class ServerApplication {
	
	private ServerData serverData;
	private ServerThread serverThread;
	private PacketsController packetsController;
	
	public ServerApplication(String[] args) {
	}
	
	public void run() {
		try {
			Logs.info("Starting server application...");
			
			serverData = new ServerData();
			
			packetsController = new PacketsController(serverData);
			
			serverThread = new ServerThread(serverData);
			serverThread.start();
			
			new ConsoleReader(serverData).readContinuously();
			
			serverThread.join();
			Logs.info("Server closed");
			
		} catch (Throwable e) {
			Logs.error(e);
		}
	}
}
