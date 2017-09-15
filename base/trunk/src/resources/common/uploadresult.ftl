<!--
	Product: 城域综合信息平台 CNet3.0 -- 办公子系统
	Company: ZDSoft
	Content: 在日程添加或修改时，若上传附件大于系统可上传附件的文件大小时，将返回此页面进行提示用户
//-->
<html>
<head><title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<script language="javascript">
function init(){
	parent.doAfterSubmit('${fileerror?default('0')}');
}
</script>
<body onLoad="init()">
</body>
</html>