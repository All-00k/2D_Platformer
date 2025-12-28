package main;



import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.*;
public class GameWindow  {
	private JFrame jframe;
	
	public GameWindow(GamePanel gamePanel) { //passing object of GamePanel class so that we can use methods of Gamepanel
		jframe = new JFrame();

		
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(gamePanel);
		
		jframe.setResizable(false);
		jframe.pack(); //set the window size to prefered size in the GamePanel class (according to the size of jPanel it will resize the size of JFrame itself)
		jframe.setLocationRelativeTo(null); //make our window(JFrame) spawn in middle
		jframe.setVisible(true); //to make is visible (this should be at the bottom)
		jframe.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				gamePanel.getGame().windowFocusLost();

				
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}

}
