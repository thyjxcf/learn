<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="会议详细">
<form name="form1" id="form1" method="POST" action="">
<p class="table-dt fb18 mt-15">会议详细</p>
		<@htmlmacro.tableDetail divClass="table-form table-list-edit">
		   <tr>
		      <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;会议名称：</th>
		   	  <td width="35%">
				${meeting.name!}
		   	  </td>
		      <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;会议时间：</th>
		   	  <td width="35%">
		   	  	${(meeting.meetingDate?string('yyyy-MM-dd HH:mm:ss'))!}
		   	  </td>
		   </tr>
		   <tr>
		      <th width="15%">&nbsp;会期：</th>
		   	  <td width="35%">
				  ${((meeting.days)?string("#.#"))?if_exists}
		   	  </td>
		      <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;会议地点：</th>
		   	  <td width="35%">
		   	  	  ${meeting.place!}	
		   	  </td>
		   </tr>
		   <tr>
		   	  <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;主办科室：</th>
		   	  <td width="35%">
			       	${meeting.hostDeptStr!"部门已删除"}
		   	  </td>
		   	  <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;参会局领导：</th>
		   	  <td width="35%">
		   	  	  ${meeting.leaderStr!}
		   	  </td>	 
		   </tr>
		   <tr>
                <th><span class="c-orange mt-5 ml-10">*</span><span  class="parts-tt1">列席科室：</th>
                <td>
            		${meeting.otherDeptStr!"部门已删除"}
            	</td>
		      <th>其他参会人员：</th>
	          <td>
	          	  ${meeting.otherPersons!}
	          </td>
		   </tr>
		   <tr>
                <th><span class="c-orange mt-5 ml-10">*</span><span  class="parts-tt1">预估参会人数：</th>
                <td>
            	${meeting.forecastNumber}	
            	</td>
		      <th>纪要维护人：</th>
	          <td>
			  	 ${meeting.minutesPeopleStr!}
	          </td>
		   </tr>
		   <tr>
                <th>备注：</th>
                <td colspan="3">
                	${meeting.remark!}
            	</td>
		   </tr>
		   <#if meeting.state gt 2>
		   	<tr>
                <th>会议类型：</th>
                <td colspan="3">
                		<div class="select_box">
                			${appsetting.getMcode("DM-GZHYLX").get(meeting.type!)}
				</div>
            	</td>
		   </tr>
		   <tr>
		      <th width="15%">审核意见：</th>
		   	  <td width="35%">
				  ${meeting.auditIdea!}
		   	  </td>
		      <th width="15%">&nbsp;是否通过：</th>
		   	  <td width="35%">
		   	  	<#if meeting.state == 3>
		   	  	已通过
		   	  	<#elseif meeting.state == 4>
		   	  	不通过
		   	  	</#if>
		   	  </td>
		   </tr>
		   <tr>
		      <th width="15%">审核人：</th>
		   	  <td width="35%">
				${meeting.auditUser!}
		   	  </td>
		      <th width="15%">&nbsp;审核时间：</th>
		   	  <td width="35%">
		   	  	${(meeting.auditTime?string('yyyy-MM-dd HH:mm:00'))!}
		   	  </td>
		   </tr>
		   </#if>
		</@htmlmacro.tableDetail>
<p class="t-center py-30">
    <a href="javascript:back();" class="abtn-blue-big ml-10" onclick="back();">返回</a></p>                        
</form> 
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
function back(){
<#if backState == '1'>
	var url="${request.contextPath}/office/meeting/workmeeting-meetingApply.action?meetingName=${meetingName!}&state=${state!}";
<#elseif backState == '2'>
	var url="${request.contextPath}/office/meeting/workmeeting-meetingAudit.action?meetingName=${meetingName!}&state=${state!}";
<#elseif backState == '3'>
	var url="${request.contextPath}/office/meeting/workmeeting-myMeeting.action?meetingName=${meetingName!}&show=${show!}";
</#if>
	load("#workmeetingDiv", url);
}
</script>
</@htmlmacro.moduleDiv>