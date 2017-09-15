<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function download(){
	var url = document.getElementById("fileUrl").value;
	document.getElementById("downloadFrame").src = url;
}

function doSave(val){
	document.getElementById("auditState").value = val;
	
	if(!isActionable("#btnPass")){
		return;
	}
	if(!isActionable("#btnReject")){
		return;
	}
	if(!checkAllValidate("#meetingAuditdiv")){
		return;
	}
	
	$("#btnPass").attr("class", "abtn-unable-big");
	$("#btnReject").attr("class", "abtn-unable-big");
	var options = {
       url:'${request.contextPath}/office/meetingmanage/meetingmanage-auditSave.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#mainform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnPass").attr("class", "abtn-blue-big");
	$("#btnReject").attr("class", "abtn-blue-big");
	if(!data.operateSuccess){
		if(data.errorMessage!=null&&data.errorMessage!=""){
		showMsgError(data.errorMessage);
			return;
		}else{
			showMsgError(data.promptMessage);
	   		return;
		}
	}
	else{
		showMsgSuccess("审核成功！", "提示", function(){
			load("#contectDiv","${request.contextPath}/office/meetingmanage/meetingmanage-auditList.action?doAction=audit");
		}); 
		return;
	}
}

function doCancle(){
	load("#contectDiv","${request.contextPath}/office/meetingmanage/meetingmanage-auditList.action?doAction=audit");
}
</script>
<div id="meetingAuditdiv">
<form id="mainform" action="" method="post" enctype="multipart/form-data">
<input type="hidden" id="id" name="id" value="${officeMeetingApply.id?default('')}"/>
<input type="hidden" id="unitId" name="unitId" value="${officeMeetingApply.unitId?default('')}"/>
<input type="hidden" id="applyUserId" name="applyUserId" value="${officeMeetingApply.applyUserId?default('')}"/>
<input type="hidden" id="auditState" name="auditState" value=""/>
<input type="hidden" id="fileUrl" name="fileUrl" value="${officeMeetingApply.fileUrl?default('')}"/>

	<p class="table-dt">会议申请审核</p>
	<table border="0" cellspacing="0" cellpadding="0" class="table-form">
		<tr>
	        <th width="15%"><span class="c-orange mr-5">*</span>会议主题：</th>
	        <td width="35%">
	        	<input type="text" id="meetingTheme" name="meetingTheme" class="input-txt fn-left input-readonly" readonly="readonly" style="width:200px;" notNull="true" msgName="会议主题" maxlength="60" value="${officeMeetingApply.meetingTheme?default("")}">
	        </td>
	        <th width="15%">主持人：</th>
	        <td width="35%">
	        	<input type="text" id="hostUserName" name="hostUserName" class="input-txt fn-left input-readonly" readonly="readonly" style="width:200px;" notNull="false" msgName="主持人" maxlength="60" value="${officeMeetingApply.hostUserName?default("")}">
	        </td>
        </tr>
        <tr>
	        <th width="15%"><span class="c-orange mr-5">*</span>使用时间：</th>
	        <td width="35%">
	        	<input type="text" id="timeStr" name="timeStr" class="input-txt fn-left input-readonly" readonly="readonly" style="width:200px;" notNull="true" msgName="使用时间" maxlength="60" value="${officeMeetingApply.timeStr?default("")}">
	        </td>
	        <th width="15%"><span class="c-orange mr-5">*</span>参与对象：</th>
        	<td width="35%">
        		<input type="text" id="participants" name="participants" class="input-txt fn-left input-readonly" readonly="readonly" style="width:200px;" notNull="true" msgName="参与对象" maxlength="60" value="${officeMeetingApply.participants?default("")}">
        	</td>
        <tr>
	        <th width="15%"><span class="c-orange mr-5">*</span>会议地点：</th>
	        <td width="35%">
	        	<input type="text" id="meetingPlace" name="meetingPlace" class="input-txt fn-left input-readonly" readonly="readonly" style="width:200px;" notNull="true" msgName="会议地点" maxlength="60" value="${officeMeetingApply.meetingPlace?default("")}">
	        </td>
	        <th width="15%"><span class="c-orange mr-5">*</span>会议室：</th>
	        <td width="35%">
	        	<input type="text" id="meetingRoom" name="meetingRoom" class="input-txt fn-left input-readonly" readonly="readonly" style="width:200px;" notNull="true" msgName="会议时" maxlength="60" value="${officeMeetingApply.meetingRoom?default("")}">
	        </td>
        </tr>
       	<tr>
       		<th width="15%"><span class="c-orange mr-5">*</span>主办部门：</th>
   			<td colspan="3">
   				<textarea name="deptNames" id="deptNames" notNull="true" msgName="主办部门" class="text-area my-10 input-readonly" rows="4" cols="69" maxLength="2000" style="width:80%;padding:5px 1%;height:50px;" readonly="readonly">${officeMeetingApply.deptNames?default('')}</textarea>
   			</td>
       	</tr>
		<tr>
			<th width="15%"><span class="c-orange mr-5">*</span>通知人员：</th>
   			<td colspan="3">
				<textarea name="meetingUserNames" id="meetingUserNames" notNull="true" msgName="通知人员" class="text-area my-10 input-readonly" rows="4" cols="69" maxLength="2000" style="width:80%;padding:5px 1%;height:50px;" readonly="readonly">${officeMeetingApply.meetingUserNames?default('')}</textarea>
			</td>
		</tr>
		<tr>
			<th width="15%"><span class="c-orange mr-5">*</span>会议内容：</th>
	   		<td colspan="3">
				<textarea name="meetingContent" id="meetingContent" notNull="true" msgName="会议内容" class="text-area my-10 input-readonly" rows="4" cols="69" maxLength="2000" style="width:80%;padding:5px 1%;height:50px;" readonly="readonly">${officeMeetingApply.meetingContent?default('')}</textarea>
			</td>
		</tr>
		<tr>
			<th width="15%">会议费用：</th>
        	<td width="35%">
        		<input type="text" id="fee" name="fee" class="input-txt fn-left input-readonly" readonly="readonly" style="width:200px;" notNull="false" msgName="会议费用" regex="/^[0-9]*$/" regexMsg="会议费用 只能输入正整数！" maxlength="9" value="${officeMeetingApply.fee?default("")}">
        	</td>
			<th width="15%">附件：</th>
        	<td width="35%">
				<div class="fn-left">
					<input id="fileName" name="fileName" type="text" class="input-txt input-readonly" readonly="readonly" style="width:150px;" value="${officeMeetingApply.fileName!}" />&nbsp;&nbsp;
				</div>
				<#if officeMeetingApply.fileName?exists>
		 		<div class="fn-rel fn-left" style="width:60px;overflow:hidden">	
					<span class="upload-span"><a href="javascript:download();" class="">
					<img alt="下载" src="${request.contextPath}/static/images/icon/download.png">
					</a></span>
				</div>
				</#if>
			</td>
		</tr>
		<tr>
			<th width="15%"><span class="c-orange mr-5">*</span>审核意见：</th>
   			<td colspan="3">
				<textarea name="opinion" id="opinion" notNull="true" msgName="审核意见" class="text-area my-10 ${classStyle?default('')}" rows="4" cols="69" maxLength="2000" style="width:80%;padding:5px 1%;height:50px;" <#if readonlyStyle?default('false') == "true">readonly="readonly"</#if>>${officeMeetingApply.opinion?default('')}</textarea>
			</td>
		</tr>
    </table>

</form>
</div>
<p class="t-center pt-15">
	<#if readonlyStyle?default('false') != "true">
	<a href="javascript:doSave(2);" id="btnPass" class="abtn-blue-big">通过</a>
	<a href="javascript:doSave(3);" id="btnReject" class="abtn-blue-big">不通过</a>
    <a href="javascript:doCancle();" class="abtn-blue-big">返回</a>
    <#else>
    <a href="javascript:doCancle();" class="abtn-blue-big">返回</a>
    </#if>
</p>
<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
<script>
vselect();
</script>
</@htmlmacro.moduleDiv>