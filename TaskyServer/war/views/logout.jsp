<% 
	// Destroy the user session
	if (session != null) session.invalidate(); 

	// Redirect the user to the homepage
	response.sendRedirect("/");
%>