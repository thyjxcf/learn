<#import "/common/htmlcomponent.ftl" as common />
<script>

function doQueryChange(){
	var years=$("#years").val();
	var semesters = $("#semesters").val();
	var startTime = $("#startTime").val();
	var currentDeptId = $("#currentDeptId").val();
	var signed = $("#signed").val();
	load("#wirkReportDiv", "${request.contextPath}/office/signmanage/signmanage-signmanageList.action?currentDeptId="+currentDeptId+"&years="+years+"&semesters="+semesters+"&startTime="+startTime+"&signed="+signed);
}

$(function(){
	var startTime = $("#startTime").val();
	load("#wirkReportDiv", "${request.contextPath}/office/signmanage/signmanage-signmanageList.action?years="+'${year!}'+"&semesters="+'${semester!}'+"&startTime="+startTime);
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
		<div class="query-tt b ml-10"><span class="fn-left">日期：</span></div>
			 <div class="fn-left">
			<@common.datepicker class="input-txt" style="width:100px;" id="startTime" 
		   	value="${startTime!}"/>
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
    	<div class="query-tt ml-10"><span class="fn-left">是否签到：</span></div>
    	<div class="ui-select-box fn-left" style="width:80px;">
            <input type="text" class="ui-select-txt" value=""  readonly/>
            <input name="signed" id="signed" type="hidden" value=""  class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option" myfunchange="doQueryChange">
        		<div class="a-wrap">
            	<a val=""><span>全部</span></a>
            	<a val="1" <#if signed?default("") == "1">class="selected"</#if>><span>已签到</span></a>
                <a val="0" <#if signed?default("") == "0">class="selected"</#if>><span>未签到</span></a>
                </div>
            </div>
	    </div>
	    </#if>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查找</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="wirkReportDiv"></div>