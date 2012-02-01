import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Sprite 
{
	String filepath;
	BufferedImage image;
	
	public Sprite(String filepath)
	{
		this.filepath = filepath;
		this.image = null;
		
		try 
		{
			this.image = ImageIO.read(new File(filepath));
		} 
		catch (IOException e) 
		{
			System.out.println("Failed to load image " + filepath);
		}
	}
}
