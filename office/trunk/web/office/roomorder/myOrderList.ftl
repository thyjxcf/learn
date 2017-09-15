<#import "/common/htmlcomponent.ftl" as common />
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >
<@common.moduleDiv titleName="">
<@common.tableList id="tablelist">
	<tr>
		<th width="5%">序号</th>
		<th width="14%">申请使用时间</th>
		<th width="28%">申请信息</th>
		<th width="24%">用途</th>
		<th width="8%">审核状态</th>
		<th width="16%">反馈信息</th>
		<th width="5%">操作</th>
	</tr>
	<#if officeApplyNumberList?exists && officeApplyNumberList?size gt 0>
		<#list officeApplyNumberList as x>
			<tr>
				<td>${x_index+1}</td>
				<td>${x.applyDate?string('yyyy-MM-dd')!}(${x.weekDay!})</td>
				<td title="${x.content!}"><@common.cutOff str='${x.content!}' length=35/></td>
				<td title="${x.purpose!}"><@common.cutOff str='${x.purpose!}' length=35/></td>
				<td>
					<#if x.state == NEEDAUDIT>
						待审核
	        		<#elseif x.state == PASS>
	                	已通过
	                <#elseif x.state == UNPASS>
	                	未通过
	                </#if>
				</td>
				<td style="word-break:break-all; word-wrap:break-word;" title="${x.feedback!}">
					<#if x.state == PASS>
	                	<@common.cutOff4List str="${x.feedback!}" length=28 />
	                </#if>
				</td>
				<td><a href="javascript:void(0);" onclick="viewInfo('${x.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" title="查看"></a></td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="7"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<@common.Toolbar container="#myOrderListDiv"/>
</@common.moduleDiv>
<script>
	function viewInfo(id){
	var url="${request.contextPath}/office/roomorder/roomorder-orderApplyView.action?applyNumberId="+id;
	load("#myOrderListDiv", url);
}
</script>