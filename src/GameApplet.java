import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class GameApplet extends Applet implements Runnable, KeyListener
{
	int current_frame;
	//int frame_count;
	int target_fps;
	
	Thread t;
	InputController input;
	GameWorld world;
	Renderer renderer;
	
	public void paint(Graphics g)
	{
		renderer.update(g, current_frame);
	}
	
	public void init()
	{
		current_frame = 0;
		//frame_count = 0;
		target_fps = 30;
		
		input = new InputController();
		world = new GameWorld(input);
		renderer = new Renderer(world, 0, 0, 800, 600);
		
		world.init();
		
		this.setBackground(Color.black);
		this.addKeyListener(this);
		t = new Thread(this);
		t.start();
	}
	
	
	static public void main (String argv[]) {
		System.out.println("Running main");
	    final Applet applet = new GameApplet();
	    System.runFinalizersOnExit(true);
	    
	    Frame frame = new Frame (
	                 "MyApplet");
	    frame.addWindowListener (
	                  new WindowAdapter()
	    {
	      public void windowClosing (
	                   WindowEvent event)
	      {
	        applet.stop();
	        applet.destroy();
	        System.exit(0);
	      }
	    });
	    frame.add (
	      "Center", applet);
	    applet.setStub (new MyAppletStub (
	         argv, applet));
	    frame.show();
	    applet.init();
	    applet.start();
	    frame.pack();
	  }
	
	
	
	
	
	

	public void keyPressed(KeyEvent arg0) {
		this.input.set(arg0.getKeyCode());
	}

	public void keyReleased(KeyEvent arg0) {
		
	}

	public void keyTyped(KeyEvent arg0) {
		//this.input.set(arg0.getKeyChar());
	}

	public void run() 
	{
		while (true)
		{
			input.update();
			world.update();
			repaint();
			
			try 
			{
				Thread.sleep(1000 / target_fps);
			}
			catch (Exception e)
			{
				System.out.println("Thread exception caught while in the Applet run() loop");
			}
			current_frame++;
		}
	}
}