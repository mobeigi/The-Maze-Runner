import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class GameFrame extends JFrame implements ActionListener {
	//Private Fields
	private static final long serialVersionUID = 1L;
	private MazeFrame mazeFrame;
	private boolean mazeSet;
	private int width;
	private int height;
	
	//Frame components
	JButton playButton = new JButton("Play Game!");
	JButton howButton = new JButton("How to Play");
	JButton exitButton = new JButton("EXIT");
	
	public GameFrame(int width, int height)
	{
		//Set Minimum size
		Dimension minSize = new Dimension(600, 600);
		this.setMinimumSize(minSize);
		
		this.width = width;
		this.height = height;
		this.mazeSet = false;
		
		//Set user size
		this.setSize(width, height);
		
		//Make size fixed
		this.setResizable(false);
		
		//Set layout
		GridLayout g = new GridLayout(3, 2);
		this.setLayout(g);
		
		//Add play button
		this.playButton.setBackground(Color.LIGHT_GRAY);
		this.add(playButton, BorderLayout.SOUTH); 
		this.playButton.addActionListener(this);
		
		//Add how to play button
		this.howButton.setBackground(Color.LIGHT_GRAY);
		this.howButton.setEnabled(false);
		this.add(howButton, BorderLayout.SOUTH);
		this.howButton.addActionListener(this);

		
		//Add exit button
		this.exitButton.setBackground(Color.LIGHT_GRAY);
		this.add(exitButton, BorderLayout.SOUTH);		
		this.exitButton.addActionListener(this);
		
		//Remove border
		//this.setUndecorated(true);
		
		//Pack
		this.pack();
		this.setVisible(true);
	}

	//Perform actions based on user actions
	public void actionPerformed(ActionEvent e)
	{
		//Detect object who performed action
		if (e.getSource() == this.playButton) {
			//Make maze frame
			this.mazeFrame = new MazeFrame(width, height);
			this.mazeSet = true;
			this.setVisible(false);
		}
		else if (e.getSource() == this.howButton) {
			//Input diagJOptionPane.showInputDialog(null, "This is the message", "This is the default text");
		}
		else if (e.getSource() == this.exitButton) {
			System.exit(0);
		}
	}
	
	public boolean isMazeSet() {
		return this.mazeSet;
	}
	
	public MazeFrame getMazeFrame() {
		return this.mazeFrame;
	}
}
