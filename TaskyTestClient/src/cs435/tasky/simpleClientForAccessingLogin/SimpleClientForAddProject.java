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
 * create a new project for a given user
 * 
 * @author Fengfeng (Mia) Liu
 * 
 */
public class SimpleClientForAddProject
{

	public static void main(String[] args) 
	{
		String email; 
		String projectName;
		String addProjectStatus;
		
		//case 1
		email = "firstUser@gmail.com";
		projectName = "FirstProject";
		addProjectStatus = addProject(email,projectName);
		System.out.println("For email: "+email+" and projectName: "+projectName+" the response was: "+addProjectStatus);
		System.out.println("====================================");
		
		//case 2
		email = "firstUser@gmail.com";
		projectName = "SecondProject";
		addProjectStatus = addProject(email,projectName);
		System.out.println("For email: "+email+" and projectName: "+projectName+" the response was: "+addProjectStatus);
		System.out.println("====================================");
		
		//case 3
		email = "firstUser@gmail.com";
		projectName = "ThirdProject";
		addProjectStatus = addProject(email,projectName);
		System.out.println("For email: "+email+" and projectName: "+projectName+" the response was: "+addProjectStatus);
		System.out.println("====================================");
	}

	private static String addProject(String email, String projectName)
	{
		try
		{
			//connect to the servlet for adding a new project
//			URL urlToServlet = new URL("http://localhost:8888/AddProjectServlet");
			URL urlToServlet = new URL("http://tasky-server.appspot.com/AddProjectServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);
	        
			//create the request to the server
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());

			//the request is like a "file" with 3 lines:
			//ADD_PROJECT
			//email
			//projectName
			writerToServer.write("ADD_PROJECT");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			writerToServer.write(projectName);
			writerToServer.write("\n");
			
			writerToServer.close();
			
			//TODO: replace with logging functionality
			System.out.println("CLIENT: generated the following request");
			System.out.println("ADD_PROJECT");
			System.out.println(email);
			System.out.println(projectName);
			System.out.println("CLIENT: end of request");

			
			//get the response from the server, which is very similar to reading from a file
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String addProjectStatus;
			
			
			//the response will be a string saying if the operation was successful or not 
			addProjectStatus = readerFromServer.readLine();
			
			readerFromServer.close();

			System.out.println("CLIENT: got response from server=" + addProjectStatus);
			
			return addProjectStatus;
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
