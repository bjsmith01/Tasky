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

<!DOCTYPE html>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />

	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>Tasky</title>
</head>
<body>
<div class="container">

<%@ include file="./header.jsp" %>


<div class="col-md-8 col-md-offset-2">
<div class="text-center"><h3>Task List</h3></div>
	<%
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


	<div class="text-center">
		<a href="new.jsp" class="btn btn-default">Add New Task</a>
	</div>
</div>


<%@ include file="./footer.jsp" %>
</div>
