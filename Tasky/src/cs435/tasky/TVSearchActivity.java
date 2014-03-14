package cs435.tasky;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class TVSearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tvsearch);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tvsearch, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.calendar:
	        Log.i("In Menu","Calendar Selected");
	        return true;
	    case R.id.todo_list:
	        Log.i("In Menu","ToDo List Selected");
	        Intent todo= new Intent(this,ToDoListActivity.class);
	        startActivity(todo);
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

}
