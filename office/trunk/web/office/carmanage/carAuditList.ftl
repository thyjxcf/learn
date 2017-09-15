<#import "/common/htmlcomponent.ftl" as common />
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >
<#assign CANCELNEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_CANCEL_NEED_AUDIT") >
<#assign CANCELPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_CANCEL_PASS") >
<form>
<@common.moduleDiv titleName="">
<@common.tableList id="tablelist">
	<tr>
		<th width="5%">序号</th>
		<th width="10%">申请人</th>
		<th width="8%">乘车人数</th>
		<th width="15%">用车时间</th>
		<th width="25%">用车事由</th>
		<th width="10%">单位审核</th>
		<th width="10%">车牌号码</th>
		<th width="10%">驾驶员</th>
		<th width="7%" style="text-align:center;">操作</th>
	</tr>
	<#if officeCarApplies?exists && officeCarApplies?size gt 0>
		<#list officeCarApplies as x>
			<tr>
				<td >${x_index + 1}</td>
				<td >${x.applyUserName!}</td>
				<td >${x.personNumber!}</td>
				<td >${x.useTime?string('yyyy-MM-dd HH:mm')}   (${x.xinqi!})</td>
				<td title="${x.reason!}">
					<@common.cutOff4List str="${x.reason!}" length=25 />
				</td>
				<td>
					<#if x.state == NEEDAUDIT>
						待审核
					<#elseif x.state == PASS>
						已通过(${x.auditUserName!})
					<#elseif x.state == UNPASS>
						未通过(${x.auditUserName!})
					<#elseif x.state == CANCELNEEDAUDIT>
						撤销待审核
					<#else>
						撤销通过(${x.auditUserName!})
					</#if>
				</td>
				<td style="word-break:break-all; word-wrap:break-word;" title="${x.carNumber!}"><@common.cutOff4List str="${x.carNumber!}" length=25 /></td>
				<td style="word-break:break-all; word-wrap:break-word;" title="${x.driverName!}"><@common.cutOff4List str="${x.driverName!}" length=25 /></td>
				<td style="text-align:center;">
					<#if x.state == NEEDAUDIT>
						<a href="javascript:void(0)" onclick="auditCarApply('${x.id!}')"><img src="${request.contextPath}/static/images/icon/check.png" title="审核"></a>
					<#elseif x.state == CANCELNEEDAUDIT>
						<a href="javascript:void(0)" onclick="auditCarCancelApply('${x.id!}')"><img src="${request.contextPath}/static/images/icon/check.png" title="审核"></a>
					<#elseif x.state == CANCELPASS>
						<a href="javascript:void(0)" onclick="viewCancelCarApply('${x.id!}')"><img src="${request.contextPath}/static/images/icon/view.png" title="查看"></a>
					<#else>
						<a href="javascript:void(0)" onclick="viewCarApply('${x.id!}')"><img src="${request.contextPath}/static/images/icon/view.png" title="查看"></a>
					</#if>
				</td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="9"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<@common.Toolbar container="#carAuditListDiv"/>
</form>
<script>

function viewCarApply(id){
	var url="${request.contextPath}/office/carmanage/carmanage-carApplyView.action?applyId="+id+"&applyType=2";
	load("#carAuditListDiv", url);
}

function viewCancelCarApply(id){
	var url="${request.contextPath}/office/carmanage/carmanage-carCancelAuditEdit.action?applyId="+id+"&applyType=1";
	load("#carAuditListDiv", url);
}

function auditCarApply(id){
	var url="${request.contextPath}/office/carmanage/carmanage-carAuditEdit.action?applyId="+id;
	load("#carAuditListDiv", url);
}

function auditCarCancelApply(id){
	var url="${request.contextPath}/office/carmanage/carmanage-carCancelAuditEdit.action?applyId="+id+"&applyType=2";
	load("#carAuditListDiv", url);
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>