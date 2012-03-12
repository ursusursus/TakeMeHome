package com.ursus.takemehome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static String databaseName = "database";
	public static String tableName = "stops";
	public static String field1 = "Name";
	public static String field2 = "Price";
	private Context context;

	public DatabaseHelper(Context context) {
		super(context, databaseName, null, 1);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public String select(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		Cursor c = db.query(tableName, new String[] {"bus","stop"}, null, null, null, null, null);
		
		c.moveToFirst();
		if (!(c.isAfterLast())) {
			do {
				sb.append(c.getString(0));
				sb.append(" ");
				sb.append(c.getString(1));
				sb.append("\n");
			} while (c.moveToNext());
		}
		c.close();
		//System.out.println(sb.toString());
		return sb.toString();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

}
