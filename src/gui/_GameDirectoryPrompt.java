package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import util.SystemInfo;

public class _GameDirectoryPrompt {

	private static String gameDirectory = SystemInfo.getLauncherDir();
	
	public static void create() {
		System.out.println( "[Draconic Launcher][GameDirectoryPrompt][Info] Generating Game Directory Prompt..." );
		
		JFrame gameDirectoryPrompt = new JFrame( "Game Directory" );
		
		JLabel enterGameDirectory = new JLabel();
		enterGameDirectory.setText( "Enter the game install directory:" );
		
		JTextField directoryPrompt = new JTextField( gameDirectory );
		directoryPrompt.setFont( directoryPrompt.getFont().deriveFont( Font.PLAIN, 14f ) );
		directoryPrompt.setColumns( 36 );
		//directoryPrompt.setBounds( 25, 96, 500, 32);
		
		JButton browse = new JButton();
		browse.setText( "Browse" );
		browse.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				
			}
		});
		
		JButton accept = new JButton();
		accept.setText( "Accept" );
		accept.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				
			}
		});
		JButton cancel = new JButton();
		cancel.setText( "Cancel" );
		cancel.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				
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
			);
		layout.setVerticalGroup( layout.createSequentialGroup()
			);
		
		gameDirectoryPrompt.addWindowListener( new WindowListener() {
			public void windowOpened(WindowEvent e) {
				System.out.println( "[Draconic Launcher][LauncherGUI][Info] Successfully created launcher window" );
				
			}
			public void windowClosing(WindowEvent e) {
				System.out.println( "[Draconic Launcher][LauncherGUI][Info] Closing launcher..." );
				
			}
			public void windowClosed(WindowEvent e) {
				System.exit( 0 );
				
			}
			public void windowIconified(WindowEvent e) {
				
			}
			public void windowDeiconified(WindowEvent e) {
				
			}
			public void windowActivated(WindowEvent e) {
				
			}
			public void windowDeactivated(WindowEvent e) {
				
			}});
		
		gameDirectoryPrompt.pack();
		gameDirectoryPrompt.setVisible( true );
		
		
	}
	
}
