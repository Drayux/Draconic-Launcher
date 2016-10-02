package main;

import java.io.IOException;

import gui.LauncherGUI;
import json.Settings;
import util.FileManager;
import util.SystemInfo;

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
		
		//Verifies that all the launcher files have the necessary information
		FileManager.verifyFile( FileManager.getAllFiles() );
		
		//Generates settings object from file
		Settings.generate();
		
		//Creates launcher GUI based off of the setting in gameDirectory
		if ( Settings.settings.gameDirectory == null ) {
			LauncherGUI.createGameDirectoryPrompt();
			
		}
		else {
			LauncherGUI.createMainGUI( "Draconic Modpack Launcher" );
			
		}
		
	}

}
