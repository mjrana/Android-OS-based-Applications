package ltu.org;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import ltu.org.FeedParser;
import ltu.org.FeedParserFactory;
import ltu.org.Group;
import ltu.org.CustomListAdapter;
import ltu.org.PersonView;
import ltu.org.Person;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.provider.BaseColumns;
import android.provider.CallLog;
import android.provider.CallLog.Calls;

//public class MyCustomListAdapter extends ListActivity implements OnClickListener{
public class GroupConstructor extends ListActivity{

	private final String DEBUG_TAG = "Groupper";
	public static String TransUname = "test";
	private List<Group> groups;
	private List<Meeting> meetings;
	
	
	String context;
	
	
	
	private ListView lv;				//this is the ref to the listView for this activity
	private Button btnRemove;			//this is the ref to the remove button
	private ArrayList<Person> myData;	//this is our list of Persons - data
	CustomListAdapter myAdapter;		//we will use this "custom adapter" to bind this data to the listView
	private Bitmap thumb;
	private ArrayList selections;		//list of selections made by the user
	private AutoCompleteTextView txtUserkeywords;
	private  final String MY_UNAME = "myname";
	private final String MY_PASS = "wallpaper";
	private String prefUName;
	/*
	 * this routine simply fills the myData array with dummy values
	 */
	private void populateList(String[] meetingTitle,String[] meetingOwner, String[] meetingID){
		//this is how you create a bitmap from an existing drawable resource
		thumb = BitmapFactory.decodeResource(getResources(),R.drawable.defaulticon );
		
		
		//this loop simply creates and adds the objects into myData
		if(meetingTitle[0].toString().length()!=0&&meetingTitle.length!=0)
		for(int i=0;i<meetingTitle.length;i++)
		{
			
				Person p = new Person(meetingTitle[i]+" "+meetingOwner[i],meetingID[i],thumb);
			
			myData.add(p);
			Log.d("PERSON:",p.toString());
		}
		
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.expose);
		
		// Functionalities of OK button
		txtUserkeywords = (AutoCompleteTextView)findViewById(R.id.txtUserNameEntry);
		context=txtUserkeywords.getText().toString();
		
		Button cmd_submit = (Button) findViewById(R.id.btnFind);
		
		
		SharedPreferences myPrefs1 = getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        int counter = myPrefs1.getInt("counter", 1);
        
        if (counter!=0)
        {
        	
        	Intent i = new Intent(GroupConstructor.this, SigninActivity.class);// opening signin intent as first operation.    	    	
            startActivity(i);
        	
        }
        else
        {
        	
        	SharedPreferences myPrefs2 = getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
            prefUName = myPrefs2.getString(MY_UNAME, "nothing");
            String prefUPass = myPrefs2.getString(MY_PASS, null);
            FeedParserFactory.TransUname = myPrefs2.getString(MY_UNAME, "nothing");
        }
		
		
		
		cmd_submit.setOnClickListener( new OnClickListener(){
	        @Override
			public void onClick(View v ){
	        	
	        	callGroupMaker(txtUserkeywords.getText().toString(),prefUName);
	            //finish();
	        }	        
	    });
		
		
		
		   myData = new ArrayList<Person>();					//create a new array that stores Person objects
	       lv = this.getListView();								//get the list view for this ListActivity
	       btnRemove = (Button) findViewById(R.id.btnRemove);	//get the button view
	       selections = new ArrayList();						//initialize the selections array
	       
	       populateList(meetingTitle,meetingOwner,meetingID);										//fill myData with dummy values for now
	       
	       //create a new custom list adapter which will work on myData as its resource
	       myAdapter = new CustomListAdapter(getApplicationContext(),myData);
	       
	       this.setListAdapter(myAdapter);						//bind this adapter to our ListActivity
	       
