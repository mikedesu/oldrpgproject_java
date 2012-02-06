import java.util.ArrayList;


public class DebugEntityMenu 
{
	int selected;
	String title;
	ArrayList<String> menu;
	
	public DebugEntityMenu()
	{
		selected = 0;
		title = "Debug Entity";
		menu = new ArrayList<String>();
		
		menu.add("Create New Entity");
		menu.add("Delete Entity");
		//menu.add("Create New Entity Type");
		//menu.add("Edit Existing Entity");
		//menu.add("Edit Existing Entity Type");
		//menu.add("About");
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
