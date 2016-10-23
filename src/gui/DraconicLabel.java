package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

import sun.swing.SwingUtilities2;

public class DraconicLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	
	Color textColor = new Color( 31, 31, 31, 255 );
	
	public DraconicLabel( String label ) {
		super( label );
		this.putClientProperty( SwingUtilities2.AA_TEXT_PROPERTY_KEY, null );
		
	}
	
	public void setOpacity( int alphaValue ) {
		int red = this.textColor.getRed();
		int green = this.textColor.getGreen();
		int blue = this.textColor.getBlue();
		
		this.textColor = new Color( red, green, blue, alphaValue );
		
	}
	
	@Override
	public void paintComponent( Graphics graphics ) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		
		graphics2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		graphics2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		
		graphics2d.setColor( textColor );
		graphics2d.drawString( this.getText(), ( this.getWidth() - graphics.getFontMetrics().stringWidth( getText() ) ) / 2, (int) ( ( ( this.getHeight() - this.getFont().getSize() ) / 2 ) + ( ( this.getFont().getSize() * 3 ) / 4 ) ) );
		
	}
	
}
