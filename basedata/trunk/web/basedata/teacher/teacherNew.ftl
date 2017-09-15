<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="${webAppTitle}--编辑用户">
<#import "/common/commonmacro.ftl" as commonmacro>
<#include "/basedata/teacher/teacherDetailValidate.ftl">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/strspell.js"></script>
<script type="text/javascript" language="JavaScript" src="${request.contextPath}/static/js/pwdintensity.js"></script>
<script>
<#--检查图片格式-->
<@checkFileType/>
<#--教职工信息验证JS-->
<@teacherCommonVialidate>
	if(document.getElementById('insertWithUser').checked){
		if(trim(document.getElementById('userdtoname').value)==''){
			addFieldError('userdtoname','请输入账号');
			return false;
		}
		else if(!checkInteger(document.getElementById('orderid'),'排序编号')){
			return false;
		}
		else if(document.getElementById('confirmPassword').value!=document.getElementById('password').value){
			addFieldError('password','请确认密码填写一致');
			document.getElementById('confirmPassword').value='';
			document.getElementById('password').value='';
			return false;
		}
	}
</@teacherCommonVialidate>
function checkoutName(elem){ 
	var userdtoname = document.getElementById("userdtoname");
	if(elem.value != '' && userdtoname.value == ''){
		var loginName = "";
		for(var i=0;i<elem.value.length;i++){
			loginName += elem.value.charAt(i).toSpell().charAt(0);
		}
		userdtoname.value = "t${eToHSchoolId?default("")}".trim() + loginName.trim();		
	}
}
function sychUser(){
	if(document.getElementById('insertWithUser').checked){
		document.getElementById('sychUser').style.display='';
	}
	else{
		document.getElementById('sychUser').style.display='none';
	}
}
function formsubmit(){
	if(formvalidate()){
		jQuery.ajax({
	   		url:"${request.contextPath}/basedata/teacher/teacherAdmin-insert.action?modID=${modID?default('')}&&deptidnow=${deptidnow?default()}",
	   		type:"POST",
	   		data:jQuery('#updateForm').serialize(),
	   		dataType:"JSON",
	   		async:false,
	   		success:function(data){
	   			if(data.operateSuccess){
	   				showMsgSuccess(data.promptMessage,"",function(){
	   					load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-list.action?modID=${modID?default('')}&deptidnow=${deptidnow?default('')}"+qNameOrId);
	   				});
	   			}else{
	   				showMsgError(data.errorMessage);
	   			}
	   		}
 		});
	}
}
function syschangeuserDtoRealname(elem){
	document.getElementById('userdtoname').value= "${eToHSchoolId?default("")}" + elem.value.toSpell();
}
function cancelBack(){
	var qNameOrId ="&queryTchName=${queryTchName?default('')}&queryTchId=${queryTchId?default('')}";
	load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-list.action?modID=${modID?default('')}&deptidnow=${deptidnow?default('')}"+qNameOrId);
}


function checkUserName(){
	var name=document.getElementById('userdtoname').value;
	if(trim(name)==''){
		addFieldError('userdtoname','请输入账号');
		return ;
	}
	
	var buffalo=new Buffalo('${request.contextPath}/basedata/user/');
	buffalo.remoteActionCall("userAdmin-service.action","validateUserNameAvaliable",[name],function(reply){
		var result=reply.getResult();
		
		if(result.length>0){
			addFieldError('userdtoname',result);
		}
		else{			
			addFieldSuccess(document.getElementById('userdtoname'),'恭喜您,'+name+'账号尚可用');
		}
	});
	return ;
}
</script>
<div id="teacherEditDiv">
<form name="updateForm" id="updateForm" method="POST" action="">
<p class="table-dt">职工新增</p>
<@common.tableDetail >
      <td  style="display:none" width="100" align="left"><label for="insertWithUser" style="visibility:visibility">
		<input type="checkbox" id="insertWithUser" name="teacher.insertWithUser" onClick="sychUser();" <#if teacher.insertWithUser?exists><#if teacher.insertWithUser>checked</#if><#else>checked</#if> value="true">
		同步新增用户
		</label>
	  </td>	
	</tr>
<#include "/basedata/teacher/teacherDetailCommon.ftl">
<#--
<tr><th colspan="4">
<p class="table-dt">
同步新增用户(若密码留空,则启用单位密码规则<当前单位密码规则为：
			  		<#switch pwdGenericRule?default(PASSWORD_GENERIC_NULL_RULE)?number>
			  			<#case PASSWORD_GENERIC_RULE_NULL>如不输入密码,则为${password_init?default("")}<#break>
			  			<#case PASSWORD_GENERIC_RULE_NAME>如不输入密码,则为账号<#break>
			  			<#case PASSWORD_GENERIC_RULE_UNIONIZE>如不输入密码,则为本单位统一密码<#break>
			  			<#case PASSWORD_GENERIC_NULL_RULE><font color="orange">尚未设置单位密码规则，请在用户管理中设置</font><#break>
			  		</#switch>>)</p>
</th></tr>
-->
<tr>
   	<th width="100px">账号：</th>
   	<td><input name="user.name" id="userdtoname" type="text" 
   	class="input-txt fn-left" style="width:140px;" value="${user.name?default('')}" msgName="账号" notNull="true" minLength="4" title="${userNameFieldTip}">
   	  <!--<img src="${request.contextPath}/static/images/toolmenu_tips2.gif" border="0"
   	   style="cursor:pointer;" onClick="checkUserName();" alt="验证该用账号是否可用">
   	   -->
   	  <input name="user.type" type="hidden" value="2">
   	  <span class="fn-left c-orange mt-5 ml-10">*</span>
   	</td>
   	<@common.tdi msgName="排序编号" name="user.orderid" id="orderid" notNull="false" value="${user.orderid?default('')}" maxLength="8"></@common.tdi>
  </tr>
  <tr>
  	<@common.tdi msgName="昵称" name="user.nickName" id="nickName" notNull="false" value="${user.nickName?default('')}" maxLength="15"></@common.tdi>
  	<th></th>
  	<td>
  	<input type="checkbox" name="teacher.hidePhone" <#if teacher.hidePhone == 1>checked="checked"</#if> value="1" />&nbsp隐藏手机号
  	</td>
  </tr>
  <tr>
  	
  	<th>登录密码：</th>
  	<td><input name="user.password" id="password" type="password" msgName="登录密码" maxLength="20" class="input-txt fn-left" style="width:140px;" value="" notNull="true" title="${userPasswordFieldTip}"> <span class="fn-left c-orange mt-5 ml-10">*</span></td>
  	<th>密码确认：</th>
  	<td><input name="user.confirmPassword" id="confirmPassword" msgName="密码确认" maxLength="20" type="password" class="input-txt fn-left" style="width:140px;" notNull="true" value=""> <span class="fn-left c-orange mt-5 ml-10">*</span></td>      
  </tr>
	<tr>
		<td colspan="4" class="td-opt" >
	    	<a href="javascript:void(0);" class="abtn-blue" onclick="formsubmit();">保存</a>
	        <a href="javascript:void(0);" class="abtn-blue ml-10" onclick="cancelBack();">取消</a>
	    </td>
  </tr>		  		
</@common.tableDetail>
<#include "/basedata/teacher/teacherColumnCommon.ftl">
</form>
</div>
</@common.moduleDiv>
