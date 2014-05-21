
public class Game {
	//Private fields
    private Maze maze;
    private GameFrame gameFrame;
    private MazeFrame mazeFrame;
    private Player player;
    
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
        
        this.width = 0;
        this.height = 0;
        
        this.gameFrame = new GameFrame(this, width, height);
    }
    
    public void createMaze(int width, int height) {
    	this.maze =  new Maze(width, height);
    	this.mazeFrame = new MazeFrame(this.maze.getWidth(), this.maze.getHeight()); //make new maze frame
    	this.mazeFrame.setVisible(true);
    }
    
    public void update() {
        
    }
    
    public void draw() {
        
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
    
    /**
     * @return <code>true</code> if game has been finished. <code>false</code> if game has not yet been finished.
     */
    public boolean isGameOver() {
        return this.isGameOver;
    }
}
