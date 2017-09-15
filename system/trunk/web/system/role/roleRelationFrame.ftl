<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<SCRIPT src="${request.contextPath}/static/js/table-split-resize.js"></SCRIPT>
<@htmlmacro.moduleDiv titleName="角色管理">
<script>
$(function(){
	var id= "00000000000000000000000000000000";
	var unitId = "${unitId?default('00000000000000000000000000000000')}";
	load("#roleListDiv", "${request.contextPath}/system/role/roleAdmin-accredit.action?modID=${modID?default('')}&deptId=" + id+"&deptName="+name+"&unitId=${unitId?default('')}&operation=index&roleids=${roleids}");
});

function queryTeacherList(deptId){
	load("#roleListDiv", "${request.contextPath}/system/role/roleAdmin-accredit.action?modID=${modID?default('')}&deptId=" +deptId+"&deptName="+name+"&unitId=${unitId?default('')}&operation=index&roleids=${roleids}");
}
</script>
<div class="tree-menu-search">
	<@commonmacro.couplingSelect idObjectId="deptId" nameObjectId="deptId" url="/common/getDeptSelectDataByParentId.action" divName='请选择部门' onclick="queryTeacherList">
	</@commonmacro.couplingSelect>
</div>
<div id="roleListDiv"><p class="no-data mt-50 mb-50">还没有任何记录哦！请选择一个部门！</p></div>           
</@htmlmacro.moduleDiv>