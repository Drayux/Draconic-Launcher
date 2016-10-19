package file;

import java.io.FileWriter;
import java.io.IOException;

import json.ParseFromJson;
import json.ParseToJson;
import util.ClientToken;
import util.SystemInfo;

//Maybe create some sort of interface in setting?
//import json.Setting;

public class Settings extends LauncherFile {

	public static transient Settings settings;
	
	//All settings to be contained within the settings file shall be defined here (using non-primitive types)
	//Anything NOT here WILL BE LOST upon update of the file
	public String gameDirectory;
	public String clientToken;
	//public String currentProfile;
	public int currentProfileIndex = 0;
	public Profiles[] profiles = new Profiles[0];
	//public boolean saveClientToken = false;
	public boolean stayLoggedIn = false;
	public float scalingFactor = 1;
	
	public Settings() throws IOException {
		super( SystemInfo.getLauncherDir(), "settings", false );

	}
	
	public static class Profiles {
		
		public String id;
		public String username;
		
		public Profiles( String id, String username ) {
			this.id = id;
			this.username = username;
			
		}
		
	}
	
	//Attempts to create settings object from file; Creates a new settings object if not
	//Updates all missing entries and 'saves' the object to the file
	public static final void generate() throws IOException {
		verifyFile( new Settings() );
		System.out.println( "[Draconic Launcher][Settings][Info] Loading settings from file..." );
		
		//think of this line as the "settings loader"
		settings = ParseFromJson.settings();
		
		/*if ( settings.updateDefaultValues( false ) ) {
			settings.write( false );
			
		}*/
		
		//Settings will instead be saved on the creation of the main launcher window
		settings.updateDefaultValues( false );
		
	}
	
	//This method checks for all setting values in the settings.json file
	//If a value is null, it's default setting will be applied (used for generating the settings file upon deletion or on first launch)
	public boolean updateDefaultValues( boolean reset ) {
		//This boolean returns true if any values were changed; This is used so that no extra settings writes are called
		boolean settingChanged = false;
		
		//Game Directory / Last Used Profile / Save Generated Client Token / Stay Logged In:
		if ( reset ) {
			this.gameDirectory = null;
			//this.currentProfile = null;
			this.currentProfileIndex = 0;
			this.profiles = new Profiles[0];
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
		//Client Token:
		//if stayLoggedIn != true, reset this to null upon close of main window and invalidate authtoken
		if ( clientToken == null ) {
			ClientToken token = new ClientToken();
			token.generateToken();
			this.clientToken = token.token;
			
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
