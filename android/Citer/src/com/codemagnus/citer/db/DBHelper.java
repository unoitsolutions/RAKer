package com.codemagnus.citer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private static String TAG = "DatabaseDbHelper";
	Context context;

	public DBHelper(Context context) {
		super(context, CiterContract.DATABASE_NAME, null, CiterContract.DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CiterContract.Quote.CREATE_TABLE);
		Log.d(TAG, "Database successfully built.");
		new QuotesInitializer(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + CiterContract.Quote.TABLE_NAME);
		onCreate(db);
	}

}
