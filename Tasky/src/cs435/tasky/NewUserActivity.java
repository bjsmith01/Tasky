package cs435.tasky;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
	}

	public void createUser(View view)
	{
		EditText userName = (EditText) findViewById(R.id.newUserEmail);
		EditText password = (EditText) findViewById(R.id.newUserPassword);
		EditText passwordCheck = (EditText) findViewById(R.id.newUserPasswordCheck);
		if (userName.getText().toString() != "" 
				&& userName.getText().toString().contains("@") 
				&& password.getText().toString() != "" 
				&& password.getText().toString()
				.equals(passwordCheck.getText().toString()))
		{

		if (create(userName.getText().toString(), 
				password.getText().toString())
				.equals(Constants.NEW_USER_ADDED_TO_DATABASE))
		{
			Intent i = new Intent(this, LogInActivity.class);
			startActivity(i);
		}
		else
			Toast.makeText(this, "Email/password already in use", Toast.LENGTH_LONG).show();
		}
		else
			{
			Toast.makeText(this, "Invalid Email/password data", Toast.LENGTH_LONG).show();
			}
			}
	
	public void Cancel(View view)
	{
		Intent i = new Intent(this, LogInActivity.class);
		startActivity(i);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_user, menu);
		return true;
	}

	private String create(String email, String password)
	{
		try
		{
			//connect to the servlet for signup command
//				URL urlToServlet = new URL("http://localhost:8888/SignupServlet");
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