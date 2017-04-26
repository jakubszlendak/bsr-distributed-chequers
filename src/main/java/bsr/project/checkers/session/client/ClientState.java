
public enum ClientState {

	/** niezalogowany */
	NOT_LOGGED_IN(0);

	private int id;

	public ClientState(int id){
		this.id = id;
	}
}