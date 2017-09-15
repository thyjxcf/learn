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
<div id="header">
	<div class="header-inner">
        <div class="mini-top">
            <p class="logo">${platFormName!}</p>
        </div>
    </div>
</div>
<form method="post" name="hiddenform" id="hiddenform" enctype="multipart/form-data">
	<input type="hidden" name="uid" id="uid" value="">
	<input type="hidden" name="pwd" id="remotePwd" value="">
	<input type="hidden" id="uucToken" name="uucToken" value="${uucToken!}" />
</form>
<form id="loginForm1" name="loginForm1" method="post">
<input type="hidden" value="${checkUserId!}" id="checkUserId" name="bind.userId"/>
<input type="hidden" value="${remoteUserId!}" id="remoteUserId" name="bind.remoteUserId" />
<div id="container">
    <div class="login-wrap">
        <div class="login-inner">
        	<h3>用户绑定</h3>
            <p class="tips"><span id="error_span" class="tip-error"></span></p>
            <p><input type="text" id="remoteUsername" name="bind.remoteUsername" value="${uid!}" class="txt txt-name" onkeydown="if(event.keyCode==13) {$('#pwd').focus();return false;}"/></p>
            <p class="mt-20"><a class="login-btn" href="javascript:void(0);" id="submitButton" onclick="login_();return false;">确 定</a></p>
        </div>
    </div>
</div>
<div id="footer"></div>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript-chkRadio.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/handlefielderror.js"></script>
<!--[if IE 6]>
<script src="${request.contextPath}/static/js/letskillie6.zh_CN.pack.js"></script>
<![endif]-->
</body>
<script type="text/javascript">
var isSubmit = false;  
function login_() {
	if (isSubmit){
		return false;
	}
	isSubmit = true;
	
	if($("#remoteUsername").val()==''){
		$(".tip-error").html("账号不能为空");
		$(".tip-error").show();
		isSubmit = false;
		return false
	}

	if($("#remoteUsername").val().length>30){
		$(".tip-error").html("账号长度不能超过30个字符");
		$(".tip-error").show();
		isSubmit = false;
		return false
	}

	var options = {
		   target :'#loginForm1',
	       url:'${request.contextPath}/common/open/apUserBind-save.action', 
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       success : showReply
	    };
	try{
		$('#loginForm1').ajaxSubmit(options);
	}catch(e){
		showMsgError('保存失败！');
		isSubmit = false;	
	}
}

function showReply(data){
	if(!data){
		isSubmit = false;
		$(".tip-error").html("账号绑定失败！");
		$(".tip-error").show();
		return;
	}
	if(data.operateSuccess){
	 	$('#uid').val($("#remoteUsername").val());
	 	if(data.hiddenText){
			var fis = data.hiddenText;
			for(var i=0;i<fis.length;i++){
				var text = fis[i];
				if(text[0] == 'pwd'){
					$('#remotePwd').val(text[1]);
				} else if(text[0] == 'uucToken'){
					$('#uucToken').val(text[1]);
				}
			}
		}
	 	$(".tip-error").html(data.promptMessage);
		$(".tip-error").show();
		document.hiddenform.action='redirectToAp-jump.action';
		document.hiddenform.submit();
	} else {
		isSubmit = false;
		$(".tip-error").html(data.errorMessage);
		$(".tip-error").show();
	}
}

$(function(){
	$("#uid").focus(function(){
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
})
</script>
</html>