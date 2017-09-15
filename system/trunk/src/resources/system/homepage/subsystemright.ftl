<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/switch.js"></script>
</head>
<!--说明：
	1、线条：line1-单平行线条；line2-水平十字；line3-上十字；line4-上封闭最顶部；line5-下十字；line6-下封闭最底部；
    2、背景状态：state1-开始和结束；state2-有链接；state3-无链接
-->
<body>
<#if flowDiagramList?exists && 0<flowDiagramList?size> 
<div class="backlog">
	<#list flowDiagramList! as diagram>
    <div class="flow-box">
    	<div class="dt">
    		<span>${diagram.tfdName!}</span>
    		<img src="${request.contextPath}/static/images/arr_top.gif" class="arr-top" <#if diagram_index != 0>style="display:none;"</#if> />
	    	<img src="${request.contextPath}/static/images/arr_bottom.gif" class="arr-bottom" <#if diagram_index == 0>style="display:none;"</#if> />
    	</div>
        <div class="dd" <#if diagram_index != 0> style="display:none;"</#if>>
        	 ${diagram.html!}
        </div>
    </div>
    </#list>
</div>
<#else>
<div class="welcome">欢迎使用${subsystemName!}</div>
</#if>
</body>
</html>