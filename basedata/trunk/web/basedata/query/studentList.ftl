<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<#assign objectName = stack.findValue("@net.zdsoft.eis.base.data.action.UnitImportAction@UNIT_IMPORT") >

<@htmlmacro.moduleDiv titleName="学生教师列表">
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>

<div id="container1">    
    <form name="form1" id="form1" action="" method="post">

    
	<@htmlmacro.tableList id="tablelist" class="public-table table-list mt-15">
	<tr>
	  
		<th width="25%">班级</th>
		<th width="25%">姓名</th>
		<th width="25%">性别</th>
		<th width="25%">账号</th>
		
	</tr>
	<#if userList?exists&&(userList?size>0)>
	<#list userList as x>
		<tr>
		<td>${x.className!}</td>
		<td>${x.realname!}</td>
		<td>${x.sex!}</td>
		<td>${x.name!}</td>
		</tr>
	</#list>	
	<#else>
	  <tr><td colspan="4"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>
	
	<@htmlmacro.Toolbar container="#container1">

	</@htmlmacro.Toolbar>
  </form>
</div>
<div class="popUp-layer" id="addDiv" style="display:none;width:600px;"></div>
<script type="text/javascript">

</script>
</@htmlmacro.moduleDiv>