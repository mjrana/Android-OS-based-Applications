package ltu.org;


import android.app.Activity;




import android.content.SharedPreferences;

import android.os.Bundle;

import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SigninActivity extends Activity {
    /** Called when the activity is first created. */
	
	private  final String MY_UNAME = "myname";
	private final String MY_PASS = "wallpaper";
	public static final String TAG = "ContactManager";
	private SharedPreferences mPreferences; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  setContentView(R.layout.list_view);
	  mPreferences = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE); 
	  Button signin= (Button) findViewById(R.id.ok);
	  signin.isClickable();
	  
	  
	  signin.setOnClickListener( new OnClickListener(){
		    
		  @Override
			public void onClick(View arg0) {
	        	  
			  EditText etxt_user = (EditText) findViewById(R.id.user_text);
		      EditText etxt_pass = (EditText) findViewById(R.id.pass_text);
		      etxt_pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		      etxt_pass.setTransformationMethod(new PasswordTransformationMethod());
		      String username = etxt_user.getText().toString();
		      String password = etxt_pass.getText().toString(); 
			  
			      
		      SharedPreferences.Editor editor=mPreferences.edit();
		      editor.putString(MY_UNAME, username);
		      editor.putString(MY_PASS, password);
		      
		      editor.putInt("counter", 0);
		      editor.commit();
	          finish();
	          Toast toast=Toast.makeText(getApplicationContext(), "Credentials are saved on Device", 1000);  
	            toast.show(); 
		  }
	        
	    });
	  
	}
}