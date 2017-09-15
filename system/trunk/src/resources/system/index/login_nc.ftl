<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${platFormName!}</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/public.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-nanchong.css">
</head>
<body class="login">
<form id="loginForm" name="loginForm" method="post">
<input type="hidden" value="1" id="loginMode" name="loginMode"/>
<input type="hidden" id="verifyCode1" name="verifyCode1" class="txt" maxlength="4" />
<input type="hidden" value="0" id="username" name="username" value=""/>
<input type="hidden" value="0" id="password" name="password" value=""/>
</form>
<form id="loginForm1" name="loginForm1" method="post">
<div id="container">
    <div class="login-wrap login-wrap-cixi">
    	<div class="login-inner">
        	<#--<p class="reg-txt">还没有账号？点击此处<a href="${request.contextPath}/basedata/serial/serialAdmin-register.action">注册</a></p>
           	<p class="tips"><span>用户名错了,再想想</span></p>-->
        	<h3>用户登录</h3>
            <p class="tips"><span id="error_span" class="tip-error"></span></p>
            <p><input type="text" placeholder="请输入用户名" id="uid" name="uid" value="${uid!}" class="txt txt-name" onkeydown="if(event.keyCode==13) {$('#pwd').focus();return false;}"/></p>
            <p><input type="password" placeholder="请输入密码" id="pwd" name="pwd" value="" class="txt txt-password" <#if showVerifyCode?default(false)><#if connectPassport>onkeydown="if(13==event.keyCode){login_();return false;}"<#else>onkeydown="if(event.keyCode==13) {$('#verifyCode').focus();return false;}"</#if><#else><#if connectPassport>onkeydown="if(13==event.keyCode){login_();return false;}"<#else>onkeydown="if(event.keyCode==13) {$('#submitButton').click();return false;}"</#if></#if>/></p>
            <#--<#if connectPassport>
	            <p id="verifyField" class="fn-clear">
	            	<span class="fn-hide">验证码</span>
	                <input id="verifyCode" name="verifyCode" type="text" value="" class="txt txt-code" placeholder="请输入验证码" maxlength="4" onkeyup="if(13==event.keyCode){login_()}"/>
	                <img id="verifyImage" src="${request.contextPath}/static/images/code.png" class="img-code" />
	            </p>
            <#else>
            	<#if showVerifyCode?default(false)>
	            <p class="fn-clear">
	                <input type="text" name="verifyCode" id="verifyCode" value="" class="txt txt-code" placeholder="请输入验证码" onkeydown="if(event.keyCode==13) {$('#submitButton').click();return false;}"/>
	                <img  id="verifyImage" src="${request.contextPath}/common/verifyImage.action"+Math.random() class="img-code" onclick="this.src='${request.contextPath}/common/verifyImage.action?'+ Math.random()"/>
	            </p>
           		</#if>
           	</#if>
           	-->
            <p class="fn-clear" style="*margin-top:20px;">
                <span><input  type="checkbox" name="cookieSaveType" id="cookieSaveType" class="chk">记住密码</span>
	            <#if findPasswordUrl?default("") !="">
	            <a class="fn-right mr-10" href="javascript:void(0);" onclick="findPassword();">找回密码</a>
            	</#if>
            </p>
            <p class="mt-20"><a class="login-btn" href="javascript:void(0);" id="submitButton" onclick="login_();return false;"></a></p>
        </div>
    </div>
</div>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript-chkRadio.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/handlefielderror.js"></script>
<script src="${request.contextPath}/static/js/login/jquery.cookies.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/js/login/md5.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/js/login/sha1.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/js/login/constants.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/js/login/login.js" type="text/javascript"></script>
<!--[if IE 6]>
<script src="${request.contextPath}/static/js/letskillie6.zh_CN.pack.js"></script>
<![endif]-->
</body>
<script type="text/javascript">  
function findPassword(){
	window.open('${findPasswordUrl!}')
} 
function login_() {
	if($("#uid").val()==''){
		$(".tip-error").html("账号不能为空");
		$(".tip-error").show();
		return false
	}

	if($("#uid").val().length>30){
		$(".tip-error").html("账号长度不能超过30个字符");
		$(".tip-error").show();
		return false
	}
	<#if connectPassport>
		<#-- login.js中会用到 -->
	  	$("#username").val($("#uid").val());
	  	$("#password").val($("#pwd").val());
	  	$("#verifyCode1").val($("#verifyCode").val());
	 	login.doLogin('${passportUrl}','${serverId}','${backUrl!}',<#if request.contextPath?default('') == ''>'1'<#else>'0'</#if>);
	 	return;
	 <#else>
	 	login.saveCookie4EisLogin();
	 	$('#loginForm1').attr("action","loginForEisOnly.action").submit() ;
	</#if>
	
}
$(function(){
	function windowAuto(){
		var window_height=$(window).height();
		var wrap_height=$('.login-wrap').outerHeight();
		$('.login-wrap').css('margin-top',parseInt(window_height-wrap_height)/2);
	};
	windowAuto();
	$(window).resize(function(){
		windowAuto();
	});
	$("#uid").focus(function(){
		$(".tip-error").hide();
	});
	$("#pwd").focus(function(){
		$(".tip-error").hide();
	});
	$("#verifyCode").focus(function(){
		$(".tip-error").hide();
	});
})

$(document).ready(function(){
<#if action.hasActionErrors()>
	<#list actionErrors! as item>
		$(".tip-error").html("${item?j_string}");
		$(".tip-error").show();
	</#list>
<#elseif action.hasActionMessages()>
	<#list actionMessages! as item>
		$(".tip-error").html("${item?j_string}");
		$(".tip-error").show();
	</#list>
<#else>
</#if>  
login.init_login_eis();
})
</script>
</html>