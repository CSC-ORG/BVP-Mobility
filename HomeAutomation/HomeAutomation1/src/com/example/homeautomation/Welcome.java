package com.example.homeautomation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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
	//private ProgressDialog pDialog;
	//JSONParser jsonParser = new JSONParser();
	//private static final String ACC_URL = "http://smarthome.net84.net/GetAppliances.php?id=12345";
	//private static final String TAG_SUCCESS = "response";
	
	//ArrayList<Appliance> AppList;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
    
    getActionBar().setDisplayHomeAsUpEnabled(true);
        
    acc = (Button)findViewById(R.id.button1);
    
    acc.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent i=new Intent(Welcome.this,AllAppliance.class);
			startActivity(i);
			finish();
			
			/*AppList = new ArrayList<Appliance>();
		    HomeDatabase hd = new HomeDatabase(getBaseContext());
		    String[] IdVal = hd.getdata();
		     new JSONAsyncTask().execute("http://smarthome.net84.net/GetAppliances.php?id="+IdVal[0]);
		*/
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
	    
	    /*class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
			
			ProgressDialog dialog;
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				dialog = new ProgressDialog(Welcome.this);
				dialog.setMessage("Loading, please wait");
				dialog.setTitle("Connecting server");
				dialog.show();
				dialog.setCancelable(false);
			}
		*/	
		/*	@Override
			protected Boolean doInBackground(String... urls) {
				try {
					
					//------------------>>
					HttpGet httppost = new HttpGet(urls[0]);
					HttpClient httpclient = new DefaultHttpClient();
					HttpResponse response = httpclient.execute(httppost);

					// StatusLine stat = response.getStatusLine();
					int status = response.getStatusLine().getStatusCode();

					if (status == 200) {
						HttpEntity entity = response.getEntity();
						String data = EntityUtils.toString(entity);
						
					
						JSONObject jsono = new JSONObject(data);
						JSONArray jarray = jsono.getJSONArray("app_list");
						
						for (int i = 0; i < jarray.length(); i++) {
							JSONObject object = jarray.getJSONObject(i);
						
							Appliance app = new Appliance();
							
							 app.setID(object.getString("AppId"));
				             app.setappname(object.getString("AppName"));
				             //app.setappid(object.getString("AppId"));
				             //app.setLMT(object.getString("LastModTime"));
				             app.setappstatus(object.getString("AppStatus"));
				             Log.e("app status", object.getString("AppId"));
				             AppList.add(app);
						}
						LocalDatabase ld = new LocalDatabase(getBaseContext());
						ld.open();
						ld.deleteAll();
						for(int i=0;i<AppList.size();i++)
						{
						ld.createEntry(AppList.get(i).getappid(),AppList.get(i).getappname(),AppList.get(i).getappstatus());
						}
						ld.close();
						return true;
					}
					
					//------------------>>
					
				} catch (ParseException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return false;
			}
		*/	
		/*	protected void onPostExecute(Boolean result) {
				dialog.cancel();
				
				if(result == false)
					Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
				else
				{
					Intent i=new Intent(Welcome.this,AllAppliance.class);
					startActivity(i);
					finish(); 
				}
			}
		}
		*/
		
		

		
		
		
	}


    	        
 	        	
 	                	        




