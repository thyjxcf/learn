<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>


function doExpenseAuditEdit(id, taskId){
	load("#adminDiv", "${request.contextPath}/office/expense/expense-expenseAudit-edit.action?officeExpense.id="+id+"&taskId="+taskId+"&viewOnly=false");
}

function doExpenseAuditView(id){
	load("#adminDiv", "${request.contextPath}/office/expense/expense-myExpense-edit.action?officeExpense.id="+id+"&viewOnly=true&fromTab=2");
}
</script>
<div class="query-builder-no">
	<div class="query-part fn-rel fn-clear promt-div">
		<span id="flowTypeSpan" class="user-sList user-sList-radio">
	    	<span <#if searchType?default(0)==0> class="current"</#if> key="0">待我审核</span>
	    	<span <#if searchType?default(0)==3> class="current"</#if> key="3">我已审核</span>
	    	<span <#if searchType?default(0)==1> class="current"</#if> key="1">审核结束</span>
	    </span>
    </div>
</div>
<div id="expenseAuditListDiv"></div>
<script>
	$(document).ready(function(){
		vselect();
		$('.user-sList-radio span').click(function(){
			$(this).addClass('current').siblings('span').removeClass('current');			var searchType=$('#flowTypeSpan.user-sList-radio span.current').attr("key");			load("#expenseAuditListDiv", "${request.contextPath}/office/expense/expense-expenseAudit-list.action?searchType="+searchType);
		});
		load("#expenseAuditListDiv", "${request.contextPath}/office/expense/expense-expenseAudit-list.action");
	});

</script>
</@common.moduleDiv>