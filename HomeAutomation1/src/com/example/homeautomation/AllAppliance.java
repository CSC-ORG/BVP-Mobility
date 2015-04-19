package com.example.homeautomation;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AllAppliance  extends Activity {

	LocalDatabase hsc;
	Button add,log;
	public static List<String> titles,notes,sum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appliance);
	
		LocalDatabase myDb = new LocalDatabase(this);
		myDb.open();
		titles= new ArrayList<String>();
		notes=new ArrayList<String>();
		sum=new ArrayList<String>();
		
		ListView myList = (ListView) findViewById(R.id.listViewFromDB);
		try {
			getValues();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myList.setAdapter(new ArrayAdapter<String>(AllAppliance.this, android.R.layout.simple_list_item_1,sum));
		myList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(AllAppliance.this,AddDevice.class);
				i.putExtra("pos",arg2);
				
				startActivity(i);
			}
		});
		myDb.close();
	}
	public void getValues() throws Exception
	{
		hsc=new LocalDatabase(getApplicationContext());
		hsc.open();
		Cursor c=hsc.display();
		
		if(c==null)
			Log.e("cursor", "null");
		if (c.moveToFirst()) {
			do {
				Log.e("function", "called");
				int i=c.getColumnIndex("name");
				int j=c.getColumnIndex("status");
				
				String time=c.getString(i);
				String name=c.getString(j);
				
				titles.add(time);
				notes.add(name);
				sum.add(time + "\n" + name);
				
			} while (c.moveToNext());
		}

		
		
		Log.e("size is ", ""+titles.size());
		
		hsc.close();
		
		c.close();
add = (Button)findViewById(R.id.imageButton2);
        
        add.setOnClickListener(new OnClickListener() {

    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent i = new Intent(getBaseContext(),AddDevice.class);
    			startActivity(i);
    		}
        	
        });
        
        log = (Button)findViewById(R.id.button2);
 
        log.setOnClickListener(new OnClickListener() {

    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent i = new Intent(getBaseContext(),MainActivity.class);
    			startActivity(i);
    		}
        	
        });
    
    }
	
}
