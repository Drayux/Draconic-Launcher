package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/*
 * Utility class for making requests to Mojang servers
 * Methods required for user login and logout
 */

public class AuthUtils {
	
	public static class Response {
		
		public String response;
		public int responseCode;
		
		public Response( String response, int responseCode ) {
			this.response = response;
			this.responseCode = responseCode;
			
		}
		
		public String toString() {
			return this.response;
			
		}
		
		public int getCode() {
			return this.responseCode;
			
		}
		
	}
	
	public static Response post( String endpoint, String payload ) throws IOException {
		HttpsURLConnection connection = null;
		OutputStream requestStream = null;
		InputStreamReader responseStream = null;
		BufferedReader reader = null;
		
		String responseString = null;
		int responseCode = 0;
		boolean errorCalled = false;
		
		try {
			URL authServer = new URL( "https://authserver.mojang.com/" + endpoint );
			connection = (HttpsURLConnection) authServer.openConnection();
			
			connection.setRequestMethod( "POST" );
			connection.setRequestProperty( "Content-Type", "application/json" );
			connection.setDoOutput( true );
			
			requestStream = connection.getOutputStream();
			requestStream.write( payload.getBytes( "UTF-8" ) );
			
			responseCode = connection.getResponseCode();
			//System.out.println(responseCode);
			//System.out.println( new String( payload.getBytes( "UTF-8" ) ) );
			
		}
		/*catch ( SocketException exception ) {
			errorCalled = true;

			return new Response( "{"
					+ "\"error\":\"SocketException\","
					+ "\"errorMessage\":\"Network is unreachable\""
					+ "}", 0 );
			
		}
		catch ( UnknownHostException exception ) {
			errorCalled = true;

			return new Response( "{"
					+ "\"error\":\"UnknownHostException\","
					+ "\"errorMessage\":\"Unable to resolve authserver hostname\""
					+ "}", 0 );
			
		}
		catch ( SSLHandshakeException exception ) {
			errorCalled = true;

			return new Response( "{"
					+ "\"error\":\"SSLHandshakeException\","
					+ "\"errorMessage\":\"Failed to establish a secure SSL connection\""
					+ "}", 0 );
			
		}*/
		catch ( IOException exception ) {
			errorCalled = true;
			//exception.printStackTrace();
			
			return new Response( "{"
					+ "\"error\":\"" + exception + "\","
					+ "\"errorMessage\":\"" + exception.getMessage() + "\""
					+ "}", 0 );
			
		}
		finally {
			if ( errorCalled ) {
				System.out.println( "[Draconic Launcher][AuthUtils][Warn] Failed to generate post request" );
				
			}
			
			if ( requestStream != null ) {
				requestStream.close();
				
			}
			
			if ( connection != null && errorCalled ) {
				connection.disconnect();
				
			}
			
		}
		
		try {
			if ( responseCode == 200 ) {
				responseStream = new InputStreamReader( connection.getInputStream() );
				
			}
			else {
				responseStream = new InputStreamReader( connection.getErrorStream() );
				
			}
			
			reader = new BufferedReader( responseStream );
			
			responseString = reader.readLine(); //Response can be read simply like this as it is always one line
			
		}
		catch ( IOException exception ) {
			System.out.println( "[Draconic Launcher][AuthUtils][Warn] Failed to read authorization response" );
			exception.printStackTrace();
			
			return new Response( "{"
					+ "\"error\":\"" + exception + "\""
					+ "}", 0 );
			
		}
		finally {
			if ( reader != null ) {
				reader.close();
				
			}
			
			if ( responseStream != null ) {
				responseStream.close();
				
			}
			
		}
		
		return new Response( responseString, responseCode );
		
	}
	
	//Used to interact with the authorization server
	//Post requests allow user to login and logout
	/* Depricated
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
		
	} */
	
	public static Response get( String url ) {
		return null;
		
	}
	
}
