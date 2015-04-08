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

import com.iangclifton.android.floatlabel.FloatLabel;

//import com.example.homeautomation.MainActivity.AttemptLogin;

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

public class Signup extends Activity {

	FloatLabel name,username,email,pswd,rpass,phone;
	Button sig;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	
	private static final String SIGNUP_URL= "http://smarthome.net84.net/registerHome.php?id=";
	private static final String TAG_SUCCESS = "response";
	private static final String TAG_MESSAGE = "msg";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        
        name = (FloatLabel)findViewById(R.id.float_label_1);
        username = (FloatLabel)findViewById(R.id.float_label_2);
        email=(FloatLabel)findViewById(R.id.float_label_5);
        pswd=(FloatLabel)findViewById(R.id.float_label_3);
        rpass=(FloatLabel)findViewById(R.id.float_label_4);
        phone=(FloatLabel)findViewById(R.id.float_label_6);
   
    getActionBar().setDisplayHomeAsUpEnabled(true);
    
    sig  = (Button)findViewById(R.id.button2);
   
    sig.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Intent i = new Intent(getBaseContext(),Welcome.class);
			//startActivity(i);
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
    		pDialog = new ProgressDialog(Signup.this); 
    		pDialog.setMessage("Attempting for login...");
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(true);
    		pDialog.show();
    		} 
    	@Override
    	protected String doInBackground(String... params)
    	{
    		// TODO Auto-generated method stub // here Check for success tag
    	
    		String username = name.getEditText().getText().toString();
    		String password = pswd.getEditText().getText().toString();
    		 HttpClient httpclient = new DefaultHttpClient();
    	        HttpResponse response;
    	        String responseString = null;
    	        try {
    	        	String url=SIGNUP_URL+username+"&password="+password;
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

    	/*try {
    		List<NameValuePair> params = new ArrayList<NameValuePair>();
    		params.add(new BasicNameValuePair("id", username));
    		params.add(new BasicNameValuePair("password", password)); 
    		Log.d("request!", "starting");
    		JSONObject json = jsonParser.makeHttpRequest( LOGIN_URL, "POST", params);
    		// checking log for json response
    		Log.d("Login attempt", json.toString()); 
    		// success tag for json */
			Log.e("response", ""+json);
    	        try{
    		String res = json.getString(TAG_SUCCESS);
    		if (res.equalsIgnoreCase("true")) 
    		{
    			Log.d("Successfully Login!", json.toString());
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
    		Toast.makeText(Signup.this, message, Toast.LENGTH_LONG).show();
    		} }

    
    

}
}