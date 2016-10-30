package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

//import javax.swing.border.LineBorder;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import file.Profile;
import file.Settings;

public class LaunchFrame extends JFrame implements Runnable, WindowListener {
	
	private static final long serialVersionUID = 1L;
	
	private static int minWidth = 640;
	private static int minHeight = 360;
	private static int prefWidth;
	private static int prefHeight;
	private static int maxWidth;
	private static int maxHeight;
	
	private BasePane basePane = new BasePane();

	static {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		float scalingFactor =  Settings.getScalingFactor();
		
		minWidth	*=	scalingFactor;
		minHeight	*=	scalingFactor;
		
		prefWidth	=	(int) ( ( ( screenSize.height * 8 ) / 9 ) * scalingFactor );
		prefHeight	=	(int) ( ( screenSize.height / 2 ) * scalingFactor );
		
		maxWidth	=	minWidth * 4;
		maxHeight	=	minHeight * 4;
		
	}
	
	public LaunchFrame() {
		super( "Draconic Launcher" );
		
		// -- Configure frame properties --
		this.setSize( prefWidth, prefHeight ); 							//Size set with static method called on every instance of the class
		this.setMinimumSize( new Dimension( minWidth, minHeight ) ); 	//Resets window size to the minimum if the window is so small its preferred size is less than the minimum
		this.setMaximumSize( new Dimension( maxWidth, maxHeight ) ); 	//Similarly, sets the maximum size of the frame so that components don't become oddly deformed
		this.setLocationRelativeTo( null );
		
		this.setIconImage( new ImageIcon( "res/bigger_test_icon.png" ).getImage() ); //Change this when actual icon created
		
		this.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		this.addWindowListener( this );
		
	}
	
	/*
	 * -- Nested Pane Classes --
	 * 
	 * Classes are nested in the same configuration as the panes are of the GUI
	 * TODO: AESTHETICS, AESTHETICS, AESTHETICS!!!
	 * 
	 */
	
	static class BasePane extends JPanel implements ActionListener {
		
		private static final long serialVersionUID = 1L;
		
		//private final Color borderColor = new Color( 0, 0, 0 );
		
		private GroupLayout gl;
		//private GridBagConstraints constraints = new GridBagConstraints();
		private Timer renderTimer = null;

		private InfoPane infoPane;
		private TabsPane tabsPane;
		private UserPane userPane;
		
		private int minUserPaneX = 196;
		//private int minUserProfilePaneX = 400; //TODO: Size
		private int minTabsPaneY = 48;
		
		private int maxUserPaneX = 256;
		//private int maxUserProfilePaneX = 600; //TODO: Size
		private int maxTabsPaneY = 64;
		
		private int rollOverX = 960;
		private int rollOverY = 540;
		
		
		public BasePane() {
			renderTimer = new Timer( 1000 / 60, this );
			gl = new GroupLayout( this );
			
			//this.setBackground( new Color( 0, 0, 0 ) );
			//TODO: This pane contains the background image!
			
			//Panes
			this.infoPane = new InfoPane();
			
			this.tabsPane = new TabsPane();
			//this.tabsPane.setBorder( new LineBorder( borderColor, 5 ) );
			
			this.userPane = new UserPane();
			
			//Layout Manager (not present here because it needs to be called separately for the frames to resize correctly
			
		}
		
		public void addComponents() {
			/* Grid bag testing
			//constraints.fill = GridBagConstraints.BOTH;
			
			//constraints.weightx = 0.5;
			//constraints.weighty = 0.5;
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			//constraints.anchor = GridBagConstraints.LAST_LINE_START;
			constraints.gridwidth = 5;
			constraints.gridheight = 5;
			
			this.add( infoPane, constraints );
			
			constraints.gridx = 0;
			constraints.gridy = 5;
			//constraints.anchor = GridBagConstraints.FIRST_LINE_START;
			
			this.add( tabsPane, constraints );
			
			constraints.gridx = 5;
			constraints.gridy = 0;
			constraints.gridwidth = 5;
			constraints.gridheight = 10;
			//constraints.anchor = GridBagConstraints.FIRST_LINE_END;
			
			this.add( userPane, constraints );*/
			
			gl.setHorizontalGroup( gl.createSequentialGroup()
				.addGroup( gl.createParallelGroup()
					.addComponent( tabsPane )
					.addComponent( infoPane )
				)
				.addComponent( userPane )
			);
			gl.setVerticalGroup( gl.createParallelGroup()
				.addGroup( gl.createSequentialGroup()
					.addComponent( tabsPane )
					.addComponent( infoPane )
				)
				.addComponent( userPane )
			);
			
		}
		
