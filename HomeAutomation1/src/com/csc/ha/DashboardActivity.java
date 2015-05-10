package com.csc.ha;


import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
import com.csc.fragments.AccessDashboardFragment;
import com.csc.fragments.AllAppliance;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class DashboardActivity extends ActionBarActivity {
	ActionBar actionBar;
	public static String HomeId;
	public static int fragment_no=-1;
	public static Context mContext;
	 Context context = this;
	 private RequestQueue mVolleyQueue;
		StringRequest stringRequest;
		private final String TAG_REQUEST = "MY_TAG";
		
		
	 public static final String PROPERTY_REG_ID = "registration_id";
	    public static final String PROPERTY_APP_VERSION = "appVersion";
	    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	    /**
	     * Substitute you own sender ID here. This is the project number you got
	     * from the API Console, as described in "Getting Started."
	     */
	    String SENDER_ID = "350741573913";

	    /**
	     * Tag used on log messages.
	     */
	    static final String TAG = "GCMDemo";

	    GoogleCloudMessaging gcm;
	    AtomicInteger msgId = new AtomicInteger();
	    SharedPreferences prefs;
	    
	    String regid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		actionBar=getSupportActionBar();
		mContext=this;
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e81e61")));
		changeFragment(new AccessDashboardFragment());
;		 SharedPreferences sharedpreferences = getSharedPreferences("ha", Context.MODE_PRIVATE);
		 HomeId=sharedpreferences.getString("user", "null");
		 Log.e("home id saved", ""+HomeId);
		 initilizeGCM();
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
	public void changeFragment(Fragment f)
	{
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, f).commit();
	}
	public ActionBar getActionbar()
	{
		return actionBar;
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(fragment_no==1)
			changeFragment(new AllAppliance());
		else if(fragment_no==0)
			changeFragment(new AccessDashboardFragment());
		else
			{
			 new AlertDialog.Builder(mContext).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
             .setMessage("Do you want exit the app?")
             .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                	finish();
                	 }
             }).setNegativeButton("no", null).show();
				
			}
	}
	private void initilizeGCM() {
		 context = getApplicationContext();

		    // Check device for Play Services APK.
		    if (checkPlayServices()) {
		        // If this check succeeds, proceed with normal processing.
		        // Otherwise, prompt user to get valid Play Services APK.
		    	 gcm = GoogleCloudMessaging.getInstance(this);
		            regid = getRegistrationId(context);

		            if (regid.isEmpty()) {
		                registerInBackground();
		            }
		        } else {
		            Log.i(TAG, "No valid Google Play Services APK found.");
		        }		
	}
	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    Log.i(TAG,"My Id : "+registrationId);
	    if (registrationId.equals("")) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}
	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the regID in your app is up to you.
	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
	
	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
		new Register().execute("");
	}
	
	public class Register extends AsyncTask<String, Integer, String>
	{

		@Override
		protected String doInBackground(String... params) {
			String msg = "";
          try {
              if (gcm == null) {
                  gcm = GoogleCloudMessaging.getInstance(context);
              }
              regid = gcm.register(SENDER_ID);
              msg = "Device registered, registration ID=" + regid;

              // You should send the registration ID to your server over HTTP,
              // so it can use GCM/HTTP or CCS to send messages to your app.
              // The request to your server should be authenticated if your app
              // is using accounts.
              sendRegistrationIdToBackend();

              // For this demo: we don't need to send it because the device
              // will send upstream messages to a server that echo back the
              // message using the 'from' address in the message.

              // Persist the regID - no need to register again.
              storeRegistrationId(context, regid);
              Log.i(TAG,"My Id : "+regid);
          } catch (IOException ex) {
              msg = "Error :" + ex.getMessage();
              // If there is an error, don't just keep trying to register.
              // Require the user to click a button again, or perform
              // exponential back-off.
          }
          return msg;
		}
		
	}
	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	/**
	 * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
	 * or CCS to send messages to your app. Not needed for this demo since the
	 * device sends upstream messages to a server that echoes back the message
	 * using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend() {
	    // Your implementation here.
		Log.e("send server", "called");
		new SendServer().execute();
	}
	
	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i(TAG, "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	private class SendServer extends AsyncTask<String, Void, String> 
	{ 
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String id=telephonyManager.getDeviceId();
		
		@Override 
		protected String doInBackground(String... params) 
		
		{ 
			
			String url = "http://smarthome.net84.net/registerUser.php?user_id="+id+"&package_name=com.csc.ha&gcm_id="+regid+"&user_type="+HomeId;
			 mVolleyQueue = Volley.newRequestQueue(mContext);
 
				stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						
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

						
						
					}
				});
				stringRequest.setShouldCache(true);
				stringRequest.setTag(TAG_REQUEST);	
				mVolleyQueue.add(stringRequest);
		   
		   // return response.toString();
		

			 return null;
		} 
	}
	
}
