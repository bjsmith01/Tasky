package edu.wm.cs.cs435.tasky.model;

/**
 * This interface stores the commands that a client can have access to.
 * This interface defines the operations between client and server.
 * 
 * @author Fengfeng (Mia) Liu
 *
 */
public interface ITaskyServer
{
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
	public String signup(String email, String password);
	
	
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
	public int login(String email, String password);
	
	
	/**
	 * Gets the list of projects associated with a specific user
	 * 
	 * @param email, representing the username
	 * @return a textual representation of the list of projects
	 */
	public String getProjects(String email);
	
	/**
	 * Add a new project for the exiting user
	 * 
	 * @param email, representing the username
	 * @return a status message if the operation was successful or not
	 */
	public String addProject(String email,String projectName);
	
	
	/**
	 * Gets the list of tasks associated with a specific projectID of a specific user
	 * 
	 * @param email, representing the username
	 * @param projectID
	 * @return a textual representation of the list of tasks
	 */
	public String getTasks(String email,String projectID);

	/**
	 * Add a new task to a project for a specific user
	 * 
	 * @param email, representing the username
	 * @param projectID
	 * @param task - the task name
	 * @return a textual representation of the list of tasks
	 */
	public String addTask(String email,String projectID,String task);

	/**
	 * Delete an existing task from a project for a specific user
	 * 
	 * @param email, representing the username
	 * @param projectID
	 * @param taskID
	 * @return a textual representation of the list of tasks
	 */
	public String deleteTask(String email,String projectID,String taskID);

}