		public void addComponents( int parentWidth, int parentHeight, int userPaneX, int tabsPaneY ) {
			gl.setHorizontalGroup( gl.createSequentialGroup()
				.addGroup( gl.createParallelGroup()
					.addComponent( tabsPane, 0, parentWidth - userPaneX, parentWidth - userPaneX )
					.addComponent( infoPane, 0, parentWidth - userPaneX, parentWidth - userPaneX )
				)
				.addComponent( userPane, userPaneX, userPaneX, userPaneX )
			);
			gl.setVerticalGroup( gl.createParallelGroup()
				.addGroup( gl.createSequentialGroup()
					.addComponent( tabsPane, tabsPaneY, tabsPaneY, tabsPaneY )
					.addComponent( infoPane, 0, parentHeight - tabsPaneY, parentHeight - tabsPaneY )
				)
				.addComponent( userPane, parentHeight, parentHeight, parentHeight )
			);
			
		}
		
		/*@Override
		public void paintComponent( Graphics graphics ) {
			super.paintComponent( graphics );	
			
		}*/
		
		//Information Pane - Displays info on whatever is selected within the selection pane or functions as a controls pane for selection pane tabs
		static class InfoPane extends JPanel {
			
			private static final long serialVersionUID = 1L;
			
			public int paneWidth;
			public int paneHeight;
			
			public InfoPane() {
				this.setBackground( new Color( 255, 0, 0 ) );
				
			}
			
		}
		
		//Tabs Pane - Allows selection of menu and controls what will be visible in the info pane
		static class TabsPane extends JPanel {
			
			private static final long serialVersionUID = 1L;
	
			public int paneWidth;
			public int paneHeight;
			public enum selectedTab { MODPACKS, NEWS, CONSOLE, TOOLS, SETTINGS };
			public boolean selectionPaneVisible = false;
			
			public TabsPane() {
				this.setBackground( new Color( 0, 255, 0 ) );
				
			}
			
			//Selection Pane - Subpane of the tabs pane, allowing for further selection on relevant tabs (ex. modpack selection)
			static class SelectionPane extends JPanel {
				
				private static final long serialVersionUID = 1L;
				
				//do stuff
				
			}
			
			//do stuff
			
		}
		
		//User Pane - The section that contains all the user information as well as the login/logout and launch controls
		static class UserPane extends JPanel implements ActionListener {
			
			private static final long serialVersionUID = 1L;
	
			public int paneWidth;
			public int paneHeight;
			public boolean profilePaneVisible = false;
			
			private Color paneColor = new Color( 0, 0, 255, 255 ); //TODO: Find some good-leukin coolars!
			
			public LaunchPane launchPane;
			
			public DraconicButton profileView; //TODO: Change to a square button!
			
			public UserPane() {
				//Set details
				//this.setBackground( new Color( 0, 0, 255 ) );
				this.setOpaque( false );
				
				//Declare components
				launchPane = new LaunchPane( LaunchPane.getPaneMode() );
				
				profileView = new DraconicButton( "<", true );
				profileView.addActionListener( this );
				
				//Set layout manager
				this.setLayout( new BorderLayout() );
				
				//Add components
				//TODO: add the user head thing
				this.add( launchPane, BorderLayout.CENTER );
				this.add( profileView, BorderLayout.WEST );
				
			}
			
			static class LaunchPane extends JPanel implements ActionListener {

