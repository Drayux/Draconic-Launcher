package main;

import java.io.IOException;

import file.LauncherFile;
import file.Profile;
import file.Settings;
import gui.LaunchFrame;
import gui.PromptFrame;
import util.SystemInfo;

/*
 * The main class for the launcher
 * Ensures that the launcher has all the necessary requirements to function correctly and initializes the GUI
 */

public class Main {
	
	public final static Thread promptFrame = new Thread( new PromptFrame() );
	public final static Thread launchFrame = new Thread( new LaunchFrame() );
	
	public static void main( String...args ) throws IOException {
		//MAJOR TODO: Finish threads!
		
		//The first thing the program does is that it gets information about the user's system so the appropriate configuration to launch the game can be used
		//It is then shown here
		System.out.println( "[Draconic Launcher][SystemInfo][Info] Current OS: " + SystemInfo.getSystemOS() );
		System.out.println( "[Draconic Launcher][SystemInfo][Info] JVM Architecture: " + SystemInfo.getJVMArch() + "-bit" );
		System.out.println( "[Draconic Launcher][SystemInfo][Info] Java Version: " + SystemInfo.getJavaVersion() );
		System.out.println( "[Draconic Launcher][SystemInfo][Info] Total Memory: " + ( SystemInfo.getSystemMemory() / 1024 ) + " GiB (" + ( SystemInfo.getSystemMemory() * 1048567 ) + " bytes)" );
		System.out.println( "[Draconic Launcher][SystemInfo][Info] Launcher Directory: " + SystemInfo.getLauncherDir() );

		//System.out.println( ParseToJson.authPayload( new AuthPayload( "testusr", new CharSequence[]{"h"} ) ) );
		
		//Generates settings object from file
		Settings.generate();
		//Profile initialization
		//Profile.generate();
		
		//Creates launcher GUI based off of the setting in gameDirectory
		if ( Settings.settings.gameDirectory == null || !LauncherFile.verifyDirectoryPermissions( Settings.settings.gameDirectory ) ) {
			promptFrame.start(); //Prompt frame will start launchFrame upon closing instead
			
		}
		else {
			launchFrame.start();
			
		}
	
	}

}
