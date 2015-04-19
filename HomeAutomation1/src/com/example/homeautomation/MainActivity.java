package com.example.homeautomation;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.iangclifton.android.floatlabel.FloatLabel;



public class MainActivity extends Activity {

	 FloatLabel user, pass,uname,email,pswd,phone;
	Button login, signup;
	// Progress Dialog 
	private ProgressDialog pDialog;
	// JSON parser class
	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://smarthome.net84.net/login.php?id=";
	
	private static final String TAG_SUCCESS = "response";
	private static final String TAG_MESSAGE = "msg";


	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.try1);
        
        user = (FloatLabel)findViewById(R.id.float_label_1);
        pass = (FloatLabel)findViewById(R.id.float_label_2);
        
       
        
        login = (Button) findViewById(R.id.button1);
        signup = (Button) findViewById(R.id.button2);
        
        
        login.setOnClickListener(new OnClickListener()
         {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				new AttemptLogin().execute();
			
			}
			});
        
        signup.setOnClickListener(new OnClickListener(){

			//@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getBaseContext(),Signup.class);
				startActivity(i);
				
			}	
        });
    }
        
        @Override
        public void onBackPressed() {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                    .setMessage("Do you want to exit?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton("no", null).show();
        } 

      
         
    class AttemptLogin extends AsyncTask<String, String, String>
    {
    	/** * Before starting background thread Show Progress Dialog * */ 
    	boolean failure = false;
    	@Override
    	protected void onPreExecute()
    	{
    		super.onPreExecute();
    		pDialog = new ProgressDialog(MainActivity.this); 
    		pDialog.setMessage("Attempting for login...");
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(true);
    		pDialog.show();
    		} 
    	@Override
    	protected String doInBackground(String... args)
    	{
    		// TODO Auto-generated method stub // here Check for success tag
    	
    		String username = user.getEditText().getText().toString(); 
    		String password = pass.getEditText().getText().toString();
    		 HttpClient httpclient = new DefaultHttpClient();
    	        HttpResponse response;
    	        String responseString = null;
    	        try {
    	        	String url=LOGIN_URL+username+"&password="+password;
    	            response = httpclient.execute(new HttpGet(url));
    	        	
    	        	//response = httpclient.execute(new HttpGet(params[0]));
    	            StatusLine statusLine = response.getStatusLine();
    	            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
    	                ByteArrayOutputStream out = new ByteArrayOutputStream();
    	                response.getEntity().writeTo(out);
    	                responseString = out.toString();
    	                out.close();
    	            } else{
    	                //Closes the connection.
    	                response.getEntity().getContent().close();
    	                throw new IOException(statusLine.getReasonPhrase());
    	            }
    	        } catch (ClientProtocolException e) {
    	            //TODO Handle problems..
    	        } catch (IOException e) {
    	            //TODO Handle problems..
    	        }
    	        
    	       JSONObject json = null;
			try {
				json = new JSONObject(responseString);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

    	
			Log.e("response", ""+json);
    	        try{
    		String res = json.getString(TAG_SUCCESS);
    		if (res.equalsIgnoreCase("true")) 
    		{
    			Log.d("Successfully Login!", json.toString());
    			HomeDatabase hd = new HomeDatabase(getApplicationContext());
    			hd.createEntry(username, password);
    			
    			Intent i = new Intent(getBaseContext(),Welcome.class);
    			finish();
    			startActivity(i);
    			
    			 return json.getString(TAG_MESSAGE);
    		}
    		else{
    			 return json.getString(TAG_MESSAGE);
    			   
    		}
    	}catch (JSONException e) {
    		e.printStackTrace();
    		}
    	return null;



    	
    
    }
    protected void onPostExecute(String message) {
    	pDialog.dismiss(); 
    	if (message != null){
    		Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    		} }

    
      
}
}