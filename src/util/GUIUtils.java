package util;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.JComponent;

public class GUIUtils {

	public static Font getDefaultFont( JComponent component ) {
		InputStream stream = null;
		//byte[] byteStream = null;
		
		try {
			stream = new FileInputStream( "res/DroidSans.ttf" );
			/*byteStream = new byte[stream.available()];
			
			for ( int i = 0; i < byteStream.length; i++ ) {
				byteStream[i] = (byte) stream.read();
				
			}*/
			
			return Font.createFont( Font.PLAIN, stream );
			//return new Font( "Arial", Font.PLAIN, 40 );
			
		}
		catch ( Exception exception ) {
			exception.printStackTrace();
			return component.getFont();
			
		}
		
	}
	
}
