/**
 * This class creates the Task objects.  
 */
package cs435.tasky;

import java.io.Serializable;
import java.util.GregorianCalendar;


/**
 * implementation of Task class
 * @author Brandon
 *
 */
public class Task implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private GregorianCalendar dueDate;
	private boolean hasDueDate = false;
	private boolean completed;
	private long id = 0;
	private int priority = 0;
	
	/**
	 * constructor for Task class
	 * @param String Name
	 * @param String Desc
	 * @param GregorianCalendar DueDate
	 */
	public Task(String Name, GregorianCalendar DueDate){
		name=Name;
		dueDate=DueDate;
		hasDueDate = true;
		completed=false;
	}
	
	/**
	 * Constructor for task without reminder or due date
	 * @param String Name
	 */
	public Task(String Name, String Desc){
		name=Name;
		completed=false;
	}

	/**
	 * set the name of the task
	 * @param String setName
	 */
	public void setName(String setName){
		name = setName;
	}
	
	/**
	 * return the name of the task
	 * @return String name
	 */
	public String getName(){
		return name;
	}

	/**
	 * get the due date of the task
	 * @return GregorianCalendar duedate
	 */
	public GregorianCalendar getDueDate() {
		if (hasDueDate)
		{
			return dueDate;
		}
		else
		{
			return new GregorianCalendar(1, 1, 1);
		}

	}

	/**
	 * set the dueDate of the task
	 * @param GregorianCalendar dueDate
	 */
	public void setDueDate(GregorianCalendar dueDate) {
		this.dueDate = dueDate;
		hasDueDate = true;
	}

	/**
	 * return if the task has been completed
	 * @return boolean completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * set the event has been completed
	 * @param isCompleted
	 */
	public void setCompleted(boolean isCompleted) {
		completed = isCompleted;
	}

	public long getID()
	{
		return id;
	}
	
	public void setID(long taskID)
	{
		id = taskID;
	}
	
	public void setPriority(int newP)
	{
		priority = newP;
	}
	
	public int getPriority()
	{
		return priority;
	}
}
