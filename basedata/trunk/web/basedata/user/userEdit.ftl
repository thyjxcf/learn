<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="${webAppTitle}--编辑用户">
<script>
var prefix="${request.contextPath}/static/common/xtree/";
</script>
<script language="JavaScript" src="${request.contextPath}/static/common/xtree/xtree.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/common/xtree/webfxcheckboxtreeitem.js"></script>
<script>
function saveValue(){
	if(!checkAllValidate("#userDiv")){
		return false;
	}
	
	if(document.getElementById('mark').value==''){
		addFieldError('mark','请选择用户状态');
		return false;
	}
	jQuery.ajax({
   		url:"${request.contextPath}/basedata/user/userAdmin-update.action?modID=${modID?default('')}&&ec_p=${ec_p?default('')}&&ec_crd=${ec_crd?default('')}",
   		type:"POST",
   		data:jQuery('#userform').serialize(),
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
function changeRole(rolId){
	load(".treDiv","${request.contextPath}/basedata/user/userAdmin-roleRightView.action?uid=${user.id?default('')}&&roleId="+rolId);
}
function showUserRight(){
	load(".treDiv","${request.contextPath}/basedata/user/userAdmin-roleRightView.action?uid=${id?default('')}");
}
function changeTeacher(elem){
	var index=elem.selectedIndex;
	var realname=document.getElementById('realname');
	if(realname && realname.value == ""){
		if(elem.options[index].value==''){ return;}
		realname.value=elem.options[index].text;
	}
}
function gback(){
	load("#container","${request.contextPath}/basedata/user/userAdmin.action");
}
</script>
<form action="" method="POST" name="userform" id="userform">
<input name="type" type="hidden" value="${user.type?default('')}">
<input name="deptid" id="deptid" type="hidden" value="${user.deptid?default('')}">
<input name="ownerType" type="hidden" value="${user.ownerType?default(2)}">
<input name="sequence" type="hidden" value="${user.sequence?default(0)}">
<input name="accountId" type="hidden" value="${user.accountId?default('')}">

    <div class="wrap">
    	<div class="pub-table-wrap">
    	<div class="pub-table-inner">
            <div id="userDiv" class="fn-left" style="width:50%;">
            	<p class="permission-tt">编辑用户（所属单位：${unit.name?default('')}）</p>
            	<table border="0" cellspacing="0" cellpadding="0" class="table-form" style="overflow-y:auto;height:320px;">
                    <tr>
                    	<@common.tdi msgName="排序编号" name="orderid"  notNull="false"  dataType="integer" readonly="false" value="${user.getOrderid()?default('')}" maxLength="8"/>
		  			</tr>
					  	<#-- connectPassport
					  	  	<#if (user.sequence>0)><#assign seqStr=user.sequence?string></#if>
					  	  	<tr>
					  	  		<@common.tdi msgName="账号" name="sequence_readonly"  notNull="true" readonly="true" value="${seqStr?if_exists}" maxLength="60"/>
					  	  	</tr>
					  	-->
				  	<tr>
				  		<@common.tdi msgName="账号" name="name"  notNull="true" readonly="true" value="${user.getName()?default('')?trim}" maxLength="60"/>
				  	    <input name="isdeleted" type="hidden" value="${user.isdeleted?string}">
				  	    <input name="id" type="hidden" value="${user.id?default('')}">
				  	  	<input name="unitid" type="hidden" value="${user.unitid?default('')}">
				  	</tr>
		  	<tr>
		  	  <th>关联职工：</th>
		  	  <td>
		  	  <#if user.type?exists && (user.type==1 || user.type==0) && (loginInfo.unitClass==2)> 
		  		<#list teacherList as teacher>
		  	  	  <#if teacher.id.equalsIgnoreCase(user.teacherid?default(''))>${teacher.name}</#if>
		  	  	</#list>
		  	  	<input name="teacherid" type="hidden" value="${user.teacherid?default('')}">
		  	  <#else>
		  	  	<div class="ui-select-box <#if user.teacherid?default("") != "">ui-select-box-disable</#if>" style="width:150px;">
                <input type="text" class="ui-select-txt" value="" />
                <input type="hidden" name="<#if user.teacherid?default("") != "">teacheridDisplay<#else>teacherid</#if>" class="ui-select-value" />
                <a class="ui-select-close"></a>
                <div class="ui-option">
                	<div class="a-wrap">
                    <a val=""><span>请选择</span></a>
                    <#list teacherList as teacher>
		  	  	  	  <a value="${teacher.id}" 
		  	  	  	  <#if teacher.id.equalsIgnoreCase(user.teacherid?default(''))>class="selected"</#if>>${teacher.name}</a>
		  	  	  	</#list>
		  	  	  	</div>
                </div>
           	 </div>
           	 	<#if user.teacherid?default("") != "">
		  	  	  	<input name="teacherid" type="hidden" value="${user.teacherid?default('')}">
		  	  	</#if>
		  	  </#if>
		  	  </td>		  	  	  	  	  
		  	</tr>
		  	<tr>
		  		<@common.tdi msgName="姓名" name="realname"  notNull="true" value="${user.realname?default('')?trim}" maxLength="60"/>
		  	</tr>
		  	<tr>
		  		<@common.tdi msgName="昵称" name="nickName"   value="${user.nickName?default('')?trim}" maxLength="60"/>
		  	</tr>
		  	<tr>
		  	<#if user.type?exists&&(user.type==1||user.type==0)>
		  	<#assign flag='true'>
		  	<#else><#assign flag='false'></#if>
  			<@common.tds msgName="用户状态" name="mark" value="${user.mark?default('1')}" readonly="${flag}" notNull="true">
			    	${appsetting.getMcode("DM-YHZT").getHtmlTag(user.mark?default('1')?string)}
			    </@common.tds>
		  	</tr>	
		  		
			<tr><td colspan="2" align="left">用户角色列表</td></tr>
			<#if roleList?exists&&(roleList?size>0)>			  	  	  	
			  	<#list roleList?if_exists as rol>
			  	  <tr>
				  	  <td colspan="2">
						<img src="${request.contextPath}/system/images/icon_sysclient.gif"/>
				  	  	<a <#if rol.isactive>href="javascript:changeRole('${rol.id?default('-1')}');"<#else>href="javascript:void(0);"</#if>>${rol.name?if_exists}<#if !rol.isactive><font color="red">(已锁定)</font></#if></a>
				  	  </td>
			  	  </tr>
			  	</#list>
			<#else>
			 	<tr>
			  	  <td colspan="2">（尚未委派角色）</td>
			  	</tr>
			</#if>
                </table>
            </div>
            <div class="fn-right treDiv" style="width:50%;"> </div>
           	<div class="fn-clear"></div>
        </div>
    </div>
   </div>
	<p style="text-align:center;padding:50px 0;">
	   <a class="abtn-blue" onclick="saveValue();" href="javascript:void(0);">保存</a>
	   <a class="abtn-blue ml-5" onclick="gback();"  href="javascript:void(0);"> 取消</a>
   </p>
</form>
<script>
$(document).ready(function(){
	load(".treDiv","${request.contextPath}/basedata/user/userAdmin-roleRightView.action?uid=${user.id?default('')}");
});
vselect();
</script>
</@common.moduleDiv>