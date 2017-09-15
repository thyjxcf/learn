<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script>

function doQueryChange(){
	var title = $("#title").val();
	var beginTimes = $("#beginTimes").val();
	var endTimes = $("#endTimes").val();
	var createUserName = $("#createUserName").val();
	var unitName = $("#unitName").val();
	var state = $("#state").val();
	
	if(compareDate(beginTimes, endTimes) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
	load("#secretaryListDiv", "${request.contextPath}/office/jzmailmanage/jzmailmanage-mailList.action?title="+encodeURIComponent(title)+"&beginTimes="+beginTimes+"&endTimes="+endTimes
		+"&createUserName="+encodeURIComponent(createUserName)+"&unitName="+encodeURIComponent(unitName)+"&state="+encodeURIComponent(state));
}

$(function(){
	$("#state").val(1);
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
        <input name="title" id="title" value="${title!}" class="input-txt fn-left" style="width:120px;"/>
    	<div class="query-tt ml-10"><span class="fn-left">举报人：</span></div>
        <input name="createUserName" id="createUserName" value="${createUserName!}" class="input-txt fn-left" style="width:120px;"/>
    	<div class="query-tt ml-10"><span class="fn-left">单位：</span></div>
        <input name="unitName" id="unitName" value="${unitName!}" class="input-txt fn-left" style="width:120px;"/>
        <div class="query-tt ml-10"><span class="fn-left">状态：</span></div>
    	<div class="ui-select-box fn-left" style="width:80px;">
            <input type="text" class="ui-select-txt" value=""  readonly/>
            <input name="state" id="state" type="hidden" value=""  class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option" myfunchange="doQueryChange">
        		<div class="a-wrap">
            	<a val=""><span>全部</span></a>
            	<a val="1" <#if state?default("") == "1">class="selected"</#if>><span>待处理</span></a>
                <a val="2" <#if state?default("") == "2">class="selected"</#if>><span>已处理</span></a>
                </div>
            </div>
	    </div>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查询</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="secretaryListDiv"></div>