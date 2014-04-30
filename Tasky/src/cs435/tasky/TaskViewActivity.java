package cs435.tasky;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
/**
 * Class that depicts a list of tasks according to some criteria
 * This class currently as a representation of the ToDo List and will eventually
 * be replaced.
 * @author Jarrett Gabel
 */
public class TaskViewActivity extends Activity {

	ArrayList<Integer> idList = new ArrayList<Integer>();
	ListView lView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_view);
		
		GlobalTaskList tasks = (GlobalTaskList) getApplication();
		ArrayAdapter<String> l = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		int year = this.getIntent().getIntExtra("YEAR", 0) + 2000;
		int month = this.getIntent().getIntExtra("MONTH", 0);
		int day = this.getIntent().getIntExtra("DAY", 0);
		
		Log.v("CalTest", String.valueOf(tasks.getList().size()));
		for (int x = 0; x < tasks.getList().size(); x++)
		{
			Task t = tasks.getList().get(x);
			Log.v("viewTest", "TYEAR: " + t.getDueDate().get(GregorianCalendar.YEAR));
			Log.v("viewTest", "TMONTH: " + t.getDueDate().get(GregorianCalendar.MONTH));
			Log.v("viewTest", "TDAY: " + t.getDueDate().get(GregorianCalendar.DAY_OF_MONTH));
			if (t.getDueDate().get(GregorianCalendar.YEAR) == year
					&& t.getDueDate().get(GregorianCalendar.MONTH) == month
					&& t.getDueDate().get(GregorianCalendar.DAY_OF_MONTH) == day)
			{
				if (t.isCompleted())
					l.add(tasks.getList().get(x).getName() + " is complete");
				else
					l.add(tasks.getList().get(x).getName());
				idList.add(x);
			}

		}
		
		lView = (ListView) findViewById(R.id.taskViewView);
		lView.setAdapter(l);
		
		final GestureDetector tL = new GestureDetector(this, new touchListener());
		OnTouchListener test = new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				tL.onTouchEvent(event);
				return false;
			}
			
		};
		lView.setOnTouchListener(test);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_view, menu);
		return true;
	}
	
	public void goBack(View view)
	{
		Intent i = new Intent(this, CalendarActivity.class);
		startActivity(i);
	}
	
	class touchListener extends SimpleOnGestureListener{
		
		@Override
		public boolean onFling(MotionEvent startTouch, MotionEvent endTouch, float velX, float velY)
		{	
			GlobalTaskList g = (GlobalTaskList) getApplication();
			
			int position = (int) lView.getItemIdAtPosition((lView.pointToPosition( (int) 
					startTouch.getX(), (int) startTouch.getY())));
			Task tk = g.getList().get(idList.get(position));
			
			if (Math.abs(endTouch.getX() - startTouch.getX()) > 
			Constants.SWIPE_MIN_DISTANCE && (endTouch.getX() > startTouch.getX()))
			{

				if (!tk.isCompleted())
				{
					ArrayAdapter<String> a = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1);	
					
					for (int x = 0; x < lView.getAdapter().getCount(); x++)
					{
						if (x == position)
						{
							a.add(tk.getName() + "  is completed");
						}
						else
						{
							a.add(lView.getAdapter().getItem(x).toString());
						}
					}
					
					lView.setAdapter(null);
					lView.setAdapter(a);

					tk.setCompleted(true);
					return true;
				}
			}
			else if (Math.abs(endTouch.getX() - startTouch.getX()) > 
			Constants.SWIPE_MIN_DISTANCE && (endTouch.getX() < startTouch.getX()))
			{
				if (tk.isCompleted())
				{
					ArrayAdapter<String> a = new ArrayAdapter
							<String>(getBaseContext(), 
									android.R.layout.simple_list_item_1);	
					
					for (int x = 0; x < lView.getAdapter().getCount(); x++)
					{
						if (x == position)
						{
							a.add(tk.getName());
						}
						else
						{
							a.add(lView.getAdapter().getItem(x).toString());
						}
					}
					
					lView.setAdapter(null);
					lView.setAdapter(a);

					tk.setCompleted(false);
				}
			}
			return false;
		}
		
		public boolean onSingleTapUp(MotionEvent tap)
		
		{
			
			GlobalTaskList g = (GlobalTaskList) getApplication();
			Intent i = new Intent(getBaseContext(), EditTaskActivity.class);

			int pos = lView.pointToPosition( (int) tap.getX(), (int) tap.getY());
			
			
			Task t = g.getList().get(idList.get(pos));
			i.putExtra("NAME", t.getName());
			i.putExtra("DESC", t.getDesc());
			i.putExtra("DYEAR", t.getDueDate().get(GregorianCalendar.YEAR));
			i.putExtra("DMONTH", t.getDueDate().get(GregorianCalendar.MONTH));
			i.putExtra("DDAY", t.getDueDate().get(GregorianCalendar.DAY_OF_MONTH));
			i.putExtra("YEAR", getIntent().getIntExtra("YEAR", 0));
			i.putExtra("MONTH", getIntent().getIntExtra("MONTH", 1));
			i.putExtra("DAY", getIntent().getIntExtra("DAY", 1));
			
			i.putExtra("HASREMINDER", t.hasReminder());
			if (t.hasReminder())
			{
				i.putExtra("RYEAR", t.getReminder().get(GregorianCalendar.YEAR));
				i.putExtra("RMONTH", t.getReminder().get(GregorianCalendar.MONTH));
				i.putExtra("RDAY", t.getReminder().get(GregorianCalendar.DAY_OF_MONTH));
			}
			i.putExtra("RETURN", Constants.TASKVIEW);
			i.putExtra("ID", idList.get(pos));
			startActivity(i);
			return false;
			
		}
		
	}
}
