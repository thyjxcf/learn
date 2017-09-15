<#import "/common/componentInnerMacro.ftl" as componentInnerMacro>
<#if codeType=1>
	<#assign codeChinese="学籍号">
<#elseif codeType=2>
	<#assign codeChinese="学号">
</#if>
<@componentInnerMacro.commonObjectTreeDiv leftUrl="${request.contextPath}/common/xtree/classTree.action?eduadmShowPopedom=${eduadmShowPopedom?string}" 
rightUrl="${request.contextPath}/common/getStudentDataFaintness.action" codeChinese=codeChinese nameChinese="姓名" codeType=codeType/>

<script>
function treeItemClick(id,type,name){
	var action = "";
	var queryObjectTable = document.getElementById("queryObjectTable");
	if(type=="${stack.findValue("@net.zdsoft.eis.base.tree.TreeConstant@ITEMTYPE_CLASS")}"){
		searchObjectForm.groupId.value = id;
		action = "${request.contextPath}/common/getStudentData.action";
		queryObjectTable.style.display = "none";
	}else{
		action = "${request.contextPath}/common/getStudentDataFaintness.action";
		queryObjectTable.style.display = "";
	}	
	
	document.getElementById("letter").value = "";//清空
	searchObjectForm.action=action;
	searchObjectForm.submit();
}

</script>