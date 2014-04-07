package cs435.tasky;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class EditTaskActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_task, menu);
		return true;
	}
	
	public void acceptClick(View view)
	{
		if (getIntent().getIntExtra("RETURN", 0) == Constants.CALENDAR)
		{
			Intent i = new Intent(this, CalendarActivity.class);
			startActivity(i);
		}
		
	}

	protected void cancelClick(View view)
	{
		if (getIntent().getIntExtra("RETURN", 0) == Constants.CALENDAR)
		{
			Intent i = new Intent(this, CalendarActivity.class);
			startActivity(i);
		}
	}
	
}
