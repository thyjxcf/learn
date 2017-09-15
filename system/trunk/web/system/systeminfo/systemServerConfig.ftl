<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="平台基础信息设置">

  <div class="pub-table-inner mt-20" id="mailDiv">
    <form id="form1" action="" name="form1">
	     <p class="table-dt">邮件服务器配置：</p>
	      <table border="0" cellspacing="0" cellpadding="0" class="table-form">
	         <tr>
	             <th width="30%">邮件发送服务器SMTP地址：</th>
	             <td colspan="3">
	                 <#if isTopEduAdmin == true><input name="ip" id="ip" type="text" msgName="邮件发送服务器SMTP地址" notNull="true"  class="input-txt fn-left" style="width:200px;"  tabindex="1" value="${mailServerDto.ip?default('')}"><#else>${mailServerDto.ip?default('')}</#if>
	                 <span class="fn-left c-orange mt-5 ml-10">*</span>
	             </td>
	         </tr>
	         <tr>
	             <th width="30%">发邮件是否需要安全认证：</th>
  	  	         <td colspan="3">
  	  	            <#if isTopEduAdmin == true>
  	  	              <span class="ui-checkbox <#if mailServerDto.needconfirm==1>ui-checkbox-current</#if>" id="ckbNeedconfirm" data-name="a" onclick="onNeedconfirm()"><input type="checkbox" <#if mailServerDto.needconfirm==1>checked</#if> class="chk" name="needconfirm" id="needconfirm" tabindex="2" value="${mailServerDto.needconfirm?default(1)}" /> <span class="fn-left c-orange mt-5 ml-10">*</span></span> 
  	  	              <input type="hidden" id="hiddenNeedconfirm" value="${mailServerDto.needconfirm?default(1)}">
  	  	            <#else>
  	  	               <#if mailServerDto.needconfirm==1>是<#else>否</#if>
  	  	            </#if>
  	  	         </td>		  	  	  	  	  			  	  	  	  	   	  	  	  	
	         </tr>
	         <tr>
	             <th><label id="pro_confirmusername" name="pro_confirmusername"  <#if mailServerDto.needconfirm==0>disabled</#if>>安全认证使用的账号：</label></th>
		         <td colspan="3">
		            <#if isTopEduAdmin == true>
		               <input name="confirmusername" id="confirmusername" type="text" class="input-txt fn-left" style="width:200px;" msgName="安全认证使用的账号" notNull="true" tabindex="3" value="${mailServerDto.confirmusername?default('')}" <#if mailServerDto.needconfirm==0>disabled</#if>>
		            <#else>
		               ${mailServerDto.confirmusername?default('')}
		            </#if>
		            <span class="fn-left c-orange mt-5 ml-10">*</span>
		         </td>						  
	         </tr>
	         <tr>
	              <th width="30%"><label name="pro_confirmuserpwd" id="pro_confirmuserpwd" <#if mailServerDto.needconfirm==0>disabled</#if>>安全认证使用的用户密码：</label></th>
		          <td width="30%" <#if isTopEduAdmin == false>colspan="3"</#if>>
		             <#if isTopEduAdmin == true>
		               <input name="confirmuserpwd" id="confirmuserpwd" type="password" class="input-txt fn-left" style="width:200px;" msgName="安全认证使用的用户密码" notNull="true" tabindex="4" value="${password_default}" <#if mailServerDto.needconfirm==0>disabled</#if> />
		             <#else>
		                ${password_default}
		             </#if>
		              <span class="fn-left c-orange mt-5 ml-10">*</span>
		          </td>					  						  						  						  
	              <#if isTopEduAdmin == true> 
					  <th width="15%"><label name="pro_reConfirmuserpwd" id="pro_reConfirmuserpwd" <#if mailServerDto.needconfirm==0>disabled</#if>>用户密码确认：</label></th>
					  <td width="35%">
					  	<input name="reConfirmuserpwd" id="reConfirmuserpwd" type="password" class="input-txt fn-left" style="width:200px;" msgName="用户密码确认" notNull="true" validateScript="checkMailPassword"  tabindex="5" value="${password_default}"  <#if mailServerDto.needconfirm==0>disabled</#if>  />
					    <span class="fn-left c-orange mt-5 ml-10">*</span>
					  </td>	
			      </#if>	
	         </tr>
	         <tr>
	              <th width="30%">系统邮件显示的发送地址：</th>
				  <td colspan="3">
				  	 <#if isTopEduAdmin == true>
				  	    <input name="displayaddress" id="displayaddress" type="input" class="input-txt fn-left" style="width:200px;" msgName="系统邮件显示的发送地址" notNull="true"  validateScript="checkMailAddress"  tabindex="6" value="${mailServerDto.displayaddress?default('')}">
				  	 <#else>
				  	    ${mailServerDto.displayaddress?default('')}
				  	 </#if>
				  	 <span class="fn-left c-orange mt-5 ml-10">*</span>
				  </td>	
	         </tr>
	     </table>
	  	 <#if isTopEduAdmin == true>
	  	 <p class="table-bt-gray t-center">
			 <a href="javascript:void(0)" class="abtn-blue submit" onclick="javascript:onSaveMailConfig();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 	 <a href="javascript:void(0)" class="abtn-blue ml-20" onclick="resetMail();">重置</a><button type="reset" id="resetButton" style="display:none;">重置</button>
	 	 </p>
	 	</#if>
     </form>
  </div>
  <div class="pub-table-inner mt-20" id="smsDiv">
     <#if smsMode?default("") == "1">
        <form id="form2" action="" name="form2">
         <p class="table-dt">通讯服务器配置：</p>
         <table border="0" cellspacing="0" cellpadding="0" class="table-form">
	         <tr>
	             <th width="30%">通讯服务器地址：</th>
	             <td width="30%">
	                 <#if isTopEduAdmin == true><input name="server" id="server" type="text" class="input-txt fn-left" style="width:200px;" msgName="通讯服务器配置" notNull="true" tabindex="9" value="${smsServerDto.server?default('')}">
	                 <#else>
	                    ${smsServerDto.server?default('')}
	                 </#if>
	                 <span class="fn-left c-orange mt-5 ml-10">*</span>
	             </td>
	             <th width="15%">端口：</th>
	             <td width="35%">
	                 <#if isTopEduAdmin == true><input name="port" id="port" type="text" class="input-txt fn-left" style="width:200px;" msgName="端口" notNull="true" validateScript="checkPort" tabindex="10" value="${smsServerDto.port?default('')}">
	                 <#else>
	                    ${smsServerDto.port?default('')}
	                 </#if>
	                 <span class="fn-left c-orange mt-5 ml-10">*</span>
	             </td>
	         </tr>
	         <tr>
	             <th width="30%">通讯帐户：</th>
	             <td colspan="3">
	                 <#if isTopEduAdmin == true><input name="localName" id="localName" type="text" class="input-txt fn-left" style="width:200px;" msgName="通讯帐户" notNull="true" tabindex="12" value="${SmsServerDto.localName?default('')}">
	                 <#else>
	                    ${SmsServerDto.localName?default('')}
	                 </#if>
	                 <span class="fn-left c-orange mt-5 ml-10">*</span>
	             </td>
	         </tr>
	         <tr>
	             <th>通讯密码：</th>
	             <td <#if isTopEduAdmin == false>colspan="3"</#if>>
	                 <#if isTopEduAdmin == true><input name="localPwd" id="localPwd" type="text" class="input-txt fn-left" style="width:200px;" msgName="通讯密码" notNull="true" tabindex="13" value="${password_default}">
	                 <#else>
	                    ${password_default}
	                 </#if>
	                 <span class="fn-left c-orange mt-5 ml-10">*</span>
	             </td>
	             <#if isTopEduAdmin == true>
	             <th width="15%">用户密码确认：</th>
	             <td width="35%">
	                 <input name="reLocalPwd" id="reLocalPwd" type="password" class="input-txt fn-left" style="width:200px;" msgName="用户密码确认" notNull="true" validateScript="checkSmsPassword" tabindex="14" value="${password_default}">
	                 <span class="fn-left c-orange mt-5 ml-10">*</span>
	             </td>
	             </#if>
	         </tr>
	     </table>
	  	 <#if isTopEduAdmin == true>
	  	   <p class="table-bt-gray t-center">
			 <a href="javascript:void(0)" class="abtn-blue submit" onclick="javascript:onSaveSmsConfig();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 	 <a href="javascript:void(0)" class="abtn-blue ml-20" onclick="resetSms();">重置</a><button type="reset" id="resetSmsButton" style="display:none;">重置</button>
	 	   </p>
	 	 </#if>
     </form>
    </#if>
  </div>  
   
