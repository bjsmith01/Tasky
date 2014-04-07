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

<!--  Login logic -->

<%
	String email = ""; 
	String password = ""; 
	
	email = request.getParameter("email");
	password = request.getParameter("password");
	
	int loginStatus = Server.login(email, password);
	
	if (loginStatus == 0) {
		session.setAttribute("email", email); 
	    String redirectURL = "user/tasks/index.jsp";
	    response.addHeader("email", email);
	    response.sendRedirect(redirectURL);
	}
%>
<!--  End login logic -->

<div class="col-md-8 col-md-offset-2">
<h2>Login</h2>
	<form action="login.jsp" method="get" role="form">
		<div class="form-group">
			<label for="email1">Email:</label>
			<input class="form-control" type="text"  id="email1" name="email"> <br />
			<label for="password1">Password:</label>
			<input class="form-control" type="text" name="password" id="password1" ><br />
			
			<div class="text-center">
				<button type="submit" class="btn btn-success">Login</button>
			</div>
		</div>
	</form>
</div>


<%@ include file="./footer.jsp" %>
</div>
