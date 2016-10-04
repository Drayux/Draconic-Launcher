package json;

import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import file.Settings;
//import json._testjson;
import json.AssetIndex;
import json.LocalModpacks;
import json.ModpackIndex;
import json.VersionLaunchJson;
import json.VersionManifest;
import util.SystemInfo;

public class ParseFromJson {
	
	private static String filePath = SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator();
	public static final Gson gsonObject;
	
	//Creates a new Gson object at every call to this method (for use in parsing)
	static {
		gsonObject = new Gson();
		
	}
	
	public static boolean verifySyntax( String filePath ) throws IOException {
		FileReader reader = new FileReader( filePath );
		try {
			gsonObject.fromJson( reader, Object.class );
			return true;
			
		}
		catch ( JsonSyntaxException exception ) {
			return false;
			
		}
		
	}
	
	/* Google GSON Testing
	public static testjson testjson( String jsonTestString ) {
		return gsonObject.fromJson( jsonTestString, testjson.class );
		
	}
	
	public static testjson testjsonFile( String file ) throws IOException {
		FileReader reader = null;
		reader = new FileReader( launcherDir + SystemInfo.getSystemFileSeperator() + file );
		testjson tjfromfile = gsonObject.fromJson( reader, testjson.class );
		
		reader.close();
		System.out.println( tjfromfile.dinosaur + " " + tjfromfile.test1 + " " + tjfromfile.list);
		return tjfromfile;
		
	} */
	
	public static AssetIndex assetIndex( String file ) throws IOException, JsonSyntaxException {
		FileReader assetIndexReader = new FileReader( filePath + file );
		AssetIndex assetIndex = gsonObject.fromJson( assetIndexReader, AssetIndex.class );
		
		assetIndexReader.close();
		return assetIndex;
		
	}
	
	public static LocalModpacks localModpacks( String file ) throws IOException, JsonSyntaxException {
		FileReader localModpacksReader = new FileReader( filePath + file );
		LocalModpacks localModpacks = gsonObject.fromJson( localModpacksReader, LocalModpacks.class );
		
		localModpacksReader.close();
		return localModpacks;
		
	}
	
	public static ModpackIndex modpackIndex( String file ) throws IOException, JsonSyntaxException {
		FileReader modpackIndexReader = new FileReader( filePath + file );
		ModpackIndex modpackIndex = gsonObject.fromJson( modpackIndexReader, ModpackIndex.class );
		
		modpackIndexReader.close();
		return modpackIndex;
		
	}
	
	public static Settings settings() throws IOException, JsonSyntaxException {
		FileReader settingsReader = new FileReader( filePath + "settings.json" );
		Settings settings = gsonObject.fromJson( settingsReader, Settings.class );
		
		settingsReader.close();
		return settings;
		
	}
	
	public static VersionLaunchJson versionLaunchJson( String file ) throws IOException, JsonSyntaxException {
		FileReader versionLaunchJsonReader = new FileReader( filePath + file );
		VersionLaunchJson versionLaunchJson = gsonObject.fromJson( versionLaunchJsonReader, VersionLaunchJson.class );
		
		versionLaunchJsonReader.close();
		return versionLaunchJson;
		
	}
	
	public static VersionManifest versionManifest( String versionManifestDownload ) throws IOException, JsonSyntaxException {
		VersionManifest versionManifest = gsonObject.fromJson( versionManifestDownload, VersionManifest.class );
		
		return versionManifest;
		
	}
	
}