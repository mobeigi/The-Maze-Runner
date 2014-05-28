import java.awt.Dimension;
import java.awt.GridBagConstraints;
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
		PlayerPanel linkPanel = new PlayerPanel(MazeFrame.playerSprite,48,48);
		PlayerPanel keyPanel = new PlayerPanel(MazeFrame.keySprite,48,48);
		
		
		nameField = new JTextField("Enter Name");
		nameField.setPreferredSize(new Dimension(200,20));
		
		
		setLink = new JButton("Link", linkPanel.getPlayerSprite() );
		setKey = new JButton("Key", keyPanel.getPlayerSprite());
		//closeButton = new JButton("close");
		
		setLink.addActionListener(this);
		setKey.addActionListener(this);
		//closeButton.addActionListener(this);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.gridx = 0;
		this.add(setLink,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(setKey,gbc);
		
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy =1;
		this.add(nameField,gbc);
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
