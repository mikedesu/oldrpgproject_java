import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Renderer 
{
	//state
	GameWorld world;
	
	
	
	public Renderer(GameWorld world, int x, int y, int width, int height)
	{
		this.world = world;
		//this.world.camera = new Rectangle(x * world.DEFAULT_TILE_SIZE, y * world.DEFAULT_TILE_SIZE, width, height);
	}
	
	public void update(Graphics g, int frame)
	{
		this.clear(g);
		//this.drawCameraView(g);
		//this.drawMap(g);
		this.drawMapWithOffset(g, world.camera.x, world.camera.y);
		this.drawEntities(g);
		this.drawDebugPanel(g, frame, world.input.entity);	
		if (world.state==world.STATE_ENTER_TEXT)
			this.drawMessageBuffer(g);
		else if (world.state==world.STATE_ENTITY_MENU)
			this.drawEntityMenu(g, world.selected);
		else if (world.state==world.STATE_HELP)
			this.drawHelpMenu(g);
		else if (world.state==world.STATE_ENTITY_MOVE) { }
	}
	
	public void clear(Graphics g)
	{
		g.clearRect(world.camera.x, world.camera.y, world.camera.width, world.camera.height);
	}
	
	public void drawMessageBuffer(Graphics g)
	{
		int x, y, dx, dy;
		x = 10;
		y = 400;
		dx = 5;
		dy = 15;
		
		g.drawImage(world.sprites.get("menu").image, x, y, x+world.camera.width - 100, y+100, 0, 0, 64, 64, null);
		
		//g.setColor(Color.blue);
		//g.fillRect(x, y, world.camera.width - 100, 100);
		
		g.setColor(Color.white);
		g.drawString(world.messageBuffer, x + dx, y + dy);
	}
	
	
	
	public void drawDebugPanel(Graphics g, int frame, Entity cursor)
	{
		int x = 5;
		int y = 5;
		
		g.drawImage(world.sprites.get("menu").image, x, y, x+world.camera.width - 10, y+110, 0, 0, 64, 64, null);
		
		g.setFont(new Font("Courier New", Font.PLAIN, 16));
		g.setColor(Color.white);
		g.drawString("Frame: "+frame, x+=5, y+=15);
		g.drawString("This is the debug panel. Configure it in Renderer.java", x, y+=15);
		
		//cursor
		g.drawString("Cursor: " + cursor.x + ", " + cursor.y + "", x, y+=15);
		
		//camera
		g.drawString("Camera: x:" + world.camera.x + "  y:" + world.camera.y + "", x, y+=15);
		
		//world state
		g.drawString("World State: " + world.getStringForState(world.state), x, y+=15);
		
		//selected entity
		String m = "Selected Entity: ";
		if (world.selected!=null)
			m += world.selected.name;
		else
			m += "null";
			
		g.drawString(m, x, y+=15);
		
		
		//draw messages in most recent->least recent order...most recent N messages
		int count = 0;
		int maxMessages = 10;
		y = 120;
		int y2 = y, dy1 = 18, dy2 = 20, dy3 = 10;
		int lastmsg = -1;
		
		//g.drawImage(world.sprites.get("menu").image, x, y, x+world.camera.width - 10, y+160, 0, 0, 64, 64, null);

		//g.drawImage(world.sprites.get("menu").image, x, y, x+world.camera.width - 10, y+5, 0, 0, 64, 64, null);
		for (int i=world.messages.size()-1; i>=0 && count<maxMessages; count++)
		{
			g.drawImage(world.sprites.get("menu").image, x-5, y2, x+world.camera.width - 15, y2+=dy2, 0, 0, 64, 64, null);
			g.drawString(world.messages.get(i--), x, y+=dy1);
		}
		//g.drawImage(world.sprites.get("menu").image, x, y, x+world.camera.width - 10, y+5, 0, 0, 64, 64, null);
		

		
	}
	
	public void drawCameraView(Graphics g)
	{
		g.setColor(Color.orange);
		g.fillRect(world.camera.x, world.camera.y, world.camera.width, world.camera.height);
	}
	
	public void drawMapTile(Graphics g, char tile, int x, int y)
	{	
		int dx1,dx2,dy1,dy2,sx1,sx2,sy1,sy2;
		dx1 = x;
		dy1 = y;
		dx2 = dx1 + world.DEFAULT_TILE_SIZE;
		dy2 = dy1 + world.DEFAULT_TILE_SIZE;
		sx1 = 0;
		sy1 = 0;
		sx2 = world.DEFAULT_TILE_SIZE;
		sy2 = world.DEFAULT_TILE_SIZE;
		
		if (tile=='0') {
			//g.setColor(Color.black);
			//g.fillRect(x, y, sx2, sy2);
			g.drawImage(this.world.sprites.get("tile_empty").image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}
		else if (tile=='#') {
			g.drawImage(this.world.sprites.get("tile_stone").image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}
		
		else if (tile=='.') {
			g.drawImage(this.world.sprites.get("tile_grass").image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}	
	}
	
	public void drawMap(Graphics g)
	{
		int x = 0;
		int y = 0;
		for (int i=0; i<world.map.length; i++)
		{
			x = 0;
			for (int j=0; j<world.map[i].length; j++)
			{
				this.drawMapTile(g, world.map[i][j], x, y);
				x += world.DEFAULT_TILE_SIZE + world.DEFAULT_DISTANCE_BETWEEN_TILES;
			}
			y += world.DEFAULT_TILE_SIZE + world.DEFAULT_DISTANCE_BETWEEN_TILES;
		}
	}
	
	public void drawMapWithOffset(Graphics g, int x, int y)
	{
		int _x = x;
		int _y = y;
		for (int i=0; i<world.map.length; i++)
		{
			_x = x;
			for (int j=0; j<world.map[i].length; j++)
			{
				this.drawMapTile(g, world.map[i][j], _x, _y);
				_x += world.DEFAULT_TILE_SIZE + world.DEFAULT_DISTANCE_BETWEEN_TILES;
			}
			_y += world.DEFAULT_TILE_SIZE + world.DEFAULT_DISTANCE_BETWEEN_TILES;
		}
	}
	public void drawEntity(Graphics g, Entity e)
	{
		int dx1,dx2,dy1,dy2;
		//dx1 = (e.x * world.DEFAULT_TILE_SIZE) + e._x + world.camera.x;
		//dy1 = (e.y * world.DEFAULT_TILE_SIZE) + e._y + world.camera.y;
		dx1 = (e.x * world.DEFAULT_TILE_SIZE) + world.camera.x;
		dy1 = (e.y * world.DEFAULT_TILE_SIZE) + world.camera.y;
		
		dx2 = dx1 + world.DEFAULT_TILE_SIZE;
		dy2 = dy1 + world.DEFAULT_TILE_SIZE;
		
		//if (e._x<0 && e._y<0) { e._x++; e._y++; }
		//if the entity is selected, draw the cursor under it before drawing it
		if (e == world.selected)
			g.drawImage(world.sprites.get("cursor_blue").image, dx1, dy1, dx2, dy2, 0, 0, world.DEFAULT_TILE_SIZE, world.DEFAULT_TILE_SIZE, null);
		
		g.drawImage(e.sprite.image, dx1, dy1, dx2, dy2, 0, 0, world.DEFAULT_TILE_SIZE, world.DEFAULT_TILE_SIZE, null);
	}
	
	
	public void drawEntities(Graphics g)
	{
		for (int i=0; i<world.entities.size(); i++)
		{
			Entity e = world.entities.get(i);
			
			this.drawEntity(g, e);
		}
	}
	
	public void drawEntityMenu(Graphics g, Entity e)
	{
		if (this.world.menu == null)
			this.world.menu = new EntityMenu(e);
		
		int dx1, dx2, dy1, dy2, sx1, sx2, sy1, sy2;
		dx1 = world.camera.width / 2 + 40;
		dy1 = 50;
		dx2 = dx1 + 350;
		dy2 = dy1 + 200;
		
		sx1 = 0;
		sy1 = 0;
		sx2 = world.DEFAULT_TILE_SIZE;
		sy2 = world.DEFAULT_TILE_SIZE;
		
		g.drawImage(this.world.sprites.get("bluemenu").image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		
		g.setColor(Color.white);
		g.setFont(new Font("Courier New", Font.PLAIN, 16));
		
		//dy1+=5;
		for (int i=0; i<this.world.menu.size(); i++)
		{
			if (this.world.menu.selected==i)
				g.drawString("> " + this.world.menu.get(i), dx1+10, dy1+=20);
			else
				g.drawString(this.world.menu.get(i), dx1+10, dy1+=20);
		}
		
		//g.drawString("", dx1, dy1+=15);	
		//g.drawString("Nothing else to see here", dx1, dy1+=15);	
		
	}
	
	public void drawHelpMenu(Graphics g)
	{
		int dx1, dx2, dy1, dy2, sx1, sx2, sy1, sy2;
		dx1 = 100;
		dy1 = 100;
		dx2 = dx1 + 600;
		dy2 = dy1 + 400;
		
		sx1 = 0;
		sy1 = 0;
		sx2 = world.DEFAULT_TILE_SIZE;
		sy2 = world.DEFAULT_TILE_SIZE;
		
		g.drawImage(this.world.sprites.get("bluemenu").image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		g.setColor(Color.white);
		g.setFont(new Font("Courier New", Font.PLAIN, 16));
		//g.drawString("Entity: " + e.name, dx1+=5, dy1+=15);
		g.drawString("Help Menu", dx1+=5, dy1+=15);	
		g.drawString("", dx1, dy1+=15);	
		g.drawString("", dx1, dy1+=15);	
		g.drawString("Default state:", dx1, dy1+=15);
		g.drawString("", dx1, dy1+=15);	
		g.drawString("  Arrow keys - Move cursor", dx1, dy1+=15);	
		g.drawString("  Enter - Select", dx1, dy1+=15);
		g.drawString("  Esc - Cancel", dx1, dy1+=15);	
		g.drawString("  ? - Help Menu", dx1, dy1+=15);	
		g.drawString("  c - Clear Messages", dx1, dy1+=15);	
		
		g.drawString("Press Esc to return", dx1, dy1+=30);	
		
		
		
	}
}
