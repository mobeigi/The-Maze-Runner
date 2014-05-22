import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Create a PlayerPanel based on provided gif sprite.
 */
public class PlayerPanel extends JPanel{
	//Private Fields
	private static final long serialVersionUID = 1L;
	private ImageIcon sprite;

    public PlayerPanel(String imageName) {
    	try {
    		this.sprite = new ImageIcon(this.getClass().getResource("/sprites/" + imageName + ".gif"));
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
