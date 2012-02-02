
public class Entity 
{
	String name;
	Sprite sprite;
	//SpriteAnimation animation;
	int x, y, z;
	//int _x, _y, _z;
	//Point position;
	boolean isMoving;
	
	public Entity(String name, String filepath)
	{
		this.name = name;
		this.sprite = new Sprite(filepath);
		this.x = this.y = this.z = 0;
		//this._x = this._y = this._y = 0;
		this.isMoving = false;
		//position = new Point(0,0);
	}
	
	public Entity(String name, Sprite sprite)
	{
		this.name = name;
		this.sprite = sprite;
		this.x = this.y = this.z = 0;
		//this._x = this._y = this._y = 0;
		this.isMoving = false;
		//position = new Point(0,0);
	}
	
	public Entity(String name, Sprite sprite, int x, int y)
	{
		this.name = name;
		this.sprite = sprite;
		this.setXY(x, y);
		//this._x = this._y = this._y = 0;
		this.isMoving = false;
	}
	
	
	public void setXY(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public String toString()
	{
		return this.name;
	}
	
	
}
