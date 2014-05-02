/**
 * Adapter for Listview
 */
package cs435.tasky;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Brandon
 *
 */
public class ListViewAdapter extends BaseAdapter {

	private Context _context;
	ArrayList<String> parentList= new ArrayList<String>();
	
	public ListViewAdapter(Context con,
			ArrayList<String> serviceList) {
		// TODO Auto-generated constructor stub
		
		_context=con;
		for (int i=0;i<serviceList.size();i++){
			parentList.add(serviceList.get(i));
		}
		//parentList=serviceList;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return parentList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return parentList.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_parent, null);
        }
 
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(parentList.get(position));
 
        return convertView;
	}
	
	public void changeAdapter(ArrayList<String> showList){
			
			parentList.clear();
					
			String show;
			
			for (int i=0; i< showList.size();i++){
				show=showList.get(i);
				parentList.add(show);
			}
	}

}
