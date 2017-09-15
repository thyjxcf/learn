<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="">
	<@common.tableList id="tablelist">
	<tr>
		<th>部门名称</th>
		<th>负责人</th>
		<th style="text-align:center;">操作</th>
	</tr>
	<#if leaderList?exists && leaderList?size gt 0>
	<#list leaderList as lead>
	<tr>
		<input type="hidden" id="deptId_${lead_index}" name="deptId" value="${lead.deptId!}"></input>
		<td>${lead.deptName!}</td>
		<td>
			<input type="hidden" id="leadId_${lead_index}" value="<#if lead.id?exists>${lead.id!}<#else></#if>"></input>
			<div style="display:none;" id="userId_${lead_index}" val="${lead.userId!}"></div>
			<div id="userName_${lead_index}" val="${lead.userName!}"><#if lead.userId?exists>${lead.userName!}<#else>没有负责人</#if></div>
		</td>
		<td style="text-align:center;"><a href="javascript:void(0)" onclick="doEdit('${lead_index}')">修改</a></td>
	</tr>
	</#list>
	<#else>
		<tr>
			<td colspan="3"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
		<tr>
	</#if>
	</@common.tableList>
	<@commonmacro.selectOneUser idObjectId="userId" nameObjectId="userName" width=400 height=400 callback="userSet1">
		<input type="hidden" id="leadIndex" name="leadIndex" />
		<input type="hidden" id="userId" name="lead.userId" value=""/> 
		<input type="hidden" id="userName" name="lead.userName" value=""/>
		<a id="poppp"></a>
	</@commonmacro.selectOneUser>
<script>
	function doEdit(index){
		$("#leadIndex").val(index);
		$("#userId").val($("#userId_"+index).attr("val"));
		$("#userName").val($("#userName_"+index).attr("val"));
		$("#poppp").click();
	}
	function userSet1(){
		var index=$("#leadIndex").val();
		$("#userId_"+index).val($("#userId").val());
		$("#userName_"+index).html($("#userName").val());
		
		var userId=$("#userId").val();
		var deptId=$("#deptId_"+index).val();
		var leadId=$("#leadId_"+index).val();
		var url="${request.contextPath}/office/meeting/workmeeting-meetAuthEdit.action?userId="+userId+"&deptId="+deptId+"&leadId="+leadId;
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
		load("#workmeetingDiv","${request.contextPath}/office/meeting/workmeeting-meetAuthList.action");
	}
</script>
</@common.moduleDiv>