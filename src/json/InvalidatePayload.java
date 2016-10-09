package json;

import file.Settings;

/*
 * Logout payload to be SENT to auth server
 * The server will respond with nothing if successful ( 200 ) or [not sure yet figure this out] if not
 */

@SuppressWarnings( "unused" )
public class InvalidatePayload {

	private String accessToken; //initialized with user profile access token
	private String clientToken = Settings.settings.clientToken;
	
}
