<%@page import="edu.wm.cs.cs435.tasky.database.GoogleDatabase"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.wm.cs.cs435.tasky.model.Server"%>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML>
<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
    <link type="text/css" rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />
  </head>

  <body>
  <div class="container">
  <div class="col-md-8 col-md-offset-2">

	<%
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String projectID = request.getParameter("projectID");
		String projectName = request.getParameter("projectName");
		String functionType = request.getParameter("functionType");
		String responseFromServer = "N/A";
		
		//set default values
		if (functionType==null)
			functionType="N/A";
		if (email==null)
			email="firstUser@gmail.com";
		if (password==null)
			password="123";
		if (projectID==null)
			projectID="10";
		if (projectName==null)
			projectID="FirstProject";
	%>
	
	<%
		//simulate access to database
		if (functionType.equals("testLogin"))
		{
			String loginStatus = Server.instance.login(email, password);
			responseFromServer = loginStatus;
		}
		else
			if (functionType.equals("getProjects"))
			{
				String actualListOfProjectsAsText = Server.instance.getProjects(email).toString();
				responseFromServer=actualListOfProjectsAsText;
			}
			else
				if (functionType.equals("testGetTasks"))
				{
					String actualListOfTasksAsText = Server.instance.getTasks(email,projectID);
					responseFromServer=actualListOfTasksAsText;
				}
				else
					if (functionType.equals("isEmailAvailableForSignup"))
					{
						GoogleDatabase googleDatabase=new GoogleDatabase();
						String isEmailAvailableForSignup=googleDatabase.isEmailAvailableForSignup(email);
						responseFromServer = isEmailAvailableForSignup;
					}
					else
						if (functionType.equals("signup"))
						{
							String signupResponse=Server.instance.signup(email, password);
							responseFromServer = "" + signupResponse;
						}
						else
							if (functionType.equals("addProject"))
							{
								String addProjectResponse=Server.instance.addProject(email, projectName);
								responseFromServer = addProjectResponse;
							}
	%>
	
	<h1>Values that were submitted</h1>
	<p>Submitted functionType: <%=functionType%></p>

	<%
		if (functionType.equals("testLogin"))
		{
	%>
		<p>The return value should be a string LOGIN_SUCCESSFUL, LOGIN_INVALID_USERNAME or LOGIN_INVALID_PASSWORD</p> 
		<p>Submitted email: <%=email%></p> 
		<p>Submitted password: <%=password%></p> 
	<%
		}
		else
			if (functionType.equals("getProjects"))
			{
	%>
			<p>Submitted email: <%=email%></p> 
	<%
			}
			else
				if (functionType.equals("testGetTasks"))
				{
	%>
			<p>Submitted email: <%=email%></p> 
			<p>Submitted projectID: <%=projectID%></p>
	<%
				}
				else
					if (functionType.equals("isEmailAvailableForSignup"))
					{
					%>
						<p>Submitted email: <%=email%></p> 
					<%
					}
					else
						if (functionType.equals("signup"))
						{
							%>
							<p>Submitted email: <%=email%></p> 
							<p>Submitted password: <%=password%></p> 
							<%
						}
						else
							if (functionType.equals("addProject"))
							{
								%>
								<p>Submitted email: <%=email%></p> 
								<p>Submitted project Name: <%=projectName%></p> 
								<%
							}
						
	
	%>
	
	<p>Response from server:<br/>
	<%= responseFromServer %>
	</p> 
	
	<hr/>
	
	<h3>Test add project</h3>
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		User/email: <input type="text" name="email" value="<%=email%>"><br/> 
		Project Name: <input type="text" name="projectName" value="<%=projectName%>"><br/>
		<input type="hidden" name="functionType" value="addProject">

		<input type="submit" value="Access Database & Test addProject" >
	</form>
	
	<h3>Test get projects</h3>
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		User/email: <input type="text" name="email" value="<%=email%>"><br/> 
		<input type="hidden" name="functionType" value="getProjects">

		<input type="submit" value="Access Database & Test getProjects" >
	</form>
	
	<h3>Test isEmailAvailableForSignup</h3>
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		User/email: <input type="text" name="email" value="<%=email%>"><br/> 
		<input type="hidden" name="functionType" value="isEmailAvailableForSignup">

		<input type="submit" value="Access Database & Test isEmailAvailableForSignup" >
	</form>
	
	<h3>Test signup</h3>
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		User/email: <input type="text" name="email" value="<%=email%>"><br/> 
		Password: <input type="text" name="password" value="<%=password%>"><br/> 
		<input type="hidden" name="functionType" value="signup">

		<input type="submit" value="Access Database & Test signup for user" >
	</form>
	
	<h3>Test Login functionality</h3>
	<form action="testAccessToTaskyDatabase.jsp" method="get" role="form">
		<div class="form-group">
			<label for="email1">Email:</label>
			<input class="form-control" type="text" name="email1" value="<%= email %>">
			<label for="password1">Password:</label>
			<input class="form-control" type="text" name="password" id="password1" value="<%=password%>"><br />
			<input type="hidden" name="functionType" value="testLogin">
			
			<button type="submit" class="btn btn-success">Access Database &amp; Test Login</button>
		</div>
	</form>
	
	<br/>
	<h3>Test get list of projects from user</h3>
	<form action="testAccessToTaskyDatabase.jsp" method="get" role="form" >
		<div class="form-group">
			<label for="email2">Email:</label>
			<input class="form-control" type="text" name="email" id="email2" value="<%=email%>"><br/> 
			<input type="hidden" name="functionType" value="testGetProjects">
	
			<button type="submit" class="btn btn-success">Access Database &amp; Test Get List of Projects</button>
		</div>
	</form>

	<br/>
	<h3>Test get list of tasks for a particular project</h3>
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		<div class="form-group">
			<label for="email3">Email:</label>
			<input class="form-control" type="text" name="email" id="email3" value="<%=email%>">
			<label for="projectID1">Project ID:</label>
			<input class="form-control" type="text" name="projectID"  id="projectID1" value="<%=projectID%>"> <br />
			
			<input type="hidden" name="functionType" value="testGetTasks">
	
			<button type="submit" class="btn btn-success">Access Database &amp; Test Get List of Tasks</button>
		</div>
	</form>
  </div> 
  </div>



  </body>
</html>