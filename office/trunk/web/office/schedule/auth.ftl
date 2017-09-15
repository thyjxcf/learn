<#import "/common/htmlcomponent.ftl" as common>
	<p class="f14 b pb-10 mt-20">单位维护人员</p>
	<@common.tableList id="tablelist1" name="tablelist1" class="public-table table-list table-dragSort mt-15" style="table-layout:fixed">
	<tr>
		<th class="t-center">单位名称</th>
		<th class="t-center">负责人</th>
		<th class="t-center">操作</th>
	</tr>
	<#if authUnitList?exists && authUnitList?size gt 0>
	<#list authUnitList as lead>
	<tr>
		<td class="t-center">${lead.objectName!}
			<input type="hidden" id="authId_${lead_index}" value="${lead.id!}"/>
			<input type="hidden" id="type_${lead_index}" value="${lead.authType!}"/>
			<input type="hidden" id="objectId_${lead_index}" name="objectId" value="${lead.objectId!}"/>
		</td>
		<td class="t-center">
			<input type="hidden" id="leaderId_${lead_index}" value="${lead.leaderId!}" />
			<div id="leaderName_${lead_index}" val="${lead.leaderName!}"><#if lead.leaderId?exists>${lead.leaderName!}<#else>没有维护人员</#if></div>
		</td>
		<td class="t-center"><a href="javascript:void(0)" onclick="doEdit('${lead_index}','${lead.authType!}')">修改</a></td>
	</tr>
	</#list>
	</#if>
	</@common.tableList>
	<p class="f14 b pb-10 mt-20">各科室维护人员</p>
	<@common.tableList id="tablelist2" name="tablelist2" class="public-table table-list table-dragSort mt-15" style="table-layout:fixed">
	<tr>
		<th class="t-center">部门名称</th>
		<th class="t-center">负责人</th>
		<th class="t-center">操作</th>
	</tr>
	<#if authDeptList?exists && authDeptList?size gt 0>
	<#list authDeptList as lead>
	<tr>
		<td class="t-center">${lead.objectName!}
			<input type="hidden" id="authId_${lead_index+1}" value="${lead.id!}"/>
			<input type="hidden" id="type_${lead_index+1}" value="${lead.authType!}"/>
			<input type="hidden" id="objectId_${lead_index+1}" name="objectId" value="${lead.objectId!}"/>
		</td>
		<td class="t-center">
			<input type="hidden" id="leaderId_${lead_index+1}" value="${lead.leaderId!}" />
			<div id="leaderName_${lead_index+1}" val="${lead.leaderName!}"><#if lead.leaderId?exists>${lead.leaderName!}<#else>没有维护人员</#if></div>
		</td>
		<td class="t-center"><a href="javascript:void(0)" onclick="doEdit('${lead_index+1}','${lead.authType!}')">修改</a></td>
	</tr>
	</#list>
	</#if>
	</@common.tableList>
	<div id="userDiv"></div>
	<input type="hidden" id="leadIndex" name="leadIndex" />
	<input type="hidden" id="leaderId" name="leaderId" value=""/> 
	<input type="hidden" id="leaderName" name="leaderName" value=""/>
	
<script>
	function doEdit(index,type){
		$("#leadIndex").val(index);
		$("#leaderId").val($("#leaderId_"+index).val());
		$("#leaderName").val($("#leaderName_"+index).attr("val"));
		var deptId='';
		if(type == '1'){
			deptId=$("#objectId_"+index).val();
		}
		var url =encodeURIComponent("/office/schedule/common/getDeptUserSelectDivData.action?showLetterIndex=true&preGraduateInclude=false&deptId="+deptId);
		load("#userDiv","${request.contextPath}/common/popDiv.action?idObjectId=leaderId&nameObjectId=leaderName&nameObjectValue="+$("#leaderName").val()+"&callback=userSet1&url="+url+"&switchId=pop-user&popup=true&useCheckbox=true&loadDataCallBack=popShow");
	}
	function userSet1(){
		var index=$("#leadIndex").val();
		$("#leaderId_"+index).val($("#leaderId").val());
		$("#leaderName_"+index).html($("#leaderName").val());
		$("#leaderName_"+index).attr("val",$("#leaderName").val());
		if($("#leaderName").val() == ''){
			$("#leaderName_"+index).html("没有维护人员");
		}
		var leaderId=$("#leaderId").val();
		var objectId=$("#objectId_"+index).val();
		var type=$("#type_"+index).val();
		var authId=$("#authId_"+index).val();
		var url="${request.contextPath}/office/schedule/schedule-authEdit.action?auth.leaderId="+leaderId+"&auth.objectId="+objectId+"&auth.id="+authId+"&auth.authType="+type;
		$.post(url,null,function(data){
			showReply(data);
		},'json');
	}
	function showReply(data){
		if(!data.operateSuccess){
			showMsgError(data.promptMessage);
		}else{
			showMsgSuccess(data.promptMessage);
		}
	}
</script>
