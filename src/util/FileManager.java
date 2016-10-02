package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;

import json.*;

/*
 * A collection of utilities for the management of files pertaining to the functionality of the launcher
 */

public class FileManager {
	
	private static String filePath = SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator();
	private static String[] configFiles;
	
	//Checks for existence and content of files
	public static void verifyFile( String[] files ) throws IOException {
		System.out.println( "[Draconic Launcher][FileManager][Info] Verifying launcher files..." );
		for ( String fileName : files ) {
			if ( isEmpty( fileName ) || !ParseFromJson.verifySyntax( fileName ) ) {
				//Ternary conditional for which debug output to print
				System.out.println( isEmpty( fileName ) ? "[Draconic Launcher][FileManager][Info] " + fileName + ".json is empty" : "[Draconic Launcher][FileManager][Info] " + fileName + ".json has invalid JSON syntax" );
				reset( fileName );
				
			}
			
			String fileContent = download( fileName );
			if ( fileContent != null && fileContent != "DOWNLOAD_FAILURE" ) {
				update( fileName, fileContent );
				
			}
			
		}
		
	}

	//Returns true if the file of interest has no content
	public static boolean isEmpty( String fileName ) throws IOException {
		try {
			BufferedReader reader = new BufferedReader( new FileReader( filePath + fileName + ".json" ) );
			if ( reader.readLine() == null ) {
				reader.close();
				return true;
				
			}
			else {
				reader.close();
				return false;
				
			}
			
		}
		catch ( FileNotFoundException exception ) {
			return true;
			
		}
		
	}
	
	//Used to update a file from an online source, or reset it to being an empty dictionary
	public static void reset( String fileName ) throws IOException {
		FileWriter writer = new FileWriter( filePath + fileName + ".json" );
		
		/* Old bits of reset... dunno if I'm going to use this again
		String fileContent = download( fileName );
		
		if ( fileContent != null && fileContent != "DOWNLOAD_FAILURE" ) {
			update( fileName, fileContent );
			
		}
		else if ( fileContent == "DOWNLOAD_FAILURE" ) {
			//check if the current file has necessary data
			//if it doesn't, set launchValid to false (nothing will set it to true)
			
		}
		else {
			writer.write( "{}" );
			
		}
		*/
		
		writer.write( "{}" );
		writer.close();
		
	}
	
	public static void update( String fileName, String Content ) {
		
		
	}
	
	//Downloads the content for a specific file
	//If this returns null, leave the current file AS IS
	//If this returns anything else, that is the necessary content; Use this string to create a JSON object
	public static String download( String fileName ) {
		String downloadStatus = "DOWNLOAD_PENDING";
		String content = null;
		
		switch ( fileName ) {
		case "ModpackVersionManifest":
			return content;
		case "VersionManifest":
			//don't forget to add this on every file case
			System.out.println( "[Draconic Launcher][FileManager][Info] Downloading the Version Manifest..." );
			
			//Attempt to download the file
			
				//If the download was not able to download, return null (downloadStatus = DOWNLOAD_FAILURE)
				
				//If the download was successful, check the syntax
			
					//If the syntax is invalid, return null (downloadStatus = INVALID_SYNTAX)
			
				//downloadStatus = DOWNLOAD_SUCCESSFUL
			
				//return the download
			
			return content;
			
		default:
			//see? here it is again!
			System.out.println( "[Draconic Launcher][FileManager][Info] No downloaded content for " + fileName + ".json" );
			return null;
			
		}
		
	}
	
	//Returns all the files for verifyFile to check
	//verifyFile has the option for a short list if just one file needs to be checked
	public static String[] getAllFiles() {
		configFiles = new String[] {"Settings"};
		
		return configFiles;
		
	}
	
}
