<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form>
<@common.tableList id="tablelist">
	<tr>
		<th width="8%">驾驶员</th>
		<th width="8%">车牌号码</th>
		<th width="8%">乘车联系人</th>
		<th width="15%">用车时间</th>
		<th width="12%">目的地</th>
		<th width="10%">用车部门</th>
		<th width="8%">人数</th>
		<th width="15%">用车事由</th>
		<th width="8%"><nobr>加班时间（天）</nobr></th>
		<th width="8%">出车补贴</th>
	</tr>
	<#if officeCarApplies?exists && officeCarApplies?size gt 0>
		<#list officeCarApplies as x>
			<tr>
				<td style="word-break:break-all; word-wrap:break-word;">${x.driverName!}</td>
				<td style="word-break:break-all; word-wrap:break-word;">${x.carNumber!}</td>
				<td>${x.linkUserName!}</td>
				<td>${x.useTime?string('yyyy-MM-dd HH:mm')}  (${x.xinqi!})</td>
				<td><@common.cutOff4List str="${x.carLocation!}" length=8 /></td>
				<td>${x.deptName!}</td>
				<td>${x.personNumber!}</td>
				<td title="${x.reason!}"><@common.cutOff4List str="${x.reason!}" length=10 /></td>
				<td>
					<#if x.overtimeNumber?exists>
						${x.overtimeNumber?string("0.#")}
					</#if>
				</td>
				<td>${x.subsidy?string('0.##')}</td>
			</tr>	
		</#list>
	<#else>
		<tr>
	        <td colspan="10"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<@common.Toolbar container="#myCarUseDetailListDiv"/>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>