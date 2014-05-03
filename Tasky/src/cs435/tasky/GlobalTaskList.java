package cs435.tasky;

import java.util.ArrayList;

import android.app.Application;
/**
 * This class will behave as a global variable in a sense that will be used to
 * store a reference to the task list across all activities.
 * @author Jarrett Gabel
 *
 */
public class GlobalTaskList extends Application{

	ArrayList<Task> taskList = new ArrayList<Task>();	
	ArrayList<Folder> folderList = new ArrayList<Folder>();
	String username = "";
	String password = "";
	public GlobalTaskList()
	{
		
	}
	
	public ArrayList<Task> getTaskList()
	{
		return taskList;
	}
	
	public void setTaskList(ArrayList<Task> list)
	{
		taskList = list;
	}
	
	public ArrayList<Folder> getFolderList()
	{
		return folderList;
	}
	
	public void setFolderList(ArrayList<Folder> l)
	{
		folderList = l;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setUsername(String u)
	{
		username = u;
	}
	
	public void setPassword(String p)
	{
		password = p;
	}
}
