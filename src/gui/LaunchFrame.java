package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

import file.Settings;

public class LaunchFrame extends JFrame implements Runnable, ActionListener, WindowListener {
	
	private static final long serialVersionUID = 1L;
	private static int minWidth = 640;
	private static int minHeight = 360;
	private static int prefWidth;
	private static int prefHeight;
	private static int maxWidth;
	private static int maxHeight;

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
	 * Classes are nested in the same configuration as the panes are of the GUI
	 * TODO: AESTHETICS, AESTHETICS, AESTHETICS!!!
	 */
	
	static class BasePane extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private final Color borderColor = new Color( 0, 0, 0 );

		private InfoPane infoPane;
		private TabsPane tabsPane;
		private UserPane userPane;
		
		public BasePane() {
			//Panes
			this.infoPane = new InfoPane();
			
			this.tabsPane = new TabsPane();
			//this.tabsPane.setBorder( new LineBorder( borderColor, 5 ) );
			
			this.userPane = new UserPane();
			
			//Layout Manager (not present here because it needs to be called separately for the frames to resize correctly
			
		}
		
		public void setLayout( JFrame parent ) {
			GroupLayout gl = new GroupLayout( this );
			this.setLayout( gl );
			
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
		
		//Information Pane - Displays info on whatever is selected within the selection pane or functions as a controls pane for selection pane tabs
		static class InfoPane extends JPanel {
			
			private static final long serialVersionUID = 1L;
			
			public InfoPane() {
				this.setBackground( new Color( 255, 0, 0 ) );
				
			}
			//size stuff //This will be changed to adapt to sizes of other panes, but is constant for now
			
		}
		
		//Tabs Pane - Allows selection of menu and controls what will be visible in the info pane
		static class TabsPane extends JPanel {
			
			private static final long serialVersionUID = 1L;
	
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
		static class UserPane extends JPanel {
			
			private static final long serialVersionUID = 1L;
	
			public UserPane() {
				this.setBackground( new Color( 0, 0, 255 ) );
				
			}
			
			//Profile Pane - Extension of the user pane that allows for profile configuration (essentially personal settings / changing your skin within the launcher)
			static class ProfilePane extends JPanel {
	
				private static final long serialVersionUID = 1L;
				
				//do stuff
				
			}
			
			//do stuff
			
		}
	
	}
	
	@Override
	public void run() {
		//Save settings file
		Settings.settings.write( false );
		
		//Define panels
		BasePane basePane = new BasePane();
		
		//Set layout manager
		this.setLayout( new BorderLayout() );
		
		//Add Components
		this.add( basePane, BorderLayout.CENTER );
		basePane.setLayout( this );
		
		this.setVisible( true ); //'Opens' the frame
		
	}
	
	@Override
	public void actionPerformed( ActionEvent event ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing( WindowEvent event ) {
		System.out.println( "WINDOW CLOSING" );
		
	}

	@Override
	public void windowClosed( WindowEvent event ) {
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
