package edu.wm.cs.cs435.tasky.model.test;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;

import edu.wm.cs.cs435.tasky.model.CurrentDate;
import edu.wm.cs.cs435.tasky.model.IDs;
import edu.wm.cs.cs435.tasky.model.Task;


/**
 * @author Fengfeng (Mia) Liu
 *
 */
public class TaskTest
{

	@Test
	public void testCreateTaskDescriptionOnly()
	{
		IDs.setMaxTaskID(0);
		Task task1 = new Task("first task");

		assertEquals("first task", task1.getTaskDescription());
		assertEquals(1, task1.getId());
	}

	@Test
	public void testCreateTwoTasksWithSameName()
	{
		IDs.setMaxTaskID(0);
		Task task1 = new Task("task same name");
		Task task2 = new Task("task same name");
		
		assertEquals("task same name", task1.getTaskDescription());
		assertEquals("task same name", task2.getTaskDescription());
		assertEquals(1, task1.getId());
		assertEquals(2, task2.getId());
	}
	
	@Test
	public void testCreateThreeTasks()
	{
		IDs.setMaxTaskID(0);
		Task task1 = new Task("first task");
		Task task2 = new Task("second task");
		Task task3 = new Task("third task");
		
		assertEquals("first task", task1.getTaskDescription());
		assertEquals("second task", task2.getTaskDescription());
		assertEquals("third task", task3.getTaskDescription());
		assertEquals(1, task1.getId());
		assertEquals(2, task2.getId());
		assertEquals(3, task3.getId());
	}

	@Test
	public void testCreateTaskDescriptionAndDeadlineToday()
	{
		//setup date Saturday, March 15, 2014 8:30pm
		DateTime currentDate = new DateTime(2014, 3, 15, 20, 30, 10);
		CurrentDate.setCurrentDateForDebugging(currentDate);

		Task task1 = new Task("first task", "today");
		
		assertEquals("first task", task1.getTaskDescription());
		assertEquals(15, task1.getDueDate().getDayOfMonth());
		assertEquals(3, task1.getDueDate().getMonthOfYear());
		assertEquals(2014, task1.getDueDate().getYear());
	}

	@Test
	public void testCreateTaskDescriptionAndDeadlineToday2()
	{
		DateTime currentDate = new DateTime(2014, 2, 28, 20, 30, 10);
		CurrentDate.setCurrentDateForDebugging(currentDate);
		
		Task task1 = new Task("first task", "today");
		
		assertEquals("first task", task1.getTaskDescription());
		assertEquals(28, task1.getDueDate().getDayOfMonth());
		assertEquals(2, task1.getDueDate().getMonthOfYear());
		assertEquals(2014, task1.getDueDate().getYear());
	}

	@Test
	public void testCreateTaskDescriptionAndDeadlineTomorrow()
	{
		DateTime currentDate = new DateTime(2014, 3, 15, 20, 30, 10);
		CurrentDate.setCurrentDateForDebugging(currentDate);
		
		Task task1 = new Task("first task", "tomorrow");
		
		assertEquals("first task", task1.getTaskDescription());
		assertEquals(16, task1.getDueDate().getDayOfMonth());
		assertEquals(3, task1.getDueDate().getMonthOfYear());
		assertEquals(2014, task1.getDueDate().getYear());
	}
	
	@Test
	public void testCreateTaskDescriptionAndDeadlineTomorrow2()
	{
		DateTime currentDate = new DateTime(2014, 2, 28, 20, 30, 10);
		CurrentDate.setCurrentDateForDebugging(currentDate);
		
		Task task1 = new Task("first task", "tomorrow");
		
		assertEquals("first task", task1.getTaskDescription());
		assertEquals(1, task1.getDueDate().getDayOfMonth());
		assertEquals(3, task1.getDueDate().getMonthOfYear());
		assertEquals(2014, task1.getDueDate().getYear());
	}
	
