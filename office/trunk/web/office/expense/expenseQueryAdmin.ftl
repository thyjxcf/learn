<#import "/common/htmlcomponent.ftl" as htmlmacro />
<@htmlmacro.moduleDiv titleName="">
<script>
function doSearch(){
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgError("结束时间不能早于开始时间，请重新选择！");
			return;
		}
	}
	var searchType=$("#searchType").val();
	var userName=$("#userName").val();
	var str="?startTime="+startTime+"&endTime="+endTime+"&searchType="+searchType+"&userName="+encodeURIComponent(userName);
	load("#myExpenseListDiv", "${request.contextPath}/office/expense/expense-expenseQueryList.action"+str);
}

function doExport(){
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgError("结束时间不能早于开始时间，请重新选择！");
			return;
		}
	}
	var searchType=$("#searchType").val();
	var userName=$("#userName").val();
	var str="?startTime="+startTime+"&endTime="+endTime+"&searchType="+searchType+"&userName="+encodeURIComponent(userName);
	location.href="${request.contextPath}/office/expense/expense-expenseExport.action"+str;
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div>
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10">
			<span class="fn-left">审核状态：</span>
		</div>
		<div class="select_box fn-left mr-10">
		<@htmlmacro.select style="width:150px;" valId="searchType" valName="searchType" myfunchange="doSearch" >
			<a val="" <#if searchType?default("") == "">class="selected"</#if>><span>全部</span></a>
    		<a val="2" <#if searchType?default("") == "2">class="selected"</#if>><span>审核中</span></a>
    		<a val="3" <#if searchType?default("") == "3">class="selected"</#if>><span>审核通过</span></a>
    		<a val="4" <#if searchType?default("") == "4">class="selected"</#if>><span>审核不通过</span></a>
		</@htmlmacro.select></div>
    			<div class="query-tt ml-10">
					<span class="fn-left">开始时间：</span>
				</div>
				<div class="fn-left">
    			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="startTime" value="${((startTime)?string('yyyy-MM-dd'))?if_exists}"/>
				</div>
    			<div class="query-tt ml-10">
					<span class="fn-left">结束时间：</span>
				</div>
				<div class="fn-left">
    			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="endTime" value="${((endTime)?string('yyyy-MM-dd'))?if_exists}"/>
				</div>
	    			<div class="query-tt ml-10">
						<span class="fn-left">申请人：</span>
					</div>
					<div class="fn-left">
						<input type="input" class="input-txt" style="width:100px;" id="userName" value="${userName!}">
					</div>
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue">查找</a>
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doExport();" class="abtn-blue">导出</a>
			</div>
    	</div>
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
</@htmlmacro.moduleDiv>