<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no,email=no" name="format-detection">
<meta content="fullscreen=yes,preventMove=no" name="ML-Config">
<title>报修管理</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath!}/static/html5/css/style.css">
<script src="${request.contextPath!}/static/html5/js/flexible_css.debug.js"></script>
<script src="${request.contextPath!}/static/html5/js/flexible.debug.js"></script>
<script src="${request.contextPath!}/static/html5/js/jquery-1.9.1.min.js"></script>
<script src="${request.contextPath!}/static/html5/js/jquery.Layer.js"></script>
<script src="${request.contextPath!}/static/html5/js/common.js"></script>
<script src="${request.contextPath!}/static/html5/js/public.js"></script>
<script src="${request.contextPath!}/static/html5/js/storage.js"></script>
<script src="${request.contextPath!}/static/html5/js/myscript.js"></script>
<script src="${request.contextPath!}/static/html5/js/validate.js"></script>

<script src="${request.contextPath!}/office/mobile/repair/js/wap.js"></script>
<script src="${request.contextPath!}/office/mobile/repair/js/wapEntities.js"></script>
<script src="${request.contextPath!}/office/mobile/repair/js/wapNetwork.js"></script>
<script src="${request.contextPath!}/office/mobile/repair/js/wapNetworkService.js"></script>
<script src="${request.contextPath!}/static/html5/js/weike.js"></script>
<script src="${request.contextPath!}/static/html5/js/myWeike.js"></script>
</head>
<body class="fn-rel">
</body>
<script>

document.addEventListener('DOMContentLoaded', function() {
	storage.set(WeikeConstants.WEIKE_FLAG_KEY, '');
	var unitId = '${unitId!}';
	var userId = '${userId!}';
	var contextPath = '${request.contextPath!}';
	storage.set(WapConstants.AUDIT_MODE,WapConstants.BOOLEAN_0);
	<#if manager>
		storage.set(WapConstants.AUDIT_MODE,WapConstants.BOOLEAN_1);
	</#if>
	wap._listService.doRemoveCache();
	setTimeout(wap.init(contextPath, unitId, userId), 200);
	
}, false);
</script>
</html>
