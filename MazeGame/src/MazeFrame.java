import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * GUI class for the maze game.
 * Responsible for displaying the current game state of the maze in addition to a sidepanel containing relevant game variables and navigation options.
 * 
 * @author Mohammad Ghasembeigi, Gavin Tam
 * @see Maze
 */
public class MazeFrame {

	private int height;	//Dimensions of the maze frame
	private int width;
	
	private Game g;	//Reference to the game state to allow querying for information
	
	//Frame components
	private JFrame frame;
	private JPanel mazeGrid;	//panel where maze grid is placed in
	private JLayeredPane[][] mazeGridComp;	//allows access to each tile in the grid
	private Dimension blockSize;	//the size of each tile in the maze grid
	
	//Side panel components
	private JPanel sidePanel;
	private JLabel score;	//shows the score of the player
	private JLabel level;	//shows level of game;
	private JButton exitButton;	//gives player option to quit
	private JButton hintButton;	//shows hint on maze when pressed
	private ArrayList<JLabel> inventory; //labels for the inventory items
	
	private Tile lastPlayerPos;	//stores where the player last was
	private Tile[] lastEnemyPos;	//stores where the enemies last were
	private boolean updatedOnce;
	
	//Store sprites once in hash map for easy access
	private HashMap<String, Sprite> sprites;	

	//All background tiles including wall and path
	public static final String wallSprite = "steel_wall";
	public static final String pathSprite = "carpet";
	
	//Character sprite constants
	private String playerSprite;
	public static final String LinkSprite = "link";
	public static final String WilliamSprite = "william";
	public static final String KainenSprite = "kainen";

