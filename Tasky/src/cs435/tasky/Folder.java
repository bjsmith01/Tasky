package cs435.tasky;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class Folder implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	String name;
	List<Task> TaskList = new ArrayList<Task>();
	
	public Folder (String folderName){
		name=folderName;
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
	
	/**
	 * deletes the folder from memory
	 * not currently implemented
	 */
	void deleteFolder(Context c){
		String filename= name+".txt";
		try {
			c.deleteFile(filename);
			Log.i(""+name,"Folder Deleted");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("delete Error",e.getMessage());
		}
		//needs to delete folder file from memory
		
	}

}
