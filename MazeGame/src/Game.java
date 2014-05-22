
public class Game {
	//Private fields
    private Maze maze;
    private GameFrame gameFrame;
    private MazeFrame mazeFrame;
    private Player player;
    private Controller c;
    
    //Flow control
    private volatile boolean inGame;
    private volatile boolean isGameOver;
    
    private int width;
    private int height;
    
    /**
     * 
     */
    public Game() {
        this.isGameOver = false;
        this.inGame = false;
        
        //Hard coded for now until difficulty is set by levels etc
        this.width = 9;
        this.height = 9;
        
        this.gameFrame = new GameFrame(this, width, height);
    }
    
    public void createMaze(int width, int height) {
    	this.maze =  new Maze(width, height);
    	this.mazeFrame = new MazeFrame(this, width, height); //make new maze frame
    	
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
    	this.mazeFrame.dispose(); //remove maze
    	this.gameFrame.setVisible(true);
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
}
