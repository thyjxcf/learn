<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="请假类型维护">
	<div id="leaveTypeContainer">
	<form action="" method="post" id="leaveTypeForm" name="leaveTypeForm">
	<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>请假类型维护</span></p>	
	<div class="wrap pa-10" id="contentDiv">
	<table class="table-form" cellpadding="0" border="0" cellspacing="0">
		<input type="hidden" name="officeLeaveType.id" value="${officeLeaveType.id!}">
		<tr>
			<th>请假类型名称</th>
			<td style="text-align:center;">
				<input class="input-txt" style="width:140px;" notNull="true" name="officeLeaveType.name" value="${officeLeaveType.name!}" maxLength="20">
			</td>
		</tr>
	</table>
	</div>
	<p class="dd">
	    <a class="abtn-blue doSave" href="javascript:void(0);" onclick="doSave();" id="btnSave">保存</a>
	    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
	</p>
	</form>
	</div>
<script>
	function doSave(){
		if(!isActionable("#btnSave")){
			return false;
		}
		if(!checkAllValidate("#leaveTypeContainer")){
			return;
		}
		$("#btnSave").attr("class","abtn-unable");
		
		var options={
			url:'${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-saveLeaveType.action',
			dataType:'json',
			clearForm:false,
			resetForm:false,
			type:'post',
			success:showReply
		};
		$("#leaveTypeForm").ajaxSubmit(options);
	}
	
	function showReply(data){
		if(!data.operateSuccess){
			showMsgError(data.promptMessage);
			$("#btnSave").attr("class","abtn-blue");
			return;
		}else{
			showMsgSuccess("保存成功","提示",leaveTypeList);
		}
	}
</script>
</@common.moduleDiv>