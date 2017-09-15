<html>
<title>${pageTitle?default("")}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script language="JavaScript" src="${request.contextPath}/static/js/prototype.js"></script>
<script language="javascript">
 /*分类按钮样式定义*/
function selectTab(tab) {
	var menus = document.getElementById("menus").getElementsByTagName("td");
	//根据<td>的id定义对象
	var _select_menu = document.getElementById("menu" + tab);
	for (var i = 0; i < menus.length; i++) {
	   //如果点击的是"style_menu3"则不做操作，如果没点击"style_menu3"，则将它设为"style_menu2"
		if ("style_menu3" == menus[i].className) {
			menus[i].className = "style_menu2";
			break;
		}
	}
	//将点击了的<td>设为"style_menu3"
	if (_select_menu) {
		_select_menu.className = "style_menu3";
	}
}
var currentTab;
function changeTab(tab) {
	currentTab = tab;
	selectTab(tab);
	
	var url = "";
	<#if tabList?exists> 
		switch (tab) {
		<#list tabList as x>			
		    case ${x_index} :
		    	url = "${request.contextPath}/leadin/import/templateConfig.action";
		    	document.getElementById("templateRemark").value="${x[1]}";
		    	document.getElementById("importFile").value="${x[2]}";
		    	document.getElementById("objectName").value="${x[3]}";
				break;
		</#list>
			default :				
				break;
		}
	</#if>
	if ("" != url) {
		var frm = document.form1;
		frm.action=url;
		frm.submit();
		//document["iframe"].location.href = url;
	}
}

function init(){
	<#if tabList?exists>
		 changeTab(0);
	</#if>
}
	
</script>
<body id="body" onclick="javascript:switchDiv(event)" onload="init();">
<form name="form1" target="iframe">
<input type="hidden" id="templateRemark" name="templateRemark" value="">
<input type="hidden" id="importFile" name="importFile" value="">
<input type="hidden" id="objectName" name="objectName" value="">
<input type="hidden" id="templateConfig" name="templateConfig" value="true">
<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="YecSpecNoTop" height="100%">
	<tr>
		<td class="padding_top2">
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="27" background="${request.contextPath}/static/images/stylemenu_zbg.gif" valign="bottom">
						<table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
							<tr id="menus">
								<#if tabList?exists> 
									<td width="3">&nbsp;</td>
									<#list tabList as x>			
									    <#if x_index == 0>
									    <td id="menu${x_index}" class="style_menu3" onclick="javascript:changeTab(${x_index})"><a>${x[0]}</a></td>
									    <#else>
									    <td id="menu${x_index}" class="style_menu2" onclick="javascript:changeTab(${x_index})"><a>${x[0]}</a></td>
									    </#if>
									</#list>									
								</#if>
								<td>&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
   	<tr>
		<td width="100%" height="100%" valign="top">
			<iframe id="iframe" name="iframe" allowTransparency="true" frameBorder="0" width="100%" height="100%" scrolling="no" src=""></iframe>
		</td>
	</tr>
</table>
</form>
</body>
</html>

