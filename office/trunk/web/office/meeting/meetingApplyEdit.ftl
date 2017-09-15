<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="会议详细">
<form name="form1" id="form1" method="POST" action="">
<p class="table-dt fb18 mt-15">会议详细</p>
<input name="meeting.unitId" type="hidden" value="${meeting.unitId?default('')}">
<input name="meeting.id" type="hidden" value="${meeting.id?default('')}">
		<@htmlmacro.tableDetail divClass="table-form table-list-edit">
		   <tr>
		   <#if meeting.state == 0 || meeting.state == 1 || meeting.state == 4>
		      <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;会议名称：</th>
		   	  <td width="35%">
				 <input type="text" name="meeting.name" id="meeting.name" class="input-txt" notNull="true" msgName="会议名称" maxlength="250" style="width:300px;" value="${meeting.name!}"/>
		   	  </td>
		      <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;会议时间：</th>
		   	  <td width="35%">
		   	  	<@htmlmacro.datepicker class="input-txt" style="width:300px;" name="meeting.meetingDate" id="meetingDate" value="${(meeting.meetingDate?string('yyyy-MM-dd HH:mm:ss'))!}" size="20" maxlength="19" msgName="会议时间" notNull="true" dateFmt="yyyy-MM-dd HH:mm:00"/>
		   	  </td>
		   </tr>
		   <tr>
		      <th width="15%">&nbsp;会期：</th>
		   	  <td width="35%">
				  <input class="input-txt" style="width:300px;" name="meeting.days" id="days"  maxlength='4' dataType='float' decimalLength='1' maxValue='10' minValue='0' value="${((meeting.days)?string("#.#"))?if_exists}"/>
		   	  </td>
		      <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;会议地点：</th>
		   	  <td width="35%">
		   	  	  <input type="text" name="meeting.place" id="meeting.place" class="input-txt" notNull="true" msgName="会议地点" maxlength="100" style="width:300px;" value="${meeting.place!}"/>	
		   	  </td>
		   </tr>
		   <tr>
		   	  <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;主办科室：</th>
		   	  <td width="35%">
			       	<@commonmacro.selectMoreTree idObjectId="meeting.hostDept" nameObjectId="meeting.hostDeptStr"  preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action" >
			  	   <input type="hidden" name="meeting.hostDept" id="meeting.hostDept" value="${meeting.hostDept!}"> 
			  	   <input type="text" name="meeting.hostDeptStr" id="meeting.hostDeptStr" notNull="true" msgName="主办科室"  style="width:300px;" value="${meeting.hostDeptStr!}" class="input-txt edit-class" readonly="readonly">
			  	   </@commonmacro.selectMoreTree>
		   	  </td>
		   	  <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;参会局领导：</th>
		   	  <td width="35%">
		   	  	  <@commonmacro.selectMoreUser idObjectId="meeting.leader" nameObjectId="meeting.leaderStr" width=400 height=300>
						<input type="hidden" id="meeting.leader" name="meeting.leader" value="${meeting.leader!!}"/> 
						<input type="text" id="meeting.leaderStr" name="meeting.leaderStr" notNull="true" msgName="参会局领导" value="${meeting.leaderStr!}" class="input-txt fn-left" style="width:300px;" readonly="readonly"/>
			  		</@commonmacro.selectMoreUser>
		   	  </td>	 
		   </tr>
		   <tr>
                <th><span class="c-orange mt-5 ml-10">*</span>&nbsp;列席科室：</th>
                <td>
            		<@commonmacro.selectMoreTree idObjectId="meeting.otherDept" nameObjectId="meeting.otherDeptStr"  preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action?allLinkOpen=false" switchSelector=".edit-class">
			  	   <input type="hidden" name="meeting.otherDept" id="meeting.otherDept" value="${meeting.otherDept!}"> 
			  	   <input type="text" name="meeting.otherDeptStr" id="meeting.otherDeptStr" notNull="true" msgName="列席科室"  style="width:300px;" value="${meeting.otherDeptStr!}" class="input-txt edit-class" readonly="readonly">
			  	   </@commonmacro.selectMoreTree>
            	</td>
		      <th>其他参会人员：</th>
	          <td>
	          	  <input type="text" name="meeting.otherPersons" id="meeting.otherPersons" class="input-txt" msgName="其他参会人员" maxlength="60" style="width:300px;" value="${meeting.otherPersons!}"/>
	          </td>
		   </tr>
		   <tr>
                <th><span class="c-orange mt-5 ml-10">*</span>&nbsp;预估参会人数：</th>
                <td>
            	<input class="input-txt" style="width:300px;" name="meeting.forecastNumber" id="forecastNumber"  maxlength='3' dataType='integer' decimalLength='0'  maxValue='100' minValue='0' value="${meeting.forecastNumber}"/>	
            	</td>
		      <th>纪要维护人：</th>
	          <td>
			  	   <@commonmacro.selectOneUser idObjectId="meeting.minutesPeople" nameObjectId="meeting.minutesPeopleStr" width=400 height=300>
						<input type="hidden" id="meeting.minutesPeople" name="meeting.minutesPeople" value="${meeting.minutesPeople!}"/> 
						<input type="text" id="meeting.minutesPeopleStr" name="meeting.minutesPeopleStr" value="${meeting.minutesPeopleStr!}" class="input-txt fn-left" style="width:300px;" readonly="readonly"/>
			  		</@commonmacro.selectOneUser>
	          </td>
		   </tr>
		   <tr>
                <th>备注：</th>
                <td colspan="3">
                	<textarea name="meeting.remark" id="remark" style="width:350px;" rows="4" cols="70" value="${meeting.remark!}" maxlength="200">${meeting.remark!}</textarea>
            	</td>
		   </tr>
	<#elseif meeting.state == 2>
		   <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;会议名称：</th>
		   	  <td width="35%">
				 ${meeting.name!}
		   	  </td>
		      <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;会议时间：</th>
		   	  <td width="35%">
		   	  	${(meeting.meetingDate?string('yyyy-MM-dd HH:mm:00'))!}
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
			       	${meeting.hostDeptStr!}
		   	  </td>
		   	  <th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;参会局领导：</th>
		   	  <td width="35%">
		   	  	  ${meeting.leaderStr!}
		   	  </td>	 
		   </tr>
		   <tr>
                <th><span class="c-orange mt-5 ml-10">*</span>&nbsp;列席科室：</th>
                <td>
            		${meeting.otherDeptStr!}
            	</td>
		      <th>其他参会人员：</th>
	          <td>
				${meeting.otherPersons!}
		     </td>
		   </tr>
		   <tr>
                <th><span class="c-orange mt-5 ml-10">*</span>&nbsp;预估参会人数：</th>
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
		   <tr>
                <th><span class="c-orange mt-5 ml-10">*</span>&nbsp;会议类型：</th>
                <td colspan="3">
                		<div class="select_box">
		    			<@htmlmacro.select style="width:350px;" valName="meeting.type" valId="type" notNull="true" msgName="会议类型" myfunchange="">
						${appsetting.getMcode("DM-GZHYLX").getHtmlTag(meeting.type?default(''))}
					</@htmlmacro.select>
				</div>
            	</td>
		   </tr>
		   <tr>
		      <th width="15%">审核意见：</th>
		   	  <td width="35%">
				  <input class="input-txt" style="width:300px;" name="meeting.auditIdea" id="auditIdea" maxlength="100"  value="${meeting.auditIdea!}"/>
		   	  </td>
		      <th width="15%">&nbsp;是否通过：</th>
		   	  <td width="35%">
		   	  	<@htmlmacro.select style="width:300px;" valName="meeting.state" valId="state" myfunchange="">
						<a val="3" <#if meeting.state == 2>class="selected"</#if>>通过</a>
						<a val="4" <#if meeting.state == 4>class="selected"</#if>>不通过</a>
					</@htmlmacro.select>
		   	  </td>
		   </tr>
		   <tr>
		      <th width="15%">审核人：</th>
		   	  <td width="35%">
				${meeting.auditUser!}
				  <input type="hidden" value="${meeting.auditUserId!}" name="meeting.auditUserId"/>
		   	  </td>
		      <th width="15%">&nbsp;审核时间：</th>
		   	  <td width="35%">
		   	  	<input class="input-txt" style="width:300px;" name="meeting.auditTime" id="auditTime" value="${(meeting.auditTime?string('yyyy-MM-dd HH:mm:00'))!}"/>
		   	  </td>
		   </tr>
		   </#if>
		   
		</@htmlmacro.tableDetail>
		
