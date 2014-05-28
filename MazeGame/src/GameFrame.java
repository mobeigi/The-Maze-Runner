import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class GameFrame extends JFrame implements ActionListener {
	//Private Fields
	private static final long serialVersionUID = 1L;
	private Game g;
	private InstructionFrame instructions;
	private OptionFrame optionFrame;
	
	//Frame components
	JButton playButton = new JButton("Play Game!");
	JButton howButton = new JButton("How to Play");
	JButton exitButton = new JButton("EXIT");
	JButton optionButton = new JButton("Options");
	
	public GameFrame(Game g, int width, int height, InstructionFrame instructions, OptionFrame optionFrame)
	{
		//Set Minimum size
		Dimension minSize = new Dimension(600, 600);
		this.setMinimumSize(minSize);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.g = g;
		
		this.instructions = instructions;
		this.optionFrame = optionFrame;
		
		//Set user size
		this.setSize(width, height);
		
		//Make size fixed
		this.setResizable(false);
		
		//Set layout
		this.setLayout(new GridBagLayout());
		
		//Set background colour
		this.getContentPane().setBackground(Color.WHITE);
		
		//Make the title page
		
		//get images
		ImageIcon link = new ImageIcon(GameFrame.this.getClass().getResource("/sprites/linkImage.gif"));
		JLabel linkImage = new JLabel(link);
		
		ImageIcon title = new ImageIcon(GameFrame.this.getClass().getResource("/sprites/mazerunner.png"));
		JLabel titleImage = new JLabel(title);
		
	    GridBagConstraints c = new GridBagConstraints();
	    
	    c.gridheight = 10;
	    c.gridwidth = 10;
	    c.gridy = 0;
	    c.gridx = 0;
	    this.add(titleImage,c);
	    
		//add image of Link
		c.gridwidth = 4;
		c.gridy = 15;
		c.gridx = 1;
		//c.insets = new Insets(0, 250, 0,0);
		c.fill = GridBagConstraints.CENTER;

	    this.add(linkImage, c);
	    
		c.gridwidth = 1;
		c.gridheight = 1;
		//Add play button
	    c.gridy = 27;
	    c.gridx = 0;
	    Toolkit tk = Toolkit.getDefaultToolkit();
	    int xSize = ((int) tk.getScreenSize().getWidth());
	    System.out.println(xSize);
	    c.insets = new Insets(0, 185, 0,0);
		this.playButton.setBackground(Color.WHITE);
		this.add(playButton,c); 
		this.playButton.addActionListener(this);
		
		//Add how to play button
		c.gridy = 27;
	    c.gridx = 1;
	    
	    c.insets = new Insets(0,0,0,0);
		this.howButton.setBackground(Color.WHITE);
		this.howButton.setEnabled(true);
		this.add(howButton,c);
		this.howButton.addActionListener(this);

		//Add option button
		c.gridy = 27;
		c.gridx = 2;
		this.optionButton.setBackground(Color.WHITE);
		this.add(optionButton,c);		
		this.optionButton.addActionListener(this);
		
		//Add exit button
		c.gridy =27;
	    c.gridx = 3;
		this.exitButton.setBackground(Color.WHITE);
		this.add(exitButton,c);		
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
			//Make maze and mazeframe
			g.createMaze(g.getLevel()); //based on user options
			g.setIsInGame(true);
			g.setIsGameOver(false);
			g.setGameFrameVisible(false);
		}
		else if (e.getSource() == this.howButton) {
			//Input diagJOptionPane.showInputDialog(null, "This is the message", "This is the default text");
			instructions.setVisible(true);
			//g.setGameFrameVisible(false);
			
		}
		else if (e.getSource() == this.optionButton){
			optionFrame.setVisible(true);
		}
		else if (e.getSource() == this.exitButton) {
			System.exit(0);
		}
	}
}
