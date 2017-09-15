<#import "/common/componentInnerMacro.ftl" as componentInnerMacro>
<@componentInnerMacro.commonObjectTreeDiv leftUrl="${request.contextPath}/common/xtree/classTree.action?needAllPopedom=${needAllPopedom?string}&needDirectPopedom=${needDirectPopedom?string}" 
rightUrl="${request.contextPath}/common/eisu/showStudentDivByPopedom.action?needAllPopedom=${needAllPopedom?string}&needDirectPopedom=${needDirectPopedom?string}" codeChinese="学号" nameChinese="姓名" />

<script>
function treeItemClick(id,type,name){
	var action = "";
	var queryObjectTable = document.getElementById("queryObjectTable");
	if(type=="${stack.findValue("@net.zdsoft.eis.base.tree.TreeConstant@ITEMTYPE_CLASS")}"){
		searchObjectForm.groupId.value = id;
		action = "${request.contextPath}/common/getStudentData.action";
		queryObjectTable.style.display = "none";
	}else{
		action = "${request.contextPath}/common/eisu/showStudentDivByPopedom.action";
		queryObjectTable.style.display = "";
	}	
	
	action += "?needAllPopedom=${needAllPopedom?string}&needDirectPopedom=${needDirectPopedom?string}";
	document.getElementById("letter").value = "";//清空
	searchObjectForm.action=action;
	searchObjectForm.submit();
}

</script>