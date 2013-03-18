import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;


public class GameWorld 
{
	final int STATE_DEFAULT = 0;
	final int STATE_ENTER_TEXT = 1;
	final int STATE_ENTITY_MENU = 2;
	final int STATE_ENTITY_MOVE = 4;
	final int STATE_ENTITY_FREEMOVE = 8;
	final int STATE_HELP = 3;
	final int STATE_DEBUG_MENU = 5;
	final int STATE_DEBUG_ENTITY_MENU = 6;
	
	
	public String getStringForState(int state) {
		String s = "";
		if (state==STATE_DEFAULT) s = "STATE_DEFAULT";
		else if (state==STATE_ENTER_TEXT) s = "STATE_ENTER_TEXT";
		else if (state==STATE_ENTITY_MENU) s = "STATE_ENTITY_MENU";
		else if (state==STATE_HELP) s = "STATE_HELP";
		else if (state==STATE_ENTITY_MOVE) s = "STATE_ENTITY_MOVE";
		else if (state==STATE_DEBUG_MENU) s = "STATE_DEBUG_MENU";
		else if (state==STATE_DEBUG_ENTITY_MENU) s = "STATE_DEBUG_ENTITY_MENU";
		else if (state==STATE_ENTITY_FREEMOVE) s = "STATE_ENTITY_FREEMOVE";
		
		return s;
	}
	
	int state, _state;
	
	EntityMenu menu = null;
	DebugMenu debugMenu = null;
	
	final int DEFAULT_TILE_SIZE = 64;
	final int DEFAULT_DISTANCE_BETWEEN_TILES = 0;
	
	InputController input;
	Rectangle camera;
	int camera_move_x = 0;
	int camera_move_y = 0;

	SpriteMap sprites;
	ArrayList<Entity> entities;
	//Hashtable<String, Entity> entities;
	ArrayList<String> messages;
	String messageBuffer;
	
	Entity selected;
	
	
	char map[][];
	
	//initializes world with empty entity list and empty map
	public GameWorld(InputController input)
	{
		state = 0;
		_state = 0;
		this.input = input;
		entities = new ArrayList<Entity>();
		//entities = new Hashtable<String, Entity>();
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
		map.add("#######");
		map.add("#.....#");
		map.add("#.....#");
		map.add("#.....#");
		map.add("#######");

		
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
		
		
		Entity cursor = new Entity("Cursor", sprites.get("cursor_blue"), 6, 5);
		this.entities.add(cursor);
		
		//Entity enemy = new Entity("Enemy", sprites.get("enemy"), 7, 7);
		
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
		//System.out.println("Handle key press");
		//just a cursor...
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
						ArrayList<Entity> entitiesAtCell = this.getEntities(input.entity.x, input.entity.y);
						if (entitiesAtCell.size()>1)
							for (int i=0; i<entitiesAtCell.size(); i++)
							{
								Entity e = entitiesAtCell.get(i);
								messages.add("Entity " + e.name + " at cell x:" + e.x + " y:" + e.y + " z:" + e.z);
							}
				
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
									_state = STATE_DEFAULT;
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
			case 27:	if (selected!=null)
							selected = null;
						else {
							state = STATE_DEBUG_MENU;
							_state = STATE_DEFAULT;
						}
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
				_state = STATE_ENTER_TEXT;
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
					state = STATE_ENTITY_MOVE;
					_state = STATE_ENTITY_MENU;
					messages.add("Move " + selected.name + " to where?");
				}
				
