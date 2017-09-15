/**
 * 独立登陆页js
 */
var isSubmitting = false;
var login = {
  init_login :function(){
    var username = $.cookies.get(Constants.LOGIN_SINGLE_COOKIE_USERNAME_KEY);
    var password = $.cookies.get(Constants.LOGIN_SINGLE_COOKIE_PASSWORD_KEY);
    if (password == "\"\"") {
      password = '';
    }
    if (username) {
      document.loginForm.username.value = username;
    }
    if (password) {
      document.loginForm.password.value = password;
    }
    if (password != null && password != '') {
      $("input[name=cookieSaveType]").attr("checked", "checked");
      //doLogin();
    }
  },
  init_login_eis :function(){
	    var username = $.cookies.get(Constants.LOGIN_SINGLE_COOKIE_USERNAME_KEY);
	    var password = $.cookies.get(Constants.LOGIN_SINGLE_COOKIE_PASSWORD_KEY);
	    if (password == "\"\"") {
	      password = '';
	    }
	    if (username) {
	      document.loginForm1.uid.value = username;
	    }
	    if (password) {
	      document.loginForm1.pwd.value = password;
	    }
	    if (password != null && password != '') {
	    	$("input[name=cookieSaveType]").attr("checked", "checked");
	    }
	  },
  doLogin : function(passportUrl, serverId, backUrl, isRoot) {
    if (isSubmitting) {
      return;
    }

    var form = document.loginForm;
    if (form.username.value == '') {
      this.showError("用户名不能为空");
      return false;
    }
    var password = form.password.value;
    if ($.cookies.get(Constants.LOGIN_SINGLE_COOKIE_PASSWORD_KEY) != password) {
      var secure = (password == null || password == "") ? "" : hex_md5(password) + hex_sha1(password);
      form.password.value = secure;
    }

    // 根据选择保存cookie
    var cookieExpires = new Date();
    cookieExpires.setTime(cookieExpires.getTime() + (1000 * 60 * 60 * 24 * Constants.LOGIN_SINGLE_COOKIE_LIFE));
    var cookieSaveType = 1;
    if($("#cookieSaveType").is(':checked')){
    	cookieSaveType = 3;
    }
    if (cookieSaveType == 3) {
      $.cookies.set(Constants.LOGIN_SINGLE_COOKIE_USERNAME_KEY, form.username.value, {
        expiresAt : cookieExpires
      });
      $.cookies.set(Constants.LOGIN_SINGLE_COOKIE_PASSWORD_KEY, form.password.value, {
        expiresAt : cookieExpires
      });
    } else if (cookieSaveType == 2) {
      $.cookies.set(Constants.LOGIN_SINGLE_COOKIE_USERNAME_KEY, form.username.value, {
        expiresAt : cookieExpires
      });
      $.cookies.del(Constants.LOGIN_SINGLE_COOKIE_PASSWORD_KEY);
    } else if (cookieSaveType == 1) {
      $.cookies.del(Constants.LOGIN_SINGLE_COOKIE_USERNAME_KEY);
      $.cookies.del(Constants.LOGIN_SINGLE_COOKIE_PASSWORD_KEY);
    }
    
    if(passportUrl == null){
    	document.loginForm.submit();
    	return;
    }
    
    this.showLoading();
    var scriptLoginURL = passportUrl + "/scriptLogin";
    scriptLoginURL += "?action=login";
    scriptLoginURL += "&username=" + encodeURIComponent(form.username.value);
    scriptLoginURL += "&server=" + serverId;
    scriptLoginURL += "&root=" + isRoot;
    scriptLoginURL += "&cookieSaveType=" + cookieSaveType;
    if (form.password.value != "") {
      scriptLoginURL += "&password=" + form.password.value;
    }
    scriptLoginURL += "&loginMode=" + form.loginMode.value;
    scriptLoginURL += "&verifyCode=" + form.verifyCode1.value;
    scriptLoginURL += "&id=" + new Date().getTime();
    scriptLoginURL += "&url=" + backUrl;
    // 动态创建登录脚本元素, 这样做的目的是因为firefox不支持多次载入某个已经存在的script元素.
    // 如果已经存在登录脚本元素, 则先删除元素
    var scriptLogin = document.getElementById("scriptLogin");
    if (scriptLogin) {
      scriptLogin.parentNode.removeChild(scriptLogin);
    }

    // 创建登录脚本元素
    scriptLogin = document.createElement("script");
    scriptLogin.id = "scriptLogin";
    scriptLogin.type = "text/javascript";
    scriptLogin.src = scriptLoginURL;
    document.body.appendChild(scriptLogin);
   
    isSubmitting = true;
  },
  saveCookie4EisLogin : function() {
    var form = document.loginForm1;
    var password = form.pwd.value;
    if ($.cookies.get(Constants.LOGIN_SINGLE_COOKIE_PASSWORD_KEY) != password) {
      var secure = (password == null || password == "") ? "" : hex_md5(password);
    }
    // 根据选择保存cookie
    var cookieExpires = new Date();
    cookieExpires.setTime(cookieExpires.getTime() + (1000 * 60 * 60 * 24 * Constants.LOGIN_SINGLE_COOKIE_LIFE));
    var cookieSaveType = 1;
    if($("#cookieSaveType").is(':checked')){
    	cookieSaveType = 3;
    }
    if (cookieSaveType == 3) {
      $.cookies.set(Constants.LOGIN_SINGLE_COOKIE_USERNAME_KEY, form.uid.value, {
        expiresAt : cookieExpires
      });
      $.cookies.set(Constants.LOGIN_SINGLE_COOKIE_PASSWORD_KEY, form.pwd.value, {
        expiresAt : cookieExpires
      });
    } else if (cookieSaveType == 2) {
      $.cookies.set(Constants.LOGIN_SINGLE_COOKIE_USERNAME_KEY, form.uid.value, {
        expiresAt : cookieExpires
      });
      $.cookies.del(Constants.LOGIN_SINGLE_COOKIE_PASSWORD_KEY);
    } else if (cookieSaveType == 1) {
      $.cookies.del(Constants.LOGIN_SINGLE_COOKIE_USERNAME_KEY);
      $.cookies.del(Constants.LOGIN_SINGLE_COOKIE_PASSWORD_KEY);
    }
  },
  showLoading : function() {
    var loadingPanel = document.getElementById("login_loading");
    if (loadingPanel == null) {
      var el = document.createElement("div");
      el.setAttribute("id", "login_loading");
      el.style.cssText = "font-family:Verdana;font-size:12px;border:1px solid #00CC00;background-color:#A4FFA4;padding:2px;position:absolute;right:2px;top:1px;height:16px;z-index:10000";
      el.innerHTML = "正在加载...";
      document.body.appendChild(el);
      loadingPanel = el;
    } else {
      loadingPanel.style.display = "block";
    }
  },
  showError : function(e) {
    isSubmitting = false;
    var errorDiv = document.getElementById("error_span");
    if (errorDiv) {
      errorDiv.style.display = "block";
      errorDiv.innerHTML = e;
    }
    var loadDiv = document.getElementById("login_loading");
    if (loadDiv != null) {
      loadDiv.style.display = "none";
    }

  }
};

function processError(e) {
  login.showError(e);
}