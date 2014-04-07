package cs435.tasky;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
/**
 * Class that depicts a list of tasks according to some criteria
 * This class currently as a representation of the ToDo List and will eventually
 * be replaced.
 * @author Jarrett Gabel
 *
 */
public class TaskViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_view);
		
		GlobalTaskList tasks = (GlobalTaskList) getApplication();
		ArrayAdapter<String> l = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		Log.v("CalTest", String.valueOf(tasks.getList().size()));
		for (int x = 0; x < tasks.getList().size(); x++)
		{
			l.add(tasks.getList().get(x).getName());
		}
		
		ListView lView = (ListView) findViewById(R.id.taskViewView);
		lView.setAdapter(l);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_view, menu);
		return true;
	}

}
