import java.awt.*;
import javax.swing.ImageIcon;

/**
 * Create a Sprite based on provided gif sprite.
 */
public class Sprite {
	
	private ImageIcon sprite;

    public Sprite(String imageName, int x, int y) {
    	try {
    		sprite = new ImageIcon(this.getClass().getResource("/sprites/" + imageName + ".gif"));
    		Image image = sprite.getImage().getScaledInstance(x, y, Image.SCALE_FAST);
    		sprite.setImage(image);
    	}
    	catch (Exception e) {}
    }
    
    /**
     * @return Stored image for player sprite.
     */
    public ImageIcon getPlayerSprite()
    {
    	return this.sprite;
    }
}
