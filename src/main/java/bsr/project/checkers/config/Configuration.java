

public class Configuration {

	private final String CONFIG_FILE = "config.properties";

	private int port = 4000;

	public Configuration(){
		loadConfig();
	}

	public void loadConfig(){
		// TODO load properties from CONFIG_FILE

		// TODO no port set - getting default

	}

	public int getPort(){
		return port;
	}

}