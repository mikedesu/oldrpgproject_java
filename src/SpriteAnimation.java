import java.util.ArrayList;


public class SpriteAnimation 
{
	boolean isRunning;
	int index;
	ArrayList<Sprite> images;
	
	public SpriteAnimation() 
	{
		isRunning = false;
		index = 0;
		images = new ArrayList<Sprite>();
	}
	
	public void add(String filepath)
	{
		Sprite tmp = new Sprite(filepath);
		images.add(tmp);
	}
	
}
