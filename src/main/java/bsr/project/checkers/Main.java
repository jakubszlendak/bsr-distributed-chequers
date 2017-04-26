package bsr.project.checkers;

import bsr.project.checkers.server.ServerApplication;

// TODO catching all exceptions in application
// TODO events on read, connect and disconnect
// TODO configuration: port number
// TODO command line for debugging - list connections, list games

public class Main {
    
    public static void main(String[] args) {
        new ServerApplication(args).run();
    }
}
