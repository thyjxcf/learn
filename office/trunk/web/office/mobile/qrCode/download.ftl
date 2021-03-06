<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no,email=no" name="format-detection">
<meta content="fullscreen=yes,preventMove=no" name="ML-Config">
<meta name="viewport" content="width=640, target-densitydpi=device-dpi, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>客户端下载</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath!}/office/mobile/qrCode/css/style.css"/>
<script type="text/javascript" src="${request.contextPath!}/office/mobile/qrCode/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath!}/office/mobile/qrCode/js/myscript.js"></script>
</head>
<body>
</body>
<script>
document.addEventListener('DOMContentLoaded', function() {
	if(isWeiXin()){
		$('body').html('<p style="text-align:right;padding-right:10px;"><img src="${request.contextPath!}/office/mobile/qrCode/images/tips.png" alt=""></p>');
	}else{
		if(isAndroid()){
			window.location.href = '${androidPath!}';
		}	
		if(isIOS()){
			window.location.href = '${iosPath!}';
		}
	}
}, false);

function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}

function isAndroid(){
	 var u = navigator.userAgent;
	 if(u.indexOf('Android') > -1 || u.indexOf('Adr') > -1){
	 	return true;
	 }else{
	 	return false;
	 }
}

function isIOS(){
	 var u = navigator.userAgent;
	 if(!!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)){
	 	return true;
	 }else{
	 	return false;
	 }
}
</script>
</html>