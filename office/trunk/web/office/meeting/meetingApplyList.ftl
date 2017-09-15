<#import "/common/htmlcomponent.ftl" as common />
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort" style="table-layout:fixed">
	<tr>
		<th class="t-center" width="18%">会议名称</th>
		<th class="t-center" width="15%">会议时间</th>
	    	<th class="t-center" width="13%">会期</th>
		<th class="t-center" width="30%">主办科室</th>
		<th class="t-center" width="12%">状态</th>
		<th class="t-center" width="12%">操作</th>
	</tr>
<#if meetinglist?exists &&  (meetinglist?size>0)>
	<#list meetinglist as x>
	<tr>
		<td style="word-break:break-all; word-wrap:break-word;"><a href="javascript:meetingView('${x.id!}','1');">${x.name!}</a></td>
		<td>${(x.meetingDate?string('yyyy-MM-dd HH:mm'))?if_exists}</td>
	  	<td style="word-break:break-all; word-wrap:break-word;">${((x.days)?string("#.#"))?if_exists}</td>
		<td style="word-break:break-all; word-wrap:break-word;">${x.hostDeptStr!}</td>
	  	<td>
	  		<#if x.state == 1>
				未提交
	  		<#elseif x.state == 2>
	  			待审核
	  		<#elseif x.state == 3>
	  			已通过
	  		<#elseif x.state == 4>
				未通过
	  		</#if>
	  	</td>
	  	<td class="t-center">
	  		<#if x.state == 1>
	  		 <a href="javascript:meetingEdit('${x.id!}');">修改</a>
		       <a href="javascript:submitMeeting('${x.id!}');">提交</a>
		       <a href="javascript:deletedMeeting('${x.id!}');">删除</a>
	  		<#elseif x.state == 2>
	  		&nbsp;
	  		<#elseif x.state == 3>
	  		<#if x.isPublish>
				已发布
	            	&nbsp;<a href="javascript:showAttendInfo('${x.id!}');">到会人员</a>
            	<#else>
            		<a href="javascript:publishMeeting('${x.id!}');">发布</a>
            	</#if>
	  		<#elseif x.state == 4>
			<a href="javascript:meetingEdit('${x.id!}');">修改</a>
	            <a href="javascript:submitMeeting('${x.id!}');">重新提交</a>
	            <a href="javascript:deletedMeeting('${x.id!}');">删除</a>
	  		</#if>
	  	</td>
	</tr>
	</#list>
<#else>
	<tr>
		<td colspan="6"> <p class="no-data mt-20">还没有任何记录哦！</p></td>
	</tr>
</#if>
</@common.tableList>
<@common.Toolbar container="#meetingApplyListDiv"/>
<script>
$(document).ready(function(){
	vselect();
});
function meetingView(id,backState){
	var state = document.getElementById("state").value;
	var meetingName = document.getElementById("meetingName").value;
	var startTime= document.getElementById("startTime").value;
	var endTime= document.getElementById("endTime").value;
   	var url="${request.contextPath}/office/meeting/workmeeting-meetingView.action?state="+state+"&meetingName="+meetingName+"&startTime="+startTime+"&endTime="+endTime+"&meetingId="+id+"&backState="+backState;
	load("#workmeetingDiv", url);
}
function deletedMeeting(id){
	if(id && id != ''){
		$.getJSON("${request.contextPath}/office/meeting/workmeeting-deletedMeeting.action", 
		{"meetingId":id}, function(data){
			if(data && data != ""){
				showMsgError(data);
			}else{
				showMsgSuccess('删除成功!','提示');
				sear();
			}
		}).error(function(){
			showMsgError("删除失败！");
		});
	}
}
function submitMeeting(id){
	if(id && id != ''){
		$.getJSON("${request.contextPath}/office/meeting/workmeeting-submitMeeting.action", 
		{"meetingId":id}, function(data){
			if(data && data != ""){
				showMsgError(data);
			}else{
				showMsgSuccess('提交成功!','提示');
				sear();
			}
		}).error(function(){
			showMsgError("提交失败！");
		});
	}
}

function publishMeeting(id){
	if(id && id != ''){
		$.getJSON("${request.contextPath}/office/meeting/workmeeting-publishMeeting.action", 
		{"meetingId":id}, function(data){
			if(data && data != ""){
				showMsgError(data);
			}else{
				showMsgSuccess('发布成功!','提示');
				sear();
			}
		}).error(function(){
			showMsgError("发布失败！");
		});
	}
}
function showAttendInfo(id){
	var url="${request.contextPath}/office/meeting/workmeeting-showAttendInfo.action?meetingId="+id;
   	openDiv("#classLayer3", "",url, false,"","300px");
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
