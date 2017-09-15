<#import "/common/htmlcomponent.ftl" as common />
<script>
$(function(){
	load("#wirkReportDiv", "${request.contextPath}/office/workreporttl/workReportTl-workReportSearchList.action?years="+${year!}+"&semesters="+${semester!}+"&weeks="+${searchWeek!});
});
function doQueryChange(){
	var years=$("#years").val();
	var semesters = $("#semesters").val();
	var weeks = $("#weeks").val();
	var contents = $("#contents").val();
	var createUserName=$("#createUserName").val();
	load("#wirkReportDiv", "${request.contextPath}/office/workreporttl/workReportTl-workReportSearchList.action?contents="+encodeURIComponent(contents)+"&years="+years+"&semesters="+semesters+"&createUserName="+encodeURIComponent(createUserName)+"&weeks="+weeks);
}

function doExport(){
	var years =$("#years").val();
	var semesters=$("#semesters").val();
	var weeks = $("#weeks").val();
	var contents = $("#contents").val();
	var createUserName = $("#createUserName").val();
	
	location.href="${request.contextPath}/office/workreporttl/workReportTl-workReportSearchExport.action?contents="+encodeURIComponent(contents)+"&years="+years+"&semesters="+semesters+"&weeks="+weeks+"&createUserName="+encodeURIComponent(createUserName);
}
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
		<div class="query-tt b ml-10"><span class="fn-left">周次：</span></div>
			 <div class="select_box fn-left">
				<@common.select style="width:67px;" valId="weeks" valName="weeks" txtId="searchWeekTxt" myfunchange="doQueryChange" >
						<a val="">请选择</a>
					<#list weekTimeList as item>
						<a val="${item}" <#if item == searchWeek?default('')>class="selected"</#if>>第${item!}周</a>
					</#list>
				</@common.select>
			</div>
    	<div class="query-tt ml-10"><span class="fn-left">汇报内容：</span></div>
        <input name="contents" id="contents" value="${contents!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
    	<div class="query-tt ml-10"><span class="fn-left">汇报人：</span></div>
        <input name="createUserName" id="createUserName" value="${createUserName!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查找</a>
	    <a href="javascript:void(0);" onclick="doExport();" class="abtn-blue fn-left" style="margin-left:50px;">导出</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="wirkReportDiv"></div>