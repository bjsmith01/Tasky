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
import edu.wm.cs.cs435.tasky.model.Server;


/**
 * Servlet implementation for the add project functionality
 *
 * @author Fengfeng (Mia) Liu
 *
 */
public class AddProjectServlet extends HttpServlet
{
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		response.getOutputStream().println("AddProjectServlet.doGet() works! Use the doPost() method from Android or web application");
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
			//ADD_PROJECT
			//email
			//projectName
			BufferedReader readerFromClient=new BufferedReader(new InputStreamReader(request.getInputStream()));
			String requestType=readerFromClient.readLine();		//read first line of the request
			
			if (ITaskyServer.ADD_PROJECT_OPERATION.equals(requestType)==false)
			{
				throw new IOException("Invalid command. "+ITaskyServer.ADD_PROJECT_OPERATION+" expected");
			}
			
			String email=readerFromClient.readLine();			//read second line of the request
			String projectName=readerFromClient.readLine();		//read third line of the request
			
			readerFromClient.close();

			
			//TODO: replace this print message with a nice logging functionality
			System.out.println("SERVER: Got the following request");
			System.out.println(requestType);
			System.out.println(email);
			System.out.println(projectName);
			System.out.println("SERVER: end of request");

			//access the database and create a new project
			String addProjectStatus=Server.instance.addProject(email, projectName);
			System.out.println("SERVER: server response is");
			System.out.println(addProjectStatus);
			System.out.println("SERVER: end of response");


			//prepare the response back to the client
			response.setStatus(HttpServletResponse.SC_OK);
			
			OutputStreamWriter writerToClient=new OutputStreamWriter(response.getOutputStream());
			writerToClient.write(addProjectStatus);
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
