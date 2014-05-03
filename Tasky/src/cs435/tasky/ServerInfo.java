package cs435.tasky;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.util.Log;
import android.widget.Toast;


public class ServerInfo {

	/**
	 * Grabs a list of all folders on server that are associated with a specific user.
	 * @param email
	 * @return
	 */
	public static ArrayList<Folder> getFolders(String email)
	{
		ArrayList<Folder> folders = new ArrayList<Folder>();
		//
		try {
			
			URL urlToServlet = new URL("http://tasky-server.appspot.com/GetProjectsServlet");
			
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);

			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());
	        
			writerToServer.write("GET_PROJECTS");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			
			writerToServer.close();
			
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			int numberOfProjects=Integer.parseInt(readerFromServer.readLine());
			
			for (int i = 0; i < numberOfProjects; i++)
			{
				int projectID = Integer.parseInt(readerFromServer.readLine());
				String projectName = readerFromServer.readLine();

				Folder project = new Folder(projectName);
				project.setID(projectID);
				folders.add(project);
				
			}
	        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return folders;
	}

	public static ArrayList<Task> getTasks(String email, String projectID)
	{
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		URL urlToServlet;
		try {
			urlToServlet = new URL("http://tasky-server.appspot.com/GetTasksServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());
			
			writerToServer.write("GET_TASKS");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			writerToServer.write(projectID);
			writerToServer.write("\n");
			writerToServer.close();
			
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			int numberOfTasks=Integer.parseInt(readerFromServer.readLine());
			for (int i = 0; i < numberOfTasks; i++)
			{
				long taskID = Long.parseLong(readerFromServer.readLine());
				Log.v("Testing", "Task ID from server = " + String.valueOf(taskID));
				String taskDescription = readerFromServer.readLine();
				long dueDateAsMilliseconds = Long.parseLong(readerFromServer.readLine());
				int priority= Integer.parseInt(readerFromServer.readLine());

				Task task = new Task(taskDescription,dueDateAsMilliseconds+"");
				GregorianCalendar c = new GregorianCalendar();
				c.setTimeInMillis(dueDateAsMilliseconds);
				task.setDueDate(c);
				task.setPriority(priority);
				task.setID(taskID);
				tasks.add(task);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tasks;
	}
	
	public static void addFolder(String email, String projectName)
	{

		try {
			URL urlToServlet;
			urlToServlet = new URL("http://tasky-server.appspot.com/AddProjectServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);
	        
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());
			writerToServer.write("ADD_PROJECT");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			writerToServer.write(projectName);
			writerToServer.write("\n");
			writerToServer.close();
			
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String addProjectStatus;

			addProjectStatus = readerFromServer.readLine();

			readerFromServer.close();
				
			Log.v("ServerTest", addProjectStatus);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("ServerTest", "malformed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("ServerTest", "ioExcept");
		}


	}

	public static void deleteTask(String email, String projectID, String taskID)
	{
		try {
			URL urlToServlet = new URL("http://tasky-server.appspot.com/DeleteTaskServlet");
			URLConnection connection = urlToServlet.openConnection();
			connection.setDoOutput(true);
			
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());
			
			writerToServer.write("DELETE_TASK");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			writerToServer.write(projectID);
			writerToServer.write("\n");
			writerToServer.write(taskID);
			writerToServer.write("\n");	
			
			writerToServer.close();

			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String deleteTaskStatus;
			
			deleteTaskStatus = readerFromServer.readLine();


			readerFromServer.close();

			Log.v("ServerTest", "Deletion status: " + deleteTaskStatus);


			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 

	}
	
	/**
	 * Takes the newly created task and sends it to the server
	 * @param t the new task
	 */
	public static void addTask(Task t, String email, String projectID)
	{
		try
		{

			URL urlToServlet = new URL("http://tasky-server.appspot.com/AddTaskServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);

			//create the request to the server
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());

			String dueDate = String.valueOf(t.getDueDate().getTimeInMillis());
			
			writerToServer.write("ADD_TASK");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			writerToServer.write(projectID);
			writerToServer.write("\n");
			writerToServer.write(t.getName());
			writerToServer.write("\n");
			writerToServer.write(dueDate);
			writerToServer.write("\n");
			writerToServer.write(String.valueOf(t.getPriority()));
			writerToServer.write("\n");
			writerToServer.close();

			//get the response from the server, which is very similar to reading from a file
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			Log.v("ServerTest", readerFromServer.readLine());
			readerFromServer.close();

		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
