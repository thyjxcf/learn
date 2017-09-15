<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">

<script language="javascript">
function saveInfo(){
	if(!checkAllValidate("#unitSaveForm")){
		return;
	}
	var unittype = document.getElementById("unittype").value;
	if(unittype != '${unittype_subedu}' && unittype !='${unitclass_nonedu}'&& unittype !='${unitclass_edu}'){
		if(document.getElementById('unitusetype').value == "01"){
			showMsgError("单位使用类别不能为教育局");
			return ;
		}
	}
	
	<#if unitclass_school==unitDto.unitclass>
		<#if unittype_asp==unitDto.unittype || unittype_kg==unitDto.unittype >
			var pattern = /^[0-9]{1,46}$/;
		   	if(!pattern.test(trim($('#schCode').val()))) {
		   		showMsgError('学校代码必须为全数字组合(1-46位)！');
				return ;
		   	}
			
			var schtype = $('#schtype').val();
			//这个涉及到学籍管理的学年学期设置，没有学制，导致界面报错
			if(unittype == 3 && (schtype == ${unitTypeKg111!} || schtype == ${unitTypeKg112!} || schtype == ${unitTypeKg119!})){
				showMsgError('单位类型为托管中小学，学校类别不能为幼儿园');
				return ;
			}
			if(unittype == '${unittype_kg}'){
				if(schtype != ${unitTypeKg111!} && schtype != ${unitTypeKg112!} && schtype != ${unitTypeKg119!}){
					showMsgError('学校类别不属于幼儿园类型，请修改');
					return ;
				}
			}
		</#if>
	</#if>
	if(!checkOrderid(document.getElementById('orderid'),'排序编号')){
		return ;
	}
	if(document.getElementById('userDtopassword').value!=document.getElementById('confirmPassword').value){
		showMsgError('请确认单位管理员用户的登录密码填写一致');
		return ;
	}
	var obj= document.getElementById("syncSmsCenter");
	var remoteSyncSmsCenter="";
	if(obj){
		remoteSyncSmsCenter = obj.value;
	}
	var options = {
		url : "${request.contextPath}/basedata/unit/unitAdmin-update.action?remoteSyncSmsCenter"+remoteSyncSmsCenter,
	    dataType : 'json',
		success : showSuccessOnly,
		clearForm : false,
		resetForm : false,
		type : 'post',
		error:function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	};
  	$("#unitSaveForm").ajaxSubmit(options);
}
function showSuccessOnly(data){
	if(data.operateSuccess){
		showMsgSuccess("保存成功","",doBackList);
	}else{
		showMsgError(data.errorMessage);
		isSubmitting=false;
		return;
	}
}
function doBackList(){
	//如果返回初始化列表，使用parentUnitId参数
	var str = "?modID=${modID?default('')}&unitId=${unitDto.parentid?default("")}&pageIndex=${pageIndex!}";
    load("#container1","${request.contextPath}/basedata/unit/unitAdmin-list.action"+str);
};

function checkOrderid(elem,field){
 	var flag=false;
    //var str=/[a-z]|[A-Z]|[0-9]/;
    var str=/[0-9]/;
    var str1=trim(elem.value).length;
 	for(var i=0;i<str1;i++){
    	if(!str.test(trim(elem.value).charAt(i))){
	    	flag=true;
			break;
		}
	}
	if(flag==true){
	   addFieldError(elem,field+"只能为数字！");
	   //addFieldError(elem,field+"只能为数字和字母！");
	   elem.focus();
	   return false;
       }  
    
    return true;   
 }

