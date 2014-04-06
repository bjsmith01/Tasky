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
	 * Gets the list of tasks associated with a specific projectID of a specific user
	 * 
	 * @param email, representing the username
	 * @param projectID
	 * @return a textual representation of the list of tasks
	 */
	public String getTasks(String email,String projectID);


}
