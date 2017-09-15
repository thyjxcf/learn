<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doApply(readonlyStyle){
	//openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingEdit.action?readonlyStyle="+readonlyStyle, null, null, "500px");
	load("#contectDiv", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingEdit.action?readonlyStyle="+readonlyStyle);
}

function doQueryChange(){
	var queryState = $("#queryState").val();
	var queryName = $("#queryName").val();
	var queryBeginDate = $("#queryBeginDate").val();
	var queryEndDate = $("#queryEndDate").val();
	
	if(compareDate(queryBeginDate, queryEndDate) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
	load("#contectDiv", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingList.action?doAction=apply&queryState="+queryState+"&queryName="+queryName+"&queryBeginDate="+queryBeginDate+"&queryEndDate="+queryEndDate);
}

function doEdit(applyId, readonlyStyle){
	//openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingEdit.action?officeMeetingApply.id="+applyId+"&readonlyStyle="+readonlyStyle, null, null, "500px");
	load("#contectDiv", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingEdit.action?officeMeetingApply.id="+applyId+"&readonlyStyle="+readonlyStyle);
}

//提交
function doSubmit(applyId){
	if(!showConfirm("确认要提交吗?")){
		return;
	}
	$.getJSON("${request.contextPath}/office/meetingmanage/meetingmanage-remote-submit.action", 
		{"officeMeetingApply.id":applyId}, function(data){
		if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   return;
		   }else{
		   	   showMsgError(data.promptMessage);
			   return;
		   }	
		}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			load("#contectDiv", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingList.action?doAction=apply");
			});
			return;
		}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
}

//删除
function doDelete(applyId){
	if(!showConfirm("确认要删除吗?")){
		return;
	}
	$.getJSON("${request.contextPath}/office/meetingmanage/meetingmanage-remote-delete.action", 
		{"officeMeetingApply.id":applyId}, function(data){
		if(!data.operateSuccess){
		   	if(data.errorMessage!=null&&data.errorMessage!=""){
			   	showMsgError(data.errorMessage);
			   	return;
		   	}
		}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			load("#contectDiv", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingList.action?doAction=apply");
			});
			return;
		}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
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
            	<a val="0" <#if queryState?default("") == "0">class="selected"</#if>><span>未提交</span></a>
                <a val="1" <#if queryState?default("") == "1">class="selected"</#if>><span>审核中</span></a>
                <a val="2" <#if queryState?default("") == "2">class="selected"</#if>><span>审核通过</span></a>
                <a val="3" <#if queryState?default("") == "3">class="selected"</#if>><span>审核未通过</span></a>
                </div>
            </div>
	    </div>
    	<div class="query-tt ml-10"><span class="fn-left">会议主题：</span></div>
        <input name="queryName" id="queryName" value="${queryName!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
	    <div class="query-tt ml-10"><span class="fn-left">日期：</span></div>
	    <@htmlmacro.datepicker name="queryBeginDate" id="queryBeginDate" style="width:120px;" value="${(queryBeginDate?string('yyyy-MM-dd'))?if_exists}"/>
	   	<div class="query-tt">&nbsp;-&nbsp;</div>
	    <@htmlmacro.datepicker name="queryEndDate" id="queryEndDate" style="width:120px;" value="${(queryEndDate?string('yyyy-MM-dd'))?if_exists}"/>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查找</a>
        <a href="javascript:void(0);" onclick="doApply('false');" class="abtn-orange-new fn-right applyForBtn" >申请会议</a>
        <div class="fn-clear"></div>
    </div>
</div>
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="18%">会议主题</th>
		<th width="12%">会议室</th>
		<th width="27%">使用时间</th>
		<th width="10%">申请人</th>
		<th width="17%">审核状态</th>
		<th class="t-center" width="16%">操作</th>
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
					<#elseif apply.auditState=="1">审核中
					<#elseif apply.auditState=="2">审核通过
					<#else>审核未通过（<@htmlmacro.cutOff4List str="${apply.opinion?default('')}" length=10 />）
					</#if>
                </td>
				<td class="t-center">
				
					<#if apply.auditState?exists && apply.auditState == "0">
						<a href="javascript:doSubmit('${apply.id!}');">提交</a> &nbsp;
						<a href="javascript:doEdit('${apply.id!}', 'false');">修改</a> &nbsp;
						<a href="javascript:doDelete('${apply.id!}');">删除</a> &nbsp;
					
					<#elseif apply.auditState?exists && apply.auditState == "3">
						<a href="javascript:doSubmit('${apply.id!}');">重新提交</a> &nbsp;
						<a href="javascript:doEdit('${apply.id!}', 'false');">修改</a> &nbsp;
						<a href="javascript:doDelete('${apply.id!}');">删除</a> &nbsp;
					<#else>
						<a href="javascript:doEdit('${apply.id!}', 'true');">查看</a>
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