package json;

/*
 * Mojang auth server response to be RECIEVED by the launcher
 * Called upon with an unsuccessful post request
 */

public class ErrorResponse {

	public String error;
	public String errorMessage;
	public String cause;
	
	/* Don't know if this is going to be used ever
	public ErrorResponse( String error, String cause ) {
		this.error = error;
		this.cause = cause;
		
	}*/
	
}
