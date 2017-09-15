<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript" src="${request.contextPath}/static/js/buffalo.js"></script>
<#include "/common/public.ftl">
<script language="JavaScript" src="${request.contextPath}/static/common/objectSelect.js"></script>

<script language="javascript">
function treeItemClick(id,type,name,parentId){
	//alert('注意：需要自己写方法覆盖 treeItemClick(id,type,name)\nid:'+id+'\nname:'+name+'\ntype:'+type+'\nparentId:'+parentId);
	submitObject('${idObjectId!}','${nameObjectId!}',id,name,<#if callback?exists>callbackObjects('${callback!}')<#else>null</#if>);
}

</script>
</head>

<body>  	
  	<input type="hidden" name="objectIds" value="${objectIds!}">
    	<#if useCheckbox=="true" && -1 == url!?index_of("checkbox")>
     	   <input type="hidden" name="checkbox" value="true">
     	</#if>
    <@html.tableDetail divClass="tree-div-class-tt">
    <tr class="first"><th class="tt">请选择</th></tr>
  	</@html.tableDetail>
  	<div class="table-all">
  	<@html.treeTableDetail divClass="tree-div-class">
  		<tr><td>
     	<form id="commonTreeForm" target="treeFrame" method="post">
     	   <input type="hidden" name="objectIds" value="${objectIds!}">
     	<#if useCheckbox=="true" && -1 == url!?index_of("checkbox")>
     	   <input type="hidden" name="checkbox" value="true">
     	</#if>
     	</form>
       <iframe name="treeFrame" id="treeFrame" marginwidth="0" allowTransparency="true"   marginheight="0" 
              frameborder="0" width="100%" SCROLLING = "auto">
	   </iframe>
     </td></tr>
  	</@html.treeTableDetail>
  	</div>

<div class="table1-bt t-center tree-div-class-bottom">
 <!--[if lte IE 6]>
<div style="position:absolute;z-index:-1;width:100%;height:100%;">  
    <iframe style="width:100%;height:100%;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe>  
</div> 
<![endif]-->
<#if useCheckbox=="true">
&nbsp;&nbsp;
<span class="input-btn1" onclick="javascript:submitObjects('${idObjectId}','${nameObjectId}',treeFrame.document.getElementsByName('ids'),treeFrame.document.getElementsByName('ids_names'),<#if callback?exists>callbackObjects('${callback!}')<#else>null</#if>)"><button type="button">确定</button></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</#if>   
<span class="input-btn1 close-btn"  onclick="javascript:cancelObjects('${idObjectId}','${nameObjectId}',<#if callback?exists>callbackObjects('${callback!}')<#else>null</#if>)"><button type="button">清除</button></span>
<span class="input-btn1 close-btn"  onclick="javascript:closeDiv();"><button type="button">关闭</button></span>
</div>

<#--
      	<#if useCheckbox=="true">
      	&nbsp;&nbsp;
        <a class="blue" href="javascript:submitObjects('${idObjectId}','${nameObjectId}',treeFrame.document.getElementsByName('ids'),treeFrame.document.getElementsByName('ids_names'),<#if callback?exists>callbackObjects('${callback!}')<#else>null</#if>)">确定</a>
        </#if>    
        &nbsp;&nbsp;
        <a class="blue" href="javascript:cancelObjects('${idObjectId}','${nameObjectId}',<#if callback?exists>callbackObjects('${callback!}')<#else>null</#if>)">取消选择</a>
        &nbsp;&nbsp;        
    
    <a class="blue" href="javascript:closeDiv()">关闭</a>
-->

<script language="javascript">
jQuery(document).ready(function(){
	jQuery("#treeFrame").height(jQuery("#_panel-pulic-window").height() 
		- jQuery('.tree-div-class-tt').height() -jQuery('.tree-div-class-bottom').height() + 5);
})

 var frm = document.getElementById("commonTreeForm");
 frm.action="${url}";
 frm.submit();
</script>

</body>
</html>