				else if (selectedstring.equalsIgnoreCase("status")) {
					
				}
				else if (selectedstring.equalsIgnoreCase("info")) {
					
				}
				else if (selectedstring.equalsIgnoreCase("help")) {
					state=STATE_HELP;
					_state = STATE_ENTITY_MENU;
				}
				else if (selectedstring.equalsIgnoreCase("free move")) {
					state=STATE_ENTITY_FREEMOVE;
					_state = STATE_ENTITY_MENU;
					
				}
			}
			
			//esc
			else if (key==27) {
				state = STATE_DEFAULT;
				_state = STATE_ENTITY_MENU;
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
				state = _state;
				_state = STATE_HELP;
				messages.add("Exitted help");
			}
		}
		
		else if (state==STATE_ENTITY_FREEMOVE)
		{
			//enter 10
			if (key==10) {
				messages.add("Hit enter in freemove");
			}
			//esc 27
			else if (key==27) {
				state = _state;
				_state = STATE_ENTITY_FREEMOVE;
				messages.add("Exitted freemove");
			}
			//LURD  37 38 39 40
			else if (key==37) {
				this.moveEntity(selected, -1, 0);
				this.moveEntity(input.entity, -1, 0);
				//this.moveCamera(-1, 0);
			}
			else if (key==38) {
				this.moveEntity(selected, 0, -1);
				this.moveEntity(input.entity, 0, -1);
				//this.moveCamera(0, -1);
			}
			else if (key==39) {
				this.moveEntity(selected, 1, 0);
				this.moveEntity(input.entity, 1, 0);
				//this.moveCamera(1, 0);
			}
			else if (key==40) {
				this.moveEntity(selected, 0, 1);
				this.moveEntity(input.entity, 0, 1);
				//this.moveCamera(0, 1);
			}
			
		}
		
		else if (state==STATE_ENTITY_MOVE)
		{
			//enter
			if (key==10) {
				//messages.add("Hit enter");
				int dx = input.entity.x - selected.x;
				int dy = input.entity.y - selected.y;
				System.out.println("dx: " + dx + " dy: " + dy);
				moveEntity(selected, dx, dy);
				//back to default state...leave unit selected. You'll most likely use it again
				state = STATE_DEFAULT;
				_state = STATE_ENTITY_MOVE;
				//selected = null;
			}
			
			//esc
			if (key==27) {
				state = STATE_ENTITY_MENU;
				_state = STATE_ENTITY_MOVE;
				//messages.add("Exitted help");
			}
			//left
			else if (key==37) {
				moveEntity(input.entity, -1, 0);
			}
			
			//up
			else if (key==38) {
				moveEntity(input.entity, 0, -1);
			}
			
			//right
			else if (key==39) {
				moveEntity(input.entity, 1, 0);
			}
			
			//down
			else if (key==40) {
				moveEntity(input.entity, 0, 1);
			}

		}
		
		else if (state==STATE_DEBUG_MENU)
		{
			//enter
			if (key==10) {
				String selectedstring = this.debugMenu.get(this.debugMenu.selected);
				messages.add("Selected " + selectedstring + " from the debugMenu");
				
				//i don't think we'll have a "move" case in the debug menu...
				//more like "load map", "spawn ___", "do impossible X", etc
				if (selectedstring.equalsIgnoreCase("entity")) {
					//need to draw a 2nd menu...DebugEntityMenu
					state = STATE_DEBUG_ENTITY_MENU;
					_state = STATE_DEBUG_MENU;
				}
				else if (selectedstring.equalsIgnoreCase("status")) {
					
				}
				else if (selectedstring.equalsIgnoreCase("info")) {
					
				}
				else if (selectedstring.equalsIgnoreCase("help")) {
					state=STATE_HELP;
					_state = STATE_DEBUG_MENU;
				}
			}
			
			//esc
			else if (key==27) {
				state = STATE_DEFAULT;
				_state = STATE_DEBUG_MENU;
			}
			
			//left
			else if (key==37) {
				//moveEntity(selected, -1, 0); 
			}
			//up
			else if (key==38) {
				//moveEntity(selected, 0, -1); 
				if (this.debugMenu.selected>0) this.debugMenu.selected--;
			}
			//right
			else if (key==39) {
				//moveEntity(selected, 1, 0); 
			}
			//down
			else if (key==40) {
				//moveEntity(selected, 0, 1); 
				if (this.debugMenu.selected<this.debugMenu.size()-1) this.debugMenu.selected++;
			}
		}
		
		else if (state==STATE_DEBUG_ENTITY_MENU)
		{
			//enter
			if (key==10) {
				String selected = this.debugMenu.submenu.get(this.debugMenu.selected_sub);
				messages.add("Selected " + selected);
				
				if (selected.equalsIgnoreCase("create new entity")) {
					//create entity menu
					//for now, just drop an enemy
					Entity tmp = new Entity("Enemy", sprites.get("enemy"), this.input.entity.x, this.input.entity.y);
					this.entities.add(tmp);
					messages.add("Created new entity at x: "+this.input.entity.x + " y: "+this.input.entity.y );
				}
				
				else if (selected.equalsIgnoreCase("delete entity")) {
					//delete entity at space				
					for (int i=0; i<entities.size(); i++) {
						Entity e = entities.get(i);
						//make sure we dont remove the cursor
						if (this.input.entity != e && e.x == input.entity.x && e.y == input.entity.y) {
							//remove entity
							try {
							entities.remove(e);
							} catch (Exception ex) { ex.printStackTrace(); }
						}
					}
				}
				else if (selected.equalsIgnoreCase("list entities")) {
					//list entities on console
					for (Entity e : this.entities) {
						System.out.println("Entity: " + e);
					}
				}
				//else if (selected.equalsIgnoreCase("create new entity type")) {
					
				//}
				

				
				
			}
			//esc
			else if (key==27) {
				//go back to previous state
				//int __state = state;
				state = _state;
				//_state = __state;
			}
			//left
			else if (key==37) {
				//moveEntity(selected, -1, 0); 
			}
			//up
			else if (key==38) {
				//moveEntity(selected, 0, -1); 
				if (this.debugMenu.selected_sub>0) this.debugMenu.selected_sub--;
			}
			//right
			else if (key==39) {
				//moveEntity(selected, 1, 0); 
			}
			//down
			else if (key==40) {
				//moveEntity(selected, 0, 1); 
				if (this.debugMenu.selected_sub<this.debugMenu.submenu.size()-1) this.debugMenu.selected_sub++;
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
		else
		{
			if (! (e.x+dx < 0 || e.y+dy < 0 || e.x+dx >= map[0].length || e.y+dy >= map.length))
			{
				e.x += dx;
				e.y += dy;
				//e._x = - dx * DEFAULT_TILE_SIZE;
				//e._y = - dy * DEFAULT_TILE_SIZE;
				//this.moveCamera(-dx * DEFAULT_TILE_SIZE, -dy * DEFAULT_TILE_SIZE);
				//this.moveCamera(dx, dy);
			}
			else {
				//System.err.println("Attempted to take cursor outside of map! " + (e.x+dx) + " " + (e.y+dy) + "");
				messages.add("Attempted to take entity outside of map! " + (e.x+dx) + " " + (e.y+dy) + "");
			}
		}
		
	}
	
	public ArrayList<Entity> getEntities(int x, int y)
	{
		//loop thru entities, 
		ArrayList<Entity> eList = new ArrayList<Entity>();
		for (Entity e : entities)
		{
			if (e.x==x && e.y==y && e!=input.entity)
				eList.add(e);
		}
		
		return eList;
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
