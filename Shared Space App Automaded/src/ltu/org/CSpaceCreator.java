package ltu.org;

import java.io.IOException;




import java.net.URL;
import java.util.UUID;



import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CSpaceCreator extends Activity {
	private ListView lView;
	String param1;
	String param3;
	@Override
	public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	Bundle bundle = this.getIntent().getExtras();
	param1 = bundle.getString("param1");
	param3 = bundle.getString("param3");
	final String param2[] = bundle.getStringArray("param2");
	setContentView(R.layout.cspace);
	Button inviteBtn = (Button) findViewById(R.id.btnInvite);
	
	
	lView = (ListView) findViewById(R.id.ListView01);
//		 Set option as Multiple Choice. So that user can able to select more the one option from list
	
	lView.setAdapter(new ArrayAdapter<String>(this,
	android.R.layout.simple_list_item_multiple_choice, param2));
	lView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	
	inviteBtn.setOnClickListener( new OnClickListener(){
        @Override
		public void onClick(View v ){
        	//long[] selectedItems= lView.getCheckItemIds();
        	StringBuilder sbParticipants = new StringBuilder();
        	//int items =selectedItems.length;
        	 SparseBooleanArray a = lView.getCheckedItemPositions();
        	 for(int i = 0; i <a.size();i++)
        	  if (a.valueAt(i)) {
        	   sbParticipants.append(param2[a.keyAt(i)]+"-");
        	  }
        	sbParticipants.append(param3);
        	Toast toast=Toast.makeText(getApplicationContext(), sbParticipants.toString(), 1000);  
            toast.show();
        	Log.i("blah", "blah blah");
        	//pubInvitation(param3,param1);
        	pubInvitation(sbParticipants.toString(),param3,param1);
            //finish();
        }	        
    });
	}
	
	protected void pubInvitation(String userlist,String owner, String meetingName) {
		
		
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
			String queryString = "http://l-sls0483d.research.ltu.se:8080/axis2/services/Dynamicgroup/AddMeeting?userlist="+userlist+"&&owner="+owner+"&&meetingName="+meetingName+"&&groupKey="+meetingIdS;
			
			/* Replace blanks with HTML-Equivalent. */		
			URL url = new URL(queryString.replace(" ", "%20"));
			url.getContent();
			
			
		}catch (IOException ie) {
			ie.printStackTrace();
		}
		
		
        
        Toast toast=Toast.makeText(getApplicationContext(), meetingIdS, 1000);  
        toast.show();
        Bundle bundle = new Bundle();
		bundle.putString("param1", meetingIdS);
		Intent i = new Intent(CSpaceCreator.this, WebViewExample.class);    	    	
        i.putExtras(bundle);
        startActivity(i);
        
    }
	
	}
