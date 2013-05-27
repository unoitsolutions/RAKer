package com.codemagnus.citer.db;

import android.provider.BaseColumns;

public class CiterContract {

	public static final String DATABASE_NAME = "citerdb";
	public static final int DATABASE_VERSION = 1;

	private CiterContract() {
	}
	
	public static abstract class Quote implements BaseColumns {
		public static final String TABLE_NAME = "quote";
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_QUOTE = "quote";
		public static final String COLUMN_NAME_LIKES = "likes";

		public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
				+ COLUMN_NAME_ID	+ " INTEGER PRIMARY KEY, "
				+ COLUMN_NAME_QUOTE + " TEXT, "
				+ COLUMN_NAME_LIKES + " INTEGER)";
	}

}
