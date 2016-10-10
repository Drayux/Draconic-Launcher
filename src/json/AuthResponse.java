package json;

/*
 * Mojang auth server response to be RECIEVED by the launcher
 * Called upon with successful post to /authenticate or /refresh
 */

public class AuthResponse {

	public String accessToken;
	public String clientToken; //Needs to be checked against the one saved in settings
	
	public AvailableProfiles[] availableProfiles;
	public SelectedProfile selectedProfile;
	public User user;
	
	//Mojang profiles have to do with available accounts registered within an email--this is currently not implemented
	public static class AvailableProfiles {
		
		public String id;
		public String name;
		public boolean legacy = false;
		
	}
	
	public static class SelectedProfile {
		
		public String id;
		public String name;
		public boolean legacy = false;
		
	}
	
	public static class User {
		
		public String id;
		public Properties[] properties;
		
		public static class Properties {
			
			public String name;
			public String value;
			
		}
		
	}
	
}
