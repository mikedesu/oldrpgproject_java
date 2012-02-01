
public class Entity 
{
	String name;
	Sprite sprite;
	int x, y, z;
	//Point position;
	
	public Entity(String name, String filepath)
	{
		this.name = name;
		this.sprite = new Sprite(filepath);
		this.x = this.y = this.z = 0;
		//position = new Point(0,0);
	}
	
	public Entity(String name, Sprite sprite)
	{
		this.name = name;
		this.sprite = sprite;
		this.x = this.y = this.z = 0;
		//position = new Point(0,0);
	}
	
	public Entity(String name, Sprite sprite, int x, int y)
	{
		this.name = name;
		this.sprite = sprite;
		this.setXY(x, y);
	}
	
	
	public void setXY(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	
}
