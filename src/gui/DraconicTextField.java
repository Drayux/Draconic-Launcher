package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;
import sun.swing.SwingUtilities2;

import util.GUIUtils;

public class DraconicTextField extends JTextField {
	
	private static final long serialVersionUID = 1L;
	
	private static final int arcSize = 13;
	
	final Color textColor = new Color( 31, 31, 31 );
	final Color boxColor = new Color( 250, 250, 250 );
	final Color borderColor = new Color( 250, 250, 250, 0 );
	//final Color transparentColor = new Color( 0, 0, 0, 0 );

	//String oldText = null;
	
	public DraconicTextField() {
		this.setOpaque( false );
		this.setForeground( textColor ); //Text color
		this.setBackground( boxColor );  //BG color
		this.putClientProperty( SwingUtilities2.AA_TEXT_PROPERTY_KEY, null );
		
		this.setFont( new Font( "Arial", Font.PLAIN, 18 ) );
		this.setFont( GUIUtils.getDefaultFont( this ).deriveFont( Font.PLAIN, 18f ) );
		
		//this.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		this.setBorder( new DraconicRoundBorder( arcSize, borderColor ) );
		//Setting the size here has no result as the layout manager overrides it
		
	}
	
	@Override
	public void paintComponent( Graphics graphics )	{
		Graphics2D graphics2d = (Graphics2D) graphics;
		
		graphics2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		graphics2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		
		//System.out.println(this.getBackground().getRed() + this.getBackground().getGreen() + this.getBackground().getBlue());
		//graphics2d.setColor( ( this.getText().equals( this.oldText ) ) ? this.boxColor : this.getBackground() );
		
		graphics2d.setColor( this.getBackground() );
		graphics2d.fillRoundRect( 0, 0, this.getWidth(), this.getHeight(), arcSize, arcSize );
		
		//graphics2d.setColor( transparentColor );
		super.paintComponent( graphics2d );
		
		//this.oldText = this.getText();
		
	}

}
