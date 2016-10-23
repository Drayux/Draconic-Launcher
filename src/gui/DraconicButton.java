package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JToolTip;
import sun.swing.SwingUtilities2;

import util.GUIUtils;

/*
 * This class defines the style of the buttons that will be used for the Lancher GUI
 */

//TODO: Choose better colors
//TODO: Make buttons have a 'lighted' effect instead of just changing color

public class DraconicButton extends JButton implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	private static final int arcSize = 13;
	
	//Colors for dark theme (to be finalized)
	private final Color borderColor = new Color( 20, 20, 20, 0 );
	private final Color backgroundColor = new Color( 22, 22, 22 );
	private final Color hoverColor = new Color( 88, 88, 88 );
	private final Color clickedColor = new Color( 127, 127, 127 );
	private final Color textColor = new Color( 255, 255, 255 );
	private final Color tooltipColor = new Color( 50, 50, 50 );
	
	//Colors for light theme (to be finalized)
	private final Color borderColorLight = new Color( 140, 140, 140, 0 );
	private final Color backgroundColorLight = new Color( 192, 192, 192 );
	private final Color hoverColorLight = new Color( 178, 178, 178 );
	private final Color clickedColorLight = new Color( 161, 161, 161 );
	private final Color textColorLight = new Color( 31, 31, 31 );
	private final Color tooltipColorLight = new Color( 250, 250, 250 );
	
	private Color fillColor;
	private boolean darkTheme;
	
	public DraconicButton( String label, boolean darkTheme ) {
		this( label, darkTheme, 0, 0, 30, 20 );
		
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
	
	public DraconicButton( String label, boolean darkTheme, int x, int y, int width, int height ) {
		super( label );
		this.darkTheme = darkTheme;
		this.putClientProperty( SwingUtilities2.AA_TEXT_PROPERTY_KEY, null );
		
		this.setBounds( x, y, width, height ); //This does nothing within GroupLayout
		this.addMouseListener( this );
		
		this.setOpaque( false );
		this.setBorder( new DraconicRoundBorder( arcSize, this.darkTheme ? this.borderColor : this.borderColorLight ) );
		this.setFont( new Font( "Arial", Font.PLAIN, 18 ) ); //This functions as a backup if the next line fails (and why 'this' is used as the parameter in the next line)
		this.setFont( GUIUtils.getDefaultFont( this ).deriveFont( 18f ) ); /*GUIUtils.getDefaultFont( this )*/
		
		this.fillColor = this.darkTheme ? this.backgroundColor : this.backgroundColorLight;
		
	}
	
	@Override
	//This method (and paint method) are called within the swing component automatically
	public void paintComponent( Graphics graphics ) {
		//super.paintComponent( graphics );
		//System.out.println( "Rendering button " + this.getText() );
		
		Graphics2D graphics2d = (Graphics2D) graphics;
		
		graphics2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		graphics2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		
		graphics2d.setColor( this.fillColor );
		graphics2d.fillRoundRect( 0, 0, this.getWidth(), this.getHeight(), arcSize + 1, arcSize );
		
		graphics2d.setColor( this.darkTheme ? this.textColor : this.textColorLight );
		//graphics2d.drawString( this.getText(), ( this.getWidth() - graphics.getFontMetrics().stringWidth( getText() ) ) / 2, (int) ( ( ( this.getHeight() - this.getFont().getSize() ) / 2 ) + ( ( this.getFont().getSize() * 3 ) / 4 ) ) );
		graphics2d.drawString( this.getText(), ( this.getWidth() - graphics.getFontMetrics().stringWidth( getText() ) ) / 2, ( ( this.getHeight() - this.getFont().getSize() ) / 2 ) + ( ( this.getFont().getSize() * 3 ) / 4 ) + 2 );

	}
	
	@Override
	public JToolTip createToolTip() {
		JToolTip toolTip = super.createToolTip();
		
		toolTip.setFont( new Font( "Arial", Font.PLAIN, 13 ) );
		toolTip.setFont( GUIUtils.getDefaultFont( this ).deriveFont( 13f ) );
		
		toolTip.setBorder( new DraconicRoundBorder( 0, this.darkTheme ? this.borderColor : this.borderColorLight ).setOpacity( 255 ) );
		toolTip.setBackground( darkTheme ? this.tooltipColor : this.tooltipColorLight );
		
		return toolTip;
		
	}
	
	@Override
	public void mouseClicked( MouseEvent event ) {
		this.fillColor = this.darkTheme ? this.hoverColor : this.hoverColorLight;
		//repaint(); <-- This is not needed as the swing component is already coded to re-render upon the activation of these events
		
	}

	@Override
	public void mousePressed( MouseEvent event ) {
		this.fillColor = this.darkTheme ? this.clickedColor : this.clickedColorLight;
		//repaint();
		
	}

	@Override
	public void mouseReleased( MouseEvent event ) {
		/*if ( ( event.getX() > this.getX() && event.getX() < ( this.getX() + this.getWidth() ) ) && ( event.getY() > this.getY() && event.getY() < ( this.getY() + this.getHeight() ) ) ) {
			this.fillColor = this.hoverColor;
			
		}
		else {
			this.fillColor = this.backgroundColor;
			
		}*/
		//repaint();
		
	}

	@Override
	public void mouseEntered( MouseEvent event ) {
		//System.out.println( "Mouse Entered " + this.getText() );
		this.fillColor = this.darkTheme ? this.hoverColor : this.hoverColorLight;
		//repaint();
		
	}

	@Override
	public void mouseExited( MouseEvent event ) {
		//System.out.println( "Mouse Exited " + this.getText() );
		this.fillColor = this.darkTheme ? this.backgroundColor : this.backgroundColorLight;
		//repaint();
		
	}

}
