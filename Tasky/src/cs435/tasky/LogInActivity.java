package cs435.tasky;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}
	/**
	 * Takes the supplied email and password and checks the database to see if the combo exists
	 * If not, the values are rejected and the user is notified.
	 * @param view the log in button
	 */
	public void login(View view)
	{
		EditText username = (EditText) findViewById(R.id.loginUserName);
		EditText password = (EditText) findViewById(R.id.loginPassword);
		
		if (username.getText().toString() != "" 
				&& password.getText().toString() != ""
				&& privLogin(username.getText().toString(), password.getText().toString()))
		{
			GlobalTaskList g = (GlobalTaskList) getApplication();
			
			g.setUsername(username.getText().toString());
			g.setPassword(password.getText().toString());
			
			getProjectsFromServer();
			getTasksFromServer();
			
			Intent i = new Intent(this, CalendarActivity.class);
			startActivity(i);
		}
		else
		{
			Toast.makeText(this, "Invalid username/password", Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Pulls the tasks from the server that pertain to the user
	 */
	private void getTasksFromServer()
	{
		GlobalTaskList g = (GlobalTaskList) getApplication();
		for (Folder f : g.getFolderList())
		{
			ArrayList<Task> tasks = ServerInfo.getTasks(g.username, String.valueOf(f.getID()));
			for (int x = 0; x < tasks.size(); x++)
			{
				f.AddTask(tasks.get(x));
				g.taskList.add(tasks.get(x));
			}
		}

	}
	/**
	 * Pulls a list of folders from the server that will store the user's tasks
	 * Note that the folders will not be visible on the Calendar side of the application
	 */
	private void getProjectsFromServer()
	{
		GlobalTaskList g = (GlobalTaskList) getApplication();
		g.setFolderList(ServerInfo.getFolders(g.getUsername()));
	}
	
	
	private boolean privLogin(String email, String password)
	{
		Log.v("loginTest", "In privLogin");
		Log.v("loginTest", "Email is: " + email);
		Log.v("loginTest", "password is: " + password);
		try {
		URL urlToServlet = new URL("http://tasky-server.appspot.com/LoginServlet");
		URLConnection connection = urlToServlet.openConnection();
        connection.setDoOutput(true);
        Log.v("loginTest", "Connection established");
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

		//get the response from the server, which is very similar to reading from a file
		BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		String loginStatus;

		//the response will be a string representing the status (LOGIN_SUCCESSFUL, LOGIN_INVALID_USERNAME, or LOGIN_INVALID_PASSWORD)
		loginStatus = readerFromServer.readLine();

		readerFromServer.close();

		System.out.println("CLIENT: got response from server=" + loginStatus);
		Log.v("loginTest", loginStatus);
		return (loginStatus.equals(Constants.LOGIN_SUCCESSFUL));

	}
	catch (MalformedURLException e)
	{
		Log.v("loginTest", "malformedURL error");
		e.printStackTrace();
	}
	catch (IOException e)
	{
		Log.v("loginTest", "IOException");
		e.printStackTrace();
	}
	return false;
}

	public void newUser(View view)
	{
		Intent i = new Intent(this, NewUserActivity.class);
		startActivity(i);
	}
}
