package util;

import com.sun.management.*;
import java.lang.management.ManagementFactory;

public class SystemInfo {
	
	//returns the current os
	public static String getSystemOS() {
		String systemOS = System.getProperty( "os.name" ).toLowerCase();
		
		if ( systemOS.contains( "linux" ) ) {
			return "LINUX";
			
		}
		else if ( systemOS.contains( "osx" ) ) {
			return "OSX";
			
		}
		/* Don't think I need this...
		 * Originally thought linux was spelled lunix or something silly...apparently
		 else if ( systemOS.contains( "unix" ) ) {
			return "UNIX";
			
		} */
		else if ( systemOS.contains( "windows" ) ) {
			return "WINDOWS";
			
		}
		else {
			return "OTHER";
			
		}
	}
	
	public static String getSystemFileSeperator() {
		switch ( getSystemOS() ) {
		case "WINDOWS":
			return "\\";
			
		default:
			return "/";
			
		}
		
	}
	
	public static String getJVMArch() {
		return System.getProperty( "sun.arch.data.model" );
		
	}
	
	public static String getOSArch() {
		return System.getProperty( "os.arch" );
		
	}
	
	public static String getJavaVersion() {
		String javaVersion = System.getProperty( "java.version" );
		if (!javaVersion.contains( "1.8." )) {
			System.out.println( "[Draconic Launcher][SystemInfo][Warn] Java version not up to date! Some features may not work!" );
			
		}
		return javaVersion;
		
	}
	
	public static long getSystemMemory() {
		try {
			OperatingSystemMXBean system = ( com.sun.management.OperatingSystemMXBean ) ManagementFactory.getOperatingSystemMXBean();
			return system.getTotalPhysicalMemorySize() / 1048567;
			
		}
		catch ( Exception e ) {
			return Runtime.getRuntime().totalMemory();
			//return Runtime.getRuntime().maxMemory();
			
		}
		
	}
	
	public static String getLauncherDir() {
		String userHome = System.getProperty( "user.home" );
		
		switch ( getSystemOS() ) {
		case "OSX":
			return userHome + "/Library/Application Support/Draconic Launcher";
			
		case "UNIX":
			return userHome + "/Draconic Launcher";
			
		case "WINDOWS":
			return userHome + "\\AppData\\Roaming\\Draconic Launcher";
			
		default:
			return userHome + "/Draconic Launcher";
			
		}
		
	}
	
}
