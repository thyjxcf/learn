<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教职工导入</title>
<#include "/common/public.ftl">
<script>
function setObjectName(objectName){
	teacherImportFrame.location.href="teacherAdmin-importMain.action?objectName="+objectName;
}
</script>
</head>

<body>
<div class="tab-bg">
	<ul class="tab-dt">
    	<li class="current li-1" onclick="setObjectName('${objectName}');"><span>教职工导入</span></li>
    </ul>
</div>

<iframe name="teacherImportFrame" id="teacherImportFrame" class="teacherImportFrame" src="teacherAdmin-importMain.action?objectName=${objectName}" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" width="100%"  height="100%"></iframe>

</body>
<script>

jQuery(function(){
	jQuery("#teacherImportFrame").height(jQuery(".mainFrame", window.parent.document).height()-jQuery(".tab-bg").height());
})
</script>
</html>
