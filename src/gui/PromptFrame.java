package gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import util.GUIUtils;

public class PromptFrame extends JFrame implements Runnable, WindowListener {

	private static final long serialVersionUID = 1L;

	public PromptFrame() {
		super( "Set Game Directory (Draconic Launcher)" );
		
		// -- Configure frame properties --
		//this.setResizable( false );
		this.setLocationRelativeTo( null );
		
		this.setIconImage( new ImageIcon( "res/bigger_test_icon.png" ).getImage() ); //Change this when actual icon created
		
		this.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		this.addWindowListener( this );
		
	}
	
	static class BasePane extends JPanel implements ActionListener {

		private static final long serialVersionUID = 1L;
		
		//private boolean colourSwap = false;
		
		public BasePane() {
			//Set panel properties (includes styling and fonts)
			
			//Define panel components
			JButton acceptButton = new FlatButton( "Accept" );
			acceptButton.addActionListener( this );
			
			JButton cancelButton = new FlatButton( "Cancel" );
			
			JButton browseButton = new FlatButton( "Browse" );
			
			//Set panel layout manager and add components
			GroupLayout gl = new GroupLayout( this );
			this.setLayout( gl );
			gl.setAutoCreateContainerGaps( true );
			
			gl.setHorizontalGroup( 
				gl.createSequentialGroup()
				.addComponent( acceptButton, 180, 200, 240 )
				.addComponent( cancelButton, 180, 200, 240 )
			);
			gl.setVerticalGroup(
				gl.createParallelGroup()
				.addComponent( acceptButton, 120, 120, 120 )
				.addComponent( cancelButton, 120, 120, 120 )
			);
			
		
		}

		//Whenever the panel needs to be re-rendered, it calls repaint on the panel's current properties
		@Override
		public void paintComponent( Graphics graphics ) {
			super.paintComponent( graphics );
			
			/*if (!colourSwap) {
				colourSwap = true;
				System.out.println( "test1 ");
				
			}
			else {
				colourSwap = false;
				System.out.println( "test2 ");
				
			}
			
			graphics.setColor( colourSwap ? new Color( 250, 255, 0 ) : new Color( 85, 10, 132 ) );
			graphics.fillRect( this.getX(), this.getY(), this.getWidth(), this.getHeight() );
			
			System.out.println("testing@");*/
			
		}
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			System.out.println( "action performed" );
			
		}
		
	}
	
	@Override
	public void run() {
		//Define panels
		JPanel basePane = new BasePane();
		
		//Set layout manager
		this.setLayout( new BorderLayout() );
		
		//Add panels to frame
		this.add( basePane, BorderLayout.CENTER );
		
		this.pack();
		this.setVisible( true ); //'Opens' the frame
		
	}
	
	@Override
	public void windowClosing( WindowEvent event ) {
		System.out.println( "PROMPT WINDOW CLOSING" );
		
	}

	@Override
	public void windowClosed( WindowEvent event ) {
		System.out.println( "PROMPT WINDOW CLOSED" );
		
	}

	@Override
	public void windowOpened( WindowEvent event ) {
		
	}
	
	@Override
	public void windowIconified( WindowEvent event ) {
		
	}

	@Override
	public void windowDeiconified( WindowEvent event ) {
		
	}

	@Override
	public void windowActivated( WindowEvent event ) {
		
	}

	@Override
	public void windowDeactivated( WindowEvent event ) {
		
	}
	
}

