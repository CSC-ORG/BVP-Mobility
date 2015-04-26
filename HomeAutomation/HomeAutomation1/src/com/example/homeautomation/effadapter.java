package com.example.homeautomation;

import java.io.ByteArrayOutputStream;
import android.widget.EditText;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

public class effadapter extends ArrayAdapter<Appliance> {

	
	private ArrayList<Appliance> objects;
     

	public effadapter(Context context, int textViewResourceId, ArrayList<Appliance> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
	}


	public View getView(int position, View convertView,ViewGroup parent){

	
		View v = convertView;

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list, parent,false);
		}

		
		Appliance i = objects.get(position);

		if (i != null) {

		

			TextView tv = (TextView)v.findViewById(R.id.textView1);
			ToggleButton mt = (ToggleButton) v.findViewById(R.id.FollowAndCenterButton3);
			if (tv != null){
				tv.setText(i.getappname());
			}
			
			if (mt != null){
				if(i.getappstatus().equalsIgnoreCase("on"))
				mt.setChecked(true);
				else
					mt.setChecked(false)
;			}
		
		
	
}
 return v;
	}
}
