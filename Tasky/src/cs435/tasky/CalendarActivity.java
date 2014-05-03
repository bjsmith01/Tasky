package cs435.tasky;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
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
/**
 * 
 * @author Jarrett Gabel
 *
 */
public class CalendarActivity extends Activity {
	
	ArrayList<TextView> a = new ArrayList<TextView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		Spinner month = (Spinner) findViewById(R.id.calMonths);
		ArrayAdapter<CharSequence> monthVals = ArrayAdapter.createFromResource(this, R.array.monthArray, android.R.layout.simple_dropdown_item_1line);
		monthVals.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		month.setAdapter(monthVals);
		
		month.setOnItemSelectedListener(new OnItemSelectedListener(){

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
				
			}});
		
		Spinner year = (Spinner) findViewById(R.id.calYear);
		ArrayAdapter<Integer> yearVals = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line);
		
		for (int x = 0; x <= 1000; x++)
		{
			yearVals.add(2000+x);
		}
		Calendar today = GregorianCalendar.getInstance();
		year.setAdapter((SpinnerAdapter)yearVals);
		year.setSelection(today.get(GregorianCalendar.YEAR) - 2000 );
		month.setSelection(today.get(GregorianCalendar.MONTH));
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
				
			}});
		
		GridView g = (GridView) findViewById(R.id.calView);
		loadGridViewData(g, month.getSelectedItemPosition() + 1, year.getSelectedItemPosition() + 2000);
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
			for (int x = 0; x < tasks.getTaskList().size(); x++)
			{
				Task t = tasks.getTaskList().get(x);
				if (monthValue == t.getDueDate().get(GregorianCalendar.MONTH) 
						&& yearValue == t.getDueDate().get(GregorianCalendar.YEAR)
						&& y == t.getDueDate().get(GregorianCalendar.DAY_OF_MONTH))
						{
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
		
		
		TextAdapter tA = new TextAdapter(a);
		
		g.setAdapter(tA);
		g.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
					long id) {
				try
				{
					TextView vi = (TextView) v;
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
