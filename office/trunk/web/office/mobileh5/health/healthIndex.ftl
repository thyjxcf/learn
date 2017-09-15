<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no,email=no" name="format-detection">
<meta content="fullscreen=yes,preventMove=no" name="ML-Config">
<title>首页</title>
<script src="${request.contextPath}/static/html5/js/flexible_css.debug.js"></script>
<script src="${request.contextPath}/static/html5/js/flexible.debug.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/html5/css/style.css">
</head>

<body class="home">
<div id="page">
</div>

<script src="${request.contextPath}/static/html5/js/jquery-1.9.1.min.js"></script>
<script src="${request.contextPath}/static/html5/js/myscript.js"></script>
<script type="text/javascript" src="${request.contextPath!}/static/html5/js/public.js"></script>
<script type="text/javascript" src="${request.contextPath!}/static/html5/js/storage.js"></script>
<script type="text/javascript" src="${request.contextPath!}/static/html5/js/weike.js"></script>
<script type="text/javascript" src="${request.contextPath!}/static/html5/js/myWeike.js"></script>
<script src="${request.contextPath!}/office/mobileh5/health/js/wapEntities.js"></script>

<script>
document.addEventListener('DOMContentLoaded', function() {
	storage.remove(WapConstants.OWNER_ID);
	storage.remove(Constants.MOBILE_CONTEXT_PATH);

	var ownerId = '${ownerId!}';
	var contextPath = '${request.contextPath!}';
	storage.set(WapConstants.OWNER_ID, ownerId);
	storage.set(Constants.MOBILE_CONTEXT_PATH, contextPath);

	//微课接入标识
	storage.set(WeikeConstants.WEIKE_FLAG_KEY, WeikeConstants.WEIKE_FLAG_VALUE_TYPE_1);
	
	//跳转消息
	location.href = contextPath + "/office/mobileh5/health/healthDetail.html?ownerId="+ownerId;
}, false);
</script>
</body>
</html>