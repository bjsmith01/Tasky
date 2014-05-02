package cs435.tasky;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.NumberPicker.OnValueChangeListener;

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
		EditText desc = (EditText) findViewById(R.id.editDesc);
		desc.setText(getIntent().getStringExtra("DESC"));
		dueYear.setValue(getIntent().getIntExtra("DYEAR", 2000));
		dueMonth.setValue(getIntent().getIntExtra("DMONTH", 1));
		dueDay.setValue(getIntent().getIntExtra("DDAY", 1));
		
		Spinner pri = (Spinner) findViewById(R.id.editPriority);
		
		ArrayAdapter<Integer> a = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line);
		
		a.add(1); a.add(2); a.add(3);
		a.add(4); a.add(5);
		
		pri.setAdapter((SpinnerAdapter) a);
		pri.setSelection(getIntent().getIntExtra("PRI", 1) - 1);
		
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
			if (getIntent().getIntExtra("RETURN", 0) == Constants.TASKVIEW)
			{
				EditText desc = (EditText) findViewById(R.id.editDesc);
				GregorianCalendar due = new GregorianCalendar(dueYear.getValue(),
						dueMonth.getValue(), dueDay.getValue());
				Spinner p = (Spinner) findViewById(R.id.editPriority);
				Task t = new Task(name.getText().toString(), 
						desc.getText().toString(), due);
				
				t.setPriority((Integer) p.getSelectedItem());
				
				GlobalTaskList gL = (GlobalTaskList) getApplication();
				gL.getList().set(getIntent().getIntExtra("ID", -1), t);
				
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
}
	
