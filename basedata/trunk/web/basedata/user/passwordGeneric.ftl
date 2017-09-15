<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="${webAppTitle}--密码生成规则">
<script>
function saveValue(){
	var genericpass1=document.getElementById('genericPass1');
	var genericpass2=document.getElementById('genericPass2');
	var genericpass3=document.getElementById('genericPass3');
	var selectdValue="";
	if(genericpass1.checked==true)
		selectdValue=genericpass1.value;
	if(genericpass2.checked==true)
		selectdValue=genericpass2.value;
	if(genericpass3.checked==true)
		selectdValue=genericpass3.value;
	
	var consistentpass=document.getElementById('consistentPass');
	var confirmpass=document.getElementById('confirmPass');
	if(selectdValue==${passwordUnionize}){
		if(consistentpass.value!=confirmpass.value){
			addActionError('请确认默认密码填写一致');
			consistentpass.value='';
			confirmpass.value='';
			consistentpass.focus();
			return ;
		}
		if(consistentpass.value.length<4 && consistentpass.value.length!=0 ){
			addActionError('默认密码不能少于4位');
			consistentpass.value='';
			confirmpass.value='';
			consistentpass.focus();
			return ;
		}
		//add 密码规则验证
		var repwd =/^[a-zA-Z0-9_]{4,20}$/;
		 if(consistentpass.value!='' && !repwd.test(consistentpass.value)) {  
    		addActionError("密码必须是4-20位的英文(A-Z，a-z)或数字(0-9)及下划线。");
    		return ;
  		}
	}
	
	if(confirm('确认保存？')){
		jQuery.ajax({
   		url:"${request.contextPath}/basedata/user/userAdmin-saveGenericPass.action?modID=${modID?default('')}",
   		type:"POST",
   		data:{"consistentPass":consistentpass.value,"genericPass":selectdValue,"confirmPass":confirmpass.value},
   		dataType:"JSON",
   		async:false,
   		success:function(data){
   			if(data.operateSuccess){
   					showMsgSuccess(data.promptMessage,"",function(){
   					load("#container","${request.contextPath}/basedata/user/userAdmin.action");
   				});
   			}else{
   				showMsgError(data.errorMessage);
   			}
   		}
  	 });
	}
}

function consistantPass(value,obj){
	
	if(obj){
		obj.checked="checked";
	}
	var consistentpass=document.getElementById('consistentPass');
	var confirmpass=document.getElementById('confirmPass');
	if(value==${passwordUnionize}){		
		confirmpass.disabled=false;
		consistentpass.disabled=false;
	}
	else{
		<#if unitIniDto.flag?exists&&unitIniDto.nowValue.equals(passwordUnionize?string)>
			confirmpass.value='${password_default}';
			consistentpass.value='${password_default}';
		<#else>
			confirmpass.value='';
			consistentpass.value='';
		</#if>
		confirmpass.disabled=true;
		consistentpass.disabled=true;
	}
}

function setDefault(){
	$("#span2").click();
	consistantPass($("#genericPass2").value);
}

</script>

<form name="passform" method="POST" id="passform">
	<p class="table-dt">设置初始密码规则</p>
	<@htmlmacro.tableDetail  class="table-form">
        <tr>
	  		  <th rowspan='4' width="110px;">空密码生成规则：<br><br>（密码留空时的，<br><br>统一生成方式）</th>
	  		  <td><span id="span1" class="ui-radio <#if unitIniDto.nowValue?exists&&unitIniDto.nowValue.equals(passwordUnionize?string)>ui-radio-current</#if>" data-name="a"><input type="radio" class="radio" name="genericPass" id="genericPass1" value="${passwordUnionize}" <#if unitIniDto.nowValue?exists&&unitIniDto.nowValue.equals(passwordUnionize?string)>checked </#if> onclick="consistantPass(this.value,this);">
	  		  	启用统一规则<#if unitIniDto.defaultValue?exists&&unitIniDto.defaultValue.equals(passwordUnionize?string)>（默认值）</#if></span></td>
	  		  <td class="send_padding_2">如果不输入密码，本单位用户密码自动生成为默认密码。</td>
	  		</tr>  	
	  		<tr>
	  		  <td><span id="span2" class="ui-radio <#if unitIniDto.nowValue?exists&&unitIniDto.nowValue.equals(passwordNull?string)>ui-radio-current</#if>" data-name="a"><input type="radio" class="radio" name="genericPass" id="genericPass2" value="${passwordNull}" <#if unitIniDto.nowValue?exists&&unitIniDto.nowValue.equals(passwordNull?string)>checked </#if> onclick="consistantPass(this.value,this);">
	  		  	启用默认密码<#if unitIniDto.defaultValue?exists&&unitIniDto.defaultValue.equals(passwordNull?string)>（默认值）</#if></span></td>
	  		  <td>如果不输入密码，本单位用户密码默认为${password_init?default("12345678")}。</td>
	  		</tr>

	  		<tr>
	  		  <td><span id="span3" class="ui-radio <#if unitIniDto.nowValue?exists&&unitIniDto.nowValue.equals(passwordName?string)>ui-radio-current</#if>" data-name="a"><input type="radio" class="radio " name="genericPass" id="genericPass3" value="${passwordName}" <#if unitIniDto.nowValue?exists&&unitIniDto.nowValue.equals(passwordName?string)>checked </#if> onclick="consistantPass(this.value,this);">
	  		  	启用账号规则<#if unitIniDto.defaultValue?exists&&unitIniDto.defaultValue.equals(passwordName?string)>（默认值）</#if></span></td>
	  		  <td>如果不输入密码，本单位用户密码默认为账号。 </td>
	  		</tr>
	  		<tr>
	  		  <td colspan="2">
	  		  默认密码：<input name="consistentPass" id="consistentPass" type="password" class="input-txt ml-5" maxLength="30" value="<#if unitIniDto.flag?exists&&unitIniDto.nowValue.equals(passwordUnionize?string)>${password_default}</#if>" onclick="this.value=''" title="请输入用户默认的密码！">
	  		  确认密码：<input name="confirmPass" id="confirmPass" maxLength="30" type="password" class="input-txt ml-5" value="<#if unitIniDto.flag?exists&&unitIniDto.nowValue.equals(passwordUnionize?string)>${password_default}</#if>" onclick="this.value=''" title="请确认重输一遍上输密码"></td>
	  		</tr>
	  		<tr>
	  			<td colspan=4 align="center">
            	<a href="javascript:void(0);" onclick="saveValue();" class="abtn-blue">保存</a>
            	<#if unitIniDto.nowValue?exists&&unitIniDto.nowValue.equals(passwordUnionize?string)>
            	<a href="javascript:void(0);" onclick="consistantPass('${passwordUnionize}');" class="abtn-blue">重置</a>
            	</#if>
            	<a href="javascript:void(0);" onclick="setDefault();" class="abtn-blue">恢复默认值</a>
                <a href="javascript:void(0);" class="abtn-blue reset ml-5">取消</a>
	  			</td>
	  		</tr>
</@htmlmacro.tableDetail>
</form>
<script>
	consistantPass('${unitIniDto.nowValue?default('')}');
</script>
</@htmlmacro.moduleDiv>
