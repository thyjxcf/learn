<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${platFormName!}</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/public.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-jian.css">
</head>
<body class="login">
<div id="header">
	<div class="header-inner">
        <div class="mini-top">
            <p class="logo"><a href="javascript:void(0);"><img src="${request.contextPath}/static/images/logo_jian.png" />${platFormName!}</a></p>
        </div>
    </div>
</div>
<form id="loginForm" name="loginForm" method="post">
<input type="hidden" value="1" id="loginMode" name="loginMode"/>
<input type="hidden" id="verifyCode1" name="verifyCode1" class="txt" maxlength="4" />
<input type="hidden" value="0" id="username" name="username" value=""/>
<input type="hidden" value="0" id="password" name="password" value=""/>
</form>
<form id="loginForm1" name="loginForm1" method="post">

<div id="container">
	<div class="banner-list">
        <a href="javascript:void(0);" target="_blank" style="background:url(${request.contextPath}/static/images/banner_bg1.jpg) no-repeat top center #f6fbff">标题1</a>
        <a href="javascript:void(0);" target="_blank" style="background:url(${request.contextPath}/static/images/banner_bg2.jpg) no-repeat top center #f6fbff">标题1</a>
        <a href="javascript:void(0);" target="_blank" style="background:url(${request.contextPath}/static/images/banner_bg3.jpg) no-repeat top center #fcfdff">标题1</a>
        <a href="javascript:void(0);" target="_blank" style="background:url(${request.contextPath}/static/images/banner_bg3.jpg) no-repeat top center #fcfdff">标题1</a>
    </div>
	<div class="login-outer fn-clear">
	    <div class="login-inner">
	        <h3>用户登录</h3>
	        <p class="tips"><span id="error_span" class="tip-error"></span></p>
	        <p><input type="text" id="uid" name="uid" value="${uid!}" class="txt txt-name" onkeydown="if(event.keyCode==13) {$('#pwd').focus();return false;}" splaceholder="请输入用户名" /></p>
	        <p><input type="password" id="pwd" name="pwd" value="" class="txt txt-password" <#if showVerifyCode?default(false)><#if connectPassport>onkeydown="if(13==event.keyCode){login_();return false;}"<#else>onkeydown="if(event.keyCode==13) {$('#verifyCode').focus();return false;}"</#if><#else><#if connectPassport>onkeydown="if(13==event.keyCode){login_();return false;}"<#else>onkeydown="if(event.keyCode==13) {$('#submitButton').click();return false;}"</#if></#if>/></p>
	        <p><span>&nbsp;记住密码:</span><input type="checkbox" name="cookieSaveType" id="cookieSaveType" /></p>
	        
	        <#if connectPassport>
	            <p id="verifyField" class="fn-clear" style="display:none">
				<span class="fn-hide">验证码</span>
				<input id="verifyCode" class="txt txt-code"  maxlength="4" placeholder="请输入验证码" onkeyup="if(13==event.keyCode){login_()}" />
				<img id="verifyImage" width="62" height="25" class="img-code"/>
				</p>
            <#else>
            <#if showVerifyCode?default(false)>
	            <p class="fn-clear">
	                <input type="text" name="verifyCode" id="verifyCode" value="" class="txt txt-code" placeholder="请输入验证码" onkeydown="if(event.keyCode==13) {$('#submitButton').click();return false;}"/>
	                <img src="${request.contextPath}/common/verifyImage.action"+Math.random() class="img-code" onclick="this.src='${request.contextPath}/common/verifyImage.action?'+ Math.random()"/>
	            </p>
            </#if>
            </#if>
            <#if needRegister> 
	            <p class="fn-clear" style="*margin-top:20px;">
	                <a class="fn-right mr-10" href="${request.contextPath}/basedata/serial/serialAdmin-register.action">注册</a>
	            </p>
            </#if>
            <p class="mt-20"><a class="login-btn" href="javascript:void(0);" id="submitButton" onclick="login_();return false;">登 录</a></p>
	    </div>
	    <ul class="cut-banner">
            <li class="on">1</li>
            <li>2</li>
            <li>3</li>
            <li>4</li>
        </ul>
        <p class="banner-info">学校位于吉安市城南工业区，环境优美，交通便利。学校教育资源丰富，师资力量雄厚，占地120余亩，建筑面积4万平方米。具有高、中级职称教师100余名。学校还多渠道地招贤纳才，从企业聘请了一批长期在生产一线从事技术攻关、经验丰富、技能突出的技师来校任教。学校坚持以法治校，以德治校，从严治校。充分利用办学优势和综合资源，坚持以“面向市场、服务经济、开门办学”为宗旨，以“宽基础、精专业、创特色、树品牌”为办学指导思想，以“加大教育投入，严格教学管理”为措施，逐渐形成自己的特色。。</p>
    </div>
</div>
<div id="footer"></div>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript-chkRadio.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript-jian-login.js"></script>
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
<#if showFoot!>
load("#footer","${request.contextPath}/common/foot.action");
</#if>
login.init_login_eis();
})
</script>
</html>