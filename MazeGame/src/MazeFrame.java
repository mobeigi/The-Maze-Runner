import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

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
	private JPanel mazeGrid;	//panel where maze grid is placed in
	private JLayeredPane[][] mazeGridComp;	//allows access to each tile in the grid
	
	private JLabel score;	//the score of the player
	private JPanel sidePanel;
	private JButton exitButton;
	private JLabel inventorySword;
	private JLabel inventoryKey;
	private JLabel inventoryIcePower;
	
	private Tile lastPlayerPos;
	private Tile[] lastEnemyPos;
	private Dimension blockSize;	//the size of each tile in the maze grid
	
	//Store sprites once
	private HashMap<String, PlayerPanel> sprites;
	

	public static String wallSprite = "steel_wall";
	public static String pathSprite = "carpet";
	public static String doorSprite = "locked_door";
	public static String playerSprite = "link";
	public static String keySprite = "key";
	public static String coinSprite = "coin";
	public static String enemySprite = "dead_pacman_monster";
	public static String killableEnemySprite = "cyan_pacman_monster";
	public static String swordSprite = "sword";
	public static String snowflakeSprite = "snowflake";
	
	public MazeFrame(Game g, int width, int height)
	{
		//Initialisation
		this.height = height+2; 	//add 2 for border around maze
		this.width = width+2;
		this.g = g;
		
		this.mazeGrid = new JPanel();
		this.mazeGridComp = new JLayeredPane[this.width][this.height];
		this.sidePanel = new JPanel();
		this.exitButton = new JButton("Exit");
		this.exitButton.addActionListener(this);
		
		//Make maze take up full screen
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight()); 
		Dimension fullscreen = new Dimension(xSize, ySize);
		this.setPreferredSize(fullscreen);
		
		//Initialise character spites
		//block is made into a square size
		//width of screen is normally shorter than height
		//so block should always fit on screen
		this.blockSize = new Dimension((int)((xSize*0.6/this.width)), (int)((xSize*0.6/this.width)));
		this.sprites = new HashMap<String, PlayerPanel>();
		
		int x = (int)blockSize.getWidth();
		int y = (int)blockSize.getHeight();
		
		//Add sprites to hashmap
		//size of each sprite is determined by x and y,
		//which is the size of each block on the maze grid
		sprites.put(wallSprite, new PlayerPanel(wallSprite,x,y));
		sprites.put(pathSprite, new PlayerPanel(pathSprite,x,y));
		sprites.put(playerSprite, new PlayerPanel(playerSprite,x,y));
		sprites.put(doorSprite, new PlayerPanel(doorSprite,x,y));
		sprites.put(keySprite, new PlayerPanel(keySprite,x,y));
		sprites.put(enemySprite, new PlayerPanel(enemySprite,x,y));
		sprites.put(coinSprite, new PlayerPanel(coinSprite,x,y));
		sprites.put(swordSprite, new PlayerPanel(swordSprite,x,y));
		sprites.put(snowflakeSprite, new PlayerPanel(snowflakeSprite,x,y));
		sprites.put(killableEnemySprite, new PlayerPanel(killableEnemySprite,x,y));
		
		//Initialise side panel looks
		//this.sidePanel.setPreferredSize(new Dimension( (int) ((this.width * blockSize.getWidth()) *0.4) ,	//side panel is based on mazes size, width is 40% of maze width 
		//												(int) (this.height * blockSize.getHeight()))); //height is matched exactly
		this.sidePanel.setBackground(new Color(240, 240, 240));
		this.sidePanel.setLayout(new GridBagLayout());
		this.sidePanel.setBorder(new LineBorder(Color.WHITE, 2));
		
		//initialise inventoryPanel
		//this.inventoryPanel = new JPanel(new GridLayout(2,1));
		//inventoryPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		
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
			int dialogResult = JOptionPane.showConfirmDialog (this, "Are you sure you want to exit to the main menu?\n\nAll game progress will be lost.","Exit Warning", JOptionPane.YES_NO_OPTION);
			
			//If user wishes to quit
			if (dialogResult == JOptionPane.YES_OPTION) {
				g.setIsGameOver(true);
				g.setIsInGame(false);
			} else {
				this.requestFocus();	//request focus again
			}
		}
	}

	//updates all changed blocks including player, enemy and unlocked door
	public void update(Maze m) 
	{
		Tile curPlayerPos = m.getPlayerTile();
		
		//if player is not dead or if he just died, update its position
		if (!m.playerDied() || lastPlayerPos != null) {
			updateBlock(m, lastPlayerPos);
			if (!m.playerDied()) {
				updateBlock(m, curPlayerPos);
			}
			lastPlayerPos = curPlayerPos;
			score.setText("Score: " + Integer.toString(g.getScore())); //update score
			// Add things to inventory
			if (m.swordCollected()){
				inventorySword.setVisible(true);
			}
			if (m.keyCollected()){
				inventoryKey.setVisible(true);
			}
			if (m.icePowerCollected()){
				inventoryIcePower.setVisible(true);
			} else {
				inventoryIcePower.setVisible(false);	//disappears according to maze settings (after 5 seconds)
			}
			
			if (m.checkReachedEnd()) {	//unlock door if the player has reached the end with the key
				updateBlock(m,m.getDestDoor());
			} else if (m.exitedMaze()) {
				Object[] options = {"Next level"};
				int dialogResult = JOptionPane.showOptionDialog (this, "The next journey awaits you...\n" +
									"What unknown challenges lay ahead?","Room " + g.getLevel() + " cleared!", 
									1,0,new ImageIcon(this.getClass().getResource("/sprites/door_open.gif")),options,0);
				//when user clicks the exit button
				if (dialogResult == 0) {
					//do nothing for now, change so that next level is reached
				} else {
					this.requestFocus();	//request focus again
				}
			}
		} else {
			Object[] options = {"End campaign"};
			int dialogResult = JOptionPane.showOptionDialog (this, "Dead Pacman monster killed you!","OH NO!", 
								1,0,new ImageIcon(this.getClass().getResource("/sprites/" + enemySprite + ".gif")),options,0);
			//when user clicks the exit button
			if (dialogResult == 0) {
				g.setIsGameOver(true);
				g.setIsInGame(false);
			} else {
				this.requestFocus();	//request focus again
			}
		}
		
		for (int i = 0; i < m.getNumEnemies(); i++) {
			Tile curEnemyPos = m.getEnemyTile(i);
			if (!m.enemyDied(i)) {	//if enemy is not dead, update its position
				updateBlock(m, lastEnemyPos[i]);
				updateBlock(m, curEnemyPos);
				lastEnemyPos[i] = curEnemyPos;
			} else if (lastEnemyPos[i] != null) {
				updateBlock(m, lastEnemyPos[i]);
				/*Object[] options = {"Continue campaign"};
				int dialogResult = JOptionPane.showOptionDialog (this, "You destroyed the Pacman monster!","HOORAH!", 
									1,0,new ImageIcon(this.getClass().getResource("/sprites/" + playerSprite + ".gif")),options,0);
				//when user clicks the exit button
				if (dialogResult == 0) {
					//do nothing
				} else {
					this.requestFocus();	//request focus again
				}*/		//removed dialogue box pop-up
				lastEnemyPos[i] = null;
			}
		}
	}
	
	private void updateBlock(Maze m, Tile old)
	{	
		mazeGridComp[old.getX()][old.getY()].removeAll();
		
		String blockSprite = pathSprite;	//base (bottom) block sprite, default is path
		String overLaySprite = "";	//Overlay sprite that goes on top of block sprite
		
		boolean enemyTile = false;
		//Determine block graphics based on type of tile
		//If player is at this tile
		if (m.getPlayerTile() != null && m.getPlayerTile().equals(old)) {
			overLaySprite = g.getPlayer().getCharacter();
		}
		//Check if enemy unit
		else if (!m.allEnemyDied()) {
			for (int i = 0; i < m.getNumEnemies(); i++) {
				if (m.getEnemyTile(i) != null && m.getEnemyTile(i).equals(old)) {
					if (m.icePowerCollected()) {
						overLaySprite = killableEnemySprite;
					} else {
						overLaySprite = enemySprite;
					}
					enemyTile = true;
					break;
				}
			}
		}
		if (!enemyTile) {
			//Check if this is a door
			if (old.getType() == Tile.DOOR) {
				blockSprite = wallSprite;
				overLaySprite = doorSprite;
			}
			//Check for key
			else if (old.getType() == Tile.KEY) {
				overLaySprite = keySprite;
			}
			else if (old.getType() == Tile.TREASURE) {
				overLaySprite = coinSprite;
			}
			else if (old.getType() == Tile.SWORD){
				overLaySprite = swordSprite;
			}
			else if (old.getType() == Tile.ICE_POWER) {
				overLaySprite = snowflakeSprite;
			}
			//Else must be wall
			else if (old.getType() == Tile.WALL){
				blockSprite = wallSprite;
			} else {	//Default is path
				//do nothing
			}
		}
		
		//Always add block sprite
		JLabel spriteImage = new JLabel(sprites.get(blockSprite).getPlayerSprite());
		this.mazeGridComp[old.getX()][old.getY()].add(spriteImage, 1);
		
		//Add overlay sprite if required
		if (overLaySprite != "") {
			JLabel overlayImage = new JLabel(sprites.get(overLaySprite).getPlayerSprite());
			this.mazeGridComp[old.getX()][old.getY()].add(overlayImage, 0);
		}
		
		this.pack();
	}
	
	//Initilise maze GUI and pack it
	public void init(Maze m) 
	{
		lastEnemyPos = new Tile[m.getNumEnemies()];	//make new enemy
		for (int i = 0; i < m.getNumEnemies(); i++) {
			lastEnemyPos[i] = new Tile();
		}
		//Clear panels
		mazeGrid.removeAll();
		sidePanel.removeAll();
		
		//Make new GridBagLayout for maze itself
		mazeGrid.setLayout(new GridLayout(width, height));
		
		//Constraints
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		//Fill All blocks
		for (int y = 0; y < height; y++)
		{
			gbc.gridy = y;	//update grid y pos
			for (int x = 0; x < width; x++)
			{
				gbc.gridx = x; //update grid x pos
				
				//Get information about current tile
				Tile t = m.getTile(x, y);
				
				//Use JLayeredPane for each block
				JLayeredPane block = new JLayeredPane();
				this.mazeGridComp[x][y] = block;	//set mazegrid to represent this block
				
				//Set size and layout
				block.setPreferredSize(blockSize);
				block.setLayout(new OverlayLayout(block));
				
				String blockSprite = pathSprite;	//base (bottom) block sprite
				String overLaySprite = "";	//Overlay sprite that goes on top of block sprite
				
				boolean enemyTile = false;
				//Determine block graphics based on type of tile
				//If player is at this tile
				if (m.getPlayerTile() != null && m.getPlayerTile().equals(t)) {
					overLaySprite = playerSprite;
					this.lastPlayerPos = t; //update last position
				}
				//Check if enemy unit
				else if (!m.allEnemyDied()) {
					for (int i = 0; i < m.getNumEnemies(); i++) {
						if (m.getEnemyTile(i) != null && m.getEnemyTile(i).equals(t)) {
							overLaySprite = enemySprite;
							this.lastEnemyPos[i] = t;
							enemyTile = true;
							break;
						}
					}
				}
				if (!enemyTile) {
					//Check if this is a door
					if (t.getType() == Tile.DOOR) {
						blockSprite = wallSprite;
						overLaySprite = doorSprite;
					}
					//Check for key
					else if (t.getType() == Tile.KEY) {
						overLaySprite = keySprite;
					}
					else if (t.getType() == Tile.TREASURE) {
						overLaySprite = coinSprite;
					}
					else if (t.getType() == Tile.SWORD){
						overLaySprite = swordSprite;
					}
					else if (t.getType() == Tile.ICE_POWER) {
						overLaySprite = snowflakeSprite;
					}
					//Else if wall
					else if (t.getType() == Tile.WALL){
						blockSprite = wallSprite;
					} else {	//else it is a path
						//do nothing
					}
				}
			
				//Always add block sprite
				JLabel spriteImage = new JLabel(sprites.get(blockSprite).getPlayerSprite());
				block.add(spriteImage, 1);
				
				//Add overlay sprite if required
				if (overLaySprite != "") {
					JLabel overlayImage = new JLabel(sprites.get(overLaySprite).getPlayerSprite());
					block.add(overlayImage, 0);
				}
				
				//Add block as hole to maze
				mazeGrid.add(block, gbc);
			}
		}
		
		//Save enemy last position
		//lastEnemyPos = m.getEnemyTile();
		
		//Add maze to this frame
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.CENTER;
		this.add(mazeGrid, gbc);
		
		//Create SidePanel components using gridbag layout 
		//Add Score Panel
		JPanel scorePanel = new JPanel(new GridLayout(2,1));
		scorePanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		
		//Add player image (set size 96 x 96)
		PlayerPanel player = new PlayerPanel(g.getPlayer().getCharacter(),96,96);	
		JLabel playerImage = new JLabel(player.getPlayerSprite());
		playerImage.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		scorePanel.add(playerImage);
		
		//Add Score for player
		JPanel playerScore = new JPanel(new GridLayout(3,1));
		JLabel name = new JLabel("Name: " + g.getPlayer().getName());
		JLabel character = new JLabel("Character: " + g.getPlayer().getCharacter().substring(0, 1).toUpperCase() + g.getPlayer().getCharacter().substring(1));
		score = new JLabel("Score: " + Integer.toString(g.getScore()));
		
		
		
		Font font = new Font("Arial", Font.PLAIN, 16);
		name.setFont(font);
		character.setFont(font);
		score.setFont(new Font("Arial", Font.BOLD, 18));
		
		playerScore.add(name);
		playerScore.add(character);
		playerScore.add(score);
		
		scorePanel.add(playerScore);
		
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 0;
		sidePanel.add(scorePanel);
		gbc.gridx = 0;
		gbc.gridy = -2;
		
		//Add exit button at very bottom
		exitButton.setMargin(new Insets(5, 10, 5, 10));
		exitButton.setToolTipText("Click here to exit to main menu.");
		sidePanel.add(exitButton, gbc);
		
		//Add key and sword (set size 48 x 48)
		PlayerPanel sword = new PlayerPanel(swordSprite,48,48);
		inventorySword = new JLabel(sword.getPlayerSprite());
		PlayerPanel key = new PlayerPanel(keySprite,48,48);
		inventoryKey = new JLabel(key.getPlayerSprite());
		PlayerPanel snowflake = new PlayerPanel(snowflakeSprite,48,48);
		inventoryIcePower = new JLabel(snowflake.getPlayerSprite());
		
		gbc.gridwidth = 1;
		gbc.gridy = 3;
		gbc.gridx = 0;
		//gbc.insets = new Insets(0,10,0,0);
		sidePanel.add(inventorySword,gbc);
		
		//gbc.insets = new Insets(0,0,0,10);
		gbc.gridy = 4;
		gbc.gridx = 0;
		sidePanel.add(inventoryKey,gbc);
		
		gbc.gridy = 5;
		gbc.gridx = 0;
		sidePanel.add(inventoryIcePower,gbc);
		
		inventorySword.setVisible(false);
		inventoryKey.setVisible(false);
		inventoryIcePower.setVisible(false);
		
		//Add sidePanel to this frame
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(sidePanel, gbc);
		
		//Repack frame
		this.pack();
	}
	
}
