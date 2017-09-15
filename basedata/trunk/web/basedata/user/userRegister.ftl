<#import "/common/product.ftl" as productmacro>
<html>
<head>
<title>新用户注册</title>
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script language="JavaScript" src="${request.contextPath}/static/js/validate.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/pwdintensity.js"></script>
<script>
function findUnit(elem){
	var unitdiv=document.getElementById('unitDiv');
	unitdiv.style.left=getPositionX(elem);
	unitdiv.style.top=getPositionY(elem);
	unitdiv.style.display='';	
}
function findUnitEmployee(){
	var buffalo=new Buffalo('');
	
	var unitid=document.getElementById('user.unitid').value;
	var employeeselect=document.getElementById('teacherid');
	
	buffalo.remoteActionCall("${request.contextPath}/basedata/user/userAdmin-service.action","getTeacherByUnit",[unitid],function(reply){
		var result=reply.getResult();
		employeeselect.options.length=0;
		employeeselect.options[0]=new Option('--请选择--','');
		if(result!=null){
			for(var i=1;i<=result.length;i++){
				employeeselect.options[i]=new Option(result[i-1].name,result[i-1].id);
				<#if user.teacherid?exists>
					if(result[i-1].id=='${teacherid}'){
						employeeselect.selectedIndex=i;
					}
				</#if>
			}
		}
	});
}
function validateform(){
	if(!checkElement(document.getElementById('name'),'账号')){
		return false;
	}
	else if(!checkElement(document.getElementById('realname'),'姓名')){
		return false;
	}
//	else if(!checkElement(document.getElementById('password'),'登录密码')){
//		return false;
//	}
	else if(document.getElementById('password').value!=document.getElementById('confirmPassword').value){
		document.getElementById('password').value='';
		document.getElementById('confirmpassword').value='';
		addFieldError('password','请确认两次密码输入相同');
		addFieldError('confirmPassword','请确认两次密码输入相同');
		return false;
	}
	else if(!checkOverLen(document.getElementById('realname'),'${userRealNameLength}','姓名')){
		return false;
	}
	<#if registerActive?default(-1)==FPF_USER_REGISTER_ACTIVE_EMAIL>
	else if(!checkElement(document.getElementById('email'),'电子邮件')){
		return false;
	}
	</#if>
	else if(document.getElementById('email').value.length>0){
		if(document.getElementById('email').value.indexOf('@')==-1){
			addFieldError('email','请正确填写电子邮件');
			document.getElementById('email').focus();
			return false;
		}
		else if(!checkOverLen(document.getElementById('email'),50,'电子邮件')){
			return false;
		}
	}
	return true;
}
function register(){
	if(validateform()){
		form1.action="userAdmin-save.action";
		form1.submit();
	}
}
function chooseEmp(elem){
	var unitid=document.getElementById('user.unitid');
	var unitname=document.getElementById('user.unitname');
	if(elem.options.length==0){
		if(unitid.value==''){
			alert('请先选择所在单位！');
			unitname.focus();
		}
		else{
			alert('该单位尚没有职工，不能注册新用户！');
		}
	}
	return;
}
function chooseUnit(){
	document.getElementById('user.unitid').value=document.getElementById('choose_unitId').value;
	document.getElementById('user.unitName').value=document.getElementById('choose_unitName').value;
	document.getElementById('unitDiv').style.display='none';
	findUnitEmployee();
}
function checkName(){
	var name=document.getElementById('name').value;
	if(trim(name)==''){
		addFieldError('name','请输入账号');
		return ;
	}
	
	var buffalo=new Buffalo('${request.contextPath}/basedata/user/');
	buffalo.remoteActionCall("userAdmin-service.action","validateUserNameAvaliable",[name],function(reply){
		var result=reply.getResult();
		
		if(result){
			addFieldError('name',result);
		}
		else{			
			addFieldSuccess(document.getElementById('name'),'恭喜您,'+name+'账号尚可用');
		}
	});
	return ;
}
</script>
</head>
<body style="background-color: #EBEEFF;">
<form action="" method="POST" name="form1">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<@productmacro.logPic />
<tr><td valign="top" height="100%"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
   <td>
	<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="8">
	<tr>
    <td width="40%" valign="top">
