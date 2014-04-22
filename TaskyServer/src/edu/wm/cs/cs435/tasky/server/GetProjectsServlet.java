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


/**
 * Servlet implementation for the get projects functionality
 *
 * @author Fengfeng (Mia) Liu
 *
 */
public class GetProjectsServlet extends HttpServlet
{
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		response.getOutputStream().println("GetProjectsServlet.doGet() works! Use the doPost() method from Android or web application");
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
			//the expected request is on 2 lines, and it is similar to reading from a file:
			//GET_PROJECTS
			//email
			BufferedReader readerFromClient=new BufferedReader(new InputStreamReader(request.getInputStream()));
			String requestType=readerFromClient.readLine();		//read first line of the request
			
			if (ITaskyServer.GET_PROJECTS_OPERATION.equals(requestType)==false)
			{
				throw new IOException("Invalid command. "+ITaskyServer.GET_PROJECTS_OPERATION+" expected");
			}
			
			String email=readerFromClient.readLine();			//read second line of the request
			
			readerFromClient.close();

			
			//TODO: replace this print message with a nice logging functionality
			System.out.println("SERVER: Got the following request");
			System.out.println(requestType);
			System.out.println(email);
			System.out.println("SERVER: end of request");

			//access the database and get all projects
			ArrayList<Project> listOfProjects=Server.instance.getProjects(email);
			System.out.println("SERVER: server response is");
			System.out.println(listOfProjects);
			System.out.println("SERVER: end of response");


			//prepare the response back to the client
			response.setStatus(HttpServletResponse.SC_OK);
			
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
			OutputStreamWriter writerToClient=new OutputStreamWriter(response.getOutputStream());
			writerToClient.write(listOfProjects.size()+"\n");
			for (Project project : listOfProjects)
			{
				writerToClient.write(project.getId()+"\n");
				writerToClient.write(project.getName()+"\n");
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
