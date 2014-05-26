import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


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
    
    //Flow control
    private volatile boolean inGame;
    private volatile boolean isGameOver;
    
    public Game() {
        this.isGameOver = false;
        this.inGame = false;
        this.instrFrame = new InstructionFrame();
        this.optionFrame = new OptionFrame(this);
        //Level 1 is 11 x 11       
        this.gameFrame = new GameFrame(this,11,11,instrFrame,optionFrame);
        
        
        this.score = 0;
    }
    
    public void createMaze(int level) {
    	if (mazeFrame != null) {
    		this.mazeFrame.dispose();
    	}
    	this.maze =  new Maze(11+2*level, 11+2*level,player);
    	this.mazeFrame = new MazeFrame(this, 11+2*level, 11+2*level); //make new maze frame
    	
    	//Init maze that was created
    	initMazeFrame();
    }
    
    public void initMazeFrame() {
       this.mazeFrame.init(this.maze); 
       this.mazeFrame.requestFocus();
    }
    
    public void updateMazeFrame() {
        this.mazeFrame.update(this.maze);
        this.mazeFrame.requestFocus();
     }
    
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
						"Tower cleared!", 1,0,new ImageIcon(this.getClass().getResource("/sprites/door_open.gif")),options,0);
    	} //otherwise no special dialog is displayed
    	level = 0;	//restart game
    	score = 0;	//restart score
    	this.maze = new Maze(11,11,player);	//level 1 maze
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
    	score = player.getNumTreasureCollected() + 10*player.getEnemyKilled();
    }
    
	public int getScore() {
		return score;
	}
	
	public void nextLevel () {
		level++;
		player.setSwordCollected(false);	//clear inventory once next level
		player.setKeyCollected(false);
		player.setIcePowerCollected(false);
	}
	
	public int getLevel () {
		return level;
	}
}
