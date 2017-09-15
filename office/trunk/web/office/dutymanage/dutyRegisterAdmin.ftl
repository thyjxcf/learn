<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>

function searchOrder(){
	var applyDate=$("#applyDate").val();
	var backUserId=$("#backUserId").val();
	var url="${request.contextPath}/office/dutymanage/dutymanage-dutyRegister.action";
	url += "?applyDate="+applyDate+"&backUserId="+backUserId;
	load("#mySalaryListDiv", url);
}

$(document).ready(function(){
	searchOrder();
});

function a1(){
	var applyDate=$("#applyDate").val();
	load("#importIdDiv","${request.contextPath}/office/dutymanage/dutymanage-dutyRegisterUser.action?applyDate="+applyDate,function(){
		searchOrder();
	});
}

</script>
<div class="popUp-layer" id="salaryAddLayer" style="display:none;width:700px;"></div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10">
					<span class="fn-left">值班日期：</span>
				</div>
				<div class="fn-left">
    			<@common.datepicker id="applyDate" dateFmt="yyyy-MM-dd" value="${applyDate?string('yyyy-MM-dd')}" onpicked="a1"/>
				</div>
				<div class="query-tt ml-10" style="margin-left:30px;">
					<span class="fn-left">值班人：</span>
				</div>
				<div class="fn-left" id="importIdDiv">
					<@common.select style="width:230px;" valName="backUserId" valId="backUserId" myfunchange="searchOrder">
						<a val="">请选择</a>
						<#list officeDutyApplies as item>
						<a val="${item.userId!}" <#if item.userId==loginUser.userId>class="selected"</#if>>${item.userName!}</a>
						</#list>
					</@common.select>
				</div>
			</div>
    	</div>
    </div>
</div>
<div id="mySalaryListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>