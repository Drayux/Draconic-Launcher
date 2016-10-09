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
