<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	salarySearch();
});

function salarySearch(){
	load("#salaryDiv", "${request.contextPath}/office/salarymanage/salarymanage-mySalaryAdmin.action");
}

function salaryManage(){
	load("#salaryDiv", "${request.contextPath}/office/salarymanage/salarymanage-salaryManageAdmin.action");
}

function salaryManageType(){
	load("#salaryDiv", "${request.contextPath}/office/salarymanage/salarymanage-salaryType.action");
}
</script>
<div class="pub-table-inner" id="teacherSalaryAdminDiv">
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<li class="current" onclick="salarySearch();">工资查询</li>
		<#if salaryManage>
		<li onclick="salaryManage();">工资维护</li>
		<li onclick="salaryManageType();">项次维护</li>
		</#if>
	</ul>
</div>
<div id="salaryDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>