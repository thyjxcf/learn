<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="请假类型维护">
	<div id="leaveTypeContainer">
	<form action="" method="post" id="leaveTypeForm" name="leaveTypeForm">
	<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>负责人设置</span></p>	
	<div class="wrap pa-10" id="contentDiv">
	<table class="table-form" cellpadding="0" border="0" cellspacing="0">
		<th>负责人：</th>
		<td>
		<@commonmacro.selectOneUser idObjectId="userId" nameObjectId="userName" width=400 height=300 callback="userSet">
			<input type="hidden" id="userId" name="lead.userId" value="${lead.userId!}"/> 
			<input type="text" id="userName" name="lead.userName" notNull="true" msgName="申请人" value="${lead.userName!}" class="input-txt fn-left" style="width:200px;" readonly="readonly"/>
		</@commonmacro.selectOneUser>
		</td>
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
			url:'${request.contextPath}/office/studentLeave/studentLeave-saveLeaveType.action',
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
			showMsgSuccess("保存成功","提示",function(){
				$("btnSave").attr("class","abtn-blue-big");
				load("#studentAdminDiv","${request.contextPath}/office/studentLeave/studentLeave-leaveType.action");
			});
		}
	}
</script>
</@common.moduleDiv>