<%@page import="java.util.ArrayList"%>
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

<% String projectID = request.getParameter("projectID");
	String projectName = request.getParameter("projectName"); 
	System.out.println("projectID:" + projectID); 
	System.out.println("projectName:  " + projectName); %>
<div class="col-md-8 col-md-offset-2">
<div class="text-center"><h3>Project: <%= projectName %> </h3></div>

	<table class="table table-hover">
		<thead> 
			<tr>
				<th>Task Description</th>
				<th>Priority</th>
				<th>Due Date</th>
			</tr>
		</thead>

	<tbody>
	<%
	ArrayList<Task> taskArray = Server.instance.getTasks(email, projectID); 
	if (taskArray.isEmpty()) { %> 
	<div class="alert alert-success">You have no tasks. Use the Add New Task button below to start!</div>
	<% } else {
	for (Task task : taskArray) { 
	%>
	
			<tr>
				<td><%=task.getTaskDescription()%></td>
				<td><%=task.getPriority() %>
				<td><%=task.getDueDateAsShortFormat()%></td>
			</tr>
	<%
		}}
	%>

	</tbody>
	</table>	


	<div class="text-center">
		<div class="btn-group btn-group-lg">
			<a href="create.jsp?projectID=<%=projectID%>&projectName=<%=projectName%>" class="btn btn-default">Add New Task</a>
		</div>
	</div> <br />

</div>


<%@ include file="./footer.jsp" %>
</div>
