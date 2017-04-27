package bsr.project.checkers.server;

import java.util.ArrayList;
import java.util.List;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.config.Configuration;
import bsr.project.checkers.db.users.UsersDatabase;
import bsr.project.checkers.game.GameSession;

public class ServerData {
	
	private Configuration configuration;
	private UsersDatabase usersDatabase;
	
	private List<ClientData> clients = new ArrayList<>();
	private List<GameSession> games = new ArrayList<>();
	
	public ServerData() {
		configuration = new Configuration();
		usersDatabase = new UsersDatabase();
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public UsersDatabase getUsersDatabase() {
		return usersDatabase;
	}
	
	public synchronized List<ClientData> getClients() {
		return clients;
	}
	
	public synchronized List<GameSession> getGames() {
		return games;
	}
	
	public synchronized void addClient(ClientData clientData) {
		clients.add(clientData);
	}
	
	public synchronized void removeClient(ClientData clientData) {
		clients.remove(clientData);
	}
}