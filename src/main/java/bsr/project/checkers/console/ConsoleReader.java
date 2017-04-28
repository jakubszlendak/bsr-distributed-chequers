package bsr.project.checkers.console;

import java.util.Scanner;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.dispatcher.EventDispatcher;
import bsr.project.checkers.events.ServerCloseEvent;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.server.ServerData;

public class ConsoleReader {
	
	private boolean exit = false;
	private ServerData serverData;
	
	public ConsoleReader(ServerData serverData) {
		this.serverData = serverData;
		Logs.info("type \"help\" to list available commands.");
	}
	
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
		if (cmd.length() == 0)
			return;
		
		if (cmd.equals("exit")) {
			disconnectAll();
			serverClose();
			exit = true;
		} else if (cmd.equals("help")) {
			printHelp();
		} else if (cmd.equals("server open")) {
			//TODO
		} else if (cmd.equals("server close")) {
			serverClose();
		} else if (cmd.equals("list clients")) {
			int number = 1;
			for (ClientData clientData : serverData.getClients()) {
				Logs.info(Integer.toString(number) + ". Client: " + clientData.getClientConnection().getHostname());
				number++;
			}
		} else if (cmd.equals("list games")) {
			//TODO
		} else if (cmd.equals("disconnect all")) {
			disconnectAll();
		} else if (cmd.startsWith("disconnect ")) {
			//TODO get client number
			//TODO disconnect client
		} else {
			Logs.warn("unknown command: " + cmd);
		}
	}
	
	private void printHelp() {
		Logs.info("Available commands:");
		Logs.info("exit - close all connections and exit");
		Logs.info("server open");
		Logs.info("server close");
		Logs.info("list clients");
		Logs.info("list games - list open game sessions");
		Logs.info("disconnect all - close all client connections");
		Logs.info("disconnect [number]");
	}
	
	private void disconnectAll() {
		serverData.getClients().forEach(clientData -> clientData.getClientConnection().close());
	}
	
	private void serverClose() {
		EventDispatcher.sendEvent(new ServerCloseEvent());
	}
}