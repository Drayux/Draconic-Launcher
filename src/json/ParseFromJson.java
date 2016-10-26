package json;

import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import file.Profile;
import file.Settings;
import util.SystemInfo;

public class ParseFromJson {
	
	private static String filePath = SystemInfo.getLauncherDir() + SystemInfo.getSystemFileSeperator();
	public static final Gson gsonObject;
	
	//Creates a new Gson object at every call to this method (for use in parsing)
	static {
		gsonObject = new Gson();
		
	}
	
	public static boolean verifySyntax( String filePath ) throws IOException {
		FileReader reader = null;
		try {
			reader = new FileReader( filePath );
			
			gsonObject.fromJson( reader, Object.class );
			return true;
			
		}
		catch ( JsonSyntaxException exception ) {
			return false;
			
		}
		finally {
			if ( reader != null ) {
				reader.close();
				
			}
			
		}
		
	}
	
	//DOWNLOADS
	public static AuthResponse authResponse( String response ) {
		return gsonObject.fromJson( response, AuthResponse.class );
		
	}
	
	public static ErrorResponse errorResponse( String response ) {
		return gsonObject.fromJson( response, ErrorResponse.class );
		
	}
	
	//FILES
	public static Settings settings() throws IOException, JsonSyntaxException {
		FileReader settingsReader = new FileReader( filePath + "settings.json" );
		Settings settings = gsonObject.fromJson( settingsReader, Settings.class );
		
		settingsReader.close();
		return settings;
		
	}
	
	public static Profile profile( String profileString ) throws IOException, JsonSyntaxException {
		return gsonObject.fromJson( profileString, Profile.class );
		
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
		
	}
	
	* CURRENTLY UNUSED
	
	public static _AssetIndex assetIndex( String file ) throws IOException, JsonSyntaxException {
		FileReader assetIndexReader = new FileReader( filePath + file );
		_AssetIndex assetIndex = gsonObject.fromJson( assetIndexReader, _AssetIndex.class );
		
		assetIndexReader.close();
		return assetIndex;
		
	}
	
	public static _LocalModpacks localModpacks( String file ) throws IOException, JsonSyntaxException {
		FileReader localModpacksReader = new FileReader( filePath + file );
		_LocalModpacks localModpacks = gsonObject.fromJson( localModpacksReader, _LocalModpacks.class );
		
		localModpacksReader.close();
		return localModpacks;
		
	}
	
	public static _ModpackIndex modpackIndex( String file ) throws IOException, JsonSyntaxException {
		FileReader modpackIndexReader = new FileReader( filePath + file );
		_ModpackIndex modpackIndex = gsonObject.fromJson( modpackIndexReader, _ModpackIndex.class );
		
		modpackIndexReader.close();
		return modpackIndex;
		
	}
	
	public static _VersionLaunchJson versionLaunchJson( String file ) throws IOException, JsonSyntaxException {
		FileReader versionLaunchJsonReader = new FileReader( filePath + file );
		_VersionLaunchJson versionLaunchJson = gsonObject.fromJson( versionLaunchJsonReader, _VersionLaunchJson.class );
		
		versionLaunchJsonReader.close();
		return versionLaunchJson;
		
	}
	
	public static _VersionManifest versionManifest( String versionManifestDownload ) throws IOException, JsonSyntaxException {
		_VersionManifest versionManifest = gsonObject.fromJson( versionManifestDownload, _VersionManifest.class );
		
		return versionManifest;
		
	} */
	
}