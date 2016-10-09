package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

/*
 * Utility class for making requests to Mojang servers
 * Methods required for user login and logout
 */

public class AuthUtils {
	
	public static class Response {
		
		String response;
		int responseCode;
		
		public Response( String response, int responseCode ) {
			this.response = response;
			this.responseCode = responseCode;
			
		}
		
		public String toString() {
			return this.response;
			
		}
		
	}
	
	public static Response post( String endpoint, String payload ) {
		return new Response("test", 0);
		
	}
	
	//Used to interact with the authorization server
	//Post requests allow user to login and logout
	public static String _post( String endpoint, String payload ) {
		String response;
		HttpsURLConnection authserverConnection;
		
		OutputStream requestStream;
		int responseCode;
		
		//Opens a connection to the auth server
		try {
			URL url = new URL( "https://authserver.mojang.com/" + endpoint );
			authserverConnection = (HttpsURLConnection) url.openConnection();
			
			//Sets properties of the connection: type, headers...
			authserverConnection.setRequestMethod( "POST" );
			authserverConnection.setRequestProperty( "Content-Type", "application/json" );
			authserverConnection.setDoOutput( true );
			
		}
		catch ( IOException exception ) {
			exception.printStackTrace();
			return null;
			
		}
		
		try {
			//Creates a stream to write the actual request payload to the connection
			requestStream = authserverConnection.getOutputStream();
			
			//This is what actually puts the data in the request
			requestStream.write( payload.getBytes( "UTF-8" ) );
			requestStream.close(); //I think this 'submits' the request...maybe?
			
			//Gets the connection code for the request
			responseCode = authserverConnection.getResponseCode();
			//System.out.println( responseCode );
			
		}
		catch ( IOException exception ) {
			exception.printStackTrace();
			return null;
			
		}
		
		try {
			//Gets the response as a file stream and opens it
			InputStreamReader responseStream = new InputStreamReader( authserverConnection.getInputStream() );
			BufferedReader streamReader = new BufferedReader( responseStream );
			
			//Since output is always one line, simply calling the readline() function once is sufficient
			response = streamReader.readLine();
			
			responseStream.close();
			streamReader.close();
			
			return response;
			
		}
		catch ( IOException exception ) {
			exception.printStackTrace();
			return null;
			
		}
		
	}
	
	public static String get( String url ) {
		return "finish this!";
		
	}
	
}
