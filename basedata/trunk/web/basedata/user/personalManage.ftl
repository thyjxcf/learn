<html>
<head>
<title>修改密码</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="${request.contextPath}/static/css/layout.css" rel="stylesheet" type="text/css">
<script src="${request.contextPath}/static/js/pwdintensity.js" language="javascript" type="text/javascript"></script>
<script>
/**
 * 判断是否为空
 *	s：值
 */
function isBlank(s) {
    var re = /^\s*$/g;
    return re.test(s);
}
function checkForm(){
  var obj = document.getElementById("new_password");
  if (isBlank(obj.value)){
  	alert("密码不允许为空!");
  	return;
  }   
  //var repwd =/^[a-zA-Z0-9]{6,18}$/;
  var repwd =/^\S{6,18}$/;
  
  if(repwd.test(obj.value)) {  
  }
  else {
    //alert('密码必须是6-18位的英文(A-Z，a-z)或数字(0-9)。');
    alert('密码必须是6-18位的英文字母，数字0-9等非中文符号。');
    obj.focus();
    return false;
  }
  
  form1.submit();
}

function changePwdRank() {
	var obj = document.getElementById("new_password");
	if (obj) {
		var str = obj.value;
		var rank = PwdIntensity(str);
		printIntensity(rank);
	}
}

</script>
</head>
<body class="YecSpec_background">
     <table width="100%" align="center" border="0" heigth="100%" class="">
        <form name="form1" action="userAdmin-savaPersonal.action" method="post">
        <#assign loginInfo=session.getAttribute("${appsetting.loginSessionName}")> 
          <#if action.hasActionErrors()> 
          <tr>
            <td height="13" colspan="2"><div class="error" style="font-weight: bold;background: transparent;color: red;margin-right: 0; margin-bottom: 0px; margin-top: 0px"> 
                <img src="${request.contextPath}/static/images/iconWarning.gif"  alt="" class="icon" /> 
                <#list actionErrors as x> ${x} </#list> </div></td>
                <td></td>
          </tr>
          <#else>
          <tr>
            <td height="13" colspan="2"> </td>
            <td></td>
          </tr>
          </#if> 
          <#if action.hasActionMessages()> 
          <tr>
            <td height="13"  colspan="2" align="center"> <div class="error" style="font-weight: bold;background: transparent;color: blue;margin-right: 0; margin-bottom: 0px; margin-top: 0px"> 
                <#list actionMessages as x> ${x} </#list> </div></td>
          </tr>
          <tr>
          	<td colspan="2" align="center">
          		<input type="button" class="sys_button1" value="关闭" onclick="window.close();">
          	</td>
          </tr>
          <#else> 
           <tr>
            <td width="170" height="30" align="right">所在单位：</td>
            <td width="50%" height="30" >${loginInfo.unitName?if_exists}</td>
          </tr>
          <tr>
            <td height="30" align="right">登录帐号：</td>
            <td height="30" >${loginInfo.user.name?if_exists}</td>
          </tr>
          <tr> 
            <td height="30" align="right">原密码：</td>
            <td height="30"> 
              <input type="password" name="old_password" class="input_login" style="width:214px" value="" fieldtip="请输入原来的密码">
            </td>
          </tr>
          <tr> 
            <td height="30" align="right">新密码：</td>
            <td height="30"><input type="password" name="new_password" class="input_login" style="width:214px" onkeyup="return changePwdRank();" value="" fieldtip="请输入新密码">
            <INPUT id=passwordrank type=hidden value=2 name=_fmm.ed._0.ne>
			</DIV>
            </td>
          </tr>
          <tr> 
            <td height="30" align="right">密码安全度：</td>
            <td height="30" align="left"><DIV id=PasswordCheck 
			style="BACKGROUND-POSITION: 0px 0px; BACKGROUND-IMAGE: url(${request.contextPath}/static/images/password_check_bk.gif); WIDTH: 315px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 15px"></td>
          </tr>
          <tr> 
            <td height="30" align="right">确认新密码：</td>
            <td height="30"><input type="password" name="new_password_c" class="input_login" style="width:214px" value="" fieldtip="请再次输入新密码"></td>
          </tr>
          <tr>
            <td height="50"  align="right"><input name="button2" type="button" class="del_button1" onClick="checkForm()" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" value="确定"></td>
            <td height="50" align="center"><input name="button2" type="button" class="del_button1" onClick="javascript:window.close();" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" value="关闭"></td>          
          </tr>
          </#if> 
       </form>
    </table>
</body>
</html>