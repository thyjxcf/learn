<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/strspell.js"></script>
<script>
function download(){
	var url = document.getElementById("leaderFileURL").value;
	document.getElementById("downloadFrame").src = url;
}

function doSave(){
	var dealUserFileName = $("#uploadFilePath").val();
	if(dealUserFileName == ""){
		if(!showConfirm('您尚未上传附件，确定提交任务吗？')){
			return;
		}
	} else {
		if(!showConfirm('提交任务后将无法修改，确定提交任务吗？')){
			return;
		}
	}
	if(!isActionable("#btnSubmit")){
		return;
	}
	if(!checkAllValidate("#dealTaskEditDiv")){
		return;
	}
	$("#btnSubmit").attr("class", "abtn-unable-big");
	var options = {
       url:'${request.contextPath}/office/taskManage/taskManage-dealTask-save.action?officeTaskManage.dealUserFileName='+dealUserFileName, 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#editform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSubmit").attr("class", "abtn-blue-big");
	if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   return;
		   }else if(data.fieldErrorMap!=null){
			  $.each(data.fieldErrorMap,function(key,value){
				   addFieldError(key,value+"");
			  });
		   }else{
		   	   showMsgError(data.promptMessage);
			   return;
		   }
	}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-dealTask-list.action?pageIndex=${pageIndex?default(1)}&queryBeginDate=${queryBeginDate!}&queryEndDate=${queryEndDate!}");
			});
			return;
	}
}

function goBack(){
	load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-dealTask-list.action?pageIndex=${pageIndex?default(1)}&queryBeginDate=${queryBeginDate!}&queryEndDate=${queryEndDate!}");
}
</script>
<div id="dealTaskEditDiv">
<form name="editform" id="editform" method="post" enctype="multipart/form-data">
<input type="hidden" id="id" name="officeTaskManage.id" value="${officeTaskManage.id!}">
<input type="hidden" id="unitId" name="officeTaskManage.unitId" value="${officeTaskManage.unitId!}">
<input type="hidden" id="hasAttach" name="officeTaskManage.hasAttach" value="${officeTaskManage.hasAttach!}">
<input type="hidden" id="createUserId" name="officeTaskManage.createUserId" value="${officeTaskManage.createUserId!}">
<input type="hidden" id="createTime" name="officeTaskManage.createTime" value="${officeTaskManage.createTime!}">
<input type="hidden" id="firstRemindTime" name="officeTaskManage.firstRemindTime" value="${officeTaskManage.firstRemindTime!}">
<input type="hidden" id="secondRemindTime" name="officeTaskManage.secondRemindTime" value="${officeTaskManage.secondRemindTime!}">
<input type="hidden" id="remindNumber" name="officeTaskManage.remindNumber" value="${officeTaskManage.remindNumber!}">
<input type="hidden" id="actualFinishTime" name="officeTaskManage.actualFinishTime" value="${officeTaskManage.actualFinishTime!}">
<input type="hidden" id="hasSubmitAttach" name="officeTaskManage.hasSubmitAttach" value="${officeTaskManage.hasSubmitAttach!}">
<input type="hidden" id="leaderFileURL" name="officeTaskManage.leaderFileURL" value="${officeTaskManage.leaderFileURL!}">

    <div class="mt-15" style="padding:0 0 5px 0;">
		<@htmlmacro.tableDetail>
		<tr>
			<th width="15%">任务名称：</th>
			<td width="85%" colspan="3">
				<input readonly="true" class="input-txt input-readonly" id="taskName" name="officeTaskManage.taskName" value="${officeTaskManage.taskName!}" maxLength="100" notNull="true" style="width:700px;">
			</td>
		</tr>
		<tr>
			<th width="15%">负责人：</th>
			<td width="35%">
				<input type="hidden" id="dealUserId" name="officeTaskManage.dealUserId" value="${officeTaskManage.dealUserId!}">
				<input readonly="true" class="input-txt input-readonly" id="dealUserName" name="officeTaskManage.dealUserName" value="${officeTaskManage.dealUserName!}" notNull="true" style="width:170px;">
			</td>
			<th width="15%">规定完成时间：</th>
			<td width="35%">
				<@htmlmacro.datepicker class="input-txt input-readonly" readonly="true" name="completeTime" value="${((officeTaskManage.completeTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:170px;" notNull="true" maxlength="20"/>
			</td>
		</tr>	
		<tr>
			<@htmlmacro.tdt colspan="3" style="width:875px;" class="text-area fn-left input-readonly" readonly="true" msgName="备注" id="remark" name="officeTaskManage.remark" value="${(officeTaskManage.remark?default('')?trim)?if_exists}" maxLength="500"/>
		</tr>
		<tr>
			<th width="15%">任务附件：</th>
			<td width="85%" colspan="3">
	 		<#if officeTaskManage.leaderFileName?exists>
				<div class="fn-left">
					<input id="leaderFileName" name="leaderFileName" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="${officeTaskManage.leaderFileName!}" maxLength="125"/>&nbsp;&nbsp;
				</div>
				<div class="mt-5">
				 		<div class="fn-rel fn-left" style="width:60px;overflow:hidden">	
							<span class="upload-span1"><a href="javascript:download();" class="">
							<img alt="下载" src="${request.contextPath}/static/images/icon/download.png">
							</a></span>
						</div>
		 		</div>
			</#if>
			</td>
		</tr>		
		<tr>
			<@htmlmacro.tdt colspan="3" style="width:875px;" class="text-area fn-left" msgName="任务完成情况" id="finishRemark" name="officeTaskManage.finishRemark" value="${(officeTaskManage.finishRemark?default('')?trim)?if_exists}" maxLength="500"/>
		</tr>
		<tr>
			<th width="15%">上传附件：</th>
			<td width="85%" colspan="3">
			<div class="fn-left">
				<input id="uploadFilePath" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="${officeTaskManage.dealUserFileName!}" maxLength="125"/>&nbsp;&nbsp;
			</div>
			<div class="mt-5">
		 		<div class="fn-rel fn-left" style="width:60px;overflow:hidden">	
		 			<span class="upload-span"><a href="javascript:void(0)" class="">选择文件</a></span>       
	     			<input  id="fileInput" name="fileInput" hidefocus='' type="file" onchange="doChange();" />
		 		</div>
		 		<div id="cleanFile" <#if officeTaskManage.dealUserFileName?exists>style="display:display"<#else>style="display:none"</#if>>
		 			<span class="upload-span"><a href="javascript:deleteFile();" class="">清空</a></span>
		 		</div>
	 		</div>
			</td>
		</tr>		

		</@htmlmacro.tableDetail>
		

<p class="t-center pt-30">
	<a href="javascript:doSave();" class="abtn-blue-big" id="btnSubmit">提交任务</a>
	<a href="javascript:goBack();" class="abtn-blue-big">取消</a>
</p>
</div>
</form>
</div>
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
	var file = $("#fileInput");
	file.after(file.clone().val(""));
	file.remove();
	$('#cleanFile').attr("style","display:none");
}
</script>
</@htmlmacro.moduleDiv>