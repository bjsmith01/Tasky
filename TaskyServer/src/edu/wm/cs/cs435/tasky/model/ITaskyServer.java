package edu.wm.cs.cs435.tasky.model;

import java.util.ArrayList;

/**
 * This interface stores the commands that a client can have access to.
 * This interface defines the operations between client and server.
 * 
 * @author Fengfeng (Mia) Liu
 *
 */
public interface ITaskyServer
{
	//constants defining the "operations" between client and server
	public static final String SIGNUP_OPERATION = "SIGNUP";
	public static final String LOGIN_OPERATION = "LOGIN";
	public static final String ADD_PROJECT_OPERATION = "ADD_PROJECT";
	public static final String GET_PROJECTS_OPERATION = "GET_PROJECTS";
	
	public static final String ADD_TASK_OPERATION = "ADD_TASK";
	public static final String DELETE_TASK_OPERATION = "DELETE_TASK";
	public static final String GET_TASKS_OPERATION = "GET_TASKS";
	
	public static final String LOGIN_SUCCESSFUL="LOGIN_SUCCESSFUL";
	public static final String LOGIN_INVALID_USERNAME="LOGIN_INVALID_USERNAME";
	public static final String LOGIN_INVALID_PASSWORD="LOGIN_INVALID_PASSWORD";
	
	public static final String NEW_USER_ADDED_TO_DATABASE="NEW_USER_ADDED_TO_DATABASE";
//	public static final int ERROR_ADDING_USER_TO_DATABASE=4;

	public static final String ADD_PROJECT_SUCCESSFUL="ADD_PROJECT_SUCCESSFUL";
	public static final String ADD_PROJECT_FAILED="ADD_PROJECT_FAILED";
	
	public static final String ADD_TASK_SUCCESSFUL = "ADD_TASK_SUCCESSFUL";
	public static final String ADD_TASK_FAILED = "ADD_TASK_FAILED";
	
	
	public static final String DELETE_TASK_SUCCESSFUL = "DELETE_TASK_SUCCESSFUL";
	public static final String DELETE_TASK_FAILED_NO_SUCH_TASK_ID = "DELETE_TASK_FAILED_NO_SUCH_TASK_ID";
	
	
	
	
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
	 * returns LOGIN_SUCCESSFUL for success
	 * returns LOGIN_INVALID_USERNAME for invalid username
	 * returns LOGIN_INVALID_PASSWORD for invalid password
	 */
	public String login(String email, String password);
	
	
	/**
	 * Gets the list of projects associated with a specific user
	 * 
	 * @param email, representing the username
	 * @return a list of projects
	 */
	public ArrayList<Project> getProjects(String email);
	
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
	public ArrayList<Task> getTasks(String email,String projectID);

	/**
	 * Add a new task to a project for a specific user
	 * 
	 * @param email, representing the username
	 * @param project, representing the ID of the "parent" of the task
	 * @param task - the task which contains id, decription, due date, priority, etc.
	 * @return a textual representation of the list of tasks
	 */
	public String addTask(String email,Project project,Task task);

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
