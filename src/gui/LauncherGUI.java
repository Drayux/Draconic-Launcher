package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.swing.*;

import file.LauncherFile;
import file.Settings;
import json.ParseFromJson;
import json.ParseToJson;
import util.SystemInfo;

/*
 * Creates launcher window and GUI elements, called by ...actually, update this later... and put this thing on all the classes ;)
 */

//not serialized, I know... I'll do that when I understand what it does
public class LauncherGUI extends JFrame {
	
	static int WIDTH;
	static int HEIGTH;
	private static String gameDirectory = SystemInfo.getLauncherDir();
	
	private LauncherGUI( String title ) {
		super( title );
		
	}
	
	public static void createMainGUI( String title ) {
		System.out.println( "[Draconic Launcher][LauncherGUI][Info] Creating launcher window..." );
		
		/*
		 * Used for returning user's screen size, and create the window accordingly
		 * Window will be no more than 800 pixels or 3/4ths of the monitor size
		 */
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if ( screenSize.width > 1066 ) {
			WIDTH = 800;
		}
		else {
			WIDTH = ( screenSize.width / 4 ) * 3;
		}
		
		Dimension windowSize = new Dimension();
		windowSize.setSize( WIDTH, ( WIDTH / 4 ) * 3 );

		//FRAMES
		//JFrame launcherWindow = new JFrame( title );
		LauncherGUI launcherWindow = new LauncherGUI( title );

		//PANELS
		JPanel login = new JPanel();
		JPanel actions = new JPanel();
		JPanel settings = new JPanel();
		JPanel gameOutput = new JPanel();
		JPanel modpackSelect = new JPanel();
		JPanel modpackInfo = new JPanel();
		JPanel toolbar = new JPanel();
		JPanel background = new JPanel();
		
		//BUTTONS
		/* Google GSON Testing
		JButton jsonTestButton = new JButton( "Print the json test" );
		jsonTestButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) { 
				ParseToJson.testjson( null, 69.0 );
				try {
					ParseFromJson.testjsonFile( "test.txt" );
					
				} 
				catch ( IOException exception ) {
					System.out.println( "[Draconic Launcher][LauncherGUI][Warn] IOException occurred reading test.txt from file." );
					
				}

			}
		}); */
		
		//launcherWindow.setIconImage(image);
		launcherWindow.setSize( windowSize );
		//launcherWindow.setResizable(false);
		launcherWindow.setMinimumSize( windowSize );
		launcherWindow.setLocationRelativeTo( null );
		launcherWindow.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		
		launcherWindow.add( login );
		
		launcherWindow.add( actions );
		
		launcherWindow.add( settings );
		
		background.setBackground(Color.DARK_GRAY);
		//background.add( jsonTestButton );
		launcherWindow.add( background );
		
		/* A useless test
		JTextArea displayTest = new JTextArea();
		displayTest.setText("test");
		JScrollPane scrollPane = new JScrollPane(displayTest);
        scrollPane.setPreferredSize(new Dimension(500, 450));
        launcherWindow.getContentPane().add(scrollPane, BorderLayout.CENTER); */
		
		launcherWindow.addWindowListener( new WindowListener() {
			//Checks for window events
			public void windowOpened( WindowEvent e ) {
				System.out.println( "[Draconic Launcher][LauncherGUI][Info] Successfully created launcher window" );
				
			}
			public void windowClosing( WindowEvent e ) {
				//System.out.println( "[Draconic Launcher][LauncherGUI][Info] Closing launcher..." );
				
			}
			public void windowClosed( WindowEvent e ) {
				System.out.println( "[Draconic Launcher][LauncherGUI][Info] Saving settings..." );
				try {
					Settings.settings.write( false );
					
				} 
				catch ( IOException exception ) {
					exception.printStackTrace();
					
				}
				//System.out.println( "[Draconic Launcher][LauncherGUI][Info] Settings saved" );
				
				System.out.println( "[Draconic Launcher][LauncherGUI][Info] Closing launcher..." );
				System.exit( 0 );
				
			}
			public void windowIconified( WindowEvent e ) {
				//System.out.println( "ICONIFIED" );
				
			}
			public void windowDeiconified( WindowEvent e ) {
				//System.out.println( "DEICONIFIED" );
				
			}
			public void windowActivated( WindowEvent e ) {
				//System.out.println( "ACTIVATED" );
				
			}
			public void windowDeactivated( WindowEvent e ) {
				//System.out.println( "DEACTIVATED" );
				
			}
		});
		
		launcherWindow.setVisible( true );
		
	}
	
