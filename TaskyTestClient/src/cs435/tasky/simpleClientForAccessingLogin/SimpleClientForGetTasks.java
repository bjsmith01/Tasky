package cs435.tasky.simpleClientForAccessingLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import edu.wm.cs.cs435.tasky.model.Project;
import edu.wm.cs.cs435.tasky.model.Task;

/**
 * 
 * This is just a simple client to show how to connect to the server, how to
 * pass an email and projectID get a list of tasks from the server
 * 
 * @author Fengfeng (Mia) Liu
 * 
 */
public class SimpleClientForGetTasks
{

	public static void main(String[] args) 
	{
		String email; 
		String projectID; 
		ArrayList<Task> getTasksStatus;
		
		//case 1
		email = "firstUser@gmail.com";
		projectID = "1";
		getTasksStatus = getTasks(email,projectID);
		System.out.println("For email: "+email+" and projectID: +"+projectID+" the response was: "+getTasksStatus);
		System.out.println("====================================");
		
		//case 2
		email = "firstUser@gmail.com";
		projectID = "20";
		getTasksStatus = getTasks(email, projectID);
		System.out.println("For email: "+email+" and projectID: +"+projectID+" the response was: "+getTasksStatus);
		System.out.println("====================================");
	}

	private static ArrayList<Task> getTasks(String email, String projectID)
	{
		try
		{
			//connect to the servlet for getting all the projects for a user in
//			URL urlToServlet = new URL("http://localhost:8888/GetTasksServlet");
			URL urlToServlet = new URL("http://tasky-server.appspot.com/GetTasksServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);
	        
			//create the request to the server
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());

			//the request is like a "file" with 3 lines:
			//GET_TASKS
			//email
			//projectID			
			writerToServer.write("GET_TASKS");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			writerToServer.write(projectID);
			writerToServer.write("\n");
			
			writerToServer.close();
			
			//TODO: replace with logging functionality
			System.out.println("CLIENT: generated the following request");
			System.out.println("GET_TASKS");
			System.out.println(email);
			System.out.println(projectID);
			System.out.println("CLIENT: end of request");

			
			//get the response from the server, which is very similar to reading from a file
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			//the response will be a list of tasks
			//the first line represents the numberOfTasks as int
			//the next 4*numberOfTasks lines represent the IDs, description, due date and priority of the tasks
			//
			//numberOfTasks		//first line
			//TaskID1
			//TaskDescription1
			//TaskDueDate1
			//TaskPriority1
			//TaskID2
			//TaskDescription2
			//TaskDueDate2
			//TaskPriority2
			//...
			//TaskIDN
			//TaskDescriptionN
			//TaskDueDateN		//line 4*N
			//TaskPriorityN		//line 4*N+1
			
			String responseFromServerAsString="";
			ArrayList<Task> listOfTasks=new ArrayList<>();
			int numberOfTasks=Integer.parseInt(readerFromServer.readLine());
			for (int i = 0; i < numberOfTasks; i++)
			{
				int taskID = Integer.parseInt(readerFromServer.readLine());
				String taskDescription = readerFromServer.readLine();
				long dueDateAsMilliseconds = Long.parseLong(readerFromServer.readLine());
				int priority= Integer.parseInt(readerFromServer.readLine());
				
				Task task = new Task(taskID+"",taskDescription,dueDateAsMilliseconds+"");
				task.setDueDate(dueDateAsMilliseconds);
				task.setPriority(priority);
				listOfTasks.add(task);
				
				responseFromServerAsString+="\n"+taskID+"\t"+taskDescription+"\t"+dueDateAsMilliseconds+"\t"+priority;
			}
			
			readerFromServer.close();

			System.out.println("CLIENT: got response from server=" + responseFromServerAsString);
			
			return listOfTasks;
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
