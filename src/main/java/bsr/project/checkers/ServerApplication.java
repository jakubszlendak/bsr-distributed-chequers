package bsr.project.checkers;

import bsr.project.checkers.config.Configuration;
import bsr.project.checkers.console.CommandLine;
import bsr.project.checkers.db.users.UsersDatabase;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.network.ServerThread;

public class ServerApplication {
	
	private UsersDatabase usersDatabase;
	private Configuration configuration;
	
	private ServerThread serverThread;
	
	public ServerApplication(String[] args) {
		Logs.info("Starting server application...");
		usersDatabase = new UsersDatabase();
		configuration = new Configuration();
	}
	
	public void run() {
		try {
			
			serverThread = new ServerThread(configuration.getPort());
			serverThread.start();
			
			new CommandLine().read();
			
			serverThread.join();
			Logs.info("Server closed");
			
		} catch (Throwable e) {
			Logs.error(e);
		}
	}
}
