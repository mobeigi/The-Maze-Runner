
public class Game {
	//Private fields
    private Maze maze;
    private GameFrame gameFrame;
    private MazeFrame mazeFrame;
    private Player player;
    private Controller c;
    
    //Flow control
    private boolean inGame;
    private boolean isGameOver;
    
    private int width;
    private int height;
    
    /**
     * 
     */
    public Game() {
        this.isGameOver = false;
        this.inGame = false;
        
        this.width = 17;
        this.height = 17;
        
        this.gameFrame = new GameFrame(this, width, height);
    }
    
    public void createMaze(int width, int height) {
    	this.maze =  new Maze(width, height);
    	this.mazeFrame = new MazeFrame(this, width, height); //make new maze frame
    	this.mazeFrame.setVisible(true);
    }
    
    public void initMazeFrame() {
       this.mazeFrame.init(this.maze); 
    }
    
    public void setGameFrameVisible(boolean isVisible) {
    	this.gameFrame.setVisible(isVisible);
    }
    
    public void setMazeFrameVisibe(boolean isVisible) {
    	this.gameFrame.setVisible(isVisible);
    }
    
    public void showUI() {
    	this.mazeFrame.removeAll(); //remove maze
    	this.gameFrame.setVisible(true);
    	this.mazeFrame.setVisible(false);
    }
    
    public void setIsGameOver(boolean isGameOver) {
    	this.isGameOver = isGameOver;
    }
    
    public void setIsInGame(boolean inGame) {
    	this.inGame = inGame;
    }
    
    public boolean isInGame() {
    	return this.inGame;
    }
    
    public void setMazeKeyListerner() {
    	this.c = new Controller(this, this.maze, this.mazeFrame, this.player);
    	this.mazeFrame.addKeyListener(this.c);
    }
    
    public void setPlayer(Player player) {
    	this.player = player;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    /**
     * @return <code>true</code> if game has been finished. <code>false</code> if game has not yet been finished.
     */
    public boolean isGameOver() {
        return this.isGameOver;
    }
}
