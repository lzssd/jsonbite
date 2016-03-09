package com.example.jsonbite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class mainactivity extends Activity {

	private ListView listView;
	private jsonAapter adapter;
    private Switch switcher;
	private MyDatabaseHelper dbHelper;
	
	SharedPreferences pre;
	SharedPreferences.Editor editor;
	
	private Handler handler=new Handler();

	private Handler handler2 = new Handler();  
	  
    private Runnable myRunnable= new Runnable() {    
        public void run() {  
        	         querydata();
                     handler2.postDelayed(this, 10000);  
        }  
    };  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.json);
		listView=(ListView)findViewById(R.id.list_view);
		switcher=(Switch)findViewById(R.id.switch1);
		 adapter=new jsonAapter(this);
	 dbHelper=new MyDatabaseHelper(this, "Coin.db", null, 3);
		pre=getSharedPreferences("money", MODE_MULTI_PROCESS);
		editor=pre.edit();
		querydata();
		
		Button CreateDatabase=(Button)findViewById(R.id.database);
		CreateDatabase.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("tag","Click Create");
				dbHelper.getWritableDatabase();
			}
		});
		Button addData=(Button)findViewById(R.id.add);
		addData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SQLiteDatabase db=dbHelper.getWritableDatabase();
				ContentValues values=new ContentValues();
				
				Time t=new Time(); 
				t.setToNow(); 
				
				values.put("name", "btc");
				values.put("price", 2870);
				values.put("vol", 4542222);
				values.put("creattime", t.format2445());
				db.insert("Coin", null, values);
				values.clear();
				values.put("name", "ltc");
				values.put("price", 22);
				values.put("vol", 3333);
				values.put("creattime", t.format2445());
				db.insert("Coin", null, values);
			}
		});
		
		Button deletedata=(Button)findViewById(R.id.delete);
		deletedata.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SQLiteDatabase db=dbHelper.getWritableDatabase();
				db.execSQL("drop table Coin");
			}
		});
		
		Button refresh=(Button)findViewById(R.id.refresh);
		refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				querydata();
				Toast toast=Toast.makeText(mainactivity.this, "ÒÑË¢ÐÂ", Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		
		OnCheckedChangeListener listener=new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					handler2.post(myRunnable);
					switcher.setChecked(true);
				}else
				{
					handler2.removeCallbacks(myRunnable);
					switcher.setChecked(false);
				}
			}
		};
		switcher.setOnCheckedChangeListener(listener);
	}
  public void querydata()
  {
	  
		String url="http://api.btc38.com/v1/ticker.php?c=all&mk_type=cny";
		
		httpjson jsonTread= new httpjson(url,listView,adapter,handler,dbHelper,pre,editor) ;
		jsonTread.start();
  }
}
