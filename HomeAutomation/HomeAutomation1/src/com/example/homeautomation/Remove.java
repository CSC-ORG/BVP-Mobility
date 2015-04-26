package com.example.homeautomation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class Remove extends Activity {
	
		
		
	    Button rem1;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	     
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.remove);
	        
	       
	 
	        
	        rem1 = (Button)findViewById(R.id.rem);
	        
	        rem1.setOnClickListener(new OnClickListener() {
	        	 
	            @Override
	            public void onClick(View v) {
	            	
	            	Intent i = new Intent(getBaseContext(),AllAppliance.class);
	    			startActivity(i);
	            }
	        });
	         
	         
	         
	        
	    }

	}



