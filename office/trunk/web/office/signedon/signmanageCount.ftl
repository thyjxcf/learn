<#import "/common/htmlcomponent.ftl" as common />
<script>

function doQueryChange(){
	var years=$("#years").val();
	var semesters = $("#semesters").val();
	var startTime = $("#startTime").val();
	var endTime=$("#endTime").val();
	if("" != startTime && ""!=endTime){
		if(compareDate(document.getElementById("startTime"), document.getElementById("endTime")) > 0){
			showMsgWarn("签到时间  前后时间不合逻辑，请更正！");
			return;
		}
	}
	var currentDeptId = $("#currentDeptId").val();
	var signed = $("#signed").val();
	load("#wirkReportDiv", "${request.contextPath}/office/signmanage/signmanage-signmanageCountList.action?currentDeptId="+currentDeptId+"&years="+years+"&semesters="+semesters+"&startTime="+startTime+"&signed="+signed+"&endTime="+endTime);
}

function doExport(){
	var years=$("#years").val();
	var semesters = $("#semesters").val();
	var startTime = $("#startTime").val();
	var endTime=$("#endTime").val();
	if("" != startTime && ""!=endTime){
		if(compareDate(document.getElementById("startTime"), document.getElementById("endTime")) > 0){
			showMsgWarn("签到时间  前后时间不合逻辑，请更正！");
			return;
		}
	}
	var currentDeptId = $("#currentDeptId").val();
	var signed = $("#signed").val();
	location.href="${request.contextPath}/office/signmanage/signmanage-doExport.action?currentDeptId="+currentDeptId+"&years="+years+"&semesters="+semesters+"&startTime="+startTime+"&signed="+signed+"&endTime="+endTime;
}

$(function(){
	var startTime = $("#startTime").val();
	var endTime=$("#endTime").val();
	load("#wirkReportDiv", "${request.contextPath}/office/signmanage/signmanage-signmanageCountList.action?years="+'${year!}'+"&semesters="+'${semester!}'+"&startTime="+startTime+"&endTime="+endTime);
});



</script>
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt b ml-10"><span class="fn-left">学年：</span></div>
			 <div class="select_box fn-left">
				<@common.select style="width:100px;float:left;" valName="years" valId="years" notNull="true" myfunchange="doQueryChange">
						<#if yearList?exists && yearList?size gt 0>
	                		<#list yearList as yearl>
	                			<a val="${yearl!}" <#if year?default('') == yearl>class="selected"</#if>>${yearl!}</a>
	                		</#list>
	                	</#if>
					</@common.select>
			</div>
				
		<div class="query-tt b ml-10"><span class="fn-left">学期：</span></div>
			<div class="select_box fn-left">
				<@common.select style="width:80px;" valName="semesters" valId="semesters" myfunchange="doQueryChange">
					<a val="1"  <#if semester?default("1")=="1">class="selected"</#if>><span>第一学期</span></a>
					<a val="2"  <#if semester?default("1")=="2">class="selected"</#if>><span>第二学期</span></a>
				</@common.select>
		</div>
		<#if officeSignOn>
    	<div class="query-tt ml-10"><span class="fn-left">部门：</span></div>
    	<@common.select style="width:120px;float:left;" valName="currentDeptId" valId="currentDeptId" myfunchange="doQueryChange">
					<a val="" >请选择</a>
					<#if deptList?exists && deptList?size gt 0>
                		<#list deptList as dept>
                			<a val="${dept.id!}" <#if deptId?default('') == dept.id>class="selected"</#if>>${dept.deptname!}</a>
                		</#list>
                	</#if>
		</@common.select>
	    </#if>
		<div class="query-tt ml-10">签到时间：</div>
			    <@common.datepicker name="startTime" id="startTime" style="width:120px;" value="${startTime!}"/>
			   	<div class="query-tt">&nbsp;-&nbsp;</div>
			    <@common.datepicker name="endTime" id="endTime" style="width:120px;" value="${endTime!}"/>
			    
		<a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查找</a>
	    <a href="javascript:void(0);" onclick="doExport();" class="abtn-blue fn-left ml-20">导出</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="wirkReportDiv"></div>