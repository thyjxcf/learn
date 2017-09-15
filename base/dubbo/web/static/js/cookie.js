var now = new Date();
var cookieSaveDate = new Date(now.getTime() + 1000 * 60 * 60 * 24 * 10000);

function setCookie(cookieName, cookieValue, expires, path, domain, secure) {
	document.cookie =
		escape(cookieName) + '=' + escape(cookieValue)
		+ (expires ? '; expires=' + expires.toGMTString() : '')
		+ (path ? '; path=' + path : '')
		+ (domain ? '; domain=' + domain : '')
		+ (secure ? '; secure' : '');
}

function getCookie(cookieName) {
  var cookieValue = null;
	var posName = document.cookie.indexOf(escape(cookieName) + '=');
	if (posName != -1) {
		var posValue = posName + (escape(cookieName) + '=').length;
		var endPos = document.cookie.indexOf(';', posValue);
		if (endPos != -1) cookieValue = unescape(document.cookie.substring(posValue, endPos));
		else cookieValue = unescape(document.cookie.substring(posValue));
	}
	return (cookieValue);
}

function clearCookie(cookieName) {
	var now = new Date();
	var yesterday = new Date(now.getTime() - 1000 * 60 * 60 * 24);
	this.setCookie(cookieName, 'cookieValue', yesterday);
}

function getCookieWithoutEncode(cookieName) {
  var cookieValue = null;
	var posName = document.cookie.indexOf(cookieName + '=');
	if (posName != -1) {
		var posValue = posName + (cookieName + '=').length;
		var endPos = document.cookie.indexOf(';', posValue);
		if (endPos != -1) cookieValue = document.cookie.substring(posValue, endPos);
		else cookieValue = document.cookie.substring(posValue);
	}
	return (cookieValue);
}

function setCookieWithoutEncode(cookieName, cookieValue, expires, path, domain, secure) {
	document.cookie =
		cookieName + '=' + cookieValue
		+ (expires ? '; expires=' + expires.toGMTString() : '')
		+ (path ? '; path=' + path : '')
		+ (domain ? '; domain=' + domain : '')
		+ (secure ? '; secure' : '');
}

/*
function fixDate(date) {
  var base = new Date(0);
  var skew = base.getTime();
  if (skew > 0){
    date.setTime(date.getTime() - skew);
  }
}

  var now = new Date();
  fixDate(now);
  now.setTime(now.getTime() + 365 * 24 * 60 * 60 * 1000); //expire in a year
  setCookie('DokuWikisizeCtl',textarea.style.height,now);

*/