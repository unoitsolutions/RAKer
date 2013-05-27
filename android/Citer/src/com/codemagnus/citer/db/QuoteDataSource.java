package com.codemagnus.citer.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class QuoteDataSource {

	private SQLiteDatabase database;
	private DBHelper dbHelper;

	private String[] allColumns = { CiterContract.Quote.COLUMN_NAME_ID,
			CiterContract.Quote.COLUMN_NAME_QUOTE,
			CiterContract.Quote.COLUMN_NAME_LIKES };

	public QuoteDataSource(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public List<Quote> getAllQuotes() {
		List<Quote> quotes = new ArrayList<Quote>();

		Cursor cursor = database.query(CiterContract.Quote.TABLE_NAME, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Quote quote = cursorToQuote(cursor);
			quotes.add(quote);
			cursor.moveToNext();
		}
		cursor.close();
		return quotes;
	}

	
	public int updateQuote(Quote quote) {
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(CiterContract.Quote.COLUMN_NAME_LIKES, quote.getLikes() + 1);
	    
	    return db.update(CiterContract.Quote.TABLE_NAME, values, CiterContract.Quote.COLUMN_NAME_ID + " = ?",
	            new String [] { String.valueOf(quote.getId()) });
	}
	
	private Quote cursorToQuote(Cursor cursor) {
		Quote quote = new Quote();
		quote.setId(cursor.getInt(0));
		quote.setQuote(cursor.getString(1));
		quote.setLikes(cursor.getInt(2));
		return quote;
	}

}
