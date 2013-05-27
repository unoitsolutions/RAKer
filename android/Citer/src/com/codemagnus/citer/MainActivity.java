package com.codemagnus.citer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codemagnus.citer.db.Quote;
import com.codemagnus.citer.db.QuoteDataSource;

public class MainActivity extends Activity {

	public static final String TAG = "MainActivity";

	private QuoteDataSource datasource;
	private List<Quote> quotes;
	private Quote quote;

	LinearLayout main;

	int color;
	int[] colors;

	private static final int MIN_FORCE = 5;
	private SensorManager mSensorManager;
	private float mAccel;
	private float mAccelCurrent;
	private float mAccelLast;

	ViewPager viewPager;
	QuotePagerAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		colors = new int[] { R.color.color1, R.color.color2, R.color.color3,
				R.color.color4, R.color.color5, R.color.color6, R.color.color7,
				R.color.color8, R.color.color9, R.color.color10,
				R.color.color11, R.color.color12, R.color.color13,
				R.color.color14, R.color.color15, R.color.color16,
				R.color.color17, R.color.color18, R.color.color19,
				R.color.color20, R.color.color21, R.color.color22,
				R.color.color23, R.color.color24 };

		datasource = new QuoteDataSource(this);
		datasource.open();

		quotes = datasource.getAllQuotes();
		long seed = System.nanoTime();
		Collections.shuffle(quotes, new Random(seed));

		main = (LinearLayout) findViewById(R.id.main);

		viewPager = (ViewPager) findViewById(R.id.view_pager);
		adapter = new QuotePagerAdapter();
		viewPager.setAdapter(adapter);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		mAccel = 0.00f;
		mAccelCurrent = SensorManager.GRAVITY_EARTH;
		mAccelLast = SensorManager.GRAVITY_EARTH;

	}

	public void settings(View v) {
		Intent i = new Intent(this, SettingsActivity.class);
		i.putExtra("color", color);
		this.startActivity(i);
	}
	

	public void share(View v) {
		final CharSequence[] items = { "Facebook", "Twitter" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Share");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int position) {
				switch (position) {
				case 0:
					facebook();
					break;
				case 1:
					twitter();
					break;
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void facebook() {
		viewPager.buildDrawingCache();
		Bitmap bitmap = viewPager.getDrawingCache();
		
		try {
			File file = new File(Environment.getExternalStorageDirectory().getPath() + "/raker-attachment.jpg");
			file.delete();
			final FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 90, fos);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		List<Intent> intents = new ArrayList<Intent>();
		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("image/jpeg");
		List<ResolveInfo> res = getPackageManager().queryIntentActivities(
				share, 0);
		if (!res.isEmpty()) {
			for (ResolveInfo info : res) {
				Intent intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("image/jpeg");

				Log.d(TAG, info.activityInfo.packageName.toLowerCase(Locale
						.getDefault()));
				Log.d(TAG,
						info.activityInfo.name.toLowerCase(Locale.getDefault()));

				if (info.activityInfo.packageName.toLowerCase(
						Locale.getDefault()).contains("facebook")
						|| info.activityInfo.name.toLowerCase(
								Locale.getDefault()).contains("facebook")) {
					intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory()
	    					.getPath() + "/raker-attachment.jpg")) );
					intent.setPackage(info.activityInfo.packageName);
					intents.add(intent);
				}

			}
			if (!intents.isEmpty()) {
				Intent chooser = Intent.createChooser(intents.remove(0),
						"Select app to share");
				chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,
						intents.toArray(new Parcelable[] {}));
				startActivity(chooser);
			} else {
				Toast.makeText(this, "Facebook application not installed.",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "Facebook application not installed.",
					Toast.LENGTH_SHORT).show();
		}
		
	}

	private void twitter() {
		viewPager.buildDrawingCache();
		Bitmap bitmap = viewPager.getDrawingCache();
		
		try {
			File file = new File(Environment.getExternalStorageDirectory().getPath() + "/raker-attachment.jpg");
			file.delete();
			final FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 90, fos);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		List<Intent> intents = new ArrayList<Intent>();
		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("image/jpeg");
		List<ResolveInfo> res = getPackageManager().queryIntentActivities(
				share, 0);
		if (!res.isEmpty()) {
			for (ResolveInfo info : res) {
				Intent intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("image/jpeg");
				if (info.activityInfo.packageName.toLowerCase(
						Locale.getDefault()).contains("twitter")
						|| info.activityInfo.name.toLowerCase(
								Locale.getDefault()).contains("twitter")) {
					intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory()
	    					.getPath() + "/raker-attachment.jpg")) );
					intent.setPackage(info.activityInfo.packageName);
					intents.add(intent);
				}

			}
			if (!intents.isEmpty()) {
				Intent chooser = Intent.createChooser(intents.remove(0),
						"Select app to share");
				chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,
						intents.toArray(new Parcelable[] {}));
				startActivity(chooser);
			} else {
				Toast.makeText(this, "Twitter application not installed.",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "Twitter application not installed.",
					Toast.LENGTH_SHORT).show();
		}
	}

	private final SensorEventListener mSensorListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent se) {
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			mAccelLast = mAccelCurrent;
			mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
			float delta = mAccelCurrent - mAccelLast;
			mAccel = mAccel * 0.9f + delta;

			if (mAccel > MIN_FORCE) {
				
				int current = viewPager.getCurrentItem() + 1;
				if (current < viewPager.getChildCount());
					viewPager.setCurrentItem(current);
			}

		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

	};

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mSensorListener);
		super.onPause();
	}

	private class QuotePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return quotes.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((TextView) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Context context = MainActivity.this;
			TextView textView = new TextView(context);
			textView.setTextAppearance(MainActivity.this, R.style.quote);
			textView.setGravity(Gravity.CENTER);

			quote = quotes.get(position);
			color = colors[new Random().nextInt(colors.length)];

			container.setBackgroundResource(color);
			main.setBackgroundResource(color);
			
			textView.setText(quote.getQuote());

			((ViewPager) container).addView(textView, 0);
			return textView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((TextView) object);
		}
	}

}
