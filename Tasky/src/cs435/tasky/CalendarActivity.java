package cs435.tasky;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		GlobalTaskList tasks = (GlobalTaskList) getApplication();
		Log.v("CalTest", "checking tasks size");
		if (tasks.getList().size() == 0)
		{
			//getTaskDataFromServer(tasks);
			
		}
		Log.v("CalTest", "task size: " + tasks.getList().size());

		Spinner s = (Spinner) findViewById(R.id.calMonths);
		ArrayAdapter<CharSequence> monthVals = ArrayAdapter.createFromResource(this, R.array.monthArray, android.R.layout.simple_dropdown_item_1line);
		monthVals.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		s.setAdapter(monthVals);
		
		s.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View item,
					int pos, long id) {
				GridView gView = (GridView) findViewById(R.id.calView);
				Spinner year = (Spinner) findViewById(R.id.calYear);
				loadGridViewData(gView, pos + 1, Integer.valueOf(year.getSelectedItem().toString()));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
				
			}
			
		});
		
		Spinner year = (Spinner) findViewById(R.id.calYear);
		ArrayAdapter<Integer> yearVals = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line);
		
		for (int x = 0; x <= 1000; x++)
		{
			yearVals.add(2000+x);
		}
		year.setAdapter((SpinnerAdapter)yearVals);
		year.setSelection(14);
		year.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View item,
					int pos, long id) {
				GridView gView = (GridView) findViewById(R.id.calView);
				Spinner month = (Spinner) findViewById(R.id.calMonths);
				TextView t = (TextView) item;
				loadGridViewData(gView, month.getSelectedItemPosition() + 1, 
						Integer.valueOf(t.getText().toString()));
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

		GridView g = (GridView) findViewById(R.id.calView);
		Spinner month = (Spinner) findViewById(R.id.calMonths);
		loadGridViewData(g, month.getSelectedItemPosition() + 1, year.getSelectedItemPosition() + 2000);
		Log.v("CalTest", "a element count = " + String.valueOf(a.size()));
		
	}

	private void loadGridViewData(GridView g, int monthValue, int yearValue)
	{
		GregorianCalendar cal = new GregorianCalendar(yearValue, monthValue - 1, 1);
		a.clear();
		TextView tView = new TextView(this);
		tView.setText("S"); a.add(tView);
		tView = new TextView(this);
		tView.setText("M"); a.add(tView);
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
		
		for (int x = 1; x < cal.get(GregorianCalendar.DAY_OF_WEEK); x++)
		{
			tView = new TextView(this);
			tView.setText(""); a.add(tView);
		}
		
		GlobalTaskList tasks = (GlobalTaskList) getApplication();
		int [] taskCount = new int[32];
		for (int y = 1; y < cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); y++)
		{
			for (int x = 0; x < tasks.getList().size(); x++)
			{
				Task t = tasks.getList().get(x);
				Log.v("CalTest", "TaskDay = " + t.getDueDate().get(GregorianCalendar.DAY_OF_MONTH));
				Log.v("CalTest", "TaskMonth = " + t.getDueDate().get(GregorianCalendar.MONTH));
				Log.v("CalTest", "TaskYear = " + t.getDueDate().get(GregorianCalendar.YEAR));
				Log.v("CalTest", "checkDay = " + y);
				Log.v("CalTest", "checkMonth = " + monthValue);
				Log.v("CalTest", "checkYear = " + yearValue);
				if (monthValue == t.getDueDate().get(GregorianCalendar.MONTH) 
						&& yearValue == t.getDueDate().get(GregorianCalendar.YEAR)
						&& y == t.getDueDate().get(GregorianCalendar.DAY_OF_MONTH))
						{
							Log.v("CalTest", "Added to list");
							taskCount[y]++;
						}
			}
		}

		for (int x = 1; x < cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) + 1; x++)
		{
			tView = new TextView(this);
			if (taskCount[x] == 1){
				tView.setText(String.valueOf(x) + "\n" + String.valueOf(taskCount[x]) + " Task"); a.add(tView);
			}
			else if(taskCount[x] > 1)
			{
				tView.setText(String.valueOf(x) + "\n" + String.valueOf(taskCount[x]) + " Tasks"); a.add(tView);
			}
			else
			{
				tView.setText(String.valueOf(x)); a.add(tView);
			}

		}
		
		Log.v("CalTest", "Adding the adapter to the gridview");
		
		TextAdapter tA = new TextAdapter(a);
		
		g.setAdapter(tA);
		Log.v("CalTest", "Setting Click Listener");
		g.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
					long id) {
				try
				{
					TextView vi = (TextView) v;
					Log.v("CalTest", "vi contents: " + vi.getText().toString());
					int day = 0;
					day = Integer.parseInt(vi.getText().
							toString().split("\n")[0]);

					Spinner m = (Spinner) findViewById(R.id.calMonths);
					Spinner y = (Spinner) findViewById(R.id.calYear);
					int month = m.getSelectedItemPosition() + 1;
					int year = y.getSelectedItemPosition();
					Intent i = new Intent(getApplicationContext(), 
							TaskViewActivity.class);
					i.putExtra("DAY", day);
					i.putExtra("MONTH", month);
					i.putExtra("YEAR", year);

					startActivity(i);
				}
				catch(NumberFormatException NFE)
				{
					
				}

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
			Intent addI = new Intent(this, AddTaskActivity.class);
			addI.putExtra("PrevView", Constants.CALENDAR);
			startActivity(addI);
			break;
		case R.id.calLogOut:
			GlobalTaskList g = (GlobalTaskList) getApplication();
			g.taskList = new ArrayList<Task>();
			i = new Intent(this, LogInActivity.class);
			startActivity(i);
		default:
			return super.onOptionsItemSelected(item);
		
		}
		
		return false;
		
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

		ArrayList<TextView> textList;
		
		public TextAdapter(ArrayList<TextView> newList) {
			
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

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			TextView newText = new TextView(getBaseContext());
			newText.setTextColor(0xFF000000);
			newText.setHeight(100);
			newText.setTextSize(20);
			newText.setText(textList.get(arg0).getText());
			return newText;
		}
		
	}
	
}
