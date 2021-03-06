<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no,email=no" name="format-detection">
<meta content="fullscreen=yes,preventMove=no" name="ML-Config">
<title>内容为空</title>
<script src="${request.contextPath}/static/html5/js/flexible_css.debug.js"></script>
<script src="${request.contextPath}/static/html5/js/flexible.debug.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/html5/css/style.css">
</head>

<body>
<div id="page">
	<div id="pageInner" class="fn-flex fn-flex-col hidde">
        <header style="display:none;">
        	<h1 class="f-20" id="msgUse">提示</h1>
        	<a href="javascript:void(0)" class="abtn abtn-left ml-20 f-18 html-window-close" id="windowClose"><span>关闭</span></a>
        </header>
        <div class="scroll-wrap fn-flex-auto ios-touch">
            <div id="container">
                <div class="no-data">
                	<p><span class="icon-img icon-no-data"></span></p>
                    <p class="pt-30 f-16"><#if promptMessageDto.errorMessage?exists&&promptMessageDto.errorMessage!=''>${promptMessageDto.errorMessage!}<#else>相关内容未找到</#if></p>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${request.contextPath}/static/html5/js/jquery-1.9.1.min.js"></script>
<script src="${request.contextPath}/static/html5/js/myscript.js"></script>
<script type="text/javascript" src="${request.contextPath!}/static/html5/js/storage.js"></script>
</body>
<script>
function connectWeikeJSBridge(callback) {
    if (window.WeikeJSBridge) {
        callback(WeikeJSBridge);
    } else {
        try {
            document.addEventListener('WeikeJSBridgeReady', function(){
               callback(WeikeJSBridge);
            });
        } catch (e){}
    }
}

connectWeikeJSBridge(AAAA);

function AAAA(bridge) {
	$(".html-window-close").click(
		function(){
			var d={"actionType":"windowClose"};
	    	bridge.send(d);//发送消息到微课		
		}
	); 
} 

//微课返回方法
function wkGoBack(){
	if($(".html-window-close").length > 0){
		$(".html-window-close").click();
	}
}

</script>
</html>
