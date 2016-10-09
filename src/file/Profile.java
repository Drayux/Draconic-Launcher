package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	
	private String accessToken;
	public String clientToken; //Client token user logged in with
	public String id;
	public String username; //Make sure to update this at EVERY login
	
	public Profile( String id ) throws IOException {
		this.path = SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator() + "profiles";
		this.id = id;
		this.filePath = SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator() + "profiles" + SystemInfo.getSystemFileSeperator() + id + ".profile";
		
	}

	public String read() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		cipher.init( Cipher.DECRYPT_MODE, key);
		
		BufferedInputStream stream = new BufferedInputStream( new FileInputStream( this.filePath ) );
		
		//byte[] encryptedBytes = new byte[10];
		byte nextByte;
		while ( ( nextByte = (byte) stream.read() ) != -1 ) {
			System.out.println( nextByte );
			
		}
		
		//byte[] profileBytes = cipher.doFinal( testbytes );
		
		//System.out.println( new String( profileBytes ) );
		
		return null;
		
	}
	
	public void write() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {

		cipher.init( Cipher.ENCRYPT_MODE, key );
		
		byte[] profileBytes = ParseToJson.profile( this ).getBytes();
		byte[] encryptedProfileBytes = cipher.doFinal( profileBytes );
		
		System.out.println( new String( profileBytes ) );
		System.out.println( new String( encryptedProfileBytes ) );
		
		//use FileOutputStream within a BufferedOutputStream to write the file
		BufferedOutputStream stream = new BufferedOutputStream( new FileOutputStream( this.filePath ) );
		
		stream.write( encryptedProfileBytes );
		stream.close();
		
	}
	
	//equivelant of settings.generate()
	//checks for and verifys profile file; loads the current profile to the currentProfile profile; saves the loaded profile to the settings
	public void create( String id ) {
		//pseudo-verify file on profile, this is the check if it exists or not
		//more stuff i haven't thought through yet
		
	}
	
	public void update() {
		//refresh login
		//write any changes to the file
		
	}
	
}
