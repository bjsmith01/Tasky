package cs435.tasky;

import java.util.GregorianCalendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.NumberPicker.OnValueChangeListener;

public class EditTaskActivity extends Activity {
	
	NumberPicker dueYear;	NumberPicker remindYear;
	NumberPicker dueMonth;	NumberPicker remindMonth;
	NumberPicker dueDay;	NumberPicker remindDay;
	TextView dueDate;		TextView remindDate;
	boolean useReminder = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		initializeDueDateInfo();
		initializeReminderDateInfo();
		EditText name = (EditText) findViewById(R.id.editName);
		name.setText(getIntent().getStringExtra("NAME"));
		EditText desc = (EditText) findViewById(R.id.editDesc);
		desc.setText(getIntent().getStringExtra("DESC"));
		dueYear.setValue(getIntent().getIntExtra("DYEAR", 2000));
		dueMonth.setValue(getIntent().getIntExtra("DMONTH", 1));
		dueDay.setValue(getIntent().getIntExtra("DDAY", 1));
		
		if (getIntent().getBooleanExtra("HASREMINDER", false))
		{
			CheckBox b = (CheckBox) findViewById(R.id.editViewRemind);
			b.setChecked(true);
			remindYear.setValue(getIntent().getIntExtra("RYEAR", 2000));
			remindYear.setVisibility(View.VISIBLE);
			remindMonth.setValue(getIntent().getIntExtra("RMONTH", 1));
			remindMonth.setVisibility(View.VISIBLE);
			remindDay.setValue(getIntent().getIntExtra("RDAY", 1));
			remindDay.setVisibility(View.VISIBLE);
			remindDate.setVisibility(View.VISIBLE);
		}
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
	/**
	 * Set up the variables and listeners necessary for the number pickers
	 * that represent the reminder date
	 */
	private void initializeReminderDateInfo()
	{
		remindDate = (TextView) findViewById(R.id.editRemindDate);
		remindDate.setText("Jan. 1 2014");
		
		remindYear = (NumberPicker) findViewById(R.id.editRemindYear);
		remindYear.setMaxValue(3000);
		remindYear.setMinValue(1);
		remindYear.setValue(2014);
		
		remindMonth = (NumberPicker) findViewById(R.id.editRemindMonth);
		remindMonth.setValue(1);
		remindMonth.setMaxValue(12);
		remindMonth.setMinValue(1);
		
		remindDay = (NumberPicker) findViewById(R.id.editRemindDay);
		remindDay.setValue(1);
		remindDay.setMinValue(1);
		remindDay.setMaxValue(31);
		
		remindYear.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				int daysInMonth = 0;
				
				GregorianCalendar c = new GregorianCalendar(newVal, remindMonth.getValue() - 1, 1);
				daysInMonth = c.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
				remindDay.setMaxValue(daysInMonth);
				if (remindDay.getValue() > daysInMonth)
					remindDay.setValue(daysInMonth);

				remindDate.setText(getMonth(remindMonth.getValue()) + " " + remindDay.getValue() + " " + newVal);
			}
			
		});
		remindMonth.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker arg0, int oldVal, int newVal) {
				// TODO Auto-generated method stub
				int daysInMonth = 0;
				
				GregorianCalendar c = new GregorianCalendar(remindYear.getValue(), newVal - 1, 1);
				daysInMonth = c.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
				remindDay.setMaxValue(daysInMonth);
				if (remindDay.getValue() > daysInMonth)
					remindDay.setValue(daysInMonth);

				remindDate.setText(getMonth(newVal) + " " + remindDay.getValue() + " " + remindYear.getValue());
			}
			
		});
		remindDay.setOnValueChangedListener(new OnValueChangeListener(){

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				
				remindDate.setText(getMonth(remindMonth.getValue()) + " " + newVal + " " + remindYear.getValue());
				
			}
			
		});
	}
	
	public void acceptClick(View view)
	{
		EditText name = (EditText) findViewById(R.id.editName);
		if (!name.getText().toString().equals(""))
		{
			if (getIntent().getIntExtra("RETURN", 0) == Constants.TASKVIEW)
			{
				EditText desc = (EditText) findViewById(R.id.editDesc);
				GregorianCalendar due = new GregorianCalendar(dueYear.getValue(),
						dueMonth.getValue(), dueDay.getValue());
				
				Task t = new Task(name.getText().toString(), 
						desc.getText().toString(), due);
				
				CheckBox hasReminder = (CheckBox) findViewById(R.id.editViewRemind);
				if (hasReminder.isChecked())
				{
					GregorianCalendar reminder = new GregorianCalendar(
							remindYear.getValue(), remindMonth.getValue(),
							remindDay.getValue());
					t.setReminder(reminder);
					GlobalTaskList gL = (GlobalTaskList) getApplication();
					gL.getList().set(getIntent().getIntExtra("ID", -1), t);
				}
				else
				{
					GlobalTaskList gL = (GlobalTaskList) getApplication();
					gL.getList().set(getIntent().getIntExtra("ID", -1), t);
				
				}
				Intent i = new Intent(this, TaskViewActivity.class);
				
				i.putExtra("YEAR", getIntent().getIntExtra("YEAR", 0));
				i.putExtra("MONTH", getIntent().getIntExtra("MONTH", 1));
				i.putExtra("DAY", getIntent().getIntExtra("DAY", 1));
				
				startActivity(i);
			}
		}
		else
			Toast.makeText(this, "All tasks require a name", Toast.LENGTH_LONG).show();
		
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
	}
	
	/**
	 * Switches the reminder option on and off for the task, thus either
	 * hiding or revealing the reminder options.
	 * @param view the Set a reminder checkbox
	 */
	public void switchReminderView(View view){
		CheckBox c = (CheckBox) view;
		if (c.isChecked()){
			remindYear.setVisibility(View.VISIBLE);
			remindMonth.setVisibility(View.VISIBLE);
			remindDay.setVisibility(View.VISIBLE);
			remindDate.setVisibility(View.VISIBLE);
		}
		else
		{
			remindYear.setVisibility(View.INVISIBLE);
			remindMonth.setVisibility(View.INVISIBLE);
			remindDay.setVisibility(View.INVISIBLE);
			remindDate.setVisibility(View.INVISIBLE);
		}
	}
}
	
