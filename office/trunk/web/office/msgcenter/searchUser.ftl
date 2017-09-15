<#list users as user>
	<a href="javascript:void(0);" onclick="addToSelected('${user.id!}','2','${user.realname!}(${user.unitName!}-${user.deptName!})');" title="${user.unitName!}-${user.deptName!}">${user.realname!}</a>
</#list>