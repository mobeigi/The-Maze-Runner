import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * The Instruction Frame contains the instructions of the game for the user
 * @author Jeremy Ma
 */
public class InstructionFrame {

	private JButton backButton;
	private JPanel instructions;
	private JFrame frame;
	
	/**
	 * Constructor of the Instruction Frame.
	 * Sets the title, creates all the buttons, loads all the sprites and their relevant
	 * instructions and packed using GridBagLayout.
	 */
	public InstructionFrame () {
		frame = new JFrame();	//setup frame
		frame.setTitle("How to Play");		//add title
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backButton = new JButton("Close");	//add close button
		instructions = new JPanel(new GridBagLayout());

		backButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);	//set frame to not visible when close button is pressed
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		instructions.setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setLayout(new GridBagLayout());
		
		//Set up images in the instruction frame
		BufferedImage wasd = null;
	   try {
	    	wasd = ImageIO.read(this.getClass().getResource("/sprites/arrow.png"));
	    } catch (IOException e) {
	    	System.err.println("Failed to read sprite.");
	    }
		
	    BufferedImage htp = null;
	    try {
	    	htp = ImageIO.read(this.getClass().getResource("/sprites/howtoplay2.png"));
	    } catch (IOException e){
	    	System.err.println("Failed to read sprite.");
	    }
	    //set up JLabels for the images displayed on the instruction screen
		JLabel sword = new JLabel(new Sprite(MazeFrame.swordSprite,48,48).getSprite());
		JLabel key = new JLabel(new Sprite(MazeFrame.keySprite,48,48).getSprite());
		JLabel coin = new JLabel(new Sprite(MazeFrame.coinSprite,48,48).getSprite());
		JLabel enemy = new JLabel(new Sprite(MazeFrame.enemySprite,48,48).getSprite());
		JLabel freeze = new JLabel(new Sprite(MazeFrame.snowflakeSprite, 48, 48).getSprite());
		JLabel enemyF = new JLabel(new Sprite(MazeFrame.killableEnemySprite, 48, 48).getSprite());

		JLabel keyboard = new JLabel(new ImageIcon(wasd));
		JLabel howtoplay = new JLabel(new ImageIcon(htp));
		
		//text for the tutorial
		sword.setText("Pick this up to kill the ghosts!");
		key.setText("Pick this up to open the door at the end of the maze");
		coin.setText("Cash money, Cash money");
		enemy.setText("Game over if you get too close");
		enemyF.setText("Chomp them when they're looking scared like this");
		keyboard.setText("Use the arrow keys to move around");
		freeze.setText("Pick this up to freeze and eat your enemies");
		
		//position the components (text and image0
		GridBagConstraints c= new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		
		c.gridy = 0;
		instructions.add(howtoplay, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0,170,0,0);
		
		c.gridy = 2;
		instructions.add(enemy,c);
		
		c.gridy = 3;
		instructions.add(enemyF,c);
		
		c.gridx = 0;
		c.gridy = 4;
		instructions.add(sword,c);
		
		c.gridy= 5;
		instructions.add(freeze, c);
		
		c.gridy = 6;
		instructions.add(key,c);
		
		c.gridy= 7;
		instructions.add(coin,c);
		
		c.insets = new Insets(0,120,0,0);
		c.gridy = 8;
		instructions.add(keyboard, c);
		
		c.gridy= 9;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,0,0,0);
		this.backButton.setEnabled(true);
		instructions.add(backButton,c);
		
		c.gridx= 0;
		frame.add(instructions);

		c.gridy = 1;
		frame.pack();
		frame.setVisible(false);
	}

	/**
	 * Sets whether the window/frame appears or not.
	 * @param b true to be visible, false otherwise.
	 */
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}
