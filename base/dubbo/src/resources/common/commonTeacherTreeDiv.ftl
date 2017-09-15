<#import "/common/componentInnerMacro.ftl" as componentInnerMacro>
<#if queryType! =="institute">
<@componentInnerMacro.commonObjectTreeDiv leftUrl="${request.contextPath}/common/xtree/instituteDeptTree.action" 
rightUrl="${request.contextPath}/common/getTeacherDataFaintness.action" codeChinese="编号" nameChinese="姓名" />
<#else>
<@componentInnerMacro.commonObjectTreeDiv leftUrl="${request.contextPath}/common/xtree/deptTree.action" 
rightUrl="${request.contextPath}/common/getTeacherDataFaintness.action" codeChinese="编号" nameChinese="姓名" />
</#if>
<script>
function treeItemClick(id,type,name){
	var action = "";
	var queryObjectTable = document.getElementById("queryObjectTable");
	if(type=="${stack.findValue("@net.zdsoft.eis.base.tree.TreeConstant@ITEMTYPE_DEPARTMENT")}"){
		searchObjectForm.groupId.value = id;
		action = "${request.contextPath}/common/getTeacherData.action";
		queryObjectTable.style.display = "none";
		jQuery("#mainframe").height(jQuery("#_panel-pulic-window").height()- jQuery('#maindiv').height()+6);
	}else if(type=="${stack.findValue("@net.zdsoft.eis.base.tree.TreeConstant@ITEMTYPE_INSTITUTE")}"){
		searchObjectForm.groupId.value = id;
		action = "${request.contextPath}/common/getTeacherData.action";
		queryObjectTable.style.display = "none";
		jQuery("#mainframe").height(jQuery("#_panel-pulic-window").height()- jQuery('#maindiv').height()+6);
	}else{
		action = "${request.contextPath}/common/getTeacherDataFaintness.action";
		queryObjectTable.style.display = "";
		jQuery("#mainframe").height(jQuery("#_panel-pulic-window").height() - jQuery('.head-tt').height()- jQuery('#maindiv').height()+6);
	}	
	document.getElementById("letter").value = "";//清空
	searchObjectForm.action=action;
	searchObjectForm.submit();
}

</script>