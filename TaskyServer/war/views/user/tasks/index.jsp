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

<%@ include file="../../header.jsp" %>

<!--  Login logic -->

<%

	String email = session.getAttribute("email").toString(); 
	
%>

<p> Submitted email: <%= email %></p>

<!--  End login logic -->

<div class="col-md-8 col-md-offset-2 text-center">

</div>


<%@ include file="../../footer.jsp" %>
</div>