<p class="t-center py-30">
<#if meeting.state == 0  || meeting.state == 1 || meeting.state == 4>
    <a href="javascript:doSave('1');"  class="abtn-blue-big">保存</a>
    <a href="javascript:doSave('2');"  class="abtn-blue-big">提交</a>
    <a href="javascript:back();" class="abtn-blue-big ml-10">返回</a>              
<#elseif meeting.state == 2>
	<a href="javascript:doSaveAudit();" class="abtn-blue-big">保存</a>
     <a href="javascript:back();" class="abtn-blue-big ml-10">返回</a>
</#if>
</p>
</form> 
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
var isSubmit = false;

function doSaveAudit(){
	if(isSubmit){
    	return;
    }
	if(!checkAllValidate("#workmeetingDiv")){
		return;
	}
    isSubmit = true;
	var options = {
       url:'${request.contextPath}/office/meeting/workmeeting-meetingAuditSave.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post',
       timeout : 3000 
    };
    $('#form1').ajaxSubmit(options);
}

function doSave(state){
	if(isSubmit){
    	return;
    }
	if(!checkAllValidate("#workmeetingDiv")){
		return;
	}
    isSubmit = true;
	var options = {
       url:'${request.contextPath}/office/meeting/workmeeting-meetingApplyAddSave.action?submitState='+state, 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post',
       timeout : 3000 
    };
    $('#form1').ajaxSubmit(options);
}

function showReply(data){
	var error = data;
	if(error && error != ''){
		showMsgError(data);
		isSubmit = false;
	} else {
		showMsgSuccess('操作成功!','提示',back);
	}
}
function back(){
	<#if meeting.state != 2>
		var url="${request.contextPath}/office/meeting/workmeeting-meetingApply.action?meetingName=${meetingName!}&state=${state!}";
	<#else>
		var url="${request.contextPath}/office/meeting/workmeeting-meetingAudit.action?meetingName=${meetingName!}&state=${state!}";
	</#if>
	load("#workmeetingDiv", url);
}
</script>
</@htmlmacro.moduleDiv>