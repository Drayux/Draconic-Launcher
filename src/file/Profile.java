package file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.JsonSyntaxException;

import json.AuthResponse;
import json.ParseFromJson;
import json.ParseToJson;
import json.RefreshPayload;
import util.AuthUtils;
import util.SystemInfo;

public class Profile extends LauncherFile {

	public static transient Profile currentProfile;
	
	public String accessToken;
	public String clientToken; //Client token user logged in with
	public String id;
	public String username; 
	public String displayname; //Make sure to update this at EVERY login
	//Add setting entries as needed
	
	private static transient Cipher cipher;
	private static transient Key key;
	
	static {
		try {
			cipher = Cipher.getInstance( "AES" );
			//TODO: Change this key
			key = new SecretKeySpec( new byte[]{'s','u','p','e','r','s','e','c','r','e','t','0','0','0','0','0'}, "AES" );
			
		} 
		catch ( NoSuchAlgorithmException | NoSuchPaddingException exception ) {
			exception.printStackTrace();
			
		}
		
	}
	
	public Profile( String id ) throws IOException {
		super( SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator() + "profiles", id, true );
		
	}
	
	//Creates a new profile
	public void create( String id ) {
		//^^Might need more params
		
		//Create a profile object with the set id
		//Also create a settings profile object using the same data
		
		//create a file of the profile if nonexistant
		//write to that file the new, full profile object
		
		//if all goes well, set that profile as currentProfile
		//add the profile to the settings (overwrite if id already existent)
		//set the currentProfile and it's id
		//save the settings
		
	}
	
	//Removes a profile from Settings.settings.profiles
	public void remove( String id ) {
		
		
	}
	
	//Loads a known profile
	public static void generate( int index ) {
		//get the id of the profile at that index
		//create a local profile object with that id
		
		//read the local profile
		//update it with the result of the read command
		
		//if all was successful, set currentProfile as the new profile we just created
		//set the current profile and it's corresponding index in settings
		//save the settings
		
		//if not, remove the entry from the list and set the index to 0
		//do nothing else as the current profile will remain as the last one
		
	}
	
	public String read() {
		return null;
		
	}
	
	public void write() {
		
		
	}
	
	/*public Profile( String id ) throws IOException {
		super( SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator() + "profiles", id, true );
		this.id = id;
		
		//Makes sure new profile (set from something other than the generate method) will be saved to the settings
		if ( Settings.settings.currentProfile != id ) {
			Settings.settings.currentProfile = id;
			System.out.println( "[Draconic Launcher][Profile][Info] Set current profile to: " + id );
			
			Settings.settings.write( false );
			
		}
		
	}

	public static final void generate() throws IOException {
		currentProfile = new Profile( Settings.settings.currentProfile );
		
		if ( currentProfile.id != null ) {
			verifyFile( currentProfile );
			
		}
		else {
			return;
			
		}
		
		//Because an empty file encoded has values, we need a secondary check
		String profileData = currentProfile.read();*/
		
		
		
		//add check for existance/emptiness
		
		/*String profileString = null;
		boolean profileComplete = false;
		
		if ( currentProfile.id != null && !currentProfile.isEmpty() ) {
			System.out.println( "[Draconic Launcher][Profile][Info] Loading profile: " + currentProfile.id + "..." );
			profileString = Profile.currentProfile.read();
			
			System.out.println(profileString); //TEST LINE
			
		}
		else {
			currentProfile.reset();
			
		}
			
		if (  ) {
			try {
				currentProfile = ParseFromJson.profile( profileString );
				System.out.println( "[Draconic Launcher][Profile][Info] Successfully loaded profile for " + currentProfile.username );
				profileComplete = true;
				
			}
			catch ( JsonSyntaxException | NullPointerException exception ) {
				System.out.println( "[Draconic Launcher][Profile][Warn] Failed to parse profile file, exception: " + exception );
				//currentProfile.reset();
				
			}
			
		}*/
		
		/*if ( Settings.settings.stayLoggedIn && profileComplete ) {
			AuthUtils.post( "refresh", ParseToJson.refreshPayload( new RefreshPayload() ) );
			
		}
		
	}*/
	
	//Returns data saved in the profile file of the current profile
	//Make sure not to call this if no profile is set (id = null)
	/*public String read() throws IOException {	
		if ( this.id == null ) {
			System.out.println( "[Draconic Launcher][Profile][Warn] No data for the current profile");
			return null;
			
		}
		
		FileInputStream stream = null; 			//Stream reader
		byte[] encryptedProfileBytes = null; 	//Byte stream from saved profile file
		byte[] profileData = null;				//Decrypted profile file
		
		try {
			stream = new FileInputStream( this.filePath );
			encryptedProfileBytes = new byte[stream.available()];
			
			for ( int i = 0; i < encryptedProfileBytes.length; i++ ) {
				encryptedProfileBytes[i] = (byte) stream.read();
				
			}
			
			//System.out.println( new String( encryptedProfileBytes ) );
			
		}
		catch ( IOException exception ) {
			System.out.println( "[Draconic Launcher][Profile][Warn] Failed to read profile file" );
			//exception.printStackTrace();
			return null;
			
		}
		finally {
			if ( stream != null ) {
				//using this format because all these error calls drive me insane... if it just doesn't work, then let it not work! xD
				//try { stream.close(); } catch ( Exception exception ) { exception.printStackTrace(); System.exit( -1 ); }
				stream.close();
				
			}
			/*else {
				System.out.println( "[Draconic Launcher][Profile][Info] Deleting profile: " + this.id + "..." );
				this.reset();
				return null;
				
			}
			
		}*/
		
		/*try {
			cipher.init( Cipher.DECRYPT_MODE, key );
			
			profileData = cipher.update( encryptedProfileBytes );
			
			//System.out.println( new String( profileBytes ) );
			
			return new String( profileData );
			
		}
		catch ( InvalidKeyException exception ) {
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
		
		FileOutputStream stream = null;
		byte[] encryptedProfileBytes = null;
		byte[] profileBytes = null;
		
		try { 
			cipher.init( Cipher.ENCRYPT_MODE, key );
			
			profileBytes = ParseToJson.profile( this ).getBytes( Charset.forName("UTF-8") );
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
			stream = new FileOutputStream( this.filePath );
			
			System.out.println( "[Draconic Launcher][Profile][Info] Saving " + this.id + ".profile..." );
			stream.write( encryptedProfileBytes );
			
			//System.out.println( new String( encryptedProfileBytes ) );
		
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
		this.id = response.selectedProfile.id;
		this.username = response.selectedProfile.name;
		this.accessToken = response.accessToken;
		this.clientToken = response.clientToken;
		
		this.name = id;
		this.filePath = SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator() + "profiles" + SystemInfo.getSystemFileSeperator() + id + ".profile";
		
		//currentProfile = this;
		
	}
	
	/* Don't think I'm going to use this
	public void reset() {
		currentProfile.id = null;
		currentProfile.username = null;
		currentProfile.accessToken = null;
		currentProfile.clientToken = null;
		
	} */
	
}
