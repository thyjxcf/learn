<html>
<head>
<title>${webAppTitle}--主页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script language="JavaScript" src="${request.contextPath}/static/js/click.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/validate.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/prototype.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script language="javascript">
function gotoModule(moduleId){
	var buffalo = new Buffalo("");
	buffalo.async = false;
	buffalo.remoteActionCall("remoteStusysIndex.action","remoteGetModule",[moduleId],
	    function(reply){
		    var module = reply.getResult();
			if(null == module){
				alert("对不起，您没有访问该模块的权限!");
			}else{
				location.href= "${request.contextPath}/" + module.url;
				top.frameleft.location.href = "${request.contextPath}/${action.getText("eis.subsystem.homepage.left.postfix")}?appId=${appId?default(-1)}&moduleID=" + module.id + "&parentId="+module.parentid;			
			}
	});	

	
}

function gotoDir(curDir,dirCnt,pic,txt){
	for(var i=1;i<=dirCnt;i++){
		if(i == curDir){
			document.getElementById("dir_"+i).style.display = "";
		}else{
			document.getElementById("dir_"+i).style.display = "none";
		}
		if(null != pic){
			document.getElementById("dir").src = "images/"+pic;		
		}
		if(null != txt){
			document.getElementById("navText").innerHTML = txt;		
		}
	}
}
function selectdogTab(moduleId,i,navDivname){
	for(j=1;j<25;j++){
		var tdn=window.parent.window.document.getElementById("td"+j);
		if(tdn) tdn.className="left_menucomm";
	}
	
	var tdm=window.parent.window.document.getElementById("td"+i);
	if(tdm) tdm.className="left_menuonmouse";
	
	var navDiv=window.parent.window.document.getElementById("currentposition");
	if(navDiv) navDiv.innerHTML = navDivname;
	
	var buffalo = new Buffalo("");
	buffalo.async = false;
	buffalo.remoteActionCall("remoteStusysIndex.action","remoteGetModule",[moduleId],
	    function(reply){
		    var module = reply.getResult();
			if(null == module){
				alert("对不起，您没有访问该模块的权限!");
			}else{
				location.href= "${request.contextPath}/" + module.url;
				top.frameleft.location.href = "${request.contextPath}/system/homepage/indexleft.action?appId=${appId?default(-1)}&moduleID=" + module.id + "&parentId="+module.parentid;			
			}
	});	
}

function init(){
<#if subSystem?exists>
	document.getElementById("nav").style.display = "";
	<#if subSystem.code?default("") != "">
		<#if loginInfo.unitClass == 1>
			<#assign tmp = "Edu">
		<#else>
			<#assign tmp = "Sch">
		</#if>
		<#if subSystem.code = "system" || subSystem.code = "stusys" || subSystem.code = "coursereform"  || (subSystem.code = "diathesis" && tmp = "Sch") >
			<#import subSystem.code+tmp+"NavMacro.ftl" as sysNavMacro>
		<#else>
			<#import "navMacro.ftl" as sysNavMacro>
			document.location.href="${request.contextPath}/system/homepage/nav.action?appId=${appId?default(-1)}";
			return;
		</#if>
	</#if>	
<#else>	
	document.getElementById("nav").style.display = "none";
</#if>
	var tdm=window.parent.window.document.getElementById("navDiv");
	if(tdm) tdm.innerText="您当前所在位置：导航";
}
</script>
</head>
<body onload="init();">
<form name="form1" onsubmit="return false;">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="YecSpec" height="100%">    
  <tr>
  	<td height="100%" valign="top">
  	  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  	    <tr>
  	      <td id="nav" width="100%" height="100%" align="center" style="display:none">
	    	 <@sysNavMacro.navMacro/>
	      </td>
	    </tr>
	  </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
