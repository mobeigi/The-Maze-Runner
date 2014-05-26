import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class InstructionFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton backButton;
	private JPanel instructions;
	private JPanel controls;
	private JFrame frame;

	
	public InstructionFrame (){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		backButton = new JButton("Close");
		instructions = new JPanel(new GridBagLayout());
		controls = new JPanel(new GridBagLayout());

		backButton.addActionListener(this);
		frame.getContentPane().setBackground(Color.WHITE);
		instructions.setBackground(Color.WHITE);
		controls.setBackground(Color.WHITE);
		
		//this.setMinimumSize(new Dimension(600, 600));
		//this.width = width;
		//this.height = height;
		//Set user size
		//this.setSize(width, height);
		//Make size fixed
		frame.setResizable(true);
		
		
		frame.setLayout(new GridBagLayout());
		
		BufferedImage wasd = null;
	    try {
	    	wasd = ImageIO.read(new File("src/sprites/movkeys.jpg"));
	    } catch (IOException e) {
	    	System.out.println("FAIL");
	    }
		
		JLabel sword = new JLabel((new PlayerPanel(MazeFrame.swordSprite,48,48).getPlayerSprite()));
		JLabel player = new JLabel((new PlayerPanel(MazeFrame.playerSprite,48,48).getPlayerSprite()));
		JLabel key = new JLabel((new PlayerPanel(MazeFrame.keySprite,48,48).getPlayerSprite()));
		JLabel coin = new JLabel((new PlayerPanel(MazeFrame.coinSprite,48,48).getPlayerSprite()));
		JLabel enemy = new JLabel((new PlayerPanel(MazeFrame.enemySprite,48,48).getPlayerSprite()));
		JLabel keyboard = new JLabel(new ImageIcon(wasd));
		
		sword.setText("Pick this up to kill the ghosts!");
		player.setText("This is you, nice hat");
		key.setText("Pick this up to open the door at the end of the maze");
		coin.setText("Cash money, Cash money");
		enemy.setText("Game over if you get too close");
		keyboard.setText("(Use the WASD keys to move around)");
		keyboard.setVerticalTextPosition(JLabel.BOTTOM);
		keyboard.setHorizontalTextPosition(JLabel.CENTER);
		
		GridBagConstraints c= new GridBagConstraints();
		c.fill=GridBagConstraints.VERTICAL;
		
		c.gridy=0;
		instructions.add(player,c);
		
		c.gridy = 1;
		
		instructions.add(enemy,c);
		
		c.gridy = 2;
		instructions.add(sword,c);
		
		c.gridy=3;
		instructions.add(key,c);
		
		c.gridy=4;
		instructions.add(coin,c);
		
		c.gridy= 5;
		this.backButton.setEnabled(true);
		instructions.add(backButton,c);
		
		controls.add(keyboard);
		
		c.gridx=0;
		frame.add(instructions);
		
		c.gridx = 1;
		frame.add(controls);
		frame.pack();
		frame.setVisible(false);
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.backButton){
			frame.setVisible(false);
			//mainMenu.setVisible(true);
		}
		
	}




	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}
