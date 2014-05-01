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
 * delete a project for a given user
 * 
 * @author Fengfeng (Mia) Liu
 * 
 */
public class SimpleClientForDeleteProject
{

	public static void main(String[] args) 
	{
		String email; 
		String projectID;
		
		//case 1
		email = "firstUser@gmail.com";
		projectID = "3";
		String deleteProjectStatus = deleteProject(email,projectID);
		System.out.println("For email: "+email+" and projectName: "+projectID+" the response was: "+deleteProjectStatus);
		System.out.println("====================================");
	}

	private static String deleteProject(String email, String projectID)
	{
		try
		{
			//connect to the servlet for adding a new project
//			URL urlToServlet = new URL("http://localhost:8888/DeleteProjectServlet");
			URL urlToServlet = new URL("http://tasky-server.appspot.com/DeleteProjectServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);
	        
			//create the request to the server
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());

			//the request is like a "file" with 3 lines:
			//DELETE_PROJECT
			//email
			//projectID
			writerToServer.write("DELETE_PROJECT");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			writerToServer.write(projectID);
			writerToServer.write("\n");
			
			writerToServer.close();
			
			//TODO: replace with logging functionality
			System.out.println("CLIENT: generated the following request");
			System.out.println("DELETE_PROJECT");
			System.out.println(email);
			System.out.println(projectID);
			System.out.println("CLIENT: end of request");

			
			//get the response from the server, which is very similar to reading from a file
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String deleteProjectStatus;
			
			//the response will be a string saying if the operation was successful or not 
			deleteProjectStatus = readerFromServer.readLine();
			
			readerFromServer.close();

			System.out.println("CLIENT: got response from server=" + deleteProjectStatus);
			
			return deleteProjectStatus;
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
