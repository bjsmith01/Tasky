<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="edu.wm.cs.cs435.tasky.model.Server"%>
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

<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>

  <body>

	<%
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String projectID = request.getParameter("projectID");
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
	%>
	
	<%
		//simulate access to database
		if (functionType.equals("testLogin"))
		{
			int loginStatus = Server.login(email, password);
			responseFromServer = "" + loginStatus;
		}
		else
			if (functionType.equals("testGetProjects"))
			{
				String actualListOfProjectsAsText = Server.getProjects(email);
				responseFromServer=actualListOfProjectsAsText;
			}
			else
				if (functionType.equals("testGetTasks"))
				{
					String actualListOfTasksAsText = Server.getTasks(email,projectID);
					responseFromServer=actualListOfTasksAsText;
				}
	%>
	
	<h1>Values that were submitted</h1>
	<p>Submitted functionType: <%=functionType%></p>

	<%
		if (functionType.equals("testLogin"))
		{
	%>
		<p>The return value should be 0 (success), 1 (invalid username) or 2 (invalid password)</p> 
		<p>Submitted email: <%=email%></p> 
		<p>Submitted password: <%=password%></p> 
	<%
		}
		else
			if (functionType.equals("testGetProjects"))
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
	%>
	
	<p>Response from server:<br/>
	<%=responseFromServer%>
	</p> 
	
	<hr/>
	
	<h3>Test Login functionality</h3>
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		User/email: <input type="text" name="email" value="<%=email%>"><br/> 
		Password: <input type="text" name="password" value="<%=password%>"><br/>
		<input type="hidden" name="functionType" value="testLogin">
		
		<input type="submit" value="Access Database & Test Login" >
	</form>
	
	<br/>
	<h3>Test get list of projects from user</h3>
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		User/email: <input type="text" name="email" value="<%=email%>"><br/> 
		<input type="hidden" name="functionType" value="testGetProjects">

		<input type="submit" value="Access Database & Test Get List of Projects" >
	</form>

	<br/>
	<h3>Test get list of tasks for a particular project</h3>
	<form action="testAccessToTaskyDatabase.jsp" method="get">
		User/email: <input type="text" name="email" value="<%=email%>"><br/> 
		ProjectID: <input type="text" name="projectID" value="<%=projectID%>"><br/>
		<input type="hidden" name="functionType" value="testGetTasks">

		<input type="submit" value="Access Database & Test Get List of Tasks" >
	</form>

  </body>
</html>