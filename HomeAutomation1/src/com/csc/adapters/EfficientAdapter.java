package com.csc.adapters;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import com.csc.fragments.AllAppliance;
import com.csc.ha.DashboardActivity;
import com.csc.ha.R;
import com.csc.model.Appliance;
import com.csc.model.GeneralizedModel;

public class EfficientAdapter extends ArrayAdapter<Appliance> implements GsonSampleRequest{

	
	private ArrayList<Appliance> objects;
     
	public Context mContext;
	private ProgressDialog pDialog;
	public static int mPosition;
	public static String mStatus;
	private RequestQueue mVolleyQueue;
	StringRequest stringRequest;
	private final String TAG_REQUEST = "MY_TAG";
	String url=null;
	
	public EfficientAdapter(Context context, int textViewResourceId, ArrayList<Appliance> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
		mContext=context;
	}

	public int getCount() {
        return objects.size();
    }
    
    

    public long getItemId(int position) {
        return position;
    }
	@Override
	public View getView(final int position, View convertView,ViewGroup parent){

	ViewHolder holder;
	if(convertView==null)
	{
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	convertView = inflater.inflate(R.layout.list, parent,false);
		holder = new ViewHolder();
		holder.name = (TextView)convertView.findViewById(R.id.textView1);
		holder.mt = (ToggleButton) convertView.findViewById(R.id.FollowAndCenterButton3);
		holder.view=convertView;
		convertView.setTag(holder);
	}else
	{
		holder=(ViewHolder)convertView.getTag();
	}
		

		
		Appliance i = objects.get(position);

		if (i != null) {

		

			
				holder.name.setText(i.getappname());
			
			
			
				if(i.getappstatus().equalsIgnoreCase("on"))
				holder.mt.setChecked(true);
				else
					holder.mt.setChecked(false)
;			
				holder.mt.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.e(" toglle presed position is" , ""+position);
						if(objects.get(position).getappstatus().toString().equalsIgnoreCase("on"))
							mStatus="OFF";
						else
							mStatus="ON";
						mPosition=position;
						
						new AttemptLogin().execute();
					}
				});
			holder.mt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(objects.get(position).getappstatus().toString().equalsIgnoreCase("on"))
						mStatus="OFF";
					else
						mStatus="ON";
					mPosition=position;
					
					//new AttemptLogin().execute();
				}
			});		
		
	
}
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				 new AlertDialog.Builder(mContext).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Remove Appliance")
                .setMessage("Do you want delete the device?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        new DeleteJSONAsyncTask().execute("http://smarthome.net84.net/RemoveAppliance.php?id="+DashboardActivity.HomeId+"&appid="+objects.get(position).getappid());
                    }
                }).setNegativeButton("no", null).show();
				
			}
		});
 return convertView;
	}
	 
    class ViewHolder {
        TextView name;
        ToggleButton mt;
        View view;
    }
    
    
	
	
class DeleteJSONAsyncTask extends AsyncTask<String, Void, Boolean> {
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(DashboardActivity.mContext);
			dialog.setMessage("Loading, please wait");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}
		
		@Override
		protected Boolean doInBackground(String... urls) {
			
				url=urls[0];
				 mVolleyQueue = Volley.newRequestQueue(mContext);

					
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
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			dialog.dismiss();
			((DashboardActivity)mContext).changeFragment(new AllAppliance());
		}
		
		
}
    
    
	 class AttemptLogin extends AsyncTask<String, String, String>
	    {
	    	/** * Before starting background thread Show Progress Dialog * */ 
	    	boolean failure = false;
	    	@Override
	    	protected void onPreExecute()
	    	{
	    		super.onPreExecute();
	    		pDialog = new ProgressDialog(mContext); 
	    		pDialog.setMessage("Changing The Status...");
	    		pDialog.setIndeterminate(false);
	    		pDialog.setCancelable(true);
	    		pDialog.show();
	    		} 
	    	@Override
	    	protected String doInBackground(String... args)
	    	{
	    		// TODO Auto-generated method stub // here Check for success tag
	    		SharedPreferences sharedpreferences = mContext.getSharedPreferences("ha", Context.MODE_PRIVATE);
   				
	        	url=MyApplication.BASE_URL+"/UpdateStatus.php?id="+sharedpreferences.getString("user", "null")+"&appid="+objects.get(mPosition).getappid()+"&status="+mStatus;
	            Log.e("status url is", url);
	            mVolleyQueue = Volley.newRequestQueue(mContext);

				
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
	    	return null;



	    	
	    
	    }
	    @Override
		protected void onPostExecute(String message) {
	    	pDialog.dismiss(); 
	    	//((DashboardActivity)mContext).changeFragment(new AllAppliance());
    		
	    }

	    
	      
	}


	@Override
	public void createSampleGsonRequest() {
		// TODO Auto-generated method stub
		
	}
	private void showToast(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
	}
	private void parseFlickrImageResponse(String response) {
		
		MockGeneralizedController mController = new MockGeneralizedController(response);
		mController.init();
		GeneralizedModel model = mController.findAll();
		if (model.getResponse().equalsIgnoreCase("true")) 
		{
			
			((DashboardActivity)mContext).changeFragment(new AllAppliance());
			
		}
		showToast(model.getMessage());
	}
	
}
