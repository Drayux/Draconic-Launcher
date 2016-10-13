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
	public String lastProfile;
	public int length = 0;
	public boolean saveClientToken = false;
	public boolean stayLoggedIn = false;
	
	public Settings() throws IOException {
		super( SystemInfo.getLauncherDir(), "settings", false );

	}
	
	//Attempts to create settings object from file; Creates a new settings object if not
	//Updates all missing entries and 'saves' the object to the file
	public static void generate() throws IOException {
		verifyFile( new Settings() );
		System.out.println( "[Draconic Launcher][Settings][Info] Loading settings from file..." );
		
		//think of this line as the "settings loader"
		settings = ParseFromJson.settings();
		
		if ( settings.updateDefaultValues( false ) ) {
			settings.write( false );
			
		}
		
	}
	
	//This method checks for all setting values in the settings.json file
	//If a value is null, it's default setting will be applied (used for generating the settings file upon deletion or on first launch)
	public boolean updateDefaultValues( boolean reset ) {
		//This boolean returns true if any values were changed; This is used so that no extra settings writes are called
		boolean settingChanged = false;
		
		//Game Directory / Last Used Profile / Save Generated Client Token / Stay Logged In:
		if ( reset ) {
			this.gameDirectory = null;
			this.lastProfile = null;
			this.saveClientToken = false;
			this.stayLoggedIn = false;
			
			System.out.println( "[Draconic Launcher][Settings][Info] Reset gameDirectory to default value" );
			System.out.println( "[Draconic Launcher][Settings][Info] Reset lastProfile to default value" );
			System.out.println( "[Draconic Launcher][Settings][Info] saveClientToken to default value" );
			System.out.println( "[Draconic Launcher][Settings][Info] Reset stayLoggedIn to default value" );
			
			settingChanged = true;
			
		}
		//Client Token:
		//if stayLoggedIn != true, reset this to null upon close of main window and invalidate authtoken
		if ( clientToken == null ) {
			ClientToken token = new ClientToken();
			token.generateToken();
			this.clientToken = token.token;
			
		}
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
	public void write( boolean verify ) throws IOException {
		// This check is not actually necessary as the file will be overwritten.
		//I had it here thinking it was a good idea, but the only time the file integrity really matters is on its load
		if ( verify ) {
			verifyFile( this );
			
		}
		
		FileWriter settingsWriter = new FileWriter( this.filePath );
		settingsWriter.write( ParseToJson.settings( this ) );
		
		settingsWriter.close();
		System.out.println( "[Draconic Launcher][Settings][Info] Successfully wrote to settings file" );
		
	}
	
}
