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
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

public class AddTaskActivity extends Activity {
	
	NumberPicker dueYear;
	NumberPicker dueMonth;
	NumberPicker dueDay;
	TextView dueDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		initializeDueDateInfo();
		
		Spinner pri = (Spinner) findViewById(R.id.addPriority);
		
		ArrayAdapter<Integer> a = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line);
		
		a.add(1); a.add(2); a.add(3);
		a.add(4); a.add(5);
		
		pri.setAdapter((SpinnerAdapter) a);
		
		pri.setSelection(0);
		
		ArrayAdapter<String> folderList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
		Spinner folders = (Spinner) findViewById(R.id.addFolders);
		
		GlobalTaskList g = (GlobalTaskList) getApplication();
		for (int x = 0;x < g.getFolderList().size(); x++)
		{
			folderList.add(g.getFolderList().get(x).getName());
		}
		folders.setAdapter(folderList);
		folders.setSelection(0);
		
		dueDate.setText(getMonth(dueMonth.getValue()) + " " + dueDay.getValue() + " " + dueYear.getValue());
		
		if (getIntent().getIntExtra("RETURN", -1) == Constants.TASKVIEW)
		{
			EditText nameBox = (EditText) findViewById(R.id.addName);
			nameBox.setText(getIntent().getStringExtra("NAME"));
			dueDay.setValue(getIntent().getIntExtra("DDAY", 1));
			dueMonth.setValue(getIntent().getIntExtra("DMONTH", 1));
			dueYear.setValue(getIntent().getIntExtra("DYEAR", 2014));
			dueDate.setText(getMonth(dueMonth.getValue()) + " " + dueDay.getValue() + " " + dueYear.getValue());
		}
		
	}
	/**
	 * Returns an integer value representing the month
	 * @param monthVal an integer value between 1 and 12 from the number
	 * pickers
	 * @return a text value representing the selected month; i.e. 'Jan.'
	 * or 'May'
	 */
	public String getMonth(int monthVal)
	{
		
		switch (monthVal)
		{
		case 1:
			return "Jan.";
		case 2:
			return "Feb.";
		case 3:
			return "Mar.";
		case 4:
			return "Apr.";
		case 5:
			return "May";
		case 6:
			return "Jun.";
		case 7:
			return "Jul.";
		case 8:
			return "Aug.";
		case 9:
			return "Sep.";
		case 10:
			return "Oct.";
		case 11:
			return "Nov.";
		case 12:
			return "Dec.";
		default:
			break;
				
		}
		return "";
	}	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_task, menu);
		return true;
	}
	/**
	 * Set up the variables and listeners necessary for the number pickers
	 * responsible for displaying the user's selected due date.
	 */
	private void initializeDueDateInfo()
	{
		Calendar today = GregorianCalendar.getInstance();
		dueDate = (TextView) findViewById(R.id.addDueDate);
		dueDate.setText("Jan. 1 2014");
		
		dueYear = (NumberPicker) findViewById(R.id.addDueYear);
		dueYear.setMaxValue(3000);
		dueYear.setMinValue(1);
		dueYear.setValue(today.get(GregorianCalendar.YEAR));
		
		dueMonth = (NumberPicker) findViewById(R.id.addDueMonth);
		dueMonth.setMaxValue(12);
		dueMonth.setMinValue(1);
		dueMonth.setValue(today.get(GregorianCalendar.MONTH) + 1);
		
		dueDay = (NumberPicker) findViewById(R.id.addDueDay);
		dueDay.setMinValue(1);
		dueDay.setMaxValue(31);
		dueDay.setValue(today.get(GregorianCalendar.DAY_OF_MONTH));
		
		dueYear.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				int daysInMonth = 0;
				
				GregorianCalendar c = new GregorianCalendar(newVal, dueMonth.getValue() - 1, 1);
				daysInMonth = c.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
				dueDay.setMaxValue(daysInMonth);
				if (dueDay.getValue() > daysInMonth)
					dueDay.setValue(daysInMonth);

				dueDate.setText(getMonth(dueMonth.getValue()) + " " + dueDay.getValue() + " " + newVal);
			}
			
		});
		dueMonth.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker arg0, int oldVal, int newVal) {
				// TODO Auto-generated method stub
				int daysInMonth = 0;
				
				GregorianCalendar c = new GregorianCalendar(dueYear.getValue(), newVal - 1, 1);
				daysInMonth = c.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
				dueDay.setMaxValue(daysInMonth);
				if (dueDay.getValue() > daysInMonth)
					dueDay.setValue(daysInMonth);

				dueDate.setText(getMonth(newVal) + " " + dueDay.getValue() + " " + dueYear.getValue());
			}
			
		});
		dueDay.setOnValueChangedListener(new OnValueChangeListener(){

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				
				dueDate.setText(getMonth(dueMonth.getValue()) + " " + newVal + " " + dueYear.getValue());
				
			}
			
		});

	}		
	/**
	 * Create a task from the designated user input.
	 * Only acts if the task has been given a name.
	 * @param view The Create Task button
	 */
	public void createTask(View view)
	{
		EditText eName = (EditText) findViewById(R.id.addName);
		GregorianCalendar g = new GregorianCalendar(dueYear.getValue(),
				dueMonth.getValue(), dueDay.getValue());
		if (!eName.getText().toString().equals(""))
		{
			Task t = new Task(eName.getText().toString(), g);
			Spinner p = (Spinner) findViewById(R.id.addPriority);
			Spinner f = (Spinner) findViewById(R.id.addFolders);
			t.setPriority((Integer) p.getSelectedItem());
			
			GlobalTaskList gL = (GlobalTaskList) getApplication();
		//	gL.taskList.add(t);
			EditText et = (EditText) findViewById(R.id.addNewFolder);
			String newFolderName = et.getText().toString();
			if (newFolderName.length() == 0)
			{
				//add task to the global and then to the server
				gL.getFolderList().get(f.getSelectedItemPosition()).AddTask(t);
				ServerInfo.addTask(t, gL.username, String.valueOf(gL.getFolderList().
						get(f.getSelectedItemPosition()).getID()));
				//update the tasks in global to get the new task id
				//Really needs a better system for retrieval. HIGHLY inefficient.
				gL.getFolderList().get(f.getSelectedItemPosition()).TaskList.clear();
				ArrayList<Task> a = new ArrayList<Task>();
				a = ServerInfo.getTasks(gL.username, String.valueOf(gL.getFolderList()
						.get(f.getSelectedItemPosition()).getID()));
				for (int x = 0; x < a.size(); x++)
				{
					gL.getFolderList().get(f.getSelectedItemPosition()).AddTask(a.get(x));
				}
			}
			else
			{
				//Same as above but this time also creating/adding a new folder/project
				//Update the ID in a similar fashion.
				ServerInfo.addFolder(gL.username, et.getText().toString());
				gL.setFolderList(ServerInfo.getFolders(gL.getUsername()));
				
				for (Folder fo: gL.getFolderList())
				{
					fo.TaskList = ServerInfo.getTasks(gL.getUsername(), String.valueOf(fo.getID()));
				}
				
				gL.getFolderList().get(gL.getFolderList().size() - 1).AddTask(t);
				ServerInfo.addTask(t, gL.username, String.valueOf(gL.getFolderList()
						.get(gL.getFolderList().size() - 1).getID()));
				gL.getFolderList().get(gL.getFolderList().size() - 1).TaskList.clear();
				
				ArrayList<Task> a = new ArrayList<Task>();
				a = ServerInfo.getTasks(gL.username, String.valueOf(gL.getFolderList()
						.get(gL.getFolderList().size() - 1).getID()));
				for (int x = 0; x < a.size(); x++)
				{
					gL.getFolderList().get(gL.getFolderList().size() - 1).AddTask(a.get(x));
				}
				
			}
			
			gL.setTaskList(new ArrayList<Task>());
			for (Folder fo : gL.getFolderList())
			{
				for (Task ta : fo.TaskList)
				{
					gL.getTaskList().add(ta);
				}
			}
			Intent i = new Intent(this, CalendarActivity.class);
			startActivity(i);
		}
		else
			Toast.makeText(this, "The task requires "
					+ "a name before being created", 
					Toast.LENGTH_LONG).show();
		
	}
	/**
	 * Cancels the creation process and returns to the previous view
	 * @param view The Cancel button
	 */
	public void cancel(View view)
	{
		Intent i = new Intent(this, CalendarActivity.class);
		startActivity(i);
	}
}