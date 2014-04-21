<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="edu.wm.cs.cs435.tasky.model.Server"%>
<%@ page import="edu.wm.cs.cs435.tasky.model.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery" %>
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
		String taskDescription = request.getParameter("taskDescription");
		String dueDate = request.getParameter("dueDate");
		String projectID = request.getParameter("projectID");
		String functionType = request.getParameter("functionType");
		String responseFromServer = "N/A";
		
		//set default values
		if (functionType==null)
			functionType="N/A";
		if (taskDescription==null)
			taskDescription="sample task";
		if (dueDate==null)
			dueDate="today";
		if (projectID==null)
			projectID="1";
	%>

	<h1>Values that were submitted</h1>
	<p>Submitted functionType: <%=functionType%></p>

	<%
	
	if (functionType.equals("addTask"))
	{
		int projectIDint=Integer.parseInt(projectID);
		
		Task task=new Task(taskDescription,dueDate,projectIDint);
		
		Entity entityTask=new Entity("Task");
		entityTask.setProperty("taskDescription", task.getTaskDescription());
		entityTask.setProperty("dueDate", task.getDueDateAsJavaData());
		entityTask.setProperty("projectID", task.getProjectID());
		entityTask.setProperty("taskID", task.getId());
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entityTask);
	}
	
	
	if (functionType.equals("getTasks"))
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
		</tr>

	<%
		
		for (Entity result : preparedQuery.asIterable())
		{
			//Integer taskIDRetrieved = (Integer) result.getProperty("taskID");
			int projectIDRetrieved = ((Long) result.getProperty("projectID")).intValue();
			int taskIDRetrieved = ((Long) result.getProperty("taskID")).intValue();
			String taskDescriptionRetrieved = (String) result.getProperty("taskDescription");
			Date dueDateRetrieved = (Date) result.getProperty("dueDate");

			Task task=new Task(taskIDRetrieved,taskDescriptionRetrieved,dueDateRetrieved);
			task.setProjectID(projectIDRetrieved);
			
		
	%>
	
			<tr>
				<td><%=task.getProjectID()%></td>
				<td><%=task.getId()%></td>
				<td><%=task.getTaskDescription()%></td>
				<td><%=task.getDueDateAsShortFormat()%></td>
			</tr>
	<%
		}
	%>

	</table>	
	
	<%
	}
	%>
	
	<hr/>
	
	<h3>Test add task</h3>
	<p>Check the <a href="https://console.developers.google.com/project/apps~tasky-server/datastore/query" target="_blank">Google Developers Console</a> to verify the data added</p>
	<form action="testGoogleDatastore.jsp" method="get">
		Task Description: <input type="text" name="taskDescription" value="sample task"><br/> 
		Due Date: <input type="text" name="dueDate" value="today"><br/>
		Project ID: <input type="text" name="projectID" value="1"><br/>
		<input type="hidden" name="functionType" value="addTask">

		<input type="submit" value="Access Google Datastore & Add Task" >
	</form>
	
	
	<h3>Test view all tasks</h3>
	<p>Check the <a href="https://console.developers.google.com/project/apps~tasky-server/datastore/query" target="_blank">Google Developers Console</a> to verify the data added</p>
	<form action="testGoogleDatastore.jsp" method="get">
		<input type="hidden" name="functionType" value="getTasks">

		<input type="submit" value="Access Google Datastore & View All Tasks" >
	</form>
		
  </body>
</html>