<script type="text/javascript">
    var isSubmitting = false;
    $(document).ready(function(){
        vselect();
    })
    
    function onNeedconfirm(){
		var needConfirm = $("#needconfirm")[0].checked;
		$("#confirmusername")[0].disabled = needConfirm;
		$("#pro_confirmusername")[0].disabled = needConfirm;
		$("#confirmuserpwd")[0].disabled = needConfirm;
		$("#pro_confirmuserpwd")[0].disabled = needConfirm;
		$("#reConfirmuserpwd")[0].disabled = needConfirm;
		$("#pro_reConfirmuserpwd")[0].disabled = needConfirm;
	}
    
    function onSaveMailConfig(){
		if(isSubmitting){
			return ;
		}
		isSubmitting = true;
		if($("#mailActionTip_actionErrorsTip")){
				$("#mailActionTip_actionErrorsTip").attr("style","display:none;");
		}
		var hasError = false;
		if(!checkAllValidate('#mailDiv')){
		   isSubmitting = false;
		   return;
		}
		if($("#needconfirm")[0].checked){
			if(trim($("#displayaddress")[0].value) == ""){
				$("#displayaddress")[0].value = $("#confirmusername")[0].value;
			}
		}else{
			showMsgError("请勾选发邮件是否需要安全认证");
			isSubmitting = false;
			return ;
		}
		var needconfirm;
		var confirmusername;
		var confirmuserpwd;
		needconfirm = $("#needconfirm")[0].checked ? 1 : 0;
		confirmusername = $("#confirmusername")[0].value;
		confirmuserpwd = $("#confirmuserpwd")[0].value;
		ip=$("#ip")[0].value;
		displayaddress=$("#displayaddress")[0].value;
		showSaveTip();
		$.ajax({
		     url:"${request.contextPath}/system/admin/platformInfoAdmin-remoteServerConfig-saveMailConfig.action",
		     type:"post",
		     data:{"mailServerDto.ip":ip,"mailServerDto.needconfirm":needconfirm,"mailServerDto.confirmusername":confirmusername,"mailServerDto.confirmuserpwd":confirmuserpwd,"mailServerDto.displayaddress":displayaddress},
		     dataType:"json",
		     timeout:5000000,
		     error:function(){
		         closeTip();
		         showMsgError('邮件服务器配置保存失败！');
		     },
		     success:function(data){
		        closeTip();	
	            var result = data.result;
	            if(result[0] == 0){  //0:代表失败，1:代表成功
					showMsgError(result[1]);
				}else{
					showMsgSuccess(result[1]);
				}	
		     }
		})
		isSubmitting = false;
	}
	
	function checkMailPassword(){
	    if(trim($("#confirmuserpwd")[0].value) != trim($("#reConfirmuserpwd")[0].value)){
			addFieldError("reConfirmuserpwd","请确认安全认证使用的用户密码填写一致");
			isSubmitting = false;
			return false;
		}
	    return true;
	}
	
	function checkMailAddress(){
	    if(trim($("#confirmusername")[0].value) != trim($("#displayaddress")[0].value)){
			addFieldError("displayaddress","请确认 安全认证使用的账号 和 系统邮件显示的发送地址 填写一致");
			isSubmitting = false;
			return false;
		}
		return true;
	}
	
	function onSaveSmsConfig(){
		if(isSubmitting){
			return ;
		}
		isSubmitting = true;
		if($("#smsActionTip_actionErrorsTip")){
			$("#smsActionTip_actionErrorsTip").attr("style","display:none");
		}
		var hasError = false;
		if(!checkAllValidate('#smsDiv')){
		   isSubmitting = false;
		   return;
		}
		var server=$("#server")[0].value;
		var port=$("#port")[0].value;
		var localName=$("#localName")[0].value;
		var localPwd=$("#localPwd")[0].value;
		showSaveTip();
	
		$.ajax({
		     url:"${request.contextPath}/system/admin/platformInfoAdmin-remoteServerConfig-saveSmsConfig.action",
		     type:"post",
		     data:{"smsServerDto.server":server,"smsServerDto.port":port,"smsServerDto.localName":localName,"smsServerDto.localPwd":localPwd},
		     dataType:"json",
		     timeout:5000,
		     error:function(){
		         closeTip();
		         showMsgError('通信服务器配置保存失败！');
		     },
		     success:function(data){
		        closeTip();	
	            var result = data.result;
	            if(result[0] == 0){  //0:代表失败，1:代表成功
					showMsgError(result[1]);
				}else{
					showMsgSuccess(result[1]);
				}
		     }
		})
		isSubmitting = false;
	}
	
	function checkPort(){
	    val=$("#port")[0].value;	
		var pattern=/[^0-9]/;
		if(pattern.test(val) || val.slice(0,1)=="0"){
			addFieldError("port","端口只能为非零的整数！");		
			isSubmitting = false;
			return false;
		}
		return true;		
	}
		
	function checkSmsPassword(){
	   if(trim($("#localPwd")[0].value) != trim($("#reLocalPwd")[0].value)){
			addFieldError("reLocalPwd","请确认通讯帐户密码填写一致");
			isSubmitting = false;
			return false;
		}
	    return true;
	}
	
	function resetMail(){
	    $("#resetButton")[0].click();
	    if($("#hiddenNeedconfirm")[0].value == "1") {
	      $("#needconfirm")[0].checked = true;
	      $("#ckbNeedconfirm").attr("class","ui-checkbox ui-checkbox-current");
	    }else {
	       $("#needconfirm")[0].checked = false;
	      $("#ckbNeedconfirm").attr("class","ui-checkbox");
	    }
	}
	
	function resetSms(){
	    $("#resetSmsButton")[0].click();
	}
</script>     
</@htmlmacro.moduleDiv>