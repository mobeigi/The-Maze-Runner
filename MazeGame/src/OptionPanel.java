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
	private JButton setWilliam;
	private JButton setKainen;
	private JTextField nameField;

	
	public OptionPanel(Game g){
		game = g;

		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		//panel.setResizable(false)
		Sprite linkPanel = new Sprite(MazeFrame.LinkSprite, 48, 48);
		Sprite williamPanel = new Sprite(MazeFrame.WilliamSprite, 48, 48);
		Sprite kainenPanel = new Sprite(MazeFrame.KainenSprite, 48, 48);
		
		
		nameField = new JTextField("enter name");
		nameField.setPreferredSize(new Dimension(200,26));
		
		setLink = new JButton("Link - The Legend", linkPanel.getPlayerSprite() );
		setWilliam = new JButton("William - The Knight", williamPanel.getPlayerSprite());
		setKainen = new JButton("Kainen - The Dark Prince", kainenPanel.getPlayerSprite());
		
		nameField = new JTextField("Enter Name");
		nameField.setPreferredSize(new Dimension(200,20));
		
		//closeButton = new JButton("close");
		
		setLink.addActionListener(this);
		setWilliam.addActionListener(this);
		setKainen.addActionListener(this);

		//closeButton.addActionListener(this);

		this.add(setLink);
		this.add(setWilliam);
		this.add(setKainen);
		
		// Move to next line
		gbc.gridy = -1;
		gbc.gridx = 0;
		
		JLabel nameText = new JLabel("Introduce yourself, Champion: ");
		this.add(nameText, gbc);
		
		gbc.gridx = 1;
		this.add(nameField, gbc);


		//panel.add(closeButton);
		
		//panel.setVisible(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == setLink){
			game.getPlayer().setCharacter(MazeFrame.LinkSprite);
		} else if (event.getSource() == setWilliam){
			game.getPlayer().setCharacter(MazeFrame.WilliamSprite);
		} else if (event.getSource() == setKainen){
			game.getPlayer().setCharacter(MazeFrame.KainenSprite);
		}
	}
	
	public String getName(){
		return nameField.getText();
	}
}
