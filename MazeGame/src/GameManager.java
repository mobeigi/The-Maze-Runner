import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class GameManager {
	 private GameFrame gameFrame;
	 private MazeFrame mazeFrame;
	 private Game g;

	 public GameManager() {
		 g = new Game();	//make a new game that will be managed
	 }
	 
	 public void run() {
		//create game frame
		 //this is the home screen of the game
		 gameFrame = new GameFrame(g, Game.START_LEVEL_WIDTH, Game.START_LEVEL_HEIGHT);
		 while (true) {
			//If not in game, do nothing (wait for play button to be clicked)
			if (!g.isInGame())
				continue;
			
			//Once made, initialise the frame of the game
			createNewMazeFrame();			//initialise maze		
			long lastUpdateTime = System.currentTimeMillis();		
			//While still in game
			while (!g.isGameOver()) {
				if (System.currentTimeMillis() - lastUpdateTime > 20) {	//update every 20ms
					if (!g.getLevelledUp()) {
						updateMazeFrame(); //update maze
					} else {
						createNewMazeFrame();
						g.setLevelledUp(false);
					}
					g.updateScore();	//update player score					
					lastUpdateTime = System.currentTimeMillis(); //update time
				}
			}
			//We have finished game, show game UI and repeat
			showUI();	
		}
	 }
    
    /**
     * Update maze frame based on current game state.
     * Also requests focus for the maze frame. 
     */
    private void updateMazeFrame() {
        this.mazeFrame.update(g.getMaze());
        this.mazeFrame.getFrame().requestFocus();
        this.mazeFrame.getFrame().repaint();
     }
    
    /**
     * Show home screen (game frame) and removes the game screen (maze frame).
     */
    private void showUI() {
    	//end game dialog if user finishes all levels
    	if (g.getLevel() == Game.MAX_LEVEL) {
    		Object[] options = {"Exit"};
			JOptionPane.showOptionDialog (this.mazeFrame.getFrame(), "Congratulations, warrior!\n" +
						"Your skill is worthy of mention but who knows\n" + "what challenges we may see ahead?\n"
						+ "We will require your assistance when the time comes...",
						"Tower cleared!", 1,0,new ImageIcon(this.getClass().getResource("/sprites/door_open.gif")), options, options[0]);
    	} //otherwise no special dialog is displayed
    	g.createMaze();
    	g.getPlayer().clearStats();
    	mazeFrame.getFrame().dispose(); //remove maze
    	gameFrame.getFrame().setVisible(true);
    }
    
    /**
     * Create a maze which scales in size based on desired game level.
     * @param level the desired game level for maze that is to be created.
     */
    private void createNewMazeFrame() {
    	// Dispose any previous maze
    	if (mazeFrame != null) {
    		this.mazeFrame.getFrame().dispose();
    	}
    	this.mazeFrame = new MazeFrame(g,g.getMaze().getWidth(), 
    									g.getMaze().getHeight()); //make new maze frame
    	//Initialise maze that was created
        this.mazeFrame.init(g.getMaze()); 
        this.mazeFrame.getFrame().requestFocus();
		this.mazeFrame.getFrame().addKeyListener(g.getController()); //update controller to new maze
    }
}
