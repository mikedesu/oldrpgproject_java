import java.awt.Rectangle;
import java.util.ArrayList;


public class GameWorld 
{
	//int state;
	InputController input;
	Rectangle camera;

	SpriteMap sprites;
	ArrayList<Entity> entities;
	ArrayList<String> messages;
	
	
	char map[][];
	
	//initializes world with empty entity list and empty map
	public GameWorld(InputController input)
	{
		this.input = input;
		entities = new ArrayList<Entity>();
		//this.fillMapWithTile('0');
		sprites = new SpriteMap();
	}
	
	public void init() 
	{	
		ArrayList<String> map2 = new ArrayList<String>();
		map2.add("##################################");
		map2.add("#................................#");
		map2.add("#................................#");
		map2.add("#................................#");
		map2.add("#................................#");
		map2.add("#................................#");
		map2.add("#................................#");
		map2.add("#................................#");
		map2.add("#................................#");
		map2.add("#................................#");
		map2.add("#................................#");
		map2.add("#................................#");
		map2.add("##################################");
		
		
		this.loadMap(map2);
		
		//set the sprites and their keys
		this.sprites.add("Selector",   "res/selector.png");	
		this.sprites.add("tile_grass", "res/tiles/grass.png");
		this.sprites.add("tile_dirt",  "res/tiles/dirt.png");
		this.sprites.add("tile_stone", "res/tiles/stone.png");
		this.sprites.add("tile_water", "res/tiles/water.png");
		this.sprites.add("tile_empty", "res/tiles/empty.png");
				
		Entity selector = new Entity("Selector", sprites.get("Selector"));
		selector.x = 5;
		selector.y = 5;
		this.entities.add(selector);
		
		input.entity = selector;
	}
	
	public void loadMap(String[] map)
	{
		this.map = new char[map.length][map[0].length()];

		for (int i=0; i<map.length; i++)
			for (int j=0; j<map[0].length(); j++)
				this.map[i][j] = map[i].charAt(j);
	}
	
	public void loadMap(ArrayList<String> map)
	{
		this.map = new char[map.size()][map.get(0).length()];

		for (int i=0; i<map.size(); i++)
			for (int j=0; j<map.get(i).length(); j++)
				this.map[i][j] = map.get(i).charAt(j);		
	}
	
	
	public void fillMapWithTile(char tile)
	{
		
		for (int i=0; i<map.length; i++)
			for (int j=0; j<map[i].length; j++)
				map[i][j] = tile;
	}
	
	
	public void update()
	{
		//System.out.println("GameWorld.update()");
		
		//check the input for a key...
		if (this.input.key!=0)
		{
			//accept the keypress
			System.out.println("Accepted keypress " + this.input.key);
			
			//handle it 
			this.handleKeyPress(this.input.key);
			
			//zero it out
			this.input.key = 0;
		}
	}
	
	
	public void handleKeyPress(int key)
	{
		switch (key)
		{
		case 0: break;
		//left
		case 37: moveEntity(input.entity, -1, 0); 
				 break;
		//up
		case 38: moveEntity(input.entity, 0, -1); 
				 //camera.y+=64; 
				 break;
		//right
		case 39: moveEntity(input.entity, 1, 0); 
				 //camera.x-=64; 
				 break;
		//down
		case 40: moveEntity(input.entity, 0, 1); 
				 //camera.y-=64; 
				 break;
		}
	}
	
	
	public void moveEntity(Entity e, int dx, int dy)
	{
		//if cursor...
		if (e==input.entity)
		{
			if (! (e.x+dx < 0 || e.y+dy < 0 || e.x+dx >= map[0].length || e.y+dy >= map.length))
			{
				e.x += dx;
				e.y += dy;
				
				camera.x+= (64 * -dx);
				camera.y+= (64 * -dy);
			}
		}
		
		
		//if not cursor...
		//check the new position to see if it is occupied
	}
}
