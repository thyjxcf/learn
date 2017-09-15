<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script>
$(function(){
	load("#wirkReportDiv", "${request.contextPath}/office/workreport/workReport-workReportSearchList.action");
});
function doQueryChange(){
	var reportTypes=$("#reportTypes").val();
	var contents = $("#contents").val();
	var createUserNames=$("#createUserNames").val();
	var beginTimes = $("#beginTimes").val();
	var endTimes = $("#endTimes").val();
	
	if(compareDate(beginTimes, endTimes) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
	load("#wirkReportDiv", "${request.contextPath}/office/workreport/workReport-workReportSearchList.action?contents="+encodeURIComponent(contents)+"&beginTimes="+beginTimes+"&endTimes="+endTimes+"&createUserNames="+encodeURIComponent(createUserNames)+"&reportTypes="+reportTypes);
}
</script>
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
<div class="query-part">
    	<div class="query-tt ml-10"><span class="fn-left">汇报类型：</span></div>
    	<div class="ui-select-box fn-left" style="width:120px;">
            <input type="text" class="ui-select-txt" value=""  readonly/>
            <input name="reportTypes" id="reportTypes" type="hidden" value=""  class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option" myfunchange="doQueryChange">
        		<div class="a-wrap">
            	<a val=""><span>全部</span></a>
            	<a val="1" <#if reportTypes?default("") == "1">class="selected"</#if>><span>周报</span></a>
                <a val="2" <#if reportTypes?default("") == "2">class="selected"</#if>><span>月报</span></a>
                </div>
            </div>
	    </div>
    	<div class="query-tt ml-10"><span class="fn-left">汇报内容：</span></div>
        <input name="contents" id="contents" value="${contents!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
    	<div class="query-tt ml-10"><span class="fn-left">汇报人：</span></div>
        <input name="createUserNames" id="createUserNames" value="${createUserNames!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
	    <div class="query-tt ml-10"><span class="fn-left">开始日期：</span></div>
	    <@htmlmacro.datepicker name="beginTimes" id="beginTimes" style="width:120px;" value="${(beginTimes)?if_exists}"/>
	   	<div class="query-tt">&nbsp;-&nbsp;</div>
	    <@htmlmacro.datepicker name="endTimes" id="endTimes" style="width:120px;" value="${(endTimes)?if_exists}"/>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查找</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="wirkReportDiv"></div>