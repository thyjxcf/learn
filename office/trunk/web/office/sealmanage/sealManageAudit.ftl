<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../sealmanage/archiveWebuploader.ftl" as archiveWebuploader>
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >
<@htmlmacro.moduleDiv titleName="用印审核">
	<div class="wrap pa-10">
	<form name="sealAuditForm" id="sealAuditForm" method="post">
	<input type="hidden" id="state" name="officeSeal.state" value=""> 
	<table border="0" cellspacing="0" cellpadding="0" class="table-form">
		<@htmlmacro.tableDetail divClass="table-form">
			<th colspan="4" style="text-align:center;">用印审核</th>
		    <tr>
		        <th style="width:10%">签章人：</th>
		        <td style="width:40%">
		        	${officeSeal.createUserName!}
		        </td>
		        <th style="width:10%">所属部门：</th>
		        <td style="width:40%">
		        	${officeSeal.deptName!}
		        </td>
		    </tr>
		    <tr>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>印章类型：</th>
		       <td style="width:20%">
		       		${officeSeal.sealName!}
  	  			</td>
		       <th style="width:20%">时间：</th>
		       <td>
		       		${(officeSeal.createTime?string('yyyy-MM-dd'))?if_exists}
		  	  </td>
		        
		    </tr>
		    <tr>
		        <th><span class="c-red">*</span>用印事由：</th>
            	<td colspan="3" style="word-break:break-all; word-wrap:break-word;">
            		<textarea id="applyOpinion"  notNull="true" class="text-area  mt-5 mb-5" readonly="true" rows="4" cols="69" style="width:470px;">${officeSeal.applyOpinion?default('')}</textarea>
            	</td>
		    </tr>
		    <tr id="auditOption">
		        <th><span class="c-red">*</span>审核意见：</th>
            	<td colspan="3" style="word-break:break-all; word-wrap:break-word;">
            		<textarea id="applyOpinion" name="officeSeal.auditOpinion" notNull="true"  msgName="审核意见" class="text-area  mt-5 mb-5" maxLength="200" rows="4" cols="69" style="width:470px;">${officeSeal.auditOpinion?default('')}</textarea>
            	</td>
		    </tr>
		    <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=false showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />
		    </@htmlmacro.tableDetail>
		    <@htmlmacro.tableDetail>
		    <tr>
		    	<td colspan="4" class="td-opt">
		    	    <a class="abtn-blue" href="javascript:void(0);" onclick="doPass();" id="btnSave">通过</a>
		    	    <a class="abtn-blue" href="javascript:void(0);" onclick="doUnPass();" id="btnSave">不通过</a>
		    	    <a class="abtn-blue reset ml-5" href="javascript:void(0);" onclick="back();">返回</a>
		        </td>
		    </tr>
		</@htmlmacro.tableDetail>
		</table>
	</form>
	<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
	</div>
</@htmlmacro.moduleDiv >
<script>
var isSubmit =false;
function doPass(){
	//$("#auditOption").css('display','none');
	$("#state").val("3");
	submitForm();
}

function doUnPass(){
	if(isSubmit){
		return;
	}
	$("#state").val("4");
	//$("#auditOption").css('display','');
	if(!checkAllValidate("#sealAuditForm")){
		return;
	}
	submitForm();
}

function submitForm(){
	var options = {
        url: '${request.contextPath}/office/sealmanage/sealmanage-sealManageSave.action?officeSealId=${officeSeal.id!}',
        success: showReply,
        dataType: 'json',
        clearForm: false,
        resetForm: false,
        type: 'post'
    };
    isSubmit = true;
    $('#sealAuditForm').ajaxSubmit(options);
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
		  	sealManage();
		});
		return;
	}
}
function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}

function back(){
	sealManage();
}
</script>