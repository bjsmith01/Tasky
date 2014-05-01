package edu.wm.cs.cs435.tasky.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.wm.cs.cs435.tasky.model.Server;


/**
 * Servlet implementation to initialize the database with entries 
 * for maxProjectID and maxTaskID
 * 
 * (This only needs to be done once)
 * 
 * @author Fengfeng (Mia) Liu
 *
 */
public class InitializeDatabaseIDsServlet extends HttpServlet
{
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		
		//use this code to access the database and initialize/reset the maxProjectID and maxTaskID
//		Server.instance.initializeDatabaseWithProjectAndTaskIDs();
//		response.getOutputStream().println("InitializeDatabaseIDsServlet.doGet(): initializeDatabaseWithProjectAndTaskIDs was successful");

		
		response.getOutputStream().println("InitializeDatabaseIDsServlet.doGet(): initializeDatabaseWithProjectAndTaskIDs was DISABLED (to prevent resetting the IDs every time)");
	}

}
