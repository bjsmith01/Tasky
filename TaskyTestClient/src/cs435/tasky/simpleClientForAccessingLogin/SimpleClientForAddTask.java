package cs435.tasky.simpleClientForAccessingLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * This is just a simple client to show how to connect to the server, and how to
 * create a new task for a given user in a specific project
 * 
 * @author Fengfeng (Mia) Liu
 * 
 */
public class SimpleClientForAddTask
{

	public static void main(String[] args) 
	{
		String email; 
		String projectID;
		String taskDescription;
		String taskDueDate;
		String taskPriority;
		String addTaskStatus;
		
		//case 1
		email = "firstUser@gmail.com";
		projectID = "1";
		taskDescription = "task description 123 for projectID 1 and priority 3";
		taskDueDate = "1393701938000";	//miliseconds
		taskPriority = "3";
		addTaskStatus = addTask(email,projectID,taskDescription,taskDueDate,taskPriority);
		System.out.println("For email: "+email+" and projectName: "+projectID+" the response was: "+addTaskStatus);
		System.out.println("====================================");
		
//		//case 2
//		email = "firstUser@gmail.com";
//		projectID = "SecondProject";
//		addTaskStatus = addTask(email,projectID);
//		System.out.println("For email: "+email+" and projectName: "+projectID+" the response was: "+addTaskStatus);
//		System.out.println("====================================");
//		
//		//case 3
//		email = "firstUser@gmail.com";
//		projectID = "ThirdProject";
//		addTaskStatus = addTask(email,projectID);
//		System.out.println("For email: "+email+" and projectName: "+projectID+" the response was: "+addTaskStatus);
//		System.out.println("====================================");
	}

	private static String addTask(String email, String projectID, String taskDescription, String taskDueDate, String taskPriority)
	{
		try
		{
			//connect to the servlet for adding a new project
//			URL urlToServlet = new URL("http://localhost:8888/AddTaskServlet");
			URL urlToServlet = new URL("http://tasky-server.appspot.com/AddTaskServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);
	        
			//create the request to the server
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());

			//the request is like a "file" with 6 lines:
			//ADD_TASK
			//email
			//projectID
			//task description
			//task due date		//as long format, representing the milliseconds
			//priority (number from 1-5)
			writerToServer.write("ADD_TASK");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			writerToServer.write(projectID);
			writerToServer.write("\n");
			writerToServer.write(taskDescription);
			writerToServer.write("\n");
			writerToServer.write(taskDueDate);
			writerToServer.write("\n");
			writerToServer.write(taskPriority);
			writerToServer.write("\n");
			
			writerToServer.close();
			
			//TODO: replace with logging functionality
			System.out.println("CLIENT: generated the following request");
			System.out.println("ADD_TASK");
			System.out.println(email);
			System.out.println(projectID);
			System.out.println(taskDescription);
			System.out.println(taskDueDate);
			System.out.println(taskPriority);
			System.out.println("CLIENT: end of request");

			
			//get the response from the server, which is very similar to reading from a file
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String addTaskStatus;
			
			//the response will be a string saying if the operation was successful or not 
			addTaskStatus = readerFromServer.readLine();
			
			readerFromServer.close();

			System.out.println("CLIENT: got response from server=" + addTaskStatus);
			
			return addTaskStatus;
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return "-1";
	}
}
