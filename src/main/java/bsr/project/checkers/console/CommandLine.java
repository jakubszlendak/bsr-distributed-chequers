package bsr.project.checkers.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.dispatcher.EventDispatcher;
import bsr.project.checkers.events.ServerCloseEvent;
import bsr.project.checkers.game.Board;
import bsr.project.checkers.game.GameSession;
import bsr.project.checkers.logger.Logs;
import bsr.project.checkers.server.ServerData;
import bsr.project.checkers.users.User;

public class CommandLine {
	
	private boolean exit = false;
	private ServerData serverData;
	
	public CommandLine(ServerData serverData) {
		this.serverData = serverData;
		Logs.info("Type \"help\" to list available commands.");
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
		} else if (cmd.equals("server close")) {
			serverClose();
		} else if (cmd.equals("clients")) {
			int number = 1;
			Logs.info("Connected clients [" + serverData.getClients().size() + "]:");
			for (ClientData clientData : serverData.getClients()) {
				StringBuilder sb = new StringBuilder();
				sb.append(Integer.toString(number));
				sb.append(". Client: ");
				sb.append(clientData.getClientConnection().getHostname());
				sb.append(", state: ");
				sb.append(clientData.getState().name());
				sb.append(", login: ");
				sb.append(clientData.getLogin());
				Logs.info(sb.toString());
				number++;
			}
		} else if (cmd.equals("games")) {
			int number = 1;
			Logs.info("Active game sessions [" + serverData.getGames().size() + "]:");
			for (GameSession game : serverData.getGames()) {
				StringBuilder sb = new StringBuilder();
				sb.append(Integer.toString(number));
				sb.append(". ");
				sb.append(game.getPlayer1().getLogin());
				sb.append(" vs ");
				sb.append(game.getPlayer2().getLogin());
				sb.append(", current Player: ");
				sb.append(game.getCurrentPlayer().getLogin());
				sb.append(", current board:");
				Logs.info(sb.toString());
				
				char[][] map = game.getBoard().getMap();
				for (int y = 0; y < Board.BOARD_SIZE; y++) {
					StringBuilder sb2 = new StringBuilder();
					for (int x = 0; x < Board.BOARD_SIZE; x++) {
						sb2.append(map[x][y]);
					}
					Logs.info(sb2.toString());
				}
				
				number++;
			}
		} else if (cmd.equals("users")) {
			int number = 1;
			List<User> users = serverData.getUsersDatabase().getUsers();
			Logs.info("Registered users [" + users.size() + "]:");
			for (User user : users) {
				StringBuilder sb = new StringBuilder();
				sb.append(Integer.toString(number));
				sb.append(". ");
				sb.append(user.getLogin());
				Logs.info(sb.toString());
				
				number++;
			}
		} else if (cmd.equals("kick all")) {
			disconnectAll();
		} else {
			Logs.warn("unknown command: " + cmd);
		}
	}
	
	private void printHelp() {
		Logs.info("Available commands:");
		Logs.info("exit - close all connections and exit");
		Logs.info("server close - close server connection");
		Logs.info("clients - list connected clients");
		Logs.info("games - list active game sessions");
		Logs.info("users - list registered users");
		Logs.info("kick all - close all client connections");
	}
	
	private void disconnectAll() {
		// to avoid concurrent modification
		List<ClientData> shallowCopy = new ArrayList<>(serverData.getClients());
		shallowCopy.forEach(clientData -> clientData.getClientConnection().close());
	}
	
	private void serverClose() {
		EventDispatcher.sendEvent(new ServerCloseEvent());
	}
}