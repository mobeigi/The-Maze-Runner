import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Option window for creating a character
 * @author Jeremy
 *
 */
public class OptionFrame implements ActionListener {
	private JFrame frame;
	private Game game;
	
	private JButton setLink;
	private JButton setKey;
	private JButton closeButton;
	
	public OptionFrame(Game g){
		game = g;
		frame = new JFrame();
		frame.setLayout(new GridBagLayout());
		frame.setResizable(false);
		
		setLink = new JButton("Link");
		setKey = new JButton("Key");
		closeButton = new JButton("close");
		
		setLink.addActionListener(this);
		setKey.addActionListener(this);
		closeButton.addActionListener(this);
		
		frame.add(setLink);
		frame.add(setKey);
		frame.add(closeButton);
		
		frame.pack();
		frame.setVisible(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == setLink){
			game.getPlayer().setCharacter(MazeFrame.playerSprite);
		} else if (event.getSource() == setKey){
			game.getPlayer().setCharacter(MazeFrame.keySprite);
		} else if (event.getSource() == closeButton) {
			frame.setVisible(false);
		}
		
		
		
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
	

}
