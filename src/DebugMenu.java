import java.util.ArrayList;


public class DebugMenu 
{
	int selected, selected_sub;
	String title;
	ArrayList<String> menu;
	ArrayList<String> submenu;
	
	public DebugMenu()
	{
		selected = selected_sub = 0;
		title = "Debug Menu";
		menu = new ArrayList<String>();
		submenu = new ArrayList<String>();
		
		menu.add("Entity");
		menu.add("Help");
		
		this.createSubMenu(selected);
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
	
	public void clearSubMenu()
	{
		submenu.removeAll(submenu);
	}
	
	public void createSubMenu(int selected) 
	{
		//entity
		if (selected==0) {
			this.clearSubMenu();
			submenu.add("Create New Entity");
			submenu.add("Delete Entity");
			submenu.add("List Entities");
		}
		//help
		else if (selected==1) {
			this.clearSubMenu();
		}
		
	}
}
