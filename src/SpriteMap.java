import java.util.Hashtable;


public class SpriteMap 
{
	
	Hashtable<String, Sprite> sprites;
	
	public SpriteMap()
	{
		sprites = new Hashtable<String, Sprite>();
	}
	
	public void add(String name, String filepath)
	{
		Sprite tmp = new Sprite(filepath);
		sprites.put(name, tmp);
	}
	
	public void add(String name, Sprite sprite)
	{
		sprites.put(name, sprite);
	}
	
	public Sprite get(String name)
	{
		return sprites.get(name);
	}

}
