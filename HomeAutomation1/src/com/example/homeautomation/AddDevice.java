package com.example.homeautomation;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import android.widget.ArrayAdapter;
import android.widget.EditText;



public class AddDevice extends Activity {
<<<<<<< HEAD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
     
    	getActionBar().setDisplayHomeAsUpEnabled(true);   
        
    	  
        
        
    	Intent intent = new Intent(getApplicationContext(),Dashboard.class);

       
    	EditText editText = (EditText) findViewById(R.id.float_label_2);
    	String message = editText.getText().toString();
    	intent.putExtra("txtData", message);
    }    
=======
	


@Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.add);
    
	getActionBar().setDisplayHomeAsUpEnabled(true);   
    
  
    
     
	Intent intent = new Intent(getApplicationContext(),Dashboard.class);
>>>>>>> f64e6e726941cfd9ade9b7425d38a7b130362880

   
	EditText editText = (EditText) findViewById(R.id.editText2);
	String message = editText.getText().toString();
	intent.putExtra("txtData", message);
}    
}



