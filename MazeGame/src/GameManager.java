import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * System managing game flow.
 * It determines what game screens to show and
 * updates the GUI of the game seen by the user.
 * @author Mohammad Ghasembeigi, Gavin Tam
 */
public class GameManager {
	 private GameFrame gameFrame;	//the home screen
	 private MazeFrame mazeFrame;	//the maze game screen
	 private Game g;		//holds information about the game

	 /**
	  * Constructs a new game manager.
	  * A new game is constructed, awaiting run to be called
	  * before activating logic on GUI flow.
	  */
	 public GameManager() {
		 g = new Game();	//make a new game that will be managed
	 }
	 
	 /**
	  * Runs the game manager.
	  * Changes to GUI are done here depending on
	  * user interactions.
	  */
	 public void run() {
		//create game frame
		//this is the home screen of the game
		gameFrame = new GameFrame(g, Game.START_LEVEL_WIDTH, Game.START_LEVEL_HEIGHT);
		
		while (true) {
			//If not in game, do nothing (wait for play button to be clicked)
			if (!g.isInGame())
				continue;
			
			//initially, maze of game is created when the play button of game frame is clicked
			//after that, Game determines levelling system and updates the maze representation
			//which is then updated by GameManager
			createNewMazeFrame();			//initialise maze		
			long lastUpdateTime = System.currentTimeMillis();		
			//while still in game
			while (!g.isGameOver()) {
				if (System.currentTimeMillis() - lastUpdateTime > 20) {	//update every 20ms
					if (!g.getFinishedLevel()) {
						updateMazeFrame(); //update maze according to game state
					} else {
						createNewMazeFrame();	//if levelled up, create a new maze for the new level
						g.setFinishedLevel(false);	//once done, set finished current level to false
					}
					g.updateScore();	//update player score					
					lastUpdateTime = System.currentTimeMillis(); //update time
				}
			}
			//the game is finished; home screen (game frame) is shown
			//game can be played repeatedly until user exits game
			showGameFrame();	
		}
	 }
    
    /**
     * Update maze frame based on current game state.
     * Also requests focus for the maze frame. 
     */
    private void updateMazeFrame() {
        this.mazeFrame.update(g.getMaze());	//update the maze frame according to game state
        this.mazeFrame.getFrame().requestFocus();
        this.mazeFrame.getFrame().repaint();
        //special dialog boxes are shown on the maze frame depending on player condition
        //game manager manages these dialog boxes for maze frame
        if (g.getPlayer().isDead()) {
        	 //if player died, so show end campaign dialog box
 			Object[] options = {"End campaign"};
 			JOptionPane.showOptionDialog (mazeFrame.getFrame(), "Pacman monster killed you!","OH NO!", 
 								JOptionPane.CLOSED_OPTION,JOptionPane.PLAIN_MESSAGE,
 								new ImageIcon(this.getClass().getResource("/sprites/dead_pacman_monster.gif")),options,options[0]);
 			//when user clicks the end campaign button
 			g.setIsGameOver(true);	//update game state to game over
 			g.setIsInGame(false);
 			mazeFrame.getFrame().requestFocus();	//request focus again
        } else if (g.getMaze().exitedMaze()) {
			Object[] options = {"Next level"};
			JOptionPane.showOptionDialog (mazeFrame.getFrame(), "The next journey awaits you...\n" +
								"What unknown challenges lay ahead?","Room " + (g.getLevel()+1) + " cleared!", 	//levels count from 0, so +1 offset to count from 1
								JOptionPane.OK_OPTION,JOptionPane.PLAIN_MESSAGE,
								new ImageIcon(this.getClass().getResource("/sprites/door_open.gif")),options,options[0]);
			
			//When user pressed next level or closes dialog, go to next level
			g.checkNextLevel(); //change game state so that next level is reached
			mazeFrame.getFrame().requestFocus();	//request focus again
		}
     }
    
    /**
     * Show home screen (game frame) and removes the game screen (maze frame).
     * Special ending dialogue box is shown if the player finished all levels of the game.
     * The statistics of the player are then cleared.
     */
    private void showGameFrame() {
    	//end game dialog if user finishes all levels
    	if (g.getLevel() == Game.MAX_LEVEL) {
    		Object[] options = {"Exit"};
			JOptionPane.showOptionDialog (this.mazeFrame.getFrame(), "Congratulations, warrior!\n" +
						"Your skill is worthy of mention but who knows\n" + "what challenges we may see ahead?\n"
						+ "We will require your assistance when the time comes...",
						"Tower cleared!", 1,0,new ImageIcon(this.getClass().getResource("/sprites/door_open.gif")), options, options[0]);
    	} //otherwise no special dialog is displayed
    	g.getPlayer().clearStats();	//clear all player stats
    	g.clearGame();	//clear player progress in game
    	mazeFrame.getFrame().dispose(); //remove maze
    	gameFrame.getFrame().setVisible(true);	//show home screen
    }
    
    /**
     * Creates a new game screen (maze frame) according to the game state.
     * It will dispose the old version and recreate a new game screen,
     * and ensures the game's controller is connected to the new game screen.
     */
    private void createNewMazeFrame() {
    	//dispose the previous maze frame
    	if (mazeFrame != null) {
    		this.mazeFrame.getFrame().dispose();
    	}
    	//make new maze frame according to the maze made by Game
    	this.mazeFrame = new MazeFrame(g,g.getMaze().getWidth(), 
    									g.getMaze().getHeight());
    	//initialise maze that was created
        this.mazeFrame.init(g.getMaze()); 
        this.mazeFrame.getFrame().requestFocus();
		this.mazeFrame.getFrame().addKeyListener(g.getController()); //update controller to new maze
    }
}
