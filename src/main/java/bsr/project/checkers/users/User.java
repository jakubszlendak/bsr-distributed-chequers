package bsr.project.checkers.users;

public class User {
	
	private String login;
	
	/**
	 * hash z hasła
	 */
	private String pwdCksum;
	
	public User(String login, String pwdCksum) {
		this.login = login;
		this.pwdCksum = pwdCksum;
	}
	
	public String getLogin() {
		return login;
	}
	
	public String getPwdCksum() {
		return pwdCksum;
	}
}
