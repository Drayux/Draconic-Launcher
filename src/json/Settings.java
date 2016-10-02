package json;

import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.JsonSyntaxException;

import util.SystemInfo;

//Maybe create some sort of interface in setting?
//import json.Setting;

public class Settings {
	
	public static transient Settings settings;
	private static transient String filePath = SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator();
	
	//All settings to be contained within the settings file shall be defined here (using non-primitive types)
	//Anything NOT here WILL BE LOST upon update of the file
	public String gameDirectory;
	public Boolean stayLoggedIn;
	
	//Attempts to create settings object from file; Creates a new settings object if not
	//Updates all missing entries and 'saves' the object to the file
	public static void generate() throws IOException {
		try {
			System.out.println( "[Draconic Launcher][Settings][Info] Loading settings from file..." );
			settings = ParseFromJson.settings( "settings.json" );
			
		}
		catch ( IOException | JsonSyntaxException exception) {
			System.out.println( "[Draconic Launcher][Settings][Info] Generating new settings file..." );
			settings = new Settings();
			
		}
		
		settings.updateDefaultValues( false );
		settings.write();
		
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
	
	//Writes data of settings object (followed by this) to the settings file
	public void write() throws IOException {
		FileWriter settingsWriter = new FileWriter( filePath + "Settings.json" );
		settingsWriter.write( ParseToJson.Settings( this ) );
		
		settingsWriter.close();
		System.out.println( "[Draconic Launcher][Settings][Info] Successfully updated settings file" );
		
	}
	
}
