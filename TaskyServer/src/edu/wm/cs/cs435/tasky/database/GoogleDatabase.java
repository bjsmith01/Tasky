package edu.wm.cs.cs435.tasky.database;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * This class implements the operations that a Server can have with the a database.
 * In this case, the database is the Google App Engine Datastore (https://developers.google.com/appengine/docs/java/datastore/)
 * 
 * @author Fengfeng (Mia) Liu
 *
 */
public class GoogleDatabase implements IServerDatabase
{

	private static final String USER_KIND="User";
	private static final String USER_PROPERTY_EMAIL="email";
	private static final String USER_PROPERTY_PASSWORD="password";

	public GoogleDatabase()
	{
	}
	
	@Override
	public String isEmailAvailableForSignup(String email)
	{
		Query query = new Query(USER_KIND).addSort(USER_PROPERTY_EMAIL);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		// Use PreparedQuery interface to retrieve results
		PreparedQuery preparedQuery = datastore.prepare(query);
		
		//iterate through the results and see if the email is there or not
		for (Entity result : preparedQuery.asIterable())
		{
			String emailRetrieved = (String) result.getProperty(USER_PROPERTY_EMAIL);
			
			if (email.equals(emailRetrieved))
				return IServerDatabase.EMAIL_NOT_AVAILABLE_FOR_SIGNUP;
		}
		
		//the email was not found in the database
		return IServerDatabase.EMAIL_AVAILABLE_FOR_SIGNUP;
	}

	@Override
	public String addNewUserToDatabase(String email, String password)
	{
		Entity entityUser = new Entity(USER_KIND);
		entityUser.setProperty(USER_PROPERTY_EMAIL, email);
		entityUser.setProperty(USER_PROPERTY_PASSWORD, password);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entityUser);
		
		return NEW_USER_ADDED_TO_DATABASE;
	}
	
}
