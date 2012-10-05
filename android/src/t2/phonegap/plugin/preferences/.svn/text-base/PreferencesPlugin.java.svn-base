package t2.phonegap.plugin.preferences;

import org.json.JSONArray;
import org.json.JSONException;

import t2.TacticalBreatherHTML5.Preferences;

import android.util.Log;

import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;
import com.phonegap.api.PluginResult.Status;

public class PreferencesPlugin extends Plugin{

	
	@Override
	public PluginResult execute(String action, JSONArray callingData, String callbackId) {
		//Log.v("PreferencesPlugin", "PreferencesPlugin:execute called with action: " + action + " and callingData: " + callingData);
		// TODO: here's where we do stuff, then return our status below.  (don't forget to nuke log.d when done.)
		// all actions get sent to .execute, and we'll need to determine which using a switch statement (or something).
		
		if (action.equals("preferences"))
		{
			try
			{
				Preferences p = new Preferences();
				if(callingData.getString(0).equals("voice")) {
					p.setPreferences("voice", callbackId);
				}
				
				if(callingData.getString(0).equals("muted")) {
					p.setPreferences("muted", callbackId);
				}
				
				if(callingData.getString(0).equals("vibrate")) {
					p.setPreferences("vibrate", callbackId);
				}
				
				if(callingData.getString(0).equals("flurry")) {
					p.setPreferences("flurry", callbackId);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new PluginResult(Status.OK, (String)null);
		}
		return new PluginResult(Status.NO_RESULT, (String)null);
	}

}
