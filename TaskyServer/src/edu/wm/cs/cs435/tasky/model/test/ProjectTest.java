package edu.wm.cs.cs435.tasky.model.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Test;

import edu.wm.cs.cs435.tasky.model.CurrentDate;
import edu.wm.cs.cs435.tasky.model.IDs;
import edu.wm.cs.cs435.tasky.model.Project;
import edu.wm.cs.cs435.tasky.model.Task;


/**
 * @author Fengfeng (Mia) Liu
 *
 */
public class ProjectTest
{

	

	@Test
	public void testCreateProject()
	{
		IDs.setMaxProjectID(0);
		Project project1 = new Project(1,"First Project");

		assertEquals("First Project", project1.getName());
		assertEquals(1, project1.getId());

	}
	
	@Test
	public void testCreateProjectAndAddTask()
	{
		IDs.setMaxProjectID(0);
		
		Project project1 = new Project(1,"First Project");
		
		assertEquals("First Project", project1.getName());
		
		Task task1 = new Task("first task");
		
		project1.addTask(task1);

		
		ArrayList<Task> listOfTasks = project1.getListOfTasks();
		assertEquals("first task", listOfTasks.get(0).getTaskDescription());
		assertEquals(1, project1.getId());
	}

	@Test
	public void testCreateProjectAndAddTwoTask()
	{
		IDs.setMaxProjectID(0);
		
		Project project1 = new Project(1,"First Project");
		
		assertEquals("First Project", project1.getName());
		
		Task task1 = new Task("first task");
		
		project1.addTask(task1);
		
		Task task2 = new Task("second task");
		
		project1.addTask(task2);
		
		ArrayList<Task> listOfTasks = project1.getListOfTasks();
		assertEquals("first task", listOfTasks.get(0).getTaskDescription());
		assertEquals("second task", listOfTasks.get(1).getTaskDescription());
		assertEquals(1, project1.getId());
	}

//	@Test
//	public void testCreateTaskDescriptionAndDeadlineToday()
//	{
//		//setup date Saturday, March 15, 2014 8:30pm
//		DateTime currentDate = new DateTime(2014, 3, 15, 20, 30, 10);
//		CurrentDate.setCurrentDateForDebugging(currentDate);
//
//		Task task1 = new Task("first task", "today");
//		
//		assertEquals("first task", task1.getTaskDescription());
//		assertEquals(15, task1.getDueDate().getDayOfMonth());
//		assertEquals(3, task1.getDueDate().getMonthOfYear());
//		assertEquals(2014, task1.getDueDate().getYear());
//	}
//
//	@Test
//	public void testCreateTaskDescriptionAndDeadlineToday2()
//	{
//		DateTime currentDate = new DateTime(2014, 2, 28, 20, 30, 10);
//		CurrentDate.setCurrentDateForDebugging(currentDate);
//		
//		Task task1 = new Task("first task", "today");
//		
//		assertEquals("first task", task1.getTaskDescription());
//		assertEquals(28, task1.getDueDate().getDayOfMonth());
//		assertEquals(2, task1.getDueDate().getMonthOfYear());
//		assertEquals(2014, task1.getDueDate().getYear());
//	}
//
//	@Test
//	public void testCreateTaskDescriptionAndDeadlineTomorrow()
//	{
//		DateTime currentDate = new DateTime(2014, 3, 15, 20, 30, 10);
//		CurrentDate.setCurrentDateForDebugging(currentDate);
//		
//		Task task1 = new Task("first task", "tomorrow");
//		
//		assertEquals("first task", task1.getTaskDescription());
//		assertEquals(16, task1.getDueDate().getDayOfMonth());
//		assertEquals(3, task1.getDueDate().getMonthOfYear());
//		assertEquals(2014, task1.getDueDate().getYear());
//	}
//	
//	@Test
//	public void testCreateTaskDescriptionAndDeadlineTomorrow2()
//	{
//		DateTime currentDate = new DateTime(2014, 2, 28, 20, 30, 10);
//		CurrentDate.setCurrentDateForDebugging(currentDate);
//		
//		Task task1 = new Task("first task", "tomorrow");
//		
//		assertEquals("first task", task1.getTaskDescription());
//		assertEquals(1, task1.getDueDate().getDayOfMonth());
//		assertEquals(3, task1.getDueDate().getMonthOfYear());
//		assertEquals(2014, task1.getDueDate().getYear());
//	}
//	
//	@Test
//	public void testCreateTaskDescriptionAndDeadline_FromSatToMon()
//	{
//		DateTime currentDate = new DateTime(2014, 3, 15, 20, 30, 10);
//		CurrentDate.setCurrentDateForDebugging(currentDate);
//		
//		Task task1 = new Task("first task", "mon");
//		
//		assertEquals("first task", task1.getTaskDescription());
//		assertEquals(17, task1.getDueDate().getDayOfMonth());
//		assertEquals(3, task1.getDueDate().getMonthOfYear());
//		assertEquals(2014, task1.getDueDate().getYear());
//	}
//
//	@Test
//	public void testCreateTaskDescriptionAndDeadline_FromSatToWed()
//	{
//		DateTime currentDate = new DateTime(2014, 3, 15, 20, 30, 10);
//		CurrentDate.setCurrentDateForDebugging(currentDate);
//		
//		Task task1 = new Task("first task", "wed");
//		
//		assertEquals("first task", task1.getTaskDescription());
//		assertEquals(19, task1.getDueDate().getDayOfMonth());
//		assertEquals(3, task1.getDueDate().getMonthOfYear());
//		assertEquals(2014, task1.getDueDate().getYear());
//	}
//	
//	@Test
//	public void testCreateTaskDescriptionAndDeadline_FromMonToWed()
//	{
//		DateTime currentDate = new DateTime(2014, 3, 17, 20, 30, 10);
//		CurrentDate.setCurrentDateForDebugging(currentDate);
//		
//		Task task1 = new Task("first task", "wed");
//		
//		assertEquals("first task", task1.getTaskDescription());
//		assertEquals(19, task1.getDueDate().getDayOfMonth());
//		assertEquals(3, task1.getDueDate().getMonthOfYear());
//		assertEquals(2014, task1.getDueDate().getYear());
//	}
//	
//	@Test
//	public void testCreateTaskDescriptionAndDeadline_FromWedToWed()
//	{
//		DateTime currentDate = new DateTime(2014, 3, 19, 20, 30, 10);
//		CurrentDate.setCurrentDateForDebugging(currentDate);
//		
//		Task task1 = new Task("first task", "wed");
//		
//		assertEquals("first task", task1.getTaskDescription());
//		assertEquals(19, task1.getDueDate().getDayOfMonth());
//		assertEquals(3, task1.getDueDate().getMonthOfYear());
//		assertEquals(2014, task1.getDueDate().getYear());
//	}
//	
//	@Test
//	public void testCreateTaskDescriptionAndDeadline_FromSatToSun()
//	{
//		DateTime currentDate = new DateTime(2014, 3, 8, 20, 30, 10);
//		CurrentDate.setCurrentDateForDebugging(currentDate);
//		
//		Task task1 = new Task("first task", "sunday");
//		
//		assertEquals("first task", task1.getTaskDescription());
//		assertEquals(9, task1.getDueDate().getDayOfMonth());
//		assertEquals(3, task1.getDueDate().getMonthOfYear());
//		assertEquals(2014, task1.getDueDate().getYear());
//	}
//	
//	@Test
//	public void testCreateTaskDescriptionAndDeadline_FromTueToFri()
//	{
//		DateTime currentDate = new DateTime(2014, 3, 25, 20, 30, 10);
//		CurrentDate.setCurrentDateForDebugging(currentDate);
//		
//		Task task1 = new Task("first task", "friday");
//		
//		assertEquals("first task", task1.getTaskDescription());
//		assertEquals(28, task1.getDueDate().getDayOfMonth());
//		assertEquals(3, task1.getDueDate().getMonthOfYear());
//		assertEquals(2014, task1.getDueDate().getYear());
//	}
//	
//	@Test
//	public void testCreateTaskDescriptionAndDeadline_FromSatToFri()
//	{
//		DateTime currentDate = new DateTime(2014, 3, 29, 20, 30, 10);
//		CurrentDate.setCurrentDateForDebugging(currentDate);
//		
//		Task task1 = new Task("first task", "fri");
//		
//		assertEquals("first task", task1.getTaskDescription());
//		assertEquals(4, task1.getDueDate().getDayOfMonth());
//		assertEquals(4, task1.getDueDate().getMonthOfYear());
//		assertEquals(2014, task1.getDueDate().getYear());
//	}
	
}
