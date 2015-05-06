package com.csc.fragments;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
import com.csc.ha.MainActivity;
import com.csc.ha.R;
import com.csc.model.GeneralizedModel;
import com.csc.model.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;
import com.iangclifton.android.floatlabel.FloatLabel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Signup extends Fragment implements GsonSampleRequest,com.csc.model.ProgressGenerator.OnCompleteListener {
	FloatLabel name,homeid,email,pswd,rpass,phone;
	ActionProcessButton btnSign;
	private ProgressGenerator progressGenerator=new ProgressGenerator(this);
	private String url = null;
	private RequestQueue mVolleyQueue;
	StringRequest stringRequest;
	private final String TAG_REQUEST = "MY_TAG";
	public boolean isValid=true;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.layout_signup, container,false);
		  name = (FloatLabel)rootView.findViewById(R.id.float_label_1);
	        homeid = (FloatLabel)rootView.findViewById(R.id.float_label_2);
	        email=(FloatLabel)rootView.findViewById(R.id.float_label_5);
	        pswd=(FloatLabel)rootView.findViewById(R.id.float_label_3);
	        rpass=(FloatLabel)rootView.findViewById(R.id.float_label_4);
	        phone=(FloatLabel)rootView.findViewById(R.id.float_label_6);
	        MainActivity.fragment_no=1;
	        MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
;	    
	    btnSign  = (ActionProcessButton)rootView.findViewById(R.id.btnSignUp);
	   
	    btnSign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent i = new Intent(getBaseContext(),Welcome.class);
				//startActivity(i);
				final String name1 = name.getEditText().getText().toString();
				if (!isValidName(name1)) {
					isValid=false;
					name.getEditText().setError("Invalid name");
				}
				final String HomeId = homeid.getEditText().getText().toString();
				if (!isValidName(name1)) {
					isValid=false;
					homeid.getEditText().setError("Invalid name");
				}
				
				final String email1 = email.getEditText().getText().toString();
				if (!isValidEmail(email1)) {
					isValid=false;
					email.getEditText().setError("Invalid Email");
				}

				final String password = pswd.getEditText().getText().toString();
				if (!isValidPassword(password)) {
					isValid=false;
					
					pswd.getEditText().setError("Invalid Password");
				}
				
				final String ph = phone.getEditText().getText().toString();
				if (!isValidPhoneNo(ph)) {
					
					isValid=false;
					phone.getEditText().setError("Invalid phone no.");
				}
				
				final String repeatPassword = rpass.getEditText().getText().toString();
				if (!checkPassWordAndRepreatPassword(password,repeatPassword)) {
					isValid=false;
					
					rpass.getEditText().setError("Passwords doesn't match");
				}
				 url=MyApplication.BASE_URL+"/registerHome.php?id="+HomeId+"&password="+password;
		          if(isValid)
		          {
		        	  progressGenerator.start(btnSign);
						btnSign.setEnabled(false);
						btnSign.setMode(ActionProcessButton.Mode.ENDLESS);
						createSampleGsonRequest();
						
		          }
			}
	    	
	    });
		return rootView;
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	// validating name
    private boolean isValidName(String name1) {
	String NAME_PATTERN = "^[\\p{L} .'-]+$";

		Pattern pattern = Pattern.compile(NAME_PATTERN);
		Matcher matcher = pattern.matcher(name1);
		return matcher.matches();
	}

// validating email id
	private boolean isValidEmail(String email1) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email1);
		return matcher.matches();
	}

	// validating password 
	private boolean isValidPassword(String pass) {
		if (pass != null && pass.length() > 5) {
			return true;
		}
		return false;
	}

// validating phone no
	private boolean isValidPhoneNo(String ph) {
		 if (!TextUtils.isEmpty(ph)) {
	        return Patterns.PHONE.matcher(ph).matches();
	    }
	    return false;
	}
// validating password and repeat password	
	private boolean checkPassWordAndRepreatPassword(String password,String repeatPassword) 
	 {
	     boolean pstatus = false;
	     if (repeatPassword != null && password != null) 
	     {
	       if (password.equals(repeatPassword)) 
	       {
	            pstatus = true;
	       } 
	     }
	    return pstatus;
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
		btnSign.setEnabled(true);
		progressGenerator.start(btnSign);
		btnSign.setText(R.string.signup);
	}
	private void parseFlickrImageResponse(String response) {
		
		MockGeneralizedController mController = new MockGeneralizedController(response);
		mController.init();
		GeneralizedModel model = mController.findAll();
		if (model.getResponse().equalsIgnoreCase("true")) 
		{
			
			SharedPreferences sharedpreferences = getActivity().getSharedPreferences("ha", Context.MODE_PRIVATE);
			 Editor editor = sharedpreferences.edit();
			 editor.putString("user", homeid.getEditText().getText().toString());
			 editor.commit();
			Intent i = new Intent(getActivity(),DashboardActivity.class);
			getActivity().finish();
			startActivity(i);
			
		}
		showToast(model.getMessage());
	}
	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		
	}
}
