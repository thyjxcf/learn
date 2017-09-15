<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<#assign objectName = stack.findValue("@net.zdsoft.eis.base.data.action.UnitImportAction@UNIT_IMPORT") >
<@htmlmacro.moduleDiv titleName="教师列表">
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>

<div id="teacherDiv">    
<form name="form1" id="form1" action="" method="post">  
<@htmlmacro.tableList id="tablelist" class="public-table table-list mt-15">
		<tr>
			<th width="25%">姓名</th>
			<th width="25%">性别</th>
			<th width="25%">手机号</th>
			<th width="25%">账号</th>
			
		</tr>
		<#if teacherList?exists&&(teacherList?size>0)>	
			<#list teacherList as x>
				<tr>
					<td>${x.name!}</td>
					<td><#if x.sex?default('')=="1">男
						<#elseif x.sex?default('')=="2">女
					</#if></td>
					<td>${x.personTel!}</td>
					<td>${x.loginName!}</td>
				</tr>
			</#list>	
		<#else>
		  	<tr><td colspan="4"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
		</#if>		
</@htmlmacro.tableList>	
 <@htmlmacro.Toolbar container="#teacherDiv">
	<p class="opt">
    </p>
</@htmlmacro.Toolbar>	
</form>
<script type="text/javascript">

</script>
</@htmlmacro.moduleDiv>