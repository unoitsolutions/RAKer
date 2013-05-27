package com.codemagnus.citer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	public static final String TAG = "SettingsActivity";
	private static final String PLAYSTORE_URL = "https://play.google.com/store/apps/details?id=com.codemagnus.citer";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		int color = getIntent().getExtras().getInt("color");
		((LinearLayout) findViewById(R.id.main)).setBackgroundResource(color);
		
	}

	public void facebook(View v) {
		List<Intent> intents = new ArrayList<Intent>();
	    Intent share = new Intent(android.content.Intent.ACTION_SEND);
	    share.setType("text/plain");
	    share.putExtra(Intent.EXTRA_TEXT, PLAYSTORE_URL);
	    List<ResolveInfo> res = getPackageManager().queryIntentActivities(share, 0);
	    if (!res.isEmpty()){
	        for (ResolveInfo info : res) {
	            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
	            intent.setType("text/plain");
	            if (info.activityInfo.packageName.toLowerCase(Locale.getDefault()).contains("facebook") ||
	            	info.activityInfo.name.toLowerCase(Locale.getDefault()).contains("facebook")) {
	            	intent.putExtra(Intent.EXTRA_TEXT, PLAYSTORE_URL);
	            	intent.setPackage(info.activityInfo.packageName);
	            	intents.add(intent);
	            }

	        }
	        if (!intents.isEmpty()) {
		        Intent chooser = Intent.createChooser(intents.remove(0), "Select app to share");
		        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toArray(new Parcelable[]{}));
		        startActivity(chooser);
	        } else {
	        	Toast.makeText(this, "Facebook application not installed.", Toast.LENGTH_SHORT).show();
	        }
	    } else {
	    	Toast.makeText(this, "Facebook application not installed.", Toast.LENGTH_SHORT).show();
	    }
	}

	public void twitter(View v) {
		List<Intent> intents = new ArrayList<Intent>();
	    Intent share = new Intent(android.content.Intent.ACTION_SEND);
	    share.setType("text/plain");
	    share.putExtra(Intent.EXTRA_TEXT,PLAYSTORE_URL);
	    List<ResolveInfo> res = getPackageManager().queryIntentActivities(share, 0);
	    if (!res.isEmpty()){
	        for (ResolveInfo info : res) {
	            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
	            intent.setType("text/plain");
	            if (info.activityInfo.packageName.toLowerCase(Locale.getDefault()).contains("twitter") ||
	            	info.activityInfo.name.toLowerCase(Locale.getDefault()).contains("twitter")) {
	            	intent.putExtra(Intent.EXTRA_TEXT, PLAYSTORE_URL);
	            	intent.setPackage(info.activityInfo.packageName);
	            	intents.add(intent);
	            }

	        }
	        if (!intents.isEmpty()) {
		        Intent chooser = Intent.createChooser(intents.remove(0), "Select app to share");
		        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toArray(new Parcelable[]{}));
		        startActivity(chooser);
	        } else {
	        	Toast.makeText(this, "Twitter application not installed.", Toast.LENGTH_SHORT).show();
	        }
	    } else {
	    	Toast.makeText(this, "Twitter application not installed.", Toast.LENGTH_SHORT).show();
	    }
	}

	public void feedback(View v) {
		Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"unoitsolutions@gmail.com"});
		intent.putExtra(Intent.EXTRA_SUBJECT, "RAKer: Feedback");
		intent.setType("message/rfc822");
		this.startActivity(Intent.createChooser(intent, "Choose an Email client :"));
	}

}
