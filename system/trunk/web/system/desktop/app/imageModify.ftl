<style>
.head_tip{ float:left; display:inline; width:165px; height:165px; margin-right:15px;};
</style>
<script>
	function reloadImage(){
		var tf = document.getElementById('uploadTempFile');
		if(tf && tf.value != ''){
			load("#mainContentContainer","${request.contextPath}/system/desktop/app/userInfo-imageModify-main.action?uploadNew=true&fileId="+tf.value,"","",true);
		} else {
			showMsgError("图片上传失败！");
		}
	}
	
	$(document).ready(function(){
		load('#mainContentContainer',"${request.contextPath}/system/desktop/app/userInfo-imageModify-main.action","","",true);
	})
</script>
<div id="mainContentContainer"></div>