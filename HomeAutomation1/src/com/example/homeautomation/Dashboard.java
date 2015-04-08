package com.example.homeautomation;

<<<<<<< HEAD
import com.getbase.floatingactionbutton.FloatingActionButton;
import android.widget.ListView;
import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
=======
>>>>>>> f64e6e726941cfd9ade9b7425d38a7b130362880
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.util.List;
import android.widget.Toast;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


public class Dashboard extends Activity {

<<<<<<< HEAD
	
	 Button add1;
     public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
     private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
    
        
        getActionBar().setDisplayHomeAsUpEnabled(true);   
        
        final FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add_app);
        add.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
  			// TODO Auto-generated method stub
  			Intent i = new Intent(getBaseContext(),AddDevice.class);
  			startActivity(i);
  		}
        });
        
        final FloatingActionButton rem1 = (FloatingActionButton) findViewById(R.id.rem_app);
        rem1.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
  			// TODO Auto-generated method stub
  			Intent i = new Intent(getBaseContext(),Remove.class);
  			startActivity(i);
  		}
        });
        
	    
	try{
		Intent intent = getIntent();
	
	String message = intent.getExtras().getString("txtData","");     
 
	lv = (ListView) findViewById(R.id.listView1);

    List<String> your_array_list = new ArrayList<String>();
    your_array_list.add(message);
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this, R.layout.list, your_array_list );

    lv.setAdapter(arrayAdapter); 
	}catch(Exception e)
	{
		
	}
        
=======
    Button add1;
	public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
	private ListView lv;
	String message;

    
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.dashboard);
	  
	  try{    
		    
			Intent intent = getIntent();
			 message = intent.getExtras().getString("txtData","");    
		    }
		    catch (Exception e) {
		       
		        ErrorDialog(e.getMessage());
		    }

	lv = (ListView) findViewById(R.id.listView1);

     
    List<String> your_array_list = new ArrayList<String>();
    your_array_list.add(message);
        
>>>>>>> f64e6e726941cfd9ade9b7425d38a7b130362880

        // This is the array adapter, it takes the context of the activity as a 
        // first parameter, the type of list view as a second parameter and your 
        // array as a third parameter.
<<<<<<< HEAD
   
   
=======
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this,  R.layout.list, your_array_list );

    lv.setAdapter(arrayAdapter); 
   

>>>>>>> f64e6e726941cfd9ade9b7425d38a7b130362880
    
    add1 = (Button)findViewById(R.id.button1); 
    add1.setOnClickListener(new View.OnClickListener() {

     		@Override
    public void onClick(View v) {
     			// TODO Auto-generated method stub
    Intent i = new Intent(getApplicationContext(),AddDevice.class);
    startActivity(i);
     		}
         	
         });
     
	}
	  
	  private void ErrorDialog(String Description) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
	                Dashboard.this);
	        alertDialog.setTitle("You get Error...");
	        alertDialog.setMessage(Description);
	        alertDialog.setIcon(R.drawable.dialog_icon);

	        alertDialog.setNegativeButton("Cancel",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.cancel();
	                    }
	                });

	        alertDialog.show();
	    }

}
	  
