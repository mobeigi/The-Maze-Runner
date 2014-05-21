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
	private JPanel mazeGrid;
	private JPanel bottomPanel;
	private JButton exitButton;

	private Point lastPlayerPos;
	private Dimension blockSize;
	
	private String wallSprite;
	private String areaSprite;
	private String player1Sprite;
	private String doorSprite;
	
	public MazeFrame(int width, int height)
	{
		//Initilisation
		this.height = height + 2; 	//add 2 for border around maze
		this.width = width + 2;
		
		this.mazeGrid = new JPanel();	//panels
		this.bottomPanel = new JPanel();
		this.exitButton = new JButton("Exit");
		this.exitButton.addActionListener(this);
		
		//Interface variables
		this.lastPlayerPos = new Point();
		this.blockSize = new Dimension(48, 48);
		this.wallSprite = "steel_wall";
		this.areaSprite = "grass";
		this.doorSprite = "locked_door";
		this.player1Sprite = "link";
		
		//Make maze take up full screen
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight()); 
		Dimension fullscreen = new Dimension(xSize, ySize);
		this.setPreferredSize(fullscreen);

		//Update state
		this.setExtendedState(Frame.MAXIMIZED_BOTH);  

		
		//Fix window size
		this.setResizable(false);
		
		//Set frame layout
		this.setLayout(new GridBagLayout());

		//Set close operation
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Pack
		this.setUndecorated(true);
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

	//Update only the player position in maze
	public void update(Maze m) 
	{
		if (m.getPlayerLoc().getX() == lastPlayerPos.getX() &&
			m.getPlayerLoc().getY() == lastPlayerPos.getY() )
			return;
			
		
		//Clear botom panel
		bottomPanel.removeAll();
		
		//Create new panel for tile
		Dimension blockSize = new Dimension(48, 48);
		
		//New player pos
		Tile newPos = m.getPlayerLoc();
		
		String blockSprite = "grass";
		JLayeredPane grassblock = new JLayeredPane();
		
		PlayerPanel g = new PlayerPanel(blockSprite);	
		JLabel l = new JLabel(g.getPlayerSprite());
		
		grassblock.setPreferredSize(blockSize);
		grassblock.setLayout(new OverlayLayout(grassblock));
		grassblock.add(l, 1);
		
		int index = (int) (((lastPlayerPos.getY()) * width) + (lastPlayerPos.getX()) );
		
		Component c = mazeGrid.getComponent(index);
		System.out.println(index);
		mazeGrid.remove(index);
		
	
		JLayeredPane playerBlock = new JLayeredPane();
		
		PlayerPanel g1 = new PlayerPanel(blockSprite);	
		JLabel l1 = new JLabel(g1.getPlayerSprite());
		
		playerBlock.setPreferredSize(blockSize);
		playerBlock.setLayout(new OverlayLayout(playerBlock));
		playerBlock.add(l, 1);
		playerBlock.add(l1, 0);
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		mazeGrid.add(grassblock, index);
		
		gbc.gridx = newPos.getX();
		gbc.gridy = -newPos.getY();
		//mazeGrid.add(playerBlock, gbc);
		
		//Set new last position for next update
		this.lastPlayerPos.setLocation(newPos.getX(), newPos.getY());
		
		//Add maze to frame
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.CENTER;
		this.add(mazeGrid, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = -1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.CENTER;
		
		//Add exit button
		bottomPanel.add(exitButton);
		
		//Add Score
		gbc.gridy = -2;
		JLabel score = new JLabel("Score: 0");
		bottomPanel.add(score);

		//Add panels to this frame
		this.add(bottomPanel, gbc);
		
		//Repack
		this.revalidate();
		this.pack();
	}
	
	//Initilise maze GUI and pack it
	public void init(Maze m) 
	{
		//Clear panels
		mazeGrid.removeAll();
		bottomPanel.removeAll();
		
		//Make new GridBagLayout for maze itself
		mazeGrid.setLayout(new GridBagLayout());
		
		//Constraints
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		//Fill All blocks
		for (int x = 0; x < width; x++)
		{
			gbc.gridx = x;
			for (int y = 0; y < height; y++)
			{
				//Get Tile information
				Tile t = m.getTile(x, y);
				
				gbc.gridy = -y;
				
				String blockSprite = "";
				
				//Use JLayeredPane for each block
				JLayeredPane block = new JLayeredPane();
				block.setPreferredSize(blockSize);
				
				//If player here
				if (m.findPlayer().equals(t)) {
					blockSprite = this.player1Sprite;
					
					PlayerPanel playerSprite = new PlayerPanel(blockSprite);
					JLabel playerSpriteImage = new JLabel(playerSprite.getPlayerSprite());
					
					block.add(playerSpriteImage, 0);	//add player sprite with high priority (overlap terrain)
					blockSprite = this.areaSprite;	//set sprite to load below to terrain
					
					this.lastPlayerPos.setLocation(gbc.gridx, -gbc.gridy); //update last player pos
					
				}
				//Check if this is a door
				else if (t.getType() == 4) {
					blockSprite = this.doorSprite;
					
					PlayerPanel playerSprite = new PlayerPanel(blockSprite);
					JLabel playerSpriteImage = new JLabel(playerSprite.getPlayerSprite());
					
					block.add(playerSpriteImage, 0);	//add player sprite with high priority (overlap terrain)
					blockSprite = this.wallSprite;	//set sprite to load below to terrain
				}
				//Else if walkable terrain
				else if (t.getType() == 1) {
					blockSprite = this.areaSprite;
				} 
				//Else must be wall
				else if (t.getType() == 0){
					blockSprite = this.wallSprite;
				}
				
				//Create sprite for block
				PlayerPanel sprite = new PlayerPanel(blockSprite);	
				JLabel spriteImage = new JLabel(sprite.getPlayerSprite());
				
				block.setPreferredSize(blockSize);
				block.setLayout(new OverlayLayout(block));
				block.add(spriteImage, 1);
				
				mazeGrid.add(block, gbc);
			}
		}
		
		//Add maze to frame
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.CENTER;
		this.add(mazeGrid, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = -1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.CENTER;
		
		//Add exit button
		bottomPanel.add(exitButton);
		
		//Add Score
		gbc.gridy = -2;
		JLabel score = new JLabel("Score: 0");
		bottomPanel.add(score);

		//Add panels to this frame
		this.add(bottomPanel, gbc);
		
		//Repack
		this.pack();
	}
	
}
