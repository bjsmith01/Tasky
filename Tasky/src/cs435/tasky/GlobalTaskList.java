package cs435.tasky;

import java.util.ArrayList;

import android.app.Application;
/**
 * This class will behave as a global variable in a sense that will be used to
 * store a reference to the task list across all activities.
 * @author Jarr
 *
 */
public class GlobalTaskList extends Application{

	ArrayList<Task> taskList = new ArrayList<Task>();	

	public GlobalTaskList()
	{
		
	}
	
	public ArrayList<Task> getList()
	{
		return taskList;
	}
	
	public void setList(ArrayList<Task> list)
	{
		taskList = list;
	}
	
}
