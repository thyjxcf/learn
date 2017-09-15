<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/attachmentUpload.js"></script>
<script>
function download(){
	var url = document.getElementById("fileUrl").value;
	document.getElementById("downloadFrame").src = url;
}

function back(){
	var url="${request.contextPath}/office/meeting/workmeeting-myMeeting.action?meetingName=${meetingName!}&show=${show!}";
	load("#workmeetingDiv", url);
}
</script>
<form method="post" class=" mt-20" enctype="multipart/form-data">
<@common.tableDetail divClass="table-form">
	<tr>
		<th style="width:30%">会议名称</th>
		<td>
			${meeting.name!}
		</td>
	</tr>
	<tr>
		<th><span class="c-orange mr-5">*</span>纪要概况</th>
		<td>
			${officeWorkMeetingMinutes.content!}
		</td>
	</tr>
	<tr>
		<th>纪要附件</th>
		<td>
			<div class="fn-left">
				<input id="uploadFilePath" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="${officeWorkMeetingMinutes.fileName!}" maxLength="125"/>&nbsp;&nbsp;
		</div>
	 		<#if officeWorkMeetingMinutes.fileName?exists>
		 		<div class="fn-rel fn-left" style="width:60px;overflow:hidden">	
		 			<input type="hidden" id="fileUrl" value="${officeWorkMeetingMinutes.fileUrl}" />
					<span class="upload-span"><a href="javascript:download();" class="">
					<img alt="下载" src="${request.contextPath}/static/images/icon/download.png">
					</a></span>
				</div>
			</#if>
		</td>
	</tr>
	<tr>
		<td colspan="2" class="td-opt">
		    <a class="abtn-blue-big" href="javascript:void(0);" onclick="back();">返回</a>
		</td>
	</tr>
</@common.tableDetail>
<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
</form>
</@common.moduleDiv>