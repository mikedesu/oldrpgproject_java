import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class GameWorld 
{
	final int STATE_DEFAULT = 0;
	final int STATE_ENTER_TEXT = 1;
	final int STATE_ENTITY_MENU = 2;
	final int STATE_ENTITY_MOVE = 4;
	final int STATE_HELP = 3;
	
	public String getStringForState(int state) {
		String s = "";
		if (state==STATE_DEFAULT) s = "STATE_DEFAULT";
		else if (state==STATE_ENTER_TEXT) s = "STATE_ENTER_TEXT";
		else if (state==STATE_ENTITY_MENU) s = "STATE_ENTITY_MENU";
		else if (state==STATE_HELP) s = "STATE_HELP";
		else if (state==STATE_ENTITY_MOVE) s = "STATE_ENTITY_MOVE";
		return s;
	}
	
	int state;
	
	EntityMenu menu = null;
	
	final int DEFAULT_TILE_SIZE = 64;
	final int DEFAULT_DISTANCE_BETWEEN_TILES = 0;
	
	InputController input;
	Rectangle camera;
	int camera_move_x = 0;
	int camera_move_y = 0;

	SpriteMap sprites;
	ArrayList<Entity> entities;
	ArrayList<String> messages;
	String messageBuffer;
	
	Entity selected;
	
	
	char map[][];
	
	//initializes world with empty entity list and empty map
	public GameWorld(InputController input)
	{
		state = 0;
		this.input = input;
		entities = new ArrayList<Entity>();
		//this.fillMapWithTile('0');
		sprites = new SpriteMap();
		selected = null;
		camera = new Rectangle(0, 0, 800, 600);
		messages = new ArrayList<String>();
		messages.add("Welcome to Mikehack!");
		messageBuffer = "";
	}
	
	public void init() 
	{	
		ArrayList<String> map = new ArrayList<String>();
		map.add("##################################");
		map.add("#................................#");
		map.add("#................................#");
		map.add("#................................#");
		map.add("#................................#");
		map.add("#................................#");
		map.add("#................................#");
		map.add("#................................#");
		map.add("#................................#");
		map.add("#................................#");
		map.add("#................................#");
		map.add("#................................#");
		map.add("##################################");
		this.loadMap(map);
		
		//set the sprites and their keys
		this.sprites.add("cursor_green",     "res/cursor_green.png");
		this.sprites.add("cursor_blue",      "res/cursor_blue.png");
		
		this.sprites.add("tile_grass", "res/tiles/grass.png");
		this.sprites.add("tile_dirt",  "res/tiles/dirt.png");
		this.sprites.add("tile_stone", "res/tiles/stone.png");
		this.sprites.add("tile_water", "res/tiles/water.png");
		this.sprites.add("tile_empty", "res/tiles/empty.png");
		this.sprites.add("enemy",      "res/enemy.png");
		this.sprites.add("menu",       "res/menu.png");
		this.sprites.add("bluemenu",   "res/bluemenu.png");
		
		
		Entity cursor = new Entity("Cursor", sprites.get("cursor_blue"), 5, 5);
		this.entities.add(cursor);
		
		//Entity enemy = new Entity("Enemy", sprites.get("enemy"), 7, 7);
		
		//ArrayList<Entity> enemies = new ArrayList<Entity>();
		int count = 0;
		for (int i=1; i<15; i+=2) {
			Entity tmp = new Entity("Enemy"+(count++), sprites.get("enemy"), i, 7);
			this.entities.add(tmp);
			messages.add("Spawned " + tmp.name + " at " + i + ", 7");
		}
		//count = 0;
		for (int i=1; i<15; i+=2) {
			Entity tmp = new Entity("Enemy"+(count++), sprites.get("enemy"), i, 5);
			this.entities.add(tmp);
			messages.add("Spawned " + tmp.name + " at " + i + ", 5");
		}
		
		
		
		//this.entities.add(enemy);
		
		//cursor.setXY(enemy.x, enemy.y);
		//camera.x = DEFAULT_TILE_SIZE * ;  camera.y = 256;
		
		
		input.entity = cursor;
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
			//System.out.println("Accepted keypress " + this.input.key);
			
			//handle it 
			this.handleKeyPress(this.input.key);
			
			//zero it out
			this.input.key = 0;
		}
	}
	
	
	public void handleKeyPress(int key)
	{
		if (state==STATE_DEFAULT)
		{
			switch (key)
			{
			case 0: break;
			
			//enter
			case 10: 	//messages.add("Hit enter!");
						//open a "chat box" and start typing
						//state = STATE_ENTER_TEXT;
				
						//select current entity at cell and bring up menu for that entity
						for (int i=0; i<entities.size(); i++)
						{
							Entity e = entities.get(i);
							if (e != null && e != input.entity && e != selected)
							{
								if (e.x == input.entity.x && e.y == input.entity.y)
								{
									messages.add("Selected entity " + e +" at x:"+e.x+" y:"+e.y);
									selected = e;
									state = STATE_ENTITY_MENU;
									break;
								}
							}
							else
							{
								selected = null;
							}		
						}
						if (selected==null)
							messages.add("Nothing selected!");
						break;
			//esc
			case 27:	selected = null;
						break;
			
			//left
			case 37: 	moveEntity(input.entity, -1, 0); 
					 	break;
			//up
			case 38: 	moveEntity(input.entity, 0, -1); 
					 	//camera.y+=64; 
					 	break;
			//right
			case 39: 	moveEntity(input.entity, 1, 0); 
					 	//camera.x-=64; 
					 	break;
			//down
			case 40: 	moveEntity(input.entity, 0, 1); 
					 	//camera.y-=64; 
					 	break;
			//'? /'
			/*
			case 47:	messages.add("Help button pressed");
						state = STATE_HELP;
						break;
			*/			
			//'C'
			case 67:	//clear messages
						messages.removeAll(messages);
						break;
					 	
			default:	break;
			}
		}

		else if (state==STATE_ENTER_TEXT)
		{
			//hit enter
			if (key==10)
			{
				//close buffer, change state, add buffer to messages
				state = STATE_DEFAULT;
				messages.add(messageBuffer);
				messageBuffer = "";
			}
			else if (key!=16 && key!=17 && key!=18 && key!=20 && key!=157)
			{
				if (key==32)
					messageBuffer += " ";
				else if (key==8)
					messageBuffer = messageBuffer.substring(0, messageBuffer.length()-1);
				else
					messageBuffer += KeyEvent.getKeyText(key);				
			}
		}
		
		else if (state==STATE_ENTITY_MENU)
		{
			
			//enter
			if (key==10) {
				String selectedstring = this.menu.get(this.menu.selected);
				messages.add("Selected " + selectedstring + " from the menu");
				
				if (selectedstring.equalsIgnoreCase("move")) {
					
				}
				
				else if (selectedstring.equalsIgnoreCase("status")) {
					
				}
				else if (selectedstring.equalsIgnoreCase("info")) {
					
				}
				else if (selectedstring.equalsIgnoreCase("help")) {
					state=STATE_HELP;
				}
			}
			
			//esc
			else if (key==27) {
				state = STATE_DEFAULT;
				this.menu = null;
				//messages.add("Exitted menu");
			}
			
			//left
			else if (key==37) {
				//moveEntity(selected, -1, 0); 
			}
			//up
			else if (key==38) {
				//moveEntity(selected, 0, -1); 
				if (this.menu.selected>0) this.menu.selected--;
			}
			//right
			else if (key==39) {
				//moveEntity(selected, 1, 0); 
			}
			//down
			else if (key==40) {
				//moveEntity(selected, 0, 1); 
				if (this.menu.selected<this.menu.size()-1) this.menu.selected++;
			}
		}
		
		else if (state==STATE_HELP)
		{
			//esc
			if (key==27) {
				state = STATE_ENTITY_MENU;
				messages.add("Exitted help");
			}
		}
		
		else if (state==STATE_ENTITY_MOVE)
		{
			//esc
			if (key==27) {
				state = STATE_ENTITY_MENU;
				//messages.add("Exitted help");
			}
		}
		
		
	}
	
	
	public void moveEntity(Entity e, int dx, int dy)
	{
		//move entity
		//attempt to animate the movement
		
		//if cursor...
		if (e==input.entity)
		{
			//if within the map boundaries (0,0) to (map[0].length, map.length)...
			if (! (e.x+dx < 0 || e.y+dy < 0 || e.x+dx >= map[0].length || e.y+dy >= map.length))
			{
				e.x += dx;
				e.y += dy;
				//e._x = - dx * DEFAULT_TILE_SIZE;
				//e._y = - dy * DEFAULT_TILE_SIZE;
				//this.moveCamera(-dx * DEFAULT_TILE_SIZE, -dy * DEFAULT_TILE_SIZE);
				this.moveCamera(dx, dy);
			}
			else {
				System.err.println("Attempted to take cursor outside of map! " + (e.x+dx) + " " + (e.y+dy) + "");
				messages.add("Attempted to take entity outside of map! " + (e.x+dx) + " " + (e.y+dy) + "");
			}
		}
		
	}
	
	public void moveCamera(int dx, int dy)
	{
		camera.x += (DEFAULT_TILE_SIZE * -dx);
		camera.y += (DEFAULT_TILE_SIZE * -dy);
		//camera.x += dx;
		//camera.y += dy;
		
		//camera_move_x += (DEFAULT_TILE_SIZE * -dx);
		//camera_move_y += (DEFAULT_TILE_SIZE * -dy);
	}
	
	
}
