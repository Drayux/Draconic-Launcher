package json;

import file.Settings;
import util.ClientToken;

/*
 * Login payload to be SENT to Mojang auth server
 * The server will respond in the format of AuthResponse ( 200 ) or ErrorResponse
 */

@SuppressWarnings( "unused" )
public class AuthPayload {

	private String username;
	private String password;
	private String clientToken = Settings.settings.clientToken;
	//may change this if necessary in the future
	private boolean requestUser;
	
	public Agent agent = new Agent( "Minecraft", 1 );
	
	public AuthPayload( String username, char[] passwordArray ) {
		this.username = username;
		this.password = new String( passwordArray );
		this.requestUser = false;
		
	}
	
	public AuthPayload( String username, char[] passwordArray, boolean requestUser ) {
		this.username = username;
		this.password = new String( passwordArray );
		this.requestUser = requestUser;
		
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
