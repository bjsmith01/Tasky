package cs435.tasky;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Toast;

public class EditTaskActivity extends Activity {
	
	NumberPicker dueYear;	
	NumberPicker dueMonth;	
	NumberPicker dueDay;	
	TextView dueDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		initializeDueDateInfo();
		EditText name = (EditText) findViewById(R.id.editName);
		name.setText(getIntent().getStringExtra("NAME"));
		dueYear.setValue(getIntent().getIntExtra("DYEAR", 2000));
		dueMonth.setValue(getIntent().getIntExtra("DMONTH", 1));
		dueDay.setValue(getIntent().getIntExtra("DDAY", 1));
		
		GlobalTaskList g = (GlobalTaskList) getApplication();
		
		Spinner pri = (Spinner) findViewById(R.id.editPriority);
		Spinner f = (Spinner) findViewById(R.id.editFolders);		
		ArrayAdapter<Integer> a = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line);
		ArrayAdapter<String> folderList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
		a.add(1); a.add(2); a.add(3);
		a.add(4); a.add(5);
		
		pri.setAdapter((SpinnerAdapter) a);
		pri.setSelection(getIntent().getIntExtra("PRI", 1) - 1);
		
		for (int x = 0;x < g.getFolderList().size(); x++)
		{
			folderList.add(g.getFolderList().get(x).getName());
		}
		
		f.setAdapter(folderList);
		f.setSelection(0);
		if (getIntent().getIntExtra("RETURN", -1) == Constants.TASKVIEW)
		{
			f.setSelection(0);
			dueDay.setValue(getIntent().getIntExtra("DDAY", 1));
			dueMonth.setValue(getIntent().getIntExtra("DMONTH", 1));
			dueYear.setValue(getIntent().getIntExtra("DYEAR", 2014));
		}
		else
		{
			Task tsk = g.getTaskList().get(getIntent().getIntExtra("ID", -1)); //CRASH
			
			for (int x = 0; x < g.getFolderList().size(); x++)
			{
				for (int y = 0; y < g.getFolderList().get(x).TaskList.size(); y++)
				{
					if (g.getFolderList().get(x).TaskList.get(y).getID() == tsk.getID())
					{
						f.setSelection(x);
						break;
					}
				}
			}
		}
		
		dueDate.setText(getMonth(dueMonth.getValue()) + " " + dueDay.getValue() + " " + dueYear.getValue());
		
	}
	
	/**
	 *
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
		getMenuInflater().inflate(R.menu.edit_task, menu);
		return true;
	}
	
	/**
	 * Set up the variables and listeners necessary for the number pickers
	 * responsible for displaying the user's selected due date.
	 */
	private void initializeDueDateInfo()
	{
		dueDate = (TextView) findViewById(R.id.editDueDate);
		dueDate.setText("Jan. 1 2014");
		
		dueYear = (NumberPicker) findViewById(R.id.editDueYear);
		dueYear.setMaxValue(3000);
		dueYear.setMinValue(1);
		dueYear.setValue(2014);
		
		dueMonth = (NumberPicker) findViewById(R.id.editDueMonth);
		dueMonth.setValue(1);
		dueMonth.setMaxValue(12);
		dueMonth.setMinValue(1);
		
		dueDay = (NumberPicker) findViewById(R.id.editDueDay);
		dueDay.setValue(1);
		dueDay.setMinValue(1);
		dueDay.setMaxValue(31);
		
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
	
	public void acceptClick(View view)
	{
		EditText name = (EditText) findViewById(R.id.editName);
		if (!name.getText().toString().equals(""))
		{

				GlobalTaskList gL = (GlobalTaskList) getApplication();
				GregorianCalendar due = new GregorianCalendar(dueYear.getValue(),
						dueMonth.getValue(), dueDay.getValue());
				Spinner p = (Spinner) findViewById(R.id.editPriority);
				Spinner f = (Spinner) findViewById(R.id.editFolders);
				Task t = new Task(name.getText().toString(), due);
				Task oldTask = gL.getTaskList().get(getIntent().getIntExtra("ID", -1));
				
				if (t.getName().equals(oldTask.getName()) && t.getDueDate() == oldTask.getDueDate() &&
						t.getPriority() == oldTask.getPriority())
				{
					for (int x = 0; x < gL.getFolderList().size(); x++)
					{
						for (Task ta : gL.getFolderList().get(x).TaskList)
						{
							if (ta.getID() == oldTask.getID())
							{
								EditText newFolderBox = (EditText) findViewById(R.id.editNewFolder);
								Spinner folderBox = (Spinner) findViewById(R.id.editFolders);
								if (folderBox.getSelectedItemPosition() == x && newFolderBox.getText().toString() == "")
								{
									cancelClick(null);
									return;
								}
								
							}
						}
					}

				}
				
				t.setPriority((Integer) p.getSelectedItem());


				long ID = gL.getTaskList().get(getIntent().getIntExtra("ID",  -1)).getID();
				
				ServerInfo.deleteTask(gL.username, String.valueOf(getProjectID(ID))
						, String.valueOf(ID));
				
				//Replace the old task object with the new.
				gL.getTaskList().set(getIntent().getIntExtra("ID", -1), t);
				
				for (Folder fo : gL.getFolderList())
				{
					for (int x = 0; x < fo.TaskList.size(); x++)
					{
						if (fo.TaskList.get(x).getID() == ID)
						{
							fo.TaskList.remove(x);
							break;
						}
					}
				}
				
				EditText et = (EditText) findViewById(R.id.editNewFolder);
				String newFolderName = et.getText().toString();
				if (newFolderName.length() == 0)
				{
					gL.getFolderList().get(f.getSelectedItemPosition()).AddTask(t);
					ServerInfo.addTask(t, gL.username, String.valueOf(gL.getFolderList().
							get(f.getSelectedItemPosition()).getID()));
				}
				else
				{
					ServerInfo.addFolder(gL.username, et.getText().toString());
					gL.setFolderList(ServerInfo.getFolders(gL.getUsername()));
					gL.getFolderList().get(gL.getFolderList().size() - 1).AddTask(t);
					ServerInfo.addTask(t, gL.username, String.valueOf(gL.getFolderList()
							.get(gL.getFolderList().size() - 1).getID()));
				}
				
				gL.setTaskList(new ArrayList<Task>());
				for (Folder fo : gL.getFolderList())
				{
					for (Task ta : fo.TaskList)
					{
						gL.getTaskList().add(ta);
					}
				}
				
				if (getIntent().getIntExtra("RETURN", 0) == Constants.TASKVIEW)
				{
				
				Intent i = new Intent(this, TaskViewActivity.class);
				
				i.putExtra("YEAR", getIntent().getIntExtra("YEAR", 0));
				i.putExtra("MONTH", getIntent().getIntExtra("MONTH", 1));
				i.putExtra("DAY", getIntent().getIntExtra("DAY", 1));
				
				startActivity(i);
			}
			else
			{
				if (gL.getTaskList().size()!=0){
					gL.getTaskList().set(getIntent().getIntExtra("ID", -1), t);
				}
				else{
					gL.getTaskList().add(t);
				}
				Intent i = new Intent(this,ToDoListActivity.class);
				startActivity(i);
				
			}
		}
		
		else
			Toast.makeText(this, "All tasks require a name", Toast.LENGTH_LONG).show();
	}
	
	private long getProjectID(long taskID)
	{
		Log.v("EditTest", "Looking for: " + String.valueOf(taskID));
		GlobalTaskList g = (GlobalTaskList) getApplication();
		for (Folder f : g.getFolderList())
		{
			Log.v("ServerTest","Folder IDs: " + String.valueOf(f.getID()));
			for (Task t : f.TaskList)
			{
				Log.v("ServerTest", "Task IDs: " + String.valueOf(t.getID()));
				if (t.getID() == taskID)
				{
					Log.v("ServerTest", String.valueOf(t.getID()));
					return f.getID();
				}
			}
		}
		
		return -1;
		
	}
 
	public void cancelClick(View view)
	{
		if (getIntent().getIntExtra("RETURN", 0) == Constants.TASKVIEW)
		{
			Intent i = new Intent(this, TaskViewActivity.class);
			i.putExtra("YEAR", getIntent().getIntExtra("YEAR", 0));
			i.putExtra("MONTH", getIntent().getIntExtra("MONTH", 1));
			i.putExtra("DAY", getIntent().getIntExtra("DAY", 1));
			startActivity(i);
		}
		
		else
		{
			Intent i = new Intent(this, ToDoListActivity.class);
			startActivity(i);
		}
	}
}
	
