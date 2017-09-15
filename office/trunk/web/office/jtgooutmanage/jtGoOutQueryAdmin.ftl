<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script>

function doSearch(){
	var type =$("#type").val();
	var unitName = $("#unitName").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	
	if(compareDate(startTime, endTime) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
	load("#wirkReportDiv", "${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutQueryList.action?unitName="+encodeURIComponent(unitName)+"&startTime="+startTime+"&endTime="+endTime+"&type="+type);
}

$(function(){
	load("#wirkReportDiv", "${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutQueryList.action");
});

</script>
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt ml-10"><span class="fn-left">开始日期：</span></div>
	    <@htmlmacro.datepicker name="startTime" id="startTime" style="width:120px;" value="${(startTime)?if_exists}"/>
	   	<div class="query-tt">&nbsp;结束时间&nbsp;</div>
	    <@htmlmacro.datepicker name="endTime" id="endTime" style="width:120px;" value="${(endTime)?if_exists}"/>
    	<div class="query-tt ml-10"><span class="fn-left">类型：</span></div>
    	<div class="ui-select-box fn-left" style="width:120px;">
            <input type="text" class="ui-select-txt" value=""  readonly/>
            <input name="type" id="type" type="hidden" value=""  class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option" myfunchange="doSearch">
        		<div class="a-wrap">
            	<a val=""><span>全部</span></a>
            	<a val="1" <#if type?default("") == "1">class="selected"</#if>><span>学生集体活动</span></a>
                <a val="2" <#if type?default("") == "2">class="selected"</#if>><span>教师集体培训</span></a>
                </div>
            </div>
	    </div>
    	<div class="query-tt ml-10"><span class="fn-left">单位：</span></div>
        <input name="unitName" id="unitName" value="${unitName!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
	    <a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="wirkReportDiv"></div>