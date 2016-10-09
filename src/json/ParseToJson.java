package json;

//import java.util.List;
import com.google.gson.*;

import file.Profile;
import file.Settings;

public class ParseToJson {
	
	public static final Gson gsonObject;
	
	//Creates a new Gson object at every call to this method (for use in parsing)
	static {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		gsonObject = gsonBuilder.create();
		
	}
	
	/* Google GSON Testing
	public static String testjson( String test1, List<Integer> list, double dinosaur ) {
		
		testjson thisIsATestObject = new testjson(  test1, list, dinosaur );
		
		System.out.println( thisIsATestObject.test1 );
		System.out.println( thisIsATestObject.list );
		System.out.println( thisIsATestObject.dinosaur );
		
		String jsonTest = gsonObject.toJson( thisIsATestObject );
		
		System.out.println( jsonTest );
		
		return jsonTest;
		
	} */
	
	public static String settings( Settings settingsObject ) {
		return gsonObject.toJson( settingsObject );
		
	}
	
	public static String profile( Profile profileObject ) {
		return gsonObject.toJson( profileObject );
		
	}
	
	public static String authPayload( AuthPayload authPayloadObject ) {
		return gsonObject.toJson( authPayloadObject );
		
	}
	
}
