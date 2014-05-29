import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * GUI class for the game interface.
 * Responsible for displaying buttons and input fields to allow users to control program flow in order to play games, look at help pages and exit the program.
 * @author Jeremy
 */
public class GameFrame {

	private InstructionFrame instructions;
	private JPanel optionPanel;
	private JFrame frame;
	
	//Frame components
	private JButton playButton;
	private JButton howButton;
	private JButton exitButton;

	
	/** Generates the introductory window of the Game
	 * @param game The game object that contains the details of in game screens and the maze.
	 * @param width Width of the window.
	 * @param height Height of the window.
	 */
	public GameFrame(Game game, int width, int height) {
		frame = new JFrame();
		//Set Minimum size
		Dimension minSize = new Dimension(600, 600);
		frame.setMinimumSize(minSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		final Game g = game;
		
		this.instructions = new InstructionFrame();
		this.optionPanel = new OptionPanel(g);
		
		//Set user size
		frame.setSize(width, height);
		
		//Make size fixed
		frame.setResizable(false);
		
		//Set layout
		frame.setLayout(new GridBagLayout());
		
		//Set background colour
		frame.getContentPane().setBackground(Color.WHITE);
		
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
	    frame.add(titleImage,c);
	    
		//add image of link
		c.gridwidth = 4;
		c.gridheight = 10;
		c.gridy = 15;
		c.gridx =3 ;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,250,0,0);
	    frame.add(linkImage, c);
	    
		c.gridwidth = 1;
		c.gridheight = 1;
		//Add play button
	    c.gridy = 26;
	    c.gridx = 3;
	    c.insets = new Insets(0,235,0,0);
	    playButton = new JButton("Play Game!");
		this.playButton.setBackground(Color.WHITE);
		frame.add(playButton,c); 
		this.playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null,optionPanel,"Choose Name & Character ",
						JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
					g.getPlayer().setName(optionPanel.getName());
					g.createMaze(); //based on user options
					g.setIsInGame(true);
					g.setIsGameOver(false);
					frame.setVisible(false);
				}
			}
		});
		c.insets = new Insets(0,0,0,0);
		//Add how to play button
		c.gridy = 26;
	    c.gridx = 4;
	    howButton = new JButton("How to Play");
		this.howButton.setBackground(Color.WHITE);
		this.howButton.setEnabled(true);
		frame.add(howButton,c);
		this.howButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructions.setVisible(true);
			}
		});

		//Add exit button
		c.gridy =26;
	    c.gridx = 5;
	    exitButton = new JButton("EXIT");
		this.exitButton.setBackground(Color.WHITE);
		frame.add(exitButton,c);		
		this.exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//Pack
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * @return The JFrame
	 */
	public JFrame getFrame() {
		return frame;
	}
}
