import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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


public class InstructionFrame implements ActionListener{

	private JButton backButton;
	private JPanel instructions;
	//private JPanel controls;
	private JFrame frame;
	
	public InstructionFrame () {
		frame = new JFrame();
		frame.setTitle("How to Play");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		backButton = new JButton("Close");
		instructions = new JPanel(new GridBagLayout());

		backButton.addActionListener(this);
		frame.getContentPane().setBackground(Color.WHITE);
		instructions.setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setLayout(new GridBagLayout());
		
		BufferedImage wasd = null;
	    try {
	    	wasd = ImageIO.read(new File("src/sprites/WASD.png"));
	    } catch (IOException e) {
	    	System.out.println("FAIL");
	    }
		
	    BufferedImage htp = null;
	    try {
	    	htp = ImageIO.read(new File("src/sprites/howtoplay2.png"));
	    } catch (IOException e){
	    	System.out.println("FAIL");
	    }
	    
		JLabel sword = new JLabel(new Sprite(MazeFrame.swordSprite,48,48).getPlayerSprite());
		JLabel player = new JLabel(new Sprite(MazeFrame.playerSprite,48,48).getPlayerSprite());
		JLabel key = new JLabel(new Sprite(MazeFrame.keySprite,48,48).getPlayerSprite());
		JLabel coin = new JLabel(new Sprite(MazeFrame.coinSprite,48,48).getPlayerSprite());
		JLabel enemy = new JLabel(new Sprite(MazeFrame.enemySprite,48,48).getPlayerSprite());
		JLabel freeze = new JLabel(new Sprite(MazeFrame.snowflakeSprite, 48, 48).getPlayerSprite());
		JLabel enemyF = new JLabel(new Sprite(MazeFrame.killableEnemySprite, 48, 48).getPlayerSprite());

		JLabel keyboard = new JLabel(new ImageIcon(wasd));
		JLabel howtoplay = new JLabel(new ImageIcon(htp));
		
		sword.setText("Pick this up to kill the ghosts!");
		player.setText("This is you, nice hat");
		key.setText("Pick this up to open the door at the end of the maze");
		coin.setText("Cash money, Cash money");
		enemy.setText("Game over if you get too close");
		enemyF.setText("Chomp them when they're looking scared like this");
		keyboard.setText("Use the WASD keys to move around");
		freeze.setText("Pick this up to freeze and eat your enemies");
		
		GridBagConstraints c= new GridBagConstraints();
		c.fill=GridBagConstraints.VERTICAL;
		
		c.gridy = 0;
		instructions.add(howtoplay, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0,170,0,0);
		c.gridy = 1;
		instructions.add(player,c);
		
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
		//frame.add(controls);
		frame.pack();
		frame.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.backButton){
			frame.setVisible(false);
		}
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}
