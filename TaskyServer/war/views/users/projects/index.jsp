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

<% String projectID = request.getParameter("projectID");  %>
<div class="col-md-8 col-md-offset-2">
<div class="text-center"><h3>Projects</h3></div>

	<table class="table table-hover">
		<thead> 
			<tr>
				<th>Project Name</th>
			</tr>
		</thead>

	<tbody>
	<%
	ArrayList<Project> projectArray = Server.instance.getProjects(email); 
	
	if (projectArray.isEmpty()) { %> 
	<div class="alert alert-success">You have no projects. Use the Add New Project button below to start!</div>
	<% } else {
	for (Project project : projectArray) { 
	%>
	
			<tr>
				<td><a href="/views/users/tasks/index.jsp
				?projectID=<%=project.getId()%>
				&projectName=<%=project.getName()%>" class="btn btn-default btn-lg">
				<%=project.getName()%></a></td>
			</tr>
	<% }} %>

	</tbody>
	</table>	


	<div class="text-center">
		<div class="btn-group btn-group-lg">
			<a href="create.jsp" class="btn btn-default">Add New Project</a>
		</div>
	</div> <br />

</div>


<%@ include file="./footer.jsp" %>
</div>
