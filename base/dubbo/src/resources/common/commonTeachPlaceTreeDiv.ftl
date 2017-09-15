<#import "/common/componentInnerMacro.ftl" as componentInnerMacro>
<@componentInnerMacro.commonObjectTreeDiv leftUrl="${request.contextPath}/common/xtree/teachAreaTree.action" 
rightUrl="${request.contextPath}/common/getTeachPlaceDataFaintness.action?placeType=${placeType?default('')}" codeChinese="代码" nameChinese="名称" />
<script>
function treeItemClick(id,type,name){
	var action = "";
	var queryObjectTable = document.getElementById("queryObjectTable");
	if(type=="${stack.findValue("@net.zdsoft.eisu.base.tree.EisuTreeConstant@ITEMTYPE_TEACH_AREA")}"){
		searchObjectForm.groupId.value = id;
		action = "${request.contextPath}/common/getTeachPlaceData.action?placeType=${placeType?default('')}";
		queryObjectTable.style.display = "none";
		jQuery("#mainframe").height(jQuery("#_panel-pulic-window").height()+6);
	}else{
		action = "${request.contextPath}/common/getTeachPlaceDataFaintness.action?placeType=${placeType?default('')}";
		queryObjectTable.style.display = "";
		jQuery("#mainframe").height(jQuery("#_panel-pulic-window").height() - jQuery('.head-tt').height()+6);
	}	
	document.getElementById("letter").value = "";//清空
	searchObjectForm.action=action;
	searchObjectForm.submit();
}

</script>