<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "../../common/commonmacro.ftl" as common>
<html>
<head>
<title>${webAppTitle}--单位编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/prototype.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/validate.js"></script>
<script type="text/javascript" language="JavaScript" src="${request.contextPath}/static/js/pwdintensity.js"></script>
<script>
function saveInfo(){
	var updateForm = document.getElementById("updateForm");
	updateForm.action = "unitAdmin-update.action?modID=${modID?default('')}&&pageIndex=${pageIndex!}";
	if(formvalidate()){
		updateForm.submit();
	}
}
function cancelEdit(){
	window.location.href="unitAdmin.action?modID=${modID?default('')}&&unitId=${unitDto.parentid}&&unitName=${unitName?default("")}&&pageIndex=${pageIndex!}";
}
function formvalidate(){
	if(trim(document.getElementById('name').value)==''){
		addFieldError('name','请输入单位名称');
		return false;
	}	
	<#if !action.isSelfDeal()>
	else if(document.getElementById('parentid').value==''){
		addFieldError('parentid','请选择上级单位');
		return false;
	}
	</#if>
	else if(!checkOrderid(document.getElementById('orderid'),'排序编号')){
		return false;
	}
	else if(trim(document.getElementById('userDtoname').value)==''){
		addFieldError('userDtoname','请输入单位管理员账号');
		return false;		
	}
	else if(document.getElementById('userDtopassword').value!=document.getElementById('confirmPassword').value){
		addFieldError('confirmPassword','请确认单位管理员用户的登录密码填写一致');	
		addFieldError('userDtopassword','请确认单位管理员用户的登录密码填写一致');
		return false;
	}
	
	var createdateField = document.getElementById("createdate");	
	if(null != createdateField && !checkDate(createdateField,"注册日期")) return false;
		
	return true;
}

function checkOrderid(elem,field){
 	var flag=false;
    var str=/[0-9]/;
    var str1=trim(elem.value).length;
 	for(var i=0;i<str1;i++){
    	if(!str.test(trim(elem.value).charAt(i))){
	    	flag=true;
			break;
		}
	}
	if(flag==true){
	   //alert(field+"只能为数字和字母");
	   addFieldError(elem,field+"只能为数字！");
	   elem.focus();
	   return false;
       }  
    
    return true;   
 }



//将原来的unionid和上级单位保存下来
var oldUnionid = "${unitDto.unionid?default('')}";
var oldParentid = "${unitDto.parentid?default('')}";
function changeParent(elem){
	var unitid=elem.value;
	if(oldParentid == unitid && oldUnionid != ""){
		document.getElementById('unionid').value=oldUnionid;
		return;
	}
	
	var buffalo=new Buffalo('');
	buffalo.remoteActionCall("unitAdmin-remoteUnitService.action","getUnionid",[unitid,${unitclass_school}],function(reply){
		var result=reply.getResult();
		if(result!=null){
			document.getElementById('unionid').value=result;
		}
	});
}

function changeDelClient(){
   var objDelClient=document.getElementById('delclient');
   var objDelSmsCenter=document.getElementById('delsmscenter');
   if(objDelClient.checked){
      objDelSmsCenter.value="1";
   }else{
      objDelSmsCenter.value="0";
   }

}
</script>
</head>
<body>
<form name="updateForm" id="updateForm" method="POST" action="" onsubmit="return formvalidate();">
<input type="hidden" name="id" value="${unitDto.id?default('')}">
<input type="hidden" name="regionlevel" value="${unitDto.regionlevel?default('')}">
<input type="hidden" name="usetype" value="${unitDto.usetype?default('')}">
<input type="hidden" name="regcode" value="${unitDto.regcode?default('')}">
<input type="hidden" name="unitintid" value="${unitDto.unitintid?default('')}">
<input type="hidden" name="etohSchoolId" value="${unitDto.etohSchoolId?default('')}">
<input type="hidden" name="userDto.id" value="${userDto.id?default('')}">
<input type="hidden" name="userDto.realname" value="${userDto.realname?default('')}">
<input type="hidden" name="userDto.type" value="${userDto.type!}">


