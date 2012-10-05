/**
 * Adds a layer of control over PhoneGap's Media object, including the ability
 * to automatically re-acquire media handles that have been released.
 * 
 * @param source
 *            String containing path to source media.
 * @returns boolean indicating successful object creation
 */
var MediaWrapper = function (source, successCallback, errorCallback)
{
    if (!(this instanceof arguments.callee)) {  
        return new MediaWrapper(source, successCallback, errorCallback);  
    }  	// in case caller forgets to use 'new'

	this.source = source;
	this.shortSource = source.match("[^/]*/[^/]*$");
	this.playing = false;
	this.media = null;
	this.successCall = successCallback;
	this.errorCall = errorCallback;
	this.muted = false;
	console.log("MediaWrapper created. [" + this.shortSource + "]");
};

MediaWrapper.prototype.play = function ()
{
	if (!this.isMuted()) // If not muted
	{
		this.playing = true;
		
		// stop any media stopping timers that might be running.  See .stopSafe() for details.
		$(this).stopTime(this.source);
		
		this.acquire();  //ensure we actually have the media handle.
		if (this.media != null)
		{
			console.log("MediaWrapper playing.  [" + this.shortSource + "]");
			this.media.play();
		} else
		{
			console.log("MediaWrapper could not acquire media:  [" + this.shortSource + "]");
		}
	}
};

MediaWrapper.prototype.setMuted = function (muted)
{
	this.muted = muted;
};

MediaWrapper.prototype.isMuted = function ()
{
	return this.muted;
};

MediaWrapper.prototype.pause = function ()
{
	console.log("MediaWrapper pausing. [" + this.shortSource + "]");
	if (this.media && this.playing)
	{
		this.media.pause();
		this.playing = false;
	}
};
MediaWrapper.prototype.togglePause = function ()
{
	console.log("MediaWrapper toggle pause. [" + this.shortSource + "]");
	if (this.media)
	{
		if (this.playing)
		{
			this.pause();
		} else
		{
			this.play();
		}
	} else
	{
		this.play();
	}
};

MediaWrapper.prototype.stop = function ()
{
	console.log("MediaWrapper stopping. [" + this.shortSource + "]");
	if (this.media)
	{
    	this.media.stop();
		this.playing = false;
	}
};
MediaWrapper.prototype.stopSafe = function ()
{
	console.log("MediaWrapper safe stopping. [" + this.shortSource + "]");
	if (this.media)
	{
		/*
		 * Still trying to figure out why, but Darren found some situations where stop doesn't stop if called
		 * very soon after play.  I'm not able to recreate at this time, but we'll leave this "stopSafe" code
		 * around in case someone encounters it again.
		 * This timeout will ensure we actually stop.  Note that we're using the jQuery timer plugin here, so 
		 * it will need to be included.  
		 * Note that play stops this timer, so pressing stop then play within the timeout  will just continue playing.*/
		
		var _this = this;  //get around this pointing to Window inside setTimeout.
		$(this).oneTime(700, this.source ,function ()
	    {
	    	_this.stopUnsafe();
	    });
	}
};

MediaWrapper.prototype.release = function ()
{
	console.log("MediaWrapper releasing resource. [" + this.shortSource + "]");
	if (this.media)
	{
		this.media.stop();
		this.media.release();
		this.media = null;
		this.playing = false;
	}
};
MediaWrapper.prototype.acquire = function ()
{
	// if 'device' has been defined by phonegap, then phonegap is active and we
	// can load the media.
	if (typeof device != 'undefined')
	{
		if (this.media == null) // media not created yet.
		{
			console.log("MediaWrapper loading media. [" + this.shortSource + "]");
			this.media = new Media(this.source, this.mediaCompleted(),
				function (code, message)
				{
					console.log("Error " + code + " (" + message + ") occurred in Media. [" + this.shortSource + "]");
				}
			);
		} else
		{ // media already created.
			// TODO: if media released....
			console.log("MediaWrapper already has media handle. [" + this.shortSource + "]");
		}
	} else
	{
		// TODO: Trying to see if an empty media gives us more control over
		// state.
		// this.media = new Media("");
		console.log("MediaWrapper attempted to load media on a non-mobile device [" + this.shortSource + "]");
	}
};

MediaWrapper.prototype.mediaCompleted = function ()
{
	this.playing = false;
	console.log("Callback reports MediaWrapper completed. [" + this.shortSource + "]");
	if (this.successCall)
	{
		this.successCall();
	}
};

MediaWrapper.prototype.mediaError = function ()
{
	this.playing = false;
	console.error("Callback reports MediaWrapper error occurred. [" + this.shortSource + "]");
	if (this.errorCall)
	{
		this.errorCall();
	}
};