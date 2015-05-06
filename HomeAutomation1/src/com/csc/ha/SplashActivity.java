package com.csc.ha;

import java.util.Timer;
import java.util.TimerTask;


import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class SplashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 SharedPreferences sharedpreferences = getSharedPreferences("ha", Context.MODE_PRIVATE);
				 if(sharedpreferences.getString("user", "null")!="null")
				 {

					 Intent i = new Intent(getApplicationContext(),DashboardActivity.class);
					 startActivity(i);
					 finish();
				 }
				 else
				 {
					 Intent i = new Intent(getApplicationContext(),MainActivity.class);
					 startActivity(i);
					 finish();
				 }
			}
		}, 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
