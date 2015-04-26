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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iangclifton.android.floatlabel.FloatLabel;



public class AddDevice extends Activity {

	Button bac,sub;
	FloatLabel sqlAppId,sqlName;
	String id=null, name=null;
	
		LocalDatabase ld ;
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add);
	        
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        
	        sqlAppId = (FloatLabel) findViewById(R.id.float_label_1);
	        sqlName = (FloatLabel) findViewById(R.id.float_label_2);
	        sub = (Button)findViewById(R.id.imageButton2);
	        ld=new LocalDatabase(getApplicationContext());
	      
			
	        sub.setOnClickListener(new OnClickListener() {

	    		@Override
	    		public void onClick(View v) {
	    			try{
	 	    			id = sqlAppId.getEditText().getText().toString();
	 	    			name = sqlName.getEditText().getText().toString();
	 	    			
	 	    			LocalDatabase entry = new LocalDatabase(AddDevice.this);
	 	    			entry.open();
	 	    			entry.createEntry(id, name, "OFF");
	 	    			entry.close();
	 	    			}catch (Exception e ){
	 	    				
	 	    				String error = e.toString();
	 		    			Log.e("add device error", error);
	 	    			}
	 	    		
	    			Intent i = new Intent(getBaseContext(),AllAppliance.class);
	    			//finish();
	    			startActivity(i);
			        
	    		}
	        	
	        });
	        
	    }
	   

}
