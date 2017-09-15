<#import "/common/htmlcomponent.ftl" as common />
<form>
<@common.moduleDiv titleName="">
<@common.tableList id="tablelist">
	<tr>
		<#if querySumType == '1'>
			<th width="10%">车辆</th>
			<th width="10%">出车次数</th>
			<th width="10%">驾驶员</th>
			<th width="10%">乘车联系人</th>
			<th width="15%">用车时间</th>
			<th width="20%">目的地</th>
			<th width="15%">人数</th>
			<th width="20%">用车事由</th>
		<#else>
			<th width="8%">驾驶员</th>
			<th width="7%">出车次数</th>
			<th width="8%">车辆</th>
			<th width="8%">乘车联系人</th>
			<th width="15%">用车时间</th>
			<th width="9%">目的地</th>
			<th width="5%">人数</th>
			<th width="20%">用车事由</th>
			<th width="10%">出车补贴</th>
			<th width="10%">补贴汇总</th>
		</#if>
	</tr>
	
	
	<#if useCarInfolist?exists && useCarInfolist?size gt 0>
		<#list useCarInfolist as useCarInfo>
			<#assign officeCarApplyList = useCarInfo.officeCarApplyList/>
			<#assign i = 1/>
			<#assign totalSize = officeCarApplyList?size/>
			<tr>
				<#if querySumType == '1'>
					<td rowspan="${totalSize}" style="word-break:break-all; word-wrap:break-word;">${useCarInfo.carNumber!}</td>
				<#else>
					<td rowspan="${totalSize}" style="word-break:break-all; word-wrap:break-word;">${useCarInfo.driverName!}</td>
				</#if>
				<td rowspan="${totalSize}">${totalSize}</td>
				
				<#list officeCarApplyList as x>
					<#if querySumType == '1'>
						<td style="word-break:break-all; word-wrap:break-word;" title="${x.driverName!}"><@common.cutOff4List str="${x.driverName!}" length=25 /></td>
					<#else>
						<td style="word-break:break-all; word-wrap:break-word;" title="${x.carNumber!}"><@common.cutOff4List str="${x.carNumber!}" length=25 /></td>
					</#if>
					<td>${x.linkUserName!}</td>
					<td>${x.useTime?string('yyyy-MM-dd HH:mm')}  (${x.xinqi!})</td>
					<td title="${x.carLocation!}">
						<@common.cutOff4List str="${x.carLocation!}" length=25 />
					</td>
					<td>${x.personNumber!}</td>
					<td title="${x.reason!}">
						<@common.cutOff4List str="${x.reason!}" length=25 />
					</td>
					<#if querySumType == '2'>
					<td>${x.subsidy?string('0.##')}</td>
					</#if>
					<#if querySumType == '2' && i == 1>
					<td rowspan="${totalSize}">${useCarInfo.sumSubsidy?string('0.##')}</td>
					</#if>
					<#if i lt totalSize>
					</tr><tr>
					</#if>
					<#assign i = i+1/>
				</#list>
				
			</tr>
		</#list>
	<#else>
		<tr>
	        <td <#if querySumType == '1'>colspan="8"<#else>colspan="10"</#if>><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>