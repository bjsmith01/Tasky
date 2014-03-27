package cs435.tasky.simpleClientForAccessingLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * This is just a simple client to show how to connect to the server, how to
 * pass an email and password and get a login status from the server
 * 
 * @author Fengfeng (Mia) Liu
 * 
 */
public class SimpleClientForAccessingLogin
{

	public static void main(String[] args) 
	{
		String email; 
		String password;
		int loginStatus;
		
		//case 1
		email = "firstUser@gmail.com";
		password = "123";
		loginStatus = login(email,password);
		System.out.println("For email: "+email+" and password: "+password+" the response was: "+loginStatus);
		System.out.println("====================================");
		
		//case 2
		email = "firstUserThatDoesNotExit@gmail.com";
		password = "123";
		loginStatus = login(email,password);
		System.out.println("For email: "+email+" and password: "+password+" the response was: "+loginStatus);
		System.out.println("====================================");
		
		//case 3
		email = "firstUser@gmail.com";
		password = "123InvalidPassword";
		loginStatus = login(email,password);
		System.out.println("For email: "+email+" and password: "+password+" the response was: "+loginStatus);
		System.out.println("====================================");
	}

	private static int login(String email, String password)
	{
		try
		{
			//connect to the servlet for logging in
//			URL urlToServlet = new URL("http://localhost:8888/LoginServlet");
			URL urlToServlet = new URL("http://tasky-server.appspot.com/LoginServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);
	        
			//create the request to the server
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());

			//the request is like a "file" with 3 lines:
			//LOGIN
			//email
			//password
			writerToServer.write("LOGIN");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			writerToServer.write(password);
			writerToServer.write("\n");
			
			writerToServer.close();
			
			//TODO: replace with logging functionality
			System.out.println("CLIENT: generated the following request");
			System.out.println("LOGIN");
			System.out.println(email);
			System.out.println(password);
			System.out.println("CLIENT: end of request");

			
			//get the response from the server, which is very similar to reading from a file
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String loginStatusAsString;
			
			
			//the response will be a single number: 0 (for success), 1 (for invalid email), 2 (for invalid password)
			loginStatusAsString = readerFromServer.readLine();
			
			readerFromServer.close();

			int loginStatus=Integer.parseInt(loginStatusAsString);
			
			System.out.println("CLIENT: got response from server=" + loginStatus);
			
			return loginStatus;
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return -1;
	}
}
