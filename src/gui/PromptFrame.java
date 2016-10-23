package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import file.LauncherFile;
import file.Settings;
import main.Main;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import util.GUIUtils;
import util.SystemInfo;

//TODO GUI Improvement: instead of red from invalid directory flashing on and off, make it fade out

public class PromptFrame extends JFrame implements Runnable, WindowListener {

	private static final long serialVersionUID = 1L;

	public PromptFrame() {
		super( "Set Game Directory (Draconic Launcher)" );
		
		// -- Configure frame properties --
		this.setResizable( false );
		this.setAlwaysOnTop( true );
		
		this.setIconImage( new ImageIcon( "res/bigger_test_icon.png" ).getImage() ); //Change this when actual icon created
		
		this.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		this.addWindowListener( this );
		
	}
	
	static class BasePane extends JPanel implements ActionListener {

		private static final long serialVersionUID = 1L;
		
		private static final Color backgroundColor = new Color( 230, 230, 230 );
		
		private DraconicTextField gamedirBox;
		private DraconicLabel dirMessage;
		//private boolean colourSwap = false;
		
		public BasePane() {
			//Set panel properties (includes styling and fonts)
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				
			} 
			catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception ) {
				exception.printStackTrace();
				
			}
			this.setBackground( backgroundColor );
			
			//Define panel buttons
			DraconicButton acceptButton = new DraconicButton( "Accept", false );
			acceptButton.setActionCommand( "ACCEPT" );
			acceptButton.addActionListener( this );
			acceptButton.setToolTipText( "Set the game directory as the directory name in the box" );
			
			DraconicButton cancelButton = new DraconicButton( "Cancel", false );
			cancelButton.setActionCommand( "CANCEL" );
			cancelButton.addActionListener( this );
			cancelButton.setToolTipText( "Ignore what's in the box and use the default game directory" );
			
			DraconicButton browseButton = new DraconicButton( "Browse", false );
			browseButton.setActionCommand( "BROWSE" );
			browseButton.addActionListener( this );
			browseButton.setToolTipText( "Look for a directory..." );
			
			//Define panel labels
			DraconicLabel gamedirLabel = new DraconicLabel( "Enter game directory:" );
			gamedirLabel.setFont( new Font( "Arial", Font.PLAIN, 18 ) );
			gamedirLabel.setFont( GUIUtils.getDefaultFont( this ).deriveFont( Font.PLAIN, 18f ) );
			
			this.dirMessage = new DraconicLabel( "" );
			this.dirMessage.setFont( new Font( "Arial", Font.PLAIN, 18 ) );
			this.dirMessage.setFont( GUIUtils.getDefaultFont( this ).deriveFont( Font.PLAIN, 18f ) );
			//this.dirMessage.setOpacity( 0 ); <-- Using this to 'show' the message is now deprecated as there are multiple possible messages
			
			//Define text fields/boxes
			this.gamedirBox = new DraconicTextField();
			this.gamedirBox.setText( SystemInfo.getLauncherDir() );
			this.gamedirBox.addKeyListener( new KeyListener() {
				@Override
				public void keyTyped( KeyEvent event ) {
					gamedirBox.setBackground( gamedirBox.boxColor );
					/*if ( dirMessage.textColor.getAlpha() > 0 ) {
						dirMessage.setOpacity( 0 );
						repaint();
						
					}*/
					if ( !dirMessage.getText().equals( "" ) ) {
						dirMessage.setText( "" );
						dirMessage.repaint();
						
					}
					
				}

				@Override
				public void keyPressed( KeyEvent event ) {
					
				}

				@Override
				public void keyReleased( KeyEvent event ) {
					
				}
				
			});
			
			//Set panel layout manager and add components
			GroupLayout gl = new GroupLayout( this );
			this.setLayout( gl );
			
			gl.setAutoCreateContainerGaps( true );
			
			/*Sizing
			gl.setHorizontalGroup( 
				gl.createSequentialGroup()
				.addComponent( acceptButton, 92, 92, 92 )
				.addComponent( cancelButton, 92, 92, 92 )
				.addComponent( gamedirLabel )
				.addComponent( gamedirBox, 400, 400, 400 )
			);
			gl.setVerticalGroup(
				gl.createParallelGroup()
				.addComponent( acceptButton, 34, 34, 34 )
				.addComponent( cancelButton, 34, 34, 34 )
				.addComponent( gamedirLabel )
				.addComponent( gamedirBox, 34, 34, 34 )
			);*/
			
