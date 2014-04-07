package edu.wm.cs.cs435.tasky.database;

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

}
