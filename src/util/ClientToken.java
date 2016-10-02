package util;

import java.util.Random;

public class ClientToken {

	private static final String[] chars = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	
	private String[] tokenArray = new String[32];
	public String token;
	
	private Random randy = new Random();
	int index;
	
	//Defines what a client token is
	public ClientToken( String token ) {
		
		this.token = token;

	}
	
	//Generates a new token when called on the object
	public void generateToken() {
		
		for ( int i = 0 ; i <= 32 - 1 ; i++ ) {
			
			index = randy.nextInt(25);
			
			if (index < 10) {
				tokenArray[i] = Integer.toString(index);
				
			}
			else {
				tokenArray[i] = chars[index - 10];
			
			}
		}
		
		this.token = String.join( "", tokenArray);
	
	}
	
}