	       btnRemove.setOnClickListener( new OnClickListener(){
		        @Override
				public void onClick(View v ){
		        	
		        	//callScreen("From create button");
		            //finish();
		        	callGroupMaker_automated(txtUserkeywords.getText().toString(),prefUName);
		        }	        
		   });
	       
	    }
	
	
	int i=1000;
	
	String[] meetingTitle = discoverGroup(i);
    String[] meetingOwner = discoverGroup(500);
    String[] meetingID = discoverGroup(100);
    
	@Override
	public void onListItemClick(ListView parent, View v,int position, long id){
		
		Person p = (Person) myAdapter.getItem(position);	//grab the object that refers to the clicked item
		PersonView pView = (PersonView)v;					//grab the view (row) that refers to the item
		
		pView.check(!(pView.isChecked()));					//toggle the brightness of its icon and checked flag
		
		if(pView.isChecked())								//if the action caused selection
			selections.add(position);						//add this item's position to the selection array
		else
		{													//if the action caused de-selection
			int index = selections.indexOf(position);		//grab the index of the un-selected item
			selections.remove(index);						//remove this index from selections array, as its not selected anymore
		}
		Toast toast=Toast.makeText(getApplicationContext(), Integer.toString(position), 1000);  
        toast.show();
        
		callScreen(p.age);
		
		// so basically now the selections array contains only indices of items that were selected by user
		//goWebView();
		Log.d("Selections:",selections.toString());			//debug statement
	}
    	
	
	protected void callScreen(String meetingID) {
		Toast toast=Toast.makeText(getApplicationContext(), meetingID, 1000);  
        toast.show();
        Bundle bundle = new Bundle();
		bundle.putString("param1", meetingID);
        Intent i = new Intent(GroupConstructor.this, WebViewExample.class);
        i.putExtras(bundle);
        startActivity(i);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//menu.add(Menu.NONE, ParserType.ANDROID_SAX.ordinal(),ParserType.ANDROID_SAX.ordinal(), R.string.signin);
		menu.add(R.string.signin);		
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	Intent i = new Intent(GroupConstructor.this, SigninActivity.class);    	    	
        startActivity(i);
		
        return true;
	}
	
	
	// This functions is created for disovering contacts 
	//@SuppressWarnings("null")
	protected void callGroupMaker(String keywords, String username) {
		Bundle bundle = new Bundle();
		bundle.putString("param1", keywords);
		 
		StringBuilder lvItemsB = new StringBuilder(); // Creates string array for the listview of the query people
		
		//Keyword search
		
		ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
        	
        	while (cur.moveToNext()) {
        		String id = cur.getString(cur.getColumnIndex(BaseColumns._ID)); // conatct id
        		
        		String name = cur.getString( cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)); // contact name
        		
        		String number="Unknown";
        		if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                     	Cursor pCur = cr.query(
                     			ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
                     			null, 
                     			ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
                     			new String[]{id}, null);
                     	while (pCur.moveToNext()) {
                     	number= pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA)); // contact phone number
                     	} 
                     	pCur.close();
      	        }
        		
        		// this part for checking keywords in the contacts note....
        		String note="this";
        		String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
                String[] noteWhereParams = new String[]{id, 
         		ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE}; 
                        Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null); 
         	    if (noteCur.moveToFirst()) { 
         	        note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));         	        
         	    }
         	    
         	    noteCur.close();
        		
        		//if keyword matches with the note it added the contact in the invitation list
         	    if(keywords.compareTo(note)==0)
        			lvItemsB.append(name+" "+number+"\n");
        	}
        }
        
        //location search based group
        
        LocationManager locationManager;
        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(context);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
    	Location location = locationManager.getLastKnownLocation(provider);
        updateWithNewLocation(location);
        String latLongString=" ";
        double lat = 0.0;
        double lng = 0.0;
        
        locationManager.requestLocationUpdates(provider, 2000, 10,locationListener);
        
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            latLongString = "Lat:" + lat + "\nLong:" + lng;
            
            //String lv_loc[]= discoverNearbyconatcts(latLongString);
            String lv_loc[]= discoverNearbyconatcts(Double.toString(lat),Double.toString(lng));
            for(int i=0; i<lv_loc.length;i++)
            	lvItemsB.append(lv_loc[i]+"\n");
            
          } else {
            latLongString = "No location found"; 
          }
        
        
        Toast toast=Toast.makeText(getApplicationContext(),latLongString, 1000);  
        toast.show();
        
        try 
		{
			
			//String queryString = "http://46.162.92.139:8080/axis2/services/Dynamicgroup/AddMeeting?username="+username+"&&meetingName="+meetingName+"&&groupKey="+meetingIdS;
        	
        	// office network
        	//String queryString = "http://172.16.0.24:8080/axis2/services/Dynamicgroup/addCurrentLocation?username="+username+"&&plat="+Double.toString(lat)+"&&plon="+Double.toString(lng);
			
        	// home network
        	//String queryString = "http://192.168.0.101:8080/axis2/services/Dynamicgroup/addCurrentLocation?username="+username+"&&plat="+Double.toString(lat)+"&&plon="+Double.toString(lng);
			
        	// server network l-sls0483d.research.ltu.se:8080
        	String queryString = "http://l-sls0483d.research.ltu.se:8080/axis2/services/Dynamicgroup/addCurrentLocation?username="+username+"&&plat="+Double.toString(lat)+"&&plon="+Double.toString(lng);
			
			
			/* Replace blanks with HTML-Equivalent. */		
			URL url = new URL(queryString.replace(" ", "%20"));
			url.getContent();
			
			
		}catch (IOException ie) {
			ie.printStackTrace();
		}
        //locationManager.requestLocationUpdates(provider, 2000, 10,locationListener);
        

		        
        //call log search for finding most important callers
        //get mostly interactive people
        String[] projection = new String[] {
        		Calls.DATE
        		, Calls.NUMBER
        		, Calls.DURATION
        		, Calls.CACHED_NAME
        };
        Cursor mCur = managedQuery(CallLog.Calls.CONTENT_URI,
         		projection, Calls.DURATION +">?", 
                        new String[] {"60"},
                        Calls.DURATION + " ASC");
        mCur.moveToFirst();

        /*Cursor mCur = managedQuery(CallLog.Calls.CONTENT_URI,
         		projection, Calls.CACHED_NAME +"==?", 
                        new String[] {"Urmi Mirza"},
                        Calls.DURATION + " ASC");
        mCur.moveToFirst();*/
        
        while (mCur.isAfterLast() == false) {
        	  
        	  /*for (int i=0; i<mCur.getColumnCount(); i++) {
                  lvItemsB.append(mCur.getString(i)+" ");
              }*/
        	  lvItemsB.append(mCur.getString(3)+"\n"); 
              //lvItemsB.append("\n");
      	      mCur.moveToNext();
        }
        
        // adding the owner of the of the meeting....
        lvItemsB.append(username+"\n");
        // generating string array from the string builder for the listview
        String[] lv_items=lvItemsB.toString().split ("\n");
        
        // Duplication check
        
        Arrays.sort(lv_items);

        int k = 0;

        for (int i = 0; i < lv_items.length; i++)
        {
        if (i > 0 && lv_items[i].equals(lv_items[i -1]))
        continue;

        lv_items[k++] = lv_items[i];
        }

        String[] unique = new String[k];

        System.arraycopy(lv_items, 0, unique, 0, k);

        
        
        // sending the recommended list to the group creation space
		bundle.putStringArray("param2", unique);
		bundle.putString("param3", username);
		Intent i = new Intent(GroupConstructor.this, CSpaceCreator.class);
        i.putExtras(bundle);        
        startActivity(i);
    }
	
	
	
	
	// This functions is created for disovering contacts 
	//@SuppressWarnings("null")
	
	protected void callGroupMaker_automated(String keywords, String username) {
		
		 
		StringBuilder lvItemsB = new StringBuilder(); // Creates string array for the listview of the query people
		
		//Keyword search
		
		ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
        	
        	while (cur.moveToNext()) {
        		String id = cur.getString(cur.getColumnIndex(BaseColumns._ID)); // conatct id
        		
        		String name = cur.getString( cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)); // contact name
        		
        		String number="Unknown";
        		if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                     	Cursor pCur = cr.query(
                     			ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
                     			null, 
                     			ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
                     			new String[]{id}, null);
                     	while (pCur.moveToNext()) {
                     	number= pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA)); // contact phone number
                     	} 
                     	pCur.close();
      	        }
        		
        		// this part for checking keywords in the contacts note....
        		String note="this";
        		String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
                String[] noteWhereParams = new String[]{id, 
         		ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE}; 
                        Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null); 
         	    if (noteCur.moveToFirst()) { 
         	        note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));         	        
         	    }
         	    
         	    noteCur.close();
        		
        		//if keyword matches with the note it added the contact in the invitation list
         	    if(keywords.compareTo(note)==0)
        			lvItemsB.append(name+" "+number+"\n");
        	}
        }
        
        //location search based group
        
        LocationManager locationManager;
        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(context);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
    	Location location = locationManager.getLastKnownLocation(provider);
        updateWithNewLocation(location);
        String latLongString=" ";
        double lat = 0.0;
        double lng = 0.0;
        
        locationManager.requestLocationUpdates(provider, 2000, 10,locationListener);
        
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            latLongString = "Lat:" + lat + "\nLong:" + lng;
            
            //String lv_loc[]= discoverNearbyconatcts(latLongString);
            String lv_loc[]= discoverNearbyconatcts(Double.toString(lat),Double.toString(lng));
            for(int i=0; i<lv_loc.length;i++)
            	lvItemsB.append(lv_loc[i]+"\n");
            
          } else {
            latLongString = "No location found"; 
          }
        
        
        Toast toast=Toast.makeText(getApplicationContext(),latLongString, 1000);  
        toast.show();
        
        try 
		{
			
			//String queryString = "http://46.162.92.139:8080/axis2/services/Dynamicgroup/AddMeeting?username="+username+"&&meetingName="+meetingName+"&&groupKey="+meetingIdS;
        	
        	// office network
        	//String queryString = "http://172.16.0.24:8080/axis2/services/Dynamicgroup/addCurrentLocation?username="+username+"&&plat="+Double.toString(lat)+"&&plon="+Double.toString(lng);
			
        	// home network
        	//String queryString = "http://192.168.0.101:8080/axis2/services/Dynamicgroup/addCurrentLocation?username="+username+"&&plat="+Double.toString(lat)+"&&plon="+Double.toString(lng);
			
        	// server network l-sls0483d.research.ltu.se:8080
        	String queryString = "http://l-sls0483d.research.ltu.se:8080/axis2/services/Dynamicgroup/addCurrentLocation?username="+username+"&&plat="+Double.toString(lat)+"&&plon="+Double.toString(lng);
			
			
			/* Replace blanks with HTML-Equivalent. */		
			URL url = new URL(queryString.replace(" ", "%20"));
			url.getContent();
			
			
		}catch (IOException ie) {
			ie.printStackTrace();
		}
        //locationManager.requestLocationUpdates(provider, 2000, 10,locationListener);
        

		        
        //call log search for finding most important callers
        //get mostly interactive people
        String[] projection = new String[] {
        		Calls.DATE
        		, Calls.NUMBER
        		, Calls.DURATION
        		, Calls.CACHED_NAME
        };
        Cursor mCur = managedQuery(CallLog.Calls.CONTENT_URI,
         		projection, Calls.DURATION +">?", 
                        new String[] {"60"},
                        Calls.DURATION + " ASC");
        mCur.moveToFirst();

        /*Cursor mCur = managedQuery(CallLog.Calls.CONTENT_URI,
         		projection, Calls.CACHED_NAME +"==?", 
                        new String[] {"Urmi Mirza"},
                        Calls.DURATION + " ASC");
        mCur.moveToFirst();*/
        
        while (mCur.isAfterLast() == false) {
        	  
        	  /*for (int i=0; i<mCur.getColumnCount(); i++) {
                  lvItemsB.append(mCur.getString(i)+" ");
              }*/
        	  lvItemsB.append(mCur.getString(3)+"\n"); 
              //lvItemsB.append("\n");
      	      mCur.moveToNext();
        }
        
        // adding the owner of the of the meeting....
        lvItemsB.append(username+"\n");
        // generating string array from the string builder for the listview
        String[] lv_items=lvItemsB.toString().split ("\n");
        
        // Duplication check
        
        Arrays.sort(lv_items);

        int k = 0;

        for (int i = 0; i < lv_items.length; i++)
        {
        if (i > 0 && lv_items[i].equals(lv_items[i -1]) && lv_items[i].length()>3)
        continue;

        lv_items[k++] = lv_items[i];
        }

        String[] unique = new String[k];

        System.arraycopy(lv_items, 0, unique, 0, k);

        StringBuilder  userListString = new StringBuilder();
        
        if(k>0)
        {
        	userListString.append(unique[0]+"-"+username);
        }
        for(int counter=1;counter<k;counter++)
        {
        	if(unique[counter].length()<3)
        	userListString.append("-"+unique[counter]);
        }
                
        UUID meetingId = UUID.randomUUID();		
		String meetingIdS = meetingId.toString();
		
		try 
		{
			
			//String queryString = "http://46.162.92.139:8080/axis2/services/Dynamicgroup/AddMeeting?username="+username+"&&meetingName="+meetingName+"&&groupKey="+meetingIdS;
			//office network
			//String queryString = "http://172.16.0.24:8080/axis2/services/Dynamicgroup/AddMeeting?userlist="+userlist+"&&owner="+owner+"&&meetingName="+meetingName+"&&groupKey="+meetingIdS;
			
			//home network
			//String queryString = "http://192.168.0.101:8080/axis2/services/Dynamicgroup/AddMeeting?userlist="+userlist+"&&owner="+owner+"&&meetingName="+meetingName+"&&groupKey="+meetingIdS;
			
			// server computer l-sls0483d.research.ltu.se
			String queryString1 = "http://l-sls0483d.research.ltu.se:8080/axis2/services/Dynamicgroup/AddMeeting?userlist="+userListString.toString()+"&&owner="+username+"&&meetingName="+keywords+"&&groupKey="+meetingIdS;
			
			//String queryString1 = "http://l-sls0483d.research.ltu.se:8080/axis2/services/Dynamicgroup/AddMeeting?userlist="+"shabuj-ema"+"&&owner="+"tester"+"&&meetingName="+keywords+"&&groupKey="+meetingIdS;
			
			
			/* Replace blanks with HTML-Equivalent. */		
			URL url1 = new URL(queryString1.replace(" ", "%20"));
			url1.getContent();
			
			
		}catch (IOException ie) {
			ie.printStackTrace();
		}
		
		
        Bundle bundle_a = new Bundle();
		bundle_a.putString("param1", meetingIdS);
		Intent i = new Intent(GroupConstructor.this, WebViewExample.class);    	    	
        i.putExtras(bundle_a);
        startActivity(i);
        
    }
        
	
	//get user location	
	private final LocationListener locationListener = new LocationListener() {
	    @Override
		public void onLocationChanged(Location location) {
	      updateWithNewLocation(location);
	    }
		 
	    @Override
		public void onProviderDisabled(String provider){
	      updateWithNewLocation(null);
	    }

	    @Override
		public void onProviderEnabled(String provider){ }
	    @Override
		public void onStatusChanged(String provider, int status, Bundle extras){ }
	  };

	  private void updateWithNewLocation(Location location) {
	    
	  }
	
	
	// Functions to get nearby contacts
	private String[] discoverNearbyconatcts(String plat, String plon){
		
		String[] FriendsLoc = {"Juwel LuleŒ","Josef LuleŒ","KŒre Amsterdam","Johan LuleŒ"};
		StringBuilder content = new StringBuilder();
		try
        {
          // create a url object
        	
			// office network
        	//String queryString = "http://172.16.0.24:8080/axis2/services/Dynamicgroup/LocDistance?plat="+plat+"&&plon="+plon;
			
        	// home network
        	//String queryString = "http://192.168.0.101:8080/axis2/services/Dynamicgroup/LocDistance?plat="+plat+"&&plon="+plon;
			
        	// server computer
        	String queryString = "http://l-sls0483d.research.ltu.se:8080/axis2/services/Dynamicgroup/LocDistance?plat="+plat+"&&plon="+plon;
			
        	
        	URL url = new URL(queryString.replace(" ", "%20"));

          // create a urlconnection object
          URLConnection urlConnection = url.openConnection();

          // wrap the urlconnection in a bufferedreader
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

          String line;

          // read from the urlconnection via the bufferedreader
          while ((line = bufferedReader.readLine()) != null)
          {
        	  if (line.contains("<ns:")==true||line.contains("</ns:")==true)
        		  ;
        	  else
        		  content.append(line + ",");
          }
          bufferedReader.close();
          Toast toast1=Toast.makeText(getApplicationContext(),content.toString(), 1000);  
	        toast1.show();
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        
        String[] same_loc=content.toString().split (",");
		return same_loc;
	}
	
	
    // Group data access
	private String[] discoverGroup(int item) {
		
		List<String> meetingTitles = null;
		List<String> meetingOwners = null;
		List<String> meetingId = null;
		
		
	   	StringBuilder sbTitles = new StringBuilder();
	   	StringBuilder sbOwner = new StringBuilder();
	   	StringBuilder sbID = new StringBuilder();
	   	
	   	
	   	String str = null;
	   	
	   	try{
			//Log.i("AndroidNews", "ParserType="+type.name());
	    	
	   		FeedParser parser;
	   		if(prefUName!=null)
	   			parser = FeedParserFactory.getParser(ParserType.SAX, prefUName);
	   		else
	   			parser = FeedParserFactory.getParser(ParserType.SAX, "jubel");
	    	
	   		long start = System.currentTimeMillis();
	    	meetings = parser.parse();
	    	
	    	long duration = System.currentTimeMillis() - start;
	    	Log.i("AndroidNews", "Parser duration=" + duration);
	    	
	    	meetingTitles = new ArrayList<String>(meetings.size());
	    	meetingOwners = new ArrayList<String>(meetings.size());
	    	meetingId = new ArrayList<String>(meetings.size());
	    	
	    	
	    	int counter=0;
	    	
	    	for (Meeting mtg : meetings){
	    		meetingTitles.add(mtg.getTitle());
	    		sbTitles.append(mtg.getTitle()+"\n");
	    		
	    		meetingOwners.add(mtg.getOwner());
	    		sbOwner.append(mtg.getOwner()+"\n");
	    		
	    		meetingId.add(mtg.getMeetingID());
	    		sbID.append(mtg.getMeetingID()+"\n");
	    		
	    		if(counter==item)
	    			str=mtg.getOwner() + " ";
	    		counter++;
	    		
	    	}
	    	
	    } catch (Throwable t){
		Log.e("AndroidNews",t.getMessage(),t);
		}
		
			// TODO Auto-generated method stub
			//return  titles;
				
		String[] separatedTitles = sbTitles.toString().split ("\n");
		String[] separatedOwner = sbOwner.toString().split ("\n");
		String[] separatedId = sbID.toString().split ("\n");
		
		if(item==1000)
			return separatedTitles;
		else if(item==500)
			return separatedOwner;
		else if(item==100)
			return separatedId;
		else
			return separatedId;
		
		}

}