<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.tableList id="tablelist">
    <tr>
		<th width="15%">议题名称</th>
    	<th width="15%">提报领导</th>
    	<th width="15%">主办科室</th>
    	<th width="15%">列席科室</th>
    	<th width="15%">意见征集科室</th>
    	<th width="10%">状态</th>
	    <th class="t-center" width="15%">操作</th>
    </tr>
   	<#if officeExecutiveIssueList?exists && officeExecutiveIssueList?size gt 0>
       <#list officeExecutiveIssueList as item>
        <tr>
        <td title="${item.name!}"><@htmlmacro.cutOff str="${item.name!}" length=15/></td>
        <td title="${item.leaderNameStr!}"><@htmlmacro.cutOff str="${item.leaderNameStr!}" length=25/></td>
        <td title="${item.hostDeptNameStr!}"><@htmlmacro.cutOff str="${(item.hostDeptNameStr!)?replace('${myDeptName!}','${myRedDeptName!}')}" length=40/></td>
    	<td title="${item.attendDeptNameStr!}"><@htmlmacro.cutOff str="${(item.attendDeptNameStr!)?replace('${myDeptName!}','${myRedDeptName!}')}" length=40/></td>
    	<td title="${item.opinionDeptNameStr!}"><@htmlmacro.cutOff str="${(item.opinionDeptNameStr!)?replace('${myDeptName!}','${myRedDeptName!}')}" length=40/></td>
    	<td><#if item.state==1>未提报<#elseif item.state==2>待审核<#elseif item.state==3>已通过<#else>不通过</#if></td>
    	<td class="t-center"><nobr>
    	<#if item.state==1>
			<a href="javascript:void(0);" onclick="doSubmit('${item.id}');">提报</a>
			<a href="javascript:void(0);" onclick="doIssueEdit('${item.id}', 'false');" class="ml-10">修改议题</a>
    		<a href="javascript:void(0);" onclick="doDelete('${item.id}');" class="ml-10">删除</a>
    	<#else>
			<a href="javascript:void(0);" onclick="doIssueEdit('${item.id}', 'true');">查看议题</a>
	    	<#if item.canManageOpinion>
		    	<#if item.createUser>
		    		<a href="javascript:void(0);" onclick="doOpinionReply('${item.id}', 'false');" class="ml-10">查看并回复意见</a>
				<#else>
					<a href="javascript:void(0);" onclick="doOpinionReply('${item.id}', 'true');" class="ml-10">查看意见</a>
		    	</#if>
		    	<#if item.reviseOpinionType == 1 && !item.createUser>
	    			<a href="javascript:void(0);" onclick="doOpinionEdit('${item.id}', '1');" class="ml-10">填写意见</a>
	    		<#elseif item.reviseOpinionType == 2  && !item.createUser>
	    			<a href="javascript:void(0);" onclick="doOpinionEdit('${item.id}', '2');" class="ml-10">修改意见</a>
		    	</#if>
		    <#else>
		    	<a href="javascript:void(0);" onclick="doOpinionReply('${item.id}', 'true');" class="ml-10">查看意见</a>
		    </#if>
    	</#if>
    	</nobr></td>
		</tr>
       </#list>
    <#else>
    <tr>
    	<td colspan="7">
    	<p class="no-data mt-20 mb-20">还没有任何记录哦！</p></td>
    </tr>
	</#if>
</@htmlmacro.tableList>
<#if officeExecutiveIssueList?exists && officeExecutiveIssueList?size gt 0>
	<@htmlmacro.Toolbar container="#issueApplyListDiv">
	</@htmlmacro.Toolbar>
</#if>
<script src="${request.contextPath}/static/js/myscript.js"></script>
