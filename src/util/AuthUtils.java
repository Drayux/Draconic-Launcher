package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

public class AuthUtils {
	
	public static String post( String endpoint, String payload ) {
		String response = null;
		
		//rewrite this! try except finally needed on specific parts
		try {
			//Opens a connection to the auth server
			URL url = new URL( "https://authserver.mojang.com/authenticate" );
			HttpsURLConnection authserverConnection = (HttpsURLConnection) url.openConnection();
			
			//Sets properties of the connection: type, headers...
			authserverConnection.setRequestMethod( "POST" );
			authserverConnection.setRequestProperty( "Content-Type", "application/json" );
			authserverConnection.setDoOutput( true );
			
			//Calls IOException if connection...fails? <-- Look into this
			//Creates a stream to write the actual request payload to the connection
			OutputStream requestStream = authserverConnection.getOutputStream();
			
			//This is what actually puts the data in the request
			requestStream.write( payload.getBytes( "UTF-8" ) );
			requestStream.close(); //I think this 'submits' the request...maybe?

			//Gets the connection code for the request
			int responseCode = authserverConnection.getResponseCode();
			//System.out.println( responseCode );
			
			//Gets the response as a file stream and opens it
			InputStreamReader responseStream = new InputStreamReader( authserverConnection.getInputStream() );
			BufferedReader streamReader = new BufferedReader( responseStream );
			
			//Since output is always one line, simply calling the readline function once is sufficient
			response = streamReader.readLine();
			
		}
		catch ( IOException exception ) {
			//System.out.println( "test" );
			exception.printStackTrace();
			
		}
		
		return response;
		
	}
	
}
