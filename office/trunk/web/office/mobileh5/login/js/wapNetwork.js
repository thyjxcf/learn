var wapNetwork = {
	
	//登录
	doLogin : function(name,pwd){
		showLoading("正在登录……");
		var options = {
				url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/loginH5/appH5Login.action",
				success : function(data){
					closeMsgTip();
					if(data.result==Constants.SUCCESS){
						storage.set(Constants.UNIT_ID, data.unitId);
						storage.set(Constants.USER_ID, data.userId);
						storage.set(Constants.USER_REALNAME, data.userName);
						storageLocal.set(WapConstants.LOGIN_USER_NAME, name);
						storageLocal.set(WapConstants.LOGIN_PASS_WORD, pwd);
//						location.href = storage.get(Constants.MOBILE_CONTEXT_PATH) + "/office/mobileh5/workflow/workflowMain.html";
						location.href = storage.get(Constants.MOBILE_CONTEXT_PATH) + "/office/mobileh5/message/messageList.html";
			    	}else{
			    		var msg = data.msg;
			    		if(isNotBlank(msg)){
			    			viewToast(msg);
			    		}else{
			    			viewToast('登录失败');
			    		}
			    		storageLocal.remove(WapConstants.LOGIN_USER_NAME);
			    		storageLocal.remove(WapConstants.LOGIN_PASS_WORD);
			    	}
				},
				dataType : 'json',
				clearForm : false,
				resetForm : false,
				type : 'post',
				error:function(XMLHttpRequest, textStatus, errorThrown){
					closeMsgTip();
					viewToast(XMLHttpRequest.status);
				}//请求出错 
			};
		  	$("#dataForm").ajaxSubmit(options);
	},
};