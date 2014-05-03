package cs435.tasky;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class Folder implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	String name;
	int id;
	List<Task> TaskList = new ArrayList<Task>();
	
	public Folder (String folderName){
		name=folderName;
	}
	
	public Folder(ArrayList<Task> taskList2) {
		TaskList = taskList2;
	}

	/**
	 * adds a task to the folder
	 * @param Task newTask
	 */
	void AddTask(Task newTask){
		//Log.i(""+name,"Task Added");
		TaskList.add(newTask);
	}
	
	/**
	 * get the task a specific location
	 * @param int taskIndex
	 * @return Task object
	 */
	Task getTask(int taskIndex){
		return TaskList.get(taskIndex);
	}
	
	/**
	 * deletes a particular task from list
	 * @param delTask
	 */
	void deleteTask (Task delTask){
		Log.i(""+name, "Deleted Task");
		TaskList.remove(delTask);		
	}
	public void setID(int newID)
	{
		id = newID;
	}
	
	public int getID()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}
	
	void changeList(ArrayList<Task> tasks){
		TaskList.clear();
		TaskList.addAll(tasks);
	}
	
}
