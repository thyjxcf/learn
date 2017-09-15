<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<#include "/common/handlefielderror.ftl">
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../businesstrip/archiveWebuploader.ftl" as archiveWebuploader>
	<br>
    <@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;">教师出差</th>
	    </tr>
	    <tr>
	    	 <th><span class="c-orange mr-5">*</span>申请人：</th>
		       <td colspan="3">
		        	<input type="text"  class="input-txt fn-left input-readonly" id="place" readonly="true" name="officeBusinessTrip.applyUserName" maxlength="50" notNull="true" value="${officeBusinessTrip.applyUserName!}" style="width:136px;">
		        </td>
	    </tr>	
	    <tr>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>开始时间：</th>
		        <td style="width:30%">
		        	<@htmlmacro.datepicker name="officeBusinessTrip.beginTime" id="beginTime" class="input-txt" style="width:39%;" msgName="开始时间" readonly="true" notNull="true" value="${((officeBusinessTrip.beginTime)?string('yyyy-MM-dd'))?if_exists}" />
		        </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>结束时间：</th>
		        <td style="width:30%">
		        	<@htmlmacro.datepicker name="officeBusinessTrip.endTime" id="endTime" class="input-txt" style="width:39%;" msgName="结束时间" readonly="true" notNull="true" value="${((officeBusinessTrip.endTime)?string('yyyy-MM-dd'))?if_exists}" />
		        </td>
		    </tr>
		    <tr>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>出差天数：</th>
		       <td style="width:30%">
		        	<input name="officeBusinessTrip.days" id="days" type="text" class="input-txt fn-left input-readonly" readonly="true" style="width:136px;" maxlength="5" dataType="float" maxValue="999" minValue="0.1" decimalLength="1" value="${(officeBusinessTrip.days?string('0.#'))?if_exists}" msgName="出差天数" notNull="true" />
		       </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>出差地点：</th>
		        <td style="width:30%">
		        	<input type="text" msgName="出差地点" class="input-txt fn-left input-readonly" id="place" readonly="true" name="officeBusinessTrip.place" maxlength="50" notNull="true" value="${officeBusinessTrip.place!}" style="width:136px;">
		        </td>
		    </tr>
		    <tr>
		        <th><span class="c-orange mr-5">*</span>出差事由：</th>
		        <td colspan="3">
		        	<textarea name="officeBusinessTrip.tripReason" id="tripReason" cols="70" rows="4"  readonly="true" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="出差事由" notNull="true" maxLength="100">${officeBusinessTrip.tripReason!}</textarea>
		        </td>
		    </tr>
		    <tr>
		        <th>提交时间：</th>
		        <td colspan="3">
		        	${(officeBusinessTrip.createTime?string('yyyy-MM-dd'))?if_exists}
		        </td>
		    </tr>
	     <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=false showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />
	</@htmlmacro.tableDetail>
    <#if officeBusinessTrip.flowId?default('')!='1'>
    <br>
	<div class="fw-edit mt-10">
    	<p class="tt">审核意见</p>
        <div class="fw-item-wrap">
        	<#if (officeBusinessTrip.hisTaskList?size>0)>
        	<#list officeBusinessTrip.hisTaskList as item>
        		<div class="fw-item fn-clear">
                    <p class="tit fn-clear">
                        <span class="num">${item_index+1}</span>
                        <span class="pl-5">${item.taskName!}</span>
                    </p>
                    <p class="name">负责人：${item.assigneeName!}</p>
                    <div class="fn-clear"></div>
                    <div class="des" >
						<#if item.comment.commentType==1>
						${item.comment.textComment!}
		                </#if>
	                </div>
	                <p class="date">${((item.comment.operateTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}</p>
                </div>
        	</#list>
        	<#else>
        	</#if>
        	<div class="fw-item fn-clear">
                <p class="tit fn-clear">
                	<span class="num">${officeBusinessTrip.hisTaskList?size+1}</span>
                	<span class="pl-5">${officeBusinessTrip.taskName!}</span>
            	</p>
                <p class="name">负责人：${officeBusinessTrip.applyUserName!}</p>
                <div class="fn-clear"></div>
                <p><textarea class="txt" name="textComment" id="textComment" maxLength="200"></textarea></p>
            </div>
        </div>
    </div>
    </#if>
	<p class="pt-20 t-center">
		<a href="javascript:void(0)" class="abtn-blue" onclick="changeFlow()">修改当前流程</a>
		<#if canChangeNextTask>
			<a href="javascript:void(0)" class="abtn-blue" onclick="changeNextStepUserDiv()">修改下一步负责人</a>
		</#if>
		<a href="javascript:void(0)" class="abtn-blue" onclick="passFlow()">审核通过</a>
		<a href="javascript:void(0)" class="abtn-blue" onclick="unpassFlow()">审核不通过</a>
		<#if canBeRetract>
			<a href="javascript:void(0)" class="abtn-blue" onclick="retractFlow('${showReBackId!}','${taskKey!}','${reTaskId!}')">撤回</a>
		</#if>
    	<a href="javascript:void(0)" class="abtn-blue" onclick="goBack()">返回</a>
	</p>
	<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
	<form name="businessTripForm" id="businessTripForm" method="post">
		<input type="hidden" name="taskHandlerSaveJson" id="taskHandlerSaveJson" value=""/>
		<input type="hidden" name="pass" id="pass" value=""/>
	</form>
	<div id="flowShow" class="docReader my-20" style="height:660px;">
	</div>
	<div  id="classLayer" class="popUp-layer showSgParam" style="display:none;width:60%"></div>
	<div id="nextStepLayer" style="display:none;width:980px;z-index:9997" class="popUp-layer "></div>
<script>
$(document).ready(function(){
	vselect();
    load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?id=${flowId!}&subsystemId=70&instanceType=instance&currentStepId=${currentStepId!}");
});
function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}
function goBack(){
	load("#businessTripeDiv", "${request.contextPath}/office/businesstrip/businessTrip-businessTripList.action?states="+"0");
}
var taskHandlerSave = ${taskHandlerSaveJson!};
isSubmit = false;
function passFlow() {
 	if (isSubmit) {
        return;
    }
 	if(!checkAllValidate("#businessTripForm")){
		return;
	}
	$("#pass").val('true');
	submitForm();
}
function unpassFlow(){
	if (isSubmit) {
        return;
    }
    if(!checkAllValidate("#businessTripForm")){
		return;
	}
	var textComment=$("#textComment").val();
	if(textComment==null||textComment==''){
		showMsgWarn("请填写审核不通过的原因！");
	  		return;
	}
	$("#pass").val('false');
	submitForm();
}
function submitForm() {
    taskHandlerSave.comment.textComment = $("#textComment").val();
    taskHandlerSave.comment.commentType = 1;
    $("#taskHandlerSaveJson").val(JSON.stringify(taskHandlerSave));
    var options = {
        url: '${request.contextPath}/office/businesstrip/businessTrip-businessTripPass.action?officeBusinessTrip.id=${officeBusinessTrip.id!}&currentStepId=${currentStepId!}',
        success: showReply,
        dataType: 'json',
        clearForm: false,
        resetForm: false,
        type: 'post'
    };
    isSubmit = true;
    $('#businessTripForm').ajaxSubmit(options);
}

function showReply(data){
	if(!data.operateSuccess){
	   if(data.errorMessage!=null&&data.errorMessage!=""){
		   showMsgError(data.errorMessage);
		   isSubmit = false;
		   return;
	   }
	}else{
		showMsgSuccess(data.promptMessage,"",function(){
		  	goBack();
		});
		return;
	}
}
function closeChange(){
	closeDiv("#classLayer");
}

function changeFlow(){
	var businessTripId = "${officeBusinessTrip.id!}";
	openDiv("#classLayer","#classLayer .close,#classLayer .reset",
 	"${request.contextPath}/office/businesstrip/businessTrip-findCurrentstep.action?businessTripId="+businessTripId+"&taskId=${taskId!}", null, null ,null,function(){closeChange()},null);
}

function reload(id, taskId){												
	load("#businessTripeDiv","${request.contextPath}/office/businesstrip/businessTrip-businessTripAudit.action?officeBusinessTrip.id="+id+"&taskId="+taskId);
}

function retractFlow(showReBackId, taskKey, reTaskId){
	$.getJSON("${request.contextPath}/office/businesstrip/businessTrip-retractFlow.action", {showReBackId:showReBackId,taskKey:taskKey,reTaskId:reTaskId}, function(data){
		if (!data.operateSuccess) {
	        if (data.errorMessage != null && data.errorMessage != "") {
	            showMsgError(data.errorMessage);
	            return;
	        }
	    } else {
	    	showMsgSuccess("撤回成功", "", goBack);
	        return;
	    }
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(errorThrown);});
}

function changeNextStepUserDiv(){
	openDiv("#nextStepLayer","#nextStepLayer .close,#nextStepLayer .reset",
 		"${request.contextPath}/office/common/loadNextStepTaskLayer.action?taskId=${taskId!}&flowId=${officeBusinessTrip.flowId!}", null, null ,null,function(){vselect();},null);
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
