<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${platFormName!}</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/public.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-default.css">

<script type="text/javascript" src="${request.contextPath}/cassso/_assets/js/thirdparty/jquery/2.0.3/jquery.js"></script>
<script type="text/javascript" src="${request.contextPath}/cassso/_assets/js/thirdparty/jquery/plugins/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/cassso/_assets/js/thirdparty/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="${request.contextPath}/cassso/_assets/sso.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<!--[if IE 6]>
<script src="${request.contextPath}/static/js/letskillie6.zh_CN.pack.js"></script>
<![endif]-->
</head>
<body class="login">
<div class="popUp-layer popUp-layer-tips" id="panelWindow_success" style="display:;">
    <div class="wrap">
        <p class="content"><span class="success" id="panelWindow_success_msg">登录跳转中...</span></p>
    </div>
</div>
<div class="popUp-layer popUp-layer-tips" id="panelWindow_error" style="display:none;">
    <div class="wrap">
        <p class="content"><span class="error" id="panelWindow_error_msg">错误</span></p>
    </div>
</div>
<form id="loginForm" name="loginForm" method="post">
<input type="hidden" value="1" id="loginMode" name="loginMode"/>
<input type="hidden" id="verifyCode1" name="verifyCode1" class="txt" maxlength="4" />
<input type="hidden" id="username" name="username" value="${uid!}"/>
<input type="hidden" id="password" name="password" value="${pwd!}"/>
</form>
</body>
<script type="text/javascript">
$(document).ready(function(){
	var surl = '${casDto.serviceUrl!}';
	var curl = '${casDto.casUrl!}';
	var np = 'http://www.ahedu.cn/SNS/index.php';
	var edusso = new EduSSO(surl, curl);
	var uid='${uid!}';
	var pwd = '${pwd!}';
	edusso.login('rrt', 'rrt', 'login_name', uid, pwd, function(result){
		if (result && result.result != "success"){
			//var jso1 = $.toJSON(result);
			var jsObj = result;
			if(jsObj){
				var code = jsObj.code;
				if('-1'==code){
					var msg = jsObj.data;
					$('#panelWindow_success').hide();
					$('#panelWindow_error_msg').html(msg);
					$('#panelWindow_error').show();
				}
			}
		} else {
			location.href=np;
		}				
	});
})
</script>
</html>