<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="103"><table width="103" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="103" align="center" valign="bottom" class="blue_bg">新用户注册</td>
      </tr>
    </table></td>
    <td width="413" class="border_black2" >&nbsp;</td>
  </tr>
  <tr>
    <td height="100%" colspan="2" valign="top" class="border_line" style="background-color:#FFFFFF;">
      <table width="93%" border="0" cellspacing="0" cellpadding="0" style="padding-left:5px;">
        <tr><td align="right" style="padding-right:5px"><font color="red">*</font>为必填项</td></tr>
        <tr>
          <td>
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="120" height="40" align="right" class="grayborder_topleft"><font color="red">*</font>账号：<br></td>
		        <td width="200" class="grayborder_top"><input name="name" type="text" class="input" value="${user.name?default('')}" fieldtip="请控制不要超过${nameMaxLength}个字符，可使用英文字母、0-9数字、下划线">
		          <img src="${request.contextPath}/static/images/toolmenu_tips2.gif" border="0" onclick="checkName();" style="cursor:pointer;" alt="点击验证该账号是否可用">
		        </td>
		        <td class="grayborder_toprightleft" style="padding-left:5px;">${userNameFieldTip}</td>
		      </tr>
		      <tr>
		      	<td height="40" class="grayborder_topleft" align="right"><font color="red">*</font>姓名：</td>
		      	<td class="grayborder_top">
		      	  <input name="realname" type="input" value="${user.realname?default('')}" class="input">
		      	</td>
		      	<td class="grayborder_toprightleft" style="padding-left:5px;">请输入用户姓名</td>
		      </tr>
		      <tr>
		        <td height="40" align="right" class="grayborder_topleft">用户密码：<br><font style="font-size:11;">密码安全度：</font></td>
		        <td class="grayborder_top">
		          <input name="password" id="password" type="password" class="input" value="" style="width:150px" onkeyup="changePwdRank(this);" fieldtip="${userPasswordFieldTip}">
		          <DIV id=PasswordCheck style="BACKGROUND-POSITION: 0px 0px; BACKGROUND-IMAGE: url(${request.contextPath}/static/images/password_check_bk.gif); WIDTH: 150px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 15px">
		        </td>
		        <td class="grayborder_toprightleft" style="padding-left:5px;">${userPasswordFieldTip}</td>
		      </tr>
		      <tr>
		        <td height="40" align="right" class="grayborder_topleft">密码确认：<br></td>
		        <td class="grayborder_top">
		          <input name="confirmPassword" id="confirmPassword" type="password" class="input" value="" style="width:150px" fieldtip="请确认重输一遍上述密码">
		        </td>
		        <td class="grayborder_toprightleft" style="padding-left:5px;">请确认重输一遍上述密码</td>
		      </tr>
		      <tr>
		        <td height="40" align="right" class="grayborder_topleft">
					<#if registerActive?default(-1)==FPF_USER_REGISTER_ACTIVE_EMAIL><font color="red">*</font></#if>电子邮件：<br>
		        </td>
		        <td class="grayborder_top">
		          <input name="email" type="text" class="input" value="${user.email?default('')}">
		        </td>
		        <td class="grayborder_toprightleft" style="padding-left:5px;">请正确填写电子邮件地址</td>
		      </tr>
		      <tr>
		        <td height="40" align="right" class="grayborder_topleft"><font color="red">*</font>所在单位：<br></td>
		        <td class="grayborder_top">
		          <div id="unitDiv" style="position:absolute;width:200px;height:350px;z-index:99;display:none;">
					<table align="left" width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="border_line" style="background-color:#FFFFFF;">
					  <tr><td>请选择所在单位：</td></tr>
					  <tr><td height="1px" bgcolor="#EEEEEE"></td></tr>
					  <tr>
					  	<td height="95%">
					  	<iframe name="unitTreeFrame" marginwidth="0" marginheight="0" src="${request.contextPath}/common/xtree/unittree.action"
							frameborder="0" width="100%" height="100%">
						</iframe>
						<input name="choose_unitId" id="choose_unitId" type="hidden" value="">
						<input name="choose_unitName" id="choose_unitName" type="hidden" value="">
					  	</td>
					  </tr>
					  <tr>
					  	<td align="middle"><img src="${request.contextPath}/static/images/queding.gif" style="cursor:pointer;" onclick="chooseUnit();">
					  	<img src="${request.contextPath}/static/images/guanbi.gif" style="cursor:pointer;" onclick="document.getElementById('unitDiv').style.display='none';">
					  	</td>
					  </tr>
					</table>
				  </div>	         
		          <input name="user.unitid" id="user.unitid" type="hidden" value="${user.unitid?default('')}">
		          <input name="user.unitName" id="user.unitName" type="text" class="input" value="${user.unitName?default('')}" readonly="true" class="input" onchange="findUnitEmployee();">
		  	  	  <img src="${request.contextPath}/static/images/tree_icon.gif" onclick="findUnit(this);" style="cursor:pointer;"> 
		        </td>
		        <td class="grayborder_toprightleft" style="padding-left:5px;">请选择用户所在单位</td>
		      </tr>
		      <tr>
		        <td height="40" align="right" class="grayborder_topleftbottom">关联职工：</td>
		        <td class="grayborder_topbottom">
		          <select name="teacherid" id="user.teacherid" style="width:150px;" onclick="chooseEmp(this);">
		  	  	  	<option value="">--请选择--</option>
		  	  	  </select>
				</td>
		        <td class="grayborder_all" style="padding-left:5px;">可选择用户关联职工</td>
		      </tr>
		      <tr>
		        <td height="40" align="right">&nbsp;</td>
		        <td><img src="${request.contextPath}/static/images/zhuce.gif" width="62" height="21" style="cursor:pointer" onclick="register();">
		          <img src="${request.contextPath}/static/images/quxiao.gif" width="62" height="21" hspace="5" style="cursor:pointer" onclick="window.close();"></td>
		        <td>&nbsp;</td>
		      </tr>
		    </table>
    	  </td>
    	</tr>
    	<tr style="height:0px;"><td id="actionTip"></td></tr>
    	<tr><td>&nbsp;</td></tr>    	     	
      </table>      
    </td></tr>
</table>
</td>
  </tr>
  </table></td></tr></table></td></tr>
  <@productmacro.copyright />
</table>
</form>
<script>
	var unitid=document.getElementById('user.unitid');
	if(unitid.value!=''){
		findUnitEmployee();
	}
</script>
</body>
</html>