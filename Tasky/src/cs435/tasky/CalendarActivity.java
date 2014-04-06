package cs435.tasky;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
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

public class CalendarActivity extends Activity {
	
	ArrayList<Task> taskList = new ArrayList<Task>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		GridView gView = (GridView) findViewById(R.id.calView);
		loadGridViewData(gView);
	}
	
	private void loadGridViewData(GridView g)
	{
		Log.v("CalTest", "starting load");
		ArrayList<TextView> a = new ArrayList<TextView>();
		//ArrayAdapter<TextView> a = new ArrayAdapter<TextView>(this, android.R.layout.simple_list_item_1);
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
			addToView(t);
			break;
		default:
			return super.onOptionsItemSelected(item);
		
		}
		
		
		return false;
		
	}
	/*
	 * The goal of the gridView is to tell the user how many tasks are due on each day
	 * Thus, addToView will increment the counter of tasks due on each day whenever
	 * tasks are added to the Calendar's Task List.
	 */
	private void addToView(Task t)
	{
		ListAdapter a = ((GridView) (findViewById(R.id.calView))).getAdapter();
		
		for (int x = 0; x < a.getCount(); x++)
		{
			TextView text = (TextView) a.getItem(x);
			String aText[] = text.getText().toString().split("\n");
			
			if (aText[0] == String.valueOf(t.getDueDate()
					.get(GregorianCalendar.DAY_OF_MONTH)))
					{
						if (aText[1] == null) //no tasks are due on this day
						{
							text.setText((aText[0] + "\n 1 Task").toString());
						}
						else //at least 1 task is due on this day
						{
							text.setText(aText[0] + "\n" + String.valueOf
									(Integer.valueOf(aText[1]) + 1) + "Tasks");
						}

					}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar, menu);
		return true;
	}

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
			return textList.get(arg0).getText();
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
			return null;
		}
		
	}
	
}
