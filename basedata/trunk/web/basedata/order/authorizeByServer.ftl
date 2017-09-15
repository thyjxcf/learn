<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script>	
$(function(){
	<#if stack.findValue('@net.zdsoft.eis.base.common.entity.Ware@ROLE_TYPE_STUDENT') == roleType>
		<#assign groupTypeName = "班级">
		<#assign roleName = "学生">
	<#elseif stack.findValue('@net.zdsoft.eis.base.common.entity.Ware@ROLE_TYPE_FAMILY') == roleType>
		<#assign groupTypeName = "班级">
		<#assign roleName = "家长">
	<#else>
		<#assign groupTypeName = "部门">
		<#assign roleName = "教师">
	</#if>
	changeGroupType(1);
});

function queryTeacherList(deptId){
		var roleType = '${roleType}';
		var ids = [];
		var i=0;
		$("#authServerDiv input[name='serverIds'][type='hidden']").each(function(){
			ids[i] = $(this).val();
			i++; 
		});
		//班级或部门
		var action="${request.contextPath}/basedata/order/serverAuthorize-authorizeByServerUser.action?"+$.param({groupIds:deptId, serverIds:ids, roleType:roleType}, true);
		load("#objectDiv", action);
}

function showReply(data){
	if(!data.operateSuccess){
		showMsgWarn(data.promptMessage);
	}else{
		load("#objectDiv", "");
	}
}

function changeGroupType(groupType){
	var roleType = '${roleType}';
	var serverIds = [];
	var i=0;
	$("#authServerDiv input[name='serverIds'][type='hidden']").each(function(){
		serverIds[i] = $(this).val();
		i++;
	});
	if(groupType == 1){
		document.getElementById("resizeColumn").style.display = "none";
		load("#objectDiv", "${request.contextPath}/basedata/order/serverAuthorize-groupList.action?"+$.param({roleType:roleType, serverIds:serverIds}, true));
	}else{
		document.getElementById("resizeColumn").style.display = "";
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/basedata/order/serverAuthorize-authorizeByServerUser.action",
			data: $.param({roleType:roleType, serverIds:serverIds}, true),
			success: function(data){
				if(!data.operateSuccess){
				
						//showMsgError(data.promptMessage);
						$("#objectDiv").html('<p class="no-data mt-50 mb-50">'+data.promptMessage+'</p>');
						return;
				}else{
					load("#objectDiv", "${request.contextPath}/basedata/order/serverAuthorize-authorizeByServerUser.action?"+$.param({roleType:roleType, serverIds:serverIds}, true));
					return;
				}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	}	
}

function cancel(){
	load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-serverList.action?roleType=${roleType}");
}
</script>
<form action="" method="post" id="formServerForm">
<div id="authServerDiv">
<input type="hidden" name="roleType" value="${roleType}">
<#list serverIds as serverId>
	<input type="hidden" name="serverIds" value="${serverId}">
</#list>
</div>
</form>
<div class="query-builder">
	<div class="query-part">
		<#assign maxsize=10 />
		<div class="query-tt">为以下服务进行授权：<#list servers as x>${x.name?default('')}<#if x_has_next&&(x_index+1<maxsize)>、</#if><#if (x_index+1 >= maxsize)>等<#break></#if></#list></div>
		<div class="query-tt">&nbsp;&nbsp;请选择需要授权的对象:</div>
		<span class="ui-radio ui-radio-current" data-name="a"><input type="radio" class="radio " name="objectType" value="1" onclick="changeGroupType(this.value);">按${groupTypeName}</span>
		<span class="ui-radio" data-name="a"><input type="radio" class="radio" name="objectType" value="2" onclick="changeGroupType(this.value);">按${roleName}用户</span>
	</div>
</div>
<div id="resizeColumn" style="display:none">
	<#if stack.findValue('@net.zdsoft.eis.base.common.entity.Ware@ROLE_TYPE_STUDENT') == roleType>
		<div class="query-builder">
		    	<div class="query-part fn-rel fn-clear">
					<@commonmacro.selectDiv idObjectId="specialtyId" nameObjectId="specialtyId" url="/common/getSpecialtySelectDivData.action" divName='请选择专业' onclick="" referto="classId">
					</@commonmacro.selectDiv>
					<@commonmacro.selectDiv idObjectId="classId" nameObjectId="classId" url="/common/getClassSelectDivData.action" divName='请选择班级' onclick="queryTeacherList" dependson="specialtyId">
					</@commonmacro.selectDiv>
				</div>
		</div>
	<#elseif stack.findValue('@net.zdsoft.eis.base.common.entity.Ware@ROLE_TYPE_FAMILY') == roleType>
		<div class="query-builder">
		    	<div class="query-part fn-rel fn-clear">
					<@commonmacro.selectDiv idObjectId="specialtyId" nameObjectId="specialtyId" url="/common/getSpecialtySelectDivData.action" divName='请选择专业' onclick="" referto="classId">
					</@commonmacro.selectDiv>
					<@commonmacro.selectDiv idObjectId="classId" nameObjectId="classId" url="/common/getClassSelectDivData.action" divName='请选择班级' onclick="queryTeacherList" dependson="specialtyId">
					</@commonmacro.selectDiv>
				</div>
		</div>
	<#else>
	<div id="treeDiv" class="tree-menu-search" >
		<@commonmacro.couplingSelect idObjectId="deptId" nameObjectId="deptId" url="/common/getDeptSelectDataByParentId.action" divName='请选择部门' onclick="queryTeacherList">
		</@commonmacro.couplingSelect>
	</div>
	</#if>
</div>
<div class="dtree-ri" id="objectDiv">
<p class="no-data mt-50 mb-50">还没有任何信息！</p>
</div>
<script>
vselect();
</script>
</@htmlmacro.moduleDiv>