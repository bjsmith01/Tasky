package cs435.tasky;

import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class TVSearchActivity extends Activity {

	EditText edit;
	Thread t;
	ArrayList<String[]> showList=new ArrayList<String[]>();
	String serviceId;
	
	//private Vibrator vibe;
	 
	MyAdapter myAdapter;
	ExpandableListView exv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tvsearch);

		edit=(EditText) findViewById(R.id.tvSearch);
		edit.setFocusableInTouchMode(true);
		edit.requestFocus();
		
		//vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
		
		myAdapter=new MyAdapter(this,showList);
		
		exv= (ExpandableListView) findViewById(R.id.tvResults);
		/*
		exv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				vibe.vibrate(50);
				TextView name= (TextView) view.findViewById(R.id.lblListHeader);
				String str= name.getText().toString();
				
				// *** create a bundle here of the name, description, date, and channel
				//     pass to edit Task
				
                Log.d("long click : ", str);
				
				return true;
			}
	       
	    });
	    */
		exv.setAdapter(myAdapter);
		
		/*
		edit.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
			            (keyCode == KeyEvent.KEYCODE_ENTER)) {
			          // Perform action on key press
			          findTvShow(edit.getText().toString());
			          return true;
			        }
			        return false;
			    }
		});
		*/
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
						myAdapter.changeAdapter(showList);
	        	        exv.setAdapter(myAdapter);
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
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.tvResults) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    menu.setHeaderTitle(showList.get(info.position)[0]);
	    String[] menuItems = getResources().getStringArray(R.array.TVNameArray);
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
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
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update((apikey+secret+unixTime).getBytes());
		
		byte byteData[] = md.digest();
		 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        } 
		
		showList.clear();
		InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
		
		//***change this to 
		URL url= new URL("http://api.rovicorp.com/data/v1.1/video/info?video="+tvShow+"&serviceid="+serviceId+"&inprogress=false&duration=20160&include=Synopsis%2CSchedule&country=US&language=en&format=xml&apikey=ftec5ntzep3uat3z3qqjy6z5&sig="+sb.toString());
		URLConnection conn= url.openConnection();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc= db.parse(conn.getInputStream());
		
		NodeList nodes= doc.getElementsByTagName("video");
		for (int i=0; i<nodes.getLength(); i++){
			String[] showInfo=new String[2];
			Element el = (Element) nodes.item(i);
			NodeList nameList= el.getElementsByTagName("masterTitle");
			Element name=(Element) nameList.item(0);
			NodeList descList= el.getElementsByTagName("synopsis");
			Element desc=(Element) descList.item(0);
			System.out.println(name.getTextContent());
			System.out.println(desc.getTextContent().trim().split("\n")[0]);
			
			if (desc!=null){
				showInfo[1]=desc.getTextContent().trim().split("\n")[0];
				Log.d("desc",desc.getTextContent());
			}
			
			showInfo[0]=name.getTextContent();
			showList.add(showInfo);
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
		serviceId= prefs.getString("service", "");
		
		if (serviceId==""){
			Log.i("Service Missing","Starting Verify");
	        Intent todo= new Intent(this,VerifyServiceActivity.class);
	        startActivity(todo);
	        
		}
	}
	
}
