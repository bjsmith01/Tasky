package cs435.tasky;

import static org.junit.Assert.*;

import org.junit.Test;

public class FolderTest {

	/**
	 * Tests the addTask method in the Folder class.
	 * Adds a task and ensures that the task can be found. 
	 */
	@Test
	public void testAddTask() {
		Folder f = new Folder("Test");
		Task t = new Task("Test", "This is a test");
		
		f.AddTask(t);
		assertTrue(f.getTask(0).getName() == "Test");
		
	}

}
