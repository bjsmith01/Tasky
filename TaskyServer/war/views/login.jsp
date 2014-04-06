<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />

	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>Tasky</title>
</head>
<body>
<div class="container">

<%@ include file="./views/header.jsp" %>

<div class="col-md-8 col-md-offset-2 text-center jumbotron">
	<h1>Welcome to Tasky!</h1>
	<p>In order to use Tasky, you should log in first.</p>
	<a class="btn btn-lg btn-success" href="./views/login.jsp" >Log In!</a>
</div>


<%@ include file="./views/footer.jsp" %>
</div>
