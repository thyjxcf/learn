<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include "/common/public.ftl">

<title>系统运行参数</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script language="javascript">
var buffalo=new Buffalo('');
buffalo.async = false; //同步执行 

function setPermissionCheckNoRuleSkip(sign){ 
	buffalo.remoteActionCall("remoteRunParam.action","setPermissionCheckNoRuleSkip",[sign],function(reply){
		var result=reply.getResult();
		showMsgSuccess("设置成功");
		window.setTimeout("document.location.href = document.location.href;",3000);
	}); 	
} 

</script>
<body>

<form  name="form1">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:10">
  <tr>
    <td valign="top">
    <br/>
    &nbsp;&nbsp;<span class="input-btn1" ><button type="button" onclick="document.location.href ='${request.contextPath}/basedata/account/userAccountIdInit.action';">userAccountId初始化</button></span> 	
    &nbsp;&nbsp;<span class="input-btn1" ><button type="button" onclick="document.location.href ='${request.contextPath}/basedata/account/initPassportAccountAdmin.action';">顶级管理员初始化到passport</button></span> 	
    </td>
  </tr>
</table>
</form>
</body>
</html>

