package com.csc.ha;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.csc.application.MyApplication;
import com.csc.controller.GsonSampleRequest;
import com.csc.controller.MockGeneralizedController;
import com.csc.fragments.Signup;
import com.csc.model.GeneralizedModel;
import com.csc.model.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;
import com.iangclifton.android.floatlabel.FloatLabel;

public class MainActivity extends ActionBarActivity implements GsonSampleRequest, com.csc.model.ProgressGenerator.OnCompleteListener {
	public static String username,password;
	public static int fragment_no=0;
	public boolean checkLogin=false;
	public static ActionBar actionBar;
	FloatLabel user, pass;
	Button  signup;
	private ProgressGenerator progressGenerator=new ProgressGenerator(this);
	private ActionProcessButton login;
	
	private RequestQueue mVolleyQueue;
	StringRequest stringRequest;
	private final String TAG_REQUEST = "MY_TAG";
	
	int max_cache_size = 1000000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e81e61")));
		 user = (FloatLabel)findViewById(R.id.homeid);
	     pass = (FloatLabel)findViewById(R.id.password);
	   
	       actionBar=getSupportActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(false);
	        fragment_no=0;
	        login = (ActionProcessButton) findViewById(R.id.btnLogin);
	        signup = (Button) findViewById(R.id.btnSignup);
	       
	        login.setOnClickListener(new OnClickListener()
	         {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					username = user.getEditText().getText().toString(); 
		    	    password = pass.getEditText().getText().toString();
		    		if(user.getEditText().getText().toString().equals(""))
		    		{
		    			user.getEditText().setError("Enter the HomeId");
		    		}
		    		else if(pass.getEditText().getText().toString().equals(""))
		    		{
		    			pass.getEditText().setError("Enter the password");
		    		}
					else
					{
						if(isConnected())
						{
							progressGenerator.start(login);
							login.setEnabled(false);
							login.setMode(ActionProcessButton.Mode.ENDLESS);
							createSampleGsonRequest();
							//new AttemptLogin().execute();
						}
					    
					    else
						 Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_LONG).show();
				} 
				}
				});
	        
	        signup.setOnClickListener(new OnClickListener(){

				//@Override
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new Signup()).commit();
					
				}	
	        });
	}

	private void showToast(String msg) {
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
		login.setEnabled(true);
		progressGenerator.start(login);
		login.setText(R.string.login);
	}
	private void parseFlickrImageResponse(String response) {
		
		MockGeneralizedController mController = new MockGeneralizedController(response);
		mController.init();
		GeneralizedModel model = mController.findAll();
		if (model.getResponse().equalsIgnoreCase("true")) 
		{
			
			Intent i = new Intent(getBaseContext(),DashboardActivity.class);
			startActivity(i);
			 SharedPreferences sharedpreferences = getSharedPreferences("ha", Context.MODE_PRIVATE);
			 Editor editor = sharedpreferences.edit();
			 editor.putString("user", username);
			 editor.commit();
			 finish();
			
		}
		showToast(model.getMessage());
	}
	 public boolean isConnected(){
	        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
	            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	            if (networkInfo != null && networkInfo.isConnected()) 
	                return true;
	            else
	                return false;  
	    }
	        @Override
	        public void onBackPressed() {
	        	if(fragment_no==1)
	        	{
	        		Intent i = new Intent(getApplicationContext(),MainActivity.class);
	        		finish();
	        		startActivity(i);
	        	}
	        	else
	        	{
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
	        }
	      
	        @Override
		    public boolean onCreateOptionsMenu(Menu menu) {
		        // Inflate the menu; this adds items to the action bar if it is present.
		        getMenuInflater().inflate(R.menu.dashboard, menu);
		        return true;
		    }

		    @Override
		    public boolean onOptionsItemSelected(MenuItem item) {
		        // Handle action bar item clicks here. The action bar will
		        // automatically handle clicks on the Home/Up button, so long
		        // as you specify a parent activity in AndroidManifest.xml.
		        int id = item.getItemId();
		        if (id == android.R.id.home) {
		        	onBackPressed();
		            return true;
		        }
		        return super.onOptionsItemSelected(item);
		    }
	
		@Override
		public void createSampleGsonRequest() {
			// TODO Auto-generated method stub
			 mVolleyQueue = Volley.newRequestQueue(this);

			 String url=MyApplication.BASE_URL+"/login.php?id="+username+"&password="+password;
		     	
		        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						parseFlickrImageResponse(response);
						
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
						// For AuthFailure, you can re login with user credentials.
						// For ClientError, 400 & 401, Errors happening on client side when sending api request.
						// In this case you can check how client is forming the api and debug accordingly.
						// For ServerError 5xx, you can do retry or handle accordingly.
						if( error instanceof NetworkError) {
						} else if( error instanceof ClientError) { 
						} else if( error instanceof ServerError) {
						} else if( error instanceof AuthFailureError) {
						} else if( error instanceof ParseError) {
						} else if( error instanceof NoConnectionError) {
						} else if( error instanceof TimeoutError) {
						}

						
						showToast(error.getMessage());
					}
				});
				stringRequest.setShouldCache(true);
				stringRequest.setTag(TAG_REQUEST);	
				mVolleyQueue.add(stringRequest);
		}

		@Override
		public void onComplete() {
			// TODO Auto-generated method stub
			
		}
	    
		
}
