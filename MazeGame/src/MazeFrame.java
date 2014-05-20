import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class MazeFrame extends JFrame implements ActionListener {
	//Private Fields
	private static final long serialVersionUID = 1L;

	private int height;
	private int width;
	
	private JPanel mazeGrid;
	private JPanel bottomPanel;

	//Frame components
	JButton exitButton;
	
	public MazeFrame(int width, int height)
	{
		this.height = height;
		this.width = width;
		
		mazeGrid = new JPanel();
		bottomPanel = new JPanel();
		exitButton = new JButton("Exit");
		
		exitButton.addActionListener(this);
		
		//Set Minimum size
		Dimension minSize = new Dimension(width*4, height*4);
		//this.setMinimumSize(minSize);
		
		//Make size fixed
		this.setResizable(false);
		
		//Set layout
		GridBagLayout g = new GridBagLayout();
		this.setLayout(g);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Pack
		//this.setUndecorated(true);
		this.pack();
		this.setVisible(true);
	}

	//Perform actions based on user actions
	public void actionPerformed(ActionEvent e)
	{
		//Detect object who performed action
		if (e.getSource() == this.exitButton) {
				System.exit(0);
		}
	}
	
	//Draw
	public void update (Maze m) 
	{
		//Clear panels
		mazeGrid.removeAll();
		bottomPanel.removeAll();
		
		//Make new GridBagLayout for maze itself
		mazeGrid.setLayout(new GridBagLayout());
		
		//Constraints
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		//Fill All blocks
		for (int x = 0; x < width; x++)
		{
			c.gridx = x;
			for (int y = 0; y < height; y++)
			{
				//Get Tile information
				Tile t = m.getTile(x, y);
				
				c.gridy = -y;
				
				//Create new panel for tile
				JPanel blocks = new JPanel();
				blocks.setLayout(new GridLayout(2,2));
				
				JPanel block1 = new JPanel();
				JPanel block2 = new JPanel();
				JPanel block3 = new JPanel();
				JPanel block4 = new JPanel();
				
				Dimension blockSize = new Dimension(48, 48);
				blocks.setPreferredSize(blockSize);
				
				/*
				Dimension innerBlockSize = new Dimension(24, 24);
				
				block1.setPreferredSize(innerBlockSize);
				block2.setPreferredSize(innerBlockSize);
				block3.setPreferredSize(innerBlockSize);
				block4.setPreferredSize(innerBlockSize);
				*/
				
				
				String blockSprite = "";
				JLayeredPane fullblock = new JLayeredPane();
				
				if (m.findPlayer().equals(t)) { //if player here
					blockSprite = "link";
					
					Goku g1 = new Goku(blockSprite);
					JLabel l1 = new JLabel(g1.image);
					
					fullblock.add(l1, 0);
					blockSprite = "wood";
				}
				else if (t.isWalkable()) {
					blockSprite = "wood";
				} else {
					blockSprite = "ques";
				}
				
				Goku g = new Goku(blockSprite);	
				JLabel l = new JLabel(g.image);
				
				fullblock.setPreferredSize(blockSize);
				fullblock.setLayout(new OverlayLayout(fullblock));
				fullblock.add(l, 1);
				
				mazeGrid.add(fullblock, c);
			}
		}
		
		//Add maze to frame
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.CENTER;
		this.add(mazeGrid, c);
		
		
		c.gridx = 0;
		c.gridy = -1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.CENTER;
		
		//Add exit button
		bottomPanel.add(exitButton);
		
		//Add Score
		c.gridy = -2;
		JLabel score = new JLabel("Score: 0");
		bottomPanel.add(score);

		//Add panels to this frame
		this.add(bottomPanel, c);
		
		//repack
		this.pack();
	}
	
}
