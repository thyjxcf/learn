<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.tableList id="tablelist">
    <tr>
		<th width="18%">议题名称</th>
    	<th width="20%">提报领导</th>
    	<th width="20%">主办科室</th>
    	<th width="20%">列席科室</th>
    	<th width="10%">状态</th>
	    <th class="t-center" width="12%">操作</th>
    </tr>
   	<#if officeExecutiveIssueList?exists && officeExecutiveIssueList?size gt 0>
    
       <#list officeExecutiveIssueList as item>
        <tr>
        <td title="${item.name!}"><@htmlmacro.cutOff str="${item.name!}" length=15/></td>
        <td title="${item.leaderNameStr!}"><@htmlmacro.cutOff str="${item.leaderNameStr!}" length=30/></td>
        <td title="${item.hostDeptNameStr!}"><@htmlmacro.cutOff str="${item.hostDeptNameStr!}" length=30/></td>
    	<td title="${item.attendDeptNameStr!}"><@htmlmacro.cutOff str="${item.attendDeptNameStr!}" length=30/></td>
    	<td><#if item.state==1>未提报<#elseif item.state==2>待审核<#elseif item.state==3>已通过<#else>不通过</#if></td>
    	<td class="t-center">
    	<#if item.state==2>
			<a href="javascript:void(0)" onclick="doIssueAudit('${item.id!}', 'false')"><img src="${request.contextPath}/static/images/icon/check.png" title="审核"></a>
		<#else>
			<a href="javascript:void(0)" onclick="doIssueAudit('${item.id!}', 'true')"><img src="${request.contextPath}/static/images/icon/view.png" title="查看"></a>
		</#if>
    	</td>
		</tr>
       </#list>
    <#else>
    <tr>
    	<td colspan="6">
    	<p class="no-data mt-20 mb-20">还没有任何记录哦！</p></td>
    </tr>
	</#if>
</@htmlmacro.tableList>
<#if officeExecutiveIssueList?exists && officeExecutiveIssueList?size gt 0>
	<@htmlmacro.Toolbar container="#issueAuditListDiv">
	</@htmlmacro.Toolbar>
</#if>
<script src="${request.contextPath}/static/js/myscript.js"></script>
