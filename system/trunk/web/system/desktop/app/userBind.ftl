<form method="post" name="skinform" id="skinform" enctype="multipart/form-data">
	<input type="hidden" name="bind.remoteUserId" id="id" value="${bind.remoteUserId!}">
	<input type="hidden" name="bind.userId" id="userId" value="${bind.userId!}">
	<input type="hidden" name="bind.remoteUsername" id="remoteUsername" value="${bind.remoteUsername?default('')}">
	<input type="hidden" name="bind.remotePwd" id="remotePwd" value="${bind.remotePwd!}">
	<div class="set-wrap" >
		<div class="t-center"><span style="font-size:20px"></span></div> 
    	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mt-10">
    		<tr>
                <th>平台账号：</th>
                <td><input id="uid" type="text" class="txt" value="${bind.remoteUsername?default('')}"></td>
            </tr>
        </table>
    </div>
    <p class="dd">
	    	<a href="javascript:void(0);" onclick="updateBind();return false;" class="abtn-blue submit">绑定</a>
	    	<#if bind.remoteUsername?default('') != ''><a href="javascript:void(0);" onclick="unbind();return false;" class="abtn-blue submit">解邦</a></#if>
	    	<a href="javascript:void(0);" onclick="closeDiv('#setLayer');return false;" class="abtn-gray reset ml-5">取消</a>
	</p>
</form>
<script>
$(document).ready(function(){
});

var isSubmit = false;
function updateBind(){
	if (isSubmit){
		return ;
	}
	isSubmit = true;
	
	if(!confirm('确定绑定该平台账号吗？')){
		isSubmit = false;
		return false;
	}
	if($("#uid").val()==''){
		showMsgError("账号不能为空");
		isSubmit = false;
		return false;
	}
	if($("#uid").val().length>50){
		showMsgError("账号长度不能超过50个字符");
		isSubmit = false;
		return false;
	}
	
	$('#remoteUsername').val($('#uid').val());
	var options = {
		   target :'#skinform',
	       url:'${request.contextPath}/common/open/apUserBind-save.action', 
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       success : showSuccess
	    };
	try{
		$('#skinform').ajaxSubmit(options);
	}catch(e){
		showMsgError('保存失败！');
		isSubmit = false;	
	}
	
}

function showSuccess(data) {
    if(!data){
		showMsgError('保存失败！');
	}else{
		if(data.operateSuccess){
			showMsgSuccess(data.promptMessage,'提示',function(){
				load('modifyUserBind','${request.contextPath}/common/open/apUserBind.action');
			});
		} else {
			showMsgError(data.errorMessage);
		}
	}
	isSubmit = false;
}

function unbind(){
	if (isSubmit){
		return ;
	}
	isSubmit = true;
	if(!confirm('确定解绑用户吗？')){
		isSubmit = false;
		return false;
	}
	$.post('${request.contextPath}/common/open/apUserBind-unbind.action',{'bind.remoteUserId':'${bind.remoteUserId!}'},function(data){
		showSuccess(data);
	});
}
</script>