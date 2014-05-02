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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class ToDoListActivity extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	
	Folder folder= new Folder("Personal");
	MyAdapter myAdapter;
	ExpandableListView exv;
	ArrayList<String> savedFolders= new ArrayList<String>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_list);

		/*
		//the names of the saved folders are stored in saved_folders.txt in
		//res/raw/  access using getResources().openRawResource(R.raw.saved_folders)
		try{
			
			FileInputStream fis= openFileInput("saved_folders.txt");

			//store the names of the folders in an array 
			int ch;
			StringBuffer fileContent = new StringBuffer("");
			while ((ch = fis.read()) != -1){
				if ((char)ch!='\n'){
					fileContent.append((char)ch);
				}
			}
			fis.close();
	        
		}catch(Exception e){
			FileOutputStream fos2 = openFileOutput("saved_folders.txt",Context.MODE_PRIVATE);
			fos2.write(null);
			fos2.close();
		}

			
        try{
        	//grab the folder file set its contents to folder
        	String filename= savedFolders.get(0)+".txt";
			FileInputStream fin = openFileInput(filename);
			ObjectInputStream ois= new ObjectInputStream(fin);
			
			folder=(Folder) ois.readObject();
			Log.e("readFile","file successfully read");
			fin.close();
			ois.close();
			
		} catch(Exception ex){
			
			Log.e("No Folders Saved","Creating New Folder");
			
			//if there are no saved folders
			//then create the default Personal Folder
			folder= new Folder("Personal");
			
	        try{
	         
	         FileOutputStream fileOut= openFileOutput("personal.txt",Context.MODE_PRIVATE);
	         ObjectOutputStream obOut= new ObjectOutputStream(fileOut);
	         obOut.writeObject(folder);
	         obOut.close();
	         fileOut.close();

	        }
	        catch (Exception ex2){
	        	Log.e("ToDoListActivity",ex2.getMessage());
	        }
			
		}
		*/
		
		//folder=new Folder("Personal");
		//folder.AddTask(new Task("Test","Testing App \n and stuff \n and stuff",new GregorianCalendar(12,12,2014)));
		//folder.AddTask(new Task("Test2","Testing App \n and stuff \n and stuff",new GregorianCalendar(12,12,2014)));
		myAdapter=new MyAdapter(this,folder);
		
		exv= (ExpandableListView) findViewById(R.id.expandableListView1);
		exv.setAdapter(myAdapter);

//		// Set up the action bar to show a dropdown list.
//		final ActionBar actionBar = getActionBar();
//		actionBar.setDisplayShowTitleEnabled(false);
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//
//		// Set up the dropdown list navigation in the action bar in the upper-left corner.
//		actionBar.setListNavigationCallbacks(
//		// Specify a SpinnerAdapter to populate the dropdown list.
//				new ArrayAdapter<String>(actionBar.getThemedContext(),
//						android.R.layout.simple_list_item_1,
//						android.R.id.text1, new String[] {
//								getString(R.string.title),
//								getString(R.string.folders), }), this);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

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
	        Bundle taskBundle = storeTasksForActivityChange();
	        calStart.putExtras(taskBundle);
	        startActivity(calStart);
	        Log.v("CalTest", "Starting Calendar");
	        return true;
	        
	        
	    case R.id.add_task:
	        Log.i("In Menu","Add Task Selected");
	        Task task=new Task("Test Add","Testing App \n and stuff \n and stuff",new GregorianCalendar(12,12,2014));
	        folder.AddTask(task);
	        myAdapter.changeAdapter(folder);
	        exv.setAdapter(myAdapter);
	        
	        //for creating  a file
	        
	        
	        return true;
	    case R.id.tv_search:
	        Log.i("In Menu","TV Search Selected");
	        Intent tv = new Intent(this,TVSearchActivity.class);
	        startActivity(tv);
	        return true;
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
	
	/*
	 * Creates a bundle object that will contain all currently available tasks.
	 * Allows for task data to be transferred to another task.
	 */
	private Bundle storeTasksForActivityChange()
	{
		Bundle dataBundle = new Bundle();
		String dataToSave = "";
		//Takes every task in folder and places it in the bundle
		//At this point in time only examines a single folder. Will need to
		//Examine ALL folders at a future point.
		for (int x = 0; x < folder.TaskList.size(); x++)
		{
			Log.v("CalTest","Real month = " +  String.valueOf(folder.getTask(x).getDueDate().get(Calendar.MONTH)));
			dataToSave = folder.getTask(x).getName() + "/" + folder.getTask(x).getDesc()
					+ "/" + String.valueOf(folder.getTask(x).getDueDate()
							.get(Calendar.MONTH)) + 
							String.valueOf(folder.getTask(x).getDueDate()
									.get(Calendar.DAY_OF_MONTH)) +
							String.valueOf(folder.getTask(x).getDueDate()
									.get(Calendar.YEAR))
					+ "/" + folder.getTask(x).isCompleted() +
					"/" + String.valueOf(folder.getTask(x).getReminder()
							.get(Calendar.YEAR)) + 
							String.valueOf(folder.getTask(x).getReminder()
									.get(Calendar.MONTH)) +
							String.valueOf(folder.getTask(x).getReminder()
									.get(Calendar.DAY_OF_MONTH));
			dataBundle.putString("TASK" + x, dataToSave);

		}
		dataBundle.putInt("COUNT", folder.TaskList.size());
		
		Log.v("CalTesting", "Leaving Bundler");
		
		return dataBundle;
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

}
