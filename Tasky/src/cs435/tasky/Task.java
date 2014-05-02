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
	private String description;
	private GregorianCalendar dueDate;
	private boolean hasDueDate = false;
	private boolean completed;
	private int id = 0;
	private int priority = 0;
	
	/**
	 * constructor for Task class
	 * @param String Name
	 * @param String Desc
	 * @param GregorianCalendar DueDate
	 */
	public Task(String Name, String Desc, GregorianCalendar DueDate){
		name=Name;
		description=Desc;
		dueDate=DueDate;
		hasDueDate = true;
		completed=false;
	}
	
	/**
	 * Constructor for task without reminder or due date
	 * @param String Name
	 * @param String Desc
	 */
	public Task(String Name, String Desc){
		name=Name;
		description=Desc;
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
	 * the description
	 * @return String description
	 */
	public String getDesc() {
		return description;
	}

	/**
	 * set the description of the task
	 * @param String desc
	 */
	public void setDesc(String desc) {
		description = desc;
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

	public int getID()
	{
		return id;
	}
	
	public void setID(int newID)
	{
		id = newID;
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
