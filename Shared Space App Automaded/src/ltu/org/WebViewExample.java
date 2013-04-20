package ltu.org;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;


public class WebViewExample extends Activity {
	String param1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = this.getIntent().getExtras();
		param1 = bundle.getString("param1");
		setContentView(R.layout.workspace);
		
		
		final Button button = (Button) findViewById(R.id.button);
        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        
        button.setOnClickListener(new OnClickListener() {
            @Override
			public void onClick(View v) {
            	
                // Perform action on clicks
                
                // office network
        		//Uri uri = Uri.parse("http://172.16.0.24/~mjrana/notes/"+param1+"/notes.html");
        		
        		//home network
        		//Uri uri = Uri.parse("http://192.168.0.101/~mjrana/notes/"+param1+"/notes.html");
        		
        		//server computer l-sls0483d.research.ltu.se:8080
            	//Uri uri = Uri.parse("http://l-sls0483d.research.ltu.se/userdata/notes/"+param1+"/notes.html");
            	Uri uri = Uri.parse("http://l-sls0483d.research.ltu.se/userdata/notes/"+param1+"/notes.html");
        		
            	
        		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        		startActivity(intent);
                
            }
        });
        
        button1.setOnClickListener(new OnClickListener() {
            @Override
			public void onClick(View v) {
            	
            	// office network
        		//Uri uri = Uri.parse("http://172.16.0.24/~mjrana/notes/"+param1+"/cchat.html");
        		
        		//home network
        		//Uri uri = Uri.parse("http://192.168.0.101/~mjrana/notes/"+param1+"/notes.html");
        		
        		//server computer l-sls0483d.research.ltu.se:8080
            	//Uri uri = Uri.parse("http://l-sls0483d.research.ltu.se/userdata/notes/"+param1+"/cchat.html");
            	Uri uri = Uri.parse("http://l-sls0483d.research.ltu.se/userdata/notes/"+param1+"/cchat.html");
        		
        		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        		startActivity(intent);
            }
        });
        
        button2.setOnClickListener(new OnClickListener() {
            @Override
			public void onClick(View v) {
            	
                // Perform action on clicks
                // office network
        		//Uri uri = Uri.parse("http://172.16.0.24/~mjrana/notes/"+param1+"/notes.html");
        		
        		//home network
        		//Uri uri = Uri.parse("http://192.168.0.101/~mjrana/notes/"+param1+"/notes.html");
        		
            	//server computer l-sls0483d.research.ltu.se:8080
            	//Uri uri = Uri.parse("http://l-sls0483d.research.ltu.se/userdata/notes/"+param1+"/notes.html");
            	Uri uri = Uri.parse("http://l-sls0483d.research.ltu.se/userdata/notes/"+param1+"/notes.html");
            	
        		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        		startActivity(intent);
            }
        });
		

	}
}