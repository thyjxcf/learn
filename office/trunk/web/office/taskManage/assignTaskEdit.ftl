<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/strspell.js"></script>
<script>
function download(){
	var url = document.getElementById("leaderFileURL").value;
	document.getElementById("downloadFrame").src = url;
}

function doSave(flag){
	if(flag == '1'){
		if(!isActionable("#btnSave")){
			return;
		}
	}else{
		if(!isActionable("#btnSubmit")){
			return;
		}
	}
	if(!checkAllValidate("#assignTaskEditDiv")){
		return;
	}
	if(flag == '1'){
		$("#btnSave").attr("class", "abtn-unable-big");
	}else{
		$("#btnSubmit").attr("class", "abtn-unable-big");
	}
	var leaderFileName = $("#uploadFilePath").val();
	var options = {
       url:'${request.contextPath}/office/taskManage/taskManage-assignTask-save.action?officeTaskManage.state='+flag+'&officeTaskManage.leaderFileName='+leaderFileName, 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#editform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSave").attr("class", "abtn-blue-big");
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
	   			load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-assignTask-list.action?pageIndex=${pageIndex?default(1)}&queryBeginDate=${queryBeginDate!}&queryEndDate=${queryEndDate!}");
			});
			return;
	}
}

function goBack(){
	load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-assignTask-list.action?pageIndex=${pageIndex?default(1)}&queryBeginDate=${queryBeginDate!}&queryEndDate=${queryEndDate!}");
}
</script>
<div id="assignTaskEditDiv">
<form name="editform" id="editform" method="post" enctype="multipart/form-data">
<input type="hidden" id="id" name="officeTaskManage.id" value="${officeTaskManage.id!}">
<input type="hidden" id="unitId" name="officeTaskManage.unitId" value="${officeTaskManage.unitId!}">
<input type="hidden" id="hasAttach" name="officeTaskManage.hasAttach" value="${officeTaskManage.hasAttach!}">
<input type="hidden" id="createUserId" name="officeTaskManage.createUserId" value="${officeTaskManage.createUserId!}">
<input type="hidden" id="createTime" name="officeTaskManage.createTime" value="${officeTaskManage.createTime!}">
<input type="hidden" id="remindNumber" name="officeTaskManage.remindNumber" value="${officeTaskManage.remindNumber!}">
<input type="hidden" id="actualFinishTime" name="officeTaskManage.actualFinishTime" value="${officeTaskManage.actualFinishTime!}">
<input type="hidden" id="hasSubmitAttach" name="officeTaskManage.hasSubmitAttach" value="${officeTaskManage.hasSubmitAttach!}">
<input type="hidden" id="finishRemark" name="officeTaskManage.finishRemark" value="${officeTaskManage.finishRemark!}">
<input type="hidden" id="leaderFileURL" name="officeTaskManage.leaderFileURL" value="${officeTaskManage.leaderFileURL!}">

    <div class="mt-15" style="padding:0 0 5px 0;">
		<@htmlmacro.tableDetail>
		<tr>
			<th width="15%">任务名称：</th>
			<td width="85%" colspan="3">
				<input <#if isView>readonly="true" class="input-txt input-readonly fn-left"<#else>class="input-txt fn-left"</#if> id="taskName" name="officeTaskManage.taskName" value="${officeTaskManage.taskName!}" maxLength="100" notNull="true" style="width:700px;">
				<span class="fn-left c-orange mt-5 ml-10">*</span>
			</td>
		</tr>
		<tr>
			<th width="15%">负责人：</th>
			<td width="35%">
			<#if isView>
				<input readonly="true" class="input-txt input-readonly fn-left" id="dealUserName" name="officeTaskManage.dealUserName" value="${officeTaskManage.dealUserName!}" notNull="true" style="width:170px;">
				<span class="fn-left c-orange mt-5 ml-10">*</span>
			<#else>
			<@commonmacro.selectObject useCheckbox=false url="${request.contextPath}/office/taskManage/taskManage-getTeacherDataPopup.action" idObjectId="dealUserId" nameObjectId="dealUserName" width="450" otherParam="showLetterIndex=true">
				<input id="dealUserName" value="${officeTaskManage.dealUserName?default('')}" class="input-txt input-readonly fn-left" style="width:170px;" notNull="true" readonly="readonly">
	            <input id="dealUserId" name="dealUserId" value="${officeTaskManage.dealUserId?default('')}" type="hidden" >
	        	<span class="fn-left c-orange mt-5 ml-10">*</span>
	        </@commonmacro.selectObject>
	        </#if>
			</td>
			<th width="15%">规定完成时间：</th>
			<td width="35%">
			<#if isView>
				<@htmlmacro.datepicker class="input-txt input-readonly fn-left" readonly="true" name="completeTime" value="${((officeTaskManage.completeTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:170px;" notNull="true" maxlength="20"/>
				<span class="fn-left c-orange mt-5 ml-10">*</span>
			<#else>
				<@htmlmacro.datepicker name="completeTime" value="${((officeTaskManage.completeTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:170px;" notNull="true" maxlength="20"/>
				<span class="fn-left c-orange mt-5 ml-10">*</span>
			</#if>
			</td>
		</tr>	
		<tr>
			<#if isView>
				<@htmlmacro.tdt colspan="3" style="width:875px;" class="text-area fn-left input-readonly" readonly="true" msgName="备注" id="remark" name="officeTaskManage.remark" value="${(officeTaskManage.remark?default('')?trim)?if_exists}" maxLength="500"/>
			<#else>
				<@htmlmacro.tdt colspan="3" style="width:875px;" class="text-area fn-left" msgName="备注" id="remark" name="officeTaskManage.remark" value="${(officeTaskManage.remark?default('')?trim)?if_exists}" maxLength="500"/>
			</#if>
		</tr>
		<tr>
			<th width="15%">附件：</th>
			<td width="85%" colspan="3">
			<#if isView>
		 		<#if officeTaskManage.leaderFileName?exists>
			 		<div class="fn-left">
						<input id="uploadFilePath" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="${officeTaskManage.leaderFileName!}" maxLength="125"/>&nbsp;&nbsp;
					</div>
					<div class="mt-5">
			 		<div class="fn-rel fn-left" style="width:60px;overflow:hidden">	
						<span class="upload-span"><a href="javascript:download();" class="">
						<img alt="下载" src="${request.contextPath}/static/images/icon/download.png">
						</a></span>
					</div>
					</div>
				</#if>
		 	<#else>
		 		<div class="fn-left">
					<input id="uploadFilePath" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="${officeTaskManage.leaderFileName!}" maxLength="125"/>&nbsp;&nbsp;
				</div>
				<div class="mt-5">
		 		<div class="fn-rel fn-left" style="width:60px;overflow:hidden">	
		 			<span class="upload-span"><a href="javascript:void(0)" class="">选择文件</a></span>       
	     			<input  id="fileInput" name="fileInput" hidefocus='' type="file" onchange="doChange();" />
		 		</div>
		 		<div id="cleanFile" <#if officeTaskManage.leaderFileName?exists>style="display:display"<#else>style="display:none"</#if>>
		 			<span class="upload-span"><a href="javascript:deleteFile();" class="">清空</a></span>
		 		</div>
		 		</div>
	 		</#if>
			</td>
		</tr>		
		<tr>
			<th width="15%">第一次提醒时间：</th>
			<td width="35%">
			<#if isView>
				<@htmlmacro.datepicker class="input-txt input-readonly fn-left" readonly="true" name="firstRemindTime" value="${((officeTaskManage.firstRemindTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:170px;" notNull="true" maxlength="20"/>
				<span class="fn-left c-orange mt-5 ml-10">*</span>
			<#else>
				<@htmlmacro.datepicker name="firstRemindTime" value="${((officeTaskManage.firstRemindTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:170px;" notNull="true" maxlength="20"/>
				<span class="fn-left c-orange mt-5 ml-10">*</span>
			</#if>
			</td>
			<th width="15%">第二次提醒时间：</th>
			<td width="35%">
			<#if isView>
				<@htmlmacro.datepicker class="input-txt input-readonly fn-left" readonly="true" name="secondRemindTime" value="${((officeTaskManage.secondRemindTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:170px;" notNull="true" maxlength="20"/>
				<span class="fn-left c-orange mt-5 ml-10">*</span>
			<#else>
				<@htmlmacro.datepicker name="secondRemindTime" value="${((officeTaskManage.secondRemindTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:170px;" notNull="true" maxlength="20"/>
				<span class="fn-left c-orange mt-5 ml-10">*</span>
			</#if>
			</td>
		</tr>

		</@htmlmacro.tableDetail>
		

<p class="t-center pt-30">
	<#if isView>
		<a href="javascript:goBack();" class="abtn-blue-big">返回</a>
	<#else>
		<a href="javascript:doSave('1');" class="abtn-blue-big" id="btnSave">保存</a>
		<a href="javascript:doSave('2');" class="abtn-blue-big" id="btnSubmit">提交</a>
		<a href="javascript:goBack();" class="abtn-blue-big">取消</a>
	</#if>
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