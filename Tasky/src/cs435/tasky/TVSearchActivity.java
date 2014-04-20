package cs435.tasky;


import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


public class TVSearchActivity extends Activity {


	EditText edit;
	Thread t;
	ArrayList<String[]> showList=new ArrayList<String[]>();




	MyAdapter myAdapter;
	ExpandableListView exv;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tvsearch);
		edit=(EditText) findViewById(R.id.tvSearch);
		edit.setFocusableInTouchMode(true);
		edit.requestFocus();


		myAdapter=new MyAdapter(this,showList);


		exv= (ExpandableListView) findViewById(R.id.tvResults);
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
            				txt=txt.replaceAll( " ", "%20");
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


	/**
	 * search for the tv show on theTVDB
	 */


	public void findTvShow(String tvShow){




		showList.clear();
		try {
			URL url= new URL("http://thetvdb.com/api/GetSeries.php?seriesname="+tvShow+"/");
			URLConnection conn= url.openConnection();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc= db.parse(conn.getInputStream());


			NodeList nodes= doc.getElementsByTagName("Series");
			for (int i=0; i<nodes.getLength(); i++){
				String[] showInfo=new String[2];
				Element el = (Element) nodes.item(i);
				NodeList nameList= el.getElementsByTagName("SeriesName");
				Element name=(Element) nameList.item(0);
				NodeList descList= el.getElementsByTagName("Overview");
				Element desc=(Element) descList.item(0);
				Log.d("name",name.getTextContent());
				if (desc!=null){
					showInfo[1]=desc.getTextContent();
					Log.d("desc",desc.getTextContent());
				}
				else{
					showInfo[1]="";
					Log.d("desc","");
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


}
