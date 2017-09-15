<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form name="deptLogForm" id="deptLogForm" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<th width="15%">姓名</th>
		<th width="20%">时间</th>
		<th width="10%">地址</th>
		<th width="40%">内容</th>
		<th width="5%">类型</th>
		<th class="t-center" width="10%">操作</th>
	</tr>
	<#if cals?exists && cals?size gt 0>
		<#list cals as item>
			<tr style="word-break:break-all; word-wrap:break-word;">
				<td>${item.creatorName!}</td>
				<td>${(item.calendarTime?string("yyyy/MM/dd HH:mm"))?if_exists} - ${(item.endTime?string("yyyy/MM/dd HH:mm"))?if_exists}</td>
				<td>${item.place!}</td>
				<td title="${item.content!}" style="line-height:19px;"><@common.cutOff str='${item.content!}' length=50/></td>
				<td><#if item.allDayEvent>全天
					<#elseif item.period == 1>上午
					<#elseif item.period == 2>中午
					<#elseif item.period == 3>下午
					<#elseif item.period == 4>晚上
					<#else></#if>
				</td>
				<td class="t-center">
					<a href="javascript:void(0);" onclick="viewSchedule('${item.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
				</td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="6"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if cals?exists && cals?size gt 0>
<@common.Toolbar container="#deptLogListDiv"/>
</#if>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>