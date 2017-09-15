<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script>
function doApply(readonlyStyle){
	//openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingEdit.action?readonlyStyle="+readonlyStyle, null, null, "500px");
	load("#wirkReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportEdit.action?readonlyStyle="+readonlyStyle);
}

function doQueryChange(){
	var states =$("#states").val();
	var reportTypes=$("#reportTypes").val();
	var contents = $("#contents").val();
	var beginTimes = $("#beginTimes").val();
	var endTimes = $("#endTimes").val();
	
	if(compareDate(beginTimes, endTimes) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
	load("#wirkReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportList.action?contents="+encodeURIComponent(contents)+"&beginTimes="+beginTimes+"&endTimes="+endTimes+"&states="+states+"&reportTypes="+reportTypes);
}

$(function(){
	load("#wirkReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportList.action");
});



</script>
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt ml-10"><span class="fn-left">状态：</span></div>
    	<div class="ui-select-box fn-left" style="width:120px;">
            <input type="text" class="ui-select-txt" value=""  readonly/>
            <input name="states" id="states" type="hidden" value=""  class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option" myfunchange="doQueryChange">
        		<div class="a-wrap">
            	<a val=""><span>全部</span></a>
            	<a val="1" <#if states?default("") == "1">class="selected"</#if>><span>未提交</span></a>
                <a val="2" <#if states?default("") == "2">class="selected"</#if>><span>已提交</span></a>
                </div>
            </div>
	    </div>
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
	    <div class="query-tt ml-10"><span class="fn-left">开始日期：</span></div>
	    <@htmlmacro.datepicker name="beginTimes" id="beginTimes" style="width:120px;" value="${(beginTimes)?if_exists}"/>
	   	<div class="query-tt">&nbsp;-&nbsp;</div>
	    <@htmlmacro.datepicker name="endTimes" id="endTimes" style="width:120px;" value="${(endTimes)?if_exists}"/>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查找</a>
        <a href="javascript:void(0);" onclick="doApply('false');" class="abtn-orange-new fn-right applyForBtn" style="width:60px;">工作汇报</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="wirkReportDiv"></div>