package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
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
import com.google.gson.JsonSyntaxException;

import json.AuthResponse;
import json.ParseFromJson;
import json.ParseToJson;
import json.RefreshPayload;
import util.AuthUtils;
import util.SystemInfo;

public class Profile extends LauncherFile {

	public static transient Profile currentProfile;
	
	private static transient Cipher cipher;
	private static transient Key key;
	
	static {
		try {
			cipher = Cipher.getInstance( "AES" );
			key = KeyGenerator.getInstance( "AES" ).generateKey();
			
		} 
		catch ( NoSuchAlgorithmException | NoSuchPaddingException exception ) {
			exception.printStackTrace();
			
		}
		
	}
	
	public String accessToken;
	public String clientToken; //Client token user logged in with
	public String id;
	public String username; //Make sure to update this at EVERY login
	
	public Profile( String id ) throws IOException {
		super( SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator() + "profiles", id, true );
		
	}

	public static void generate() throws IOException {
		currentProfile = new Profile( Settings.settings.lastProfile );
		String profileString = Profile.currentProfile.read();
		boolean profileComplete = false;
		
		if ( profileString != null ) {
			System.out.println( "[Draconic Launcher][Settings][Info] Loading profile " + currentProfile.username + " from file..." );
			
			try {
				currentProfile = ParseFromJson.profile( profileString );
				profileComplete = true;
				
			}
			catch ( JsonSyntaxException exception ) {
				currentProfile.reset();
				
			}
			
		}
		
		if ( Settings.settings.stayLoggedIn && profileComplete ) {
			AuthUtils.post( "refresh", ParseToJson.refreshPayload( new RefreshPayload() ) );
			
		}
		
	}
	
	//Returns data saved in the profile file of the current profile
	//Make sure not to call this if no profile is set (id = null)
	public String read() throws IOException {
		if ( this.id == null ) {
			return null;
			
		}
		
		BufferedInputStream stream = null;
		byte[] encryptedProfileBytes = null;
		byte[] profileBytes = null;
		
		try {
			stream = new BufferedInputStream( new FileInputStream( this.filePath ) );
			encryptedProfileBytes = new byte[stream.available()];
			
			for ( int i = 0; i < encryptedProfileBytes.length; i++ ) {
				encryptedProfileBytes[i] = (byte) stream.read();
				
			}
			
		}
		catch ( IOException exception ) {
			System.out.println( "[Draconic Launcher][Profile][Warn] Failed to read profile file" );
			exception.printStackTrace();
			
		}
		finally {
			if ( stream != null ) {
				//using this format because all these error calls drive me insane... if it just doesn't work, then let it not work! xD
				//try { stream.close(); } catch ( Exception exception ) { exception.printStackTrace(); System.exit( -1 ); }
				stream.close();
				
			}
			else {
				System.out.println( "[Draconic Launcher][Profile][Info] Deleting profile: " + this.id + "..." );
				this.reset();
				return null;
				
			}
			
		}
		
		try {
			cipher.init( Cipher.DECRYPT_MODE, key );
			
			profileBytes = cipher.doFinal( encryptedProfileBytes );
			//System.out.println( new String( profileBytes ) );
			
			return new String( profileBytes );
			
		}
		catch ( InvalidKeyException | BadPaddingException | IllegalBlockSizeException exception ) {
			System.out.println( "[Draconic Launcher][Profile][Warn] Failed to decrypt " + this.id + ".profile" );
			exception.printStackTrace();
			return null;
			
		}
		
	}
	
	//Writes data of currentProfile object to file
	//Make sure not to call this if no profile is set (id = null)
	public void write() throws IOException {
		if ( this.id == null ) {
			return;
			
		}
		
		BufferedOutputStream stream = null;
		byte[] encryptedProfileBytes = null;
		byte[] profileBytes = null;
		
		try { 
			cipher.init( Cipher.ENCRYPT_MODE, key );
			
			profileBytes = ParseToJson.profile( this ).getBytes();
			encryptedProfileBytes = cipher.doFinal( profileBytes );
			
		} 
		catch ( InvalidKeyException | IllegalBlockSizeException | BadPaddingException exception ) {
			System.out.println( "[Draconic Launcher][Profile][Warn] Failed to encrypt " + this.id + ".profile" );
			exception.printStackTrace();
			return;
			
		}
		
		//System.out.println( new String( profileBytes ) );
		//System.out.println( new String( encryptedProfileBytes ) );
		
		try {
			//use FileOutputStream within a BufferedOutputStream to write the file
			stream = new BufferedOutputStream( new FileOutputStream( this.filePath ) );
			
			System.out.println( "[Draconic Launcher][Profile][Info] Saving " + this.id + ".profile..." );
			stream.write( encryptedProfileBytes );
			stream.close();
		
		}
		catch ( IOException exception ) {
			System.out.println( "[Draconic Launcher][Profile][Warn] Failed to write profile file" );
			exception.printStackTrace();
			
		}
		finally {
			if ( stream != null ) {
				//try { stream.close(); } catch ( Exception exception ) { exception.printStackTrace(); System.exit( -1 ); }
				stream.close();
				System.out.println( "[Draconic Launcher][Profile][Info] Successfully wrote to " + this.id + ".profile" );
				
			}
			else {
				System.out.println( "[Draconic Launcher][Profile][Info] Deleting profile: " + this.id + "..." );
				this.reset();
				
			}
			
		}
		
	}
	
	//Equivalent of settings.generate()
	//checks for and verifies profile file; loads the current profile to the currentProfile profile; saves the loaded profile to the settings
	public static void create( AuthResponse response ) throws IOException {
		verifyFile( new Profile( response.selectedProfile.id ) );
		
		//appends the values from the login to the 
		currentProfile.update( response );
		currentProfile.write();
		
	}
	
	public void update( AuthResponse response ) {
		currentProfile.id = response.selectedProfile.id;
		currentProfile.username = response.selectedProfile.name;
		currentProfile.accessToken = response.accessToken;
		currentProfile.clientToken = response.clientToken;
		
	}
	
	/* Don't think I'm going to use this
	public void reset() {
		currentProfile.id = null;
		currentProfile.username = null;
		currentProfile.accessToken = null;
		currentProfile.clientToken = null;
		
	} */
	
}
