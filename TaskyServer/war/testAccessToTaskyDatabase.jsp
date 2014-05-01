<%@page import="edu.wm.cs.cs435.tasky.database.GoogleDatabase"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="edu.wm.cs.cs435.tasky.model.Server"%>
<%@ page import="edu.wm.cs.cs435.tasky.model.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>

  <body>

	<%
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String projectID = request.getParameter("projectID");
		String taskID = request.getParameter("taskID");
		String projectName = request.getParameter("projectName");
		String functionType = request.getParameter("functionType");
		String responseFromServer = "N/A";

		String taskDescription = request.getParameter("taskDescription");
		String dueDate = request.getParameter("dueDate");
		String priority = request.getParameter("priority");

		
		//set default values
		if (functionType==null)
			functionType="N/A";
		if (email==null)
			email="firstUser@gmail.com";
		if (password==null)
			password="123";
		if (projectID==null)
			projectID="10";
		if (taskID==null)
			taskID="1";
		if (projectName==null)
			projectName="FirstProject";
		if (taskDescription==null)
			taskDescription="sample task";
		if (dueDate==null)
			dueDate="today";
		if (priority==null)
			priority="5";

	%>
	
	<%
		//simulate access to database
		if (functionType.equals("testLogin"))
		{
			String loginStatus = Server.instance.login(email, password);
			responseFromServer = loginStatus;
		}

		if (functionType.equals("getProjects"))
		{
			String actualListOfProjectsAsText = Server.instance.getProjects(email).toString();
			responseFromServer=actualListOfProjectsAsText;
		}

		if (functionType.equals("getTasks"))
		{
			String actualListOfTasksAsText = Server.instance.getTasks(email,projectID).toString();
			responseFromServer=actualListOfTasksAsText;
		}

		if (functionType.equals("isEmailAvailableForSignup"))
		{
			GoogleDatabase googleDatabase=new GoogleDatabase();
			String isEmailAvailableForSignup=googleDatabase.isEmailAvailableForSignup(email);
			responseFromServer = isEmailAvailableForSignup;
		}

		if (functionType.equals("signup"))
		{
			String signupResponse=Server.instance.signup(email, password);
			responseFromServer = "" + signupResponse;
		}

		if (functionType.equals("addProject"))
		{
			String addProjectResponse=Server.instance.addProject(email, projectName);
			responseFromServer = addProjectResponse;
		}

		if (functionType.equals("addTask"))
		{
			Project projectObject=new Project(Integer.parseInt(projectID),"");
			
			Task taskObject=new Task(taskDescription,dueDate,projectObject.getId());
			taskObject.setPriority(Integer.parseInt(priority));

			String addTaskResponse=Server.instance.addTask(email, projectObject, taskObject);
			responseFromServer = addTaskResponse;
		}

		if (functionType.equals("deleteTask"))
		{
			String addTaskResponse=Server.instance.deleteTask(email, projectID, taskID);
			responseFromServer = addTaskResponse;
		}

		if (functionType.equals("viewAllTasksInTheDatabase"))
		{
			Query query = new Query("Task").addSort("taskID");
			
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			// Use PreparedQuery interface to retrieve results
			PreparedQuery preparedQuery = datastore.prepare(query);
			
			%>
			<table border="1">
				<tr>
					<th>Project ID</th>
					<th>ID</th>
					<th>Task Description</th>
					<th>Due Date</th>
					<th>Priority</th>
				</tr>
	
			<%
			
			for (Entity result : preparedQuery.asIterable())
			{
				//Integer taskIDRetrieved = (Integer) result.getProperty("taskID");
				int projectIDRetrieved = ((Long) result.getProperty("projectID")).intValue();
				int taskIDRetrieved = ((Long) result.getProperty("taskID")).intValue();
				String taskDescriptionRetrieved = (String) result.getProperty("taskDescription");
				Date dueDateRetrieved = (Date) result.getProperty("dueDate");
				int priorityRetrieved = ((Long) result.getProperty("priority")).intValue();

				Task task=new Task(taskIDRetrieved,taskDescriptionRetrieved,dueDateRetrieved);
				task.setProjectID(projectIDRetrieved);
				task.setPriority(priorityRetrieved);
			
				%>
		
				<tr>
					<td><%=task.getProjectID()%></td>
					<td><%=task.getId()%></td>
					<td><%=task.getTaskDescription()%></td>
					<td><%=task.getDueDateAsShortFormat()%></td>
					<td><%=task.getPriority()%></td>
				</tr>
				<%
			}
			%>

		</table>	
		
		<%
		responseFromServer="See table above. The talbe will not be sent to the client, and is for testing purposes only";
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

	if (functionType.equals("getProjects"))
	{
	%>
		<p>Submitted email: <%=email%></p> 
	<%
	}

	if (functionType.equals("getTasks"))
	{
	%>
		<p>Submitted email: <%=email%></p> 
		<p>Submitted projectID: <%=projectID%></p>
	<%
	}

	if (functionType.equals("isEmailAvailableForSignup"))
	{
	%>
		<p>Submitted email: <%=email%></p> 
	<%
	}
	
	if (functionType.equals("signup"))
	{
	%>
		<p>Submitted email: <%=email%></p> 
		<p>Submitted password: <%=password%></p> 
	<%
	}

	if (functionType.equals("addProject"))
	{
	%>
		<p>Submitted email: <%=email%></p> 
		<p>Submitted project Name: <%=projectName%></p> 
	<%
	}

	if (functionType.equals("addTask"))
	{
	%>
		<p>Submitted Task Description: <%=taskDescription%></p> 
		<p>Submitted Due Date: <%=dueDate%></p> 
		<p>Submitted Project ID: <%=projectID%></p> 
		<p>Submitted Priority: <%=priority%></p> 
	<%
	}

	if (functionType.equals("deleteTask"))
	{
	%>
		<p>Submitted Task Description: <%=taskDescription%></p> 
		<p>Submitted email: <%=email%></p> 
		<p>Submitted project ID: <%=projectID%></p> 
		<p>Submitted task ID: <%=taskID%></p> 
	<%
	}
					
	
	%>
	
	<p>Response from server:<br/>
	<%=responseFromServer%>
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
	
	<hr/>
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
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		User/email: <input type="text" name="email" value="<%=email%>"><br/> 
		Password: <input type="text" name="password" value="<%=password%>"><br/>
		<input type="hidden" name="functionType" value="testLogin">
		
		<input type="submit" value="Access Database & Test Login" >
	</form>
	
	<hr/>
	
	<h3>Test view all tasks in the database</h3>
	<p>Check the <a href="https://console.developers.google.com/project/apps~tasky-server/datastore/query" target="_blank">Google Developers Console</a> to verify the data</p>
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		<input type="hidden" name="functionType" value="viewAllTasksInTheDatabase">

		<input type="submit" value="Access Google Datastore & View All Tasks in the database" >
	</form>
	
	<h3>Test add task</h3>
	<p>Check the <a href="https://console.developers.google.com/project/apps~tasky-server/datastore/query" target="_blank">Google Developers Console</a> to verify the data added</p>
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		Task Description: <input type="text" name="taskDescription" value="sample task"><br/> 
		Due Date: <input type="text" name="dueDate" value="today"><br/>
		Project ID: <input type="text" name="projectID" value="1"><br/>
		Priority (1 (high)-5(low)): <input type="text" name="priority" value="5"><br/>
		<input type="hidden" name="functionType" value="addTask">

		<input type="submit" value="Access Google Datastore & Add Task" >
	</form>
	
	<h3>Test get all tasks for a particular project</h3>
	<p>Check the <a href="https://console.developers.google.com/project/apps~tasky-server/datastore/query" target="_blank">Google Developers Console</a> to verify the data</p>
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		User/email: <input type="text" name="email" value="<%=email%>"><br/> 
		Project ID: <input type="text" name="projectID" value="1"><br/>
		<input type="hidden" name="functionType" value="getTasks">

		<input type="submit" value="Access Google Datastore & Get All Tasks for a specific project that belongs to a user" >
	</form>
	
	<h3>Test delete a task for a particular project</h3>
	<p>Check the <a href="https://console.developers.google.com/project/apps~tasky-server/datastore/query" target="_blank">Google Developers Console</a> to verify the data</p>
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		User/email: <input type="text" name="email" value="<%=email%>"><br/> 
		Project ID: <input type="text" name="projectID" value="1"><br/>
		Task ID: <input type="text" name="taskID" value="-1"><br/>
		<input type="hidden" name="functionType" value="deleteTask">

		<input type="submit" value="Access Google Datastore & Delete a task" >
	</form>
	

  </body>
</html>