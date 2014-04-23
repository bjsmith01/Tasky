<%
	String email = session.getAttribute("email").toString(); 
%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
     		<a class="navbar-brand" href="/views/users/projects/index.jsp">Tasky</a>
	    </div>
      
		<ul class="nav navbar-nav navbar-right">
		  <li class="dropdown">
		    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><%= email %> <b class="caret"></b></a>
		    <ul class="dropdown-menu">
		      <li><a href="index.jsp">Tasks</a></li>
		      <li><a href="#">Calendar</a></li>
		      <li><a href="#">Something else here</a></li>
		      <li class="divider"></li>
		      <li><a href="/views/sessions/destroy.jsp">Log Out</a></li>
		    </ul>
		  </li>
		</ul>
	</div><!-- /.container-fluid -->
</nav>