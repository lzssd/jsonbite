package com.example.jsonbite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	public static final String CREATE_COIN="create table Coin("
             +"id integer primary key autoincrement,"
             +"name text,"
             +"price real,"
             +"vol real,"
             +"creattime time)";
	
	private Context mcontext;
	
	public MyDatabaseHelper(Context context,String name ,  CursorFactory factory,int version)
	{
		super(context,name,factory,version);
		mcontext=context;
		
	}
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_COIN);
		Toast.makeText(mcontext, "Create succeeded", Toast.LENGTH_SHORT).show();
		Log.d("tag","Create succeeded");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
		db.execSQL("drop table if exists Coin");
		onCreate(db);
	}
		

}
