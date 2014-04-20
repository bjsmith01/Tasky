package cs435.tasky.simpleClientForAccessingLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import edu.wm.cs.cs435.tasky.model.Project;

/**
 * 
 * This is just a simple client to show how to connect to the server, how to
 * pass an email and get a list of projects from the server
 * 
 * @author Fengfeng (Mia) Liu
 * 
 */
public class SimpleClientForGetProjects
{

	public static void main(String[] args) 
	{
		String email; 
		ArrayList<Project> getProjectsStatus;
		
		//case 1
		email = "firstUser@gmail.com";
		getProjectsStatus = getProjects(email);
		System.out.println("For email: "+email+" the response was: "+getProjectsStatus);
		System.out.println("====================================");
		
		//case 2
		email = "secondUser@gmail.com";
		getProjectsStatus = getProjects(email);
		System.out.println("For email: "+email+" the response was: "+getProjectsStatus);
		System.out.println("====================================");
	}

	private static ArrayList<Project> getProjects(String email)
	{
		try
		{
			//connect to the servlet for getting all the projects for a user in
//			URL urlToServlet = new URL("http://localhost:8888/GetProjectsServlet");
			URL urlToServlet = new URL("http://tasky-server.appspot.com/GetProjectsServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);
	        
			//create the request to the server
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());

			//the request is like a "file" with 2 lines:
			//GET_PROJECTS
			//email
			writerToServer.write("GET_PROJECTS");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			
			writerToServer.close();
			
			//TODO: replace with logging functionality
			System.out.println("CLIENT: generated the following request");
			System.out.println("GET_PROJECTS");
			System.out.println(email);
			System.out.println("CLIENT: end of request");

			
			//get the response from the server, which is very similar to reading from a file
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			//the response will be a list of projects
			//the first line represents the numberOfProjects as int
			//the next 2*numberOfProjects represent the IDs and names of the projects
			//
			//numberOfProjects		//first line
			//ProjectID1
			//projectName1
			//ProjectID2
			//projectName2
			//...
			//ProjectIDN			//line 2N
			//projectNameN			//line 2N+1
			
			String responseFromServerAsString="";
			ArrayList<Project> listOfProjects=new ArrayList<>();
			int numberOfProjects=Integer.parseInt(readerFromServer.readLine());
			for (int i = 0; i < numberOfProjects; i++)
			{
				int projectID = Integer.parseInt(readerFromServer.readLine());
				String projectName = readerFromServer.readLine();
				
				Project project = new Project(projectID,projectName);
				listOfProjects.add(project);
				
				responseFromServerAsString+=projectID+"\t"+projectName+"\n";
			}
			
			readerFromServer.close();

			System.out.println("CLIENT: got response from server=" + responseFromServerAsString);
			
			return listOfProjects;
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
