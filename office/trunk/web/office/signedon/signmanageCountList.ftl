<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<div class="pub-table-inner">

<div class="pub-table-wrap" id="myWorkReportDiv">
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="20%">编号</th>
		<th width="25%">姓名</th>
		<th width="25%">部门</th>
		<th width="30%">签到次数</th>
	</tr>
	<#if officeSignedOnList?exists && (officeSignedOnList?size>0)>
		<#list officeSignedOnList as officeSignedOn>
		    <tr>
                <td>${officeSignedOn_index+1}</td>
                <td>${officeSignedOn.userName!}</td>
				<td>${officeSignedOn.deptName!}</td>
				<td>${officeSignedOn.count!}</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="4"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<#if officeSignOn>
	<@htmlmacro.Toolbar container="#myWorkReportDiv"></@htmlmacro.Toolbar>
	</#if>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>