package com.example.homeautomation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Login extends Activity {

	Button log,sig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.try1);
        
        log = (Button) findViewById(R.id.button1);
        
        log.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getBaseContext(),Welcome.class);
				startActivity(i);
			}
        	
        });
        
sig = (Button) findViewById(R.id.button2);
        
        sig.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getBaseContext(),Signup.class);
				startActivity(i);
			}
        	
        });
    }

}
