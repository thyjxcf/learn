<#import "/common/htmlcomponent.ftl" as common>
<#assign HASEND=stack.findValue("@net.zdsoft.office.meeting.entity.OfficeWorkMeeting@HAS_END")>
<#assign NOTEND=stack.findValue("@net.zdsoft.office.meeting.entity.OfficeWorkMeeting@NOT_END")>
<@common.moduleDiv titleName="">
	<@common.tableList id="tablelist">
		<tr>
			<th>会议名称</th>
			<th>会议类型</th>
			<th>会议日期</th>
			<th>会期</th>
			<th>状态</th>
			<th style="text-align:center;">操作</th>
		</tr>
		<#if meetinglist?exists && meetinglist?size gt 0>
			<#list meetinglist as min>
				<tr>
					<td><a href="javascript:showDetail('${min.id!}')">${min.name!}</a></td>
					<td>${appsetting.getMcode("DM-GZHYLX").get(min.type!)}</td>
					<td>${min.meetingDate?string('yyyy-MM-dd HH:mm')}</td>
					<td>${min.days?string('0.#')!}</td>
					<td><#if (min.sueState==NOTEND)>未结束<#elseif (min.sueState==HASEND)>已结束</#if></td>
					<td style="text-align:center;"><a href="javascript:void(0)" <#if (min.sueState==NOTEND)>onclick="doWarn();"<#else>onclick="minManage('${min.id!}');"</#if>>纪要管理</a></td>
				</tr>
			</#list>
		<#else>
			<tr>
				<td colspan="6"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
			<tr>
		</#if>
	</@common.tableList>
	<@common.Toolbar container="#meetMinutesManageListDiv"/>
<script>
	function minManage(id){
		var url="${request.contextPath}/office/meeting/workmeeting-meetingMinutesManageEdit.action?meetingId="+id;
		load("#meetMinutesManageListDiv",url);
	}
	
	function showDetail(id){
		var url="${request.contextPath}/office/meeting/workmeeting-queryView.action?meetingId="+id;
		load("#meetMinutesManageListDiv",url);
	}
	
	function doWarn(){
		showMsgError("请待会议结束后再维护！");
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>