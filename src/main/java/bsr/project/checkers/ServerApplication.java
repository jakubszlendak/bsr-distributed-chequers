package bsr.project.checkers;

import bsr.project.checkers.console.CommandLine;
import bsr.project.checkers.controller.PacketsController;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.server.ServerData;
import bsr.project.checkers.server.ServerThread;

public class ServerApplication {
	
	private ServerData serverData;
	private ServerThread serverThread;
	private PacketsController packetsController;
	
	public ServerApplication(String[] args) {
	}
	
	public void run() {
		try {
			Logs.debug("Starting server application...");
			
			serverData = new ServerData();
			
			packetsController = new PacketsController(serverData);
			
			serverThread = new ServerThread(serverData);
			serverThread.start();
			
			new CommandLine(serverData).readContinuously();
			
			serverThread.join();
			
		} catch (Throwable e) {
			Logs.error(e);
		}
	}
}
