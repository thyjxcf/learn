<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort" style="table-layout:fixed">
	<tr>
		<th class="t-center" width="20%">会议名称</th>
		<th class="t-center" width="15%">会议时间</th>
	    	<th class="t-center" width="15%">会期</th>
		<th class="t-center" width="30%">主办科室</th>
		<#if show == '1'>
		<th class="t-center" width="20%">确认情况</th>
		<#else>
		<th class="t-center" width="20%">操作</th>
		</#if>
	</tr>
<#if meetinglist?exists &&  (meetinglist?size>0)>
	<#list meetinglist as x>
	<tr>
		<td style="word-break:break-all; word-wrap:break-word;"><a href="javascript:meetingView('${x.id!}','3');">${x.name!}</a></td>
		<td>${(x.meetingDate?string('yyyy-MM-dd HH:mm'))?if_exists}</td>
	  	<td style="word-break:break-all; word-wrap:break-word;">${((x.days)?string("#.#"))?if_exists}</td>
		<td style="word-break:break-all; word-wrap:break-word;">${x.hostDeptStr!}</td>
		<#if show=='1'>
	  	<td class="t-center">
	  		<#if x.attendState == 0>
	            <span class="state-wrap" style="z-index:999;">
	            	<a href="javascript:yesMeeting('${x.id!}');">确认参会</a>
		            &nbsp;<a href="javascript:void(0);" onclick="noMeeting(this);">无法参会</a>
		            &nbsp;<a href="javascript:transferMeeting('${x.id!}');" onclick="">转交他人</a>
                        <div class="state-layer">
                            <p><textarea id="${x.id!}"></textarea></p>
                            <p>
                                <a href="javascript:void(0);" onclick="noMeetingSub('${x.id!}')" class="abtn-submit">确定</a>
                                <a href="javascript:void(0);" class="abtn-reset" onclick="hid(this);">取消</a>
                            </p>
            		    </div>
                	</span>
	  		<#else>
	  			${x.transferDetail!}
	  		</#if>
	  	</td>
	  	<#else>
		  	<td class="t-center">
		  	<#if x.minutesId?exists && x.minutesId?size gt 0>
		  		<a href="javascript:void(0);" onclick="showMinutes('${x.minutesId!}')">查看纪要</a>
		  	</#if>
		  	</td>
	  	</#if>
	</tr>
	</#list>
<#else>
	<tr>
		<td colspan="5"><p class="no-data mt-20 mb-20">还没有任何记录哦！</p></td>
	</tr>
</#if>
</@common.tableList>
<@common.Toolbar container="#myMeetingListDiv"/>
<@commonmacro.selectOneUser idObjectId="transferId" nameObjectId="transferStr" callback="transferSet">
<input type="hidden" id="meetingId" name="meetingId" />
	<input type="hidden" id="transferId" name="transferId" value=""/> 
	<input type="hidden" id="transferStr" name="transferStr" value=""/>
	<a id="poptransfer"></a>
</@commonmacro.selectOneUser>
<script>
$(document).ready(function(){
	vselect();
});

function showMinutes(id){
	var meetingName = document.getElementById("meetingName").value;
	var startTime= document.getElementById("startTime").value;
	var endTime= document.getElementById("endTime").value;
   	var url="${request.contextPath}/office/meeting/workmeeting-minutesView.action?show=${show!}&meetingName="+meetingName+"&startTime="+startTime+"&endTime="+endTime+"&minutesId="+id;
	load("#workmeetingDiv", url);
}

function meetingView(id,backState){
	var meetingName = document.getElementById("meetingName").value;
	var startTime= document.getElementById("startTime").value;
	var endTime= document.getElementById("endTime").value;
   	var url="${request.contextPath}/office/meeting/workmeeting-meetingView.action?show=${show!}&meetingName="+meetingName+"&startTime="+startTime+"&endTime="+endTime+"&meetingId="+id+"&backState="+backState;
	load("#workmeetingDiv", url);
}
function transferMeeting(id){
		$("#meetingId").val(id);
		$("#transferId").val();
		$("#transferStr").val();
		$("#poptransfer").click();
	}
	function transferSet(){
		var meetingId=$("#meetingId").val();
		var transferId=$("#transferId").val();

		if(transferId == ''){
			return;
		}

		$.getJSON("${request.contextPath}/office/meeting/workmeeting-transferMeeting.action", 
		{"meetingId":meetingId,"transferId":transferId}, function(data){
			if(data && data != ""){
				showMsgError(data);
			}else{
				showMsgSuccess('转交成功!','提示');
				sear();
			}
		}).error(function(){
			showMsgError("转交失败！");
		});
	}
function yesMeeting(id){
	if(id && id != ''){
		$.getJSON("${request.contextPath}/office/meeting/workmeeting-yesMeeting.action", 
		{"meetingId":id}, function(data){
			if(data && data != ""){
				showMsgError(data);
			}else{
				showMsgSuccess('确认参会成功!','提示');
				sear();
			}
		}).error(function(){
			showMsgError("确认参会失败！");
		});
	}
}
function noMeeting(obj){
		$('.state-layer').hide();
		$(obj).siblings('.state-layer').show();
	}
function hid(obj){
		$(obj).parents('.state-layer').hide();
	}
	function noMeetingSub(id){
		var remark =  document.getElementById(id).value;

		if(remark == ''){
			showMsgError('原因不能为空');
			return false;
		}

		if(remark.length > 50){
			showMsgError('原因不能超过100个字符');
			return false;
		}

		//remark = encodeURIComponent(remark);

		$.getJSON("${request.contextPath}/office/meeting/workmeeting-noMeeting.action", 
		{"meetingId":id,"attendRemark":remark}, function(data){
			if(data && data != ""){
				showMsgError(data);
			}else{
				showMsgSuccess('确认不参会成功!','提示');
				sear();
			}
		}).error(function(){
			showMsgError("确认不参会失败！");
		});
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
