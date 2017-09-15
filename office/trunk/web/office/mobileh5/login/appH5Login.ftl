<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no,email=no" name="format-detection">
<meta content="fullscreen=yes,preventMove=no" name="ML-Config">
<title>登录</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath!}/static/html/css/style.css">
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${request.contextPath!}/static/html/js/flexible_css.debug.js"></script>
<script src="${request.contextPath!}/static/html/js/flexible.debug.js"></script>
<script src="${request.contextPath!}/static/html/js/jquery-1.9.1.min.js"></script>
<script src="${request.contextPath!}/static/html/js/jquery.Layer.js"></script>
<script src="${request.contextPath!}/static/html/js/public.js"></script>
<script src="${request.contextPath!}/static/html/js/storage.js"></script>
<script src="${request.contextPath!}/static/html/js/myscript.js"></script>
<script src="${request.contextPath!}/static/html/js/validate.js"></script>

</head>
<body class="fn-rel">
</body>
<script>
document.addEventListener('DOMContentLoaded', function() {
	var contextPath = '${request.contextPath!}';
    storage.set(Constants.MOBILE_CONTEXT_PATH,contextPath);
    location.href = contextPath + "/office/mobileh5/login/login.html";
}, false);
</script>
</html>
