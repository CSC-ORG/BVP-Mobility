package com.example.homeautomation;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

public class HomeDatabase {

	public static final String KEY_ID = "home_id";
	public static final String KEY_PWD = "password";
	
	private static final String DATABASE_NAME = "home_info";
	private static final String DATABASE_TABLE = "password";
	private static final int DATABASE_VERSION = 1;
	
	public static final String[] ALL_KEYS = new String[] {KEY_ID, KEY_PWD};
	
	
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
			KEY_ID + " TEXT PRIMARY KEY , " + 
			KEY_PWD + " TEXT NOT NULL);"
			);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	public HomeDatabase(Context c){
		ourContext = c;
	}
	
	public HomeDatabase open() throws SQLException{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		ourHelper.close();
	}

	public long createEntry(String home_id,String password) {
		// TODO Auto-generated method stub
		open();
		ContentValues cv = new ContentValues();
		cv.put(KEY_ID,home_id);
		cv.put(KEY_PWD,password);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public String[] getdata(){
		// TODO Auto-generated method stub
		open();
		String[] columns = new String[]{ KEY_ID, KEY_PWD};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String[] result = new String[2];
		
		int ihome_id = c.getColumnIndex(KEY_ID);
		int ipassword = c.getColumnIndex(KEY_PWD);
		
		
		c.moveToFirst(); 
			result[0] = c.getString(ihome_id) ;
			result[1] = c.getString(ipassword);
		
		
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
		String[] columns = new String[]{ KEY_ID, KEY_PWD};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		return c;
		
	}

	public void updateEntry(String ti, String t2) throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cvUpdate = new ContentValues();
		//cvUpdate.put(KEY_ID, t1);
		cvUpdate.put(KEY_PWD, t2);
		ourDatabase.update(DATABASE_TABLE, cvUpdate ,KEY_ID + "=?", new String[] { ti });
		
	}
	
	public void deleteEntry(String ti) throws SQLException{
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE,KEY_ID + "=?", new String[] { ti });
		
	}
	
	public void deleteAll() throws SQLException {
		ourDatabase.execSQL("TRUNCATE table" + DATABASE_TABLE);
	}
}