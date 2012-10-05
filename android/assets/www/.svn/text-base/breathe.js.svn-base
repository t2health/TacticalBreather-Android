var audio_intro = null;
var audio_inhale = null;
var audio_hold = null;
var audio_exhale = null;
var audio_tutorial = null;

var pausedByLifecycleChange = false;

var StateEnum = {"NONE": 0, "INTRO": 1, "TUTORIAL": 2, "BREATHE": 3};
var state = StateEnum.NONE;

var voice = "Alford";
var muted = "off";
var vibrate = "off";
var flurry = "on";

//function variable declarations 
var onDeviceReady, onApplicationPause;
var buttonPress, buttonPressIntro, buttonPressTutorial, buttonPressBreathe, buttonPressMute, buttonPressUnmute;
var changeState, runIntro, runTutorial, runBreathe;
var updateMedia, destroyAllMedia, releaseAllMedia;
var showAbout, showExcerpt;
var logAnalytics;

$(document).ready(function () 
{
	//console.log("Document Ready");
	document.addEventListener("deviceready", onDeviceReady, false);

	// make the footer float up onto the page.
	$("#footer").css("bottom", "0px");

	$("#speaker_unmuted").hide();
	$("#speaker_muted").hide();

	// register event listeners (touchstart is faster to respond than click)
	//TODO: should detect environment and use click when not on device.
	$(document).delegate("#introButton", "touchstart", buttonPressIntro);
	//$("#introButton").live('touchstart', {targetState: StateEnum.INTRO}, buttonPress);
	$(document).delegate("#tutorialButton", "touchstart", buttonPressTutorial);
	//$("#tutorialButton").live('touchstart', runTutorial);
	$(document).delegate("#breatheButton", "touchstart", buttonPressBreathe);
	//$("#breatheButton").live('touchstart', runBreathe);

	$(document).delegate("#speaker_muted", "touchstart", buttonPressUnmute);
	
	$(document).delegate("#speaker_unmuted", "touchstart", buttonPressMute);
		
	updateMedia();
	
});

refreshPrefsNative = function (inVoice, inMute, inVibrate, inFlurry )
{
	voice = inVoice; 
		
	muted = inMute; 
	
	vibrate = inVibrate; 
		
	flurry = inFlurry;
	changeState(StateEnum.NONE);
};

/*
 * Temporary until I can get passing parameters from delegate or live functions.
 */
buttonPressIntro = function ()
{
	buttonPress(StateEnum.INTRO);
};
buttonPressTutorial = function ()
{
	buttonPress(StateEnum.TUTORIAL);
};
buttonPressBreathe = function ()
{
	//$('#buttonBreathe').attr("disabled", true);
	buttonPress(StateEnum.BREATHE);
	//$('#buttonBreathe').attr("disabled", false);	
};

buttonPressMute = function ()
{
	$("#speaker_unmuted").hide();
	$("#speaker_muted").show();
	muted = "on";
	if (audio_inhale)
	{
		audio_inhale.stop();
	}
	if (audio_hold)
	{
		audio_hold.stop();
	}
	if (audio_exhale)
	{
		audio_exhale.stop();
	}
};

buttonPressUnmute = function ()
{
	$("#speaker_unmuted").show();
	$("#speaker_muted").hide();
	muted = "off";
};

buttonPress = function (targetState)
{
	//console.log("Button press: " + targetState);
	// if we press the button for the state we're in, we really want to stop.
	if (targetState == state)
	{
		targetState = StateEnum.NONE;
	}
	changeState(targetState);
};

changeState = function (newState)
{
	var oldState = state;
	state = newState;
	
	//console.log("State change, " + oldState + " to " + state);

	//clean up from the old state
	switch (oldState)
	{
	case StateEnum.NONE:
		break;
	case StateEnum.INTRO:
		if (audio_intro != null)
		{
			audio_intro.stop();
		}
		$(this).stopTime("intro");
		$("#introButton").button().text("Intro");
		$("#counter").text("");
		break;
	case StateEnum.TUTORIAL:
		if (audio_tutorial != null)
		{
			audio_tutorial.stop();
		}
		$(this).stopTime("tutorial");
		$("#tutorialButton").button().text("Tutorial");
	    $("#instruction").text("");
	    $("#counter").text("");
	    $("#orb_green").hide();
	    $("#orb_yellow_full").hide();
	    $("#orb_red").hide();
	    $("#orb_yellow_empty").hide();
	    $("#lungs").css("opacity", "1");
		break;
	case StateEnum.BREATHE:
		if (audio_inhale)
		{
			audio_inhale.stop();
		}
		if (audio_hold)
		{
			audio_hold.stop();
		}
		if (audio_exhale)
		{
			audio_exhale.stop();
		}
		$(this).stopTime("breathe");
		$("#breatheButton").button().text("Breathe");
	    $("#instruction").text("");
	    $("#counter").text("");
	    $("#orb_green").hide();
	    $("#orb_yellow_full").hide();
	    $("#orb_red").hide();
	    $("#orb_yellow_empty").hide();
	    $("#speaker_unmuted").hide();
		$("#speaker_muted").hide();
		$("#lungs").css("opacity", "1");
		break;
	}
	
	switch (state)
	{
	case StateEnum.NONE:
		$("#lungs").css("opacity", "1");
		break;
	case StateEnum.INTRO:
		$("#introButton").button().text("Stop");
		runIntro();
		break;
	case StateEnum.TUTORIAL:
		$("#tutorialButton").button().text("Stop");
		runTutorial();
		break;
	case StateEnum.BREATHE:
		$("#breatheButton").button().text("Stop");
		runBreathe();
		break;	
	}

};


