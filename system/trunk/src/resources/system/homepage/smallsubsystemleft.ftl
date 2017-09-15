<html>
<head>
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<link href="${request.contextPath}/static/system/homepage/css/subsystem.css" type="text/css" rel="stylesheet">
<SCRIPT type="text/javascript" src="${request.contextPath}/static/system/js/click.js"></SCRIPT>
<SCRIPT language=javascript src="${request.contextPath}/static/system/js/preparedUtil.js" type=text/javascript></SCRIPT>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="YecSpec2">
 <tr>
 <td width="155" height="100%" valign="top" id="leftframe" class="left_bttbg"><table width="155" border="0" cellspacing="0" cellpadding="0" height="100%">
            <tr>
              <td valign="top" height="100%">
				<table cellSpacing=0 cellPadding=0 width=155 border=0>
			  <#if !topModules?exists || topModules.size() == 0>
				  <tbody id=tabs></tbody>
			  <#else>
				<#assign j = 1>
				<#list topModules as module>
				  <tbody>
				    <tr>
				      <td class="left_menutitle" id="headtitle" onclick="menu_display(${module_index});currentID=${module.id}">${module.name}</td>
				    </tr>
				    <#assign secondModuleList = secondModules.get(module.getId())>
				    <#if secondModuleList?default("") != "" >
				    <tbody id="tabs">
				    <tr>
				      <td class="left_contentbg"><table cellSpacing="0" cellPadding="0" width="90%" align="right" border="0">
				       <tbody>
				         <#list secondModuleList as secModule>
				            <tr>
				              <td><table width="90%" border="0" cellspacing="0" cellpadding="0" class="left_menutab">
				                <tr>
				                <#assign pic=secModule.picture?replace('images','smallimages')/>
				                <#if secModule.picture?contains("../")>
							  		<#assign imagesUrl="../"+secModule.picture>
							  	<#else>
							  		<#assign imagesUrl="../../stusys/"+secModule.picture>
							  	</#if>
							  	<#if imagesUrl?contains("img")>
							  		<#assign smallimagesUrl=imagesUrl?replace('img','smallimg')>
							  	<#else>
							  		<#assign smallimagesUrl=imagesUrl?replace('images','smallimages')>
							  	</#if>
				                  <td width="22"><img  src="${smallimagesUrl}" width="18" height="18"></td>
				                  <#assign addToTopUrl="" />
				                    <#if secModule.url?contains("?")>
								  		<#assign tUrl=secModule.url+"&moduleID="+secModule.id>
								  		<#assign addToTopUrl=addToTopUrl + secModule.url+"&moduleID="+secModule.id+"&parentId="+secModule.id>
								  	<#else>
								  		<#assign tUrl=secModule.url+"?moduleID="+secModule.id>
								  		<#assign addToTopUrl=addToTopUrl + secModule.url+"?moduleID="+secModule.id+"&parentId="+secModule.id>
								  	</#if>
				                  <td id="td${j}" modName="${secModule.name?default("")}" modID="${secModule.mid?default("")}" class="left_menucomm" onClick="selectdogTab(${j},'${secModule.name}');currentID=${secModule.id};">
				                    <a onClick="javascript: addUrlToTop('${addToTopUrl}','${secModule.id}', '${secModule.name}', '${secModule.subsystem}', '${secModule.picture?default("")}');selectModule('${secModule.id}','${(secModule.limit)?default('0')}','${module.name}','${secModule.name}');" 
				                    href="${request.contextPath}/${tUrl}&appId=${secModule.subsystem}&modelId=${secModule.modelId!}" target="mainframe">${secModule.name}</a></td>
				                </tr>
				              </table></td>
				            </tr>
				            <#assign j = j+1>
				         </#list>
				         </tbody>          
				        </table></td>
				    </tr>
				    </#if>
				  </tbody>
				</#list>
			  </#if>
				</table>
				<SCRIPT language="javascript">
				<!--
					function initHide() {
						var tabslength = tabs.length;
						for (var i = 0; i < tabslength; i++) {
							tabs[i].style.display="none";
						}
					}
					initHide();
					menu_display(0);
				//-->
				</SCRIPT>
              </td>
            </tr>
        </table></td>
 </tr>
</table>
</div>
<script>

<!-- 2007-04-10 zhaosf 当前模块id、子系统号-->
var currentID;
function getCurrentID(){
	return currentID;
}
<!--    end   -->

function selectModule(current,fullscrean,sectionName, moduleName){
	currentID = current;
	parent.changeNavigation2(sectionName, moduleName);	

	if(fullscrean=='1')parent.switchSysBar();
	
	if (parent.document.getElementById("displayFavorite").style.display = "none"){
		parent.document.getElementById("displayFavorite").style.display = "";
	}
	
}
function addUrlToTop(url, id, name, subsystem, picture){
	parent.addUrl(url, id, name, subsystem, picture);
}


/*左侧菜单点击切换样式*/
function selectdogTab(i,value){
	for(j=1;j<100;j++){
		var tdn=document.getElementById("td"+j);
		if(tdn) tdn.className="left_menucomm";
	}
	
	var tdm=document.getElementById("td"+i);
	if(tdm) tdm.className="left_menuonmouse";
	
	 changeCurrentName(value);
 }
 
</script>
</body>
</html>