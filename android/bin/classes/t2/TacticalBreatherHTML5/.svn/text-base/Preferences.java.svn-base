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