runIntro = function ()
{
	//console.log("Intro button pressed.");
	logAnalytics("intro");
	
	$("#counter").text("Intro");
	
	// load media if not already loaded.
	if (audio_intro == null) 
	{
		audio_intro = new MediaWrapper("/android_asset/www/audio/" + voice + "/intro1.mp3");
	}

	audio_intro.play();
	
	// set a timer to reset our state and button when audio is completed.
	var duration;
	if (voice === "Alford")
	{
		duration = "57s";
	} else {
		duration = "68s";
	}
	$(this).oneTime(duration, "intro", function () 
    {
		$("#counter").text("");
		changeState(StateEnum.NONE);
    });
		
};
	
runTutorial = function ()
{
	//console.log("Tutorial button pressed.");
	logAnalytics("tutorial");
	
	$("#counter").text("Tutorial");
	
	// load media if not already loaded.
	if (audio_tutorial == null) 
	{
		audio_tutorial = new MediaWrapper("/android_asset/www/audio/" + voice + "/intro2.mp3");
	}
	
	//TODO: worried about this variable getting out of sync with buttons... need to think about it.
	var counter = 0;
	$(this).everyTime(1000, "tutorial", function () 
	{
		// Play audio and show the green circle at the appropriate time
		switch (counter)
		{
		//Steveo: added a two second time delay for Adam's voiceover
		case 0:
			if (voice === "Alford")
			{
				if (audio_tutorial) {
					audio_tutorial.play();
				}
			}
			break;
		case 2:
			if (voice === "Adam")
			{
				if (audio_tutorial) {
					audio_tutorial.play();
				}
			}
			//console.log("Playing new audio:" + audio_tutorial.source);
			break;
		case 10:
			$("#counter").text("");
			$("#lungs").css("opacity", "0"); // fade out lungs image
			break;
		case 12:
			
			$("#orb_green").replaceWith($("#orb_green").clone(true));  //resets animation
			$("#orb_green").show();
			$("#orb_green").css("webkitAnimationPlayState", "running");
			break;
		case 13:
			$("#counter").text(1);
			break;
		case 14:
			$("#counter").text(2);
			break;
		case 15:
			$("#counter").text(3);
			break;
		case 16:
			$("#counter").text(4);
			break;
		case 17:
			$("#orb_yellow_full").replaceWith($("#orb_yellow_full").clone(true)); //resets animation
			$("#orb_yellow_full").show();
			$("#orb_yellow_full").css("webkitAnimationPlayState", "running");
			$("#counter").text(1);
			break;
		case 18:
			$("#counter").text(2);
			break;
		case 19:
			$("#counter").text(3);
			break;
		case 20:
			$("#counter").text(4);
			break;
		case 21:
			$("#orb_red").replaceWith($("#orb_red").clone(true)); //resets animation
			$("#orb_red").show();
			$("#orb_red").css("webkitAnimationPlayState", "running");
			break;
		case 22:
			$("#counter").text(1);
			break;
		case 23:
			$("#counter").text(2);
			break;
		case 24:
			
			$("#counter").text(3);
			break;
		case 25:
			$("#counter").text(4);
			break;
		case 26:
			$("#orb_yellow_empty").replaceWith($("#orb_yellow_empty").clone(true)); //resets animation
			$("#orb_yellow_empty").show();
			$("#orb_yellow_empty").css("webkitAnimationPlayState", "running");
			$("#counter").text(1);
			break;
		case 27:
			$("#counter").text(2);
			break;
		case 28:
			$("#counter").text(3);
			break;
		case 29:
			$("#counter").text(4);
			break;
		case 30:
			$("#counter").text("");
			break;	
		case 31:
			// all done.
			$("#tutorialButton").stopTime("tutorial");
			changeState(StateEnum.NONE);
			return;
		}
		counter++;
	});
};

runBreathe = function ()
{
	logAnalytics("breathe");
		
	$("#lungs").css("opacity", "0");

	if (muted == "off") // Show the speaker
	{
		$("#speaker_unmuted").show();
		$("#speaker_muted").hide();
	}
	else if (muted === "on") // Hide the speaker
	{
		$("#speaker_unmuted").hide();
		$("#speaker_muted").show();
	}

	// load media if not already loaded.
	if (audio_inhale == null) 
	{    	
		audio_inhale = new MediaWrapper("/android_asset/www/audio/" + voice + "/inhale.mp3");
	}
	if (audio_hold   == null) 
	{		
		audio_hold   = new MediaWrapper("/android_asset/www/audio/" + voice + "/hold.ogg");
	}
    if (audio_exhale == null) 
	{
    	audio_exhale = new MediaWrapper("/android_asset/www/audio/" + voice + "/exhale.ogg");
	}    	    		
	
	var counter = -4;

	$(this).everyTime(1200, "breathe", function () 
	{
		if (muted === "off") // Show the speaker
		{
	    	audio_inhale.setMuted(false);
	    	audio_hold.setMuted(false);
	    	audio_exhale.setMuted(false);
		}
		else if (muted === "on") // Hide the speaker
		{
			audio_inhale.setMuted(true);
			audio_hold.setMuted(true);
	    	audio_exhale.setMuted(true);
		}
		
		// do the counting automatically
	    if (counter < 0) {
	        $("#counter").text(Math.abs(counter));
	    } else {
	    	var fourcount = counter % 4 + 1;
	        $("#counter").text(fourcount);
	        
	        
	    }
	    
	    // Show the text and then display the circles.
		switch (counter)
		{
		case -4:
			$("#instruction").text("Ready");
			break;
		case -3:
			$("#instruction").text("Relax");
			break;
		case -2:
			$("#instruction").text("Exhale");
			break;
		case -1:
		    $("#instruction").text("Begin");
			break;
		case 0:
			$("#instruction").text("Inhale");
			$("#orb_green").replaceWith($("#orb_green").clone(true));  //resets animation
			$("#orb_green").show();
			$("#orb_green").css("webkitAnimationPlayState", "running");
			if (audio_inhale) 
			{
				audio_inhale.play();
			}
			break;
		case 4:
			$("#instruction").text("Hold");
			$("#orb_yellow_full").replaceWith($("#orb_yellow_full").clone(true)); //resets animation
            $("#orb_yellow_full").show();
			$("#orb_yellow_full").css("webkitAnimationPlayState", "running"); 
			if (audio_hold) 
			{
				audio_hold.play();
			}
			break;
		case 8:
		    $("#instruction").text("Exhale");
		    $("#orb_red").replaceWith($("#orb_red").clone(true)); //resets animation
            $("#orb_red").show();
		    $("#orb_red").css("webkitAnimationPlayState", "running");
		    if (audio_exhale) 
		    {
		    	audio_exhale.play();
		    }    			    
			break;
		case 12:
			$("#instruction").text("Hold");
			$("#orb_yellow_empty").replaceWith($("#orb_yellow_empty").clone(true)); //resets animation
            $("#orb_yellow_empty").show();
			$("#orb_yellow_empty").css("webkitAnimationPlayState", "running");
			if (audio_hold)
			{
				audio_hold.play();
			}
			break;
		case 15:
		    //reset to beginning.
		    counter = -1;
		    break;
		}
		
		// TODO: need to have this off by default and provide a way to turn it on in the settings.
        // heartbeat vibrations
        if (vibrate === "on")
        {
	        if (fourcount === 1)
        	{
        		navigator.notification.vibrate(70);
        	} else {
        		navigator.notification.vibrate(30);
        	}
        }
		counter++;
	});
};


