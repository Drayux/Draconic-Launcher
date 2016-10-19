package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

import util.GUIUtils;

public class FlatButton extends JButton implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	private final Color backgroundColor = new Color( 22, 22, 22 ); //Colors not yet decided for project
	private final Color hoverColor = new Color( 88, 88, 88 );
	private final Color clickedColor = new Color( 127, 127, 127 );
	
	private final Color textColor = new Color( 255, 255, 255 );
	private Color fillColor;
	
	//private static final int radius = 15;
	
	public FlatButton( String label ) {
		this( label, 0, 0, 30, 20 );
		
		/*this.setBorder( new Border() {
			
			@Override
			public void paintBorder( Component component, Graphics graphics, int x, int y, int width, int height ) {
				graphics.drawRoundRect( x, y, width - 1, height - 1, radius, radius );
				
			}

			@Override
			public Insets getBorderInsets( Component component ) {
				return new Insets( radius + 1, radius + 1, radius + 2, radius );
				
			}

			@Override
			public boolean isBorderOpaque() {
				return false;
				
			}
			
		});*/
		
	}
	
	public FlatButton( String label, int x, int y, int width, int height ) {
		super( label );
		
		this.setBounds( x, y, width, height );
		this.addMouseListener( this );
		//this.setBorder();
		this.setFont( GUIUtils.getDefaultFont( this ).deriveFont( 60f ) ); /*GUIUtils.getDefaultFont( this )*/
		
		this.fillColor = this.backgroundColor;
		
	}
	
	@Override
	//This method (and paint method) are called within the swing component automatically
	public void paintComponent( Graphics graphics ) {
		//super.paintComponent( graphics );
		System.out.println( "Rendering button " + this.getText() );
		
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		
		graphics2d.setColor( this.fillColor );
		graphics2d.fillRect( 0, 0, this.getWidth(), this.getHeight() );
		
		graphics2d.setColor( this.textColor );
		graphics2d.drawString( this.getText(), ( this.getWidth() - graphics.getFontMetrics().stringWidth( getText() ) ) / 2, (int) ( ( ( this.getHeight() - this.getFont().getSize() ) / 2 ) + ( ( this.getFont().getSize() * 3 ) / 4 ) ) );
		
	}
	
	@Override
	public void mouseClicked( MouseEvent event ) {
		
	}

	@Override
	public void mousePressed( MouseEvent event ) {
		this.fillColor = this.clickedColor;
		//repaint();
		
	}

	@Override
	public void mouseReleased( MouseEvent event ) {
		this.fillColor = this.hoverColor;
		//repaint();
		
	}

	@Override
	public void mouseEntered( MouseEvent event ) {
		System.out.println( "Mouse Entered " + this.getText() );
		this.fillColor = this.hoverColor;
		//repaint();
		
	}

	@Override
	public void mouseExited( MouseEvent event ) {
		System.out.println( "Mouse Exited " + this.getText() );
		this.fillColor = this.backgroundColor;
		//repaint();
		
	}

}
