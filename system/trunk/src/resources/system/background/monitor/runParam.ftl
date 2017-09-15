<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>系统运行参数</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<#--buffalo-->
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script language="javascript">
var buffalo=new Buffalo('');
buffalo.async = false; //同步执行 

function setPermissionCheckNoRuleSkip(sign){ 
	buffalo.remoteActionCall("remoteRunParam.action","setPermissionCheckNoRuleSkip",[sign],function(reply){
		var result=reply.getResult();
		showMsgSuccess("设置成功");
		window.setTimeout("document.location.href = document.location.href",1000);
		//document.location.href = document.location.href;
	}); 	
} 

</script>
<body>

<form  name="form1">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td valign="top">
    权限检查时不符合规则(主链接-子链接)时是否忽略：${permissionCheckNoRuleSkip?string}<br>
    <span class="input-btn2" onClick="javascript:setPermissionCheckNoRuleSkip(true);"><button type="button">忽略</button></span>
    <span class="input-btn2" onClick="javascript:setPermissionCheckNoRuleSkip(false);"><button type="button">不忽略</button></span>
    </td>
  </tr>
</table>
</form>
</body>
</html>

