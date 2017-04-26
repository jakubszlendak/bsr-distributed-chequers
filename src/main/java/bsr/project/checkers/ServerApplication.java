package bsr.project.checkers.server;

import bsr.project.checkers.db.users.UsersDatabase;
import bsr.project.checkers.logger.Logs;

public class ServerApplication {
    
    private UsersDatabase usersDatabase;
    
    public ServerApplication(String[] args) {
        Logs.info("Starting server application...");
        usersDatabase = new UsersDatabase();
    }
    
    public void run() {
    
    
        Logs.info("Server closed");
    }
}
