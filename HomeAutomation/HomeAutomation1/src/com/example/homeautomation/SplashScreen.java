package com.example.homeautomation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(3000);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openStartingPoint = new Intent(SplashScreen.this,MainActivity.class);
					startActivity(openStartingPoint);
				}
			}
		};
	timer.start();
	/*		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
			Intent beg = new Intent(FirstPage.this,MainActivity.class);
			FirstPage.this.startActivity(beg);
			FirstPage.this.finish();
			}
		}, 5000);
		*/
	}


}
