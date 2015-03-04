package com.ryg.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBController {
	private DBHelper helper = null;
	public DBController(Context cxt, int version)
	{
		helper = new DBHelper(cxt, version);
	}
	public void insert()
	{
		String insertSql = "insert into NeedInfo (name) values('huhuhu')";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(insertSql);
	}
	 public List<String> query()
	 {
	        List<String> list=new ArrayList<String>();
	        SQLiteDatabase db = helper.getWritableDatabase();
	        try
	        {
	            String sql="select * from NeedInfo";
	            Cursor cur=db.rawQuery(sql, new String[]{});
	            while(cur.moveToNext())
	            {
	                list.add(cur.getString(0));  //username
	                list.add(cur.getString(1));  //password 
	            }
	            cur.close();
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	        return list;        
	    }
}
