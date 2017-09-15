<script>
	var isSubmit = false;
	function saveExternalApp(){
		if (isSubmit){
			return ;
		}
		<#if systemDeploySchool =='nbzx'>
		//判断当前已经有的个数
		if($('#appListId .externalAppList').length >=3){
			showMsgError('最多支持添加3个应用！');
			return false;
		}
		</#if>
		
		if(!checkAllValidate("#appForm1")){
			return false;
		}
		
		var elem = document.getElementById("appPic");	
	   	if(elem.value !="" ){
		   	if(!checkPicFile(elem,1024)){
	   			return false;
	   		}
	   	}else{
	   		showMsgError('请上传图标！');
	   		return false;
	   	}
		
		isSubmit = true;
		var options = {
			   target :'#appForm1',
		       url:'${request.contextPath}/system/desktop/app/externalApp-save.action', 
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post',
		       timeout : 3000,
		       success : showSuccess
		    };
		try{
			$('#appForm1').ajaxSubmit(options);
		}catch(e){
			showMsgError('添加失败！');
			isSubmit = false;	
		}
	}
	
	function showSuccess(data) {
	   if(data.operateSuccess){
			load("#appListId","${request.contextPath}/system/desktop/app/externalApp-list.action?externalType=${externalType!}");
		}else{
			showMsgError(data.errorMessage);
		}
		isSubmit = false;
	}
	
	function assembleAppData(data){
		$("#appListId").html("");
		var html="";
		$.each(data.externalAppList,function(j,app){
	   		html+="<li><i title='移除'></i><a href='javascript:void(0);' onclick='deleteApp('"+app.id+"')'><img src='' alt='"+app.appName+"'><span>"+app.appName+"</span></a></li>";
		});	
		html+="<li><a href='javascript:void(0);' class='add'><img src='${request.contextPath}/static/images/app/desk_icon/add.png' alt='添加'><span>添加</span></a></li>";
		$("#appListId").html(html);
	}
	
	function deleteExternalApp(id){
		$.getJSON("${request.contextPath}/system/desktop/app/externalApp-delete.action",{"externalAppId":id},function(data){
			//如果有错误信息（与action中对应），则给出提示
			if(data && data != ""){
				showMsgError(data);
				return;
			}else{
				load("#appListId","${request.contextPath}/system/desktop/app/externalApp-list.action?externalType=${externalType!}");
				return;
			}
		}).error(function(){
			showMsgError("删除失败！");
		}); 
	}

	function closeExternalApp(){
		<#if divId! =="">
			closeDiv("#addSystem","load('#externalApp','${request.contextPath}/system/desktop/app/externalApp.action')");
		<#else>
			closeDiv("#addSystem","load('#${divId}','${request.contextPath}/system/desktop/app/subsystemApp.action?sortType=${externalType!}&divId=${divId!}')");
		</#if>
	}
	
	$(document).ready(function(){
		load("#appListId","${request.contextPath}/system/desktop/app/externalApp-list.action?externalType=${externalType!}");
	})
</script>
<form name="form1" id="appForm1" method="post" enctype="multipart/form-data">
<p class="tt" style="width:670px;"><a href="javascript:void(0);" onclick="closeExternalApp();return false;" class="close">关闭</a><#if externalAppName! !="">${externalAppName!}<#else><#if systemDeploySchool =='nbzx'>外部链接设置(最多支持添加3个应用)<#elseif systemDeploySchool! =='xinjiang'>管理服务<#elseif systemDeploySchool! =='hzzc'>信息化建设<#elseif systemDeploySchool! =='hdjy'>智慧教育服务应用<#else>数字化对接</#if></#if></p>
<#if systemDeploySchool =='nbzx'>
<div class="wrap" style="width:690px;height:370px;overflow-y:auto;">
<#else>
<div class="wrap" style="width:690px;height:470px;overflow-y:auto;">
</#if>
    <ul class="fn-clear" id="appListId" style="height:220px;position:relative;overflow-y:scroll">
    	
    </ul>
    <div class="form" style="display:none;">
    	<input type="hidden" id="type" name="type" value="${externalType?default('1')}"/>
    	<p><span>应用名称：</span><input type="text" id="appName" name="appName" class="input-txt" notNull="true" maxLength="16" style="width:150px;"><span style="color:red;width:15px">*</span>&nbsp;最多支持8个汉字(16个字符)</p>
    	<p><span>应用地址：</span><input type="text" id="appUrl" name="appUrl" class="input-txt" notNull="true" maxLength="1000" style="width:320px;"><span style="color:red;width:15px">*</span>&nbsp;(请以http://开始)</p>
    	<p><span>应用排序：</span><input type="text" id="orderNo" name="orderNo" class="input-txt" notNull="true" maxLength="2" dataType="integer" nonnegative="true" style="width:50px;"><span style="color:red;width:15px">*</span></p>
    	<p><span>应用图标：</span><input name="appPic" id="appPic" type="file" class="input-txt" style="width:340px;"><span style="color:red;width:15px">*</span></p>
        <p class="dd"><a href="javascript:void(0);" onclick="saveExternalApp();return false;" class="abtn-blue">保存</a></p>
    </div>
</div>
</form>