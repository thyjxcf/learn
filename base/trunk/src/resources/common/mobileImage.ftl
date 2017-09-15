<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no,email=no" name="format-detection">
<meta content="fullscreen=yes,preventMove=no" name="ML-Config">
<title>图片展示</title>
</head>
<body>
<div style="position:absolute; width:100%; height:100%; z-index:-1; left:0; top:0;">
	<img src="${request.contextPath!}/common/open/base/showPicture.action?dirId=${dirId}&filePath=${filePath}" style="left:0; top:0;" height="100%" width="100%" style="no-repeat"/>
</div>
</body>
<script>
</script>
</html>