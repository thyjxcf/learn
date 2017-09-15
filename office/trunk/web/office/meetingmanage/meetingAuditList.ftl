<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doAudit(applyId, readonlyStyle){
	//openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/meetingmanage/meetingmanage-auditEdit.action?officeMeetingApply.id="+applyId+"&readonlyStyle="+readonlyStyle, null, null, "500px");
	load("#contectDiv", "${request.contextPath}/office/meetingmanage/meetingmanage-auditEdit.action?officeMeetingApply.id="+applyId+"&readonlyStyle="+readonlyStyle);
}

function doQueryChange(){
	var queryState = $("#queryState").val();
	var queryBeginDate = $("#queryBeginDate").val();
	var queryEndDate = $("#queryEndDate").val();
	
	if(compareDate(queryBeginDate, queryEndDate) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
	load("#contectDiv", "${request.contextPath}/office/meetingmanage/meetingmanage-auditList.action?doAction=audit&queryState="+queryState+"&queryBeginDate="+queryBeginDate+"&queryEndDate="+queryEndDate);
}
</script>
<div class="pub-table-wrap" id="courseContainer">
<div class="pub-table-inner">
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt ml-10"><span class="fn-left">审核状态：</span></div>
    	<div class="ui-select-box fn-left" style="width:120px;">
            <input type="text" class="ui-select-txt" value=""  readonly/>
            <input name="queryState" id="queryState" type="hidden" value=""  class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option" myfunchange="doQueryChange">
        		<div class="a-wrap">
            	<a val=""><span>全部</span></a>
                <a val="1" <#if queryState?default("") == "1">class="selected"</#if>><span>待审核</span></a>
                <a val="2" <#if queryState?default("") == "2">class="selected"</#if>><span>审核通过</span></a>
                <a val="3" <#if queryState?default("") == "3">class="selected"</#if>><span>审核未通过</span></a>
                </div>
            </div>
	    </div>
	    <div class="query-tt ml-10"><span class="fn-left">日期：</span></div>
	    <@htmlmacro.datepicker name="queryBeginDate" id="queryBeginDate" style="width:150px;" value="${(queryBeginDate?string('yyyy-MM-dd'))?if_exists}"/>
	   	<div class="query-tt">&nbsp;-&nbsp;</div>
	    <@htmlmacro.datepicker name="queryEndDate" id="queryEndDate" style="width:150px;" value="${(queryEndDate?string('yyyy-MM-dd'))?if_exists}"/>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查找</a>
        <div class="fn-clear"></div>
    </div>
</div>
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="18%">会议主题</th>
		<th width="12%">会议室</th>
		<th width="26%">使用时间</th>
		<th width="10%">使用人</th>
		<th width="19%">审核状态</th>
		<th class="t-center" width="15%">操作</th>
	</tr>
	<#if officeMeetingApplyList?exists && (officeMeetingApplyList?size>0)>
		<#list officeMeetingApplyList as apply>
		    <tr>
                <td><@htmlmacro.cutOff4List str="${apply.meetingTheme?default('')}" length=30/></td>
				<td>${apply.meetingRoom?default("")}</td>
				<td>${apply.timeStr?default("")}</td>
				<td>${apply.applyUserName?default("")}</td>
                <td>
               		<#if apply.auditState?default("0") == "0">未提交
					<#elseif apply.auditState=="1">待审核
					<#elseif apply.auditState=="2">审核通过
					<#else>审核未通过（<@htmlmacro.cutOff4List str="${apply.opinion?default('')}" length=10 />）
					</#if>
                </td>
				<td class="t-center">
					<#if apply.auditState?exists && apply.auditState == "1">
						<a href="javascript:doAudit('${apply.id!}', 'false');"><img alt="审核" 
							src="${request.contextPath}/static/images/icon/check.png"></a>
					<#else>
						<a href="javascript:doAudit('${apply.id!}', 'true');"><img alt="查看" 
							src="${request.contextPath}/static/images/icon/view.png"></a>
					</#if>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="6"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#courseContainer"></@htmlmacro.Toolbar>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>