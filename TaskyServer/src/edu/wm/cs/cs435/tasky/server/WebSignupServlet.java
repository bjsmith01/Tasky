package edu.wm.cs.cs435.tasky.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.wm.cs.cs435.tasky.database.IServerDatabase;
import edu.wm.cs.cs435.tasky.model.ITaskyServer;
import edu.wm.cs.cs435.tasky.model.Server;



public class WebSignupServlet extends HttpServlet
{
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		response.getOutputStream().println("SignupServlet.doGet() works! Use the doPost() method from Android or web application");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		System.out.println("email: " + email); 
		System.out.println("password: " + password); 
		
		String signupStatus = Server.instance.signup(email, password);
		System.out.println("signupstatus: " + signupStatus); 

		if (signupStatus.equals(IServerDatabase.NEW_USER_ADDED_TO_DATABASE)) { 
			HttpSession session = request.getSession(); 
			session.setAttribute("email", email);
			response.sendRedirect("/views/users/tasks/index.jsp");
			
		} else { 
			request.setAttribute("signupStatus", signupStatus);
			request.getRequestDispatcher("/views/users/create.jsp").forward(request, response);
		}

	}
}
