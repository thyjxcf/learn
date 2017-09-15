<#import "/common/htmlcomponent.ftl" as common />
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort" style="table-layout:fixed">
	<tr>
		<th class="t-center" width="15%">姓名</th>
		<th class="t-center" width="20%">时间</th>
    	<th class="t-center" width="10%">地点</th>
    	<th class="t-center" width="40%">内容</th>
    	<th class="t-center" width="5%">类型</th>
		<th class="t-center" width="10%">操作</th>
	</tr>
<#if cals?exists &&  cals?size gt 0>
	<#list cals as item>
	<tr>
		<td>${item.creatorName!}</td>
		<td>${(item.calendarTime?string("yyyy-MM-dd HH:mm"))!}-${(item.endTime?string("yyyy-MM-dd HH:mm"))!}</td>
	  	<td style="word-break:break-all; word-wrap:break-word;">${item.place!}</td>
	  	<td title="${item.content!}" style="line-height:19px;"><@common.cutOff str='${item.content!}' length=50/></td>
		<td><#if item.allDayEvent>全天
			<#elseif item.period == 1>上午
			<#elseif item.period == 2>中午
			<#elseif item.period == 3>下午
			<#elseif item.period == 4>晚上
			<#else></#if>
		</td>
		<td class="t-center">
			<a href="javascript:void(0);" onclick="viewSchedule('${item.id!}');"><img alt="查看" src="${request.contextPath}/static/images/icon/view.png"></a>
	  	</td>
	</tr>
	</#list>
<#else>
	<tr>
		<td colspan="6"> <p class="no-data mt-20">还没有任何记录哦！</p></td>
	</tr>
</#if>
</@common.tableList>
<@common.Toolbar container="#deptLeaderScheduleList"/>
<script>
$(document).ready(function(){
	vselect();
});
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
