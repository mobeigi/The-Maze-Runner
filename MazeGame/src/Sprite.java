import java.awt.*;
import javax.swing.ImageIcon;

/**
 * Create a Sprite based on provided string identifer of gif image in sprite directory.
 * 
 * @author Mohammad Ghasembeigi
 */
public class Sprite {
	
	private ImageIcon sprite;

	/**
	 * Constructor.
	 * 
	 * Loads and sets sprite field if image successfully loaded or creates an empty sprite otherwise.
	 * 
	 * @param imageName - Identifier of gif image to be loaded from sprite directory
	 * @param x	- Pixel width that sprite should be resized to.
	 * @param y - Pixel height that sprite should be resized to.
	 */
    public Sprite(String imageName, int x, int y) {
    	try {	
    		//Check if image exists 
    		sprite = new ImageIcon(this.getClass().getResource("/sprites/" + imageName + ".gif"));
    		Image image = sprite.getImage().getScaledInstance(x, y, Image.SCALE_FAST);
    		sprite.setImage(image);	//Set sprite field to resized image
    	}
    	catch (Exception e) {}	//If not, blank image sprite is created
    }
    
    /**
     * @return Stored image of resized sprite.
     */
    public ImageIcon getPlayerSprite()
    {
    	return this.sprite;
    }
}
