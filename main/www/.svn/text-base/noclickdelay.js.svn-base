/*
 * From http://cubiq.org/remove-onclick-delay-on-webkit-for-iphone
 * Use:  new NoClickDelay(document.getElementById('element'));
 * 
 * After discovering this code, I came across:
 * http://code.google.com/intl/ro-RO/mobile/articles/fast_buttons.html
 * and
 * https://github.com/jbroadway/jquery-fast-click
 * ... but chose not to implement them, as this seemed to be working.  I did steal a bit of code to add the
 * touchmove 50 pixel tolerance below. -ca
 * ... also, read the comments on the first url to discover possible problems with the pressed class
 * and the possible touchcancel event.
 */
function NoClickDelay(el) {
	console.log ("Creating new NoClickDelay");
	this.element = typeof el == 'object' ? el : document.getElementById(el);
	//if( window.Touch )
	this.element.addEventListener('touchstart', this, false);
}

NoClickDelay.prototype = {
	handleEvent: function(e) {
		switch(e.type) {
			case 'touchstart': this.onTouchStart(e); break;
			case 'touchmove': this.onTouchMove(e); break;
			case 'touchend': this.onTouchEnd(e); break;
		}
	},

	onTouchStart: function(e) {
		e.preventDefault();
		this.moved = false;

		this.theTarget = document.elementFromPoint(e.targetTouches[0].clientX, e.targetTouches[0].clientY);
		if(this.theTarget.nodeType == 3) this.theTarget = theTarget.parentNode;
		this.theTarget.className+= ' pressed';
        this.startX = event.touches[0].clientX;
        this.startY = event.touches[0].clientY;
		this.element.addEventListener('touchmove', this, false);
		this.element.addEventListener('touchend', this, false);
	},

	onTouchMove: function(e) {
		// if we haven't moved 50 pixels, let's not call it a move.
		if(Math.abs(event.touches[0].clientX - this.startX) > 50 || Math.abs(event.touches[0].clientY - this.startY) > 50)
		{
			this.moved = true;
		}
		this.theTarget.className = this.theTarget.className.replace(/ ?pressed/gi, '');
	},

	onTouchEnd: function(e) {
		this.element.removeEventListener('touchmove', this, false);
		this.element.removeEventListener('touchend', this, false);

		if( !this.moved && this.theTarget ) {
			this.theTarget.className = this.theTarget.className.replace(/ ?pressed/gi, '');
			var theEvent = document.createEvent('MouseEvents');
			theEvent.initEvent('click', true, true);
			this.theTarget.dispatchEvent(theEvent);
		}

		this.theTarget = undefined;
	}
};