	//Other block sprite constants
	public static final String doorSprite = "locked_door";
	public static final String keySprite = "key";
	public static final String coinSprite = "coin";
	public static final String enemySprite = "cyan_pacman_monster";
	public static final String killableEnemySprite = "dead_pacman_monster";
	public static final String swordSprite = "master_sword";
	public static final String snowflakeSprite = "snowflake";
	public static final String hintSprite = "hint";
	
	
	/**
	 * Constructor.
	 * 
	 * @param game - Game object with game state information.
	 * @param width - Width of maze grid to be painted (number of tiles).
	 * @param height - Height of maze grid to be painted (number of tiles).
	 */
	public MazeFrame(Game game, int width, int height) {
		//Set up MazeFrame with provided parameters
		frame = new JFrame();	//make new frame to place maze frame components in
		this.height = height; 	//number of tiles in y direction
		this.width = width;		//number of tiles in x direction
		this.g = game;		//save reference to game state
		
		//Set up side panel
		mazeGrid = new JPanel();
		mazeGridComp = new JLayeredPane[this.width][this.height];
		sidePanel = new JPanel();
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {	//Anonymous actionListerner class, Shows dialog box giving exit option
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Yes", "Cancel"};
				Icon icon = UIManager.getIcon("OptionPane.informationIcon");
				
				int dialogResult = JOptionPane.showOptionDialog(frame, "Are you sure you want to exit to the main menu?\n\nAll game progress will be lost.", "Exit Warning", 
																JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, icon, options, options);
				//If user wishes to quit
				if (dialogResult == JOptionPane.YES_OPTION) {
					g.setIsGameOver(true);
					g.setIsInGame(false);
				} else {
					frame.requestFocus();	//request focus again
				}
			} 
		});
		
		hintButton = new JButton("Hint");
		hintButton.addActionListener(new ActionListener() {	//Anonymous actionListerner class, shows hint tiles on maze
			public void actionPerformed(ActionEvent e) {
				Maze m = g.getMaze();
				List<Tile> hintTiles = m.giveHint(m.getPlayerTile());
				for (int i = 0; i < hintTiles.size(); i++) {
					JLabel hintImage = new JLabel(sprites.get(hintSprite).getPlayerSprite());
					//add hint tile to second layer from the top
					int numComponents = mazeGridComp[hintTiles.get(i).getX()][hintTiles.get(i).getY()].getComponentCount();
					if (numComponents >= 3) {	//if more than 3 components, a hint tile was already added, so don't add again
						continue;
					}
					//if only one component that is a path tile, add a hint tile on top
					if (numComponents == 1 && g.getMaze().getTile(hintTiles.get(i).getX(),hintTiles.get(i).getY()).getType() == Tile.PATH) {
						mazeGridComp[hintTiles.get(i).getX()][hintTiles.get(i).getY()].add(hintImage, new Integer(0));
					} else if (g.getMaze().getTile(hintTiles.get(i).getX(),hintTiles.get(i).getY()).getType() != Tile.PATH) {
						//if more than one component and is not a path tile i.e. is special tile
						//add hint tile in between
						mazeGridComp[hintTiles.get(i).getX()][hintTiles.get(i).getY()].add(hintImage, new Integer(-1));
					} else {	//else don't do anything
						continue;
					}
					mazeGridComp[hintTiles.get(i).getX()][hintTiles.get(i).getY()].repaint();	//refresh block
					frame.pack();
				}
			}
		});
		inventory = new ArrayList<JLabel>();	//initialise inventory
		
		//Make maze take up full screen
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight()); 
		Dimension fullscreen = new Dimension(xSize, ySize);
		frame.setPreferredSize(fullscreen);
		
		//Initialise character spites
		//Maze block is made into a sqaure size
		//Screen height is often shorter than width, thus use 95% of height for pixel width and pixel height of maze GUI
		blockSize = new Dimension((int)((ySize*0.95/this.height)), (int)((ySize*0.95/this.height)));	
		
		//Store sprites in hashmap for easy access
		sprites = new HashMap<String, Sprite>();
		
		//Store x, y of blockSize
		int x = (int)blockSize.getWidth();
		int y = (int)blockSize.getHeight();
		
		//Set PlayerSprite
		playerSprite = g.getPlayer().getCharacter();
		
		//Add sprites to hashmap
		//Size of each sprite is determined by x and y,
		//which is the size of each block on the maze grid
		sprites.put(wallSprite, new Sprite(wallSprite,x,y));
		sprites.put(pathSprite, new Sprite(pathSprite,x,y));
		sprites.put(playerSprite, new Sprite(playerSprite,x,y));
		sprites.put(doorSprite, new Sprite(doorSprite,x,y));
		sprites.put(keySprite, new Sprite(keySprite,x,y));
		sprites.put(enemySprite, new Sprite(enemySprite,x,y));
		sprites.put(coinSprite, new Sprite(coinSprite,x,y));
		sprites.put(swordSprite, new Sprite(swordSprite,x,y));
		sprites.put(snowflakeSprite, new Sprite(snowflakeSprite,x,y));
		sprites.put(killableEnemySprite, new Sprite(killableEnemySprite,x,y));
		sprites.put(hintSprite, new Sprite(hintSprite,x,y));
		
		//Set side panel characteristics
		//Size is based on mazes size. Its width is 40% of mazes width.
		//Height is equivalent to mazes height.
		sidePanel.setPreferredSize(new Dimension((int) ((this.width * blockSize.getWidth())*0.4),
													(int) (this.height * blockSize.getHeight())));
		sidePanel.setBackground(new Color(240, 240, 240));
		sidePanel.setLayout(new GridBagLayout());
		sidePanel.setBorder(new LineBorder(Color.BLACK, 2));
		
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);  //Update state 
		frame.setResizable(false); //Fix window size
		frame.setLayout(new GridBagLayout()); //Set frame layout
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set close operation
		
		//Pack frame
		frame.setUndecorated(true);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Update only GUI components that will have changed since initialisation or a previous update cal1l.
	 * These components include blocks with the player, enemy and unlocked door.
	 * @param m Maze that GUI will use to create a visual representation of the maze game.
	 */
	public void update(Maze m) {
		Tile curPlayerPos = m.getPlayerTile();
		
		//if player is not dead or if he just died, update its position
		if (!m.playerDied() || lastPlayerPos != null) {
			if (!lastPlayerPos.equals(curPlayerPos)) {
				updateBlock(m, lastPlayerPos);
				if (!m.playerDied()) {
					updateBlock(m, curPlayerPos);
				}
				score.setText("Score: " + Integer.toString(g.getScore())); //update score
				// Add things to inventory
				if (m.itemCollected(Player.SWORD)){
					inventory.get(Player.SWORD).setVisible(true);
				}
				if (m.itemCollected(Player.KEY)){
					inventory.get(Player.KEY).setVisible(true);
				}
				if (m.itemCollected(Player.ICE_POWER)){
					inventory.get(Player.ICE_POWER).setVisible(true);
				} else {
					inventory.get(Player.ICE_POWER).setVisible(false);	//disappears according to maze settings (after 5 seconds)
				}
				
				if (m.checkReachedEnd()) {	//unlock door if the player has reached the end with the key
					updateBlock(m,m.getDestDoor());
				} else if (m.exitedMaze()) {
					Object[] options = {"Next level"};
					JOptionPane.showOptionDialog (frame, "The next journey awaits you...\n" +
										"What unknown challenges lay ahead?","Room " + (g.getLevel()+1) + " cleared!", 	//levels count from 0, so +1 offset to count from 1
										JOptionPane.OK_OPTION,JOptionPane.PLAIN_MESSAGE,
										new ImageIcon(this.getClass().getResource("/sprites/door_open.gif")),options,options[0]);
					//when user clicks the exit button
					g.checkNextLevel(); //change game state so that next level is reached
					frame.requestFocus();	//request focus again
				}
			}
			lastPlayerPos = curPlayerPos;
		} else {	//else player died, so show end campaign dialog box
			Object[] options = {"End campaign"};
			int dialogResult = JOptionPane.showOptionDialog (frame, "Pacman monster killed you!","OH NO!", 
								JOptionPane.CLOSED_OPTION,JOptionPane.PLAIN_MESSAGE,
								new ImageIcon(this.getClass().getResource("/sprites/" + enemySprite + ".gif")),options,options[0]);
			//when user clicks the end campaign button
			if (dialogResult == 0) {
				g.setIsGameOver(true);	//update game state to game over
				g.setIsInGame(false);
			} else {
				frame.requestFocus();	//request focus again
			}
		}
		
		for (int i = 0; i < m.getNumEnemies(); i++) {
			Tile curEnemyPos = m.getEnemyTile(i);
			if (!m.enemyDied(i)) {	//if enemy is not dead, update its position
				if (!lastEnemyPos[i].equals(curEnemyPos)) {
					updateBlock(m, lastEnemyPos[i]);
					updateBlock(m, curEnemyPos);
					updatedOnce = false;
				} else if (m.itemCollected(Player.ICE_POWER) && !updatedOnce) {
					updateBlock(m, lastEnemyPos[i]);
					if (i == m.getNumEnemies()-1) {	//if all frozen enemies have been updated once
						updatedOnce = true;			//no need to update again until unfrozen
					}
				}
				lastEnemyPos[i] = curEnemyPos;
			} else if (lastEnemyPos[i] != null) {
				updateBlock(m, lastEnemyPos[i]);
				lastEnemyPos[i] = null;
			}
		}
	}
	
	/**
	 * Update a particular tile on the maze.
	 * @param m the maze to be updated
	 * @param old the tile to be updated
	 */
	private void updateBlock(Maze m, Tile old) {	
		//update top layers when updating block
		//leave bottom background layer
		int numComponents = this.mazeGridComp[old.getX()][old.getY()].getComponentCount();
		for (int i = 0; i < numComponents-1; i++) {
			this.mazeGridComp[old.getX()][old.getY()].remove(0);
		}
		String overLaySprite = "";	//Overlay sprite that goes on top of block sprite
		
		//Determine block graphics based on type of tile
		//If player is at this tile
		if (m.getPlayerTile() != null && m.getPlayerTile().equals(old)) {
			overLaySprite = g.getPlayer().getCharacter();
		} 
		//Check if enemy unit
		else if (m.isEnemyTile(old)) {
			if (m.itemCollected(Player.ICE_POWER)) {
				overLaySprite = killableEnemySprite;
			} else {
				overLaySprite = enemySprite;
			}
		}
		//if the tile is door and is a path now (key collected)
		else if (m.getDestDoor().equals(old) && m.getDestDoor().getType() == Tile.PATH) {
			this.mazeGridComp[old.getX()][old.getY()].remove(0);
			overLaySprite = pathSprite;
		}
		//Check to see what tile type this is
		else if (old.getType() == Tile.DOOR) {
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
		if (overLaySprite != "") {	//if an overlay sprite has been determined
			JLabel overlayImage = new JLabel(sprites.get(overLaySprite).getPlayerSprite());
			this.mazeGridComp[old.getX()][old.getY()].add(overlayImage, new Integer(0));		
			frame.pack();
		}
	}
	
	/**
	 * Initialise GUI components in frame based on the initial state of maze.
	 * 
	 * @param m Maze that GUI will use to create a visual representation of the maze game.
	 */
	public void init(Maze m) 
	{
		//Clear panels
		mazeGrid.removeAll();
		sidePanel.removeAll();
		
		//Make new GridLayout for maze itself
		mazeGrid.setLayout(new GridLayout(width, height));
		
		//Constraints to GridBayLayout
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		//Make new enemy
		lastEnemyPos = new Tile[m.getNumEnemies()];
		for (int i = 0; i < m.getNumEnemies(); i++) {
			lastEnemyPos[i] = new Tile();
		}
		
		//Fill all blocks
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
				
				//Determine block graphics based on type of tile
				//If player is at this tile
				if (m.getPlayerTile() != null && m.getPlayerTile().equals(t)) {
					overLaySprite = playerSprite;
					this.lastPlayerPos = t; //update last position
				}
				//Check if enemy unit
				else if (m.isEnemyTile(t)) {
					for (int i = 0; i < m.getNumEnemies(); i++) {	//find enemy ID
						if (m.getEnemyTile(i) != null && m.getEnemyTile(i).equals(t)) {
							overLaySprite = enemySprite;
							this.lastEnemyPos[i] = t;
							break;
						}
					}
				}
				//Check if this is a door
				else if (t.getType() == Tile.DOOR) {
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
				}
				//else, path so default block sprite is used
			
				//Always add block sprite
				JLabel spriteImage = new JLabel(sprites.get(blockSprite).getPlayerSprite());
				block.add(spriteImage, new Integer(-2));
				
				//Add overlay sprite if required
				if (overLaySprite != "") {
					JLabel overlayImage = new JLabel(sprites.get(overLaySprite).getPlayerSprite());
					block.add(overlayImage, new Integer(0));
				}
				
				//Add block as hole to maze
				mazeGrid.add(block, gbc);
			}
		}
		
		//Add maze to this frame
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.CENTER;
		frame.add(mazeGrid, gbc);
		
		//Create side panel components using gridbag layout 
		//Add panel with player information
		JPanel playerPanel = new JPanel(new GridLayout(3,1));
		playerPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		
		//Add game icon to side panel
		ImageIcon title = new ImageIcon(this.getClass().getResource("/sprites/mazerunner.png"));
		Image image = title.getImage().getScaledInstance(288, 144, Image.SCALE_FAST);
		title.setImage(image);	
		JLabel titleImage = new JLabel(title);
		playerPanel.add(titleImage);
		
		//Add current player image (set size 96 x 96)
		Sprite player = new Sprite(g.getPlayer().getCharacter(),96,96);	
		JLabel playerImage = new JLabel(player.getPlayerSprite());
		playerImage.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		playerPanel.add(playerImage);
		
		//Add Score label for player
		JPanel playerStats = new JPanel(new GridLayout(4,1));
		JLabel name = new JLabel("Name: " + g.getPlayer().getName());
		JLabel character = new JLabel("Character: " + g.getPlayer().getCharacter().substring(0, 1).toUpperCase() + g.getPlayer().getCharacter().substring(1));
		score = new JLabel("Score: " + g.getScore());
		level = new JLabel("Level: " + (g.getLevel()+1)); //levels count from 0, so +1 offset so that levels count from 1
		
		//Setup font of different labels
		name.setFont(new Font("Arial", Font.PLAIN, 16));
		character.setFont(new Font("Arial", Font.PLAIN, 16));
		score.setFont(new Font("Arial", Font.BOLD, 18));
		level.setFont(new Font("Arial", Font.BOLD, 18));
		
		//Add components to player stats panel
		playerStats.add(name);
		playerStats.add(character);
		playerStats.add(score);
		playerStats.add(level);	
		playerPanel.add(playerStats);	//add player stats to panel with player information
		
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 0;
		sidePanel.add(playerPanel);	//add player information panel to side panel
		
		gbc.gridx = 0;
		gbc.gridy = -3;
		
		//Add exit button and hint button at bottom of side panel
		hintButton.setMargin(new Insets(5, 10, 5, 10));
		exitButton.setToolTipText("Click here to exit to main menu.");
		sidePanel.add(hintButton, gbc);
		exitButton.setMargin(new Insets(5, 10, 5, 10));
		sidePanel.add(exitButton, gbc);
		
		//Add key and sword (set size 48 x 48)
		inventory.add(Player.KEY, new JLabel(new Sprite(keySprite,48,48).getPlayerSprite()));
		inventory.add(Player.SWORD, new JLabel(new Sprite(swordSprite,48,48).getPlayerSprite()));
		inventory.add(Player.ICE_POWER, new JLabel(new Sprite(snowflakeSprite,48,48).getPlayerSprite()));
		
		 JPanel invPanel = new JPanel();
         invPanel.setPreferredSize(new Dimension(200,60));
        
         gbc.gridwidth = 1;
         gbc.gridy = 0;
         gbc.gridx = 0;
         invPanel.add(inventory.get(Player.SWORD),gbc);
        
         gbc.gridy = 0;
         gbc.gridx = 1;
         invPanel.add(inventory.get(Player.KEY),gbc);
        
         gbc.gridy = 0;
         gbc.gridx = 2;
         invPanel.add(inventory.get(Player.ICE_POWER),gbc);
        
         for (int i = 0; i < Player.NUM_INVENTORY_ITEMS; i++) {
                 inventory.get(i).setVisible(false);
         }
         
         gbc.gridy = 2;
         gbc.gridx = 0;
         sidePanel.add(invPanel,gbc);
         
         //Add sidePanel to this frame
         gbc.gridx = 1;
         gbc.gridy = 0;
         frame.add(sidePanel, gbc);
        
         //Pack frame
         frame.pack();
	}
	
	/**
	 * Gets the frame component of the maze frame.
	 * This is where all the components are added onto.
	 * @return the frame component of the maze frame
	 */
	public JFrame getFrame() {
		return frame;
	}	
}