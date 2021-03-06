<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "../jtgooutmanage/archiveWebuploader.ftl" as archiveWebuploader>
<@htmlmacro.moduleDiv titleName="">
<#include "/common/handlefielderror.ftl">
<#import "/common/commonmacro.ftl" as commonmacro>
	<br>
    <@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;">集体外出</th>
	    </tr>
	    <tr>
	        <th  ><span class="c-orange mr-5">*</span>外出时间：</th>
	        <td colspan="3" >
	        ${((officeJtGoout.startTime)?string('yyyy-MM-dd'))?if_exists}至${((officeJtGoout.endTime)?string('yyyy-MM-dd'))?if_exists}
	        </td>
	    </tr>
	    <tr>
	       <th style="width:20%"><span class="c-orange mr-5">*</span>外出类型：</th>
	       <td>
		       <#if officeJtGoout.type?default('')=='1'>学生集体活动</#if>
			   <#if officeJtGoout.type?default('')=='2'>教师培训</#if>
		   </td>
	    </tr>
	    
	    <#if officeJtGoout.type?default('')=='1'>
	    
	     <tr>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>组织活动的年级或班级：</th>
		        <td style="width:30%">
		        	${gooutStudentEx.organize!}
		        </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>活动人数：</th>
		        <td style="width:30%">
		        	${gooutStudentEx.activityNumber!}
		        </td>
	    </tr>
	     <tr>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>活动地点：</th>
		       <td style="width:30%"  colspan="3">
		        	${gooutStudentEx.place!}
		      </td>
	    </tr>
	    <tr>
	        <th><span class="c-orange mr-5">*</span>活动内容：</th>
		    <td colspan="3">
		       <textarea name="gooutStudentEx.content" id="content" cols="70" rows="4" class="text-area my-5" readonly="true" style="width:80%;padding:5px 1%;height:50px;" msgName="活动内容" notNull="true" maxLength="200">${gooutStudentEx.content!}</textarea>
		    </td>
	    </tr>
	     <tr>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>交通工具：</th>
		       <td style="width:30%">
		        	${gooutStudentEx.vehicle!}
		       </td>
		       <th style="width:10%"><span class="c-orange mr-5">*</span>是否有营运证：</th>
		        <td style="width:40%">
                <#if !gooutStudentEx.isDrivinglicence?default(false)>无营运证</#if>
                <#if gooutStudentEx.isDrivinglicence?default(false)>有营运证</#if>
                </td>
	    </tr>
	    <tr class="studenthid">
		       <th style="width:10%"><span class="c-orange mr-5">*</span>是否由旅行社组织：</th>
		        <td style="width:40%">
                <#if !gooutStudentEx.isOrganization?default(false)>否</#if>
                <#if gooutStudentEx.isOrganization?default(false)>是</#if>
                </td>
                <#if gooutStudentEx.isOrganization?default(false)>
		       <th style="width:20%" class="lxsunit"><span class="c-orange mr-5">*</span>旅行社单位：</th>
		       <td style="width:30%" colspan="3" class="lxsunit">
		        	${gooutStudentEx.traveUnit!}
		       </td>
		       </#if>
		    </tr>
		    <#if gooutStudentEx.isOrganization?default(false)>
		    <tr class="studenthid lxsunit">
		       <th style="width:20%"><span class="c-orange mr-5">*</span>旅行社联系人：</th>
		       <td style="width:30%">
		        	${gooutStudentEx.traveLinkPerson!}
		       </td>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>旅行社联系人手机号：</th>
		       <td style="width:30%">
		        	${gooutStudentEx.traveLinkPhone!}
		       </td>
		    </tr>
		    </#if>
		    <tr class="studenthid">
		       <th style="width:10%"><span class="c-orange mr-5">*</span>是否购买人身保险和意外伤害保险：</th>
		        <td style="width:40%" colspan="3">
                <#if !gooutStudentEx.isInsurance?default(false)>未入</#if>
                <#if gooutStudentEx.isInsurance?default(false)>已入</#if>
                </td>
                
		    </tr>
		    <tr class="studenthid">
		    	<th style="width:20%"><span class="c-orange mr-5">*</span>活动负责人：</th>
		        <td style="width:30%">
		        	${gooutStudentEx.activityLeaderName!}
		        </td>
                <th style="width:20%"><span class="c-orange mr-5">*</span>活动负责人手机号：</th>
		        <td style="width:30%">
					${gooutStudentEx.activityLeaderPhone!}
		        </td>
                
		    </tr>
		    <tr class="studenthid">
		    	<th style="width:20%"><span class="c-orange mr-5">*</span>带队老师：</th>
		        <td style="width:30%">
					${gooutStudentEx.leadGroupName!}
		        </td>
		    	<th style="width:20%"><span class="c-orange mr-5">*</span>带队老师手机号：</th>
		        <td style="width:30%">
					${gooutStudentEx.leadGroupPhone!}
		        </td>
		    </tr>
		    <tr class="studenthid">
		       <th>其他老师：</th>
		        <td colspan="3">
		        	${gooutStudentEx.otherTeacherNames!}
		        </td>
		    </tr>
		    <tr class="studenthid">
		       <th style="width:10%"><span class="c-orange mr-5">*</span>活动方案：</th>
		        <td style="width:40%">
                <#if !gooutStudentEx.activityIdeal?default(false)>无活动方案</#if>
                <#if gooutStudentEx.activityIdeal?default(false)>有活动方案</#if>
                </td>
		       <th style="width:10%"><span class="c-orange mr-5">*</span>安全方案：</th>
		        <td style="width:40%">
                <#if !gooutStudentEx.saftIdeal?default(false)>无安全方案</#if>
                <#if gooutStudentEx.saftIdeal?default(false)>有安全方案</#if>
                </td>
		    </tr>
	    	
	    	<#elseif officeJtGoout.type?default('')=='2'>
	    	
	    	<tr class="teacherhid">
		        <th><span class="c-orange mr-5">*</span>外出内容：</th>
		        <td colspan="3">
		        	<textarea name="gooutTeacherEx.content" id="contents" cols="70" readonly="true" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="外出内容" maxLength="200">${gooutTeacherEx.content!}</textarea>
		        </td>
		    </tr>
		    <tr class="teacherhid">
		       <th>参与人员：</th>
		        <td colspan="3">
		        	${gooutTeacherEx.partakePersonNames!}
		        </td>
		    </tr>	
	    	</#if>
	    	<tr>
		        <th>提交时间：</th>
		        <td colspan="3">
		        	${(officeJtGoout.createTime?string('yyyy-MM-dd'))?if_exists}
		        </td>
		    </tr>
	<@archiveWebuploader.archiveWebuploaderEditViewer canEdit=false showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />   
	</@htmlmacro.tableDetail>
    <#if officeJtGoout.flowId?default('')!='1'>
    <br>
	<div class="fw-edit mt-10">
    	<p class="tt">审核意见</p>
        <div class="fw-item-wrap">
        	<#if (officeJtGoout.hisTaskList?size>0)>
        	<#list officeJtGoout.hisTaskList as item>
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
        	</#if>
        	<div class="fw-item fn-clear">
                <p class="tit fn-clear">
                	<span class="num">${officeJtGoout.hisTaskList?size+1}</span>
                	<span class="pl-5">${officeJtGoout.taskName!}</span>
            	</p>
                <p class="name">负责人：${userName!}</p>
                <div class="fn-clear"></div>
                <p><textarea class="txt" name="textComment" id="textComment" maxLength="200">${textComment!}</textarea></p>
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
	<form name="jtGoOutForm" id="jtGoOutForm" method="post">
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

