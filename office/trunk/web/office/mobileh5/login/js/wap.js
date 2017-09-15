var wap = {
	cancelLogin : function(){
		storageLocal.remove(WapConstants.LOGIN_PASS_WORD);//清除密码则不会自动登录
	},
	autoLogin : function(){
		var storNmae = storageLocal.get(WapConstants.LOGIN_USER_NAME);
		var storPwd = storageLocal.get(WapConstants.LOGIN_PASS_WORD);
		if(isNotBlank(storNmae)&&isNotBlank(storPwd)){
			wapNetwork.doLogin(storNmae,storPwd);
		}
	},
	init : function(){
		var storNmae = storageLocal.get(WapConstants.LOGIN_USER_NAME);
		var storPwd = storageLocal.get(WapConstants.LOGIN_PASS_WORD);
		if(isNotBlank(storNmae))
		$("#user").val(storNmae);
		if(isNotBlank(storNmae)&&isNotBlank(storPwd)){
			$("#pwd").val(storPwd);
			$("#layerSubmit").html("切换账号");
			showMsgTip("自动登录中...",wap.cancelLogin);
			setTimeout(wap.autoLogin,2000);
		}
		$("#login").click(function(){
			var name = $("#user").val();
			var pwd = $("#pwd").val();
			if(!isNotBlank(name)){
				viewToast('请输入用户名');
				return;
			}
			if(!isNotBlank(pwd)){
				viewToast('请输入密码');
				return;
			}
			wapNetwork.doLogin(name,pwd);
		});
	},
	
};  