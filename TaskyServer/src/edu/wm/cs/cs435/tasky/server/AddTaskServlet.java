package edu.wm.cs.cs435.tasky.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.wm.cs.cs435.tasky.model.ITaskyServer;
import edu.wm.cs.cs435.tasky.model.Project;
import edu.wm.cs.cs435.tasky.model.Server;
import edu.wm.cs.cs435.tasky.model.Task;


/**
 * Servlet implementation for the add task functionality
 *
 * @author Fengfeng (Mia) Liu
 *
 */
public class AddTaskServlet extends HttpServlet
{
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		response.getOutputStream().println("AddTaskServlet.doGet() works! Use the doPost() method from Android or web application");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		try
		{
			//read the actual request sent by the client using readerFromClient
			//the expected request is on 6 lines, and it is similar to reading from a file:
			//ADD_TASK
			//email
			//projectID
			//task description
			//task due date		//as long format, representing the milliseconds
			//priority (number from 1-5)
			BufferedReader readerFromClient=new BufferedReader(new InputStreamReader(request.getInputStream()));
			String requestType=readerFromClient.readLine();		//read first line of the request
			
			if (ITaskyServer.ADD_TASK_OPERATION.equals(requestType)==false)
			{
				throw new IOException("Invalid command. "+ITaskyServer.ADD_TASK_OPERATION+" expected");
			}
			
			String email=readerFromClient.readLine();			//read second line of the request
			String projectID=readerFromClient.readLine();		//read third line of the request
			String taskDescription=readerFromClient.readLine();	//read fourth line of the request
			
			//due date is represented as long format, representing the milliseconds
			String taskDueDate=readerFromClient.readLine();		//read fifth line of the request
			String taskPriority=readerFromClient.readLine();	//read sixth line of the request
			
			readerFromClient.close();

			
			//TODO: replace this print message with a nice logging functionality
			System.out.println("SERVER: Got the following request");
			System.out.println(requestType);
			System.out.println(email);
			System.out.println(projectID);
			System.out.println(taskDescription);
			System.out.println(taskDueDate);
			System.out.println(taskPriority);
			System.out.println("SERVER: end of request");

			//access the database and create a new task
			
			//create project and task objects
			Project project=new Project(Integer.parseInt(projectID),"");
			
			Task task=new Task(taskDescription,taskDueDate,project.getId());
			task.setDueDate(Long.parseLong(taskDueDate));
			task.setPriority(Integer.parseInt(taskPriority));

			String addTaskStatus=Server.instance.addTask(email, project, task);
			
			System.out.println("SERVER: server response is");
			System.out.println(addTaskStatus);
			System.out.println("SERVER: end of response");

			//prepare the response back to the client
			response.setStatus(HttpServletResponse.SC_OK);
			
			OutputStreamWriter writerToClient=new OutputStreamWriter(response.getOutputStream());
			writerToClient.write(addTaskStatus);
			writerToClient.flush();
			writerToClient.close();
		}
		catch (IOException e)
		{
			try
			{
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(e.getMessage());
				response.getWriter().close();
			}
			catch (IOException ioe)
			{
			}
		}
	}

}
