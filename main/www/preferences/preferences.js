/**
 * 
 * 
 * All actions here get sent to FlurryPlugin.execute and pass the action name.
 * 
 * @return Instance of FlurryPlugin
 */
var PreferencesPlugin = function () {
};

/**
 * @param directory        The directory for which we want the listing
 * @param successCallback  The callback which will be called on successful completion
 * @param failureCallback  The callback which will be called on error
 */
PreferencesPlugin.prototype.logEvent = function(event, successCallback, failureCallback)
{
	return PhoneGap.exec(successCallback, failureCallback, 'PreferencesPlugin', 'preferences', [event]);
};

/**
 * <ul>
 * <li>Register the Preferences Plugin</li>
 * <li>Also register native call which will be called when this plugin runs</li>
 * </ul>
 */
PhoneGap.addConstructor(function()
{
	// Register the javascript plugin with PhoneGap
	PhoneGap.addPlugin('preferences', new PreferencesPlugin());

	// Register the native class of plugin with PhoneGap
	PluginManager.addService("PreferencesPlugin", "t2.phonegap.plugin.preferences.PreferencesPlugin");
});

preferences = function (tag)
{
	try
	{
		window.plugins.preferences.logEvent(
			tag,
			function() {
				console.log("Callback reports preferences event recorded: " + tag);
			}, 
			function(error) {
				console.log("Callback reports preferences event FAILED: " + tag);
			}
		);
	}
	catch (err)
	{
		alert("error message =" + err.message);
		console.log ("Error calling preferences");
	}
};