updateMedia = function ()
{
	//console.log("updateMedia");
	
    if (voice == null)
	{
    	// Alford will be the default if nothing is in the localStorage
		voice = "Alford";
	}
    
    if (muted == null)
	{
    	// Off will be the default if nothing is in the localStorage
    	muted = "off";
	}
    
    if (vibrate == null)
	{
    	// Off will be the default if nothing is in the localStorage
    	vibrate = "off";
	}
    
    if (flurry == null)
	{
    	// On will be the default if nothing is in the localStorage
    	flurry = "on";
	}
    
    destroyAllMedia();
};

/**
 * Releases all acquired media handles.
 */
releaseAllMedia = function ()
{
    if (audio_intro != null)
	{
    	audio_intro.release();
	}
    if (audio_tutorial != null)
	{
    	audio_tutorial.release();
	}
    if (audio_inhale != null)
	{
    	audio_inhale.release();
	}
    if (audio_hold != null)
	{
    	audio_hold.release();
	}
    if (audio_exhale != null)
	{
    	audio_exhale.release();
	}    
};

/**
 * Releases all acquired media handles and then nulls all media variables.
 */
destroyAllMedia = function ()
{
	releaseAllMedia();
	audio_intro = null;
	audio_tutorial = null;
	audio_inhale = null;
	audio_hold = null;
	audio_exhale = null;
};

/**
 * Runs on PhoneGap initialization (does not run for browser)
 */
onDeviceReady = function () {
    //console.log("Device Ready");
    document.addEventListener("pause", onApplicationPause, false);
    
    //voice = preferences("voice");
	//muted = preferences("muted");
	//vibrate = preferences("vibrate");
	//flurry = preferences("flurry");
	//Used here for the preferences menu
	
    //document.addEventListener("resume", onApplicationResume, false);
    //document.addEventListener("backbutton", onBackButton, false);
};

onApplicationPause = function () {
    //console.log("applicationPause");
    destroyAllMedia();
};

showAbout = function ()
{
	changeState(StateEnum.NONE);
	//TODO: so... ideally, we'd like to load this from another file... not now, though.  -c
	//remember that \n gives newline, not html br. 
	navigator.notification.alert("Tactical Breather\n\n" +
		"Tactical Breather is brought to you by the National Center for Telehealth and Technology (T2), a component " + 
		"center of the Defense Centers of Excellence for Psychological Health and Traumatic Brain Injury (DCoE). " + 
		"T2 is dedicated to providing innovative technology applications for psychological health and " + 
		"well-being. For more information, visit T2Health.org.\n\n" + 
		"Tactical Breather can be used to gain control over physiological response to stress. Tactical Breather is " +
		"based on ideas presented by Lt. Col Dave Grossman in his book, \"On Combat: The Psychology and Physiology " + 
		"of Deadly Conflict in War and in Peace.\"\n\n" + 
		"The National Center for Telehealth and Technology |T2| researches, develops, evaluates and deploys new and " + 
		"existing technologies for Psychological Health (PH) and Traumatic Brain Injury (TBI) across the " + 
		"Department of Defense (DoD). The T2 center meets this mission by serving as the principal DoD coordinator " + 
		"in such areas as innovative technology applications, suicide surveillance and prevention, online behavioral " + 
		"health tools, and telepsychological health. T2 is a component center of the Defense Centers of Excellence for " +
		"Psychological Health and Traumatic Brain Injury (DCoE), which leads a collaborative global network to promote " + 
		"the resilience, recovery and reintegration of Warriors and their families who face psychological health and " + 
		"traumatic brain injury issues.\n\nTechnology to Make People Healthy", null, "About Tactical Breather");
};

