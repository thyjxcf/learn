<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>DBCP的状态</title>
<script language="javascript">
 jQuery(document).ready(function(){
	jQuery(".table-content").height(jQuery("#subIframe", window.parent.document).height()-jQuery(".head-tt").height()-jQuery(".table1-bt").height()-5);
})
</script>
</head>
<body>
<div class="head-tt">
<div class="tt-le">	 
</div>
<div class="f-right mt-5 mr-30">
    	<span class="input-btn2" onClick="javascript:window.location.reload();"><button type="button">刷新</button></span>
</div>
<div class="clr"></div>
</div>
<@common.tableList>
<#list dbcpStatus as b>
<tr>
<td>${b}</td>
</tr>
</#list>
</@common.tableList>  
<@common.ToolbarBlank>
</@common.ToolbarBlank>
</body>
</html>