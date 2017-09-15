<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/util.js"></script>
<#import "/common/htmlcomponent.ftl" as common />
<script>
function doEdit(id){
	var unitClass=parent.$F('unitclass');
	var subSystemId=parent.$F('subSystemId');
	window.location.href = "${request.contextPath}/basedata/sys/module-edit.action?pageIndex=${pageIndex}&unitClass="
		+unitClass+"&subSystemId="+subSystemId+"&id="+id;
}
jQuery(document).ready(function(){
	jQuery(".table-content").height(jQuery("#planframe", window.parent.document).height() - jQuery('.table1-bt').height() - 4)
})
</script>
</head>
<body>
<@common.tableList>
					<tr>
						<th width="10%">模块名称</th>
						<th width="10%">单位</th>
						<th width="20%">上级模块</th>
						<th width="20%">所属子系统</th>
						<th width="10%">自动收缩</th>
						<th width="15%">排序号</th>
						<th width="5%">启用</th>
					</tr>
					
					<#list moduleList as x>
						<tr onclick="doEdit('${x.id!}')" >	
							<td>
								${x.name?default("")}
							</td>
							<td>
								${appsetting.getMcode("DM-DWFL").get(x.unitclass?string?default("0"))}
							</td>
							<td>
								${x.parentModName?default("")}
							</td>
							<td>
								${x.subSysName?default("")}
							</td>
							<td>
								${appsetting.getMcode("DM-BOOLEAN").get(x.limit?default("0"))}
							</td>
							<td>
								${x.orderid?default("")}
							</td>
							<td>
								${appsetting.getMcode("DM-BOOLEAN").get(x.isActive?string?default("0"))}
							</td>
						</tr>
					</#list>		
	</@common.tableList>
<@common.Toolbar>
</@common.Toolbar>
</body>
</head>
</html>