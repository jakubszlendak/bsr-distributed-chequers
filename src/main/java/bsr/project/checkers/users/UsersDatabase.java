package bsr.project.checkers.users;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import bsr.project.checkers.logger.Logs;

public class UsersDatabase {
	
	private List<User> users = new ArrayList<>();
	
	private final String USERS_DATAFILE = "accounts.properties";
	
	public UsersDatabase() {
		loadUsers();
	}

	public List<User> getUsers() {
		return users;
	}
	
	public synchronized void loadUsers() {
		Logs.debug("loading users database from file " + USERS_DATAFILE + "...");
		
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(USERS_DATAFILE);
			prop.load(input);
			
			for (String key : prop.stringPropertyNames()) {
				String pwdCksum = prop.getProperty(key);
				User user = new User(key, pwdCksum);
				users.add(user);
			}
			
		} catch (IOException ex) {
			Logs.error(ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private synchronized void saveUsers() {
		Logs.debug("saving new users database...");
		
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream(USERS_DATAFILE);
			
			for (User user : users) {
				prop.setProperty(user.getLogin(), user.getPwdCksum());
			}
			
			prop.store(output, null);
			
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean userExists(String login) {
		return users.stream().anyMatch(user -> user.getLogin().equals(login));
	}
	
	public boolean passwordValid(String login, String passwd) {
		String hashed = hashPassword(passwd);
		return users.stream()
				.anyMatch(user -> user.getLogin().equals(login) && user.getPwdCksum()
						.equals(hashed));
	}
	
	private String hashPassword(String pass) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes());
			String hashedPass = new BigInteger(1, md.digest()).toString(16);
			return hashedPass;
		} catch (NoSuchAlgorithmException e) {
			Logs.error("no MD5 MEssage Digest instance");
			return null;
		}
	}
	
	public synchronized void addUser(String login, String password) {
		if (!userExists(login)) {
			String hashed = hashPassword(password);
			users.add(new User(login, hashed));
			saveUsers();
			Logs.info("new user " + login + " has been added.");
		} else {
			Logs.warn("user " + login + " already exists");
		}
	}
}
