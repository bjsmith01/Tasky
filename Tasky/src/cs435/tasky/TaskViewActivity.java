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
	ListViewAdapter l;
	ArrayList<String> taskList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_view);
		
		GlobalTaskList tasks = (GlobalTaskList) getApplication();
		//ArrayAdapter<String> l = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		int year = this.getIntent().getIntExtra("YEAR", 0) + 2000;
		int month = this.getIntent().getIntExtra("MONTH", 0);
		int day = this.getIntent().getIntExtra("DAY", 0);
		
		Log.v("CalTest", String.valueOf(tasks.getTaskList().size()));
		for (int x = 0; x < tasks.getTaskList().size(); x++)
		{
			Task t = tasks.getTaskList().get(x);
			if (t.getDueDate().get(GregorianCalendar.YEAR) == year
					&& t.getDueDate().get(GregorianCalendar.MONTH) == month
					&& t.getDueDate().get(GregorianCalendar.DAY_OF_MONTH) == day)
			{
				if (t.isCompleted())
					taskList.add(tasks.getTaskList().get(x).getName() + " is complete");
				else
					taskList.add(tasks.getTaskList().get(x).getName());
				idList.add(x);
			}

		}
		
		lView = (ListView) findViewById(R.id.taskViewView);
		l = new ListViewAdapter(this, taskList);
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
	/**
	 * Takes a given task and marks it as complete if it is not already completed
	 * @param tk the task to complete
	 * @param position the index of the task within the list view
	 */
	public void completeTask(Task tk, int position)
	{
			//ArrayAdapter<String> a = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1);	
			
			//for (int x = 0; x < lView.getAdapter().getCount(); x++)
			//{
			//	if (x == position)
			//	{
					taskList.remove(position);
					taskList.add(position, tk.getName() + " is completed");
				//	a.add(tk.getName() + "  is completed");
			//	}
			//	else
			//	{
			//		a.add(lView.getAdapter().getItem(x).toString());
			//	}
		//	}
			
			l.changeAdapter(taskList);
					
			lView.setAdapter(null);
			lView.setAdapter(l);

			tk.setCompleted(true);
	}
	/**
	 * Mark a completed task as not complete. The task must be completed beforehand.
	 * @param tk the completed task
	 * @param position the index of the task within the listview
	 */
	public void uncompleteTask(Task tk, int position)
	{
		if (tk.isCompleted())
		{
			taskList.remove(position);
			taskList.add(position, tk.getName());
			l.changeAdapter(taskList);
			lView.setAdapter(null);
			lView.setAdapter(l);

			tk.setCompleted(false);
			//ArrayAdapter<String> a = new ArrayAdapter
			//		<String>(getBaseContext(), 
			//				android.R.layout.simple_list_item_1);	
			
			//for (int x = 0; x < lView.getAdapter().getCount(); x++)
			//{
			//	if (x == position)
			//	{
			//		a.add(tk.getName());
			//	}
			///	else
			//	{
			//		a.add(lView.getAdapter().getItem(x).toString());
			//	}
			}
			

		}
	/**
	 * Delete a completed task
	 * @param tk the task to be deleted
	 * @param position the index of the task within the listview
	 */
	public void deleteTask(Task tk, int position)
	{		
		/*ArrayAdapter<String> a = new ArrayAdapter
				<String>(getBaseContext(), 
						android.R.layout.simple_list_item_1);	
		
		for (int x = 0; x < lView.getAdapter().getCount(); x++)
		{
			if (x == position)
			{
				//Do nothing. Acts as the delete.
			}
			else
			{
				a.add(lView.getAdapter().getItem(x).toString());
			}
		}
		*/
		taskList.remove(position);
		l.changeAdapter(taskList);
		
		lView.setAdapter(null);
		lView.setAdapter(l);
		
		GlobalTaskList gL = (GlobalTaskList) getApplication();
		Task t = gL.getTaskList().get((int) idList.get(position));
		Log.v("ServerTest", String.valueOf(t.getID()));
		ServerInfo.deleteTask(gL.username, String.valueOf(getProjId(t.getID())), 
				String.valueOf(t.getID()));

		for (int x = 0; x < gL.getTaskList().size(); x++){
			if (t.getID() == gL.getTaskList().get(x).getID())
			{
				gL.getTaskList().remove(x);
				break;
			}
		}
		
		for (Folder f : gL.getFolderList())
		{
			for (int x = 0; x < f.TaskList.size(); x++)
			{
				if (t.getID() == f.TaskList.get(x).getID())
				{
					f.TaskList.remove(x);
					break;
				}
			}
		}
		
		
		//gL.taskList.remove((int)idList.get(position));

	}

	private int getProjId(long taskID)
	{
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
	
	class touchListener extends SimpleOnGestureListener{
		
		@Override
		public boolean onFling(MotionEvent startTouch, MotionEvent endTouch, float velX, float velY)
		{	
			GlobalTaskList g = (GlobalTaskList) getApplication();
			
			int position = (int) lView.getItemIdAtPosition((lView.pointToPosition( (int) 
					startTouch.getX(), (int) startTouch.getY())));
			Task tk = g.getTaskList().get(idList.get(position));
			
			if (Math.abs(endTouch.getX() - startTouch.getX()) > 
			Constants.SWIPE_MIN_DISTANCE && (endTouch.getX() > startTouch.getX()))
			{
				if (!tk.isCompleted())
				{
					completeTask(tk, position);
				}
				else
				{
					deleteTask(tk, position);
				}
			}
			else if (Math.abs(endTouch.getX() - startTouch.getX()) > 
			Constants.SWIPE_MIN_DISTANCE && (endTouch.getX() < startTouch.getX()))
			{
				uncompleteTask(tk, position);
			}
			
			return false;
		}
		
		public boolean onSingleTapUp(MotionEvent tap)
		{
			
			GlobalTaskList g = (GlobalTaskList) getApplication();
			Intent i = new Intent(getBaseContext(), EditTaskActivity.class);

			int pos = lView.pointToPosition( (int) tap.getX(), (int) tap.getY());
			
			Task t = g.getTaskList().get(idList.get(pos));
			i.putExtra("NAME", t.getName());
			i.putExtra("DYEAR", t.getDueDate().get(GregorianCalendar.YEAR));
			i.putExtra("DMONTH", t.getDueDate().get(GregorianCalendar.MONTH));
			i.putExtra("DDAY", t.getDueDate().get(GregorianCalendar.DAY_OF_MONTH));
			i.putExtra("YEAR", getIntent().getIntExtra("YEAR", 0));
			i.putExtra("MONTH", getIntent().getIntExtra("MONTH", 1));
			i.putExtra("DAY", getIntent().getIntExtra("DAY", 1));
			i.putExtra("PRI", t.getPriority());
			
			i.putExtra("RETURN", Constants.TASKVIEW);
			i.putExtra("ID", idList.get(pos));
			startActivity(i);
			return false;
			
		}
	}
}
