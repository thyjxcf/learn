<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title></title>
<style type="text/css">
/*reset*/
html{font-size:12px}body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,button,textarea,p,blockquote,th,td{margin:0;padding:0}body,button,input,select,textarea{font:12px/1.5 "SimSun","Microsoft YaHei",Tahoma,Verdana,Arial;outline:none}fieldset,img{border:0}address,caption,cite,code,dfn,var,optgroup{font-style:inherit;font-weight:inherit}del,ins{text-decoration:none}li{list-style:none}caption{text-align:left}h1,h2,h3,h4,h5,h6{font-size:100%;font-weight:normal}q:before,q:after{content:''}abbr,acronym{border:0;font-variant:normal}sup{vertical-align:baseline}sub{vertical-align:baseline}a{outline:none}
textarea{resize:none}/*禁用文本域手动放大缩小*/
input:focus,textarea:focus{outline:none}/*chrome获取焦点外框去除*/
input:-webkit-autofill,textarea:-webkit-autofill,select:-webkit-autofill{-webkit-box-shadow: 0 0 0 1000px white inset}/*chrome表单自动填充去掉input黄色背景*/

.login{width: 184px;height: 116px;}
.login table{width: 100%;border-collapse: collapse;}
.login table th{width: 50px;height: 28px;text-align: right;font-weight: 100;color: #4D6C5F;}
.login table td{width: 134px;}
.login table .txt{width: 132px;height: 20px;line-height: 20px;background: #fff;border: 1px solid #b2b2b2;}
.login table .code .txt{float:left;width: 60px;}
.login table .code a{margin-left: 15px;text-decoration: none;color: #333;}
.login table .code a:hover{text-decoration: underline;}
.login table .btn{width: 49px;height: 21px;background: url(${request.contextPath}/static/images/custom/btn.jpg) no-repeat;border: 0;cursor: pointer;}
</style>
</head>
<body>
<form id="loginForm" name="loginForm" method="post">
<input type="hidden" value="1" id="loginMode" name="loginMode"/>
<input type="hidden" id="verifyCode1" name="verifyCode1" class="txt" maxlength="4" />
<input type="hidden" value="0" id="username" name="username" value=""/>
<input type="hidden" value="0" id="password" name="password" value=""/>
</form>
<form id="loginForm1" name="loginForm1" method="post">
<div class="login">
	<table>
		<tr>
			<th>用户名：</th>
			<td><input type="text" id="uid" name="uid" class="txt" /></td>
		</tr>
		<tr>
			<th>密码：</th>
			<td><input type="password" id="pwd" name="pwd" class="txt" /></td>
		</tr>
		<#if connectPassport>
		<tr class="code" id="verifyField" style="display:none">
			<th>验证码：</th>
			<td>
			<input id="verifyCode" class="txt"  maxlength="4" placeholder="请输入验证码" onkeyup="if(13==event.keyCode){login_()}" />
			<img id="verifyImage" width="56" height="25" class="img-code"/>
			</td>
		</tr>
		</#if>
		<tr>
			<th>&nbsp;</th>
			<td><input type="submit" value="" class="btn" onclick="login_();return false;"/></td>
		</tr>
	</table>
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
<script src="${request.contextPath}/static/js/login/login_bj.js" type="text/javascript"></script>
<!--[if IE 6]>
<script src="${request.contextPath}/static/js/letskillie6.zh_CN.pack.js"></script>
<![endif]-->
</body>
<script type="text/javascript">   
function login_() {
	if($("#uid").val()==''){
		alert("账号不能为空");
		return false
	}

	if($("#uid").val().length>30){
		alert("账号长度不能超过30个字符");
		return false
	}

	<#if connectPassport>
		<#-- login.js中会用到 -->
	  	$("#username").val($("#uid").val());
	  	$("#password").val($("#pwd").val());
	  	$("#verifyCode1").val($("#verifyCode").val());
	 	login.doLogin('${passportUrl!}','${serverId}','${request.contextPath}/fpf/login/remote/login_success.action',<#if request.contextPath?default('') == ''>'1'<#else>'0'</#if>);
	 	return;
	 <#else>
	 	login.saveCookie4EisLogin();
	 	$('#loginForm1').attr("action","loginForEisOnly.action").submit() ;
	</#if>
	
}

$(document).ready(function(){
<#if action.hasActionErrors()>
	<#list actionErrors! as item>
		alert("${item?j_string}");
	</#list>
<#elseif action.hasActionMessages()>
	<#list actionMessages! as item>
		alert("${item?j_string}");
	</#list>
<#else>
</#if>  
login.init_login_eis();
})
</script>
</html>
