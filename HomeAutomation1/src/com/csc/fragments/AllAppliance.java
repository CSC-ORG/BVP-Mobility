package com.csc.fragments;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
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
import com.csc.adapters.EfficientAdapter;
import com.csc.application.MyApplication;
import com.csc.controller.GsonSampleRequest;
import com.csc.controller.MockAllApplianceController;
import com.csc.database.LocalDatabase;
import com.csc.ha.DashboardActivity;
import com.csc.ha.R;
import com.csc.model.Appliance;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class AllAppliance extends Fragment implements GsonSampleRequest {
	ArrayList<Appliance> mDataList;
	ListView mListView;
	EfficientAdapter adapter;
	private RequestQueue mVolleyQueue;
	StringRequest stringRequest;
	private final String TAG_REQUEST = "MY_TAG";
	String url=null;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.layout_all_appliances, container,false);
		DashboardActivity.fragment_no=0;
		((DashboardActivity)getActivity()).getActionbar().setDisplayHomeAsUpEnabled(true);
		final FloatingActionButton add = (FloatingActionButton) rootView.findViewById(R.id.add_app);
        add.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
  			// TODO Auto-generated method stub
  			((DashboardActivity)getActivity()).changeFragment(new AddAppliance());
  		}
        });
        mListView = (ListView) rootView.findViewById(R.id.listViewFromDB);
        mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.e("click", "click");
			}
		});
        
		return rootView;
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		try {
			mDataList=new LocalDatabase(getActivity()).open().getdata(DashboardActivity.HomeId);
			Log.e("database list sixe", ""+mDataList.size());
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter = new EfficientAdapter (getActivity(),  R.layout.list, mDataList );
		 
		mListView.setAdapter(adapter);
		url = MyApplication.BASE_URL+"/GetAppliances.php?id="+DashboardActivity.HomeId;
		createSampleGsonRequest();
		}
	
	

	




@Override
public void createSampleGsonRequest() {
	// TODO Auto-generated method stub
	 mVolleyQueue = Volley.newRequestQueue(getActivity());

		
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				parseFlickrImageResponse(response);
				//adapter.setNotifyOnChange(true)
;			}
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
	
}
private void parseFlickrImageResponse(String response) {

	mDataList.clear();
	MockAllApplianceController mController = new MockAllApplianceController(response);
	mController.init();
	for(Appliance app : mController.findAll())
	{
		mDataList.add(new Appliance(app.getID(), app.getappname(), app.getappid(), app.getappstatus()));
	}
	try{
		LocalDatabase ld = new LocalDatabase(getActivity());
		ld.open();
		ld.deleteAll();
	
		for(int i=0;i<mDataList.size();i++)
		{
			ld.createEntry(mDataList.get(i).getappid(),mDataList.get(i).getappname(),mDataList.get(i).getappstatus(),DashboardActivity.HomeId);
		}
		ld.close();
	}catch(Exception e)
	{
		
	}
	adapter = new EfficientAdapter (getActivity(),  R.layout.list, mDataList );
	 
	mListView.setAdapter(adapter);
	
}

}
