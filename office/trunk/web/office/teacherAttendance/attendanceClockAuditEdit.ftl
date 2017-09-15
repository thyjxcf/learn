<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
function doSave(){
	if(!isActionable("#btnSave")){
		return;
	}
	if(!checkAllValidate("#contentAuditDiv")){
		return;
	}
	if(!confirm("您确定要不通过该申请？")){
		return;
	}
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/teacherAttendance/teacherAttendance-auditIsPassApply.action?pass=false', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#editform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSave").attr("class", "abtn-blue");
	if(!data.operateSuccess){
	   if(data.errorMessage!=null&&data.errorMessage!=""){
		   showMsgError(data.errorMessage);
		   return;
	   }
	}else{
		showMsgSuccess(data.promptMessage,"",function(){
			closeDiv("#classLayer3");
		  	load("#contentDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-auditList.action");
		});
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>补卡审批</span></p>
<div class="wrap pa-10" id="contentAuditDiv">
<form id="editform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    <input type="hidden" id="taskId" name="taskId" value="${taskId?default('')}">
    <input type="hidden" id="id" name="id" value="${id?default('')}">
	<tr>
    	<th class="pt-10" style="width:20%"><span class="c-orange mt-5 mr-5">*</span>审核意见：</th>
        <td class="pt-10" style="width:80%">
        	<textarea class="text-area my-5" id="auditComment" name="auditComment" maxlength="1000" notNull="true" msgName="审核意见" style="width:90%;padding:5px 1%;height:50px;"></textarea>
        </td>
    </tr>
	</table>
</form>    
</div>
<p class="dd">
    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave();" id="btnSave">确定</a>
    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>
</@htmlmacro.moduleDiv>