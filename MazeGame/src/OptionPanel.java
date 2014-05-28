import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Option window for creating a character
 * @author Jeremy
 *
 */
public class OptionPanel extends JPanel implements ActionListener {

	private Game game;
	
	private JButton setLink;
	private JButton setKey;
	private JTextField nameField;

	
	public OptionPanel(Game g){
		game = g;

		this.setLayout(new GridBagLayout());
		//panel.setResizable(false);
		PlayerPanel linkPanel = new PlayerPanel(MazeFrame.playerSprite,48,48);
		PlayerPanel keyPanel = new PlayerPanel(MazeFrame.keySprite,48,48);
		
		
		nameField = new JTextField("Default");
		nameField.setPreferredSize(new Dimension(100,100));
		
		setLink = new JButton("Link", linkPanel.getPlayerSprite() );
		setKey = new JButton("Key", keyPanel.getPlayerSprite());
		//closeButton = new JButton("close");
		
		setLink.addActionListener(this);
		setKey.addActionListener(this);
		//closeButton.addActionListener(this);
		
		this.add(setLink);
		this.add(setKey);
		this.add(nameField);
		//panel.add(closeButton);
		
		//panel.setVisible(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == setLink){
			game.getPlayer().setCharacter(MazeFrame.playerSprite);
		} else if (event.getSource() == setKey){
			game.getPlayer().setCharacter(MazeFrame.keySprite);
		}

	}
	
	public String getName(){
		return nameField.getText();
	}
}
