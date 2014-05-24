import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 * Create a PlayerPanel based on provided gif sprite.
 */
public class PlayerPanel extends JPanel{
	//Private Fields
	private static final long serialVersionUID = 1L;
	private ImageIcon sprite;

    public PlayerPanel(String imageName, int x, int y) {
    	try {
    		sprite = new ImageIcon(this.getClass().getResource("/sprites/" + imageName + ".gif"));
    		Image image = sprite.getImage().getScaledInstance(x, y, Image.SCALE_FAST);
    		sprite.setImage(image);
    	}
    	catch (Exception e) {} //Empty JPanel if file not found
    	
    	this.setOpaque(false);	//For transparency
    }
    
    /**
     * @return Stored image for player sprite.
     */
    public ImageIcon getPlayerSprite()
    {
    	return this.sprite;
    }
}
