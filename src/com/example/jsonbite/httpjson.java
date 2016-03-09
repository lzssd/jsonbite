package com.example.jsonbite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.text.format.Time;
import android.util.Log;
import android.widget.ListView;

public class httpjson extends Thread {

	
	private String url;
	private Context context;
	private ListView listView;
	private jsonAapter adapter;
	private Handler handler;
	private MyDatabaseHelper db;
	SharedPreferences pre;
	SharedPreferences.Editor editor;
	
	public httpjson(String url,ListView listview,jsonAapter adapter,Handler handler,MyDatabaseHelper db,SharedPreferences pre,SharedPreferences.Editor editor)
	{
		this.url=url;
		this.listView=listview;
		this.adapter=adapter;
		this.handler=handler;
		this.db=db;
		this.pre=pre;
		this.editor=editor;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stu
		Log.d("tag","run");
		Looper.prepare();
		URL httpUrl;
		try {
			httpUrl=new URL(url);
			HttpURLConnection conn=(HttpURLConnection)httpUrl.openConnection();
			conn.setRequestProperty("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22");
			conn.setRequestProperty("Content-Type", "text/html");
			conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
//			Log.d("tag","conn config");
			InputStreamReader is=new InputStreamReader(conn.getInputStream());
//			Log.d("tag","is");
	     	BufferedReader reader=new BufferedReader(is);
//	     	Log.d("tag","reader");
			StringBuffer sb =new StringBuffer();
//					Log.d("tag","get reader");
					String str;
					while((str=reader.readLine())!=null)
					{
						sb.append(str);
					}
					Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。  
					t.setToNow(); 
//					Log.d("tag",t.format2445()+sb.toString());
					final List<money> data=parseJson(sb.toString());
					
					
					
					SQLiteDatabase dbw=db.getWritableDatabase();
					ContentValues values=new ContentValues();
					Iterator<money> iter=data.iterator();
					while (iter.hasNext()) {
						money a=(money)iter.next();
						values.put("name", a.getName());
						values.put("price", a.getLast());
						values.put("vol", a.getVolume());
						values.put("creattime", t.format2445());
						dbw.insert("Coin", null, values);
						values.clear();
//						Log.d("tag",t.format2445()+"增加数据");
					}
						
					
					handler.post(new Runnable()
					{
					 @Override
					 public void run()
					 {
						 adapter.setData(data);
						 listView.setAdapter(adapter);					 }
					});
					
		} catch (Exception e) {
			Log.d("tag","exception");// TODO: handle exception
			e.printStackTrace();
		}
		Looper.loop();
	  }
	
	private List<money> parseJson(String json)
	{
		try {
			List<money> moneys=new ArrayList<money>();
			JSONObject object=new JSONObject(json);
		
			       
					moneys.add(getmoney(object,"btc"));
                    moneys.add(getmoney(object,"ltc"));
				    moneys.add(getmoney(object,"doge"));
		            moneys.add(getmoney(object,"anc"));		
                    moneys.add(getmoney(object,"xpm"));	
                    moneys.add(getmoney(object,"bec"));
                    moneys.add(getmoney(object,"src"));
                    moneys.add(getmoney(object,"mec"));
                    moneys.add(getmoney(object,"bts"));
                    moneys.add(getmoney(object,"blk"));
                    moneys.add(getmoney(object,"xem"));
                    moneys.add(getmoney(object,"ppc"));
//			Log.d("tag","return money");
			return moneys;
		
		} catch (Exception e) {
			Log.d("tag","json object exception");
			e.printStackTrace();// TODO: handle exception
		}
		return null;
	}

	public money getmoney(JSONObject object,String name) throws JSONException
	{
		JSONObject Name=object.getJSONObject(name);
		JSONObject ticker=Name.getJSONObject("ticker");
	       Double high=ticker.getDouble("high");
	       Double low=ticker.getDouble("low");
	       Double last=ticker.getDouble("last");
	       Double vol=ticker.getDouble("vol");
	       Double buy=ticker.getDouble("buy");
	       Double sell=ticker.getDouble("sell");
	       boolean flag=false;
	       if (pre.getString(name, null)!=null) {
	    	   Log.d("tag","last"+last);  
	    	   Log.d("tag","pre"+pre.getString(name, null));
			 flag=(last>Double.parseDouble(pre.getString(name, null)));
			 Log.d("tag","flag"+flag);
		}
	       
	       money moneyobject=new money(name,last,high,low,vol,buy,sell,flag);
	       
	       editor.putString(name, last.toString());
	       editor.commit();
	       return moneyobject;
	}

}
