package com.csc.fragments;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.csc.ha.DashboardActivity;
import com.csc.ha.R;
import com.csc.model.GeneralizedModel;
import com.csc.model.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;
import com.iangclifton.android.floatlabel.FloatLabel;

public class AddAppliance extends Fragment implements GsonSampleRequest, com.csc.model.ProgressGenerator.OnCompleteListener {
	FloatLabel applianceName;
	private ProgressGenerator progressGenerator=new ProgressGenerator(this);
	private ActionProcessButton btnAdd;
	private RequestQueue mVolleyQueue;
	StringRequest stringRequest;
	private final String TAG_REQUEST = "MY_TAG";
	String url=null;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.layout_add_appliance, container,false);
		DashboardActivity.fragment_no=1;
		applianceName = (FloatLabel)rootView.findViewById(R.id.appName);
		((DashboardActivity)getActivity()).getActionbar().setDisplayHomeAsUpEnabled(true);
		btnAdd = (ActionProcessButton)rootView.findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				url=MyApplication.BASE_URL+"/AddAppliance.php?name="+applianceName.getEditText().getText().toString()+"&id="+DashboardActivity.HomeId;
				if(applianceName.getEditText().getText().toString().trim().length()==0)
					applianceName.getEditText().setError("Enter Valid Appliance Name");
				else
					{
					progressGenerator.start(btnAdd);
					btnAdd.setEnabled(false);
					btnAdd.setMode(ActionProcessButton.Mode.ENDLESS);
					createSampleGsonRequest();
						
					}
			}
		});
		return rootView;
	}
	

    

	@Override
	public void createSampleGsonRequest() {
		// TODO Auto-generated method stub
		 mVolleyQueue = Volley.newRequestQueue(getActivity());

			
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
	private void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
		btnAdd.setEnabled(true);
		progressGenerator.start(btnAdd);
		btnAdd.setText(R.string.add);
	}
	private void parseFlickrImageResponse(String response) {
		
		MockGeneralizedController mController = new MockGeneralizedController(response);
		mController.init();
		GeneralizedModel model = mController.findAll();
		if (model.getResponse().equalsIgnoreCase("true")) 
		{
			
			((DashboardActivity)getActivity()).changeFragment(new AllAppliance());
			
		}
		showToast(model.getMessage());
	}
	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		
	}
	
}
