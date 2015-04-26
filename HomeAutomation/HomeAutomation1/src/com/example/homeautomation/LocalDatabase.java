package com.example.homeautomation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

public class LocalDatabase {

	public static final String KEY_APP_ID = "id";
	public static final String KEY_APP_NAME = "name";
	public static String KEY_STATUS = "status";
	
	private static final String DATABASE_NAME = "home_automation";
	private static final String DATABASE_TABLE = "name";
	private static final int DATABASE_VERSION = 1;
	
	public static final String[] ALL_KEYS = new String[] {KEY_APP_ID, KEY_APP_NAME, KEY_STATUS};
	
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
			KEY_APP_ID + " TEXT PRIMARY KEY , " + 
			KEY_APP_NAME + " TEXT NOT NULL , " +
			KEY_STATUS + " TEXT NOT NULL);"
			);
			Log.e("table created as", ("CREATE TABLE " + DATABASE_TABLE + " (" +
			KEY_APP_ID + " TEXT PRIMARY KEY , " + 
			KEY_APP_NAME + " TEXT NOT NULL , " +
			KEY_STATUS + " TEXT NOT NULL);"));
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	public LocalDatabase(Context c){
		ourContext = c;
	}
	
	public LocalDatabase open() throws SQLException{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		ourHelper.close();
	}

	public long createEntry(String id,String name,String status) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_APP_ID,id);
		cv.put(KEY_APP_NAME,name);
		cv.put(KEY_STATUS,status);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public ArrayList<Appliance> getdata(){
		// TODO Auto-generated method stub
		String[] columns = new String[]{ KEY_APP_ID, KEY_APP_NAME, KEY_STATUS};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		ArrayList<Appliance> result = new ArrayList<Appliance>();
		
		int iid = c.getColumnIndex(KEY_APP_ID);
		int iname = c.getColumnIndex(KEY_APP_NAME);
		int istatus = c.getColumnIndex(KEY_STATUS);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Appliance app = new Appliance();
			app.setappid(c.getString(iid));
			app.setappname(c.getString(iname));
			app.setappstatus(c.getString(istatus));
			result.add(app);
		}
		return result;
	}
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	ourDatabase.query(DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	public Cursor display() {
		String[] columns = new String[]{ KEY_APP_ID, KEY_APP_NAME, KEY_STATUS};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		return c;
		
	}

	
	  public void updateEntry(String ti, String t2) throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cvUpdate = new ContentValues();
		//cvUpdate.put(KEY_APP_ID, t1);
		cvUpdate.put(KEY_STATUS, t2);
		ourDatabase.update(DATABASE_TABLE, cvUpdate ,KEY_APP_ID + "=?", new String[] { ti });
		
	}
	
	public void deleteEntry(String ti) throws SQLException{
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE,KEY_APP_ID + "=?", new String[] { ti });
		
	}
	public void deleteAll() throws SQLException {
		Log.e("string is ", "delete from " + DATABASE_TABLE);
		ourDatabase.execSQL("delete from " + DATABASE_TABLE);
	}
}