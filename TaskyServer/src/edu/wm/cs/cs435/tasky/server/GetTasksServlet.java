package edu.wm.cs.cs435.tasky.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.wm.cs.cs435.tasky.model.ITaskyServer;
import edu.wm.cs.cs435.tasky.model.Project;
import edu.wm.cs.cs435.tasky.model.Server;
import edu.wm.cs.cs435.tasky.model.Task;


/**
 * Servlet implementation for the get tasks functionality
 *
 * @author Fengfeng (Mia) Liu
 *
 */
public class GetTasksServlet extends HttpServlet
{
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		response.getOutputStream().println("GetTasksServlet.doGet() works! Use the doPost() method from Android or web application");
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
			//the expected request is on 3 lines, and it is similar to reading from a file:
			//GET_TASKS
			//email
			//projectID
			BufferedReader readerFromClient=new BufferedReader(new InputStreamReader(request.getInputStream()));
			String requestType=readerFromClient.readLine();		//read first line of the request
			
			if (ITaskyServer.GET_TASKS_OPERATION.equals(requestType)==false)
			{
				throw new IOException("Invalid command. "+ITaskyServer.GET_TASKS_OPERATION+" expected");
			}
			
			String email=readerFromClient.readLine();			//read second line of the request
			String projectID=readerFromClient.readLine();		//read third line of the request
			
			readerFromClient.close();

			
			//TODO: replace this print message with a nice logging functionality
			System.out.println("SERVER: Got the following request");
			System.out.println(requestType);
			System.out.println(email);
			System.out.println(projectID);
			System.out.println("SERVER: end of request");

			//access the database and get all tasks
			ArrayList<Task> listOfTasks=Server.instance.getTasks(email, projectID);
			System.out.println("SERVER: server response is");
			System.out.println(listOfTasks);
			System.out.println("SERVER: end of response");


			//prepare the response back to the client
			response.setStatus(HttpServletResponse.SC_OK);
			
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
			
			OutputStreamWriter writerToClient=new OutputStreamWriter(response.getOutputStream());
			writerToClient.write(listOfTasks.size()+"\n");
			for (Task task : listOfTasks)
			{
				writerToClient.write(task.getId()+"\n");
				writerToClient.write(task.getTaskDescription()+"\n");
				writerToClient.write(task.getDueDateAsLong()+"\n");
				writerToClient.write(task.getPriority()+"\n");
			}
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
