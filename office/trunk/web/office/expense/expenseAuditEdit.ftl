<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../expense/archiveWebuploader.ftl" as archiveWebuploader>
<@htmlmacro.moduleDiv titleName="报销审核">
<form name="expenseAuditForm" id="expenseAuditForm" method="post">
	<input id="id" name="officeExpense.id" value="${officeExpense.id!}" type="hidden" />
	<input id="unitId" name="officeExpense.unitId" value="${officeExpense.unitId!}" type="hidden" />
	<input id="applyUserId" name="officeExpense.applyUserId" value="${officeExpense.applyUserId!}" type="hidden" />
	<input type="hidden" name="taskHandlerSaveJson" id="taskHandlerSaveJson" value=""/>
	<@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;" class="p1 pt-10">报销审核</th>
	    </tr>
	    <tr>
	       <th style="width:20%"><span class="c-orange mr-5">*</span>报销金额（元）：</th>
	       <td style="width:30%">
	        	<input name="officeExpense.expenseMoney" id="expenseMoney" type="text" class="input-txt input-readonly" readonly="true" style="width:140px;" value="${(officeExpense.expenseMoney?string('0.00'))?if_exists}" msgName="报销金额" />
	       </td>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>报销类别：</th>
	        <td style="width:30%">
				<input type="text" id="expenseType" name="officeExpense.expenseType" notNull="true" msgName="报销类别" value="${officeExpense.expenseType!}" class="input-txt fn-left input-readonly" readonly="true" style="width:200px;" maxlength="30"/>
	        </td>
	    </tr>
	    <tr>
	        <th><span class="c-orange mr-5">*</span>费用明细：</th>
	        <td colspan="3">
	        	<textarea name="officeExpense.detail" id="detail" cols="70" rows="4" class="text-area my-5 input-readonly" readonly="true" style="width:80%;padding:5px 1%;height:50px;" msgName="费用明细" notNull="true" maxLength="250">${officeExpense.detail!}</textarea>
	        </td>
	    </tr>
	    <tr>
	        <th>提交时间：</th>
	        <td colspan="3">
	        	${(officeExpense.createTime?string('yyyy-MM-dd'))?if_exists}
	        </td>
	    </tr>
	    <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=false showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />
	</@htmlmacro.tableDetail>
    <br>
	<div class="fw-edit mt-10">
    	<p class="tt">审核意见</p>
        <div class="fw-item-wrap">
        	<#if (officeExpense.hisTaskList?size>0)>
        	<#list officeExpense.hisTaskList as item>
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
		                <#else>
		                <img name='imgPic' class="my-image-class" border='0' align='absmiddle'  onmouseover="style.cursor='hand'"
							src="<#if item.comment.downloadPath?default("") != "">${item.comment.downloadPath?default("")}<#else></#if>" >
		                </#if>
		                </div>
		                <p class="date">${((item.comment.operateTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}</p>
                    </div>
        	</#list>
        	<#elseif viewOnly>
        		<p class="no-data mt-50 mb-50">还没有数据哦！</p>
        	</#if>
        	<#if !viewOnly>
	        	<div class="fw-item fn-clear">
	                <p class="tit fn-clear">
	                	<span class="num">${officeExpense.hisTaskList?size+1}</span>
	                	<span class="pl-5">${officeExpense.taskName!}</span>
	            	</p>
	                <p class="name">负责人：${userName!}</p>
	                <div class="fn-clear"></div>
	                <p><textarea class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" cols="70" rows="4" name="textComment" id="textComment" maxLength="200"></textarea></p>
	            </div>
        	</#if>
        </div>
    </div>
	<p class="pt-20 t-center">
	<#if !viewOnly>
		<a class="abtn-blue-big" href="javascript:void(0)"  onclick="changeFlow()">修改当前流程</a>
		<#if canChangeNextTask>
			<a href="javascript:void(0)" class="abtn-blue-big" onclick="changeNextStepUserDiv()">修改下一步负责人</a>
		</#if>
	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doAudit('true');">审核通过</a>
	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doAudit('false');">审核不通过</a>
	</#if>
	<#if canBeRetract>
		<a href="javascript:void(0)" class="abtn-blue-big" onclick="retractFlow('${showReBackId!}','${taskKey!}','${reTaskId!}')">撤回</a>
	</#if>
	    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="goBack();">返回</a>
    </p>
</form>
<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
<div id="flowShow"  class="docReader my-20" style="height:660px;">
</div>
<div  id="classLayer" class="popUp-layer showSgParam" style="display:none;width:60%"></div>
<div id="nextStepLayer" style="display:none;width:980px;z-index:9997" class="popUp-layer "></div>
<script>
$(document).ready(function(){
	vselect();
    load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?id=${officeExpense.flowId!}&subsystemId=70&instanceType=instance&currentStepId=${currentStepId!}");
});
function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}
function goBack(){
	load("#adminDiv","${request.contextPath}/office/expense/expense-expenseAudit.action");
}
var taskHandlerSave = ${taskHandlerSaveJson!};
isSubmit = false;
function doAudit(state) {
 	if (isSubmit) {
        return;
    }
 	if(!checkAllValidate("#expenseAuditForm")){
		return;
	}
	var textComment=$("#textComment").val();
	if(state=='false' && (textComment==null||textComment=='')){
		showMsgWarn("请填写审核不通过的原因！");
	  		return;
	}

    taskHandlerSave.comment.textComment = $("#textComment").val();
    taskHandlerSave.comment.commentType = 1;
    $("#taskHandlerSaveJson").val(JSON.stringify(taskHandlerSave));
    var options = {
        url: '${request.contextPath}/office/expense/expense-expenseAudit-save.action?pass='+state+'&currentStepId=${currentStepId!}',
        success: showReply,
        dataType: 'json',
        clearForm: false,
        resetForm: false,
        type: 'post'
    };
    isSubmit = true;
    $('#expenseAuditForm').ajaxSubmit(options);
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
	var expenseId = "${officeExpense.id!}";
	openDiv("#classLayer","#classLayer .close,#classLayer .reset",
 	"${request.contextPath}/office/expense/expense-findCurrentstep.action?expenseId="+expenseId+"&taskId=${taskId!}", null, null ,null,function(){closeChange()},null);
}

function reload(id, taskId){
	load("#adminDiv", "${request.contextPath}/office/expense/expense-expenseAudit-edit.action?officeExpense.id="+id+"&taskId="+taskId+"&viewOnly=false");
}

function retractFlow(showReBackId, taskKey, reTaskId){
	$.getJSON("${request.contextPath}/office/expense/expense-retractFlow.action", {showReBackId:showReBackId,taskKey:taskKey,reTaskId:reTaskId}, function(data){
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
 		"${request.contextPath}/office/common/loadNextStepTaskLayer.action?taskId=${taskId!}&flowId=${officeExpense.flowId!}", null, null ,null,function(){vselect();},null);
}
</script>
</@htmlmacro.moduleDiv >