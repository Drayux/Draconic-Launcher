package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import json.ParseFromJson;
import util.SystemInfo;

public class LauncherFile {

	public transient String path;
	public transient String name;
	public transient String filePath;
	public transient boolean isProfile;
	
	public LauncherFile( String path, String name, boolean isProfile ) throws IOException {
		this.path = path;
		this.name = name;
		this.isProfile = isProfile;
		this.filePath = isProfile ? path + SystemInfo.getSystemFileSeperator() + name + ".profile" : path + SystemInfo.getSystemFileSeperator() + name + ".json";
		
		//verifyFile( this );
		
	}
	
	//Writes an empty dictionary ( {} ) to the file
	public void reset() throws IOException {
		if ( !this.isProfile ) {
			FileWriter writer = new FileWriter( this.filePath );
			writer.write( "{}" );
			
			writer.close();
			System.out.println( "[Draconic Launcher][LauncherFile][Info] Successfully reset " + this.filePath );
			
		}
		else {
			File profileFile = new File( this.filePath );
			if ( profileFile.exists() ) {
				profileFile.delete();
				
			}
			
			Profile.currentProfile = new Profile( null );
			
		}
		
	}
	
	//Default method to write to the file
	//Most methods will override this (but not actually @override, I have no idea what this is called)
	public void write( String content ) throws IOException {
		verifyFile( this );
		
		FileWriter writer = new FileWriter( this.filePath );
		
		writer.write( content );
		writer.close();
		
		System.out.println( "[Draconic Launcher][LauncherFile][Info] Successfully wrote to " + this.filePath );
		
	}
	
	//Method is used to create path for a file
	//It's a separate method because I need to generate a file object based on the directory tree of the file
	public static boolean createPath( String path ) {
		File launcherFilePath = new File( path );
		return launcherFilePath.mkdirs();
		
	}
	
	public boolean verifyFilePermissions() {
		File launcherFile = new File( this.filePath );
		if ( launcherFile.canWrite() ) {
			return true;
			
		}
		
		return false;
		
	}
	
	//This method pretty much only used when setting the game directory
	public static boolean verifyDirectoryPermissions( String path ) {
		File directoryPath = new File( path );
		File tempFile = new File( path + SystemInfo.getSystemFileSeperator() + "TEMP" );
		
		boolean directoryPermissions = true;
		
		System.out.println( "[Draconic Launcher][LauncherFile][Info] Verifying " + path + "..." );
		
		try {
			directoryPath.mkdirs();
			tempFile.createNewFile();
			
			FileWriter writer = new FileWriter( tempFile );
			
			writer.write( "Drayux loves Zahrya! <3 <3 <3" );
			writer.close();
			
		}
		catch ( IOException exception ) {
			directoryPermissions = false;
			
		}
		if ( tempFile.exists() ) {
			tempFile.delete();
			
		}
		
		return directoryPermissions;
	
	}
	
	public boolean isEmpty() throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader( this.filePath ) );
		if ( reader.readLine() == null ) {
			reader.close();
			return true;
			
		}
		
		reader.close();
		return false;
		
	}
	
	//Checks for existence, completeness, and syntax of file in question
	//Ensures file is valid
	public static boolean verifyFile( LauncherFile file ) throws IOException {
		System.out.println( "[Draconic Launcher][LauncherFile][Info] Verifying " + file.name + ( file.isProfile ? ".profile..." : ".json..." ) );
		
		File launcherFile = new File( file.filePath );
		boolean fileValid = true;
		
		if ( !launcherFile.exists() ) {
			if ( createPath( file.path ) ) {
				System.out.println( "[Draconic Launcher][LauncherFile][Info] Generated path: " + file.path );
				
			}
			
			System.out.println( "[Draconic Launcher][LauncherFile][Info] Generating new file at " + file.filePath );
			
			launcherFile.createNewFile();
			fileValid = false;
			
		}
		
		//Permissions check not fully necessary in this field
		//Still good to check tho
		if ( !file.verifyFilePermissions() ) {
			System.out.println( "[Draconic Launcher][LauncherFile][Info] Launcher does not have write permissions for " + file.name + ( file.isProfile ? ".profile..." : ".json..." ) );
			
			if ( file.path == SystemInfo.getLauncherDir() ) {
				//change this to some sort of warning eventually
				System.out.println( "[Draconic Launcher][LauncherFile][Warn] Exiting launcher with error code: 100 - Invalid permissions in critical directory" );
				System.exit( 100 );
				
			}
			else {
				Settings.settings.gameDirectory = null;
				Settings.settings.write( false );
				
			}
			
		}
		
		boolean isEmpty = file.isEmpty();
		boolean validJsonSyntax = ParseFromJson.verifySyntax( file.filePath );
		if ( isEmpty || ( !validJsonSyntax && !file.isProfile ) ) {
			System.out.println( "[Draconic Launcher][LauncherFile][Info] " + file.filePath + ( isEmpty ? " is empty" : "has invalid JSON syntax" ) );
			file.reset();
			fileValid = false;
			
		}
		
		//return statement simply for launcher console output
		return fileValid;
		
	}
	
	/* Deprecated, will be used per class
	public static String download() {
		//complete this
		return null;
	
	}*/
	
}
