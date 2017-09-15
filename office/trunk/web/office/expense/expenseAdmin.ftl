<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	getMyExpense();
});

function getMyExpense(){
	load("#adminDiv", "${request.contextPath}/office/expense/expense-myExpense.action");
}

function doExpenseAudit(){
	load("#adminDiv", "${request.contextPath}/office/expense/expense-expenseAudit.action");
}

function doExpenseQuery(){
	load("#adminDiv", "${request.contextPath}/office/expense/expense-expenseQueryAdmin.action");
}
</script>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<li class="current" onclick="getMyExpense();">我的报销</li>
		<li onclick="doExpenseAudit();">报销审核</li>
		<#if canQuery>
		<li onclick="doExpenseQuery();">报销查询</li>
		</#if>
	</ul>
</div>
<div id="adminDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>