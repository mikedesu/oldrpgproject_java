import java.util.ArrayList;


public class EntityMenu 
{
	int selected;
	String title;
	ArrayList<String> menu;
	
	public EntityMenu()
	{
		selected = 0;
		title = "Entity Menu";
		menu = new ArrayList<String>();
		menu.add("Default entry");
		
	}
	
	public EntityMenu(Entity e)
	{
		this();
		menu.removeAll(menu);
		menu.add("Move");
		menu.add("Free Move");
		menu.add("Status");
		menu.add("Info");
		menu.add("Help");
	}
	
	public void add(String s)
	{
		menu.add(s);
	}
	
	public String get(int i)
	{
		return menu.get(i);
	}
	
	public int size()
	{
		return menu.size();
	}

}
