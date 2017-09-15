/**
 * 独立登陆页js
 */
var isSubmitting = false;
var pasUrl = '';
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
    
    pasUrl = passportUrl;
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
    scriptLoginURL += "&version=2&multiAccount=1&multiAccountByOneMatch=1&loginMode="+form.loginMode.value;
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
  doLoginWithoutPwd : function(passportUrl, serverId, backUrl, isRoot,key,stamp) {
	    if (isSubmitting) {
	      return;
	    }

	    var form = document.loginForm;
	    if (form.username.value == '') {
	      this.showError("用户名不能为空");
	      return false;
	    }
//	    var password = form.password.value;
//	    if ($.cookies.get(Constants.LOGIN_SINGLE_COOKIE_PASSWORD_KEY) != password) {
//	      var secure = (password == null || password == "") ? "" : hex_md5(password) + hex_sha1(password);
//	      form.password.value = secure;
//	    }

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
//	      $.cookies.set(Constants.LOGIN_SINGLE_COOKIE_PASSWORD_KEY, form.password.value, {
//	        expiresAt : cookieExpires
//	      });
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
	    
	    pasUrl = passportUrl;
	    this.showLoading();
	    var scriptLoginURL = passportUrl + "/scriptLogin";
	    scriptLoginURL += "?action=loginFree";
	    scriptLoginURL += "&username=" + encodeURIComponent(form.username.value);
	    scriptLoginURL += "&server=" + serverId;
	    scriptLoginURL += "&root=" + isRoot;
	    scriptLoginURL += "&cookieSaveType=" + cookieSaveType;
	    scriptLoginURL += "&version=2&multiAccount=1&multiAccountByOneMatch=1&loginMode="+form.loginMode.value;
	    scriptLoginURL += "&url=" + backUrl;
	    scriptLoginURL += "&keyt=" + key;
	    scriptLoginURL += "&timestamp="+stamp;
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
	 $('#loginMode').val('0');
	 if(!$('.layer-user').is(':hidden')){
		 $('.reset').click();
	 }
	 
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

/* 多用户选择 列表dom操作 */
function processAccounts(accounts){
	isSubmitting = false;
	$('#uid').blur();
    $('#pwd').blur();
    if($('#verifyField').css("display") != "none"){
        $("#verifyCode").blur();
    }
	//多账号信息设置
	var $multiTable=$("#multiTable");
	for (var i=0;i<accounts.length;i++ )  {   
	    var accountId=accounts[i].split(",")[0];
	    var username=accounts[i].split(",")[1];
	    var ownerType=accounts[i].split(",")[2]; // 1:学生 2:教师 3:家长
	    var realname=accounts[i].split(",")[4];
	    var unitName=accounts[i].split(",")[5];
	    //默认头像
	    var $img=$('<img src="'+pasUrl+'/login/images/h1.png" alt="头像">');
	    //默认未知身份
	    var $spanOwnerType=$('<span class="id id-t">未知</span>');
	    switch(ownerType)
        {   
           case '1':
        	   $img= $('<img src="'+pasUrl+'/login/images/h2.png" alt="头像">');
        	   $spanOwnerType=$('<span class="id id-x">学生</span>');
               break;
           case '2':
        	   $img= $('<img src="'+pasUrl+'/login/images/h1.png" alt="头像">');
               $spanOwnerType=$('<span class="id id-t">教职工</span>');
               break;
           case '3':
        	   $img= $('<img src="'+pasUrl+'/login/images/h1.png" alt="头像">');
               $spanOwnerType=$('<span class="id id-p">家长</span>');
               break;
        }
	    //生成table信息
	    var $tr=$('<tr></tr>');
	    $tr.prepend($("<td class='last'>&nbsp;</td>"));
	    $td_a=$("<td class='a'></td>");
	    var $a_abtn=$('<a href="javascript: $(\'#uid\').val(\'' + username + '\');scriptLogin();" class="abtn-blue">进入</a>');
	    $td_a.append($a_abtn);
	    $tr.prepend($td_a);
	    var $info_p1=$('<p></p>');
	    var $info_p2=$('<p>'+unitName+'</p>');
	    $info_p1.prepend($("<span class='name'>"+realname+"</span>"));
	    $info_p1.prepend($spanOwnerType);
	    var $td_info=$('<td class="info"></td>');
	    $td_info.prepend($info_p2);
	    $td_info.prepend($info_p1);
	    $tr.prepend($td_info);
	    var $imgTd=$('<td class="img"></td>');
	    $imgTd.append($img);
	    $tr.prepend($imgTd);
	    $tr.prepend($('<td class="first">&nbsp;</td>'));
	    $multiTable.append($tr);
    }
	var loadDiv = document.getElementById("login_loading");
    if (loadDiv != null) {
      	loadDiv.style.display = "none";
    }
	
	$('.layer-user').jWindowOpen({
        modal:true,
        center:true,
        drag : ".title"
    });
}