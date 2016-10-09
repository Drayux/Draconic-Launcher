package file;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import json.ParseToJson;
import util.SystemInfo;

public class Profile {

	public static transient Profile currentProfile;
	
	public transient String path;
	public transient String filePath;
	private static transient Cipher cipher;
	private static transient Key key;
	
	static {
		try {
			cipher = Cipher.getInstance( "AES" );
			key =  KeyGenerator.getInstance( "AES" ).generateKey();
			
		} 
		catch ( NoSuchAlgorithmException | NoSuchPaddingException exception ) {
			exception.printStackTrace();
			
		}
		
	}
	
	public String accessToken;
	public String clientToken; //Client token user logged in with
	public String id;
	public String username;
	
	public Profile( String id ) throws IOException {
		this.path = SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator() + "profiles";
		this.id = id;
		this.filePath = SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator() + "profiles" + SystemInfo.getSystemFileSeperator() + id + ".profile";
		
	}

	public byte[] write() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

		cipher.init( Cipher.ENCRYPT_MODE, key );
		
		byte[] profileBytes = ParseToJson.profile( this ).getBytes();
		byte[] encryptedProfileBytes = cipher.doFinal( profileBytes );
		
		System.out.println( new String( profileBytes ) );
		System.out.println( new String( encryptedProfileBytes ) );
		
		//use FileOutputStream within a BufferedOutputStream to write the file
		
		return encryptedProfileBytes; //change the return to void when finished testing
		
	}
	
	public String read( byte[] testbytes ) throws FileNotFoundException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		//BufferedInputStream reader = new BufferedInputStream( new FileInputStream( this.filePath ) );
		
		cipher.init( Cipher.DECRYPT_MODE, key);
		
		byte[] profileBytes = cipher.doFinal( testbytes );
		
		System.out.println( new String( profileBytes ) );
		
		return null;
		
	}
	
}
