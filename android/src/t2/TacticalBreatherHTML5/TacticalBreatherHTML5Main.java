/*
 * 
 * Tactical Breather
 * 
 * Copyright © 2009-2012 United States Government as represented by 
 * the Chief Information Officer of the National Center for Telehealth 
 * and Technology. All Rights Reserved.
 * 
 * Copyright © 2009-2012 Contributors. All Rights Reserved. 
 * 
 * THIS OPEN SOURCE AGREEMENT ("AGREEMENT") DEFINES THE RIGHTS OF USE, 
 * REPRODUCTION, DISTRIBUTION, MODIFICATION AND REDISTRIBUTION OF CERTAIN 
 * COMPUTER SOFTWARE ORIGINALLY RELEASED BY THE UNITED STATES GOVERNMENT 
 * AS REPRESENTED BY THE GOVERNMENT AGENCY LISTED BELOW ("GOVERNMENT AGENCY"). 
 * THE UNITED STATES GOVERNMENT, AS REPRESENTED BY GOVERNMENT AGENCY, IS AN 
 * INTENDED THIRD-PARTY BENEFICIARY OF ALL SUBSEQUENT DISTRIBUTIONS OR 
 * REDISTRIBUTIONS OF THE SUBJECT SOFTWARE. ANYONE WHO USES, REPRODUCES, 
 * DISTRIBUTES, MODIFIES OR REDISTRIBUTES THE SUBJECT SOFTWARE, AS DEFINED 
 * HEREIN, OR ANY PART THEREOF, IS, BY THAT ACTION, ACCEPTING IN FULL THE 
 * RESPONSIBILITIES AND OBLIGATIONS CONTAINED IN THIS AGREEMENT.
 * 
 * Government Agency: The National Center for Telehealth and Technology
 * Government Agency Original Software Designation: TacticalBreather001
 * Government Agency Original Software Title: TacticalBreather
 * User Registration Requested. Please send email 
 * with your contact information to: robert.kayl2@us.army.mil
 * Government Agency Point of Contact for Original Software: robert.kayl2@us.army.mil
 * 
 */
package t2.TacticalBreatherHTML5;

import t2.tacticalBreather.R;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.phonegap.DroidGap;

public class TacticalBreatherHTML5Main extends DroidGap 
{

	/**
	 * Simple Dialog used to show the splash screen
	 */
	protected Dialog mSplashDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		//Log.v("TacticalBreather", "onCreate begin");
		super.onCreate(savedInstanceState);

		showSplashScreen();
		super.init();

		super.loadUrl("file:///android_asset/www/breathe.html");

		CharSequence text = "Press menu for settings...";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(getBaseContext(), text, duration);
		toast.show();        
	}

	public void RefreshPrefs()
	{
		//Preferences have changed, reload and update breathe.js//
		String voice = "";
		String mute = "";
		String vibrate = "";
		String flurry = "";
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		voice = prefs.getString("listPrefVoice", "female");
		if(voice.equals("male")) voice = "Adam";
		else voice = "Alford";

		mute = prefs.getString("listPrefMute", "no");
		if(mute.equals("yes")) mute = "on";
		else mute = "off";

		vibrate = prefs.getString("listPrefVibrate", "no");
		if(vibrate.equals("yes")) vibrate = "on";
		else vibrate = "off";

		flurry = prefs.getString("listPrefAnonymous", "yes");
		if(flurry.equals("yes")) flurry = "on";
		else flurry = "off";

		sendJavascript("refreshPrefsNative('" + voice + "','" + mute + "','" + vibrate + "','" + flurry + "');");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		menu.getItem(2).setIcon(R.drawable.ic_menu_book);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) {
		case R.id.settings:
			Intent settingsActivity = new Intent(getBaseContext(), Preferences.class);
			startActivity(settingsActivity);			
			break;
		case R.id.about:
			sendJavascript("showAbout();");
			//Log.v("SendJavascript", "about");
			break;
		case R.id.excerpt:
			sendJavascript("showExcerpt();");
			//Log.v("SendJavascript", "excerpt");
			break;
//		case R.id.exit:
//			finish();
		default:
			return super.onOptionsItemSelected(item);

		}

		return true;
	}

	public void onResume() 
	{
		super.onResume();
		try
		{
			RefreshPrefs();
		}
		catch(Exception ex){}
	}
	public void onStart() 
	{
		super.onStart();
		FlurryAgent.onStartSession(this, "8IIPJH5Q7IZ33ZSDT9Z8");
		RefreshPrefs();
	}

	public void onStop() 
	{
		super.onStop();
		FlurryAgent.onEndSession(this);
	}


	/**
	 * Removes the Dialog that displays the splash screen
	 */
	protected void removeSplashScreen() 
	{
		if (mSplashDialog != null) {
			mSplashDialog.dismiss();
			mSplashDialog = null;
		}
	}

	/**
	 * Shows the splash screen over the full Activity
	 */
	protected void showSplashScreen() 
	{
		mSplashDialog = new Dialog(this, R.style.splashstyle);
		mSplashDialog.setContentView(R.layout.splash);
		mSplashDialog.setCancelable(false);
		mSplashDialog.show();

		// Set Runnable to remove splash screen
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				removeSplashScreen();
			}
		}, 3000);
	}

}