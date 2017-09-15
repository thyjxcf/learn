<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function myOrder(){
	var url="${request.contextPath}/office/roomorder/roomorder.action";
	load("#container", url);
}

function orderApply(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderApply.action";
	load("#container", url);
}

function orderAudit(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderAudit.action";
	load("#container", url);
}

function timeSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-timeSet.action";
	load("#container", url);
}
function orderSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderSet.action";
	load("#container", url);
}

function labTypeSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-labTypeSet.action";
	load("#container", url);
}

function labApplyCount(){
	var url="${request.contextPath}/office/roomorder/roomorder-labApplyCount.action";
	load("#container", url);
}

function searchOrder(){
	var startTime = document.getElementById("startTime").value;
	var endTime = document.getElementById("endTime").value;
	if("" != startTime && ""!=	endTime){
		if(compareDate(document.getElementById("startTime"), document.getElementById("endTime")) > 0){
			showMsgWarn("结束时间不能小于开始时间！");
			return;
		}
	}
	var roomType = document.getElementById("roomType").value;
	var auditState = document.getElementById("auditState").value;
	if("${edu?string('true','false')}" == "false" && roomType == "11"){
		var searchSubject = document.getElementById("searchSubject").value;
	}else{
		var searchSubject = "";
	}
	var url="${request.contextPath}/office/roomorder/roomorder-orderAuditList.action";
	url += "?startTime="+startTime;
	url += "&endTime="+endTime;
	url += "&roomType="+roomType;
	url += "&auditState="+auditState;
	url += "&searchSubject="+searchSubject; 
	load("#orderAuditListDiv", url);
}

function changeRoomType(){
	var roomType = document.getElementById("roomType").value;
	if("${edu?string('true','false')}" == "false" && roomType == "11"){
		jQuery("#selectSubject").attr("style","display:display;");
	}else{
		jQuery("#selectSubject").attr("style","display:none;");
		searchOrder();
	}
}

function changeSmsSet(){
	var auditSms = jQuery("#needSms").attr("checked");
	var msg;
	var needSms;
	if(auditSms == null){
		msg = "您确定接收审核提醒短信？";
		needSms = "1"; 
	}else{
		msg = "您确定不接收审核提醒短信？";
		needSms = "0"; 
	}
	if(!showConfirm(msg)){
		var smsSpan = jQuery("#smsSpan");
		smsSpan.after(smsSpan.clone());
		smsSpan.remove();
		return;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/roomorder/roomorder-auditSms-save.action",
		data: $.param( {"roomorderAuditSms.needSms":needSms},true),
		success: function(data){
			if(data!=null&&data!=""){
					showMsgError(data);
					return;
				}else{
					showMsgSuccess("保存成功");
					return;
				}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

$(document).ready(function(){
	searchOrder();
});
</script>
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >

<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li onclick="myOrder();">我的预约</li>
	<li onclick="orderApply();">预约申请</li>
	<#if auditAdmin>
	<li class="current" onclick="orderAudit();">预约审核</li>
	<li onclick="timeSet();">时段设置</li>
	<li onclick="orderSet();">类型信息设置</li>
	<#if !edu>
	<li onclick="labTypeSet();">实验种类设置</li>
	</#if>
	</#if>
	<#if !edu>
	<li onclick="labApplyCount();">实验申请统计</li>
	</#if>
	</ul>
</div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10">
					<span class="fn-left">开始时间：</span>
				</div>
				<div class="fn-left">
    			<@common.datepicker class="input-txt" style="width:100px;" id="startTime" value=""/>
				</div>
    			<div class="query-tt ml-10">
					<span class="fn-left">结束时间：</span>
				</div>
				<div class="fn-left">
    			<@common.datepicker class="input-txt" style="width:100px;" id="endTime" value=""/>
				</div>
				<div class="query-tt ml-10">
					<span class="fn-left">类型：</span>
				</div>
				<div class="fn-left">
					<@common.select style="width:170px;" valName="roomType" valId="roomType" myfunchange="changeRoomType">
						<a val="">请选择</a>
						<#list officeRoomOrderSetList as item>
						<a val="${item.thisId}">${appsetting.getMcode('DM-CDLX').get(item.thisId?default(''))}</a>
						</#list>
					</@common.select>
				</div>
				<div id="selectSubject" style="display:none;">
					<div class="query-tt ml-10">
						<span class="fn-left">学科：</span>
					</div>
					<div class="fn-left">
					<@common.select style="width:210px;" valName="searchSubject" valId="searchSubject" notNull="true" msgName="学科" myfunchange="searchOrder">
						<a val=""><span>请选择</span></a>
			        	<#list appsetting.getMcode('DM-SYSLX').getMcodeDetailList() as item>
		            		<a val="${item.thisId}" <#if searchSubject?default('') == item.thisId>class="selected"</#if>><span>${item.content}</span></a>
		            	</#list>
					</@common.select>
					</div>
				</div>
				<div class="query-tt ml-10">
					<span class="fn-left">审核状态：</span>
				</div>
				<div class="fn-left">
					<@common.select style="width:110px;" valName="auditState" valId="auditState" myfunchange="searchOrder">
						<a val="">请选择</a>
						<a val="${NEEDAUDIT}">待审核</a>
						<a val="${PASS}">已通过</a>
						<a val="${UNPASS}">未通过</a>
					</@common.select>
				</div>
				&nbsp;<a href="javascript:void(0);" onclick="searchOrder();" class="abtn-blue">查找</a>
				
				<div class="fn-right mr-30">
				<span class="ui-checkbox <#if roomorderAuditSms.needSms! == "1">ui-checkbox-current</#if>" onclick="changeSmsSet();" id="smsSpan"><input id="needSms" type="checkbox" class="chk" value="1" <#if roomorderAuditSms.needSms! == "1">checked</#if>><p class="b">接收短信</p></span>
				</div>
			</div>
    	</div>
    </div>
</div>
<div id="orderAuditListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>