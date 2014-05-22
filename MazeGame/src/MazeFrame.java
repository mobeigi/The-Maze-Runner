import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class MazeFrame extends JFrame implements ActionListener {
	//Private Fields
	private static final long serialVersionUID = 1L;

	private int height;
	private int width;
	
	private Game g;
	
	//Frame components
	private JPanel mazeGrid;
	private JPanel sidePanel;
	private JButton exitButton;

	private Point lastPlayerPos;
	private Dimension blockSize;
	
	private String wallSprite;
	private String areaSprite;
	private String playerSprite;
	private String doorSprite;
	private String keySprite;
	private String coinSprite;
	
	public MazeFrame(Game g, int width, int height)
	{
		//Initilisation
		this.height = height + 2; 	//add 2 for border around maze
		this.width = width + 2;
		this.g = g;
		
		this.mazeGrid = new JPanel();	//panels
		this.sidePanel = new JPanel();
		this.exitButton = new JButton("Exit");
		this.exitButton.addActionListener(this);
		
		//Interface variables
		this.lastPlayerPos = new Point();
		this.blockSize = new Dimension(48, 48);
		this.wallSprite = "steel_wall";
		this.areaSprite = "grass";
		this.doorSprite = "locked_door";
		this.playerSprite = "link";
		this.keySprite = "key";
		this.coinSprite = "coin";
		
		//Initilise side panel looks
		this.sidePanel.setPreferredSize(new Dimension( (int) ((this.width * blockSize.getWidth()) *0.4) ,	//side panel is based on mazes size, width is 40% of maze width 
														(int) (this.height * blockSize.getHeight()))); //height is matched exactly
		this.sidePanel.setBackground(new Color(240, 240, 240));
		this.sidePanel.setLayout(new GridBagLayout());
		this.sidePanel.setBorder(new LineBorder(Color.black, 2));
		
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
			int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit to the main menu?\n\nAll game progress will be lost.","Exit Warning", JOptionPane.YES_NO_OPTION);
			
			//If user wishes to quit
			if (dialogResult == JOptionPane.YES_OPTION) {
				g.setIsGameOver(true);
				g.setIsInGame(false);
			} else {
				this.requestFocus();	//request focus again
			}
		}
	}

	//Update only the player position in maze
	public void update(Maze m) 
	{
		/*
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
		*/
	}
	
	//Initilise maze GUI and pack it
	public void init(Maze m) 
	{
		//Clear panels
		mazeGrid.removeAll();
		sidePanel.removeAll();
		
		//Make new GridBagLayout for maze itself
		mazeGrid.setLayout(new GridBagLayout());
		
		//Constraints
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		//Fill All blocks
		for (int x = 0; x < width; x++)
		{
			gbc.gridx = x;	//update grid x pos
			for (int y = 0; y < height; y++)
			{
		
				gbc.gridy = -y; //update grid y pos
				
				//Get information about current tile
				Tile t = m.getTile(x, y);
				
				//Use JLayeredPane for each block
				JLayeredPane block = new JLayeredPane();
				
				//Set size and layout
				block.setPreferredSize(blockSize);
				block.setLayout(new OverlayLayout(block));
				
				String blockSprite = "";	//base (bottom) block sprite
				String overLaySprite = "";	//Overlay sprite that goes on top of block sprite
				
				//Determine block graphics based on type of tile
				//If player is at this tile
				if (m.findPlayer().equals(t)) {
					blockSprite = this.areaSprite;
					overLaySprite = this.playerSprite;
				}
				//Check if this is a door
				else if (t.getType() == Tile.DOOR) {
					blockSprite = this.wallSprite;
					overLaySprite = this.doorSprite;
				}
				//Check for key
				else if (t.getType() == Tile.KEY) {
					blockSprite = this.areaSprite;
					overLaySprite = this.keySprite;
				}
				else if (t.getType() == Tile.TREASURE) {
					blockSprite = this.areaSprite;
					overLaySprite = this.coinSprite;
				}
				//Else if walkable path
				else if (t.getType() == Tile.PATH) {
					blockSprite = this.areaSprite;
				} 
				//Else must be wall
				else if (t.getType() == Tile.WALL){
					blockSprite = this.wallSprite;
				}

				//Always add block sprite
				PlayerPanel sprite = new PlayerPanel(blockSprite);	
				JLabel spriteImage = new JLabel(sprite.getPlayerSprite());
				block.add(spriteImage, 1);
				
				//Add overlay sprite if required
				if (overLaySprite != "") {
					PlayerPanel overlaySprite = new PlayerPanel(overLaySprite);	
					JLabel overlayImage = new JLabel(overlaySprite.getPlayerSprite());
					block.add(overlayImage, 0);
				}
				
				//Add block as hole to maze
				mazeGrid.add(block, gbc);
			}
		}
		
		//Add maze to this frame
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.CENTER;
		this.add(mazeGrid, gbc);
		
		//Create SidePanel components using gridbaglayout 
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		//Add Score Panel
		JPanel scorePanel = new JPanel(new GridLayout(2,1));
		scorePanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		
		//Add player image
		PlayerPanel player = new PlayerPanel(this.playerSprite);	
		JLabel playerImage = new JLabel(player.getPlayerSprite());
		playerImage.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		scorePanel.add(playerImage);
		
		//Add Score for player
		JPanel playerScore = new JPanel(new GridLayout(3,1));
		JLabel name = new JLabel("Name: " + g.getPlayer().getName());
		JLabel character = new JLabel("Character: " + g.getPlayer().getCharacter().substring(0, 1).toUpperCase() + g.getPlayer().getCharacter().substring(1));
		JLabel score = new JLabel("Score: 9001");
		playerScore.add(name);
		playerScore.add(character);
		playerScore.add(score);
		
		scorePanel.add(playerScore);
		
		sidePanel.add(scorePanel);
		
		gbc.gridx = 0;
		gbc.gridy = -2;
		
		//Add exit button at very bottom
		exitButton.setMargin(new Insets(5, 10, 5, 10));
		exitButton.setToolTipText("Click here to exit to main menu.");
		sidePanel.add(exitButton, gbc);

		
		//Add sidePanel to this frame
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(sidePanel, gbc);
		
		//Repack frame
		this.pack();
	}
	
}
