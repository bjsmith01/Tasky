package cs435.tasky;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ToDoListActivity extends FragmentActivity implements
		ActionBar.OnNavigationListener {

		ArrayList<Integer> idList = new ArrayList<Integer>();
		ListView lView;
		ListViewAdapter l;
		ArrayList<String> taskList=new ArrayList<String>();
		static Folder folder;
		int folderIndex = 0;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_to_do_list);

			GlobalTaskList tasks = (GlobalTaskList) getApplication();
			folder=new Folder(tasks.taskList);

			//ArrayAdapter<String> l = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
			if (tasks.getFolderList().size() > 0)
			{
				for (int x = 0; x < tasks.getFolderList().get(folderIndex).TaskList.size(); x++)
				{
					Task t = tasks.getFolderList().get(folderIndex).TaskList.get(x);
					if (t.isCompleted())
						taskList.add(tasks.getTaskList().get(x).getName() + " is complete");
					else
						taskList.add(tasks.getTaskList().get(x).getName());
					idList.add(x);		
				}
			}
			lView = (ListView) findViewById(R.id.expandableListView1);
			l=new ListViewAdapter(this,taskList);
			lView.setAdapter(l);

			TextView t = (TextView) findViewById(R.id.toDoFolder);
			if (tasks.getFolderList().size()!=0){
				t.setText(tasks.getFolderList().get(folderIndex).getName());
			}

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
/*
		@Override
		public void onResume() {
		    super.onResume(); 
		    GlobalTaskList tasks = (GlobalTaskList) getApplication();
		    folder.changeList(tasks.taskList);
		    taskList.clear();
		    for (int i=0;i<tasks.taskList.size();i++){
		    	taskList.add(tasks.taskList.get(i).getName());
		    }
		    
		    l.changeAdapter(taskList);
			lView.setAdapter(null);
			lView.setAdapter(l);
		}
	
*/
	//determines what shows up when menu button is pressed.
	//looks at to_do_list.xml and populates the menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do_list, menu);
		return true;
	}

	//determines what happens when an item in the menu is selected
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.calendar:
	        Log.i("In Menu","Calendar Selected");
	        //Bring up the calendar activity
	        Intent calStart = new Intent(this, CalendarActivity.class);
	        startActivity(calStart);
	        Log.v("CalTest", "Starting Calendar");
	        return true;


	    case R.id.add_task:
	        Log.i("In Menu","Add Task Selected");
	        Intent add = new Intent(this,AddTaskActivity.class);
	        startActivity(add);

	        //for creating  a file


	        return true;
	    case R.id.tv_search:
	        Log.i("In Menu","TV Search Selected");
	        Intent tv = new Intent(this,TVSearchActivity.class);
	        startActivity(tv);
	        return true;
	    case R.id.toDoLogOut:
	    	GlobalTaskList t = (GlobalTaskList) getApplication();
	    	t.folderList = new ArrayList<Folder>();
	    	t.taskList = new ArrayList<Task>();
	    	Intent lI = new Intent(this, LogInActivity.class);
	    	startActivity(lI);
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	//determines what happens when an item in the action bar is pressed
	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
		return true;
	}


	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_to_do_list_dummy, container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
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
		taskList.remove(position);
		taskList.add(position,tk.getName()+ "  is completed");

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
			taskList.add(position,tk.getName());

			l.changeAdapter(taskList);

			lView.setAdapter(null);
			lView.setAdapter(l);

			tk.setCompleted(false);
		}
	}
	/**
	 * Delete a completed task
	 * @param tk the task to be deleted
	 * @param position the index of the task within the listview
	 */
	public void deleteTask(Task tk, int position)
	{		

		taskList.remove(position);

		l.changeAdapter(taskList);
		lView.setAdapter(null);
		lView.setAdapter(l);

		GlobalTaskList gL = (GlobalTaskList) getApplication();
		gL.taskList.remove((int)idList.get(position));

	}

	public void nextFolder(View view)
	{
		
		GlobalTaskList tasks = (GlobalTaskList) getApplication();
		if (tasks.getFolderList().size()>1){
			taskList.clear();
			folderIndex++;
			if (folderIndex >= tasks.getFolderList().size())
			{
				folderIndex = 0;
			}
	
			for (int x = 0; x < tasks.getFolderList().get(folderIndex).TaskList.size(); x++)
			{
				Task t = tasks.getFolderList().get(folderIndex).TaskList.get(x);
				if (t.isCompleted())
					taskList.add(tasks.getTaskList().get(x).getName() + " is complete");
				else
					taskList.add(tasks.getTaskList().get(x).getName());
				idList.add(x);		
			}
	
			lView = (ListView) findViewById(R.id.expandableListView1);
			l=new ListViewAdapter(this,taskList);
			lView.setAdapter(l);
	
			TextView t = (TextView) findViewById(R.id.toDoFolder);
			
			t.setText(tasks.getFolderList().get(folderIndex).getName());
		}

	}

	public void prevFolder(View view)
	{
		GlobalTaskList tasks = (GlobalTaskList) getApplication();
		if (tasks.getFolderList().size()>1){
			taskList.clear();
			folderIndex--;
			if (folderIndex < 0)
			{
				folderIndex = tasks.getFolderList().size() - 1;
			}
	
			for (int x = 0; x < tasks.getFolderList().get(folderIndex).TaskList.size(); x++)
			{
				Task t = tasks.getFolderList().get(folderIndex).TaskList.get(x);
				if (t.isCompleted())
					taskList.add(tasks.getTaskList().get(x).getName() + " is complete");
				else
					taskList.add(tasks.getTaskList().get(x).getName());
				idList.add(x);		
			}
	
			lView = (ListView) findViewById(R.id.expandableListView1);
			l=new ListViewAdapter(this,taskList);
			lView.setAdapter(l);
	
			TextView t = (TextView) findViewById(R.id.toDoFolder);
			t.setText(tasks.getFolderList().get(folderIndex).getName());
		}
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
			i.putExtra("RETURN", Constants.TASKY);
			i.putExtra("ID", idList.get(pos));
			startActivity(i);
			return false;

		}


	}

}