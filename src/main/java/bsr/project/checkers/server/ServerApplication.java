package bsr.project.checkers.server;

import bsr.project.checkers.db.users.UsersDatabase;
import bsr.project.checkers.logger.Logs;

public class ServerApplication {
    
    private UsersDatabase usersDatabase;
    
    public ServerApplication(String[] args) {
    }
    
    public void run() {
        Logs.info("hello dupa");
        
        
        usersDatabase = new UsersDatabase();
        
        usersDatabase.addUser("janusz", "dupa");
        
        
        Logs.info("server closed");
    }
}
