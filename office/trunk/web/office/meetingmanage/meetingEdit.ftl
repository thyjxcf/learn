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
	
	if(!isActionable("#btnSave")){
		return;
	}
	if(!isActionable("#btnSaveSubmit")){
		return;
	}
	if(!checkAllValidate("#meetingApplydiv")){
		return;
	}
	if(!checkAfterDate(document.getElementById("startTime"), document.getElementById("endTime"))){
		return;
	}
	var startTime = document.getElementById("startTime").value;
	var endTime = document.getElementById("endTime").value;
	if("" != startTime){
		document.getElementById("startTime").value = startTime +":00";
	}
	if("" != endTime){
		document.getElementById("endTime").value = endTime +":00";
	}
	var fileName = document.getElementById('uploadFilePath').value;
	$("#btnSave").attr("class", "abtn-unable-big");
	$("#btnSaveSubmit").attr("class", "abtn-unable-big");
	var options = {
       url:'${request.contextPath}/office/meetingmanage/meetingmanage-remote-save.action?officeMeetingApply.fileName='+fileName, 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#mainform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSave").attr("class", "abtn-blue-big");
	$("#btnSaveSubmit").attr("class", "abtn-blue-big");
	if(!data.operateSuccess){
		if(data.errorMessage!=null&&data.errorMessage!=""){
			showMsgError(data.errorMessage);
			return;
		}else{
			var startTime = document.getElementById("startTime").value;
			var endTime = document.getElementById("endTime").value;
			if("" != startTime){
				$("#startTime").val(startTime.substring(0,16));
			}
			if("" != endTime){
				$("#endTime").val(endTime.substring(0,16));
			}
			showMsgError(data.promptMessage);
	   		return;
		}
	}
	else{
		showMsgSuccess("保存成功！", "提示", function(){
			load("#contectDiv","${request.contextPath}/office/meetingmanage/meetingmanage-meetingList.action?doAction=apply");
		}); 
		return;
	}
}

