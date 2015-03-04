package com.ryg.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper {
	private final static String DB_NAME ="test.db";//数据库名
	private final static int VERSION = 1;//版本号

	 //自带的构造方法
	 public DBHelper(Context context, String name, CursorFactory factory,
	   int version) 
	 {
		 super(context, name, factory, version);
	 }
	 //版本变更时
	 public DBHelper(Context cxt, int version) 
	 {
		 this(cxt,DB_NAME, null, version);
	 }
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createSql = "Create Table NeedInfo ("+
				"id integer primary key autoincrement, name varchar(20)" + ")";
		db.execSQL(createSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
