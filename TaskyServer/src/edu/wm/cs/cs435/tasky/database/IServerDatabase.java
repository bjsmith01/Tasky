package edu.wm.cs.cs435.tasky.database;

import java.util.ArrayList;

import edu.wm.cs.cs435.tasky.model.Project;
import edu.wm.cs.cs435.tasky.model.Task;

/**
 * This interface defines the operations between a server and a database.
 * 
 * @author Fengfeng (Mia) Liu
 *
 */
public interface IServerDatabase
{
	public static final String EMAIL_AVAILABLE_FOR_SIGNUP="EMAIL_AVAILABLE_FOR_SIGNUP";
	public static final String EMAIL_NOT_AVAILABLE_FOR_SIGNUP="EMAIL_NOT_AVAILABLE_FOR_SIGNUP";
	public static final String PASSWORD_INVALID_FOR_SIGNUP="PASSWORD_INVALID_FOR_SIGNUP";
	
	public static final String NEW_USER_ADDED_TO_DATABASE="NEW_USER_ADDED_TO_DATABASE";
//	public static final int ERROR_ADDING_USER_TO_DATABASE=4;
	
	
	/**
	 * Check in the database to see if the username (email) is available for signup
	 * @param email
	 * @return 
	 * 		EMAIL_AVAILABLE_FOR_SIGNUP (success)
			EMAIL_NOT_AVAILABLE_FOR_SIGNUP (username already exists in database)
	 */
	String isEmailAvailableForSignup(String email);

	/**
	 * Adds a new user to the database
	 * @param email
	 * @param password
	 * @return 
	 * 		NEW_USER_ADDED_TO_DATABASE (success)
			ERROR_ADDING_USER_TO_DATABASE (user could not be added to database)
	 */
	String addNewUserToDatabase(String email, String password);

	
	/**
	 * Checks the database for a particular user with the specified email and password
	 * @param email
	 * @param password
	 * @return LOGIN_SUCCESSFUL, LOGIN_INVALID_USERNAME, or LOGIN_INVALID_PASSWORD
	 */
	String login(String email, String password);

	/**
	 * Adds a new project to the database
	 * @param email	representing the username
	 * @param project
	 * @return ADD_PROJECT_SUCCESSFUL or ADD_PROJECT_FAILED
	 * 
	 */
	String addProject(String email, Project project);

	/**
	 * Gets the list of projects associated with a specific user
	 * 
	 * @param email, representing the username
	 * @return a list of projects
	 */
	ArrayList<Project> getProjects(String email);
	
	/**
	 * Delete an existing project for a specific user
	 * 
	 * @param email, representing the username
	 * @param projectID
	 * @return a status message
	 */
	public String deleteProject(String email,String projectID);

	
	/**
	 * Adds a new task to the database, which belongs to a specific project/user
	 * @param project representing the id of the project it belongs to (i.e., the parent of the task)
	 * @param task representing the task that will be added, which contains the description, due date, priority, etc. 
	 * @return ADD_TASK_SUCCESSFUL or ADD_TASK_FAILED
	 * 
	 */
	String addTask(Project project, Task task);

	/**
	 * Gets the list of tasks associated with a user for a specific project
	 * 
	 * @param email, representing the username
	 * @param project, representing the project from which the tasks will be retrieved
	 * @return a list of tasks
	 */
	ArrayList<Task> getTasks(String email,Project project);
	
	/**
	 * Delete an existing task from a project for a specific user
	 * 
	 * @param email, representing the username
	 * @param projectID
	 * @param taskID
	 * @return a status message
	 */
	public String deleteTask(String email,String projectID,String taskID);

}
