package edu.wm.cs.cs435.tasky.model;

import java.util.ArrayList;

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
	 * returns LOGIN_SUCCESSFUL for success
	 * returns LOGIN_INVALID_USERNAME for invalid username
	 * returns LOGIN_INVALID_PASSWORD for invalid password
	 */
	public String login(String email, String password)
	{
		//check the database for the specified email and password
		return databaseInstance.login(email,password);
	}

	/**
	 * Gets the list of projects associated with a specific user
	 * 
	 * @param email, representing the username
	 * @return a list of projects
	 */
	public ArrayList<Project> getProjects(String email)
	{
		//get all the projects associated with a user from the database
		return databaseInstance.getProjects(email);
	}
	
	/**
	 * Gets the list of tasks associated with a specific projectID of a specific user
	 * 
	 * @param email, representing the username
	 * @param projectID
	 * @return a textual representation of the list of tasks
	 */
	public ArrayList<Task> getTasks(String email,String projectID)
	{
		//get all the tasks associated with a project that belongs to a user from the database
		Project project=new Project(Integer.parseInt(projectID), "");
		return databaseInstance.getTasks(email, project);
	}

	@Override
	public String addProject(String email, String projectName)
	{
		//create a new project and assign it a unique ID
		Project project = new Project(projectName);
		
		//store the project in the database
		return databaseInstance.addProject(email,project);

	}

	@Override
	public String addTask(String email, Project project, Task task)
	{
		//store the task in the database
		return databaseInstance.addTask(project, task);
	}

	@Override
	public String deleteTask(String email, String projectID, String taskID)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