<table align="left" width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16">
  <tr>		
	<td valign="top">
	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tr>
		  <td height="100%" valign="top">
		  	<table width="100%"  border="0" cellpadding="0" cellspacing="0">
		  	  <tr>
		  	  	<td>		  	  	  
		  	  	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		  	  	  	<tr><td class="send_font_title">单位编辑</td></tr>
		  	  	  	<tr><td bgcolor="#FCFFFF">
		  	  	  	  <table width="100%" border="0" cellpadding="1" cellspacing="1">
		  	  	  	  	<tr>
		  	  	  	  	  <input type="hidden" name="unitPartitionNum" value="${unitDto.unitPartitionNum?default('')}"/>
		  	  	  	  	  <td class="send_font_no_width" width="15%">统一编号：</td>
		  	  	  	  	  <td class="send_padding" width="35%"><input name="unionid" type="text" class="input_readonly" value="${unitDto.unionid?default('')?trim}"></td>		  	  	  	  	  
		  	  	  	  	  <td class="send_font_no_width" width="15%">单位分类：</td>
						  <td class="send_padding" width="35%">
						  	${appsetting.getMcode("DM-DWFL").get(unitDto.unitclass?default('')?string)}
						  	<input name="unitclass" type="hidden" value="${unitDto.unitclass?default('')?string}">
						  </td>
		  	  	  	  	</tr>		  	  	  	  	
		  	  	  	  	<tr>
		  	  	  	  	  <td class="send_font_no_width"><font color="red">*</font>单位名称：</td>
		  	  	  	  	  <td class="send_padding"><input name="name" id="name" type="input" tabindex="1" value="${unitDto.name?default('')?trim}"
		  	  	  	  	    <#if unitDto.parentid == "00000000000000000000000000000000">
		  	  	  	  	    	class="input_readonly" fieldtip="顶级单位，不允许修改单位名称"
		  	  	  	  	    <#else>
				  	  	  	    <#if unitDto.usetype==2>class="input_readonly" fieldtip="报送单位，不允许本地修改单位名称"
				  	  	  	  	<#else>class="input" 		  	  	  	  	  			  	  	  	  	   	  	  	  	  		  	  	  	  	  
				  	  	  	  	</#if>
			  	  	  	  	</#if>
			  	  	  	   maxlength="75"></td>
						  <td class="send_font_no_width">单位类型：</td>
						  <td class="send_padding">
						  	${appsetting.getMcode("DM-DWLX").get(unitDto.unittype?default('')?string)}	  					  
						  	<input name="unittype" type="hidden" value="${unitDto.unittype?default('')?string}">
						  </td>
						</tr>
						<tr>
						  <td class="send_font_no_width"><font color="red">*</font>上级单位：</td>
						  <td class="send_padding">
						  	  <select name="parentid" style="width:150px" onchange="changeParent(this);"
						  	    <#if unitclass_edu==unitDto.unitclass?default('1')||action.isSelfDeal() || unitDto.usetype==2>disabled</#if>>
						  		  <#if unitDto.parentid=='00000000000000000000000000000000'><option value='00000000000000000000000000000000'>（本平台顶级单位）</option></#if>
						  		  <#list listOfUnitDto as unit>
						  		  	<option value='${unit.id?default('')}'<#if unitDto.parentid?exists&&unitDto.parentid.equals(unit.id)>selected</#if>>${unit.name?default('')}</option>
						  		  </#list>
						  	  </select>
						  	  <#if unitclass_edu==unitDto.unitclass?default('1')||action.isSelfDeal() || unitDto.usetype==2>						  	  	
						  	  	<input type="hidden" name="parentid" value="${unitDto.parentid?default('')}">
						  	  </#if>
						  </td>
						  <td class="send_font_no_width">行政级别：</td>
						  <td class="send_padding">
						  	${appsetting.getMcode("DM-XZJB").get(unitDto.regionlevel?default("")?string)}
						  </td>							  
						</tr>
						<tr>
						  <td class="send_font_no_width">注册日期：</td>
						  <td class="send_padding"><input name="creationTime" type="text" class="input" readonly="true" value="<#if unitDto.creationTime?exists>${unitDto.creationTime?string("yyyy-MM-dd")}</#if>"></td>					  						  						  						  
				  		  <td class="send_font_no_width">报送类型：</td>
						  <td class="send_padding">
						  <#if unitDto.usetype?exists>
							  <#if unitDto.usetype==0>
							  	顶级单位
							  <#elseif unitDto.usetype==1>
							  	本平台单位
							  <#elseif unitDto.usetype==2>
							     报送单位
							  </#if>
						  </#if>
						  </td>						  									
						</tr>
						<tr>
						<input type="hidden" name="ueTablePostfix" value="${unitDto.ueTablePostfix?default('')}">
						  <td class="send_font_no_width">当前状态：</td>
						  <td class="send_padding">
						  <#-- 如果是本单位，则不能修改当前状态 -->
						  <#if loginInfo.getUnitID().equals(unitId)>
						  	${appsetting.getMcode('DM-DWZT').get(unitDto.mark?string)}
						  	<input type="hidden" name="mark" value="${unitDto.mark}">
						  <#else>
							  <select name='mark' style="width:150px;" tabindex='12'>
							  	${appsetting.getMcode('DM-DWZT').getHtmlTag(unitDto.mark?default("")?string)}
							  </select>
						  </#if>
						  </td>
						  <td class="send_font_no_width">授权类型：</td>
						  <td class="send_padding">
						  	<#if unitDto.authorized?exists>
						  		<#if unitDto.authorized==0>
						  			未授权
						  		<#elseif unitDto.authorized==1>
						  			授权
						  		<#elseif unitDto.authorized==2>
						  			附属授权
						  		</#if>	
						  	</#if>
							<input name="authorized" type="hidden" value="${unitDto.authorized?default('-1')}">
						  </td>	
						</tr>
						<tr>
						  <td class="send_font_no_width">排序编号：</td>
						  <td class="send_padding"><input name="orderid" id="orderid" type="text" class="input" tabindex="8" value="${unitDto.orderid?default('')}"  maxlength="18"></td>						  
						  	
						  <td class="send_font_no_width">单位使用类别：</td>
						  <td class="send_padding">
						  	${appsetting.getMcode("DM-UNITUSETYPE").get(unitDto.getUnitusetype()?default(''))}
							<input type="hidden" name="unitusetype" id="unitusetype" value="${unitDto.getUnitusetype()?default('')}">
						  </td>
						</tr>
						
		  	  	  	  </table>
		  	  	  	</td></tr>
		  	  	  </table>
		  	  	</td>
		  	  </tr>		  	  
		  	</table>
		  </td>
		</tr>
		<tr>
		  <td>
		  	<table width="100%"  border="0" cellpadding="0" cellspacing="0">
		  	  <tr>
		  	  	<td><img src="${request.contextPath}/static/images/head_icon2.gif" width="17" height="17" /><font class="send_font_title">该单位管理员</font></td>		  	  	  	  
		  	  </tr>
		  	  <tr>
		  	  	<td bgcolor="#FCFFFF">
		  	  	  <table width="100%" border="0" cellpadding="1" cellspacing="1">
		  	  	  	<tr>
		  	  	  	  <td class="send_font_no_width" width="15%"><font color="red">*</font>账号：</td>
		  	  	  	  <td class="send_padding" width="35%"><input name="userDto.name" id="userDtoname" class="input_readonly" type="text" class="input" tabindex="12" value="${userDto.name?default('')?trim}" fieldtip="${userNameFieldTip}"></td>
		  	  	  	  <td class="send_padding" colspan="2"></td>
		  	  	  	</tr>
		  	  	  	<tr>
		  	  	  	  <td class="send_font_no_width">登录密码：</td>
		  	  	  	  <td class="send_padding"><input name="userDto.password" id="userDtopassword" type="password" tabindex="13" class="input" style="width:150px;" value="${password_default}" onkeyup="changePwdRank(this);" onclick="if(this.value=='${password_default}') this.value='';" fieldtip="${userPasswordFieldTip}"></td>
		  	  	  	  <td class="send_font_no_width" width="15%">密码安全度：</td>
		  	  	  	  <td class="send_padding_no_width" width="35%">
		  	  	  	  	 <DIV id=PasswordCheck style="BACKGROUND-POSITION: 0px 0px; BACKGROUND-IMAGE: url(${request.contextPath}/static/images/password_check_bk.gif); WIDTH: 150px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 15px;" />
		  	  	  	  </td>
		  	  	  	</tr>
		  	  	  	<tr>
		  	  	  	  <td class="send_font_no_width">密码确认：</td>
		  	  	  	  <td class="send_padding"><input name="userDto.confirmPassword" id="confirmpassword" type="password" tabindex="14" class="input" style="width:150px;" value="${password_default}" onclick="this.value='';"></td>
		  	  	  	  <td colspan="2" class="send_padding"></td>
		  	  	  	</tr>
		  	  	  	<#-- 
		  	  	  	<tr>
		  	  	  	  <td class="send_font_no_width">电子邮件地址：</td>
		  	  	  	  <td colspan="3" class="send_padding"><input name="userDto.email" id="userdtoemail" type="text" class="input" size="80" tabindex="15" value="${userDto.email?default('')?trim}"></td>		  	  	  	  	  	
		  	  	  	</tr>
		  	  	  	-->
		  	  	  </table>
		  	  	</td>
		  	  </tr>
		  	  <#--start smscenter-->
		  	  	  	<#if (useSmsCenter&&smsUseConfigClientId.equals(""))>
		  	  	  	<tr>
		  	  	  	  <td><img src="${request.contextPath}/static/images/icon_smsadmin.gif" width="17" height="17" /><font class="send_font_title">同步在短信接入平台里创建账号</font></td>		  	  	  	  
		  	  	  	</tr>
		  	  	  	<tr>
		  	  	  	  <td bgcolor="#FCFFFF"><input type="hidden" name="syncSmsCenter" value="0">
		  	  	  	  	<table width="100%" border="0" cellpadding="1" cellspacing="1">		  	  	  	  	  
		  	  	  	  	  <tr>
		  	  	  	  	  	<td class="send_padding_no_width">&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="isSmsCenter1" name="isSmsCenter" value="1" onclick="syncSmsCenter.value=this.value;" ><label for="isSmsCenter1">是，同步创建账号</label></td>		  	  	  	  	  		  	  	  	  	  	
		  	  	  	  	  </tr>
		  	  	  	  	  <tr>
		  	  	  	  	  	<td class="send_padding_no_width">&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="isSmsCenter2" name="isSmsCenter" value="0" onclick="syncSmsCenter.value=this.value;" checked><label for="isSmsCenter2">否，不同步创建账号</label></td>		  	  	  	  	  		  	  	  	  	  	
		  	  	  	  	  </tr>
		  	  	  	  	</table>
		  	  	  	  </td>
		  	  	  	</tr>
		  	  	  	<#elseif (useSmsCenter&&!smsUseConfigClientId.equals(""))>
		  	  	  	<tr>
		  	  	  	  <td><img src="${request.contextPath}/static/images/icon_smsadmin.gif" width="17" height="17" /><font class="send_font_title">同步在短信接入平台里创建账号</font></td>		  	  	  	  
		  	  	  	</tr>
		  	  	  	<tr>
		  	  	  	  <td bgcolor="#FCFFFF"><input type="hidden" name="syncSmsCenter" value="1">
		  	  	  	  	<table width="100%" border="0" cellpadding="1" cellspacing="1">		  	  	  	  	  
		  	  	  	  	  <tr>
		  	  	  	  	  	<td class="send_padding_no_width">&nbsp;&nbsp;&nbsp;&nbsp;已创建账号，账号为&nbsp;${smsUseConfigClientId}<input type="hidden" name="currentClientid" value="${smsUseConfigClientId}">
		  	  	  	  	  	</td>		  	  	  	  	  		  	  	  	  	  	
		  	  	  	  	  </tr>
		  	  	  	  	  <tr>
		  	  	  	  	  	<td class="send_padding_no_width">&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="delclient" onclick="changeDelClient()"><label for="delclient">删除此单位在短信平台的账号，同时停用此单位的短信配置</lable>
		  	  	  	  	  	<input type="hidden" name="delsmscenter" value="0">
		  	  	  	  	  	</td>		  	  	  	  	  		  	  	  	  	  	
		  	  	  	  	  </tr>
		  	  	  	  	</table>
		  	  	  	  </td>
		  	  	  	</tr>
		  	  	  	<#else>
		  	  	  	</#if>
		  	 <#--end smscenter-->
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
			  <input type="button" name="Submit" value="保存"class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="saveInfo();"/>
	        </label></td>	        
			<td width="53"><label>
			  <input type="button" name="Submit2" value="取消"class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="cancelEdit();"/>
			</label></td>
		  </tr>
		</table>
	  
	</td>
  </tr>
</table>
</form>
</body>
</html>