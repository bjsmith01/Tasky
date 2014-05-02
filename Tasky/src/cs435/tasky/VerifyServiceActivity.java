package cs435.tasky;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.preference.PreferenceManager;

public class VerifyServiceActivity extends Activity {

	EditText edit;
	Thread t;
	ArrayList<String> serviceList=new ArrayList<String>();
	ArrayList<String> serviceIDList=new ArrayList<String>();
	Context con=this;
	
	ListViewAdapter myAdapter;
	ListView lv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify_service);

		
		edit=(EditText) findViewById(R.id.serviceSearch);
		edit.setFocusableInTouchMode(true);
		edit.requestFocus();
		
		myAdapter=new ListViewAdapter(this,serviceList);
		lv= (ListView) findViewById(R.id.serviceResults);
		lv.setAdapter(myAdapter);
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// when selected set as service
				//LinearLayout par = (LinearLayout) view;
				//String result= ((TextView) par.findViewById(R.id.lblListHeader)).getText().toString();
				//settings.putString("service", result);
				String result= serviceIDList.get(position);
				
				SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(con);
				SharedPreferences.Editor editor= settings.edit();
				editor.putString("service", result);
				editor.commit();
				
				
				Log.i("Service Applied","Starting TVSearch");
		        Intent todo= new Intent(con,TVSearchActivity.class);
		        startActivity(todo);
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
            				findService(txt.trim());
            			}
                	});
            		t.start();
            		try {
						t.join();
						myAdapter.changeAdapter(serviceList);
	        	        lv.setAdapter(myAdapter);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		
                    return true;
                }
                return false;
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.verify, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_verify,
					container, false);
			return rootView;
		}
	}
	
public void findService(String zipcode){
		
		String apikey = "ftec5ntzep3uat3z3qqjy6z5st";
		
		try{

			serviceList.clear();
			InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
			
			
			URL url= new URL("http://api.rovicorp.com/TVlistings/v9/listings/services/postalcode/"+zipcode+"/info?locale=en-US&countrycode=US&format=xml&apikey=khf7cvbzxg52um6reynek5st");
			URLConnection conn= url.openConnection();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc= db.parse(conn.getInputStream());
			
			
			NodeList nodes= doc.getElementsByTagName("Service");
			for (int i=0; i<nodes.getLength(); i++){
				
				Element el = (Element) nodes.item(i);
				NodeList nameList= el.getElementsByTagName("Name");
				Element name=(Element) nameList.item(0);
				NodeList IDList= el.getElementsByTagName("ServiceId");
				Element ID= (Element) IDList.item(0);
				serviceIDList.add(ID.getTextContent().toString());
				System.out.println(name.getTextContent());
				serviceList.add(name.getTextContent().toString());
			}

			if (nodes.getLength()==0){
				Log.v("none","Sorry, I Can't Find That Zip Code");
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