function doCancle(){
	load("#contectDiv","${request.contextPath}/office/meetingmanage/meetingmanage-meetingList.action?doAction=apply");
}
</script>
<div id="meetingApplydiv">
<form id="mainform" action="" method="post" enctype="multipart/form-data">
<input type="hidden" id="id" name="id" value="${officeMeetingApply.id?default('')}"/>
<input type="hidden" id="unitId" name="unitId" value="${officeMeetingApply.unitId?default('')}"/>
<input type="hidden" id="applyUserId" name="applyUserId" value="${officeMeetingApply.applyUserId?default('')}"/>
<input type="hidden" id="auditState" name="auditState" value=""/>
<input type="hidden" id="fileUrl" name="fileUrl" value="${officeMeetingApply.fileUrl?default('')}"/>

	<p class="table-dt">会议申请</p>
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    <tr>
        <th width="15%"><span class="c-orange mr-5">*</span>会议主题：</th>
        <td width="35%">
        	<input type="text" id="meetingTheme" name="meetingTheme" class="input-txt fn-left ${classStyle?default('')}" <#if readonlyStyle == "true">readonly="readonly"</#if> style="width:200px;" notNull="true" msgName="会议主题" maxlength="60" value="${officeMeetingApply.meetingTheme?default("")}">
        </td>
        <th width="15%">主持人：</th>
        <td width="35%">
        <#if readonlyStyle?default('false') != "true">
			<@commonmacro.selectOneUser idObjectId="hostUserId" nameObjectId="hostUserName" width=400 height=300>
				<input type="hidden" name="hostUserId" id="hostUserId" value="${officeMeetingApply.hostUserId?default('')}"> 
				<input type="text" name="hostUserName" id="hostUserName" notNull="false" msgName="主持人"  class="input-txt fn-left" value="${officeMeetingApply.hostUserName?default("")}"  style="width:200px;" readonly="readonly">
	  		</@commonmacro.selectOneUser>
		<#else>
			<input type="text" id="hostUserName" name="hostUserName" class="input-txt fn-left input-readonly" readonly="readonly" style="width:200px;" notNull="false" msgName="主持人" maxlength="60" value="${officeMeetingApply.hostUserName?default("")}">
  		</#if>
        </td>
    </tr> 
    <tr>
        <th width="15%"><span class="c-orange mr-5">*</span>使用时间：</th>
        <td width="35%">
    		<@htmlmacro.datepicker name="startTime" id="startTime" class="input-date"  notNull="true" msgName="会议开始时间" maxlength="30" readonly="${readonlyStyle?default('false')}" value="${(officeMeetingApply.startTime?string('yyyy-MM-dd HH:mm'))?if_exists}" dateFmt='yyyy-MM-dd HH:mm'/>
            <span>-</span>
            <@htmlmacro.datepicker name="endTime" id="endTime" class="input-date"  notNull="true" msgName="会议结束时间" maxlength="30" readonly="${readonlyStyle?default('false')}" value="${(officeMeetingApply.endTime?string('yyyy-MM-dd HH:mm'))?if_exists}" dateFmt='yyyy-MM-dd HH:mm'/>
        </td>
        <th width="15%"><span class="c-orange mr-5">*</span>参与对象：</th>
        <td width="35%">
        	<input type="text" id="participants" name="participants" class="input-txt fn-left ${classStyle?default('')}" <#if readonlyStyle == "true">readonly="readonly"</#if> style="width:200px;" notNull="true" msgName="参与对象" maxlength="60" value="${officeMeetingApply.participants?default("")}">
        </td>
    </tr>
    <tr>
    	<th width="15%"><span class="c-orange mr-5">*</span>会议地点：</th>
        <td width="35%">
        	<input type="text" id="meetingPlace" name="meetingPlace" class="input-txt fn-left ${classStyle?default('')}" <#if readonlyStyle == "true">readonly="readonly"</#if> style="width:200px;" notNull="true" msgName="会议地点" maxlength="60" value="${officeMeetingApply.meetingPlace?default("")}">
        </td>
    	<th width="15%"><span class="c-orange mr-5">*</span>会议室：</th>
        <td width="35%">
        	<input type="text" id="meetingRoom" name="meetingRoom" class="input-txt fn-left ${classStyle?default('')}" <#if readonlyStyle == "true">readonly="readonly"</#if> style="width:200px;" notNull="true" msgName="会议室" maxlength="60" value="${officeMeetingApply.meetingRoom?default("")}">
        </td>
	</tr>
   	<tr>
   		<th width="15%"><span class="c-orange mr-5">*</span>主办部门：</th>
   		<td colspan="3">
	       	<#if readonlyStyle?default('false') != "true">
	       		<@commonmacro.selectMoreTree idObjectId="deptIds" nameObjectId="deptNames"  preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action?allLinkOpen=false" >
		  			<input type="hidden" name="deptIds" id="deptIds" value="${officeMeetingApply.deptIds?default('')}" class="select_current02"> 
		  	   		<textarea name="deptNames" id="deptNames" notNull="true" msgName="主办部门"  class="text-area my-10 ${classStyle?default('')}" rows="4" cols="69" style="width:80%;padding:5px 1%;height:50px;" readonly="readonly">${officeMeetingApply.deptNames?default("")}</textarea>
				</@commonmacro.selectMoreTree>
			<#else>
	  			<textarea name="deptNames" id="deptNames" notNull="true" msgName="主办部门" class="text-area my-10 input-readonly" rows="4" cols="69" maxLength="2000" style="width:80%;padding:5px 1%;height:50px;" readonly="readonly">${officeMeetingApply.deptNames?default('')}</textarea>
	  		</#if>
		</td>
   	</tr>
	<tr>
		<th width="15%"><span class="c-orange mr-5">*</span>通知人员：</th>
   		<td colspan="3">
   			<#if readonlyStyle?default('false') != "true">
				<@commonmacro.selectMoreUser idObjectId="meetingUserIds" nameObjectId="meetingUserNames" width=400 height=300>
					<input type="hidden" name="meetingUserIds" id="meetingUserIds" value="${officeMeetingApply.meetingUserIds?default('')}"> 
					<textarea name="meetingUserNames" id="meetingUserNames" notNull="true" msgName="通知人员"  class="text-area my-10 ${classStyle?default('')}" rows="4" cols="69" style="width:80%;padding:5px 1%;height:50px;" readonly="readonly">${officeMeetingApply.meetingUserNames?default("")}</textarea>
		  		</@commonmacro.selectMoreUser>
	  		<#else>
	  			<textarea name="meetingUserNames" id="meetingUserNames" notNull="true" msgName="通知人员" class="text-area my-10 input-readonly" rows="4" cols="69" maxLength="2000" style="width:80%;padding:5px 1%;height:50px;" readonly="readonly">${officeMeetingApply.meetingUserNames?default('')}</textarea>
	  		</#if>
		</td>
	</tr>
	<tr>
		<th width="15%"><span class="c-orange mr-5">*</span>会议内容：</th>
   		<td colspan="3">
			<textarea name="meetingContent" id="meetingContent" notNull="true" msgName="会议内容" class="text-area my-10 ${classStyle?default('')}" rows="4" cols="69" maxLength="2000" style="width:80%;padding:5px 1%;height:50px;" <#if readonlyStyle?default('false') == "true">readonly="readonly"</#if>>${officeMeetingApply.meetingContent?default('')}</textarea>
		</td>
	</tr>
	<tr>
		<th width="15%">会议费用：</th>
        <td width="35%">
        	<input type="text" id="fee" name="fee" class="input-txt fn-left ${classStyle?default('')}" <#if readonlyStyle == "true">readonly="readonly"</#if> style="width:200px;" notNull="false" msgName="会议费用" regex="/^[0-9]*$/" regexMsg="会议费用 只能输入正整数！" maxlength="9" value="${officeMeetingApply.fee?default("")}">
        </td>
		<th width="15%">附件：</th>
        <td width="35%">
		<div class="fn-left">
			<#if officeMeetingApply.fileName?exists>
				<input id="uploadFilePath" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="${officeMeetingApply.fileName!}" maxLength="125"/>&nbsp;&nbsp;
			<#else>
				<input id="uploadFilePath" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="" maxLength="125"/>&nbsp;&nbsp;
			</#if>
		</div>
		<#if readonlyStyle?default('false') != "true">
	 		<div class="fn-rel fn-left" style="width:60px;overflow:hidden">	
	 			<span class="upload-span"><a href="javascript:void(0)" class="">选择文件</a></span>       
     			<input  id="fileInput" name="fileInput" hidefocus='' type="file" onchange="doChange();" />
	 		</div>
	 		<div id="cleanFile" <#if officeMeetingApply.fileName?exists>style="display:display"<#else>style="display:none"</#if>>
	 			<span class="upload-span"><a href="javascript:deleteFile();" class="">清空</a></span>
	 		</div>
	 	<#else>
	 		<#if officeMeetingApply.fileName?exists>
		 		<div class="fn-rel fn-left" style="width:60px;overflow:hidden">	
					<span class="upload-span"><a href="javascript:download();" class="">
					<img alt="下载" src="${request.contextPath}/static/images/icon/download.png">
					</a></span>
				</div>
			</#if>
 		</#if>
		</td>
	</tr>
    </table>

</form>
</div>
<p class="t-center pt-15">
	<#if readonlyStyle?default('false') != "true">
	<a href="javascript:doSave('0');" id="btnSave" class="abtn-blue-big">保存</a>
    <a href="javascript:doSave('1');" id="btnSaveSubmit" class="abtn-blue-big">保存并提交</a>
    <a href="javascript:doCancle();" class="abtn-blue-big">返回</a>
    <#else>
    <a href="javascript:doCancle();" class="abtn-blue-big">返回</a>
    </#if>
</p>
<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
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
</@htmlmacro.moduleDiv>