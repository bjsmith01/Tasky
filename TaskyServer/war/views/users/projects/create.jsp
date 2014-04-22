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

<!--  Project add logic -->

<%
	String projectName = request.getParameter("projName"); 
	String functionType = request.getParameter("functionType");
	
	//set default values
	if (functionType==null)
		functionType="N/A";

	
	if (functionType.equals("addProject")) {
		String projectID = Server.instance.addProject(email, projectName);
		response.sendRedirect("/views/users/projects/index.jsp");
	}
	
%>

<!--  End task add logic -->

<div class="col-md-8 col-md-offset-2">

<div class="text-center"><h3>Add New Project</h3></div>

	<form class="form-horizontal" action="create.jsp" method="get">
		<div class="form-group">
			<label for="taskdesc" class="col-sm-3 control-label">Project Name: </label>
			<div class="col-sm-8">
				<input class="form-control" id="projName" type="text" name="projName" > <br />
			</div>
			<input type="hidden" name="functionType" value="addProject">
	
			<div class="text-center">
				<button class="btn btn-success" type="submit">Create Project!</button>
			</div>
		</div>
	</form>
</div>


<%@ include file="./footer.jsp" %>
</div>
