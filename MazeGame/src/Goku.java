import javax.swing.*;


public class Goku extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public ImageIcon image;

    public Goku(String imageName) {
    	try {
    		this.image = new ImageIcon(this.getClass().getResource("/" + imageName + ".gif"));
    	}
    	catch (Exception e) {} //Empty Jpanel if file not found
    	
    	this.setOpaque(false);
    }
}
