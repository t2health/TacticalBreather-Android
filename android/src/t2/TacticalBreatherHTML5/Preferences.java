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
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;


public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener{


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);

	}

	@Override
	protected void onResume() 
	{
		super.onResume();

		// Get notified when a pref changes it's value            
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	public void setPreferences(String key, String value)
	{
		SharedPreferences settings = getSharedPreferences("TBPreferences", MODE_PRIVATE); 
		SharedPreferences.Editor prefEditor = settings.edit();
		prefEditor.putString(key, value);
		prefEditor.commit();

	}

	public String getPreferences(String key)
	{

		SharedPreferences settings = getSharedPreferences("TBPreferences", MODE_PRIVATE);
		if (key.equalsIgnoreCase("voice"))
			return settings.getString(key, "Female");
		else if (key.equalsIgnoreCase("muted"))
			return settings.getString(key, "no");
		else if (key.equalsIgnoreCase("vibration"))
			return settings.getString(key, "no");
		else if (key.equalsIgnoreCase("flurry"))
			return settings.getString(key, "yes");
		else if (key.equalsIgnoreCase("eula"))
			return settings.getString(key, "no");
		else return "";//
	}


	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) 
	{

	}

}
