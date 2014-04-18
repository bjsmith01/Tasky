/**
 * 
 */
package cs435.tasky;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * @author Brandon
 *
 */
public class MyAdapter extends BaseExpandableListAdapter {

	private Context context;
	private Folder folder;
	ArrayList<String> parentList= new ArrayList<String>();
	ArrayList<String[]> childList= new ArrayList<String[]>();
	
	public MyAdapter(Context con, Folder fold) {
		// TODO Auto-generated constructor stub
		context=con;
		folder=fold;
		
		String[] temp={""};
		for (int i=0; i<fold.TaskList.size();i++){
			parentList.add(fold.getTask(i).getName());
			temp[0]=fold.getTask(i).getDesc();
			childList.add(temp);
		}
	}

	public MyAdapter(Context con, ArrayList<String[]> showList) {
		// TODO Auto-generated constructor stub
		context=con;
		
		String[] show=new String[2];
		String[] temp={""};
		for (int i=0; i< showList.size();i++){
			show=showList.get(i);
			parentList.add(show[0]);
			temp[0]=show[1];
			childList.add(temp);
			
			
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChild(int, int)
	 */
	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(context);
		tv.setText(childList.get(groupPosition)[childPosition]);
		
		return tv;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childList.get(groupPosition).length;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroup(int)
	 */
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return childList.get(groupPosition);
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupCount()
	 */
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return parentList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupId(int)
	 */
	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(context);
		tv.setText(parentList.get(groupPosition));
		
		return tv;
	}
	
	public void changeAdapter(Folder fold){
		
		parentList.clear();
		childList.clear();
		
		String[] temp={""};
		for (int i=0; i<fold.TaskList.size();i++){
			parentList.add(fold.getTask(i).getName());
			temp[0]=fold.getTask(i).getDesc();
			childList.add(temp);
		}
		
	}
	
	public void changeAdapter(ArrayList<String[]> showList){
		
		parentList.clear();
		childList.clear();
		
		String[] show=new String[2];
		
		for (int i=0; i< showList.size();i++){
			String[] temp=new String[1];
			show=showList.get(i);
			parentList.add(show[0]);
			temp[0]=show[1];
			if (temp[0]==""){
				temp[0]="No description available";
			}
			childList.add(temp);
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
