import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameFrame extends JFrame implements ActionListener {
	//Private Fields
	private static final long serialVersionUID = 1L;
	private Game g;
	private InstructionFrame instructions;
	private JPanel optionPanel;
	
	//Frame components
	private JButton playButton;
	private JButton howButton;
	private JButton exitButton;

	
	public GameFrame(Game g, int width, int height) {
		//Set Minimum size
		Dimension minSize = new Dimension(600, 600);
		this.setMinimumSize(minSize);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.g = g;
		
		this.instructions = new InstructionFrame();
		this.optionPanel = new OptionPanel(g);
		
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
	    
	    c.gridheight = 15;
	    c.gridwidth = 10;
	    c.gridy = 0;
	    c.gridx = 0;
	    this.add(titleImage,c);
	    
		//add image of link
		c.gridwidth = 3;
		c.gridheight = 10;
		c.gridy = 15;
		c.gridx = 6;
		c.fill = GridBagConstraints.HORIZONTAL;

	    this.add(linkImage, c);
	    
		c.gridwidth = 1;
		c.gridheight = 1;
		//Add play button
	    c.gridy = 26;
	    c.gridx = 3;
	    playButton = new JButton("Play Game!");
		this.playButton.setBackground(Color.WHITE);
		this.add(playButton,c); 
		this.playButton.addActionListener(this);
		
		//Add how to play button
		c.gridy = 26;
	    c.gridx = 4;
	    howButton = new JButton("How to Play");
		this.howButton.setBackground(Color.WHITE);
		this.howButton.setEnabled(true);
		this.add(howButton,c);
		this.howButton.addActionListener(this);

		//Add exit button
		c.gridy =26;
	    c.gridx = 5;
	    exitButton = new JButton("EXIT");
		this.exitButton.setBackground(Color.WHITE);
		this.add(exitButton,c);		
		this.exitButton.addActionListener(this);
		
		//Pack
		this.pack();
		this.setVisible(true);
	}

	//Perform actions based on user actions
	public void actionPerformed(ActionEvent e) {
		//Detect object who performed action
		if (e.getSource() == this.playButton) {
			//Make maze and mazeframe
			if (JOptionPane.showConfirmDialog(null,optionPanel,"Choose Name & Character ",
				JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
				g.getPlayer().setName(optionPanel.getName());
				g.createMaze(g.getLevel()); //based on user options
				g.setIsInGame(true);
				g.setIsGameOver(false);
				this.setVisible(false);
			}
		}
		else if (e.getSource() == this.howButton) {
			instructions.setVisible(true);
		}
		else if (e.getSource() == this.exitButton) {
			System.exit(0);
		}
	}
}
