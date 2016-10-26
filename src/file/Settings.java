package file;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import json.ParseFromJson;
import json.ParseToJson;
import util.ClientToken;
import util.SystemInfo;

//Maybe create some sort of interface in setting?
//import json.Setting;

public class Settings extends LauncherFile {

	public static transient Settings settings;
	
	public transient int currentProfileIndex;
	public transient String clientToken;
	
	//All settings to be contained within the settings file shall be defined here (using non-primitive types)
	//Anything NOT here WILL BE LOST upon update of the file
	public String gameDirectory;
	public String selectedProfile; //The ID of the last profile used successfully
	//public Profiles[] profiles = new Profiles[0];
	public List<Profiles> profiles;
	//public boolean saveClientToken = false;
	public boolean stayLoggedIn = false; //NOTE: If false, the password will not be saved
	public float scalingFactor = 1;
	
	public Settings() throws IOException {
		super( SystemInfo.getLauncherDir(), "settings", false );

	}
	
	public static class Profiles {
		
		public String id;
		public String displayname;
		
		//This Profiles object is different from the Profile Profile object as these are only the values written to the settings file
		public Profiles( String id, String username ) {
			this.id = id;
			this.displayname = username;
			
		}
		
	}
	
	//Attempts to create settings object from file; Creates a new settings object if not
	//Updates all missing entries and 'saves' the object to the file
	//NOTE: Settings generated here are saved upon the creation of the main window
	public static final void generate() throws IOException {
		System.out.println( "[Draconic Launcher][Settings][Info] Loading settings from file..." );
		
		verifyFile( new Settings() );
		
		settings = ParseFromJson.settings(); //think of this line as the "settings loader"
		settings.updateDefaultValues( false );
		
		ClientToken token = new ClientToken();
		token.generateToken();
		settings.clientToken = token.token;
		
		settings.updateProfileIndex();
		
	}
	
	//This method checks for all setting values in the settings.json file
	//If a value is null, it's default setting will be applied (used for generating the settings file upon deletion or on first launch)
	public boolean updateDefaultValues( boolean reset ) {
		//This boolean returns true if any values were changed; This is used so that no extra settings writes are called
		boolean settingChanged = false;
		
		//Game Directory / Last Used Profile / Save Generated Client Token / Stay Logged In:
		if ( reset ) {
			this.gameDirectory = null;
			this.selectedProfile = null;
			//this.currentProfileIndex = 0;
			this.profiles.clear();
			//this.saveClientToken = false;
			this.stayLoggedIn = false;
			this.scalingFactor = 1;
			
			System.out.println( "[Draconic Launcher][Settings][Info] Reset gameDirectory to default value" );
			System.out.println( "[Draconic Launcher][Settings][Info] Reset lastProfile to default value" );
			//System.out.println( "[Draconic Launcher][Settings][Info] Reset saveClientToken to default value" );
			System.out.println( "[Draconic Launcher][Settings][Info] Reset stayLoggedIn to default value" );
			System.out.println( "[Draconic Launcher][Settings][Info] Reset scalingFactor to 1" );
			System.out.println( "[Draconic Launcher][Settings][Info] Deleted all profile entries" );
			
			settingChanged = true;
			
		}
		
		//Profiles List:
		//if (  )
		/* Unnecessary code
		//Save Client Token:
		if ( saveClientToken == null || reset ) {
			this.saveClientToken = false;
			System.out.println( "[Draconic Launcher][Settings][Info] saveClientToken to default value" );
			
		}
		//Stay Logged In (upon closing launcher):
		if ( stayLoggedIn == null || reset ) {
			this.stayLoggedIn = false;
			System.out.println( "[Draconic Launcher][Settings][Info] Reset stayLoggedIn to default value" );
			
		}*/
		
		return settingChanged;
		
	}
	
	//This needs to be called whenever the profile is changed
	public void updateProfileIndex() {
		if ( this.selectedProfile != null ) {
			/*for ( int i = 0; i < this.profiles.length; i++ ) {
				if ( this.profiles[i].id.equals( this.selectedProfile ) ) {
					System.out.println( "[Draconic Launcher][Settings][Info] Current profile at index: " + i );
					
					this.currentProfileIndex = i;
					return;
					
				}
				
			}*/
			int i = 0;
			for ( Profiles profile : profiles ) {
				if ( profile.id.equals( this.selectedProfile ) ) {
					System.out.println( "[Draconic Launcher][Settings][Info] Current profile at index: " + i );
					
					this.currentProfileIndex = i;
					return;
					
				}
				
				i++;
				
			}
		
		}
		
		this.currentProfileIndex = -1;
		
	}
	
	// Writes data of settings object (followed by this) to the settings file
	public void write( boolean verify ) {
		FileWriter settingsWriter = null;
		
		try {
			if ( verify ) {
				verifyFile( this );
				
			}
			
			settingsWriter = new FileWriter( this.filePath );
			settingsWriter.write( ParseToJson.settings( this ) );
			
			System.out.println( "[Draconic Launcher][Settings][Info] Successfully wrote to settings file" );
			
		}
		catch ( IOException exception ) {
			System.out.println( "[Draconic Launcher][Settings][Warn] Failed to write to settings file" );
			
		}
		finally {
			try {
				if ( settingsWriter != null ) {
					settingsWriter.close();
					
				}
				
			}
			catch ( Exception exception ) {
				exception.printStackTrace();
				System.exit( 100 );
				
			}
			
		}
		
	}
	
	public static float getScalingFactor() {
		try {
			return settings.scalingFactor;
			
		}
		catch ( NullPointerException exception ) {
			return 1;
			
		}
		
	}
	
}
