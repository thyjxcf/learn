<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var searchType = $("#searchType").val();
	load("#myExpenseListDiv", "${request.contextPath}/office/expense/expense-myExpense-list.action?searchType="+searchType);
}

function doExpenseAdd(){
	load("#adminDiv", "${request.contextPath}/office/expense/expense-myExpense-add.action?fromTab=1");
}

function doExpenseEdit(id){
	load("#adminDiv", "${request.contextPath}/office/expense/expense-myExpense-edit.action?officeExpense.id="+id+"&viewOnly=false&fromTab=1");
}

function doExpenseView(id){
	load("#adminDiv", "${request.contextPath}/office/expense/expense-myExpense-edit.action?officeExpense.id="+id+"&viewOnly=true&fromTab=1");
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10">
			<span class="fn-left">审核状态：</span>
		</div>
		<div class="select_box fn-left mr-10">
		<@common.select style="width:150px;" valId="searchType" valName="searchType" myfunchange="doSearch" >
			<a val=""><span>全部</span></a>
    		<a val="1"><span>待提交</span></a>
    		<a val="2"><span>审核中</span></a>
    		<a val="3"><span>审核通过</span></a>
    		<a val="4"><span>审核不通过</span></a>
		</@common.select></div>
		<a href="javascript:void(0);" onclick="doExpenseAdd();" class="abtn-orange-new fn-right mr-10">新增报销申请</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="myExpenseListDiv"></div>
<script>
	$(document).ready(function(){
		doSearch();
	});
</script>
</@common.moduleDiv>