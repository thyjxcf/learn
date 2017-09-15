<#import "/common/htmlcomponent.ftl" as common />
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort" style="table-layout:fixed">
	<tr>
		<th class="t-center" width="15%">会议名称</th>
		<th class="t-center" width="10%">会议时间</th>
	    	<th class="t-center" width="9%">会期</th>
		<th class="t-center" width="25%">主办科室</th>
		<th class="t-center" width="8%">状态</th>
		<th class="t-center" width="25%">参会局领导</th>
		<th class="t-center" width="8%">操作</th>
	</tr>
<#if meetinglist?exists &&  (meetinglist?size>0)>
	<#list meetinglist as x>
	<tr>
		<td style="word-break:break-all; word-wrap:break-word;"><a href="javascript:meetingView('${x.id!}','2');">${x.name!}</a></td>
		<td>${(x.meetingDate?string('yyyy-MM-dd HH:mm'))?if_exists}</td>
	  	<td style="word-break:break-all; word-wrap:break-word;">${((x.days)?string("#.#"))?if_exists}</td>
		<td style="word-break:break-all; word-wrap:break-word;">${x.hostDeptStr!}</td>
	  	<td>
	  		<#if x.state == 2>
	  			待审核
	  		<#elseif x.state == 3>
	  			已通过
	  		<#elseif x.state == 4>
				未通过
	  		</#if>
	  	</td>
	  	<td style="word-break:break-all; word-wrap:break-word;">${x.leaderStr!}</td>
	  	<td class="t-center">
	  		<#if x.state == 2>
	  		<a href="javascript:showMeetingAudit('${x.id!}');">审核</a>
	  		<#elseif x.state == 3>
		  		<#if x.isPublish>
					&nbsp;已发布
	            	</#if>
	  		<#elseif x.state == 4>
			&nbsp;
	  		</#if>
	  	</td>
	</tr>
	</#list>
<#else>
	<tr>
		<td colspan="7"> <p class="no-data mt-20">还没有任何记录哦！</p></td>
	</tr>
</#if>
</@common.tableList>
<@common.Toolbar container="#meetingAuditListDiv"/>
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

function showMeetingAudit(id){
	var state = document.getElementById("state").value;
	var meetingName = document.getElementById("meetingName").value;
	var startTime= document.getElementById("startTime").value;
	var endTime= document.getElementById("endTime").value;
   	var url="${request.contextPath}/office/meeting/workmeeting-meetingApplyAdd.action?state="+state+"&meetingName="+meetingName+"&startTime="+startTime+"&endTime="+endTime+"&meetingId="+id;
   	load("#workmeetingDiv", url);
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
