package json;

import file.Settings;
import util.ClientToken;

@SuppressWarnings( "unused" )
public class AuthPayload {

	private String username;
	private String password;
	private String clientToken = Settings.settings.clientToken;
	//may change this if necessary in the future
	private boolean requestUser = false;
	
	public Agent agent = new Agent( "Minecraft", 1 );
	
	public AuthPayload( String username, char[] passwordArray ) {
		this.username = username;
		this.password = new String( passwordArray );
		
	}
	
	static class Agent {
		
		private String name;
		private int version;
		
		private Agent( String name, int version ) {
			this.name = name;
			this.version = version;
			
		}
		
	}
	
}
