package bsr.project.checkers.console;

import java.util.Scanner;

import bsr.project.checkers.dispatcher.EventDispatcher;
import bsr.project.checkers.events.ServerCloseEvent;
import bsr.project.checkers.logger.Logs;

public class ConsoleReader {
	
	private boolean exit = false;
	
	public void readContinuously() {
		while (!exit) {
			String cmd = readLine();
			execute(cmd);
		}
	}
	
	public String readLine() {
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}
	
	public void execute(String cmd) {
		if (cmd.length() == 0) return;
		
		if (cmd.equals("exit")) {
			exit = true;
		} else if (cmd.equals("help")) {
			printHelp();
		} else if (cmd.equals("server open")) {
			//TODO
		} else if (cmd.equals("server close")) {
			EventDispatcher.sendEvent(new ServerCloseEvent());
		} else if (cmd.equals("list clients")) {
			//TODO
		} else if (cmd.equals("list games")) {
			//TODO
		} else if (cmd.equals("disconnect all")) {
			//TODO
		} else if (cmd.startsWith("disconnect ")) {
			//TODO get client number
			//TODO disconnect client
		} else {
			Logs.warn("unknown command: " + cmd);
		}
	}
	
	private void printHelp() {
	
	}
	
}