package res;

import java.io.InputStream;

import javax.swing.ImageIcon;

public class ResourceLoader {
	
	static ResourceLoader rl = new ResourceLoader();
	
	public static ImageIcon getImageIcon(String path) {
		return new ImageIcon(rl.getClass().getResource("images/" + path));
	}
	
	public static InputStream getInputStream(String path) {
		return rl.getClass().getResourceAsStream("data/" + path);
	}
	
}
