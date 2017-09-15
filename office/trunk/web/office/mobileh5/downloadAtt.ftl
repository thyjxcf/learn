<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no,email=no" name="format-detection">
<meta content="fullscreen=yes,preventMove=no" name="ML-Config">
<meta name="viewport" content="width=640, target-densitydpi=device-dpi, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>附件下载</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath!}/office/mobile/qrCode/css/style.css?version=v1.0"/>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${request.contextPath!}/office/mobile/qrCode/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath!}/office/mobile/qrCode/js/myscript.js?version=v1.0"></script>
<script type="text/javascript" src="${request.contextPath!}/static/html/js/public.js?version=v1.0"></script>
<script type="text/javascript" src="${request.contextPath!}/static/html/js/storage.js?version=v1.0"></script>
</head>
<body>

</body>
<script>
document.addEventListener('DOMContentLoaded', function() {
	if(isWeiXin()){
		  $('body').html('<p style="text-align:right;padding-right:10px;" id="hideMenuItems"><img src="${request.contextPath!}/office/mobile/qrCode/images/tips.png" alt=""></p>');
		//setTimeout(window.history.back(), 1500);
		storage.set(Constants.WECHAT_BACK_BOOL,'true');
	}else{
		window.location.href = '${path!}';
	}
}, false);


</script>
</html>