package edu.wm.cs.cs435.tasky.database;

import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import edu.wm.cs.cs435.tasky.model.ITaskyServer;
import edu.wm.cs.cs435.tasky.model.Project;
import edu.wm.cs.cs435.tasky.model.Task;

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
	
	private static final String PROJECT_KIND = "Project";
	private static final String PROJECT_PROPERTY_ID = "projectID";
	private static final String PROJECT_PROPERTY_EMAIL = "projectEmail";
	private static final String PROJECT_PROPERTY_NAME = "projectName";

	private static final String TASK_KIND = "Task";
	private static final String TASK_PROPERTY_ID = "taskID";
	private static final String TASK_PROPERTY_PROJECT_ID = "projectID";
	private static final String TASK_PROPERTY_DESCRIPTION = "taskDescription";
	private static final String TASK_PROPERTY_DUE_DATE = "dueDate";
	private static final String TASK_PROPERTY_PRIORITY = "priority";

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

	public String login(String email, String password)
	{
		Query query = new Query(USER_KIND).addSort(USER_PROPERTY_EMAIL);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		// Use PreparedQuery interface to retrieve results
		PreparedQuery preparedQuery = datastore.prepare(query);
		
		//iterate through the results and see if the email is there or not
		for (Entity result : preparedQuery.asIterable())
		{
			String emailRetrieved = (String) result.getProperty(USER_PROPERTY_EMAIL);
			String passwordRetrieved = (String) result.getProperty(USER_PROPERTY_PASSWORD);
			
			if (email.equals(emailRetrieved))
			{
				if (password.equals(passwordRetrieved))
					return ITaskyServer.LOGIN_SUCCESSFUL;
				else
					return ITaskyServer.LOGIN_INVALID_PASSWORD;
			}
				
		}
		//the email was not found in the database
		return ITaskyServer.LOGIN_INVALID_USERNAME;
	}

	public String addProject(String email, Project project)
	{
		Entity entityProject = new Entity(PROJECT_KIND);
		entityProject.setProperty(PROJECT_PROPERTY_ID, project.getId());
		entityProject.setProperty(PROJECT_PROPERTY_EMAIL, email);
		entityProject.setProperty(PROJECT_PROPERTY_NAME, project.getName());

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entityProject);
		
		return ITaskyServer.ADD_PROJECT_SUCCESSFUL;
	}

	public ArrayList<Project> getProjects(String email)
	{
		Query query = new Query(PROJECT_KIND).addSort(PROJECT_PROPERTY_EMAIL);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		// Use PreparedQuery interface to retrieve results
		PreparedQuery preparedQuery = datastore.prepare(query);
		
		ArrayList<Project> listOfProjects=new ArrayList<>();
		
		//iterate through the results and see if the email is there or not
		for (Entity result : preparedQuery.asIterable())
		{
			long projectIDRetrieved = (long) result.getProperty(PROJECT_PROPERTY_ID);
			String emailRetrieved = (String) result.getProperty(PROJECT_PROPERTY_EMAIL);
			String projectNameRetrieved = (String) result.getProperty(PROJECT_PROPERTY_NAME);
			
			if (email.equals(emailRetrieved))
			{
				Project project = new Project((int) projectIDRetrieved,projectNameRetrieved);
				listOfProjects.add(project);
			}
		}
		
		return listOfProjects;
	}

	@Override
	public String addTask(Project project, Task task)
	{
		Entity entityTask=new Entity(TASK_KIND);
		entityTask.setProperty(TASK_PROPERTY_DESCRIPTION, task.getTaskDescription());
		entityTask.setProperty(TASK_PROPERTY_DUE_DATE, task.getDueDateAsJavaData());
		entityTask.setProperty(TASK_PROPERTY_PROJECT_ID, project.getId());
		entityTask.setProperty(TASK_PROPERTY_ID, task.getId());
		entityTask.setProperty(TASK_PROPERTY_PRIORITY, task.getPriority());
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entityTask);

		return ITaskyServer.ADD_TASK_SUCCESSFUL;
	}

	@Override
	public ArrayList<Task> getTasks(String email, Project project)
	{
		
		Query query = new Query(TASK_KIND).addSort(TASK_PROPERTY_ID);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		// Use PreparedQuery interface to retrieve results
		PreparedQuery preparedQuery = datastore.prepare(query);
		
		ArrayList<Task> listOfTasks=new ArrayList<>();
		
		//iterate through the results and see if the task belongs to the project or not
		for (Entity result : preparedQuery.asIterable())
		{
			long projectIDRetrieved = (long) result.getProperty(TASK_PROPERTY_PROJECT_ID);
			long taskIDRetrieved = (long) result.getProperty(TASK_PROPERTY_ID);
			String taskDescriptionRetrieved = (String) result.getProperty(TASK_PROPERTY_DESCRIPTION);
			Date dueDateRetrieved = (Date) result.getProperty(TASK_PROPERTY_DUE_DATE);
			long priorityRetrieved = (long) result.getProperty(TASK_PROPERTY_PRIORITY);
			
			if (project.getId()==projectIDRetrieved)
			{
				Task task = new Task((int)taskIDRetrieved,taskDescriptionRetrieved,dueDateRetrieved);
				task.setProjectID((int) projectIDRetrieved);
				task.setPriority((int) priorityRetrieved);
				listOfTasks.add(task);
			}
		}
		
		return listOfTasks;
	}
	
}
