<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${bulletin.title!}</title>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.8.3.min.js"></script>
</head>
<body>
<#if officeTempComment?exists>
	${officeTempComment.htmlContent!}
<#else>
	模板不存在或已删除
</#if>
</body>
<script>
	<#if officeTempComment?exists>
	$(document).ready(function(){
		$("#title").html('${bulletin.title!}');
		$("#content").html('${bulletin.content!}');
		$("#messageNumber").html('${bulletin.messageNumber!}');
	});
	</#if>
</script>
</html>