import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Game class that setups maze and mazeFrame in preparation for a game.
 *
 * @version 1.0
 */

public class Game {
	//Private fields
    private Maze maze;
    private GameFrame gameFrame;
    private MazeFrame mazeFrame;
    private InstructionFrame instrFrame;
    private OptionFrame optionFrame;
    private Player player;
    private Controller c;
   
    private int score;
    private int level;
    
    //Game constants
    public static final int START_LEVEL_WIDTH = 11;
    public static final int START_LEVEL_HEIGHT = 11;
    public static final int POINTS_ENEMY_KILLED = 10;
    public static final int MAX_LEVEL = 10;
    
    //Flow control
    private volatile boolean inGame;
    private volatile boolean isGameOver;
    
    /**
     * Constructor for creating game object.
     */
    public Game() {
        this.isGameOver = false;
        this.inGame = false;
        this.instrFrame = new InstructionFrame();
        this.optionFrame = new OptionFrame(this);
        this.gameFrame = new GameFrame(this, START_LEVEL_WIDTH, START_LEVEL_HEIGHT, instrFrame, optionFrame);	//create game frame
        this.score = 0;	//initially empty score
    }
    
    /**
     * Create a maze which scales in size based on desired game level.
     * 
     * @param level - Desired game level for maze that is to be created.
     */
    public void createMaze(int level) {
    	// Dispose any previous mazes
    	if (mazeFrame != null) {
    		this.mazeFrame.dispose();
    	}
    	this.maze =  new Maze(START_LEVEL_WIDTH+ (2*level), START_LEVEL_HEIGHT + (2*level), player);
    	this.mazeFrame = new MazeFrame(this, START_LEVEL_WIDTH+ (2*level), START_LEVEL_HEIGHT + (2*level)); //make new maze frame
    	
    	//Initilise maze that was created
    	initMazeFrame();
    }
    
    /**
     * Initilise mazeframe based on initial game state.
     * 
     * Also requests focus for the mazeframe. 
     */
    public void initMazeFrame() {
       this.mazeFrame.init(this.maze); 
       this.mazeFrame.requestFocus();
    }
    
    /**
     * Update mazeframe based on current game state.
     * 
     * Also requests focus for the mazeframe. 
     */
    public void updateMazeFrame() {
        this.mazeFrame.update(this.maze);
        this.mazeFrame.requestFocus();
     }
    
    /**
     * Set the visibility of the game interface.
     * 
     * @param isVisible - boolean expression for the 
     */
    public void setGameFrameVisible(boolean isVisible) {
    	this.gameFrame.setVisible(isVisible);
    }
    
    public void setMazeFrameVisible(boolean isVisible) {
    	this.gameFrame.setVisible(isVisible);
    }
    
    public void showUI() {
    	//end game dialog if user finishes all levels
    	if (level == 10) {
    		Object[] options = {"Exit"};
			JOptionPane.showOptionDialog (this.mazeFrame, "Congratulations, warrior!\n" +
						"Your skill is worthy of mention but who knows\n" + "what challenges we may see ahead?\n"
						+ "We will require your assistance when the time comes...",
						"Tower cleared!", 1,0,new ImageIcon(this.getClass().getResource("/sprites/door_open.gif")), options, options[0]);
    	} //otherwise no special dialog is displayed
    	level = 0;	//restart game
    	score = 0;	//restart score
    	this.maze = new Maze(START_LEVEL_WIDTH, START_LEVEL_HEIGHT, player);	//level 1 maze
    	player.clearStats();
    	this.mazeFrame.dispose(); //remove maze
    	this.gameFrame.setVisible(true);
    }
    
    public void setIsGameOver(boolean isGameOver) {
    	this.isGameOver = isGameOver;
    	//set score back to zero.
    	this.score = 0;
    }
    
    public void setIsInGame(boolean inGame) {
    	this.inGame = inGame;
    }
    
    public boolean isInGame() {
    	return this.inGame;
    }
    
    public void setController(Controller c) {
    	this.c = c;
    }
    
    public void setMazeKeyListener() {
    	this.mazeFrame.addKeyListener(this.c);
    }
    
    public void setPlayer(Player player) {
    	this.player = player;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public Maze getMaze() {
    	return this.maze;
    }
    
    public MazeFrame getMazeFrame() {
    	return this.mazeFrame;
    }
    
    /**
     * @return <code>true</code> if game has been finished. <code>false</code> if game has not yet been finished.
     */
    public boolean isGameOver() {
        return this.isGameOver;
    }
    
    public void updateScore(){
    	//1 point for each treasure collected
    	//10 points for each enemy killed
    	score = player.getNumTreasureCollected() + POINTS_ENEMY_KILLED * player.getEnemyKilled();
    }
    
	public int getScore() {
		return score;
	}

	public void nextLevel () {
		level++;
		player.setItemCollected(Player.SWORD,false);	//clear inventory once next level
		player.setItemCollected(Player.KEY,false);
		player.setItemCollected(Player.ICE_POWER,false);
	}

	public int getLevel () {
		return level;
	}
}