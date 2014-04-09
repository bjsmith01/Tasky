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
	<p>In order to use Tasky, you should log in or sign up first.</p>
	<div class="btn-group btn-group-lg">
		<a class="btn btn-default" href="/views/sessions/create.jsp" >Log In!</a>
		<a class="btn btn-default" href="/views/users/create.jsp">Sign Up!</a>
	</div>
</div>


<%@ include file="./views/footer.jsp" %>
</div>

</body>
</html>