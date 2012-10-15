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
