package cs435.tasky;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author Jarrett Gabel
 *
 */
public class CalendarActivity extends Activity {
	
	ArrayList<Task> taskList = new ArrayList<Task>();
	ArrayList<TextView> a = new ArrayList<TextView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		GridView gView = (GridView) findViewById(R.id.calView);
		loadGridViewData(gView);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		GlobalTaskList tasks = (GlobalTaskList) getApplication();
		if (tasks.getList().size() == 0)
		{
			getTaskDataFromServer(tasks);
		}
		
	}
	
	/**
	 * Strictly testing purposes only; this method will be removed.
	 * @param email
	 * @param password
	 * @return
	 */
	private String signup(String email, String password)
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
	
	private void loadGridViewData(GridView g)
	{
		Log.v("CalTest", "starting load");
		
		Log.v("CalTest", "creating textview and setting text");
		TextView tView = new TextView(this);
		tView.setText("S"); a.add(tView);
		Log.v("CalTest", "onto Monday build");
		tView = new TextView(this);
		tView.setText("M"); a.add(tView);
		Log.v("CalTest", "onto Tuesday build");
		tView = new TextView(this);
		tView.setText("T"); a.add(tView);
		
		tView = new TextView(this);
		tView.setText("W"); a.add(tView);
		
		tView = new TextView(this);
		tView.setText("TH"); a.add(tView);
		
		tView = new TextView(this);
		tView.setText("F"); a.add(tView);
		
		tView = new TextView(this);
		tView.setText("S"); a.add(tView);
		
		for (int x = 1; x < 32; x++)
		{
			tView = new TextView(this);
			tView.setText(String.valueOf(x)); a.add(tView);
		}
		
		Log.v("CalTest", "Adding the adapter to the gridview");
		
		TextAdapter tA = new TextAdapter(this, a);
		
		g.setAdapter(tA);
		Log.v("CalTest", "Setting Click Listener");
		g.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
					long id) {
				String data = (String) ((TextView) v).getText();
				if (data.contains("\n"))
				{
					Intent i = new Intent(getApplicationContext(), TaskViewActivity.class);
					i.putExtra("DATE", ((TextView)v).getText());
					startActivity(i);
				}
				else
					Toast.makeText(getApplicationContext(), "Didn't have it", Toast.LENGTH_LONG).show();
			}
			
		});
		Log.v("CalTest", "Finished onCreate");
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent i;
		switch (item.getItemId()) {
		
		case R.id.calToDoList:
			Intent calI = new Intent(this, ToDoListActivity.class);
			startActivity(calI);
			break;
		case R.id.calTVOpt:
			Intent TVI = new Intent(this, TVSearchActivity.class);
			startActivity(TVI);
			break;
		case R.id.calAddTask:
			Task t = new Task("test", "Description", new GregorianCalendar(2014, 4, 5));
			taskList.add(t);
			GlobalTaskList tL = (GlobalTaskList) getApplication();
			tL.getList().add(t);
			addToView(t);
			addToServer(t);
			break;
		case R.id.calEdit:
			i = new Intent(this, EditTaskActivity.class);
			i.putExtra("RETURN", Constants.CALENDAR);
			startActivity(i);
		case R.id.calViewAll:
			i = new Intent(this, TaskViewActivity.class);
			startActivity(i);
		default:
			return super.onOptionsItemSelected(item);
		
		}
		
		return false;
		
	}
	/**
	 * The goal of the gridView is to tell the user how many tasks are due on each day
	 * Thus, addToView will increment the counter of tasks due on each day whenever
	 * tasks are added to the Calendar's Task List.
	 * @param t a new task to be added to the gridview
	 */
	private void addToView(Task t)
	{
		String dayToCheck = String.valueOf(t.getDueDate()
				.get(GregorianCalendar.DAY_OF_MONTH));
		Log.v("CalTest", dayToCheck);
		for (int x = 0; x < a.size(); x++)
		{
			TextView text = (TextView) a.get(x);
			String aText[] = text.getText().toString().split("\n");
			
			if (aText[0] == dayToCheck)
					{
						if (aText.length == 1) //no tasks are due on this day
						{
							a.get(x).setText((aText[0] + "\n 1 Task").toString());
						}
						else //at least 1 task is due on this day
						{
							a.get(x).setText(aText[0] + "\n" + String.valueOf
									(Integer.valueOf(aText[1]) + 1) + "Tasks");
						}

					}
		}
		TextAdapter tA = new TextAdapter(this, a);
		GridView g = (GridView) findViewById(R.id.calView);
		g.setAdapter(tA);
		
	}
	
	private void addToServer(Task t)
	{
		try
		{
			//connect to the servlet for adding a new project
//			URL urlToServlet = new URL("http://localhost:8888/AddProjectServlet");
			URL urlToServlet = new URL("http://tasky-server.appspot.com/AddProjectServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);


			//create the request to the server
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());


			//the request is like a "file" with 3 lines:
			//ADD_PROJECT
			//email
			//projectName
			writerToServer.write("ADD_PROJECT");
			writerToServer.write("\n");
			writerToServer.write("firstUser@gmail.com");
			writerToServer.write("\n");
			writerToServer.write(t.getName());
			writerToServer.write("\n");


			writerToServer.close();


			//TODO: replace with logging functionality
			System.out.println("CLIENT: generated the following request");
			System.out.println("ADD_PROJECT");
			System.out.println("firstUser@gmail.com");
			System.out.println(t.getName());
			System.out.println("CLIENT: end of request");

			//get the response from the server, which is very similar to reading from a file
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String addProjectStatus;

			//the response will be a string saying if the operation was successful or not 
			addProjectStatus = readerFromServer.readLine();

			readerFromServer.close();

			System.out.println("CLIENT: got response from server=" + addProjectStatus);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	
	private void getTaskDataFromServer(GlobalTaskList t)
	{
		try
		{
			//connect to the servlet for getting all the projects for a user in
//			URL urlToServlet = new URL("http://localhost:8888/GetProjectsServlet");
			URL urlToServlet = new URL("http://tasky-server.appspot.com/GetProjectsServlet");
			URLConnection connection = urlToServlet.openConnection();
	        connection.setDoOutput(true);


			//create the request to the server
			OutputStreamWriter writerToServer = new OutputStreamWriter(connection.getOutputStream());


			//the request is like a "file" with 2 lines:
			//GET_PROJECTS
			//email
			writerToServer.write("GET_PROJECTS");
			writerToServer.write("\n");
			writerToServer.write("firstUser@gmail.com");
			writerToServer.write("\n");


			writerToServer.close();


			//get the response from the server, which is very similar to reading from a file
			BufferedReader readerFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));


			String responseFromServerAsString="";
			ArrayList<Task> listOfProjects=new ArrayList<Task>();
			int numberOfProjects=Integer.parseInt(readerFromServer.readLine());
			for (int i = 0; i < numberOfProjects; i++)
			{
				int projectID = Integer.parseInt(readerFromServer.readLine());
				String projectName = readerFromServer.readLine();


				Task project = new Task(projectName, "NA");
				listOfProjects.add(project);


				responseFromServerAsString+=projectID+"\t"+projectName+"\n";
			}


			readerFromServer.close();

			t.setList(listOfProjects);
			

			System.out.println("CLIENT: got response from server=" + responseFromServerAsString);

		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar, menu);
		return true;
	}
	/**
	 * 
	 * @author Jarrett Gabel
	 *
	 */
	private class TextAdapter extends BaseAdapter 
	{

		Context c;
		ArrayList<TextView> textList;
		
		public TextAdapter(Context c, ArrayList<TextView> newList) {
			
			this.c = c;
			this.textList = newList;
			Log.v("CalTest","TextList is (true = null): " + String.valueOf(textList == null));
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return textList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return textList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public void setItemText(int index, String newText)
		{
			textList.get(index).setText(newText);
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			TextView newText = new TextView(getBaseContext());
			newText.setTextColor(0xFF000000);
			newText.setHeight(50);
			newText.setTextSize(24);
			newText.setText(textList.get(arg0).getText());
			return newText;
		}
		
	}
	
}
