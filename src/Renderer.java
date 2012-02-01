import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Renderer 
{
	//state
	GameWorld world;
	
	final int DEFAULT_TILE_SIZE = 64;
	final int DEFAULT_DISTANCE_BETWEEN_TILES = 0;
	
	
	public Renderer(GameWorld world, int x, int y, int width, int height)
	{
		this.world = world;
		this.world.camera = new Rectangle(x * DEFAULT_TILE_SIZE, y * DEFAULT_TILE_SIZE, width, height);
	}
	
	public void update(Graphics g, int frame)
	{
		this.clear(g);
		//this.drawCameraView(g);
		//this.drawMap(g);
		this.drawMapWithOffset(g, world.camera.x, world.camera.y);
		this.drawEntities(g);
		this.drawDebugPanel(g, frame, world.input.entity);	
	}
	
	public void clear(Graphics g)
	{
		g.clearRect(world.camera.x, world.camera.y, world.camera.width, world.camera.height);
	}
	
	
	
	public void drawDebugPanel(Graphics g, int frame, Entity cursor)
	{
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.setColor(Color.white);
		g.drawString("Frame: "+frame, 10, 20);
		g.drawString("This is the debug panel", 10, 35);
		
		//cursor
		g.drawString("Cursor: (" + cursor.x + ", " + cursor.y + ")", 10, 50);
		
		//camera
		g.drawString("Camera: (" + world.camera.x + ", " + world.camera.y + ")", 10, 65);
		
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
		dx2 = dx1 + DEFAULT_TILE_SIZE;
		dy2 = dy1 + DEFAULT_TILE_SIZE;
		sx1 = 0;
		sy1 = 0;
		sx2 = DEFAULT_TILE_SIZE;
		sy2 = DEFAULT_TILE_SIZE;
		
		if (tile=='0') {
			g.setColor(Color.black);
			g.fillRect(x, y, sx2, sy2);
			//g.drawImage(this.world.sprites.get("tile_empty").image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
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
				x += DEFAULT_TILE_SIZE + DEFAULT_DISTANCE_BETWEEN_TILES;
			}
			y += DEFAULT_TILE_SIZE + DEFAULT_DISTANCE_BETWEEN_TILES;
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
				_x += DEFAULT_TILE_SIZE + DEFAULT_DISTANCE_BETWEEN_TILES;
			}
			_y += DEFAULT_TILE_SIZE + DEFAULT_DISTANCE_BETWEEN_TILES;
		}
	}
	
	
	
	public void drawEntities(Graphics g)
	{
		int dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, pad;
		sx1 = 0; 
		sy1 = 0;
		sx2 = DEFAULT_TILE_SIZE;
		sy2 = DEFAULT_TILE_SIZE;
		
		dx1 = 0;
		dy1 = 0;
		dx2 = dx1 + DEFAULT_TILE_SIZE;
		dy2 = dy1 + DEFAULT_TILE_SIZE;
		
		pad = 0;
		
		
		for (int i=0; i<world.entities.size(); i++)
		{
			Entity e = world.entities.get(i);
			
			if (e == this.world.input.entity)
			{
				dx1 = (5) * DEFAULT_TILE_SIZE;
				dy1 = (5) * DEFAULT_TILE_SIZE;
				dx2 = (dx1) + DEFAULT_TILE_SIZE;
				dy2 = (dy1) + DEFAULT_TILE_SIZE;
			}
			else 
			{
				dx1 = e.x * DEFAULT_TILE_SIZE + pad;
				dy1 = e.y * DEFAULT_TILE_SIZE + pad;
				dx2 = dx1 + DEFAULT_TILE_SIZE;
				dy2 = dy1 + DEFAULT_TILE_SIZE;	
			}
			
			g.drawImage(world.entities.get(i).sprite.image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}
	}
}
