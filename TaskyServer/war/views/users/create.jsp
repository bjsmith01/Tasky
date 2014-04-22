<%@page import="edu.wm.cs.cs435.tasky.database.IServerDatabase"%>
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

<%@ include file="/views/header.jsp" %>


<!--  Check signup response -->
<% 
if (pageContext.findAttribute("signupStatus") != null) { 
	System.out.println("asdf"); 
	String signupStatus = pageContext.findAttribute("signupStatus").toString();
	System.out.println("signupstatus2:" + signupStatus); 
	if (signupStatus.equals(IServerDatabase.EMAIL_NOT_AVAILABLE_FOR_SIGNUP)) { %> 
<div class="alert alert-danger">
	Email already in use - try a different one. 
</div>
	<% } else if (signupStatus.equals(IServerDatabase.PASSWORD_INVALID_FOR_SIGNUP)) %>
<div class="alert alert-danger"> 
	Password invalid - please make it longer than 5 characters. 
</div>
 	<% } %>
	 

<div class="col-md-8 col-md-offset-2">
<h2>Sign Up</h2>
	<form action="/WebSignupServlet" method="post" role="form">
		<div class="form-group">
			<label for="email1">Email:</label>
			<input class="form-control" type="text"  id="email1" name="email"> <br />
			<label for="password1">Password:</label>
			<input class="form-control" type="password" name="password" id="password1" ><br />
			
			<div class="text-center">
				<button type="submit" class="btn btn-success">Sign Up</button>
			</div>
		</div>
	</form>
</div>


<%@ include file="/views/footer.jsp" %>
</div>
