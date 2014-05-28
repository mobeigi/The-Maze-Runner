import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * Option window for creating a character
 * @author Jeremy
 *
 */
public class OptionPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	private Game game;
	
	private JButton setLink;
	private JButton setWilliam;
	private JButton setKainen;
	private JTextField nameField;
	
	private JRadioButton easyDifficulty;
	private JRadioButton mediumDifficulty;
	private JRadioButton hardDifficulty;

	
	public OptionPanel(Game g){
		game = g;

		this.setLayout(new GridBagLayout());		
		GridBagConstraints gbc = new GridBagConstraints();
		
		//panel.setResizable(false)
		Sprite linkPanel = new Sprite(MazeFrame.LinkSprite, 48, 48);
		Sprite williamPanel = new Sprite(MazeFrame.WilliamSprite, 48, 48);
		Sprite kainenPanel = new Sprite(MazeFrame.KainenSprite, 48, 48);
		
		setLink = new JButton("Link - The Legend", linkPanel.getPlayerSprite() );
		setWilliam = new JButton("William - The Knight", williamPanel.getPlayerSprite());
		setKainen = new JButton("Kainen - The Dark Prince", kainenPanel.getPlayerSprite());
		
		setLink.addActionListener(this);
		setWilliam.addActionListener(this);
		setKainen.addActionListener(this);
		this.add(setLink);
		this.add(setWilliam);
		this.add(setKainen);
		
		nameField = new JTextField("Enter Name");
		nameField.setPreferredSize(new Dimension(200,20));
		
		// Move to next line
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.insets = new Insets(20,20,0,0);
		
		JLabel nameText = new JLabel("Introduce yourself, Champion: ");
		this.add(nameText, gbc);
		
		gbc.gridx = 1;
		this.add(nameField, gbc);

		// Move to next line
		gbc.gridy = 2;
		gbc.gridx = 0;
		JLabel difficultyText = new JLabel("Choose difficulty:");
		this.add(difficultyText, gbc);
		
		//Add setting difficulty option
		ButtonGroup difficultyButtons = new ButtonGroup();
		easyDifficulty = new JRadioButton("Easy");
		mediumDifficulty = new JRadioButton("Medium", true);
		hardDifficulty = new JRadioButton("Hard");
		difficultyButtons.add(easyDifficulty);
		difficultyButtons.add(mediumDifficulty);
		difficultyButtons.add(hardDifficulty);
		
		//Move to next line, and add buttons
		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.insets = new Insets(10,10,0,0);
		this.add(easyDifficulty,gbc);
		
		gbc.gridx = 1;
		this.add(mediumDifficulty,gbc);
		
		gbc.gridx = 2;
		this.add(hardDifficulty,gbc);
		
		easyDifficulty.addActionListener(this);
		mediumDifficulty.addActionListener(this);
		hardDifficulty.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == setLink){
			game.getPlayer().setCharacter(MazeFrame.LinkSprite);
		} else if (event.getSource() == setWilliam){
			game.getPlayer().setCharacter(MazeFrame.WilliamSprite);
		} else if (event.getSource() == setKainen){
			game.getPlayer().setCharacter(MazeFrame.KainenSprite);
		} else if (event.getSource() == easyDifficulty){
			game.setDifficulty(Game.EASY);
		} else if (event.getSource() == mediumDifficulty){
			game.setDifficulty(Game.MEDIUM);
		} else if (event.getSource() == hardDifficulty){
			game.setDifficulty(Game.HARD);
		}
	}
	
	public String getName(){
		return nameField.getText();
	}
}