			gl.setHorizontalGroup( gl.createSequentialGroup()
				.addGroup( gl.createParallelGroup()
					.addComponent( gamedirLabel )
					.addComponent( this.gamedirBox, 500, 500, 500 )
					.addGroup( gl.createSequentialGroup()
						.addComponent( this.dirMessage )
						.addPreferredGap( LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
						.addComponent( acceptButton, 92, 92, 92 )
					)
				)
				.addPreferredGap( LayoutStyle.ComponentPlacement.RELATED )
				.addGroup( gl.createParallelGroup()
					.addComponent( browseButton, 92, 92, 92 )
					.addComponent( cancelButton, 92, 92, 92 )
				)
			);
			gl.setVerticalGroup( gl.createSequentialGroup()
				.addComponent( gamedirLabel )
				//.addPreferredGap( LayoutStyle.ComponentPlacement.RELATED )
				.addGroup( gl.createParallelGroup()
					.addComponent( this.gamedirBox, 32, 32, 32 )
					.addComponent( browseButton, 32, 32, 32 )
				)
				.addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED )
				.addGroup( gl.createParallelGroup( Alignment.BASELINE )
					.addComponent( this.dirMessage )
					.addComponent( acceptButton, 32, 32, 32 )
					.addComponent( cancelButton, 32, 32, 32 )
				)
			);
			
		}
		
		//Whenever the panel needs to be re-rendered, it calls repaint on the panel's current properties
		/*@Override
		public void paintComponent( Graphics graphics ) {
			super.paintComponent( graphics );
			
			if (!colourSwap) {
				colourSwap = true;
				System.out.println( "test1 ");
				
			}
			else {
				colourSwap = false;
				System.out.println( "test2 ");
				
			}
			
			graphics.setColor( colourSwap ? new Color( 250, 255, 0 ) : new Color( 85, 10, 132 ) );
			graphics.fillRect( this.getX(), this.getY(), this.getWidth(), this.getHeight() );
			
			System.out.println("testing@");
			
		}*/
		
		// -- ALL BUTTON ACTIONS HERE --
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			switch ( event.getActionCommand() ) {
				case "ACCEPT":
					if ( LauncherFile.verifyDirectoryPermissions( gamedirBox.getText() ) ) {
						System.out.println( "[Draconic Launcher][LauncherGUI][Info] Directory successfully verified" );
						//gamedirBox.setBackground( new Color( 34, 227, 34 ) );
						
						File directory = new File( gamedirBox.getText() );
						String absPath = directory.getAbsolutePath();
						
						if ( gamedirBox.getText().equals( absPath ) ) {
							gamedirBox.setBackground( new Color( 34, 227, 34 ) );
							gamedirBox.repaint();
							
							Settings.settings.gameDirectory = gamedirBox.getText();
							SwingUtilities.getWindowAncestor( this ).dispose();
							
						}
						else {
							gamedirBox.setText( absPath );
							gamedirBox.setBackground( new Color( 34, 227, 34 ) );
							gamedirBox.repaint();
							
							dirMessage.setText( "Reletive directory valid!" );
							System.out.println( "[DraconicLauncher][PromptFrame][Info] Using reletive path as absolute path: " + absPath );
							
						}
						
					}
					else {
						System.out.println( "[Draconic Launcher][LauncherGUI][Warn] Failed to verify directory" );
						//gamedirBox.oldText = gamedirBox.getText();
						gamedirBox.setBackground( new Color( 227, 34, 34 ) );
						gamedirBox.repaint();
						
						//dirMessage.setOpacity( 255 );
						dirMessage.setText( "Directory invalid" );
						dirMessage.repaint();
						
					}
					return;
					
				case "BROWSE":
					JFileChooser browseDialog = new JFileChooser( "Browse for game directory..." );
					int returnValue;
					
					JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor( this );
					parentFrame.setAlwaysOnTop( false );
					
					//Maybe add something to show hidden folders...
					browseDialog.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
					browseDialog.setCurrentDirectory( new File( SystemInfo.getLauncherDir() ) );
					
					returnValue = browseDialog.showOpenDialog( parentFrame );
					
					if ( returnValue == JFileChooser.APPROVE_OPTION ) {
						gamedirBox.setBackground( gamedirBox.boxColor );
						/*if ( dirMessage.textColor.getAlpha() > 0 ) {
							dirMessage.setOpacity( 0 );
							repaint();
							
						}*/
						if ( !dirMessage.getText().equals( "" ) ) {
							dirMessage.setText( "" );
							dirMessage.repaint();
							
						}
						gamedirBox.setText( browseDialog.getSelectedFile().getAbsolutePath() );
						
					}
					return;
					
				case "CANCEL":
					Settings.settings.gameDirectory = SystemInfo.getLauncherDir();
					SwingUtilities.getWindowAncestor( this ).dispose();
					return;
					
				default:
					System.out.println( "[DraconicLauncher][PromptFrame][Warn] Performed action: " + event.getActionCommand() );
					return;
			
			}
			
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
		this.setLocationRelativeTo( null );
		this.setVisible( true ); //'Opens' the frame
		
	}
	
	@Override
	//This method is not called when using frame.dispose()
	public void windowClosing( WindowEvent event ) {
		//System.out.println( "PROMPT WINDOW CLOSING" );
		System.out.println( "[Draconic Launcher][LauncherGUI][Info] Closing launcher..." );
		System.exit( 0 );
		
	}

	@Override
	public void windowClosed( WindowEvent event ) {
		//System.out.println( "PROMPT WINDOW CLOSED" );
		Main.launchFrame.start();
		
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

