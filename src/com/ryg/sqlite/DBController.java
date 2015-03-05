package com.ryg.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ryg.model.Task;

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
		String insertSql = "insert into NeedInfo (TitleName, TaskRequirement, Heavy, OtherWelfare, TaskAddress, Stars, Charges, IsRealname, ImageName, start, end) "
				+ "values('搜秀城购物广场', "
				+ "'20公里，环街骑行，提供衣物旗帜，自备车辆。提供热水，洗浴等',"
				+ "0,"
				+ "'提供午餐',"
				+ "'北京市朝阳区三丰北里',"
				+ "3,"
				+ "300,"
				+ "1,"
				+ "'hehe',"
				+ "0,"
				+ "1)";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(insertSql);
	}
	/**
	 * 
	 * @param TitleName 标题
	 * @param TaskRequirement 任务需求
	 * @param Heavy 负重
	 * @param OtherWelfare 其他福利
	 * @param TaskAddress 任务地址
	 * @param Stars 星星数
	 * @param Charges 佣金
	 * @param IsRealname 是否实名认证
	 * @param ImageName logo图片名称
	 * @param start 标红开始位置
	 * @param end 标红结束位置
	 */
	public void insert(String TitleName,String TaskRequirement,String Heavy,String OtherWelfare,String TaskAddress,String  Stars, String Charges, String IsRealname, String ImageName, String start, String end)
	{
		String insertSql = "insert into NeedInfo (TitleName, TaskRequirement, Heavy, OtherWelfare, TaskAddress, Stars, Charges, IsRealname, ImageName, start, end) "
				+ "values('"+TitleName+"', "
				+ "'"+TaskRequirement+"',"
				+ Heavy +","
				+ "'"+OtherWelfare+"',"
				+ "'"+TaskAddress+"',"
				+ Stars+","
				+ Charges+","
				+ IsRealname+","
				+ "'"+ImageName+"',"
				+ start + ","
				+ end + ")";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(insertSql);
	}
	 public List<HashMap<String, String>> queryTask()
	 {
	        List<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();
	        SQLiteDatabase db = helper.getWritableDatabase();
	        try
	        {
	            String sql="select * from NeedInfo";
	            Cursor cur=db.rawQuery(sql, new String[]{});
	            int length = 12;
	            while(cur.moveToNext())
	            {
	            	HashMap<String, String> map = new HashMap<String, String>();
		            for(int i=0; i < length; i++)
		            {
		            	map.put(cur.getColumnName(i), cur.getString(i));
		            }
		            list.add(map);
	            }
	            cur.close();
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	        return list;        
	 }
	 public void execSql(String SQL)
	 {
		 SQLiteDatabase db = helper.getWritableDatabase();
		 db.execSQL(SQL);
	 }
}
