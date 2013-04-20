package ltu.org;

import java.util.ArrayList;



import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.*;


/*
 *	This class defines the list adapter being used for our ListView
 *	This uses the PersonView class in the getView method. Also contains a list
 *  of items that will store the actual object records
 *  
 *   Extends the BaseAdapter class
 */

public class CustomListAdapter extends BaseAdapter{
		
	public Context cxt;
	public ArrayList<Person> items;
	
	public CustomListAdapter(Context context,ArrayList<Person> list){

		this.cxt = context;
		items = list;
	}
	
	@Override
	public int getCount(){
		return items.size();
	}
	
	@Override
	public Object getItem(int position){
		return items.get(position);
	}
	
	@Override
	public long getItemId(int position){
		return position;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 * This is the most important method. Called in order to get a "View" for the Person object
	 * this is where the PersonView class is used to create a visual representation of the 
	 * object.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 Person p = items.get(position);
		 return new PersonView(cxt,p);
	 }
	 
	/*
	 * this routine helps remove an item from the list
	 */
	 public void remove(int position){
		 if((position < items.size()) && (position >= 0))
			 	items.remove(position);
	 }
	 
}
