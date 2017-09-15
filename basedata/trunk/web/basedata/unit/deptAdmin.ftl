<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "../../common/commonmacro.ftl" as common>
<html>
<head>
<title>${webAppTitle}--部门管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<SCRIPT src="${request.contextPath}/static/js/table-split-resize.js"></SCRIPT>
<script>
function treeItemClick(id,name,unitId){
	var str;
	if(id){
		str = "deptId="+id;
	}
	if(name){
		str += "&deptName="+name;
	}
	if(unitId){
		str += "&unitId="+unitId;
	}
	document.getElementById("deptListFrame").src = "deptAdmin-list.action?modID=${modID?default('')}&" +str;
}
</script>
</head>
<body>
<table width="100%" height="100%" cellspacing="0" cellpadding="0" class="YecSpec">
	<tr>
		<td>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" height="100%">			  	    		
	   			<tr>
				    <td id="resizeColumn" width="150px" height="100%" class="">
				        <iframe  name="deptTreeFrame" marginwidth="0" allowTransparency="true"
					            marginheight="0" src="${request.contextPath}/common/xtree/depttree.action?unitId=${unitId?default('')}&&modID=${modID?default('')}" 
					            frameborder="0" width="100%" height="100%">
					    </iframe>
				    </td>
				    <TD>
				      <DIV onmouseup="MouseUpToResize(this)"; class="resizeDivClass" 
				      onmousemove="MouseMoveToResize(this,'resizeColumn');" 
				      onmousedown="MouseDownToResize(this,'resizeColumn');"></DIV></TD>				    
				    <td border="0" cellspacing="0" cellpadding="0" height="100%">
				        <iframe name="deptListFrame" marginwidth="0" allowTransparency="true" 
				        		marginheight="0" src="" frameborder="0" width="100%" height="100%">
					    </iframe>
				    </td>
				</tr>				
			</table>
		</td>
	</tr>
</table>
</body>
</html>