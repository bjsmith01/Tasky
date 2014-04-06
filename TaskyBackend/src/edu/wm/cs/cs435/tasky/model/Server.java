package edu.wm.cs.cs435.tasky.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * This class stores the typical commands that a client can have access to
 * (This class is mostly for testing purposes)
 * 
 * @author Fengfeng (Mia) Liu
 *
 */
public class Server implements ITaskyServer
{
	//use the Singleton pattern to have only one server
	//Any call to the server will use "Server.instance"
	public static final Server instance = new Server();
	 
    /**
     * Private constructor; Part of the Singleton pattern 
     */
    private Server() 
    {
    	
    }

	/**
	 * Check if the credentials for a user are correct
	 * 
	 * @param email
	 * @param password
	 * 
	 * returns 0 for success
	 * returns 1 for invalid username
	 * returns 2 for invalid password
	 */
	public int login(String email, String password)
	{
		//check the database for the specified email and password
		//this should be replaced with an actual database
		
		Hashtable<String, String> testDatabaseEmailsPasswords=new Hashtable<>();

		//simulate loading from a database
		try
		{
			BufferedReader br;
			br = new BufferedReader(new FileReader("databaseTextFiles/listOfUsers.txt"));
			String emailAndPassword;
			while ((emailAndPassword = br.readLine()) != null)
			{
				String[] splittedEmailAndPassword = emailAndPassword.split("\t");
				
				testDatabaseEmailsPasswords.put(splittedEmailAndPassword[0], splittedEmailAndPassword[1]);
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		
//		testDatabaseEmailsPasswords.put("firstUser@gmail.com", "123");
//		testDatabaseEmailsPasswords.put("secondUser@gmail.com", "456");
//		testDatabaseEmailsPasswords.put("thirdUser@gmail.com", "789");
		
		Enumeration<String> keys = testDatabaseEmailsPasswords.keys();
		
		while (keys.hasMoreElements())
		{
			String currentEmail = (String) keys.nextElement();
			
			if (currentEmail.equals(email))
			{
				if (testDatabaseEmailsPasswords.get(currentEmail).equals(password))
				{
					return Constants.LOGIN_SUCCESS;
				}
				else
				{
					return Constants.LOGIN_INVALID_PASSWORD;
				}
			}
		}
		
		return Constants.LOGIN_INVALID_EMAIL;
	}

	/**
	 * Gets the list of projects associated with a specific user
	 * 
	 * @param email, representing the username
	 * @return a textual representation of the list of projects
	 */
	public String getProjects(String email)
	{
		//this code should be replaced with an actual database
		
		Hashtable<String, String> testDatabaseProjects=new Hashtable<>();

		//simulate loading from a database
		try
		{
			BufferedReader br;
			br = new BufferedReader(new FileReader("databaseTextFiles/listOfProjects_"+email+".txt"));
			//each line contains the "projectID\tprojectName"
			String project;
			while ((project = br.readLine()) != null)
			{
				String[] splittedProject = project.split("\t");
				
				testDatabaseProjects.put(splittedProject[0], splittedProject[1]);
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		
		StringBuffer listOfProjects=new StringBuffer();
		
		Enumeration<String> keys = testDatabaseProjects.keys();
		
		while (keys.hasMoreElements())
		{
			String currentProjectID = (String) keys.nextElement();
		
			listOfProjects.append(currentProjectID+":"+testDatabaseProjects.get(currentProjectID));
			listOfProjects.append("\n");
		}
		
		return listOfProjects.toString();
	}
	
	/**
	 * Gets the list of tasks associated with a specific projectID of a specific user
	 * 
	 * @param email, representing the username
	 * @param projectID
	 * @return a textual representation of the list of tasks
	 */
	public String getTasks(String email,String projectID)
	{
		//this code should be replaced with an actual database
		
		Project project = new Project("sampleProject");
		
		//simulate loading from a database
		try
		{
			BufferedReader br;
			br = new BufferedReader(new FileReader("databaseTextFiles/listOfTasks_"+email+"_"+projectID+".txt"));
			//each line contains the "taskID\tTaskDescription\tDueDate"
			String task;
			while ((task = br.readLine()) != null)
			{
				String[] splittedTask = task.split("\t");

				project.addTask(new Task(splittedTask[0],splittedTask[1],"noduedate"));
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		StringBuffer listOfTasks=new StringBuffer();
		for (Task task : project.getListOfTasks())
		{
			listOfTasks.append(task.getId()+"::"+task.getTaskDescription());
			listOfTasks.append("\n");
		}
		
		return listOfTasks.toString();
	}
}
