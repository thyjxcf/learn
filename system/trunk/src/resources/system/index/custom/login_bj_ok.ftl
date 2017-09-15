<!doctype html>
<html>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.7.2.min.js"></script>
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
.login table .code .txt{width: 80px;}
.login table .code a{margin-left: 15px;text-decoration: none;color: #333;}
.login table .code a:hover{text-decoration: underline;}
.login table .btn{width: 49px;height: 21px;background: url(btn.jpg) no-repeat;border: 0;cursor: pointer;}
.login-yes{padding: 20px;font-size: 14px;}
.login-yes a{text-decoration: underline;color: #4D6C5F;}
.login-yes .go-in{margin: 0 20px;}
</style>
</head>
<body>
</form>
<div class="login">
<p class="login-yes"><#if loginInfo?exists>${loginInfo.user.realname!},欢迎进入</#if><br /><br /><a href="javascript:void(0);" onclick="go2OA();return false;" class="go-in">办公平台</a><a href="javascript:void(0);" onclick="go2Out();return false;" class="login-out">退出</a></p>
</div>
</body>
<script type="text/javascript">   
	function go2OA(){
		window.open("${request.contextPath}/fpf/homepage/loginForEisOnly.action");
	}
	
	function go2Out(){
		if(window.confirm('是否要退出系统？')){   
	   		window.location.href="${request.contextPath}/fpf/login/remote/login_out.action?loginoutUrl=${request.contextPath}/fpf/login/remote/loginForBJ.action";
		}else{   
		    return false;   
		} 
	
	}
</script>
</html>
