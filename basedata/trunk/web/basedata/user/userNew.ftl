<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<html>
<head>
<title>${webAppTitle}--新增用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/validate.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/prototype.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/pwdintensity.js"></script>
<script>
function validateform(){
	if(!checkElement(document.getElementById('name'),'账号')){
		return false;
	}
	else if(!checkInteger(document.getElementById('orderid'),'排序编号')){
		return false;
	}
	else if(document.getElementById('password').value!=document.getElementById('confirmPassword').value){
		addFieldError('confirmPassword','请确认密码填写一致');
		addFieldError('password','请确认密码填写一致');
		document.getElementById('password').value='';
		document.getElementById('confirmPassword').value='';
		return false;
	}
	else if(!checkOverLen(document.getElementById('realname'),'${userRealNameLength}','姓名')){
		return false;
	}
	else if(document.getElementById('mark').value==''){
		addFieldError('mark','请选择用户状态');
		return false;
	}
	return true;
}
function cancel(){
	window.location.href="userAdmin.action?modID=${modID?default('')}&&ec_p=${ec_p?default()}&&ec_crd=${ec_crd?default()}";
}
function changeDept(){
	var buffalo=new Buffalo('');
	
	var deptid=document.getElementById('deptid').value;
	var tchselect=document.getElementById('teacherid');
	
	buffalo.remoteActionCall("userAdmin-service.action","getTeacherByDeptId",[deptid],function(reply){
		var result=reply.getResult();
		if(result!=null){
			tchselect.options.length=0;
			tchselect.options[0]=new Option('--请选择关联职工--','');
			for(var i=1;i<=result.length;i++){
				tchselect.options[i]=new Option(result[i-1].name,result[i-1].id);
			}
		}
	});
}
function checkName(){
	var name=document.getElementById('name').value;
	if(trim(name)==''){
		addFieldError('name','请输入账号');
		return ;
	}
	
	var buffalo=new Buffalo('');
	buffalo.remoteActionCall("userAdmin-service.action","validateUserNameAvaliable",[name],function(reply){
		var result=reply.getResult();
//		alert(result);
		if(result){
			addFieldError('name',result);
		}
		else{			
			addFieldSuccess(document.getElementById('name'),'恭喜您,'+name+'账号尚可用');
		}
	});
	return ;
}
function changeTeacher(elem){
	var index=elem.selectedIndex;
	var realname=document.getElementById('realname');
	if(realname){
		realname.value=elem.options[index].text;
	}
}
</script>
</head>
<body>
<form action="userAdmin-insert.action?modID=${modID?default('')}&&ec_p=${ec_p?default()}&&ec_crd=${ec_crd?default()}" method="POST" name="userform" onsubmit="return validateform();">
<input name="unitid" type="hidden" class="input" value="${user.unitid?default('')}">
<input name="ownerType" type="hidden" value="${user.ownerType?default(2)}">
<input name="sequence" type="hidden" value="${user.sequence?default(0)}">
<input name="accountId" type="hidden" value="${user.accountId?default('')}">
<input name="deptid" id="deptid" type="hidden" value="${user.deptid?default('')}">

