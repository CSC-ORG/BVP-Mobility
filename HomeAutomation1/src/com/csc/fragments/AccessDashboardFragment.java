package com.csc.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.csc.ha.DashboardActivity;
import com.csc.ha.MainActivity;
import com.csc.ha.R;

public class AccessDashboardFragment extends Fragment {
	Button btnAccessDashboard,btnLogout;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.layout_dashboard, container,false);
		btnAccessDashboard = (Button)rootView.findViewById(R.id.button1);
		    DashboardActivity.fragment_no=-1;
		    ((DashboardActivity)getActivity()).getActionbar().setDisplayHomeAsUpEnabled(false);
		    btnAccessDashboard.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					((DashboardActivity)getActivity()).changeFragment(new AllAppliance());
				}
		    	
		    });
		    
		    btnLogout = (Button)rootView.findViewById(R.id.button2);
		    
		    btnLogout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				                getActivity());
				        // Setting Dialog Title
				        alertDialog.setTitle("Logout before exiting");
				        // Setting Dialog Message
				        alertDialog.setMessage("Do you want to Logout?");
				        // Setting Icon to Dialog
				        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
				        // Setting Positive "Yes" Button
				        alertDialog.setPositiveButton("YES",
				                new DialogInterface.OnClickListener() {
				                    @Override
									public void onClick(DialogInterface dialog, int which) {
				                    	SharedPreferences sharedpreferences = getActivity().getSharedPreferences("ha", Context.MODE_PRIVATE);
				   					 Editor editor = sharedpreferences.edit();
				   					 editor.putString("user", "null");
				   					 editor.commit();
				   					 Intent i = new Intent(getActivity(),MainActivity.class);
				   					 startActivity(i);
				   					 getActivity().finish();
				                    }
				                });
				        // Setting Negative "NO" Button
				        alertDialog.setNegativeButton("NO",
				                new DialogInterface.OnClickListener() {
				                    @Override
									public void onClick(DialogInterface dialog, int which) {
				                        // Write your code here to invoke NO event
				                        dialog.cancel();
				                    }
				                });
				        // Showing Alert Message
				        alertDialog.show();	
				}
		    	
		    }); 
		return rootView;
	}

}
