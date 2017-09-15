<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
	<@common.tableList id="tablelist">
		<tr>
			<th style="width:15%;">会议名称</th>
			<th style="width:15%;">会议日期</th>
			<th>地点</th>
			<th style="width:10%;">会期</th>
			<th style="width:30%">主办科室</th>
			<th style="width:15%;text-align:center;">操作</th>
		</tr>
		<#if meetingList?exists && meetingList?size gt 0>
		<#list meetingList as ml>
		<tr>
			<td><a href="javascript:showDetail('${ml.id!}');">${ml.name!}</a></td>
			<td>${ml.meetingDate?string('yyyy-MM-dd HH:mm')?if_exists}</td>
			<td>${ml.place!}</td>
			<td>${ml.days?string("0.#")!}</td>
			<td>${ml.hostDeptStr!"部门已删除"}</td>
			<td style="text-align:center;"><a href="javascript:void(0)" onclick="showAttendInfo('${ml.id!}')">到会人员</a></td>
		</tr>
		</#list>
		<#else>
			<tr>
				<td colspan="6"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
			<tr>
		</#if>
	</@common.tableList>
	<@common.Toolbar container="#meetQueryListDiv"/>
	<div class="popUp-layer" id="classLayer" style="display:none;width:450px;"></div>
<script>
	function showAttendInfo(id){
		var url="${request.contextPath}/office/meeting/workmeeting-showAttendInfo.action?meetingId="+id;
		openDiv("#classLayer","#classLayer .close,#classLayer .submit,#classLayer .reset",url,null,null,"200px",function(){
			
		});
	}
	
	function showDetail(id){
		var url="${request.contextPath}/office/meeting/workmeeting-queryView.action?meetingId="+id;
		load("#meetQueryListDiv",url);
	}
</script>
</@common.moduleDiv>