<table align="left" width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16">
  <tr>		
	<td valign="top">
	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tr>
		  <td height="100%" valign="top">
		  	<table  width="100%"  border="0" cellpadding="0" cellspacing="0">
		  	  <tr>
		  	  	<td>
		  	  	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		  	  	  	<tr><td ><font class="send_font_title">新增用户</font>
			  	  	  	(若登录密码留空,则启用单位密码规则<当前单位密码规则为：
				  		<#switch pwdGenericRule?default(PASSWORD_GENERIC_NULL_RULE)?number>
				  			<#case PASSWORD_GENERIC_RULE_NULL>如不输入密码,则为${password_init?default("")}<#break>
				  			<#case PASSWORD_GENERIC_RULE_NAME>如不输入密码,则为账号<#break>
				  			<#case PASSWORD_GENERIC_RULE_UNIONIZE>如不输入密码,则为本单位统一密码<#break>
				  			<#case PASSWORD_GENERIC_NULL_RULE><font color="orange">尚未设置单位密码规则，请在用户管理中设置</font><#break>
				  		</#switch>>)
		  	  	  	</td></tr>
		  	  	  	<tr><td bgcolor="#FCFFFF">
		  	  	  	  <table width="100%" border="0" cellpadding="1" cellspacing="1">
		  	  	  	  	<tr>
		  	  	  	  	  <td class="send_font_no_width" width="100">关联职工：</td>
		  	  	  	  	  <td class="send_padding_no_width"><select name="empleeid" id="empleeid" onchange="changeTeacher(this);" style="width:150px">
		  	  	  	  	  	<option value="">--请选择关联职工--</option>
		  	  	  	  	  	<#list teacherList as teacher>
		  	  	  	  	  	  <option value="${teacher.id?default('')}" <#if teacher.id.equals(user.teacherid?default(''))>selected</#if>>${teacher.name?default('')}(${teacher.tchId})</option>
		  	  	  	  	  	</#list>
		  	  	  	  	  </select></td>
		  	  	  	  	  <td class="send_font_no_width">排序编号：</td>
		  	  	  	  	  <td class="send_padding_no_width"><input name="orderid" id="orderid" type="text" class="input" value="${user.orderid?default('')}" fieldtip="用户排列顺序编号"></td>		  	  	  	  	  
		  	  	  	  	  		  	  	  	  	  
		  	  	  	  	</tr>
		  	  	  	  	<tr>
		  	  	  	  	  <td class="send_font_no_width"><font color="red">*</font>账号：</td>
		  	  	  	  	  <td class="send_padding_no_width">		  	  	  	  	  	
		  	  	  	  	  	<input name="name" type="text" class="input" tabindex="1" value="${user.name?default('')}" fieldtip="${userNameFieldTip}">
		  	  	  	  	  	<img src="${request.contextPath}/static/images/toolmenu_tips2.gif" border="0" onclick="checkName();" style="cursor:pointer;" alt="点击验证该账号是否可用">
		  	  	  	  	  </td>
		  	  	  	  	  <td class="send_font_no_width"><font color="red">*</font>姓名：</td>
		  	  	  	  	  <td class="send_padding_no_width">
		  	  	  	  	  	<input name="realname" type="text" class="input" value="${user.realname?default('')}" fieldtip="请输入用户姓名">
		  	  	  	  	  </td>
		  	  	  	  	</tr>
		  	  	  	  	<tr>
		  	  	  	  	  <td class="send_font_no_width" width="100">登录密码：</td>
		  	  	  	  	  <td class="send_padding_no_width"><input name="password" type="password" class="input" tabindex="2" value="" style="width:150px" onkeyup="changePwdRank(this);" fieldtip="${userPasswordFieldTip}，如果为空则启用单位密码规则设置">
		  	  	  	  	  <INPUT id=passwordrank type=hidden value=2 name=_fmm.ed._0.ne>
		  	  	  	  	  </td>
		  	  	  	  	  <td class="send_font_no_width">密码安全度：</td>
		  	  	  	  	  <td class="send_padding_no_width">
		  	  	  	  	  	<DIV id=PasswordCheck style="BACKGROUND-POSITION: 0px 0px; BACKGROUND-IMAGE: url(${request.contextPath}/static/images/password_check_bk.gif); WIDTH: 150px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 15px">
		  	  	  	  	  </td>	  
		  	  	  	  	</tr>
		  	  	  	  	<tr>
		  	  	  	  	  <td class="send_font_no_width">密码确认：</td>
		  	  	  	  	  <td class="send_padding_no_width"><input name="confirmPassword" type="password" class="input" tabindex="3" value="" style="width:150px" fieldtip="请确认重输一遍上输密码"></td>  	  	  	  	  		  	  	  	  	  
		  	  	  	  	  <td class="send_font_no_width"><font color="red">*</font>用户状态：</td>
		  	  	  	  	  <td class="send_padding_no_width"><select name="mark" <#if user.type?exists&&(user.type==1||user.type==0)>disabled</#if> style="width:150px">
			  	  	  	  	  	${appsetting.getMcode("DM-YHZT").getHtmlTag(user.mark?default('-1')?string)}
			  	  	  	  	  </select></td>
		  	  	  	  	</tr>
		  	  	  	  </table>
		  	  	  	</td></tr>
		  	  	  </table>
		  	  	</td>
		  	  </tr>
		  	</table>
		  </td>
		</tr>				
	  </table>			
	</td>
  </tr>
  <tr style="height:0px;"><td id="actionTip"></td></tr> 
  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
  <tr><td bgcolor="#ffffff" height="1"></td></tr>
  <tr>
  	<td bgcolor="#C2CDF7" height="32" class="padding_left4">
	  	<table width="153" border="0" cellspacing="0" cellpadding="0">
	      <tr>
			<td width="100" align="center"><label>
			  <input type="submit" name="saveBtn" value="保存" tabindex="4" class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" />
	        </label></td>
			<td width="53"><label>
			  <input type="button" name="cancelBtn" value="取消"class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="cancel();"/>
			</label></td>
		  </tr>
		</table>
	</td>
  </tr>
</table>
</form>
</body>