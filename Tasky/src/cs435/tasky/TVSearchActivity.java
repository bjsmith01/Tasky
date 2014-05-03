package cs435.tasky;

import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class TVSearchActivity extends Activity {

	EditText edit;
	Thread t;
	//ArrayList<String[]> showList=new ArrayList<String[]>();
	String serviceId;
	Task tvShowTask;
	GlobalTaskList tasklist;
	//private Vibrator vibe;
	 
	//MyAdapter myAdapter;
	//ExpandableListView exv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tvsearch);
		tasklist=(GlobalTaskList) getApplication();
		edit=(EditText) findViewById(R.id.tvSearch);
		edit.setFocusableInTouchMode(true);
		edit.requestFocus();
		
		edit.setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
				{
					t= new Thread(new Runnable(){

            			@Override
            			public void run() {
            				// TODO Auto-generated method stub
            				String txt=edit.getText().toString();
            				txt=txt.replaceAll( " ", "+");
            				findTvShow(txt);
            			}
                	});
            		t.start();
            		try {
						t.join();
						Intent i = new Intent(getBaseContext(), AddTaskActivity.class);
						i.putExtra("NAME", tvShowTask.getName());
						i.putExtra("DYEAR", tvShowTask.getDueDate().get(GregorianCalendar.YEAR));
						i.putExtra("DMONTH", tvShowTask.getDueDate().get(GregorianCalendar.MONTH));
						i.putExtra("DDAY", tvShowTask.getDueDate().get(GregorianCalendar.DAY_OF_MONTH));
						i.putExtra("YEAR", getIntent().getIntExtra("YEAR", 0));
						i.putExtra("MONTH", getIntent().getIntExtra("MONTH", 1));
						i.putExtra("DAY", getIntent().getIntExtra("DAY", 1));
						
						i.putExtra("RETURN", Constants.TASKVIEW);
						if (tasklist.taskList==null){
							i.putExtra("ID", 0);
						}
						else{
							i.putExtra("ID", 0);
						}
						startActivity(i);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		
                    return true;
				}
				return false;
			}
			
		});
		
		edit.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                    KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                	
                	t= new Thread(new Runnable(){

            			@Override
            			public void run() {
            				// TODO Auto-generated method stub
            				String txt=edit.getText().toString();
            				txt=txt.replaceAll( " ", "+");
            				findTvShow(txt);
            			}
                	});
            		t.start();
            		try {
						t.join();
						Intent i = new Intent(getBaseContext(), AddTaskActivity.class);
						i.putExtra("NAME", tvShowTask.getName());
						i.putExtra("DYEAR", tvShowTask.getDueDate().get(GregorianCalendar.YEAR));
						i.putExtra("DMONTH", tvShowTask.getDueDate().get(GregorianCalendar.MONTH));
						i.putExtra("DDAY", tvShowTask.getDueDate().get(GregorianCalendar.DAY_OF_MONTH));
						i.putExtra("YEAR", getIntent().getIntExtra("YEAR", 0));
						i.putExtra("MONTH", getIntent().getIntExtra("MONTH", 1));
						i.putExtra("DAY", getIntent().getIntExtra("DAY", 1));
						
						i.putExtra("RETURN", Constants.TASKVIEW);
						if (tasklist.taskList==null){
							i.putExtra("ID", 0);
						}
						else{
							i.putExtra("ID", 0);
						}
						startActivity(i);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		
                    return true;
                }
                return false;
            }
        });
		checkForService();
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
	        Intent cal= new Intent(this,CalendarActivity.class);
	        startActivity(cal);
	        return true;
	    case R.id.todo_list:
	        Log.i("In Menu","ToDo List Selected");
	        Intent todo= new Intent(this,ToDoListActivity.class);
	        startActivity(todo);
	        return true;
	    case R.id.action_settings:
	        Log.i("In Menu","Change Zip Selected");
	        Intent vsa= new Intent(this,VerifyServiceActivity.class);
	        startActivity(vsa);
	        return true;
	        
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	

	/**
	 * search for the tv show on theTVDB
	 */
	
	public void findTvShow(String tvShow){
		
		String apikey = "ftec5ntzep3uat3z3qqjy6z5";
		String secret = "sBFzQ8YKNE";
		long unixTime= System.currentTimeMillis() / 1000L;
		
		
		try{
			//get custom key needed for query
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update((apikey+secret+unixTime).getBytes());
			
			byte byteData[] = md.digest();
			 
		    //convert the byte to hex format method 1
		    StringBuffer sb = new StringBuffer();
		    for (int i = 0; i < byteData.length; i++) {
		     sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		    } 
		    // end custom key
			
			//showList.clear();
			//hide keyboard after input
			InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
			
			//create url
			URL url= new URL("http://api.rovicorp.com/data/v1.1/video/info?video="+tvShow+"&serviceid="+serviceId+"&inprogress=false&duration=20160&include=Synopsis%2CSchedule&country=US&language=en&format=xml&apikey=ftec5ntzep3uat3z3qqjy6z5&sig="+sb.toString());
			URLConnection conn= url.openConnection();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc= db.parse(conn.getInputStream());
			
			String Name="";
			String Desc="Sorry This Show Doesn't Come On Anytime Soon...";
			String datestr="1 1 2014";
			GregorianCalendar date=new GregorianCalendar(2015,12,12);
			
			//get results and create Task
			NodeList nodes= doc.getElementsByTagName("video");
			for (int i=0; i<nodes.getLength(); i++){
				
				Element el = (Element) nodes.item(i);
				NodeList nameList= el.getElementsByTagName("masterTitle");
				Element name=(Element) nameList.item(0);
				NodeList descList= el.getElementsByTagName("desc");
				Element desc=(Element) descList.item(0);
				NodeList dateList=el.getElementsByTagName("time");
				Element dates=(Element) dateList.item(0);
				if (dates!=null){
					datestr=dates.getTextContent();
					datestr=datestr.split("T")[0];
					date=new GregorianCalendar(Integer.parseInt(datestr.split("-")[0]),Integer.parseInt(datestr.split("-")[1]),Integer.parseInt(datestr.split("-")[2]));
				}
				//System.out.println(name.getTextContent());
				//System.out.println(desc.getTextContent().trim().split("\n")[0]);
				
				if (desc!=null){
					Desc=desc.getTextContent().trim().split("\n")[0];
					Log.d("desc",desc.getTextContent());
				}
				
				Name =name.getTextContent();
				if (date!=null){
					tvShowTask=new Task(Name,date);
				}
				
			}
		
		

			if (nodes.getLength()==0){
				Log.v("none","Sorry, I Can't Find That Show");
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void checkForService(){
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		serviceId= "365890";//prefs.getString("service", "");
		/*
		if (serviceId==""){
			Log.i("Service Missing","Starting Verify");
	        Intent todo= new Intent(this,VerifyServiceActivity.class);
	        startActivity(todo);
	        */
	        
		}
	}
	