function changeParent(elem){
	//将原来的unionid和上级单位保存下来
	var oldUnionid = "${unitDto.unionid?default('')}";
	var oldParentid = "${unitDto.parentid?default('')}";
	var unitid=elem.value;
	if(oldParentid == unitid && oldUnionid != ""){
		document.getElementById('unionid').value=oldUnionid;
		return;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/basedata/unit/unitAdmin-unitGetUnionId.action",
		data: $.param({unitId:unitid,unitClass:${unitclass_school}},true),
		dataType: "json",
		success: function(data){
			var result=data;
			if(result!=null){
				document.getElementById('unionid').value=result;
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
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
<div id="unitContent">
<form name="unitSaveForm" id="unitSaveForm" method="POST" action="" >
<input type="hidden" name="id" value="${unitDto.id?default('')}">
<input type="hidden" name="regionlevel" value="${unitDto.regionlevel?default('')}">
<input type="hidden" name="region" value="${unitDto.region?default('')}">
<input type="hidden" name="usetype" value="${unitDto.usetype?default('')}">
<input type="hidden" name="regcode" value="${unitDto.regcode?default('')}">
<input type="hidden" name="etohSchoolId" value="${unitDto.etohSchoolId?default('')}">
<input type="hidden" name="mark" value="${unitDto.mark?default('1')}">
<p class="tt"><a href="javascript:void(0)" class="close">关闭</a><span>编辑单位</span></p>
<div class="wrap pa-10">
<table border="0" cellspacing="0" cellpadding="0" class="table-list-edit table-form mt-5">
	<tr>
  	  <input type="hidden" name="unitPartitionNum" value="${unitDto.unitPartitionNum?default('')}"/>
  	  <th width="18%">统一编号：</th>
  	  <td width="32%"><input name="unionid" id="unionid" type="text" style="width:150px;" class="input-txt input-readonly" readonly="true" value="${unitDto.unionid?default('')?trim}"></td>		  	  	  	  	  
  	  <th width="18%">单位分类：</th>
	  <td width="32%">
	  	${appsetting.getMcode("DM-DWFL").get(unitDto.unitclass?default('')?string)}
	  	<input name="unitclass" type="hidden" value="${unitDto.unitclass?default('')?string}">
	  </td>
  	</tr>		  	  	  	  	
  	<tr>
  	  <th><span class="c-red">*</span>单位名称：</th>
  	  <td><input name="name" id="name" type="input" style="width:150px;" notNull="true" msgName="单位名称" tabindex="1" value="${unitDto.name?default('')?trim}"
  	    <#if unitDto.parentid == "00000000000000000000000000000000">
  	    	class="input-txt input-readonly" title="顶级单位，不允许修改单位名称"
  	    <#else>
  	  	    <#if unitDto.usetype==2>class="input-txt input-readonly" title="报送单位，不允许本地修改单位名称"
  	  	  	<#else>class="input-txt" 		  	  	  	  	  			  	  	  	  	   	  	  	  	  		  	  	  	  	  
  	  	  	</#if>
  	  	</#if>
  	   maxlength="64"></td>
	  <th>单位类型：</th>
	  <td>
	  	${appsetting.getMcode("DM-DWLX").get(unitDto.unittype?default('')?string)}	  					  
	  	<input name="unittype" id="unittype" type="hidden" value="${unitDto.unittype?default('')?string}">
	  </td>
	</tr>
	<tr>
	  <th ><span class="c-red">*</span>上级单位：</th>
	  <td>
	  	  <select name="parentid" id="parentid" class="input-txt" notNull="true" msgName="上级单位" style="width:162px;" onchange="changeParent(this);"
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
	  <th>行政级别：</th>
	  <td>
	  	${appsetting.getMcode("DM-XZJB").get(unitDto.regionlevel?default("")?string)}
	  </td>							  
	</tr>
	<tr>
	  <th>注册日期：</th>
	  <td><input type="text" class="input-txt input-readonly" style="width:150px;" readonly="true" value="<#if unitDto.creationTime?exists>${unitDto.creationTime?string("yyyy-MM-dd")}</#if>"></td>					  						  						  						  
	  <th>报送类型：</th>
	  <td>
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
	  <th>排序编号：</th>
	  <td><input name="orderid" id="orderid" type="text" class="input-txt" style="width:150px;" tabindex="8" value="${unitDto.orderid?default('')}"  maxlength="18"></td>						  
	  	
	  <th>单位使用类别：</th>
	  <td>
		<select id="unitusetype" name="unitusetype" class="input-txt" style="width:162px;" tabindex="9">
		  	${appsetting.getMcode("DM-UNITUSETYPE").getHtmlTag(unitDto.getUnitusetype()?default(''),true, 1)}
  	  	</select>
	  </td>
	</tr>
	<#if unitclass_school==unitDto.unitclass>
	<tr >
		<th><span class="c-red">*</span>区域属性码：</th>
		
		<td id="colspanId">
		<select name="regionPropertyCode" id="regionPropertyCode" class="input-txt" style="width:162px;" notNull="true" msgName="区域属性码" >									
			${appsetting.getMcode("DM-TZ-QYSX").getHtmlTag(unitDto.regionPropertyCode?default(""),true,1)}
		</select>
		</td>
	<#if unittype_asp==unitDto.unittype || unittype_kg==unitDto.unittype>
		<th><span class="c-red">*</span>学校代码：</th>
		<td><input name="schCode" id="schCode" type="text" notNull="true" msgName="学校代码" class="input-txt" style="width:150px;"
				maxlength="46" value="${unitDto.schCode?default('')?trim}" title="学校代码必须为1-46位">
		</td>
	</#if>
	</tr>
	<#if unittype_asp==unitDto.unittype || unittype_kg==unitDto.unittype >
	<tr >
			<th><span class="c-red">*</span>学校类别：</th>
			<td>									
				<select name="schtype" id="schtype" class="input-txt" style="width:162px;" notNull="true" msgName="学校类别" >									
					${appsetting.getMcode("DM-XXLB").getHtmlTag(unitDto.schtype?default(""),true,1)}
				</select>
			</td>
			<th><span class="c-red">*</span>办学性质：</th>
			<td>									
				<select name="runschtype" id="runschtype" class="input-txt" style="width:162px;" notNull="true" msgName="办学性质" >									
					${appsetting.getMcode("DM-XXBB").getHtmlTag(unitDto.runschtype?default(""),true,1)}
				</select>
			</td>
		</tr>
	</#if>
	</#if>
	<!--<tr >
		<th>组织机构代码：</th>
		<td colspan=3>									
			<input name="organizationCode" id="organizationCode" type="text" class="edit-txt input-txt" dataType="integer" style="width:150px;" value="${unitDto.organizationCode?default('')}"  maxlength="9">
		</td>
		<th>单位性质：</th>
		<td>									
			<select name="unitProperty" id="unitProperty" class="input-txt" style="width:162px;" notNull="false" >									
				${appsetting.getMcode("DM-UNITPROPERTY").getHtmlTag(unitDto.unitProperty?default(""),true,1)}
			</select>
		</td>
	</tr>-->
</table>
<table border="0" cellspacing="0" cellpadding="0" class="table-form mt-5">
	<tr class="t-center"><th colspan="4" class="tt">该单位管理员维护</th></tr>
  	<tr>
	  	<th><span class="c-red">*</span>用户名：</th>
	  	<td>
	  	  <input name="userDto.name" id="userDtoname" type="text" notNull="true" msgName="用户名" class="input-txt <#if connectPassport?default(false)>input-readonly</#if>" style="width:150px;"  tabindex="12" value="${userDto.name?default('')}" <#if connectPassport?default(false)>readonly="readonly"</#if>>
	  	</td>
	  	<td colspan="2"></td>		  	  	  	  	  	  
	</tr>
	<tr>
	  	<th width="15%"><span class="c-red">*</span>登录密码：</th>
	  	<td width="35%">
	  	  <input name="userDto.password" id="userDtopassword" type="password" notNull="true" msgName="登录密码" tabindex="13" class="input-txt" style="width:150px;" value="${password_default}" title="${userPasswordFieldTip}"></td>
	  	<th width="15%"><span class="c-red">*</span>密码确认：</th>
	  	<td width="35%"><input name="userDto.confirmPassword" id="confirmPassword" type="password" notNull="true" msgName="密码确认" tabindex="14" class="input-txt" style="width:150px;" value="${password_default}"></td>
	</tr>
	<#-- 
	  <tr>
	  	<th>电子邮件地址：</th>
	  	<td colspan="3"><input name="userDto.email" id="userdtoemail" type="text" class="input-txt150" size="80" tabindex="15" value="${userDto.email?default('')}" title="请输入用户联系电子邮件地址"></td>		  	  	  	  	  	
	  </tr>
	-->
  	  	<!--start smscenter-->
  	<#if (useSmsCenter&&smsUseConfigClientId.equals(""))>
  	<tr>
  	  <th>创建短信账号：</th>
  	  <td colspan="3">
  	  	<input type="hidden" name="syncSmsCenter" id="syncSmsCenter" value="0">
	   &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="isSmsCenter1" name="isSmsCenter" value="1" onclick="syncSmsCenter.value=this.value;" ><label for="isSmsCenter1">是，同步创建账号</label>
		&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="isSmsCenter2" name="isSmsCenter" value="0" onclick="syncSmsCenter.value=this.value;" checked><label for="isSmsCenter2">否，不同步创建账号</label>  	  	  
  	  </td>
  	</tr>
  	<#elseif (useSmsCenter&&!smsUseConfigClientId.equals(""))>
  	<tr>
  		<th>短信账号管理：</th>
  	  <td colspan="3" title="删除此单位在短信平台的账号，同时停用此单位的短信配置">
  	  	<input type="hidden" name="syncSmsCenter" id="syncSmsCenter" value="0">
	   &nbsp;&nbsp;&nbsp;&nbsp;已创建账号，账号为&nbsp;${smsUseConfigClientId}<input type="hidden" name="currentClientid" value="${smsUseConfigClientId}">
		&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="delclient" onclick="changeDelClient()">
	  	<input type="hidden" name="delsmscenter" value="0">  	  	  
  	  </td>
  	</tr>
  	<#else>
  	</#if>
</table>
<input type="hidden" name="userDto.id" value="${userDto.id?default('')}">
<input type="hidden" name="userDto.realname" value="${userDto.realname?default('')}">
<input type="hidden" name="userDto.type" value="${userDto.type!}">
<input type="hidden" name="userDto.unitid" value="${userDto.unitid?default('')}">
</div>
<p class="dd">
	<a href="javascript:saveInfo(); " class="abtn-blue submit">保存</a>
	<a href="javascript:void(0);" class="abtn-blue reset ml-5">取消</a>
</p>
</form>
</div>
<script type="text/javascript">


</script>    
</@htmlmacro.moduleDiv>