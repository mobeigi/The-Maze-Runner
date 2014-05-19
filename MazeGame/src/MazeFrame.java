import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class MazeFrame extends JFrame implements ActionListener {
	//Private Fields
	private static final long serialVersionUID = 1L;

	private int height;
	private int width;

	//Frame components
	JButton exitButton = new JButton("Exit");
	
	public MazeFrame(int width, int height)
	{
		this.height = height;
		this.width = width;
		
		exitButton.addActionListener(this);
		
		//Set Minimum size
		Dimension minSize = new Dimension(width, height);
		this.setMinimumSize(minSize);
		
		//Set user size
		this.setSize(width, height);
		
		//Make size fixed
		this.setResizable(false);
		
		//Set layout
		GridBagLayout g = new GridBagLayout();
		this.setLayout(g);

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
	public void draw (Maze m) 
	{
		//Constraints
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		//Fill All blocks
		for (int x = 0; x <= width; x++)
		{
			c.gridx = x;
			for (int y = 0; y <= height; y++)
			{
				//Get Tile information
				Tile t = m.getTile(x, y);
				
				c.gridy = y;
				
				//Create new panel for tile
				JPanel newEntry = new JPanel();
				
				if (m.findPlayer().equals(t)) { //if player here
					newEntry.setBackground(Color.BLUE);
				}
				else if (t.isWalkable()) {	//if walkable area
					newEntry.setBackground(Color.ORANGE);
				} else {	//if wall
					newEntry.setBackground(Color.BLACK);	
				}
				
				this.add(newEntry, c);
			}
		}
		
		c.gridx = width / 2;
		c.gridy = -(height + 1);
		c.gridwidth = 5;
		c.weightx = 2;
		c.weighty = 2;
		c.fill = GridBagConstraints.SOUTH;
		
		//Add exit button
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(exitButton);
		
		//Add Score
		c.gridy = -(height + 2);
		JLabel score = new JLabel("Score: 0");
		bottomPanel.add(score);
		
		this.add(bottomPanel, c);
		
		//Pack
		this.pack();
		this.revalidate();
		this.setVisible(true);
	}
	
}
