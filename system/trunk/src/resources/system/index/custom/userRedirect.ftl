<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${platFormName!}</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/public.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-default.css">
</head>
<body class="login">
<#--
<div id="header">
	<div class="header-inner">
        <div class="mini-top">
            <p class="logo">${platFormName!}</p>
        </div>
    </div>
</div>-->
<form id="loginForm" name="loginForm" method="post">
<input type="hidden" value="1" id="loginMode" name="loginMode"/>
<input type="hidden" id="verifyCode1" name="verifyCode1" class="txt" maxlength="4" />
<input type="hidden" id="username" name="username" value="${uid!}"/>
<input type="hidden" id="password" name="password" value="${pwd!}"/>
</form>
<#--
<form id="loginForm1" name="loginForm1" method="post">
<input type="hidden" value="${checkUserId!}" id="checkUserId" name="checkUserId"/>
<input type="hidden" value="${remoteUserId!}" id="remoteUserId" name="remoteUserId" />
<input type="hidden" value="${uucToken!}" id="uucToken" name="uucToken" />
<div id="container">
    <div class="login-wrap">
        <div class="login-inner">
        	<h3>用户绑定</h3>
            <p class="tips"><span id="error_span" class="tip-error"></span></p>
            <p><input type="text" id="uid" name="uid" value="${uid!}" class="txt txt-name" onkeydown="if(event.keyCode==13) {$('#pwd').focus();return false;}"/></p>
            <p><input type="password" id="pwd" name="pwd" value="" class="txt txt-password" onkeydown="if(event.keyCode==13) {$('#submitButton').click();return false;}"/></p>
            <p class="mt-20"><a class="login-btn" href="javascript:void(0);" id="submitButton" onclick="login_();return false;">确 定</a></p>
        </div>
    </div>
</div>
<div id="footer"></div>
</form>
-->
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
$(document).ready(function(){
<#if promptMessageDto.operateSuccess>
	login.doLogin('${passportUrl!}','${serverId}','${backUrl!}',<#if request.contextPath?default('') == ''>'1'<#else>'0'</#if>);
</#if>  
})
</script>
</html>