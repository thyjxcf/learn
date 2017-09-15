<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form name="labApplyCountListForm" id="labApplyCountListForm" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<th width="10%">实验名称</th>
		<th width="10%">实验形式</th>
		<th width="10%">学科</th>
		<#if hasGrade>
		<th width="10%">年级</th>
		</#if>
		<th width="10%">申请人</th>
		<th width="15%">时间</th>
		<th width="20%">申请信息</th>
		<th width="15%">用途</th>
	</tr>
	<#if officeApplyNumberList?exists && officeApplyNumberList?size gt 0>
		<#list officeApplyNumberList as item>
			<tr style="word-break:break-all; word-wrap:break-word;">
				<td>${item.labName!}</td>
				<td>${item.labMode!}</td>
				<td>${item.labSubject!}</td>
				<#if hasGrade>
				<td>${item.labGrade!}</td>
				</#if>
				<td>${item.userName!}</td>
				<td>${item.applyDate?string('yyyy-MM-dd')!}(${item.weekDay!})</td>
				<td title="${item.content!}"><@common.cutOff str='${item.content!}' length=35/></td>
				<td title="${item.purpose!}"><@common.cutOff str='${item.purpose!}' length=35/></td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td <#if hasGrade>colspan="8"<#else>colspan="7"</#if>><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if officeApplyNumberList?exists && officeApplyNumberList?size gt 0>
<@common.Toolbar container="#labApplyCountListDiv" />
</#if>
</form>
<script>
vselect();
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>