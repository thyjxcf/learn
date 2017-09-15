<script>
	var isSubmit = false;
	function saveBroadcast(){
		if (isSubmit){
			return ;
		}
		
		if(!checkAllValidate("#broadcastForm1")){
			return false;
		}
		
		isSubmit = true;
		var options = {
			   target :'#broadcastForm1',
		       url:'${request.contextPath}/system/desktop/app/broadcast-save.action', 
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post',
		       timeout : 3000,
		       success : showSuccess
		    };
		try{
			$('#broadcastForm1').ajaxSubmit(options);
		}catch(e){
			showMsgError('添加失败！');
			isSubmit = false;	
		}
	
	}
	
	function deleteBroadcast(id){
		$.getJSON("${request.contextPath}/system/desktop/app/externalApp-delete.action",{"externalAppId":id},function(data){
			//如果有错误信息（与action中对应），则给出提示
			if(data && data != ""){
				showMsgError(data);
				return;
			}else{
				load("#broadcastList","${request.contextPath}/system/desktop/app/broadcast-list.action");
				return;
			}
		}).error(function(){
			showMsgError("删除失败！");
		}); 
	}
	
	function closeBroadcast(){
		closeDiv("#broadcastDiv","load('#broadcast','${request.contextPath}/system/desktop/app/broadcast.action')");
	}
	
	function showSuccess(data) {
	   if(data.operateSuccess){
	  		load("#broadcastList","${request.contextPath}/system/desktop/app/broadcast-list.action");
		}else{
			showMsgError(data.errorMessage);
		}
		isSubmit = false;
	}
	
	$(document).ready(function(){
		load("#broadcastList","${request.contextPath}/system/desktop/app/broadcast-list.action");
	})
</script>
<form name="form1" id="broadcastForm1" method="post" enctype="multipart/form-data">
<p class="tt"><a href="javascript:void(0);" onclick="closeBroadcast();return false;" class="close">关闭</a>设置录播接入</p>
<div class="wrap" style="width:690px;height:390px;overflow-y:auto;">
    <ul class="fn-clear" id="broadcastList" style="height:220px;overflow-y:scroll">
    	
    </ul>
	<div class="form" style="display:none;">
    	<p><span>录播教室名称：</span><input type="text" id="appName" name="appName" class="input-txt" notNull="true" maxLength="32" style="width:320px;"><span style="color:red;width:15px">*</span>&nbsp;最多支持16个汉字(32个字符)</p>
    	<p><span>录播教室地址：</span><input type="text" id="appUrl" name="appUrl" class="input-txt" notNull="true" maxLength="1000" style="width:320px;"><span style="color:red;width:15px">*</span>&nbsp;(请以http://开始)</p>
        <p class="dd"><a href="javascript:void(0);" onclick="saveBroadcast();return false;" class="abtn-blue">保存</a></p>
    </div>
</div>
</form>