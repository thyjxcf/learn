<#import "/common/htmlcomponent.ftl" as common />
<form>
<@common.moduleDiv titleName="">
<@common.tableList id="tablelist">
	<tr>
		<th width="10%">驾驶员</th>
		<th width="10%">车辆</th>
		<th width="15%">用车时间</th>
		<th width="20%">目的地</th>
		<th width="10%">用车部门</th>
		<th width="10%">乘车联系人</th>
		<th width="5%">人数</th>
		<th width="20%">用车事由</th>
	</tr>
	<#if officeCarApplies?exists && officeCarApplies?size gt 0>
		<#list officeCarApplies as x>
			<tr>
				<td style="word-break:break-all; word-wrap:break-word;" title="${x.driverName!}"><@common.cutOff4List str="${x.driverName!}" length=25 /></td>
				<td style="word-break:break-all; word-wrap:break-word;" title="${x.carNumber!}"><@common.cutOff4List str="${x.carNumber!}" length=25 /></td>
				<td>${x.useTime?string('yyyy-MM-dd HH:mm')}  (${x.xinqi!})</td>
				<td title="${x.carLocation!}">
					<@common.cutOff4List str="${x.carLocation!}" length=25 />
				</td>
				<td>${x.deptName!}</td>
				<td>${x.linkUserName!}</td>
				<td>${x.personNumber!}</td>
				<td title="${x.reason!}">
					<@common.cutOff4List str="${x.reason!}" length=25 />
				</td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="8"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<@common.Toolbar container="#carUseSummaryListDiv"/>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>