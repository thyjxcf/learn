<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>

function searchOrder(){
	var importId=$("#importId").val();
	var url="${request.contextPath}/office/salarymanage/salarymanage-salaryManageList.action";
	url += "?importId="+importId;
	load("#myOrderListDiv", url);
}

$(document).ready(function(){
	searchOrder();
});

function importTeacherSalary(){
	load("#teacherSalaryAdminDiv", "${request.contextPath}/office/salarymanage/salarymanage-import.action");
}

function doExport(){
	var importId=$("#importId").val();
	location.href="${request.contextPath}/office/salarymanage/salarymanage-doExport.action?importId="+importId;
}

function test(){
	var startTime=$("#startTime").val();
	load("#importIdDiv","${request.contextPath}/office/salarymanage/salarymanage-getSalaryTime.action?startTime="+startTime,function(){
		searchOrder();
	});
}
</script>
<div class="popUp-layer" id="salaryAddLayer" style="display:none;width:1000px;"></div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10">
					<span class="fn-left">年月：</span>
				</div>
				<div class="fn-left">
    			<@common.datepicker id="startTime" dateFmt="yyyy-MM" value="${startTime!}" onpicked="test"/>
				</div>
				<div class="query-tt ml-10">
					<span class="fn-left">工资发放项次：</span>
				</div>
				<div class="fn-left" id="importIdDiv">
					<@common.select style="width:230px;" valName="importId" valId="importId" myfunchange="searchOrder">
						<a val="">请选择</a>
						<#list officeSalaryImports as item>
						<a val="${item.id}">${item.monthtime!}</a>
						</#list>
					</@common.select>
				</div>
	    <a href="javascript:void(0);" onclick="doExport();" class="abtn-blue fn-right">导出</a>
	    <a href="javascript:void(0);" onclick="importTeacherSalary();" class="abtn-blue fn-right mr-30">导入</a>
			</div>
    	</div>
    </div>
</div>
<div id="myOrderListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>