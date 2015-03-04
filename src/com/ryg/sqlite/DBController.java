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
		String insertSql = "insert into NeedInfo (TitleName, TaskRequirement, Heavy, OtherWelfare, TaskAddress, Stars, Charges, IsRealname, ImageName) "
				+ "values('搜秀城购物广场', "
				+ "'20公里，环街骑行，提供衣物旗帜，自备车辆。提供热水，洗浴等',"
				+ "0,"
				+ "'提供午餐',"
				+ "'北京市朝阳区三丰北里',"
				+ "3,"
				+ "300,"
				+ "1,"
				+ "'hehe')";
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
	            int length = 10;
	            while(cur.moveToNext())
	            {
		            for(int i=0; i < length; i++)
		            {
		            	 list.add(cur.getString(i)); 
		            }
//	                list.add(cur.getString(0));  //username
//	                list.add(cur.getString(1));  //password 
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
