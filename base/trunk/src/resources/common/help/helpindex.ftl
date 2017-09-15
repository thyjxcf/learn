<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>帮助</title>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jscroll.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/switch.js"></script>
</head>
<script>
jQuery.noConflict();

function command_back() {
	window.history.go(-1);
}
function command_forward() {
	window.history.go(1);
}

function loadHelp(action){
	jQuery("#helpIframe").attr("src","${request.contextPath}"+action);
}

jQuery(document).ready(function(){
	<#assign mainUrl = systemVersion.productId?lower_case +".htm">
	<#if "" != subSystem?default("") && (platform == stack.findValue("@net.zdsoft.eis.base.constant.BaseConstant@PLATFORM_STUPLATFORM") ||
		 platform == stack.findValue("@net.zdsoft.eis.base.constant.BaseConstant@PLATFORM_BACKGROUND")) >
		<#assign mainUrl = request.contextPath + subSystem.getHelpPath(platform) + mainUrl>
	<#else>
		<#assign mainUrl = request.contextPath + '/help/' + mainUrl>
	</#if>
	jQuery(".help-content").load("${mainUrl?default("")}");
})
</script>
<body class="frm auto">
<div class="header-warp">
<div id="header" class="help-header">
	<div class="name">
    </div>
    <ul class="set-info">
    </ul>
</div>
</div>
<div class="grid-404">
	<p class="dt"></p>
    <div class="help" id="help">
        <div class="slider-tree">
           	<iframe id="treeIframe" src='tree.action?appId=${appId?default(-1)}&platform=${platform}&moduleID=${moduleID?default("")}' frameborder="0" style="width:165px;height:599px;" ></iframe>
        </div>
        <iframe id="helpIframe" name="helpIframe" marginwidth="0" allowTransparency="true" style="width:815px;width:808px\0;height:600px;"
              frameborder="0" width="100%" SCROLLING = "no"></iframe>
	   <div id="help_btn1" class="help-page" style="display:none;">
		   <a onclick="command_back();" href="javascript:void(0);" ><img src="${request.contextPath}/static/images/help_prev2.gif" alt="上一页" /></a>&nbsp;&nbsp;
		   <a onclick="command_forward();" href="javascript:void(0);"><img src="${request.contextPath}/static/images/help_next2.gif" alt="下一页" /></a>
	   </div>      
    </div>    
    <p class="bt"></p>
</div>
</body>
</html>