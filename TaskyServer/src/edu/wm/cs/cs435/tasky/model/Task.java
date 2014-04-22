package edu.wm.cs.cs435.tasky.model;


import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


/**
 * Each @Project can have multiple tasks (see @Task)
 * 
 * 
 * @author Fengfeng (Mia) Liu
 */
public class Task
{
	private int id;
	private int projectID;		//id of the parent (i.e., project it belongs to)
	private int priority;		//value between 1 and 5 (1 for high priority, 5 for low priority)
	private String taskDescription;
	private DateTime dueDate;



	public Task(String taskDescription)
	{
		this.setTaskDescription(taskDescription);
		this.id=IDs.getNextAvailableTaskID();
		this.setProjectID(-1);
		this.setPriority(5);

	}

	public Task(String taskDescription, String dueDate)
	{
		this(taskDescription);
		
		convertDueDate(dueDate);
	}

	public Task(String taskDescription, String dueDate, int projectID)
	{
		this(taskDescription,dueDate);
		
		this.setProjectID(projectID);
	}

	public Task(String id,String taskDescription, String dueDate)
	{
		this(taskDescription,dueDate);
		
		this.id=Integer.parseInt(id);
		
	}

	public Task(int taskID, String taskDescription, Date dueDate)
	{
		this.id = taskID;
		this.taskDescription = taskDescription;
		this.dueDate = new DateTime(dueDate); 
	}

	/**
	 * This method converts a due date that may be expressed as textual dates (e.g., "today",
	 * "tomorrow", "next fri", etc.) to actual dates
	 * 
	 * @param dueDate
	 */
	private void convertDueDate(String dueDate)
	{
		DateTime currentDate=CurrentDate.getCurrentDate();

		if ("today".equals(dueDate))
		{
			this.setDueDate(currentDate);
			return;
		}
		if ("tod".equals(dueDate))
		{
			this.setDueDate(currentDate);
			return;
		}
		
		if ("tomorrow".equals(dueDate))
		{
			this.setDueDate(currentDate.plus(Period.days(1)));
			return;
		}
		if ("tom".equals(dueDate))
		{
			this.setDueDate(currentDate.plus(Period.days(1)));
			return;
		}
		
		
		//check if the dueDate is specified as "mon", "Tuesday", "fri", etc.
		//if it is, then we adjust the current date to the date that should be "mon", "Tuesday", etc.
		//by adding to the current date the corresponding number of days (i.e., difference) to the current date
		int dayOfWeekForDueDate = CurrentDate.getDayOfWeekAsInt(dueDate);
		if (dayOfWeekForDueDate != -1)
		{
			System.out.println("Debugging for day of the week..."+dueDate+"("+dayOfWeekForDueDate+")");
			System.out.println("Current date is"+currentDate);
			System.out.println("getDayOfWeek "+currentDate.getDayOfWeek());
			int numberOfDaysToBeAdded = (dayOfWeekForDueDate + 7 - currentDate.getDayOfWeek()) % 7;
			this.setDueDate(currentDate.plus(Period.days(numberOfDaysToBeAdded)));
			return;
		}
	}

	public int getId()
	{
		return id;
	}

	public String getTaskDescription()
	{
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription)
	{
		this.taskDescription = taskDescription;
	}

	public DateTime getDueDate()
	{
		return dueDate;
	}
	
	public Date getDueDateAsJavaData()
	{
		if (dueDate!=null)
			return dueDate.toDate();
		return null;
	}

	public void setDueDate(DateTime dueDate)
	{
		this.dueDate = dueDate;
	}

	public String getDueDateAsShortFormat()
	{
		DateTimeFormatter fmt = DateTimeFormat.longDate();
		return fmt.print(dueDate);
	}

	public int getProjectID()
	{
		return projectID;
	}

	public void setProjectID(int projectID)
	{
		this.projectID = projectID;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	
	@Override
	public String toString()
	{
		return "Task [id=" + id + ", projectID=" + projectID + ", priority=" + priority + ", taskDescription="
				+ taskDescription + ", dueDate=" + dueDate + "]";
	}

	public long getDueDateAsLong()
	{
		return dueDate.getMillis();
	}

	/**
	 * Create a new date from milliseconds 
	 * @param parseLong
	 */
	public void setDueDate(long milliseconds)
	{
		this.setDueDate(new DateTime(milliseconds));
	}
}
