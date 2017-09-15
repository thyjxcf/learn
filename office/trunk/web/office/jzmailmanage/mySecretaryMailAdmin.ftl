<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script>
function doApply(readonlyStyle){
	//openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingEdit.action?readonlyStyle="+readonlyStyle, null, null, "500px");
	load("#jzMailDiv", "${request.contextPath}/office/jzmailmanage/jzmailmanage-myMailEdit.action");
}

function doQueryChange(){
	var title = $("#title").val();
	var beginTimes = $("#beginTimes").val();
	var endTimes = $("#endTimes").val();
	
	if(compareDate(beginTimes, endTimes) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
	load("#mySecretaryListDiv", "${request.contextPath}/office/jzmailmanage/jzmailmanage-myMailList.action?title="+encodeURIComponent(title)+"&beginTimes="+beginTimes+"&endTimes="+endTimes);
}

$(function(){
	doQueryChange();
});

</script>
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt ml-10"><span class="fn-left">日期：</span></div>
	    <@htmlmacro.datepicker name="beginTimes" id="beginTimes" style="width:120px;" value="${(beginTimes)?if_exists}"/>
	   	<div class="query-tt">&nbsp;-&nbsp;</div>
	    <@htmlmacro.datepicker name="endTimes" id="endTimes" style="width:120px;" value="${(endTimes)?if_exists}"/>
    	<div class="query-tt ml-10"><span class="fn-left">标题：</span></div>
        <input name="title" id="title" value="${title!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查询</a>
        <a href="javascript:void(0);" onclick="doApply('false');" class="abtn-orange-new fn-right applyForBtn" style="width:50px;">我要留言</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="mySecretaryListDiv"></div>