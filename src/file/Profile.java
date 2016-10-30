package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.JsonSyntaxException;

import file.Settings.Profiles;
import json.AuthResponse;
import json.ParseFromJson;
import json.ParseToJson;
import json.RefreshPayload;
import util.AuthUtils;
import util.SystemInfo;

//MAJOR TODO: MAKE THIS FILE ENCRYPTED!!
public class Profile extends LauncherFile {
	
	//If this object is null, no profile is selected. This happens when the launcher is opened the first time OR a user specifically clicks "LOG OUT".
	public static transient Profile selectedProfile;
	
	//If either of these two fields are null, then the selected profile is not logged in. This happens with a failed attempt to relog the user, or if the user has save password turned off.
	public String accessToken;
	public String clientToken; //Client token user logged in with
	
	public String id;
	public String username; 
	public String displayname; //Make sure to update this at EVERY login
	
	public Profile( String id ) throws IOException {
		super( SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator() + "profiles", id, true );
		this.id = id;
		
	}

	//Checks for and attempts to load the last used profile.
	public static final void generate() throws IOException {
		int currentProfileIndex = Settings.settings.currentProfileIndex;
		
		if ( currentProfileIndex < 0 ) {
			return;
			
		}
		
		Profile loadProfile = new Profile( Settings.settings.profiles.get( currentProfileIndex ).id );
		
		loadProfile = loadProfile.read(); //This attempts to read the file. This object is ultimately left untouched if it fails.
		
		//try and update the auth info if necessary (checks stayLoggedIn)
		if ( Settings.settings.stayLoggedIn ) {
			loadProfile.updateAuthInfo();
			
		}
		
		Profile.selectedProfile = loadProfile;
		
	}
	
	//Uses the current profile object and attempts to update the access token. Also updates display name in case of changes.
	public boolean updateAuthInfo() throws IOException {
		if ( this.accessToken != null && this.clientToken != null ) {
			AuthUtils.Response response = AuthUtils.post( "refresh", ParseToJson.refreshPayload( new RefreshPayload() ) );
			
			if ( response.getCode() == 200 ) {
				String responseString = response.toString();
				AuthResponse authResponse = ParseFromJson.authResponse( responseString );
				
				this.accessToken = authResponse.accessToken;
				this.displayname = authResponse.selectedProfile.name;
				
				return true;
				
			}
			else {
				this.accessToken = null;
				this.clientToken = null;
				
			}
			
		}
		
		return false;
		
	}
	
	//Reads the profile file and generates the JSON object from it. Needs a separate method as profile files are encrypted
	public Profile read() throws IOException {
		String profileName = this.name;
		String profilePath = this.path;
		String profileFilePath = this.filePath;
		
		FileInputStream profileReader = null;
		InputStreamReader profileStream = null;
		char[] profileString = null;
		
		Profile newProfile = this;
		boolean remove = false;
		
		if ( verifyFile( this ) ) {
		
				try {
				System.out.println( "[Draconic Launcher][Profile][Info] Loading last profile " + this.id + "..." );
				
				profileReader = new FileInputStream( this.filePath );
				profileStream = new InputStreamReader( profileReader, "UTF-8" );
				profileString = new char[profileReader.available()];
				
				int readChar;
				int i = 0;
				while ( ( readChar = profileStream.read() ) != -1 ) {
					profileString[i] = (char) readChar;
					i++;
					
				}
				
				System.out.println(new String(profileString));
				
				try {
					newProfile = ParseFromJson.profile( new String( profileString ) );
					newProfile.name = profileName;
					newProfile.path = profilePath;
					newProfile.filePath = profileFilePath;
					
					System.out.println( "[Draconic Launcher][Profile][Info] Successfully loaded " + newProfile.id /*+ ( newProfile.displayname != null ? " (" + newProfile.displayname + ") " : null )*/ );
					
				}
				catch ( IOException | JsonSyntaxException exception ) {
					exception.printStackTrace();
					remove = true;
					
				}
				
				//System.out.println( newProfile );
				
			}
			//This really shouldn't ever be called, but is here just in case
			catch ( IOException exception ) {
				exception.printStackTrace();
				remove = true;
				
			}
			finally {
				if ( profileReader != null) {
					try {
						profileReader.close();
						
					}
					catch ( Exception exception ) {
						exception.printStackTrace();
						System.exit( 100 );
						
					}
					
				}
				
				if ( profileStream != null) {
					try {
						profileStream.close();
						
					}
					catch ( Exception exception ) {
						exception.printStackTrace();
						System.exit( 100 );
						
					}
					
				}
				
				if ( remove ) {
					System.out.println( "[Draconic Launcher][Profile][Warn] Failed to load profile, deleting..." );
					this.remove( false );
					
				}
				
			}
		
		}
		else {
			System.out.println( "[Draconic Launcher][Profile][Warn] Failed to load profile, deleting..." );
			this.remove( false );
			
		}
		
		return newProfile;
		
	}
	
	//(Encrypts and) Saves the profile file.
	public void write() throws IOException {
		//needs to add the profile to the list in settings
		
	}
	
	public void remove( boolean deleteEntry ) {
		Settings.settings.selectedProfile = null;
		
		File failedProfile = new File( this.filePath );
		//System.out.println(this.filePath); //Troubleshooting
		//System.out.println( failedProfile.getAbsoluteFile().delete() ); //Troubleshooting
		failedProfile.getAbsoluteFile().delete();
		
		if ( deleteEntry ) {
			/*Settings.Profiles[] profiles = new Settings.Profiles[0];
			//for ( int i = Settings.settings.profiles.length - 1; i >= 0 ; i-- ) {
			for ( int i = 0; i > Settings.settings.profiles.length; i++ ) {
				if ( !Settings.settings.profiles[i].id.equals( this.id ) ) {
					 
					
				}
				
			}*/
			
			int i = 0;
			for ( Profiles profile : Settings.settings.profiles ) {
				if ( profile.id.equals( Settings.settings.selectedProfile ) ) {
					Settings.settings.profiles.remove( i );
					
				}
				
				i++;
				
			}
			
		}
		
	}
	
}
