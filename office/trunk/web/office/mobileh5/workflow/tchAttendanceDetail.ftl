<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no,email=no" name="format-detection">
<meta content="fullscreen=yes,preventMove=no" name="ML-Config">
<title>补卡详情</title>
<link isVersion="true" rel="stylesheet" type="text/css" href="${request.contextPath!}/static/html/css/style.css?version=v1.0">
</head>

<body class="gray">
<div id="page">
	<div id="pageInner" class="fn-flex fn-flex-col hidde">
    	<header style="display:none">
        	<h1 class="f-20" ><span id="title"><!--吕小城的请假--></span></h1>
            <a href="#" class="abtn abtn-img abtn-back abtn-left html-window-back" id="cancelId"><span></span></a>
            <a href="javascript:void(0)" class="abtn abtn-left2 f-18 html-window-close"><span>关闭</span></a>
        </header>
        <div class="scroll-wrap fn-flex-auto ios-touch">
            <div id="container" class="fn-rel">
        		<div id="auditImg">
            		<!--<i class="icon-img icon-state icon-state-yse"></i>
                	<i class="icon-img icon-state icon-state-no" style="display:none;"></i>-->
        		</div>
            	<ul class="ui-detail f-14">
            		<li class="avatar fn-clear" id="userPhotoImg"></li>
            		<li class="fn-clear  mt-20">
                        <span class="tt">所在部门：</span>
                        <span class="dd" id="deptName"></span>
                    </li>
                    <li class="fn-clear">
                        <span class="tt">补卡班次：</span>
                        <span class="dd" id="attenceDateType"><!--5天--></span>
                    </li>
                    <li class="fn-clear">
                        <span class="tt">补卡原因：</span>
                        <span class="dd" id="clockReason"><!--参加青岛教育技术展览会议，主要过去介绍公司产品。--></span>
                    </li>
            	</ul>
            	<ul class="acc-wrap  ios-touch mt-20" id="fileDiv">
                    <!--<li class="fn-clear">
                        <span class="icon-img icon-class icon-pdf"></span>
                        <a href="#" class="icon-img icon-download"></a>
                        <p class="acc-tt f-16">2014年6月全国计算机等级考试</p>
                        <p class="acc-dd">805.2KB</p>
                    </li>-->
                </ul>
                <ul class="ui-axis mt-20" id="auditDiv">
                	<!--<li>
                    	<span class="icon-img icon-right"></span>
                        <div class="item">
                        	<i></i>
                            <p class="tt"><span class="time">2015-07-28 15:19</span><span class="f-16">吕晓晨</span></p>
                            <p class="dd f-14"><span class="c-blue">发起申请</span></p>
                        </div>
                    </li>-->
                </ul>
                <!--
                <p class="abtn-full f-16" style="display:none" id="delete"><a href="javascript:void(0);" >撤销</a></p>
                -->
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${request.contextPath!}/static/html/js/flexible_css.debug.js"></script>
<script src="${request.contextPath!}/static/html/js/flexible.debug.js"></script>
<script src="${request.contextPath!}/static/html/js/jquery-1.9.1.min.js"></script>
<script src="${request.contextPath!}/static/html/js/jquery.Layer.js"></script>
<script src="${request.contextPath!}/static/html/js/storage.js?version=v1.0"></script>
<script src="${request.contextPath!}/static/html/js/jquery.form.js?version=v1.0"></script>
<script src="${request.contextPath!}/static/html/js/mobiscroll.custom-3.0.0-beta2.min.js"></script>
<!-- 需要加版本号刷新缓存的js -->
<script isVersion="true" src="${request.contextPath!}/static/html/js/public.js?version=v1.0"></script>
<script src="${request.contextPath!}/static/html/js/myscript.js?v=20170619"></script>
<script isVersion="true" src="${request.contextPath!}/static/html/js/common.js?version=v1.0"></script>
<script isVersion="true" src="${request.contextPath!}/static/html/js/validate.js?version=v1.0"></script>
<script isVersion="true" src="${request.contextPath!}/static/html/js/weike.js?version=v1.0"></script>
<script isVersion="true" src="${request.contextPath!}/static/html/js/myWeike.js?version=v1.0"></script>
<script isVersion="true" src="${request.contextPath!}/office/mobileh5/workflow/js/wap.js?version=v1.0"></script>
<script isVersion="true" src="${request.contextPath!}/office/mobileh5/workflow/js/wapEntities.js?version=v1.0"></script>
<script isVersion="true" src="${request.contextPath!}/office/mobileh5/workflow/js/wapNetwork.js?version=v1.0"></script>
<script isVersion="true" src="${request.contextPath!}/office/mobileh5/workflow/js/wapNetworkService.js?version=v1.0"></script>

<script src="${request.contextPath!}/static/html/js/my.cache.script.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
	var version = "${version!}";
	storage.set('version_time_key', version);

	//storage.remove(Constants.UNIT_ID);
	//storage.remove(Constants.USER_ID);
	//storage.remove(Constants.MOBILE_CONTEXT_PATH);

	//var unitId = '${unitId!}';
	//var userId = '${userId!}';
	var contextPath = '${request.contextPath!}';
	//storage.set(Constants.UNIT_ID, unitId);
	//storage.set(Constants.USER_ID, userId);
	storage.set(Constants.MOBILE_CONTEXT_PATH, contextPath);
	
	//微课接入标识
	storage.set(WeikeConstants.WEIKE_FLAG_KEY, WeikeConstants.WEIKE_FLAG_VALUE_TYPE_2);

	initScript(function(){
		initWieikeGoBack();
		setTimeout(wap.initDetail(), 200);
	});
}, false);	
</script>
</html>