function goBack(){
	load("#goOutDiv","${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutAuditList.action");
}
var taskHandlerSave = ${taskHandlerSaveJson!};
isSubmit = false;
function passFlow() {
 	if (isSubmit) {
        return;
    }
 	if(!checkAllValidate("#jtGoOutForm")){
		return;
	}
	$("#pass").val('true');
	submitForm();
}
function unpassFlow(){
	if (isSubmit) {
        return;
    }
    if(!checkAllValidate("#jtGoOutForm")){
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
        url: '${request.contextPath}/office/jtgooutmanage/jtgooutmanage-auditPassJtGoOut.action?jtGoOutId=${officeJtGoout.id!}&currentStepId=${currentStepId!}',
        success: showReply,
        dataType: 'json',
        clearForm: false,
        resetForm: false,
        type: 'post'
    };
    isSubmit = true;
    $('#jtGoOutForm').ajaxSubmit(options);
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

function changeFlow(){
	openDiv("#classLayer","#classLayer .close,#classLayer .reset",
 	"${request.contextPath}/office/jtgooutmanage/jtgooutmanage-findCurrentstep.action?jtGoOutId=${officeJtGoout.id!}&taskId=${taskId!}", null, null ,null,function(){closeChange()},null);
}

function reload(id, taskId){
	load("#goOutDiv","${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutAuditEdit.action?jtGoOutId="+id+"&taskId="+taskId);
}

function retractFlow(showReBackId, taskKey, reTaskId){
	$.getJSON("${request.contextPath}/office/jtgooutmanage/jtgooutmanage-retractFlow.action", {showReBackId:showReBackId,taskKey:taskKey,reTaskId:reTaskId}, function(data){
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
 		"${request.contextPath}/office/common/loadNextStepTaskLayer.action?taskId=${taskId!}&flowId=${officeJtGoout.flowId!}", null, null ,null,function(){vselect();},null);
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
