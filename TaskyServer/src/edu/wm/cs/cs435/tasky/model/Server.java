package edu.wm.cs.cs435.tasky.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import edu.wm.cs.cs435.tasky.database.GoogleDatabase;
import edu.wm.cs.cs435.tasky.database.IServerDatabase;

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
	private static final GoogleDatabase databaseInstance=new GoogleDatabase();
	 
    /**
     * Private constructor; Part of the Singleton pattern 
     */
    private Server() 
    {
    	System.out.println("SERVER is created");
//    	databaseInstance=new GoogleDatabase();
    }

	/**
	 * Create a new user with the given credentials
	 * 
	 * @param email
	 * @param password
	 * 
	 * returns NEW_USER_ADDED_TO_DATABASE for success
	 * returns EMAIL_NOT_AVAILABLE_FOR_SIGNUP for username(email) already exists
	 * returns PASSWORD_INVALID_FOR_SIGNUP for invalid password (i.e., password is too short, or does not have enough characters)
	 */
	public String signup(String email, String password)
	{
		//check to see if user exists in database
		if (databaseInstance.isEmailAvailableForSignup(email)!=IServerDatabase.EMAIL_AVAILABLE_FOR_SIGNUP)
		{
			return IServerDatabase.EMAIL_NOT_AVAILABLE_FOR_SIGNUP;
		}
		
		//check if password is less than 5 characters
		//other password checks could be added here
		if (password.length()<5)
		{
			return IServerDatabase.PASSWORD_INVALID_FOR_SIGNUP;
		}
		
		databaseInstance.addNewUserToDatabase(email,password);
		
		//add user to database and respond that everything is fine
		return IServerDatabase.NEW_USER_ADDED_TO_DATABASE;
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

	@Override
	public String addProject(String email, String projectName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addTask(String email, String projectID, String task)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteTask(String email, String projectID, String taskID)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
