package com.example.homeautomation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.homeautomation.MainActivity.AttemptLogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Welcome extends Activity {

	Button acc,log;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static final String ACC_URL = "http://smarthome.net84.net/GetAppliances.php?id=12345";
	private static final String TAG_SUCCESS = "response";
	
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
    
    getActionBar().setDisplayHomeAsUpEnabled(true);
        
    acc = (Button)findViewById(R.id.button1);
    
    acc.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new AttemptLogin().execute();
			
		}
    	
    });
    
    log = (Button)findViewById(R.id.button2);
    
    log.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(getBaseContext(),MainActivity.class);
			startActivity(i);
		}
    	
    }); 
  }
    
    
	  public void onBackPressed() {
	        //Display alert message when back button has been pressed
	        backButtonHandler();
	        return;
	    }

	    public void backButtonHandler() {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
	                Welcome.this);
	        // Setting Dialog Title
	        alertDialog.setTitle("Leave application?");
	        // Setting Dialog Message
	        alertDialog.setMessage("Do you want to Logout?");
	        // Setting Icon to Dialog
	        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
	        // Setting Positive "Yes" Button
	        alertDialog.setPositiveButton("YES",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        finish();
	                    }
	                });
	        // Setting Negative "NO" Button
	        alertDialog.setNegativeButton("NO",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        // Write your code here to invoke NO event
	                        dialog.cancel();
	                    }
	                });
	        // Showing Alert Message
	        alertDialog.show();
	    }
	    


    class AttemptLogin extends AsyncTask<String, String, ArrayList<Appliance>>
    {
    	/** * Before starting background thread Show Progress Dialog * */ 
    	boolean failure = false;
    
    	
    	@Override
    	protected void onPreExecute()
    	{
    		super.onPreExecute();
    		pDialog = new ProgressDialog(Welcome.this); 
    		pDialog.setMessage("Attempting for login...");
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(true);
    		pDialog.show();
    		} 
    	@Override
    	protected ArrayList<Appliance> doInBackground(String... args)
    	{
    		// TODO Auto-generated method stub // here Check for success tag
      
    		ArrayList<Appliance> list = new ArrayList<Appliance>();
    		
    		
   		 HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
    		
	     
	  
	        try{ 
	        	
	        	//make the request
	        	response = httpclient.execute(new HttpGet(ACC_URL));
	        
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
	        	//pass the data into json object
	        	JSONObject get_string = new JSONObject(responseString); 

	            
	        	JSONArray jsonProduct_jsonarray = new JSONArray();

	        	
	            //get the data associated with array called app_list
	        	jsonProduct_jsonarray = get_string .getJSONArray("app_list");

	        	
	            //iterate over the collection of array from json
	            for (int i = 0; i < jsonProduct_jsonarray.length(); i++) {

	             System.out.println("GOT JSON VALUE ");
	             JSONObject c = jsonProduct_jsonarray.getJSONObject(i);

	             Appliance app = new Appliance(); 
	             
	             app.setID(c.getString("HomeID"));
	             app.setappname(c.getString("AppName"));
	             app.setappid(c.getString("AppId"));
	             app.setLMT(c.getString("LastModTime"));
	             app.setappstatus(c.getString("AppStatus"));
	             
	             list.add(app);
	            }
	            
	            
	           
	        } catch (ClientProtocolException e) {
	            //TODO Handle problems..
	        } catch (IOException e) {
	            //TODO Handle problems..
	        } catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	       JSONObject json = null;
		try {
			json = new JSONObject(responseString);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Log.e("response", ""+json);
try  {
		String res = json.getString(TAG_SUCCESS);
	
		if (res.equalsIgnoreCase("true")) 
	
		{
			Log.d("Successfully displayed!", json.toString());
			Intent i = new Intent(getBaseContext(),Dashboard.class);
			finish();
			startActivity(i);
			 return list;
		}
		else{
			ArrayList<Appliance> list2 = new ArrayList<Appliance>();
			list2.add(new Appliance("",TAG_SUCCESS,"","",""));
			
			 return list2;
			   
		}
	}catch (JSONException e) {
	e.printStackTrace();
	}
return null;

 	        
 	        	
 	                	        













	        }
    	 protected void onPostExecute(String message) {
    	    	pDialog.dismiss(); 
    	    	if (message != null){
    	    		Toast.makeText(Welcome.this, message, Toast.LENGTH_LONG).show();
    	    		} }


    	



}
}