<#import "/common/htmlcomponent.ftl" as common />
<script>
function doApply(readonlyStyle){
	//openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingEdit.action?readonlyStyle="+readonlyStyle, null, null, "500px");
	load("#wirkReportDiv", "${request.contextPath}/office/workreporttl/workReportTl-myWorkReportEdit.action?readonlyStyle="+readonlyStyle);
}

function doQueryChange(){
	var states =$("#states").val();
	var years=$("#years").val();
	var semesters = $("#semesters").val();
	var weeks = $("#weeks").val();
	var contents = $("#contents").val();
	
	load("#wirkReportDiv", "${request.contextPath}/office/workreporttl/workReportTl-myWorkReportList.action?contents="+encodeURIComponent(contents)+"&states="+states+"&years="+years+"&semesters="+semesters+"&weeks="+weeks);
}

$(function(){
	load("#wirkReportDiv", "${request.contextPath}/office/workreporttl/workReportTl-myWorkReportList.action?years="+${year!}+"&semesters="+${semester!});
});

function doExport(){
	var states =$("#states").val();
	var years=$("#years").val();
	var semesters = $("#semesters").val();
	var weeks = $("#weeks").val();
	var contents = $("#contents").val();
	
	//load("#wirkReportDiv", "${request.contextPath}/office/workreporttl/workReportTl-myWorkReportList.action?contents="+encodeURIComponent(contents)+"&states="+states+"&years="+years+"&semesters="+semesters+"&weeks="+weeks);
	location.href="${request.contextPath}/office/workreporttl/workReportTl-workReportExport.action?contents="+encodeURIComponent(contents)+"&states="+states+"&years="+years+"&semesters="+semesters+"&weeks="+weeks;
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
						<a val="${item}">第${item!}周</a>
					</#list>
				</@common.select>
			</div>
    	<div class="query-tt ml-10"><span class="fn-left">状态：</span></div>
    	<div class="ui-select-box fn-left" style="width:80px;">
            <input type="text" class="ui-select-txt" value=""  readonly/>
            <input name="states" id="states" type="hidden" value=""  class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option" myfunchange="doQueryChange">
        		<div class="a-wrap">
            	<a val=""><span>全部</span></a>
            	<a val="1" <#if states?default("") == "1">class="selected"</#if>><span>未提交</span></a>
                <a val="2" <#if states?default("") == "2">class="selected"</#if>><span>已提交</span></a>
                <a val="8" <#if states?default("") == "8">class="selected"</#if>><span>已撤回</span></a>
                </div>
            </div>
	    </div>
    	<div class="query-tt ml-10"><span class="fn-left">汇报内容：</span></div>
        <input name="contents" id="contents" value="${contents!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查找</a>
	    <a href="javascript:void(0);" onclick="doExport();" class="abtn-blue fn-left" style="margin-left:50px;">导出</a>
        <a href="javascript:void(0);" onclick="doApply('false');" class="abtn-orange-new fn-right applyForBtn" style="width:85px;">工作汇报</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="wirkReportDiv"></div>