				private static final long serialVersionUID = 1L;
				
				private GroupLayout gl;
				private enum paneMode { INACTIVE, SUSPENDED, ACTIVE };
				private paneMode mode;
				
				public LaunchPane( paneMode mode ) {
					this.mode = mode;
					gl = new GroupLayout( this );
					
					//Details
					this.setOpaque( false );
					this.setBackground( new Color( 0, 0, 0, 0 ) );
					
					//Components
					this.addComponents( 0 );
					
				}
				
				public void addComponents( float baseSize ) {
					switch ( this.mode ) {
						case INACTIVE:
							gl.setHorizontalGroup( gl.createParallelGroup()
							);
							gl.setVerticalGroup( gl.createSequentialGroup()
							);
							
							break;
							
						case SUSPENDED:
							gl.setHorizontalGroup( gl.createParallelGroup()
								//Welcome label
								//PlayerName label
								//Username box (prefilled with saved username)
								//Password box
								//Log in button
							);
							gl.setVerticalGroup( gl.createSequentialGroup()
							);
							
							break;
						
						case ACTIVE:
							gl.setHorizontalGroup( gl.createParallelGroup()
								//Welcome label
								//PlayerName label
								//Account buttons
								//Play status label
								//Selected modpack label
								//Launch button
							);
							gl.setVerticalGroup( gl.createSequentialGroup()
								//Welcome label
								//PlayerName label
								//Account buttons
								//Play status label
								//Selected modpack label
								//Launch button
							);
							
							break;
					
					}
					
				}
				
				public static paneMode getPaneMode() {
					//Mode will be either suspended or active if a profile is set
					if ( Profile.selectedProfile == null ) {
						return paneMode.INACTIVE;
						
					}
					else if ( Profile.selectedProfile.accessToken == null || Profile.selectedProfile.clientToken == null ) {
						return paneMode.SUSPENDED;
						
					}
					else {
						return paneMode.ACTIVE;
						
					}
					
				}
				
				@Override
				public void actionPerformed( ActionEvent event ) {
					// TODO Auto-generated method stub
					
				}
				
			}
			
			//Profile Pane - Extension of the user pane that allows for profile configuration (essentially personal settings / changing your skin within the launcher)
			static class ProfilePane extends JPanel {
	
				private static final long serialVersionUID = 1L;
				
				//do stuff
				
			}
			
			@Override 
			public void paintComponent( Graphics graphics ) {
				super.paintComponent( graphics );
				Graphics2D graphics2d = (Graphics2D) graphics;
				
				graphics2d.setColor( this.paneColor );
				graphics2d.fillRect( 0, 0, this.getWidth(), this.getHeight() );
				
				
			}

			@Override
			public void actionPerformed( ActionEvent event ) {
				if ( this.profilePaneVisible ) {
					this.profilePaneVisible = false;
					this.profileView.setText( "<" );
					return;
					
				}
				this.profilePaneVisible = true;
				this.profileView.setText( ">" );
				
			}
			
		}

