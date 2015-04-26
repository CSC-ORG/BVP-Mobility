package com.example.homeautomation;

import com.getbase.floatingactionbutton.FloatingActionButton;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AllAppliance  extends Activity {

	LocalDatabase hsc;
	Button log;
	
	HomeDatabase hd1 ;
	LocalDatabase ld ;
	private ProgressDialog pDialog;
    // JSON parser class
	JSONParser jsonParser = new JSONParser();

    private String STATUS_URL = null;

	String[] nameVal;
    String[] IdVal;
   ArrayList<Appliance> AppList;
    effadapter arrayAdapter;
    ListView myList;

	private static final String TAG_SUCCESS = "response";
	private static final String TAG_MESSAGE = "msg";



	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appliance);
		AppList = new ArrayList<Appliance>(); 
		final FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add_app);
        add.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
  			// TODO Auto-generated method stub
  			Intent i = new Intent(getBaseContext(),AddDevice.class);
  			startActivity(i);
  		}
        });
        
        final FloatingActionButton rem1 = (FloatingActionButton) findViewById(R.id.rem_app);
        rem1.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
  			// TODO Auto-generated method stub
  			Intent i = new Intent(getBaseContext(),Remove.class);
  			startActivity(i);
  		}
        });
        myList = (ListView) findViewById(R.id.listViewFromDB);
		
	   
				LocalDatabase myDb = new LocalDatabase(this);
		myDb.open();
	
	

		
		try {
			getValues();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 arrayAdapter = new effadapter (this,  R.layout.list, AppList );
		 
		myList.setAdapter(arrayAdapter);
		myList.setOnItemClickListener(new OnItemClickListener() {

		 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	            Appliance app = AppList.get(position);

	                     Intent intent = new Intent(AllAppliance.this, AllAppliance.class);
	                     Bundle bundle = new Bundle();
	                     bundle.putString("AppId", app.getappid());
	                     bundle.putString("AppName", app.getappname());
	                     bundle.putString("AppStatus", app.getappstatus());
	                   
	                     intent.putExtras(bundle);
	                     startActivity(intent);

	        }
	    });
		myDb.close();
		HomeDatabase hd = new HomeDatabase(getBaseContext());
		String[] IdVal = hd.getdata();
		new JSONAsyncTask().execute("http://smarthome.net84.net/GetAppliances.php?id="+IdVal[0]);
		
		     
		  
	}

	
	public void getValues() throws Exception
	{
		hsc=new LocalDatabase(getApplicationContext());
		hsc.open();
		AppList =hsc.getdata();
		
		
		hsc.close();
	
        
	 
	}
	
	class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(AllAppliance.this);
			dialog.setMessage("Loading, please wait");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}
		
		@Override
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
		
		protected void onPostExecute(Boolean result) {
			dialog.cancel();
			
			if(result == false)
				Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
			
		}
	}
	


}