	public static void createGameDirectoryPrompt() {
		System.out.println( "[Draconic Launcher][LauncherGUI][Info] Generating game directory prompt..." );
		
		LauncherGUI gameDirectoryPrompt = new LauncherGUI( "Game Directory" );
		
		JLabel enterGameDirectory = new JLabel();
		enterGameDirectory.setText( "Enter the game install directory:" );
		
		JTextField directoryPrompt = new JTextField( gameDirectory );
		directoryPrompt.setFont( directoryPrompt.getFont().deriveFont( Font.PLAIN, 14f ) );
		directoryPrompt.setColumns( 36 );
		//directoryPrompt.setBounds( 25, 96, 500, 32);
		
		JButton browse = new JButton();
		browse.setText( "Browse" );
		//button format testing...
		//browse.setFocusPainted( false );
		browse.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				System.out.println( "[Draconic Launcher][LauncherGUI][Info] Generating browse window..." );
				
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
				
				if ( chooser.showOpenDialog( gameDirectoryPrompt ) == JFileChooser.APPROVE_OPTION ) {
					directoryPrompt.setText( chooser.getSelectedFile().getAbsolutePath() );
					
				}
				
			}
		});
		
		JButton accept = new JButton();
		accept.setText( "Accept" );
		//button format testing...
		//accept.setBackground( new Color( 255, 32, 32 ) );
		accept.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				if ( LauncherFile.verifyDirectoryPermissions( directoryPrompt.getText() ) ) {
					System.out.println( "[Draconic Launcher][LauncherGUI][Info] Directory successfully verified" );
					
					Settings.settings.gameDirectory = directoryPrompt.getText();
					
					gameDirectoryPrompt.dispose();
					
				}
				
			}
		});
		JButton cancel = new JButton();
		cancel.setText( "Cancel" );
		//button format testing...
		//cancel.setBorder( null );
		cancel.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				Settings.settings.gameDirectory = SystemInfo.getLauncherDir();
				
				gameDirectoryPrompt.dispose();
				
			}
		});
		
		JLabel separator = new JLabel();
		separator.setText( null );
		
		//launcherWindow.setIconImage(image);
		gameDirectoryPrompt.setSize( 550, 250 );
		gameDirectoryPrompt.setResizable( false );
		gameDirectoryPrompt.setLocationRelativeTo( null );
		gameDirectoryPrompt.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		
		GroupLayout layout = new GroupLayout( gameDirectoryPrompt.getContentPane() );
		gameDirectoryPrompt.getContentPane().setLayout( layout );
		
		layout.setAutoCreateGaps( false );
		layout.setAutoCreateContainerGaps( true );
		/* OLD Layout w/out gaps
		layout.setHorizontalGroup( layout.createSequentialGroup()
			.addGroup( layout.createParallelGroup()
				.addGroup( layout.createSequentialGroup()
					.addGroup( layout.createParallelGroup()
						.addComponent( enterGameDirectory )
						.addComponent( directoryPrompt )
						)
					.addComponent( browse )
					)
				.addComponent( separator )
				.addGroup( GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
					.addComponent( accept )
					.addComponent( cancel )
					)
				)		
			);
		layout.setVerticalGroup( layout.createSequentialGroup()
			.addComponent( enterGameDirectory )
			.addGroup( layout.createParallelGroup()
				.addComponent( directoryPrompt )
				.addComponent( browse )
				)
			.addComponent( separator )
			.addGroup( layout.createParallelGroup()
				.addComponent( accept )
				.addComponent( cancel )
				)
			);
			*/
		layout.setHorizontalGroup( layout.createSequentialGroup()
			.addGroup( layout.createParallelGroup()
				.addComponent( enterGameDirectory )
				.addComponent( directoryPrompt )
				.addComponent( accept, GroupLayout.Alignment.TRAILING )
				)
			.addPreferredGap( LayoutStyle.ComponentPlacement.RELATED )
			.addGroup( layout.createParallelGroup()
				.addComponent( browse )
				.addComponent( cancel )
				)
			);
		layout.setVerticalGroup( layout.createSequentialGroup()
			.addComponent( enterGameDirectory )
			.addPreferredGap( LayoutStyle.ComponentPlacement.RELATED )
			.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE )
				.addComponent( directoryPrompt, 28, 28, 28 )
				.addComponent( browse, 27, 27, 27 )
				)
			.addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED )
			.addGroup( layout.createParallelGroup()
				.addComponent( accept )
				.addComponent( cancel )
				)
			);
		layout.linkSize( SwingConstants.HORIZONTAL, accept, cancel, browse );
		layout.linkSize( SwingConstants.VERTICAL, accept, cancel, browse );
		
		gameDirectoryPrompt.addWindowListener( new WindowListener() {
			public void windowOpened( WindowEvent e ) {
				System.out.println( "[Draconic Launcher][LauncherGUI][Info] Successfully created prompt" );
				
			}
			//Called on press of X button, but NOT on gameDirectoryPrompt.dispose()
			public void windowClosing( WindowEvent e ) {
				Settings.settings.gameDirectory = null;
				
				System.out.println( "[Draconic Launcher][LauncherGUI][Info] Closing launcher..." );
				System.exit( 0 );
				
			}
			//Always called upon close, skipped on X because of System.exit(0);
			public void windowClosed( WindowEvent e ) {
				System.out.println( "Set game directory as: " + Settings.settings.gameDirectory );
				
				System.out.println( "[Draconic Launcher][LauncherGUI][Info] Saving settings..." );
				try {
					Settings.settings.write( false );
					
				} 
				catch ( IOException exception ) {
					exception.printStackTrace();
					
				}
				//System.out.println( "[Draconic Launcher][LauncherGUI][Info] Settings saved" );
				
				createMainGUI( "Draconic Launcher" );
				
			}
			public void windowIconified( WindowEvent e ) {}
			public void windowDeiconified( WindowEvent e ) {}
			public void windowActivated( WindowEvent e ) {}
			public void windowDeactivated( WindowEvent e ) {}
			});
		
		gameDirectoryPrompt.pack();
		gameDirectoryPrompt.setAlwaysOnTop( true );
		gameDirectoryPrompt.getRootPane().setDefaultButton( accept );
		gameDirectoryPrompt.setVisible( true );
		
	}
	
}
