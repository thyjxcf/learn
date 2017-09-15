<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include "/common/public.ftl">
<title>baseSupportInit</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script language="javascript">
var buffalo=new Buffalo('');
buffalo.async = false; //同步执行 
function initPassportClient(sign){ 
	buffalo.remoteActionCall("remoteBaseSupportInit.action","initPassportClient",[],function(reply){
		var result=reply.getResult();
		showMsgSuccess("初始化成功");
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
    PassportClient初始化：与连接Passport时，才可以进行初始化
    <span class="input-btn1" ><button type="button" onclick="initPassportClient();">PassportClient初始化</button></span> 
    </td>
  </tr>
</table>
</form>
</body>
</html>


