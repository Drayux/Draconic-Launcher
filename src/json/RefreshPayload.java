package json;

import file.Settings;

/*
 * Refresh payload to be SENT to Mojang auth server
 * The server will respond in the format of AuthResponse ( 200 ) or ErrorResponse
 */

@SuppressWarnings( "unused" )
public class RefreshPayload {

	private String accessToken; //this will be initialized with the saved accesstoken in the userprofile file
	private String clientToken = Settings.settings.clientToken;
	private boolean requestUser;
	
	//Makes auth server return an error
	//private SelectedProfile selectedProfile;
	
	public RefreshPayload() {
		this.requestUser = false;
		
	}
	
	public RefreshPayload( boolean requestUser ) {
		this.requestUser = requestUser;
		
	}
	
	/* Unused
	static class SelectedProfile {
		
		private String id;   //Would initialize with user id
		private String name; //and name if used
		
	}*/
	
}
