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

import com.example.homeautomation.MainActivity.AttemptLogin;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class AddDevice extends Activity {

	Button bac,sub;
	EditText sqlAppId,sqlName;
	// Progress Dialog 
		private ProgressDialog pDialog;
		// JSON parser class
		JSONParser jsonParser = new JSONParser();
		HomeDatabase hd1 ;
		LocalDatabase ld ;
		
		 String[] nameVal;
		 String[] IdVal;
	private String LOGIN_URL =null;
	private static final String TAG_SUCCESS = "response";
	private static final String TAG_MESSAGE = "msg";
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add);
	        
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        
	        sqlAppId = (EditText) findViewById(R.id.editText1);
	        sqlName = (EditText) findViewById(R.id.editText2);
	        sub = (Button)findViewById(R.id.imageButton2);
	        ld=new LocalDatabase(getApplicationContext());
	        hd1=new HomeDatabase(getApplicationContext());
	        ld.open();
	        hd1.open();
	         nameVal = ld.getdata();
			 IdVal = hd1.getdata();
			 LOGIN_URL  = "http://smarthome.net84.net/AddAppliance.php?name="+nameVal[1] +"&id="+IdVal[0];
	        sub.setOnClickListener(new OnClickListener() {

	    		@Override
	    		public void onClick(View v) {
	    			new AttemptLogin().execute();
			        
	    		}
	        	
	        });
	        
	    }
	    class AttemptLogin extends AsyncTask<String, String, String>
	    {
	    	/** * Before starting background thread Show Progress Dialog * */ 
	    	boolean failure = false;
	    	@Override
	    	protected void onPreExecute()
	    	{
	    		super.onPreExecute();
	    		pDialog = new ProgressDialog(AddDevice.this); 
	    		pDialog.setMessage("Attempting to add device...");
	    		pDialog.setIndeterminate(false);
	    		pDialog.setCancelable(true);
	    		pDialog.show();
	    		} 
	    	@Override
	    	protected String doInBackground(String... args)
	    	{
	    		// TODO Auto-generated method stub // here Check for success tag
	    	
	    	
	    		 HttpClient httpclient = new DefaultHttpClient();
	    	        HttpResponse response;
	    	        String responseString = null;
	    	        try {
	    	        	//String url=LOGIN_URL+username+"&password="+password;
	    	            response = httpclient.execute(new HttpGet(LOGIN_URL));
	    	        	
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
	    			Log.d("Successfully added!", json.toString());
	    			 boolean didItWork = true;
	 				try{
	 	    			String id = sqlAppId.getText().toString();
	 	    			String name = sqlName.getText().toString();
	 	    			
	 	    			LocalDatabase entry = new LocalDatabase(AddDevice.this);
	 	    			entry.open();
	 	    			entry.createEntry(id, name, "OFF");
	 	    			entry.close();
	 	    			}catch (Exception e ){
	 	    				didItWork = false;
	 	    				String error = e.toString();
	 	    				Log.e("add device error", error);
	 	    			}
	 	    		
	    			Intent i = new Intent(getBaseContext(),AllAppliance.class);
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
	    		Toast.makeText(AddDevice.this, message, Toast.LENGTH_LONG).show();
	    		} }

	    
	      
	}

}
