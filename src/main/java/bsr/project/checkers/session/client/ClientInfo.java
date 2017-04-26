
public class ClientInfo {

	private ClientConnectionThread clientConnection;
	private ClientState state;
	private GameSession gameSession = null;
	private String login = null;

	public ClientInfo(ClientConnectionThread clientConnection){
		this.clientConnection = clientConnection;
		state = NOT_LOGGED_IN;
	}

	public synchronized void setState(ClientState state){
		this.state = state;
	}

	public synchronized ClientState getState(){
		return state;
	}

}