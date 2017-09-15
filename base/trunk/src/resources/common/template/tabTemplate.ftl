<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>${pageTitle?default("")}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/switch.js"></script>
<#include "/common/public.ftl">
<script language="javascript">
jQuery.noConflict();
function changeTab(tab) {
	var url = "";
	<#if tabList?exists> 
		switch (tab) {
		<#list tabList as x>			
		    case ${x_index} :
		    	url = "${request.contextPath}/${x[1]}";
				break;
		</#list>
			default :
				url = "${request.contextPath}/${tabList[0][1]}";
				break;
		}
	</#if>
	if ("" != url) {
		iframe.location = url;
	}
}

jQuery(document).ready(function(){
	jQuery("#iframe").height(jQuery(".mainFrame",window.parent.document).height()-jQuery(".tab-bg").height());
	<#if tabList?exists>
		 changeTab(0);
	</#if>
})

</script>
<body >
<form  name="form1" >
				<div class="tab-bg">
					<ul class="tab-dt" >
						<#if tabList?exists>
						<#list tabList as x> 
							<#if x_index == 0>
					    		<li  class="current"><span  onclick="javascript:changeTab(${x_index});">${x[0]}</span></li>
					    	<#else>
					    		<li id="menu${x_index}" ><span onclick="javascript:changeTab(${x_index});">${x[0]}</span></li>
							</#if>
						</#list>
						</#if>	
					</ul>
				</div>
	<iframe id="iframe" name="iframe" allowTransparency="true" frameBorder="0" width="100%" height="100%" scrolling="no" src=""></iframe>
</form>
</body>
</html>

