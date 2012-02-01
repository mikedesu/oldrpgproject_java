import java.awt.event.KeyEvent;


public class InputController
{	
	int key, new_key;
	Entity entity;
	
	public InputController()
	{
		this.key = 0;
		this.new_key = 0;
		this.entity = null;
	}
	
	public void update()
	{
		this.key = this.new_key;
		this.new_key = 0;
		//System.out.println("InputController.update()");
	}
	
	public void set(int key)
	{
		this.new_key = key;
		System.out.println("Key: " + KeyEvent.getKeyText(key) + " " + key);
	}
}
