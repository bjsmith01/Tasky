package cs435.tasky;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * @author Jarrett Gabel
 * This test class runs tests across the basic functionality present within the
 * Task class.
 */
public class TaskTest {

	/**
	 * Tests the name storing and changing abilities of the Task class
	 * Task name should start as 'Test' and be changed to 'NewTest'
	 */
	@Test
	public void testName() {
		Task t = new Task("Test", "This is a test");
		assertTrue(t.getName() == "Test");
		
		t.setName("NewTest");
		assertTrue(t.getName() == "NewTest");
	}
	
	/**
	 * Tests the completed value of the Task class
	 * The 'completed' boolean should start as false and be changed to true
	 * 
	 */
	@Test
	public void testCompleted()
	{
		Task t = new Task("Test", "This is a test");
		assertTrue(t.isCompleted() == false);
		
		t.setCompleted(true);
		assertTrue(t.isCompleted() == true);
		
		}

	/**
	 * Tests the dueDate value of the Task class
	 * As GregorianCalendar dates must be entered specifically, it is important
	 * to ensure that the date is being entered and stored properly
	 * The stored year should be 1, the month 1 and the day 1 at first and then
	 * The stored year should be 2014, the month 4 and the day 6 in the next 3
	 * tests to ensure that both constructors with DueDate values work as well as
	 * the setDueDate command.
	 */
	@Test
	public void testDueDate()
	{
		Task t = new Task("Test", "This is a test");
		assertTrue(t.getDueDate().get(GregorianCalendar.MONTH) == 1);
		assertTrue(t.getDueDate().get(GregorianCalendar.DAY_OF_MONTH) == 1);
		assertTrue(t.getDueDate().get(GregorianCalendar.YEAR) == 1);
		
		t.setDueDate(new GregorianCalendar(2014, 4, 6));
		
		assertTrue(t.getDueDate().get(GregorianCalendar.MONTH) == 4);
		assertTrue(t.getDueDate().get(GregorianCalendar.DAY_OF_MONTH) == 6);
		assertTrue(t.getDueDate().get(GregorianCalendar.YEAR) == 2014);
		
		Task t2 = new Task("Test", "This is a test", new GregorianCalendar(2014, 4, 6));
		
		assertTrue(t2.getDueDate().get(GregorianCalendar.MONTH) == 4);
		assertTrue(t2.getDueDate().get(GregorianCalendar.DAY_OF_MONTH) == 6);
		assertTrue(t2.getDueDate().get(GregorianCalendar.YEAR) == 2014);
		
		Task t3 = new Task("Test", "This is a test", new GregorianCalendar(2014, 4, 6), new GregorianCalendar(2014, 4, 6));
		
		assertTrue(t3.getDueDate().get(GregorianCalendar.MONTH) == 4);
		assertTrue(t3.getDueDate().get(GregorianCalendar.DAY_OF_MONTH) == 6);
		assertTrue(t3.getDueDate().get(GregorianCalendar.YEAR) == 2014);
		
	}
	/**
	 * Tests the reminderDate value of the Task class
	 * Should first return 1 as year, 1 as month and 1 as day as no reminder
	 * has been set. Should return 2014 as year, 4 as month and 6 as day in the
	 * 3 next sets to test the constructors as well as the setReminder command
	 */
	@Test
	public void testReminderDate()
	{
		Task t = new Task("Test", "This is a test");

		assertTrue(t.getReminder().get(GregorianCalendar.YEAR) == 1);
		assertTrue(t.getReminder().get(GregorianCalendar.MONTH) == 1);
		assertTrue(t.getReminder().get(GregorianCalendar.DAY_OF_MONTH) == 1);
		
		t.setReminder(new GregorianCalendar(2014, 4, 6));
		
		assertTrue(t.getReminder().get(GregorianCalendar.MONTH) == 4);
		assertTrue(t.getReminder().get(GregorianCalendar.DAY_OF_MONTH) == 6);
		assertTrue(t.getReminder().get(GregorianCalendar.YEAR) == 2014);
		
		Task t2 = new Task("Test", "This is a test", new GregorianCalendar(2014, 4, 6));
		
		assertTrue(t2.getReminder().get(GregorianCalendar.MONTH) == 1);
		assertTrue(t2.getReminder().get(GregorianCalendar.DAY_OF_MONTH) == 1);
		assertTrue(t2.getReminder().get(GregorianCalendar.YEAR) == 1);
		
		Task t3 = new Task("Test", "This is a test", new GregorianCalendar(2014, 4, 6), new GregorianCalendar(2014, 4, 6));
		
		assertTrue(t3.getReminder().get(GregorianCalendar.MONTH) == 4);
		assertTrue(t3.getReminder().get(GregorianCalendar.DAY_OF_MONTH) == 6);
		assertTrue(t3.getReminder().get(GregorianCalendar.YEAR) == 2014);
	}
	/**
	 * Tests the description value of the Task class
	 * First checks to ensure that description = "This is a test", then changes
	 * description and ensures that it = "This is another test"
	 */
	@Test
	public void testDesc()
	{
		Task t = new Task("Test", "This is a test");
		assertTrue(t.getDesc() == "This is a test");
		
		t.setDesc("This is another test");
		assertTrue(t.getDesc() == "This is another test");
	}
	
}
