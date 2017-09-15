<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="教师请假查看">
	<input id="id" name="officeTeacherLeaveNh.id" value="${officeTeacherLeaveNh.id!}" type="hidden" />
	<input id="flowType" name="officeTeacherLeaveNh.flowType" value="${officeTeacherLeaveNh.flowType!}" type="hidden" />
	<@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;">教师请假查看</th>
	    </tr>
	    <tr>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>请假时间：</th>
	        <td colspan="3" >
	        	${((officeTeacherLeaveNh.beginTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}
	        	至
	        	${((officeTeacherLeaveNh.endTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}
	        </td>
	    </tr>
	    <tr>
	       <th style="width:20%"><span class="c-orange mr-5">*</span>共计天数：</th>
	       <td style="width:30%">
	        	${(officeTeacherLeaveNh.days?string('0.#'))?if_exists}
	       </td>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>申请人：</th>
	        <td style="width:30%">
	        	${officeTeacherLeaveNh.userName!}
	        </td>
	    </tr>
	     <tr>
	        <th style="width:20%">请假类型：</th>
	        <td colspan="3" style="width:80%">
        	 	${officeTeacherLeaveNh.leaveTypeName!}
	        </td>
	    </tr>
	    <tr>
	        <th style="width:20%">早自修调换人：</th>
	        <td style="width:30%">
	        	${officeTeacherLeaveNh.morningChange!}
	        </td>
	        <th style="width:20%">晚自修调换人：</th>
	        <td style="width:30%">
	        	${officeTeacherLeaveNh.nightChange!}
	        </td>
	    </tr>
	    <tr>
	        <th style="width:20%">值周调换人：</th>
	        <td style="width:30%">
	        	${officeTeacherLeaveNh.weekChange!}
	        </td>
	        <th style="width:20%">代理班主任：</th>
	        <td style="width:30%">
	        	${officeTeacherLeaveNh.actChargeTeacher!}
	        </td>
	    </tr>
	    <tr>
	        <th>备注：</th>
	        <td colspan="3">
	        	${officeTeacherLeaveNh.remark!}
	        </td>
	    </tr>
	    <tr>
	    	<td colspan="4" class="td-opt">
			    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="doSearch();">返回</a>
	        </td>
	    </tr>
	</@htmlmacro.tableDetail>
	<#if officeTeacherLeaveNh.flowId?default('')!='1'>
    <br>
	<div class="fw-edit mt-10">
    	<p class="tt">审核意见</p>
        <div class="fw-item-wrap">
        	<#if (officeTeacherLeaveNh.hisTaskList?size>0)>
        	<#list officeTeacherLeaveNh.hisTaskList as item>
        		<div class="fw-item fn-clear">
	        		<div class="tit">
	                	<p class="num">${item_index+1}</p>
	                	<p>${item.taskName!}</p>
	                </div>
	                <div class="des fn-clear">
	                    <p class="name"><span>${((item.comment.operateTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}</span>${item.assigneeName!}</p>
	                    <p ><#if item.comment.commentType==1>${item.comment.textComment!}<#else></#if></p>
	                </div>
	        	</div>
        	</#list>
        	<#else>
        	</#if>
        	<div class="fw-item fw-item-last fn-clear">
        		<div class="tit">
                	<p class="num">${officeTeacherLeaveNh.hisTaskList?size+1}</p>
                	<p>${officeTeacherLeaveNh.taskName!}</p>
                </div>
                <div class="des fn-clear">
                    <p class="name">负责人：${userName!}</p>
                    <p><textarea class="txt" name="textComment" id="textComment" maxLength="200"></textarea></p>
                </div>
        	</div>
        </div>
    </div>
    </#if>
	<p class="pt-20 t-center">
		<a href="javascript:void(0)" class="abtn-blue" onclick="passFlow()">审核通过</a>
		<a href="javascript:void(0)" class="abtn-blue" onclick="unpassFlow()">审核不通过</a>
    	<a href="javascript:void(0)" class="abtn-blue" onclick="doSearch()">返回</a>
	</p>
	<form name="teacherLeaveForm" id="teacherLeaveForm" method="post">
		<input type="hidden" name="taskHandlerSaveJson" id="taskHandlerSaveJson" value=""/>
		<input type="hidden" name="pass" id="pass" value=""/>
	</form>
	<div id="flowShow" class="docReader my-20" style="height:660px;">
	</div>
<script>
$(document).ready(function(){
	vselect();
    load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?id=${officeTeacherLeaveNh.flowId!}&subsystemId=70&instanceType=instance&currentStepId=${currentStepId!}");
});
var taskHandlerSave = ${taskHandlerSaveJson!};
isSubmit = false;
function passFlow() {
 	if (isSubmit) {
        return;
    }
 	if(!checkAllValidate("#teacherLeaveForm")){
		return;
	}
	$("#pass").val('true');
	submitForm();
}
function unpassFlow(){
	if (isSubmit) {
        return;
    }
    if(!checkAllValidate("#teacherLeaveForm")){
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
        url: '${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-saveTeacherLeaveNhAudit.action?teacherLeaveNhId=${officeTeacherLeaveNh.id!}',
        success: showReply,
        dataType: 'json',
        clearForm: false,
        resetForm: false,
        type: 'post'
    };
    isSubmit = true;
    $('#teacherLeaveForm').ajaxSubmit(options);
}

function showReply(data){
	if(!data.operateSuccess){
	   if(data.errorMessage!=null&&data.errorMessage!=""){
		   showMsgError(data.errorMessage);
		   isSubmit = false;
		   return;
	   }
	}else{
		showMsgSuccess(data.promptMessage,"",doSearch);
		return;
	}
}

</script>
</@htmlmacro.moduleDiv>