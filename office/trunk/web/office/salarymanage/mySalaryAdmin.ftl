<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>

function searchOrder(){
	var importId=$("#importId").val();
	var cardnumber=trim($("#cardnumber").val());
	var url="${request.contextPath}/office/salarymanage/salarymanage-mySalaryList.action";
	url += "?importId="+importId+"&cardnumber="+cardnumber;
	load("#mySalaryListDiv", url);
}

$(document).ready(function(){
	<#if officeSalaryImport?default("")!="">
	$("#importId").val('${officeSalaryImport.id!}');
	</#if>
	searchOrder();
});

function a1(){
	var startTime=$("#startTime").val();
	load("#importIdDiv","${request.contextPath}/office/salarymanage/salarymanage-getSalaryTime.action?startTime="+startTime,function(){
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
					<span class="fn-left">年月：</span>
				</div>
				<div class="fn-left">
    			<@common.datepicker id="startTime" dateFmt="yyyy-MM" value="${startTime!}" onpicked="a1"/>
				</div>
				<div class="query-tt ml-10" style="margin-left:30px;">
					<span class="fn-left">工资发放项次：</span>
				</div>
				<div class="fn-left" id="importIdDiv">
					<@common.select style="width:230px;" valName="importId" valId="importId" myfunchange="searchOrder">
						<a val="">请选择</a>
						<#list officeSalaryImports as item>
						<a val="${item.id!}" <#if item.id==officeSalaryImport.id>class="selected"</#if>>${item.monthtime!}</a>
						</#list>
					</@common.select>
				</div>
			<#if salaryManage>
			<div class="query-tt ml-30 b">身份证件号：</div>
	        <input type="text" class="input-txt fn-left" style="width:120px;" id="cardnumber" name="cardnumber" value="${cardnumber!}"/>
	        </#if>
	    <a href="javascript:void(0);" onclick="searchOrder();" class="abtn-blue fn-left" style="margin-left:100px;">查找</a>
			</div>
    	</div>
    </div>
</div>
<div id="mySalaryListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>