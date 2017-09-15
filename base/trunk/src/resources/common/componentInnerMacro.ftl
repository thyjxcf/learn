<#macro commonObjectTreeDiv leftUrl="" rightUrl="" codeChinese="" nameChinese="" supportFullQuery="false" codeType=2>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<script language="JavaScript" src="${request.contextPath}/static/common/objectSelect.js"></script>
<script>
function searchKeyDownObject(){
    if (event.keyCode != 13) return;   
    
    searchObject();
}

function searchObject(){
	document.getElementById("letter").value = "";//清空	
	
	var queryObjectName = trim($F("queryObjectName"));
	var queryObjectCode = trim($F("queryObjectCode"));
	<#--不支持全单位查询需要判断查找条件-->
	<#if supportFullQuery=="false">
    if(queryObjectName == "" && queryObjectCode == ""){
		showMsgWarn("请输入查找条件","","");	  	
	    return;
	}
	</#if>
	doSearchObject();
}

function doSearchObject(){	
    //searchObjectForm.submit();
    //firefox表单提交有问题因此改成以下模式 兼容ie和firefox modify by zhuw 20130418
	document.searchObjectForm.submit1.click();
}

<#if useCheckbox=="true" && objectIds! !="">
var objectIds = "${objectIds!}";
var objectNames = document.getElementById("${nameObjectId}").value;
//初始化姓名列表
initSelection(objectIds,objectNames);
</#if>	
//searchObjectForm.submit();
//firefox表单提交有问题因此改成以下模式 兼容ie和firefox modify by zhuw 20130418
document.searchObjectForm.submit1.click();
</script>
<body leftmargin="0" topmargin="0" >
<div class="dtree-box" id="resizeColumn" <#if leftUrl="">style="display:none"</#if>>
	<iframe name="treeframe" id="treeframe" marginwidth="0" class="dtreeFrame" marginheight="0" src="${leftUrl}" style="height:425px;"
		frameborder="0" width="100%" height="100%" allowTransparency="true" SCROLLING = "auto">
	</iframe>
</div>
<form name="searchObjectForm" method="post" action="${rightUrl}" target="mainframe">			
	<input type="submit" name="submit1" style="display:none">
	<input type="hidden" name="letter" id="letter" value="">
	<input type="hidden" name="groupId" value="${groupId!}">
	<input type="hidden" name="codeType" value="${codeType!}">			
	<input type="hidden" name="iframe" value="true">
	<input type="hidden" name="queryType" value="${queryType!}">
	<input type="hidden" name="showLetterIndex" value="${showLetterIndex?string}">			
	
	<input type="hidden" name="idObjectId" value="${idObjectId}">
	<input type="hidden" name="nameObjectId" value="${nameObjectId}">
	<input type="hidden" name="objectIds" id="objectIds" value="${objectIds!}">
	<input type="hidden" name="useCheckbox" value="${useCheckbox?string}">
	<input type="hidden" name="callback" value="${callback!}">
	<div class="head-tt" id="queryObjectTable">
	    <div class="tt-le">
	    	<#if codeChinese! !="">
	    	${codeChinese}：
			<input id="queryObjectCode" name="queryObjectCode" type="text" size="15" onkeydown="searchKeyDownObject(this)"/>
			<#else>
			<input id="queryObjectCode" name="queryObjectCode" type="hidden" value=""/>
			</#if>
			<#if nameChinese! !="">
			${nameChinese}：
			<input id="queryObjectName" name="queryObjectName" type="text" size="15" onkeydown="searchKeyDownObject(this)" value="" />
	 		<#else>
	 		<input id="queryObjectName" name="queryObjectName" type="hidden" value="">
	 		</#if>
	 		<span class="input-btn1" onclick="searchObject();"><button type="button">查找</button></span> 
		</div>
	</div>
</form>
<iframe name="mainframe" id="mainframe" marginwidth="0" allowTransparency="true" marginheight="0" 
	src="" frameborder="0" style="width:480px;height:400px;" SCROLLING = "no"></iframe>
<div id="maindiv">
<#if useCheckbox=="true">
   <b>已选择：</b>(小提示：双击姓名可删除)
	&nbsp;&nbsp;
	<span class="input-btn2" id="comfirm"><button type="button">确定</button></span>&nbsp;&nbsp;
	<span class="input-btn2" onclick="javascript:clearAll();"><button type="button">清空</button></span>
	<div id="nameList" style="height:80px;overflow-y:auto;"></div>
</#if>
</div>
</body>
<script>
//重新计算mainframe的高度
/*function computeHeight(){
	jQuery("#mainframe").height(jQuery("#_panel-pulic-window").height() - jQuery('.head-tt').height()- jQuery('#maindiv').height()+6);
	mainframe.computeListDivHi();
}*/
jQuery(document).ready(function(){
	jQuery(".dtreeFrame").height(jQuery("#_panel-pulic-window").height()+6);
	jQuery("#mainframe").height(jQuery("#_panel-pulic-window").height() - jQuery('.head-tt').height()- jQuery('#maindiv').height()+6);
})
jQuery("#comfirm").click(function(){
	submitObjects2('${idObjectId}','${nameObjectId}',selection,<#if callback?exists>callbackObjects('${callback!}')<#else>null</#if>);
});
</script>
</html>
</#macro>
