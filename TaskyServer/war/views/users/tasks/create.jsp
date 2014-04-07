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

<!--  Task add logic -->

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
	
	if (functionType.equals("addTask"))
	{
		Task task=new Task(taskDescription,dueDate);
		
		Entity entityTask=new Entity("Task");
		entityTask.setProperty("taskDescription", task.getTaskDescription());
		entityTask.setProperty("dueDate", task.getDueDateAsJavaData());
		entityTask.setProperty("taskID", task.getId());
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entityTask);
		
		response.sendRedirect("index.jsp");
	}
%>

<!--  End task add logic -->

<div class="col-md-8 col-md-offset-2">

<div class="text-center"><h3>Add New Task</h3></div>

	<form action="new.jsp" method="get">
		<div class="form-group">
			<label for="taskdesc">Task Description: </label>
			<input class="form-control" id="taskdesc" type="text" name="taskDescription" > <br />
			<label for="dueDate">Due Date: </label>
			<input class="form-control" id="dueDate" type="text" name="dueDate" value="today"><br />
			<input type="hidden" name="functionType" value="addTask">
	
			<div class="text-center">
				<button class="btn btn-success" type="submit">Add Task</button>
			</div>
		</div>
	</form>
</div>


<%@ include file="./footer.jsp" %>
</div>
