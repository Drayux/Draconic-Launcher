package main;

import java.io.IOException;

import file.Settings;
import gui.LauncherGUI;
import util.SystemInfo;

/*
 * The main class for the launcher
 * Ensures that the launcher has all the necessary requirements to function correctly and initializes the GUI
 */

public class Main {
	
	public static void main( String...args ) throws IOException {

		//The first thing the program does is that it gets information about the user's system so the appropriate configuration to launch the game can be used
		//It is then shown here
		System.out.println( "[Draconic Launcher][SystemInfo][Info] Current OS: " + SystemInfo.getSystemOS() );
		System.out.println( "[Draconic Launcher][SystemInfo][Info] JVM Architecture: " + SystemInfo.getJVMArch() + "-bit" );
		System.out.println( "[Draconic Launcher][SystemInfo][Info] Java Version: " + SystemInfo.getJavaVersion() );
		System.out.println( "[Draconic Launcher][SystemInfo][Info] Total Memory: " + ( SystemInfo.getSystemMemory() / 1024 ) + " GiB (" + ( SystemInfo.getSystemMemory() * 1048567 ) + " bytes)" );
		System.out.println( "[Draconic Launcher][SystemInfo][Info] Launcher Directory: " + SystemInfo.getLauncherDir() );
		//also needs to check install directory and prompt user if nonexistent
		
		//Generates settings object from file
		Settings.generate();
		
		//Creates launcher GUI based off of the setting in gameDirectory
		if ( Settings.settings.gameDirectory == null ) {
			LauncherGUI.createGameDirectoryPrompt();
			
		}
		//add something to check the permissions of the game directory at launch
		else {
			LauncherGUI.createMainGUI( "Draconic Modpack Launcher" );
			
		}
	
	}

}