	@Test
	public void testCreateTaskDescriptionAndDeadline_FromSatToMon()
	{
		DateTime currentDate = new DateTime(2014, 3, 15, 20, 30, 10);
		CurrentDate.setCurrentDateForDebugging(currentDate);
		
		Task task1 = new Task("first task", "mon");
		
		assertEquals("first task", task1.getTaskDescription());
		assertEquals(17, task1.getDueDate().getDayOfMonth());
		assertEquals(3, task1.getDueDate().getMonthOfYear());
		assertEquals(2014, task1.getDueDate().getYear());
	}

	@Test
	public void testCreateTaskDescriptionAndDeadline_FromSatToWed()
	{
		DateTime currentDate = new DateTime(2014, 3, 15, 20, 30, 10);
		CurrentDate.setCurrentDateForDebugging(currentDate);
		
		Task task1 = new Task("first task", "wed");
		
		assertEquals("first task", task1.getTaskDescription());
		assertEquals(19, task1.getDueDate().getDayOfMonth());
		assertEquals(3, task1.getDueDate().getMonthOfYear());
		assertEquals(2014, task1.getDueDate().getYear());
	}
	
	@Test
	public void testCreateTaskDescriptionAndDeadline_FromMonToWed()
	{
		DateTime currentDate = new DateTime(2014, 3, 17, 20, 30, 10);
		CurrentDate.setCurrentDateForDebugging(currentDate);
		
		Task task1 = new Task("first task", "wed");
		
		assertEquals("first task", task1.getTaskDescription());
		assertEquals(19, task1.getDueDate().getDayOfMonth());
		assertEquals(3, task1.getDueDate().getMonthOfYear());
		assertEquals(2014, task1.getDueDate().getYear());
	}
	
	@Test
	public void testCreateTaskDescriptionAndDeadline_FromWedToWed()
	{
		DateTime currentDate = new DateTime(2014, 3, 19, 20, 30, 10);
		CurrentDate.setCurrentDateForDebugging(currentDate);
		
		Task task1 = new Task("first task", "wed");
		
		assertEquals("first task", task1.getTaskDescription());
		assertEquals(19, task1.getDueDate().getDayOfMonth());
		assertEquals(3, task1.getDueDate().getMonthOfYear());
		assertEquals(2014, task1.getDueDate().getYear());
	}
	
	@Test
	public void testCreateTaskDescriptionAndDeadline_FromSatToSun()
	{
		DateTime currentDate = new DateTime(2014, 3, 8, 20, 30, 10);
		CurrentDate.setCurrentDateForDebugging(currentDate);
		
		Task task1 = new Task("first task", "sunday");
		
		assertEquals("first task", task1.getTaskDescription());
		assertEquals(9, task1.getDueDate().getDayOfMonth());
		assertEquals(3, task1.getDueDate().getMonthOfYear());
		assertEquals(2014, task1.getDueDate().getYear());
	}
	
	@Test
	public void testCreateTaskDescriptionAndDeadline_FromTueToFri()
	{
		DateTime currentDate = new DateTime(2014, 3, 25, 20, 30, 10);
		CurrentDate.setCurrentDateForDebugging(currentDate);
		
		Task task1 = new Task("first task", "friday");
		
		assertEquals("first task", task1.getTaskDescription());
		assertEquals(28, task1.getDueDate().getDayOfMonth());
		assertEquals(3, task1.getDueDate().getMonthOfYear());
		assertEquals(2014, task1.getDueDate().getYear());
	}
	
	@Test
	public void testCreateTaskDescriptionAndDeadline_FromSatToFri()
	{
		DateTime currentDate = new DateTime(2014, 3, 29, 20, 30, 10);
		CurrentDate.setCurrentDateForDebugging(currentDate);
		
		Task task1 = new Task("first task", "fri");
		
		assertEquals("first task", task1.getTaskDescription());
		assertEquals(4, task1.getDueDate().getDayOfMonth());
		assertEquals(4, task1.getDueDate().getMonthOfYear());
		assertEquals(2014, task1.getDueDate().getYear());
	}
	
	
}
