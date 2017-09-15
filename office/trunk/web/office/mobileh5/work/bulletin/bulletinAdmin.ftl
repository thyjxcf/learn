<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no,email=no" name="format-detection">
<meta content="fullscreen=yes,preventMove=no" name="ML-Config">
<title>通知公告</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath!}/static/html5/css/style.css?version=v1.0">
<script src="${request.contextPath!}/static/html5/js/flexible_css.debug.js"></script>
<script src="${request.contextPath!}/static/html5/js/flexible.debug.js"></script>
<script src="${request.contextPath!}/static/html5/js/jquery-1.9.1.min.js"></script>
<script src="${request.contextPath!}/static/html5/js/jquery.Layer.js"></script>
<script src="${request.contextPath!}/static/html5/js/common.js?version=v1.0"></script>
<script src="${request.contextPath!}/static/html5/js/public.js?version=v1.0"></script>
<script src="${request.contextPath!}/static/html5/js/storage.js?version=v1.0"></script>
<script src="${request.contextPath!}/static/html5/js/myscript.js?version=v1.0"></script>
<script src="${request.contextPath!}/static/html5/js/validate.js?version=v1.0"></script>

<script src="${request.contextPath!}/office/mobile/bulletin/js/wap.js?version=v1.0"></script>
<script src="${request.contextPath!}/office/mobile/bulletin/js/wapEntities.js?version=v1.0"></script>
<script src="${request.contextPath!}/office/mobile/bulletin/js/wapNetwork.js?version=v1.0"></script>
<script src="${request.contextPath!}/office/mobile/bulletin/js/wapNetworkService.js?version=v1.0"></script>
</head>
<body class="fn-rel">
</body>
<script>
document.addEventListener('DOMContentLoaded', function() {
	var unitId = '${unitId!}';
	var userId = '${userId!}';
	var contextPath = '${request.contextPath!}';
    setTimeout(wap.init(contextPath, unitId, userId), 200)
}, false);
</script>
</html>
