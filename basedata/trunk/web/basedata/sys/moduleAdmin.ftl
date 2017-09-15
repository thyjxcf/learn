<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${webAppTitle}--后台管理</title>
<#assign actionUrl="${request.contextPath}/basedata/sys/module-list.action">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/util.js"></script>

<script>
function onQuery(){
	var unitclass=$F('unitclass');
	var subSystemId=$F('subSystemId');
	if(unitclass==""&&subSystemId==""){
		alert("请确保至少选择了一个条件");
		return ;
	}
	var url="${actionUrl}?unitClass="+unitclass+"&subSystemId="+subSystemId;
	//alert("####:"+url);
	planframe.location=url;
}
 jQuery(document).ready(function(){
	jQuery("#planframe").height(jQuery(".mainFrame", window.parent.document).height() - jQuery('.head-tt').height());
	planframe.location.href="${actionUrl}";
})
</script>
</head>
<body >
<div class="head-tt head-tt1">
<div class="tt-le">
单位类型：<select name="unitclass" id="unitclass" class="input-sel">
				  	${appsetting.getMcode("DM-DWFL").getHtmlTag(unitClass?default(''))}
				  	</select>
子系统：<select name="subSystemId" id="subSystemId" class="input-sel">
		  	  			<option value=''>--请选择--</option>
		  	  		<#list subSysList as x>
				  		<option value='${x.id}' <#if subSystemId==x.id?string>selected</#if> >${x.name}</option>
				  	</#list>
				  	</select>
</div>
<div class="f-left mt-5 ml-30">
    	<span class="input-btn1" onClick="onQuery();"><button type="button">查询</button></span>
</div>
<div class="clr"></div>
</div>
			<iframe name="planframe"id="planframe" marginwidth=0 marginheight=0 frameborder="0" height="100%" width="100%" 
				src="" SCROLLING = "no"/>

</body>
</html>



