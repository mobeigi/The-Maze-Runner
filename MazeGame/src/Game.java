import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Game class that setups maze and in-game screens in preparation for a game.
 * In-game screens include the home screen and the maze game screen.
 * All players and controllers are configured.
 */

public class Game {
	
    private Maze maze;
    private GameFrame gameFrame;
    private MazeFrame mazeFrame;
    private Player player;
    private Controller c;	//controller for player
    
    private int difficulty;
    private int score;
    private int level;
    
    //Flow control
    private volatile boolean inGame;	//whether or not the game has not been exited yet
    private volatile boolean isGameOver;	//whether or not player has lost the game
    										//or has passed all the levels
    
    //Game constants
    public static final int START_LEVEL_WIDTH = 11;
    public static final int START_LEVEL_HEIGHT = 11;
    public static final int POINTS_ENEMY_KILLED = 10;
    public static final int MAX_LEVEL = 10;
    
    //Difficulty constants
    public static final int EASY = -1;
    public static final int MEDIUM = 0;
    public static final int HARD = 1;  
    
    /**
     * Constructor for creating a maze game.
     */
    public Game() {
        this.isGameOver = false;
        this.inGame = false;
        //Make one player
		player = new Player("Default", "link");
        this.score = 0;	//initially empty score
        this.difficulty = MEDIUM;	//default difficulty is medium
        this.gameFrame = new GameFrame(this, START_LEVEL_WIDTH, START_LEVEL_HEIGHT);	//create game frame
    }
    
    /**
     * Create a maze which scales in size based on desired game level.
     * @param level the desired game level for maze that is to be created.
     */
    public void createMaze(int level) {
    	// Dispose any previous mazes
    	if (mazeFrame != null) {
    		this.mazeFrame.getFrame().dispose();
    	}
    	this.maze =  new Maze(START_LEVEL_WIDTH + (2*(level+difficulty)), START_LEVEL_HEIGHT + (2*(level+difficulty)), player);
    	this.mazeFrame = new MazeFrame(this, START_LEVEL_WIDTH + (2*(level+difficulty)), START_LEVEL_HEIGHT + (2*(level+difficulty))); //make new maze frame
    	
    	//Initialise maze that was created
    	initMazeFrame();
		c = new Controller(maze);	//update controller
		this.mazeFrame.getFrame().addKeyListener(this.c);
    }
    
    /**
     * Initialise maze frame based on initial game state.
     * Also requests focus for the maze frame. 
     */
    public void initMazeFrame() {
       this.mazeFrame.init(this.maze); 
       this.mazeFrame.getFrame().requestFocus();
    }
    
    /**
     * Update maze frame based on current game state.
     * Also requests focus for the maze frame. 
     */
    public void updateMazeFrame() {
        this.mazeFrame.update(this.maze);
        this.mazeFrame.getFrame().requestFocus();
        this.mazeFrame.getFrame().repaint();
     }
    
    /**
     * Show home screen (game frame) and removes the game screen (maze frame).
     */
    public void showUI() {
    	//end game dialog if user finishes all levels
    	if (level == MAX_LEVEL) {
    		Object[] options = {"Exit"};
			JOptionPane.showOptionDialog (this.mazeFrame.getFrame(), "Congratulations, warrior!\n" +
						"Your skill is worthy of mention but who knows\n" + "what challenges we may see ahead?\n"
						+ "We will require your assistance when the time comes...",
						"Tower cleared!", 1,0,new ImageIcon(this.getClass().getResource("/sprites/door_open.gif")), options, options[0]);
    	} //otherwise no special dialog is displayed
    	level = 0;	//restart game
    	score = 0;	//restart score
    	this.maze = new Maze(START_LEVEL_WIDTH, START_LEVEL_HEIGHT, player);	//level 1 maze
    	player.clearStats();
    	this.mazeFrame.getFrame().dispose(); //remove maze
    	this.gameFrame.setVisible(true);
    }
    
    /**
     * Updates the status of whether the game is over or not.
     * The game is over when the player dies or
     * all levels are completed.
     * @param isGameOver if true, the game is over;
     * if false, the game is still continuing.
     */
    public void setIsGameOver(boolean isGameOver) {
    	this.isGameOver = isGameOver;
    	//set score back to zero.
    	this.score = 0;
    }
    
    /**
     * Updates the status of whether the player is still in the game or not.
     * @param inGame if true, the game should be in the exit state;
     * if false, the game should still be on.
     */
    public void setIsInGame(boolean inGame) {
    	this.inGame = inGame;
    }
    
    /**
     * Checks if the player is still in the game or not.
     * @return true if the player is still in the game.
     */
    public boolean isInGame() {
    	return this.inGame;
    }
    
    /**
     * Sets the controller for the player.
     * @param c the controller for the player.
     */
    public void setController(Controller c) {
    	this.c = c;
    	this.mazeFrame.getFrame().addKeyListener(this.c);
    }
    
    /**
     * Gets the player of the game.
     * @return the player of the game.
     */
    public Player getPlayer() {
    	return this.player;
    }
    
    /**
     * Gets the maze of the game.
     * @return the maze of the game.
     */
    public Maze getMaze() {
    	return this.maze;
    }
    
    /**
     * Gets the maze frame of the game.
     * @return the maze frame of the game.
     */
    public MazeFrame getMazeFrame() {
    	return this.mazeFrame;
    }
    
    /**
     * Checks if the game is over or not.
     * The game is over when the player has lost the game
  	 * or has passed all the levels.
     * @return true if game has been finished.
     */
    public boolean isGameOver() {
        return this.isGameOver;
    }
    
    /**
     * Updates the game score.
     * The game is dependent on the number of treasure collected
     * by the player, and the number of enemies killed.
     */
    public void updateScore(){
    	//1 point for each treasure collected
    	//10 points for each enemy killed
    	score = player.getNumTreasureCollected() + POINTS_ENEMY_KILLED * player.getEnemyKilled();
    }
    
    /**
     * Gets the current score of the game.
     * @return the current score of the game.
     */
	public int getScore() {
		return score;
	}

	/**
	 * Checks if the game should be on the next level,
	 * and updates the game state accordingly.
	 * Next level should be reached when player exits the room.
	 */
	public void checkNextLevel () {
		 //If level is complete
		 if (maze.exitedMaze()) {
			level++;
			player.clearInventory();	//clear inventory once next level
			 if (level == MAX_LEVEL) {
				 setIsGameOver(true);	//end game if passed all levels
				 setIsInGame(false);
			 } else {
				 createMaze(level);
			 }
		 }
	}

	/**
	 * Get the current level of the game.
	 * @return the current level of the game.
	 */
	public int getLevel () {
		return level;
	}
	
	/**
	 * Sets the difficulty of the game.
	 * @param difficulty the difficulty of the game.
	 * Defined in Game.
	 */
	public void setDifficulty (int difficulty) {
		this.difficulty = difficulty;
	}
}