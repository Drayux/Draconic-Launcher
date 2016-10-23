package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.Border;

public class DraconicRoundBorder implements Border {

	private int arcSize;
	private Color color;
	
	public DraconicRoundBorder() {
		this.arcSize = 15;
		this.color = new Color( 0, 0, 0 );
		
	}
	
	public DraconicRoundBorder( int arc ) {
		this();
		this.arcSize = arc;
		
	}
	
	public DraconicRoundBorder( Color color ) {
		this();
		this.color = color;
		
	}
	
	public DraconicRoundBorder( int arc, Color color ) {
		this.arcSize = arc;
		this.color = color;
		
	}
	
	/*public void paintComponent( Graphics graphics ) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		
		graphics2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		
		graphics2d.setColor( this.color );
		graphics2d.fillRect( 0, 0, 1, 1 );
		
		System.out.println( "testing" );
		
	}*/

	public Border setOpacity( int alphaValue ) {
		int red = this.color.getRed();
		int green = this.color.getGreen();
		int blue = this.color.getBlue();
		
		this.color = new Color( red, green, blue, alphaValue );
		
		return this;
		
	}
	
	@Override
	public void paintBorder( Component component, Graphics graphics, int x, int y, int width, int height ) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		
		graphics2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		
		graphics2d.setColor( this.color );
		graphics2d.drawRoundRect( x, y, width - 1, height - 1, arcSize + 1, arcSize );
		
	}

	@Override
	public Insets getBorderInsets( Component component ) {
		return new Insets( 5, 5, 5, 5 ); //Top border, Left border, Bottom border, Right border
		
	}

	@Override
	public boolean isBorderOpaque() {
		if ( this.color.getAlpha() < 255 ) {
			return false;
			
		}
		else {
			return true;
			
		}
		
	}
	
}
