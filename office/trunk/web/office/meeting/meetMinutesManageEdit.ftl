<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/attachmentUpload.js"></script>
<script>
function download(){
	var url = document.getElementById("fileUrl").value;
	document.getElementById("downloadFrame").src = url;
}
function doSave(){
	if(!isActionable("#btnSave")){
		return;
	}
	if(!checkAllValidate("#meetMn")){
		return;
	}
	$("btnSave").attr("class","abtn-unable");
	var fileName=document.getElementById("uploadFilePath").value;
	
	var options={
		url:'${request.contextPath}/office/meeting/workmeeting-meetingMinutesManageSave.action?fileName='+fileName,
		dataType:'json',
		clearForm:false,
		resetForm:false,
		type:'post',
		success:showReply
	};
	$("#meetMn").ajaxSubmit(options);
}

function showReply(data){
	if(!data.operateSuccess){
		showMsgError(data.promptMessage);
		$("btnSave").attr("class","abtn-blue");
		return;
	}else{
		showMsgSuccess(data.promptMessage,"提示",function(){
			doSearch();
		});
	}
}

function back(){
	doSearch();
}
</script>
<form id="meetMn" action="" method="post" enctype="multipart/form-data">
<@common.tableDetail divClass="table-form">
	<input type="hidden" id="meeting.id" name="meeting.id" value="${meeting.id!}"></input>
	<input type="hidden" id="officeWorkMeetingMinutes.id" name="officeWorkMeetingMinutes.id" value="${officeWorkMeetingMinutes.id!}"></input>
	<input type="hidden" id="officeWorkMeetingMinutes.meetingId" name="officeWorkMeetingMinutes.meetingId" value="${meeting.id!}"></input>
	<input type="hidden" id="fileUrl" name="fileUrl" value="${officeWorkMeetingMinutes.fileUrl?default('')}"/>
	<tr>
		<th style="width:30%">会议名称</th>
		<td>
			${meeting.name!}
		</td>
	</tr>
	<tr>
		<th><span class="c-orange mr-5">*</span>纪要概况</th>
		<td>
			<textarea name="officeWorkMeetingMinutes.content" notNull="true" id="officeWorkMeetingMinutes.content" rows="4" cols="70" msgName="纪要概况" maxlength="100">${officeWorkMeetingMinutes.content!}</textarea>
		</td>
	</tr>
	<tr>
		<th>纪要附件</th>
		<td>
			<div class="fn-left">
			<#if officeWorkMeetingMinutes.fileName?exists>
				<input id="uploadFilePath" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="${officeWorkMeetingMinutes.fileName!}" maxLength="125"/>&nbsp;&nbsp;
			<#else>
				<input id="uploadFilePath" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="" maxLength="125"/>&nbsp;&nbsp;
			</#if>
		</div>
		<#if readonlyStyle?default('false') != "true">
	 		<div class="fn-rel fn-left" style="width:80px;overflow:hidden">	
	 			<span class="upload-span"><a href="javascript:void(0)" class="">选择文件</a></span>       
     			<input  id="fileInput" name="fileInput" hidefocus='' type="file" onchange="doChange();" />
	 		</div>
	 		<div id="cleanFile" <#if officeWorkMeetingMinutes.fileName?exists>style="display:display"<#else>style="display:none"</#if>>
	 			<span class="upload-span"><a href="javascript:deleteFile();" class="">清空</a></span>
	 		</div>
	 	<#else>
	 		<#if officeWorkMeetingMinutes.fileName?exists>
		 		<div class="fn-rel fn-left" style="width:60px;overflow:hidden">	
					<span class="upload-span"><a href="javascript:download();" class="">
					<img alt="下载" src="${request.contextPath}/static/images/icon/download.png">
					</a></span>
				</div>
			</#if>
 		</#if>
		</td>
	</tr>
	<tr>
		<td colspan="2" class="td-opt">
		    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doSave();" id="btnSave">保存</a>
			<a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="back();">返回</a>
		</td>
	</tr>
</@common.tableDetail>
<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
</form>
<script>
$(function(){
	vselect();
	$(".upload-span").mouseover(function(){
		$("#fileInput").offset({"top":$(".upload-span").offset().top });
	});
	resetFilePos();
})
function doChange(){
	$('#uploadFilePath').val($('#fileInput').val());
	$('#cleanFile').attr("style","display:display");
}
function resetFilePos(){
	$("#fileInput").css({"position":"absolute","-moz-opacity":"0","opacity":"0","filter":"alpha(opacity=0)","cursor":"pointer","font-size":"30px","left":"-350px"});
	$("#fileInput").css({"display":""});
}
function deleteFile(){
	$('#uploadFilePath').val('');
	var file = $("#fileInput")
	file.after(file.clone().val(""));
	file.remove();
	$('#cleanFile').attr("style","display:none");
}
</script>
</@common.moduleDiv>