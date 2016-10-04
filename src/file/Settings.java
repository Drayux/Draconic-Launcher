package file;

import java.io.FileWriter;
import java.io.IOException;

import json.ParseFromJson;
import json.ParseToJson;
import util.SystemInfo;

//Maybe create some sort of interface in setting?
//import json.Setting;

public class Settings extends LauncherFile {

	public static transient Settings settings;
	
	//All settings to be contained within the settings file shall be defined here (using non-primitive types)
	//Anything NOT here WILL BE LOST upon update of the file
	public String gameDirectory;
	public Boolean stayLoggedIn;
	
	public Settings() throws IOException {
		super( SystemInfo.getLauncherDir(), "settings" );

	}
	
	//Attempts to create settings object from file; Creates a new settings object if not
	//Updates all missing entries and 'saves' the object to the file
	public static void generate() throws IOException {
		/*try {
			settings = ParseFromJson.settings( "settings.json" );
			System.out.println( "[Draconic Launcher][Settings][Info] Loaded settings from file..." );
			
		}
		catch ( IOException | JsonSyntaxException exception) {
			System.out.println( "[Draconic Launcher][Settings][Info] Generating new settings file..." );
			settings = new Settings();
			
		}*/
		
		verifyFile( new Settings() );
		System.out.println( "[Draconic Launcher][Settings][Info] Loading settings from file..." );
		
		//think of this line as the "settings loader"
		settings = ParseFromJson.settings();
		
		settings.updateDefaultValues( false );
		settings.write( false );
		
	}
	
	//This method checks for all setting values in the settings.json file
	//If a value is null, it's default setting will be applied (used for generating the settings file upon deletion or on first launch)
	public void updateDefaultValues( boolean reset ) {
		//Game Directory:
		if ( reset ) {
			this.gameDirectory = null;
			System.out.println( "[Draconic Launcher][Settings][Info] Reset gameDirectory to default value" );
			
		}
		//Stay Logged In (upon closing launcher):
		if ( stayLoggedIn == null || reset ) {
			this.stayLoggedIn = false;
			System.out.println( "[Draconic Launcher][Settings][Info] Reset stayLoggedIn to default value" );
			
		}
		
	}
	
	// Writes data of settings object (followed by this) to the settings file
	public void write( boolean verify ) throws IOException {
		if ( verify ) {
			verifyFile( this );
			
		}
		
		FileWriter settingsWriter = new FileWriter( this.filePath );
		settingsWriter.write( ParseToJson.settings( this ) );
		
		settingsWriter.close();
		System.out.println( "[Draconic Launcher][Settings][Info] Successfully updated settings file" );
		
	}
	
}