		//This listener is specifically for the RENDER TIMER
		@Override
		public void actionPerformed( ActionEvent event ) {
			//System.out.println( "Rendering!" );
			//System.out.println( this.getWidth() + ", " + this.getHeight() );
			
			//Minimum size at minimum window size
			//Maximum size at 960 x 540
			
			double userPaneXSize = maxUserPaneX; //( this.userPane.profilePaneVisible ) ? maxUserProfilePaneX : maxUserPaneX;
			double userPaneYSize = maxUserPaneX; //Misleading name - It is the size of the pane calculated from the height of the frame
			
			double tabsPaneXSize = maxTabsPaneY;
			double tabsPaneYSize = maxTabsPaneY;
			
			//Small note, going further on the Y doesn't match up with the X. This is because the window can get smaller than the minHeight for some reason...probably the title bar.
			//Calculate the size of the user pane
			if ( this.getWidth() <= rollOverX ) {
				double userPaneXSlope = (double) ( /*( ( this.userPane.profilePaneVisible ) ? maxUserProfilePaneX : maxUserPaneX )*/maxUserPaneX - /*( ( this.userPane.profilePaneVisible ) ? minUserProfilePaneX : minUserPaneX )*/minUserPaneX ) / (double) ( rollOverX - minWidth );
				userPaneXSize = ( userPaneXSlope * this.getWidth() ) + ( /*( ( this.userPane.profilePaneVisible ) ? maxUserProfilePaneX : maxUserPaneX )*/maxUserPaneX - ( userPaneXSlope * (double) rollOverX ) );
				
				double tabsPaneSlope = (double) ( maxTabsPaneY - minTabsPaneY ) / (double) ( rollOverX - minWidth );
				tabsPaneXSize = ( tabsPaneSlope * this.getWidth() ) + ( maxTabsPaneY - ( tabsPaneSlope * (double) rollOverX ) );
				
			}
			
			if ( this.getHeight() <= rollOverY ) {
				double userPaneYSlope = (double) ( /*( ( this.userPane.profilePaneVisible ) ? maxUserProfilePaneX : maxUserPaneX )*/maxUserPaneX - /*( ( this.userPane.profilePaneVisible ) ? minUserProfilePaneX : minUserPaneX )*/minUserPaneX ) / (double) ( rollOverY - minHeight );
				userPaneYSize = ( userPaneYSlope * this.getHeight() ) + ( /*( ( this.userPane.profilePaneVisible ) ? maxUserProfilePaneX : maxUserPaneX )*/maxUserPaneX - ( userPaneYSlope * (double) rollOverY ) );
				
				double tabsPaneYSlope = (double) ( maxTabsPaneY - minTabsPaneY ) / (double) ( rollOverY - minHeight );
				tabsPaneYSize = ( tabsPaneYSlope * this.getHeight() ) + ( maxTabsPaneY - ( tabsPaneYSlope * (double) rollOverY ) );
				
			}
			
			//This statement checks if the user pane uses the entire frame or not. This is ignored with the second as the function takes the bigger of the two
			if ( this.userPane.profilePaneVisible ) {
				userPaneXSize = userPaneYSize = this.getWidth();
				
			}
			
			//TODO: Calculate the size of the user pane font
			
			//TODO: Calculate the size of the tabs pane font
			
			//Re-add the components
			//userPane.paneWidth = (int) ( ( userPaneXSize < userPaneYSize ) ? (int) userPaneXSize : (int) userPaneYSize );
			//tabsPane.paneHeight = (int) ( ( tabsPaneXSize < tabsPaneYSize ) ? (int) tabsPaneXSize : (int) tabsPaneYSize );
			//this.addComponents( this.getWidth(), this.getHeight(), userPane.paneWidth, tabsPane.paneHeight );
			this.addComponents( this.getWidth(), this.getHeight(), (int) ( ( userPaneXSize < userPaneYSize ) ? (int) userPaneXSize : (int) userPaneYSize ), (int) ( ( tabsPaneXSize < tabsPaneYSize ) ? (int) tabsPaneXSize : (int) tabsPaneYSize ) );
			
		}
	
	}
	
	@Override
	public void run() {
		//Save settings file
		Settings.settings.write( false );
		//Usually, I would declare components here, but this simply encases basePane which is where all the code for this GUI will actually go
		
		//Set layout manager
		this.setLayout( new BorderLayout() );
		
		//Add Components
		this.add( this.basePane, BorderLayout.CENTER );
		this.basePane.setLayout( this.basePane.gl );
		this.basePane.renderTimer.start();
		
		this.setVisible( true ); //'Opens' the frame
		
	}

	@Override
	public void windowClosing( WindowEvent event ) {
		System.out.println( "WINDOW CLOSING" );
		
	}

	@Override
	public void windowClosed( WindowEvent event ) {
		if ( this.basePane.renderTimer != null ) {
			this.basePane.renderTimer.stop();
			
		}
		System.out.println( "WINDOW CLOSED" );
		
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
