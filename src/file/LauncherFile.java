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
	
	public LauncherFile( String path, String name ) throws IOException {
		this.path = path;
		this.name = name;
		this.filePath = path + SystemInfo.getSystemFileSeperator() + name + ".json";
		
		//verifyFile( this );
		
	}
	
	//Writes an empty dictionary ( {} ) to the file
	public void reset() throws IOException {
		FileWriter writer = new FileWriter( this.filePath );
		writer.write( "{}" );
		
		writer.close();
		System.out.println( "[Draconic Launcher][LauncherFile][Info] Successfully reset " + this.filePath );
		
	}
	
	public void write( String content ) throws IOException {
		verifyFile( this );
		
		FileWriter writer = new FileWriter( this.filePath );
		writer.write( content );
		
		writer.close();
		System.out.println( "[Draconic Launcher][LauncherFile][Info] Successfully wrote to " + this.filePath );
		
	}
	
	public boolean isEmpty() throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader( this.filePath ) );
		if ( reader.readLine() == null ) {
			reader.close();
			return true;
			
		}
		else {
			reader.close();
			return false;
			
		}
		
	}
	
	//Checks for existence, completeness, and syntax of file in question
	//Ensures file is valid
	public static boolean verifyFile( LauncherFile file ) throws IOException {
		System.out.println( "[Draconic Launcher][LauncherFile][Info] Verifying " + file.name + ".json..." );
		
		File launcherFile = new File( file.filePath );
		boolean fileValid = true;
		
		if ( !launcherFile.exists() ) {
			System.out.println( "[Draconic Launcher][LauncherFile][Info] Generating new file at " + file.filePath );
			launcherFile.createNewFile();
			fileValid = false;
			
		}
		
		boolean isEmpty = file.isEmpty();
		if ( isEmpty || !ParseFromJson.verifySyntax( file.filePath ) ) {
			System.out.println( "[Draconic Launcher][LauncherFile][Info] " + file.filePath + ( isEmpty ? " is empty" : "has invalid JSON syntax" ) );
			file.reset();
			fileValid = false;
			
		}
		
		//return statement simply for launcher console output
		return fileValid;
		
	}
	
	public static boolean checkDirectoryPermissions() {
		return false;
		
	}
	
	/* Deprecated, will be used per class
	public static String download() {
		//complete this
		return null;
	
	}*/
	
}
