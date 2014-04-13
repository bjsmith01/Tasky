<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.wm.cs.cs435.tasky.model.Server"%>
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
		String taskDescription = request.getParameter("taskDescription");
		String dueDate = request.getParameter("dueDate");
		String functionType = request.getParameter("functionType");
		String responseFromServer = "N/A";
		
		//set default values
		if (functionType==null)
			functionType="N/A";
		if (taskDescription==null)
			taskDescription="sample task";
		if (dueDate==null)
			dueDate="today";
	%>

	<h1>Values that were submitted</h1>
	<p>Submitted functionType: <%=functionType%></p>

	<%
	
	if (functionType.equals("addTask"))
	{
		Task task=new Task(taskDescription,dueDate);
		
		Entity entityTask=new Entity("Task");
		entityTask.setProperty("taskDescription", task.getTaskDescription());
		entityTask.setProperty("dueDate", task.getDueDateAsJavaData());
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
	<table class="table table-hover">
		<thead> 
			<tr>
				<th>ID</th>
				<th>Task Description</th>
				<th>Due Date</th>
			</tr>
		</thead>

	<tbody>
	<%
		
		for (Entity result : preparedQuery.asIterable())
		{
			//Integer taskIDRetrieved = (Integer) result.getProperty("taskID");
			int taskIDRetrieved = ((Long) result.getProperty("taskID")).intValue();
			String taskDescriptionRetrieved = (String) result.getProperty("taskDescription");
			Date dueDateRetrieved = (Date) result.getProperty("dueDate");

			Task task=new Task(taskIDRetrieved,taskDescriptionRetrieved,dueDateRetrieved);
			
		
	%>
	
			<tr>
				<td><%=task.getId()%></td>
				<td><%=task.getTaskDescription()%></td>
				<td><%=task.getDueDateAsShortFormat()%></td>
			</tr>
	<%
		}
	%>

	</tbody>
	</table>	
	
	<%
	}
	%>
	
	<hr/>
	
	<h3>Test add task</h3>
	<p>Check the <a href="https://console.developers.google.com/project/apps~tasky-server/datastore/query" target="_blank">Google Developers Console</a> to verify the data added</p>
	<form action="testGoogleDatastore.jsp" method="get">
		<div class="form-group">
			<label for="taskdesc">Task Description: </label>
			<input class="form-control" id="taskdesc" type="text" name="taskDescription" value="sample task">
			<label for="dueDate">Due Date: </label>
			<input class="form-control" id="dueDate" type="text" name="dueDate" value="today"><br/>
			<input type="hidden" name="functionType" value="addTask">
	
			<button class="btn btn-success" type="submit">Access Google Datastore &amp; Add Task</button>
		</div>
	</form>
	
	
	<h3>Test view all tasks</h3>
	<p>Check the <a href="https://console.developers.google.com/project/apps~tasky-server/datastore/query" target="_blank">Google Developers Console</a> to verify the data added</p>
	<form action="testGoogleDatastore.jsp" method="get">
		<div class="form-group">
			<input type="hidden" name="functionType" value="getTasks">
			<button class="btn btn-success" type="submit">Access Google Datastore &amp; View All Tasks</button>
		</div>
	</form>
	</div>
	
  </div>	
  </body>
</html>