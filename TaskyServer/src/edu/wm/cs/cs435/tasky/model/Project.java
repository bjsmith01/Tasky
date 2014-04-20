package edu.wm.cs.cs435.tasky.model;

import java.util.ArrayList;

/**
 * This represents a group of tasks (see @Task)
 * 
 * @author Fengfeng (Mia) Liu
 *
 */
public class Project
{
	private int id;
	private String name;
	private ArrayList<Task> listOfTasks;

	/**
	 * Create a new project and initialize an empty lists of tasks
	 * @param name
	 */
	public Project(String name)
	{
		this.setName(name);
		
		this.id=IDs.getNextAvailableProjectID();
		
		this.listOfTasks=new ArrayList<>();
	}

	public Project(int projectID, String projectName)
	{
		this.id=projectID;
		this.setName(projectName);
		this.listOfTasks=new ArrayList<>();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void addTask(Task task)
	{
		this.getListOfTasks().add(task);
	}

	public ArrayList<Task> getListOfTasks()
	{
		return listOfTasks;
	}

	public int getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return "Project[id="+id+", name="+name+"]";
	}
}
