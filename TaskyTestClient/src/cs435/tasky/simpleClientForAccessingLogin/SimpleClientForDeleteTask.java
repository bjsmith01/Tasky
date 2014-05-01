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
 * delete a task for a given user in a specific project
 * 
 * @author Fengfeng (Mia) Liu
 * 
 */
public class SimpleClientForDeleteTask
{

	public static void main(String[] args) 
	{
		String email; 
		String projectID;
		String taskID;
		
		//case 1
		email = "firstUser@gmail.com";
		projectID = "1";
		taskID = "7";
		String deleteTaskStatus = deleteTask(email,projectID,taskID);
		System.out.println("For email: "+email+" and projectName: "+projectID+" and taskID: "+taskID+" the response was: "+deleteTaskStatus);
		System.out.println("====================================");
	}

	private static String deleteTask(String email, String projectID, String taskID)
	{
		try
		{
			//connect to the servlet for adding a new project
//			URL urlToServlet = new URL("http://localhost:8888/DeleteTaskServlet");
			URL urlToServlet = new URL("http://tasky-server.appspot.com/DeleteTaskServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);
	        
			//create the request to the server
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());

			//the request is like a "file" with 4 lines:
			//DELETE_TASK
			//email
			//projectID
			//taskID
			writerToServer.write("DELETE_TASK");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			writerToServer.write(projectID);
			writerToServer.write("\n");
			writerToServer.write(taskID);
			writerToServer.write("\n");
			
			writerToServer.close();
			
			//TODO: replace with logging functionality
			System.out.println("CLIENT: generated the following request");
			System.out.println("DELETE_TASK");
			System.out.println(email);
			System.out.println(projectID);
			System.out.println(taskID);
			System.out.println("CLIENT: end of request");

			
			//get the response from the server, which is very similar to reading from a file
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String deleteTaskStatus;
			
			//the response will be a string saying if the operation was successful or not 
			deleteTaskStatus = readerFromServer.readLine();
			
			readerFromServer.close();

			System.out.println("CLIENT: got response from server=" + deleteTaskStatus);
			
			return deleteTaskStatus;
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
