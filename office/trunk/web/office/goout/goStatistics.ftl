<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<#include "/common/handlefielderror.ftl">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10">
					<span class="fn-left">开始时间：</span>
				</div>
				<div class="fn-left">
    			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="startTime" value="${startTime?string('yyyy-MM-dd')!}"/>
				</div>
    			<div class="query-tt ml-10">
					<span class="fn-left">结束时间：</span>
				</div>
				<div class="fn-left">
    			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="endTime" value="${endTime?string('yyyy-MM-dd')!}"/>
				</div>
				<#if unitViewRole>
	    			<div class="query-tt ml-10">
						<span class="fn-left">部门：</span>
					</div>
					<div class="select_box fn-left" objectId="deptId">
					<@htmlmacro.select style="width:100px;" valName="deptId" txtId="deptIdTxt" valId="deptId" myfunchange="doSearch">
						<a val="">--请选择--</a>
						<#if directDepts?exists>
			                	<#list directDepts as pn> 
			                   		<a val="${pn.id}" <#if pn.id==deptId?default('')>class="selected"</#if>><span>${pn.deptname?default("")}</span></a>
				                </#list>
				        </#if>
					</@htmlmacro.select>
					</div>
				<#else>
					<input type="hidden" id="deptId" name="deptId" />
				</#if>
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue">查找</a>
			</div>
    	</div>
    </div>
</div>
<div id="statisticsListList"></div>
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
		var deptId=$("#deptId").val();
		var str="?startTime="+startTime+"&endTime="+endTime+"&deptId="+deptId;
		load("#statisticsListList","${request.contextPath}/office/goout/goout-statisticsList.action"+str);
	}
	$(document).ready(function(){
		doSearch();
	});
</script>
</@htmlmacro.moduleDiv>