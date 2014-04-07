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
 * signup a user given the email and password and how to get a response status from the server
 * 
 * @author Fengfeng (Mia) Liu
 * 
 */
public class SimpleClientForSignup
{

	public static void main(String[] args) 
	{
		String email; 
		String password;
		String signupStatus;
		
		//case 1
		email = "firstNewUser@gmail.com";
		password = "12345";
		signupStatus = signup(email,password);
		System.out.println("For email: "+email+" and password: "+password+" the response was: "+signupStatus);
		System.out.println("====================================");
		
		//case 2 should return that user already exists
		email = "firstNewUser@gmail.com";
		password = "123";
		signupStatus = signup(email,password);
		System.out.println("For email: "+email+" and password: "+password+" the response was: "+signupStatus);
		System.out.println("====================================");
		
		//case 3: should return that the password is invalid (too short)
		email = "firstNewNewUser@gmail.com";
		password = "123";
		signupStatus = signup(email,password);
		System.out.println("For email: "+email+" and password: "+password+" the response was: "+signupStatus);
		System.out.println("====================================");
	}

	private static String signup(String email, String password)
	{
		try
		{
			//connect to the servlet for signup command
//			URL urlToServlet = new URL("http://localhost:8888/SignupServlet");
			URL urlToServlet = new URL("http://tasky-server.appspot.com/SignupServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);
	        
			//create the request to the server
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());

			//the request is like a "file" with 3 lines:
			//SIGNUP
			//email
			//password
			writerToServer.write("SIGNUP");
			writerToServer.write("\n");
			writerToServer.write(email);
			writerToServer.write("\n");
			writerToServer.write(password);
			writerToServer.write("\n");
			
			writerToServer.close();
			
			//TODO: replace with logging functionality
			System.out.println("CLIENT: generated the following request");
			System.out.println("SIGNUP");
			System.out.println(email);
			System.out.println(password);
			System.out.println("CLIENT: end of request");

			
			//get the response from the server, which is very similar to reading from a file
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String signupStatus;
			
			
			//the response will be a String
			signupStatus = readerFromServer.readLine();
			
			readerFromServer.close();

			System.out.println("CLIENT: got response from server=" + signupStatus);
			
			return signupStatus;
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return "-1";
	}
}
