<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${platFormName!}</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/public.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-default.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/custom-zj.css">

</head>
<body class="custom-zj">
<form id="loginForm" name="loginForm" method="post">
<input type="hidden" value="1" id="loginMode" name="loginMode"/>
<input type="hidden" id="verifyCode1" name="verifyCode1" class="txt" maxlength="4" />
<input type="hidden" value="0" id="username" name="username" value=""/>
<input type="hidden" value="0" id="password" name="password" value=""/>
</form>
<form id="loginForm1" name="loginForm1" method="post">
    <div class="login-wrap">
		
        <p class="fn-clear">
            <label for="user">用户名：</label>
            <input type="text" id="uid" name="uid" value="${uid!}" class="txt txt-name" onkeydown="if(event.keyCode==13) {$('#pwd').focus();return false;}">
        	<span id="error_span" class="tip-error" style="color:red"></span>
		</p>
        <p class="fn-clear">
            <label for="pwd"><span class="mr-12">密</span>码：</label>
            <input type="password" id="pwd" name="pwd" value="" class="txt txt-password" <#if showVerifyCode?default(false)><#if connectPassport>onkeydown="if(13==event.keyCode){login_();return false;}"<#else>onkeydown="if(event.keyCode==13) {$('#verifyCode').focus();return false;}"</#if><#else><#if connectPassport>onkeydown="if(13==event.keyCode){login_();return false;}"<#else>onkeydown="if(event.keyCode==13) {$('#submitButton').click();return false;}"</#if></#if>>
        </p>
       	<#if connectPassport>    
        <p class="fn-clear" id="verifyField" style="display:none">
            <label for="yzm">验证码：</label>
            <input type="text" class="txt txt-yzm" id="verifyCode" maxlength="4" placeholder="请输入验证码" onkeyup="if(13==event.keyCode){login_()}">
            <a href="javascript:void(0);" class="img-yzm"><img id="verifyImage"></a>
        </p>
        <#else>
            <#if showVerifyCode?default(false)>
            <p class="fn-clear">
            	<label for="yzm">验证码：</label>
                <input type="text" name="verifyCode" id="verifyCode" value="" class="txt txt-yzm" placeholder="请输入验证码" onkeydown="if(event.keyCode==13) {$('#submitButton').click();return false;}"/>
                <a href="javascript:void(0);" class="img-yzm"><img src="${request.contextPath}/common/verifyImage.action"+Math.random() onclick="this.src='${request.contextPath}/common/verifyImage.action?'+ Math.random()"/></a>
            </p>
            </#if>
		</#if>
		<p class="fn-clear"><a href="javascript:void(0);" class="login-btn" id="submitButton" onclick="login_();return false;"></a></p>
    </div>
	<div class="footer"><p>主办：浙江省教育厅 承办：浙江省教育技术中心 浙ICP备05000083号</p></div>
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