showExcerpt = function ()
{
	changeState(StateEnum.NONE);
	
	navigator.notification.alert("Extract from \"On Combat\",\n" +  
			"provided by Lt. Col. Dave Grossman:\n\nUsing tactical breathing to stay in the zone\n\n" + 
			"I will not fear. Fear is the mind-killer. Fear is the little-death that brings total obliteration. " + 
			"I will face my fear. I will permit it to pass over me and through me. And when it has gone past I will turn " +
			"the inner eye to see its path. Where the fear has gone there will be nothing. Only I will remain. Frank Herbert Dune\n\n" + 
			"Autopilot responses developed through repetitive practice, and stress inoculation through realistic, stressful training " + 
			"are two powerful and effective tools to push the envelope and stay in the zone. One additional tool to control " + 
			"physiological response is the tactical breathing exercise. Let us go back to Ron Avery and his performance as a " + 
			"professional shooter. When running and gunning in a pistol competition, his heart rate peaks around 145 bpm, but when he " + 
			"is standing and shooting, his heartbeat stays around 90 bpm. He pulls himself down to Condition Yellow, like a sniper taking calm, " + 
			"precision shots. A good professional shooter functions at two levels, bouncing back and forth between Condition Yellow and " + 
			"Condition Red (or, sometimes, in a Condition Red that extends into the realm of Condition Gray). Something similar happens " + 
			"with professional basketball players. They are running up and down the court, functioning in high Condition Red, often pushing " + 
			"the envelope well into the Gray zone, which is fine for that phase of the game. However, should a player stop and attempt to make " + 
			"a free throw while his heart is pounding in his chest, he is going to \"throw a brick\"--a miss. Typically, a professional " + 
			"basketball player pauses for a moment before making a free throw and intuitively knows to take a few deep breaths. He uses all " + 
			"the time available to him to bring his heart rate down. He rolls his large shoulder muscles, consciously relaxes his body and " + 
			"avoids looking at the crowd as he concentrates on his task. He intentionally pulls himself down into Condition Yellow, becoming " + 
			"a \"sniper\" in order to make the shot. Every basketball player intuitively understands that if he wants to make that free throw, " + 
			"he must switch back and forth between Condition Yellow and Condition Red. For those who don't push the envelope through physical " + 
			"fitness and repetitive training, Condition Gray is generally a realm in which complex motor skills begin to break down.  " + 
			"One of the things that appears to be happening here is that bilateral symmetry begins to set in, meaning that what you do " +  
			"with one hand you are likely to do with your other. For example, startle a baby and both of its little arms and legs jerk in " + 
			"what is known as the startle response, doing the exact same thing on both sides of the body. Bilateral symmetry can have grave " +
			"consequences for a police officer in a tense situation where he is holding a pistol on a suspect. Say the suspect attempts to " + 
			"flee and the police officer grabs him with his free hand. Now, this is never a good tactical technique, and it is especially " + 
			"bad when the officer's heart rate is racing past 145 bpm. His accelerated heartbeat causes bilateral symmetry, so that as the " + 
			"officer grabs a fistful of the suspect's shirt with one hand, he has a convulsive clutch response in his gun hand, which causes " + 
			"an unintentional discharge of the weapon. That is my definition of a bad day for our police officer, and a very bad day for the " + 
			"suspect. Bilateral symmetry can also happen when you are startled. In an article titled, \"The Impact of the Sympathetic " + 
			"Nervous System on Use of Force Investigations,\" Bruce Siddle writes, \n\n" + 
			"Being startled while otherwise physically and/or mentally preoccupied will result in four involuntary actions occurring within " + 
			"150 milliseconds. First, the eyes blink; second, the head and upper torso move forward; third, the arms bend at the elbow; " + 
			"and lastly, the hands begin to tighten into fists. If a person is under extreme stress and adrenaline has been introduced into " + 
			"the system, the resulting startle response contraction [of the hands] can generate as much as 25 pounds of pressure. That amount " + 
			"of force is approximately twice the amount needed to discharge a double action revolver.\n\n" + 
			"Now, there are many safeguards to prevent this convulsive clutch response. One is to keep the finger off the trigger until it is time " + 
			"to destroy the target, a technique that has become the standard for military and law enforcement training. Even that is not a guarantee, " + 
			"since the clutch response can sometimes be so intense that the finger will slip back into the trigger guard causing an unintentional " + 
			"discharge. My co-author told of being involved in a large brawl as a military policeman (MP) in Saigon.\n\n" + 
			"There were six enraged American soldiers and five or six MPs, and we were going at it hot and heavy all over the sidewalk, out into the " + 
			"street, and back onto the sidewalk again. At one point, one of the soldiers started to disarm one of the MPs, so I pulled my .45, pumped " + 
			"the slide to chamber a round, and stuck it into the man's face. When he obeyed my commands to lie on his stomach, I pointed my .45 into " + 
			"the air and inadvertently squeezed the trigger, discharging a round into the sky. It startled everyone and stopped the fighting so that " + 
			"we were able to get the suspects into handcuffs. By the way, I got a verbal commendation from the provost officer for my quick thinking.  " + 
			"Since I had not received any commendation up to that point, I accepted his with grace and humility, never mentioning that it had been a mistake.\n\n" +  
			"Another standard safeguard is to hold the gun at a low port position, angled downward. Should there be an unintentional discharge the round will " + 
			"hopefully strike the floor, or at least hit the lower portion of any individual unfortunate enough to be in front of the weapon. The greatest " + 
			"safeguard, however, is to not allow your heart rate to get too high. Calm people are much less likely to make these kinds of mistakes. Once your " + 
			"heart rate rises above Condition Red (generally beginning around 145 bpm) there is nothing there that you want. Most people have experienced " + 
			"anxiety before taking an important test. There are two components to test anxiety: the psychological and the physiological. The latter is " + 
			"characterized by an accelerated heart rate and a loss of fine-motor control. When I was teaching at West Point and Arkansas State University, " + 
			"I found that football players generally suffered the worst from test anxiety. Now, on most campuses the football players have an unfortunate " + 
			"reputation of being \"dumber than a box of rocks,\" as one of my fellow professors put it, which is generally unfair. The truth is that they " + 
			"are generally no less smart than any other athelete, but their sport can work against them. With the exception of the quarterback and the kicker, " + 
			"no one on the football team needs fine-motor control. The blockers and the backers certainly do not need it, and most of the time the receiver " + 
			"(catching with two hands) only needs bilateral symmetry. When a football player is in the game, functioning in what is often referred to as his " + 
			"\"sweet spot,\" his performance level, his Condition Red (which may be pushed very high up into the Gray zone), is somewhere between 140 and " + 
			"175 bpm. An unfortunate bi-product of this is that because he performed in his sweet spot so often in middle school and in high school, and now " +
			"in college, his performance-level bpm can overlap to the classroom. He might have studied all night for a test, he knows the material and he is raring " +
			"to go, but when he sits down to take it, his heart pounds in his chest just as it does on the field. He suddenly cannot get his fingers to operate his " +
			"pen, and the harder he tries, the worse his condition gets. So, he blanks. He cannot get even the first question, and he goes downhill from there. " +
			"When he fails the test, everyone is convinced he is stupid and, sadly, he may even believe it himself. But he is not stupid; it is just his body " + 
			"working against him. During my time as a professor at Arkansas State, most of the coaches tried to send their students through my Introduction to " + 
			"Psychology class.  It was not because I am an easy professor, but rather because I taught them a powerful breathing technique, the same one that I will " + 
			"give you later in the book. I would watch the students as they took their test, keeping an eye out for cheating, but mostly I watched for signs of test " + 
			"anxiety. Often I would see them hunch their shoulders and hyperventilate. If they were light complexioned, their faces would turn white around the nose and " + 
			"lips, and their knuckles would turn white. When this happened I knew I had someone suffering from test anxiety. So periodically during the test, I would have " + 
			"them all stop and put their pencils down, and I say my mantra: \"It's a free throw. It's a free throw.\" Then I would make the whole class do exactly what a " + 
			"basketball player does to prepare for a free throw: breathe to lower the heart rate. More specifically, I would have them do the four-count tactical breathing " + 
			"exercise used by elite SWAT teams and special ops soldiers world-wide. And it worked.  Tactical breathing is truly a revolution in warrior training and I am " +
			"just one of many individuals teaching this technique. The Calibre Press Street Survival Seminar is a world-class program that has trained hundreds of thousands " + 
			"of law enforcement officers in this method, and they have received extensive feedback confirming that tactical breathing has undoubtedly saved many lives. If this " +
			"technique can be useful for college basketball players in the heat of a game, and for football players contending with test anxiety, how infinitely more important " + 
			"is it for warriors making life and death decisions and taking life and death shots in the middle of the toxic, corrosive, destructive environment of the universal " + 
			"human phobia?\n\n" +
			"Condition Black\n\n" +  
			"O judgment! Thou art fled to brutish beasts, and men have lost their reason. Shakespeare Julius Caesar\n\n" + 
			"Bruce Siddle's heart rate research, Popular Science's reports of NASCAR drivers, and the Green Beret \"stress test\" research, all indicate that 175 bpm is about " + 
			"as high as Condition Red can be pushed into this mysterious Gray zone. Again, we must be cautious about putting specific numbers on these Conditions, but it would " +
			"appear that even under the most ideal circumstances, above 175 bpm a catastrophic set of events begins to happen. Cardiologists tell us that at a certain point an " + 
			"increased heart rate becomes counterproductive because the heart is pumping so fast that it cannot draw in a full load of blood before pumping it back out. As the heart " +
			"rate increases beyond this point, the effectiveness of the heart, and the level of oxygen provided to the brain, steadily decreases. One cardiologist suggested to me " +
			" that this might be what is happening when SNS arousal induces a heart rate above 175 bpm. Whatever the cause, something remarkable appears to be happening when the SNS " +
			"accelerates the heart rate above 175 bpm. For our purposes here, let us call this Condition Black, and let's examine what is going on inside the mind of a person trying " + 
			"to function at that level. The Triune Brain Model was developed by Dr. Paul MacLean, chief of the Laboratory of Brain Evolution and Behavior at the National Institute " + 
			"of Mental Health in Bethesda, Maryland. He suggests that we think of the human brain as consisting of three parts--the forebrain, the part that makes you a human being; " +
			"the midbrain, or mammalian brain, the part that all mammals have in common; and the hindbrain, or the brain stem. The forebrain performs basic thought processes, the midbrain " +
			"performs extensive reflexive processes, and the hindbrain takes care of the heart rate and respiration. If you were to get shot in the forebrain, you might survive the wound " +
			"since you can survive a lot of damage in that portion of the brain. In fact, until you were a young teenager, doctors could perform a hemispherectomy and remove half " +
			"your brain, and you could still have a fully functional life. Severe damage occurs, however, when a bullet hits your midbrain and, should even a small caliber bullet enter " +
			"your hindbrain, the intrusion would probably shut down your breathing and your heartbeat. As you enter into Condition Black, your cognitive processing deteriorates, which " +
			"is a fancy way of saying that you stop thinking. 2,500 years ago Brasidas of Sparta said that, \"Fear makes men forget, and skill that cannot fight is useless.\" In " +
			"Condition Black you can run and you can fight like a big, hairless, clawless bear, but that is about all you are capable of doing. Your forebrain shuts down and the midbrain, " +
			"the \"puppy\" inside, that part that is the same as your dog's brain, reaches up and \"hijacks\" the forebrain.  It is important to note that there is a tremendous difference " +
			"between the performance impact of a heart rate increase caused by fear (i.e., the sympathetic nervous system flooding stress hormones into your body), and a heart rate increase " +
			"caused by physical exercise. When your heart is pounding due to physical exertion, your face will usually be beet red, as every vessel dilates wide open to get blood to the " +
			"muscles. But with a fear-induced heart rate increase, your face will usually turn white, due to vasoconstriction. If there are extreme physical demands placed upon the body at " +
			"the same time that the vasoconstriction is occurring, then these two processes apparently work against each other to cause a skyrocketing heart rate. We are not sure why this " +
			"happens, but the current, dominant theory is that the physical demands cause the body to scream for oxygen while the vasoconstriction shuts down the blood flow that provides " +
			"the oxygen, causing the heart to beat ever faster while achieving very little. I mentioned this earlier but it bears repeating here in order to understand that using physical " +
			"exercise to increase your heart rate is an excellent technique to simulate the effects of combat stress. But we must remember that the powerful phsyiological effects of someone " +
			"trying to kill you are not something we can replicate in training, although force-on-force paint bullet training (someone trying to hurt you) can come fairly close. Have you " +
			"ever tried to have an argument or a discussion with a truly frightened or angry person? It cannot be done, because the more frightened and angry the person is, the less rational " +
			"he is. This is because his forebrain has shut down and his midbrain, the one like a dog's, is in control. In fact, you might as well try to argue with your dog; he might be " + 
			"intrigued by the experience but it will not accomplish much. Nor will you accomplish much when trying to talk to a human being in this heightened condition. To connect with him, " +
			"you must first calm him down. Artwohl and Christensen, in Deadly Force Encounters, give a classic example of this irrational behavior. Officer Peterson didn't hear the other " +
			"officer's shotgun explode or his partner's handgun fire or even his own fire, but every shot fired hit the suspect. \"I went up on the guy, who slid down the side of his car " +
			"into a seated position, and kicked his Beretta away.  Another officer picked up the gun as two others pulled the guy onto his stomach and handcuffed him.\" Peterson was hit " +
			"with a rush of adrenaline, greater than any synthetic pill could create. \"I went to the car phone and punched in my home number. I got the recorder, but I knew my boys " +
			"were home, probably still sleeping. I screamed for someone to pick it up and I kept screaming until it awoke my boys downstairs. When they answered, I screamed at them what " +
			"had just happened and that I wanted to see them. I just needed to hold them. When they asked how they would get to me, I told them that my partner would come and get them.\"  " + 
			"Peterson says the rush was so intense, so extraordinary, that it was almost like an out-of-body experience. The boys were not brought to the scene.\n\n" +
			"Vasoconstriction: white with fear\n\n" + 
			"I ain't got time to bleed. Jesse Ventura\n\n" +
			"Think of those cold mornings when your fingers are white and not working for you. That is an example of vasoconstriction caused by the cold. It also happens as a result of " +
			"stress. At its earliest stage, as you enter into Condition Red (starting around 115 bpm), you begin to experience a loss of fine-motor control. In Condition Gray " +
			"(beginning around 145 bpm) the average individual begins to lose complex motor control. But when your heart roars up into the realm of Condition Black (around 175 bpm), \n" + 
			"the effects of vasoconstriction become catastrophic . It is easy to detect this in a light-complexioned person (though it happens to everyone of every race), because you " + 
			"can see the skin turn white when the blood flow to the outer layer of his body is constricted. Specifically, the blood pumps from the heart through the arteries and then, " +
			"at the pre-capillary stage (just before entering the capillaries), the blood flow constricts. At low levels of vasoconstriction (from cold or stress), only the little " +
			"capillaries shut down, causing some loss of fine-motor control. It happens to your fingers on those cold mornings and it happens when you are under stress. As the " +
			"vasoconstriction becomes more intense, the blood flow to the complex motor muscles begins to shut down. The blood pools into the body core and large muscle groups, " +
			"and your blood pressure skyrockets. (This increase in blood pressure is an important aspect of what is happening to your body; research at the Federal Law Enforcement " +
			"Training Center suggests that systolic blood pressure is a much better indicator of stress than heart rate.) The outer layer of your body becomes almost a layer of armor, " +
			"and as long as an artery is not hit, you can take extensive damage without much blood loss. (This is why surface wounds on the face and scalp tend to bleed more. In this " +
			"region the intake and output vessels are both close to the surface, so the ability of vasoconstriction to limit bleeding is less effective here.) This appears to be a survival " +
			"mechanism intended to limit blood loss in a combat situation. The price you pay, however, is a loss of motor control because when the muscles stop getting blood, they stop " +
			"working. Eventually, there is going to be a backlash called vasodilatation, which is the opposite of vasoconstriction. When vasodilatation occurs, your veins go wide open, " +
			"and (if you are light-complexioned) your face turns red. Police officers sometimes refer to this as a \"tomato face,\" and they have learned that it generally represents " +
			"significantly less danger than the white face of vasoconstriction. Bruce Siddle gives a classic example of vasoconstriction. As three police officers checked each other for " +
			"injury after a shooting, one of them found a small pucker in the front of his upper sleeve and another pucker on the other side. \"The bullet must have missed me,\" he said, " +
			"visibly relieved. But as soon as he uttered those words, the wound in his arm opened and the hole gushed blood. When he thought he was okay he relaxed, which caused the " +
			"vasoconstriction to stop and the vasodilatation to begin.\n\n" +
			"Loss of near vision and pistol marksmanship\n\n" + 
			"As difficult as it may seem during the heat of the battle it is important to shift your focus from the weapon itself to the living, breathing, human being wielding the " +
			"weapon--the same human being who eats, sleeps, urinates and also puts his pants on one leg at a time like you did this morning. Steve Tarani and Damon Fay Contact " +
			"Weapons: Lethality and Defense\n\n" +
			"In a heart-pounding situation, the loss of your fine-motor control and near vision makes it mandatory that you drill on those things that seem simple when you are calm and " +
			"collected. In Sharpening the Warrior's Edge Bruce Siddle notes that fine-motor skill decreases as heart rate increases, but you can limit the impact by preparing mentally and " +
			"physically ahead of time. With a loss of near vision, you may not be able to see your pistol sights. (On a rifle, it appears that the front sight is far enough away that the " +
			"ability to see it clearly is not affected by loss of near vision, and on a military peep sight the rear sight is supposed to be out of focus.  That may help explain why " + 
			"virtually all armies in all nations converted to the peep sight on their military weapons throughout the 20th century.) There is no debate about the fact that this inability " +
			"to focus on the pistol sight can occur, although there is an ongoing debate as to how to deal with the problem. Some say that if you are taught to see the front sight long " +
			"enough and hard enough, you will see it at the moment of truth. Others say that should you suck up two slugs and your heart races to 220 bpm, you cannot be expected to see your " +
			"sights and you need to learn instinctive shooting (sometimes called target-focused shooting or point shooting). I don't want to get involved in this point-shooting vs. " + 
			"aimed-shooting debate. I have good friends (men I admire and respect greatly) on both sides of this issue, and I think we are probably evolving to a solution that uses a " +
			"combination of the two methods: When you have time and distance, use those sights, but when you do not, at extreme close ranges there may be value in reverting to point " +
			"shooting. Just know that under extreme stress you might not be able to see your pistol sights. There are several case studies of police officers reporting after their " +
			"gunfights that because they could not see their sights, they did not fire. Know ahead of time that this phenomenon might happen and don't let it paralyze you. If you find " +
			"yourself in a desperate, life-and-death, point-blank confrontation, and you have a clear shot, trust your instincts, point and fire. But, if you are having difficulty focusing " +
			"on your sights and you have just a few seconds, breathe. Most of us were taught to breathe during our first day on the firing range, using the acronym BRASS, Breathe, Relax, " +
			"Aim, Sight, and Squeeze. Breathe in, hold it, let it out, hold it and squeeze. This is perfect opportunity to use the tactical breathing method. If you have the time, breathe " +
			"and pull yourself down to Condition Red so you can get those sights into focus. Or take it a step further and pull yourself down into Condition Yellow and make a rock-solid, " +
			"precision shot. The breathing technique will be covered in greater detail later, but let me give you just one example of an individual who consciously used this technique to " +
			"bring himself down into Condition Yellow. One officer told me of an experience he had while hunting. \"I had this magnificent buck in my sights,\" he said. I'm up in my deer " +
			"stand and my heart is pounding in my chest. It's a long shot at an awkward angle and all I can see is the back of his head and neck. I had the time, so I took four, deep, belly " +
			"breaths, just like you taught us. The breathing brought my heart rate down and I made the shot. If a little breathing exercise can work in competition, on the range and while " +
			"hunting, know that it will have enormous power to control your stress response during combat. There is cause to believe that we can train warriors to break out of tunnel vision " +
			"by having them scan and breathe after taking a shot. Physically turning the head and scanning the battlefield after an engagement seems to cause tunnel vision to diminish. Even " +
			"if the warrior stayed in tunnel vision, turning his head permits him to see additional threats, like scanning with a flashlight beam. Tactical breathing (which will be covered " +
			"in detail later) also helps the individual to be calm and regain his composure. By having individuals scan and breathe on the range every time after every engagement, the " +
			"procedure soon becomes a valuable and automatic conditioned response.  There is enormous value in warning our warriors ahead of time that such phenomena might occur. One " + 
			"police officer in Artwohl and Christensen's book, gave a classic example of the tremendous advantages of knowing about these things. I've been involved in three shootings. " +
			"Before the first two, I had no training in what to expect.  I performed well, but felt shocked, disoriented, confused, and at times out of control by all the weird stuff that I " + 
			"experienced during and after the shooting. I didn't know what to think and that made it harder to cope during and after the event. After the second shooting, I sought counseling " + 
			"and learned about all that weird stuff I'd been experiencing. The doctor also taught me the principles of Stress Inoculation Training and I started using it to prepare myself for the " +
			"future. Then when I got into another situation, the training made all the difference in the world. This time I knew what to expect and I was even able to control and compensate somewhat " + 
			"for the tunnel vision, sound distortions, and other strange things my mind and emotions were going through. I also bounced back a lot quicker because I knew I wasn't crazy and I knew " +
			"what to do to take care of myself. Our goal is to create warriors like this, preferably before they go into combat for the first time. \"Forewarned is forearmed,\" and we must send " +
			"our warriors into combat as well armed and well informed as humanly possible. Now, you cannot do anything about the exposure to a life threatening situation, as a warrior it is your " + 
			"job to go into danger, but you can do something about how you respond to it. This is critical because if you do not feel a sense of intense fear, helplessness or horror, there is no " +
			"PTSD. You create fear, helplessness and horror by being a sheep. You prevent it by being a sheepdog, a warrior. If there is no sense of helplessness because your training has taught " +
			"you what to do, there is no PTSD. If there is no horror because you have been inoculated against seeing blood, guts and brains, there is no PTSD. If there is no intense fear, meaning " +
			"that your heart rate does not shoot up to 175 bpm because you use tactical breathing, there is no PTSD.  Re-experiencing the event: The puppy comes for a visit ...we must admit that " +
			"what is closest to us is the very thing we know least about, although it seems to be what we know best of all... it is just because the psyche is so close to us that psychology has been " + 
			"discovered so late. C.G. Jung\n\n" +
			"Modern Man in Search of a Soul\n\n" +
			"Next, look at paragraph B, re-experiencing the event. Think of it as when the puppy comes for a visit. Anyone can re-experience the event after a traumatic episode. Many police officers " + 
			"and soldiers, warriors who purposely go into the toxic realm of violence, have experienced this. It is important to emphasize here that just having the puppy come for a visit (re-experiencing " +
			"the event) is not, by itself, PTSD. My co-writer, Loren Christensen served as a military policeman in Saigon, Vietnam in 1969 and 1970, perhaps the most dangerous city in the world at that time. " + 
			"He had to contend with rocket attacks, snipers, terrorist bombings, anti-American riots, bar fights, racial violence, and a host of other deadly stressors. He said that after he returned home " + 
			"and bought a house in a quiet neighborhood, a large community of Vietnamese sprang up about a mile from where he lived. He never thought much of it and even drove by the housing projects every " +
			"morning on his way to the police precinct.  One morning, almost 10 years to the day after he had returned from the war, he drove by the projects on his way to the firing range where he worked " +
			"as an instructor. As he waited at a stoplight, he said a dozen Vietnamese children crossing the street in front of him.  \"Panic hit me like an overwhelming wave,\" he said. \"I looked at " + 
			"those kids, especially the girls who were wearing ao dai, the traditional white tunic over black pants, a sight I saw every day in Vietnam, and my first thought was, \"I have to get more guns.\"  " +
			"Sweating profusely and breathing as if he had just run a mile, he turned his truck around and returned home to get two more firearms.  He had the presence of mind to take a different route to " +
			"the firing range, and eventually his heart rate, trembling hands and profuse sweating went away. About two weeks later, he had a similar experience while shopping in a military surplus store " +
			"for backpacking gear. When he bent over a large bin to rummage through a pile of used fatigue pants, he smelled the familiar odor of the material, and he again began to sweat heavily, breath " +
			"raggedly and became nearly overpowered with a sense of unexplained panic. As before, it took Christensen several minutes to return to normal. He says it was not until he had had two more " +
			"episodes in the following weeks that he began to research the symptoms of PTSD. With a new understanding of what was happening to him, he was able to \"make peace with the memory\" and has " +
			"never had such intense episodes since.  Christensen was like every returning soldier from Vietnam in that he was not debriefed. One day he was in a war torn environment and the next day he " +
			"was stepping off an airplane in Oakland, California. He got a steak dinner, a fresh uniform, and a discharge. Four hours later he was sitting in his parent's living room. Re-experiencing the " +
			"event can be caused by extended periods of danger and stress, as happened with Christensen after his tour of duty in Vietnam. Or, the puppy might come for a visit after a single, significant, " +
			"traumatic event. Robert Speer, an Arkansas State Trooper who is now in command of their state SWAT team, tells about when he had to kill a gunman in a desperate and explosive gunfight at " +
			"point-blank range. He had difficulty sleeping for a few nights, but otherwise he was fine; at least he thought so until one evening a week later when he and his wife were watching his " +
			"daughter at a swimming competition. \"Maybe it was one of the starter's guns that set me off,\" he said. \"All of a sudden out of the blue my heart started pounding, I began " +
			"hyperventilating and I was drenched with sweat.\" His reaction was so powerful that his wife thought he was having a heart attack.  This was a classic example of a powerful, post-combat response," +
			"commonly called a panic attack. Sometimes it is referred to as an anxiety attack, but that term is not appropriate. He was not feeling a little anxiety; the puppy inside his head was panicking! " +
			"During his gunfight, his heart rate skyrocketed and a neural network was established when the puppy \"blew a hole through the screen door.\" That neural network was still in place when a week " +
			"later the unexpected sound of the starter's pistol at a swim meet caused the trooper's puppy to burst through that hole in the screen door, jump into his lap, pee, gnaw at his throat, and " +
			"cry out, \"Gunfight!  Gunfight! Where's the gunfight? Where? Where? Where?\" Robert is fine today, a true warrior leader with a lifetime of successful combat experiences behind him. But at " +
			"that time he had two things working against him: First, no one warned him that he might have such a reaction. Second, no one taught him what to do should it happen.  Untold hundreds of " +
			"thousands of other people over the millennia have been through traumatic events and have later relived their experiences. They too had two things working against them: No one warned " +
			"them that they might re-experience the event, and no one taught them what to do should it happened. So, I'm warning you now that it might happen, and here is what you should do if it does happen. " +
			"Again, do not think that everyone who gets in a deadly force encounter will get a \"visit\" from the puppy. In Dr. Dave Klinger's research of 113 SWAT team members who had shot and hit an " + 
			"adversary in real combat, approximately 40 percent of them re-experienced the event. If you are stress inoculated, know how to use our breathing techniques and are properly trained in survival " +
			"tactics, then you can reduce the possibility that it might happen.  Still, even mature, world-class warrior leaders with a lifetime of experience under their belts (like Robert Speer in this case, " +
			"and Randy Watt who we will meet later in this chapter) can re-experience their traumatic event and have the puppy come for a visit. It is important that you understand that a visit from the puppy " +
			"does not necessarily mean you have PTSD. Re-experiencing the event can be a normal reaction to an abnormal event. You need to make peace with the memory and get on with your life. PTSD, as you " +
			"will see later, occurs when you try to flee from the memory.\n\n" +
			"Neitze said, \"What does not kill me only makes me stronger.\" But this concept was communicated long before Neitze, almost 2000 years before, when it was written:\n\n" + 
			"we glory in tribulations...knowing that tribulation worketh patience; And patience, experience; and experience, hope: And hope maketh not ashamed. Romans 5:3-5 The Bible\n\n" + 
			"The young warrior fails, and picks himself up, dusts himself off and drives on, year after year, to become the veteran that others can depend upon in their hour of need.  " +
			"And always the lesson he learns is to remain calm, to have grace under pressure. So you see, the debriefing is not some \"Kumbayah sobfest.\" We can weep at funerals, but " +
			"we strive not to weep at the memory of battle. The tool that we use to control our emotions, to keep the puppy from coming through the screen door, is the tactical breathing " +
			"exercise, which will be covered in the next chapter. For now, understand that the goal is to maintain control of your emotions and remain calm, before, during and after combat. This " +
			"does not mean that the warrior knows no joy. The process of multiplying the joy includes finding humor and laughter in your memories. Often with an earthy, even bawdy humor. " +
			"Controlling your emotions does not mean you have no emotions. There can be a deep and abiding joy in the warrior's life, as long as he keeps the puppy under control. As Shakespeare " +
			"wrote:\nGive me that man", null, "Excerpt Tactical Breather");
};

