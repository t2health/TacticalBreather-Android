/**
 * 
 */
package t2.phongap.plugin.flurry;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.flurry.android.FlurryAgent;
import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;
import com.phonegap.api.PluginResult.Status;

/**
 * @author chris.allen
 *
 */
public class FlurryPlugin extends Plugin {

	/* (non-Javadoc)
	 * @see com.phonegap.api.Plugin#execute(java.lang.String, org.json.JSONArray, java.lang.String)
	 */
	@Override
	public PluginResult execute(String action, JSONArray callingData, String callbackId) {
		//Log.d("FlurryPlugin", "FlurryPlugin:execute called with action: " + action + " and callingData: " + callingData);
		// TODO: here's where we do stuff, then return our status below.  (don't forget to nuke log.d when done.)
		// all actions get sent to .execute, and we'll need to determine which using a switch statement (or something).
		
		try
		{
			if(callingData.getString(0).equals("intro")) {
				FlurryAgent.onEvent("intro");
			}
			
			if(callingData.getString(0).equals("tutorial")) {
				FlurryAgent.onEvent("tutorial");
			}
			
			if(callingData.getString(0).equals("breathe")) {
				FlurryAgent.onEvent("breathe");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new PluginResult(Status.OK, (String)null);
	}

}
