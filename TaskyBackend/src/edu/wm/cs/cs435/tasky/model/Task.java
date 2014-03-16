package edu.wm.cs.cs435.tasky.model;


import org.joda.time.DateTime;
import org.joda.time.Period;


/**
 * Each @Project can have multiple tasks (see @Task)
 * 
 * 
 * @author Fengfeng (Mia) Liu
 */
public class Task
{
	private String taskDescription;
	private DateTime dueDate;

	public Task(String taskDescription)
	{
		this.setTaskDescription(taskDescription);
		
	}

	public Task(String taskDescription, String dueDate)
	{
		this(taskDescription);
		
		convertDueDate(dueDate);
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

	public void setDueDate(DateTime dueDate)
	{
		this.dueDate = dueDate;
	}
	

}
