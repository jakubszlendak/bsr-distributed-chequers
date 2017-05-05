package bsr.project.checkers.server;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import bsr.project.checkers.client.ClientData;
import bsr.project.checkers.config.Configuration;
import bsr.project.checkers.users.UsersDatabase;
import bsr.project.checkers.game.GameSession;
import bsr.project.checkers.game.GameInvitation;

public class ServerData {
	
	private Configuration configuration;
	private UsersDatabase usersDatabase;
	
	private List<ClientData> clients = new ArrayList<>();
	private List<GameSession> games = new ArrayList<>();
	private List<GameInvitation> invitations = new ArrayList<>();
	
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

	/* CLIENTS */
	public synchronized List<ClientData> getClients() {
		return clients;
	}

	public synchronized ClientData findLoggedClient(String login){
		return clients.stream()
			.filter(client -> client.getLogin() != null && client.getLogin().equals(login))
			.findFirst()
			.orElse(null); // null if not found
	}
	
	public synchronized void addClient(ClientData clientData) {
		clients.add(clientData);
	}
	
	public synchronized void removeClient(ClientData clientData) {
		clients.remove(clientData);
	}

	/* INVITATIONS */
	public synchronized List<GameInvitation> getInvitations() {
		refreshInvitations();
		return invitations;
	}

	private synchronized void refreshInvitations(){
		invitations = invitations.stream()
			.filter(inv -> inv.sender != null
				&& inv.receiver != null
				&& clients.contains(inv.sender)
				&& clients.contains(inv.receiver))
			.collect(Collectors.toList());
	}
	
	public synchronized GameInvitation findInvitation(ClientData invited){
		refreshInvitations();
		return invitations.stream()
			.filter(inv -> inv.receiver == invited)
			.findFirst()
			.orElse(null); // null if not found
	}

	public synchronized void removeInvitation(GameInvitation invitation) {
		invitations.remove(invitation);
	}

	/* GAME SESSIONS */
	public synchronized List<GameSession> getGames() {
		refreshGames();
		return games;
	}

	private synchronized void refreshGames(){
		games = games.stream()
			.filter(game -> game.getPlayer1() != null
				&& game.getPlayer2() != null
				&& clients.contains(game.getPlayer1())
				&& clients.contains(game.getPlayer2()))
			.collect(Collectors.toList());
	}

	public synchronized GameSession findGame(ClientData player){
		refreshGames();
		return games.stream()
			.filter(game -> game.getPlayer1() == player || game.getPlayer2() == player)
			.findFirst()
			.orElse(null); // null if not found
	}

	public synchronized void addGame(GameSession game) {
		games.add(game);
	}

	public synchronized void removeGame(GameSession game) {
		games.remove(game);
	}

}