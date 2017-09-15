<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="no-scroll">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${platFormName!}</title>
<script type="text/javascript" src="${request.contextPath}/static/js/chinaexcel.js"></script>
</head>
<script>
//判断是自己点击触发还是首页点击触发的
var target='homepage';
function goToModule(url,modName,parentModName,parentId){
	enablePrintSave(null);
	if(${moduleID!} !=0 && target=='homepage'){
		jQuery("#"+parentId).click();
		target='self';
	}
	jQuery("#naviBar").html("<a href='javascript:void(0);' onClick='goToNavi();'>${subSystem.name!}</a>><a href='javascript:void(0);'>"+parentModName+"</a>><span>"+modName+"</span>");
	jQuery("#mainFrame").attr("src","${request.contextPath}/"+url); 
}

function goToNavi(){
	jQuery('.current').removeClass('current');
	jQuery("#naviBar").html("<a href='javascript:void(0)' onClick='goToNavi();'>${subSystem.name!}</a>><span>首页</span>");
	jQuery("#mainFrame").attr("src","${request.contextPath}/system/homepage/nav.action?subSystemId=${subSystem.id!}&platform=${platform}");
}

function redirectUrl(url){
	window.location.href=url;
}

function skinManage(){
	openWindow("#skinWindow",".skin-manage","${request.contextPath}/system/common/set/skin.action");
}

function appManage(){
	openWindow("#appWindow",".app-manage","${request.contextPath}/system/common/set/app.action");
}

function loadApp(){}

function flow(){
	showMsgWarn("没需求,无从下手............");
}

function pwdManage(){
	openWindow("#pwdWindow",".pwd-manage","${request.contextPath}/system/common/set/password.action");
}

function helpManage(){
	window.open ('${request.contextPath}/${action.getText('eis.help.postfix')}?appId=${subSystem.id!}');
}

function openWindow(id,from,url){
	jQuery(id).jWindowOpen({  //弹出层的id
			modal:true,
			center:true,
			//drag : ".title",
			//close:"#"+id,  //关闭层按钮，可以id也可以class
			closeHoverClass:"hover",
			transfererFrom:from,
			transfererClass:"transferer" //弹开效果
			//transfererClass:"" //直接显示
		});
	//jQuery("#_______overlayer").css("display","none"); //背景遮罩效果，默认有遮罩效果，若是不要遮罩请去掉此行的注释
	jQuery("#_______overlayer").css({"-moz-opacity":"0.5","opacity":"0.5","filter":"alpha(opacity=50)"}); //遮罩背景透明度，涉及到IE兼容，所以3个数字必须全部修改，默认20%
	jQuery(id).html("<p class='loading'>数据加载中······</p>");
	load(id,url,haveOverlayer);
}


jQuery(document).ready(function(){
	if(${moduleID!} !=0){
		jQuery("#${moduleID!}").click();
	}
})

</script>
<body class="frm">
<div class="header-warp">
<div id="header">
	<div class="name">
        <div class="user">${timeBucket!}好！后台管理员</div>
    </div>
    <ul class="set-info">
    	<li class="last"><img src="${request.contextPath}/static/images/icon_out.png" /><a href="${request.contextPath}/${action.getText('eis.background.logout.postfix')}">退出</a></li>
    </ul>
</div>
</div>
    <div class="slider">
        <div id="slidScroll">
        <#assign num =1>
        <#if modelList?exists && (modelList?size >0)>
	    	<#list modelList as msg>
	        	<#if msg.parentid== -1>
	        		<#if msg_index ==0>
	        			<div class="sid-panel" >
		        		<div class="sid-panel-title fir" id="${msg.id!}"><span class="icon"><img src=<#if msg.picture! =="">"${request.contextPath}/static/images/icon1.png"<#else>"${request.contextPath}${msg.picture}"</#if> /></span>${msg.name!}</div>
		        		<div class="ul-box">
		        		<ul>
	        		<#else>
	        			</ul>
	        			</div>
	        			</div>
	        			<div class="sid-panel" style="height:46px;">
	        			<div class="sid-panel-title fir" id="${msg.id!}"><span class="icon"><img src=<#if msg.picture! =="">"${request.contextPath}/static/images/icon1.png"<#else>"${request.contextPath}${msg.picture}"</#if> /></span>${msg.name!}</div>
		        		<div class="ul-box">
		        		<ul>
	        		</#if>
	        		<#assign num=num+1>
	        	<#else>
	        		 <li><a hidefocus="true" href="javascript:void(0)" <#if msg.parentModType! !="common">id="${msg.id!}"</#if> onclick="jQuery('.current').removeClass('current');jQuery(this).addClass('current');goToModule('${msg.url!}','${msg.name!}','${msg.parentModName!}','${msg.parentid!}');return false;" >${msg.name!}</a></li>
	        	</#if>
	    	</#list>
	    	</ul>
			</div>
			</div>
	    </#if>
        </div>
    </div>
    
    <div class="content">
    	<div class="dt">
	    	<div id="naviBar" class="crumb"><a href="javascript:void(0)">${subSystem.name!}</a>><span>首页</span></div>
	    	<div class="menu-option">
	                <span id="chinaExcelPrintPreview" style="display:none" onclick="chinaExcelPreview();"><img src="${request.contextPath}/static/images/icon_print.gif" /><a href="javascript:void(0);">打印预览</a>|</span>
	                <span id="chinaExcelSave" style="display:none" onclick="chinaExcelSave();return false;"><img src="${request.contextPath}/static/images/icon_savaas.gif" /><a href="javascript:void(0);">另存为</a></span>
	                <!--<span id="" ><a href="javascript:void(0)" onClick="flow();return false;"><img src="${request.contextPath}/static/images/flow.gif" alt="相关流程" /></a></span>-->
	        </div>
    	</div>
        <iframe src="${request.contextPath}/system/homepage/nav.action?subSystemId=${subSystem.id!}&platform=${platform}" id="mainFrame" name="mainFrame" class="mainFrame" scrolling="no" frameborder="0"></iframe>
    	<p class="bt"></p>
    </div>
<!--[if IE 6]>
<script type="text/javascript" src="${request.contextPath}/static/js/DD_belatedPNG.js"></script>
<script language="javascript" type="text/javascript">
DD_belatedPNG.fix(".set-info img,.desk-applist img,.dsk-sys,.dsk-sys ul img,.spot-news,.spot-news .bt");
</script>
<![endif]-->
<script>
jQuery(function(){
	var $window_h=jQuery(window).height(); //可视窗口高度
	var $header_h=jQuery('#header').height(); //头部高度
	var $dt_h=jQuery('.dt').height(); //导航高度
	var $bt_h=jQuery('.bt').height(); //底部高度
	var $mainFrame_h=$window_h-$header_h-$dt_h-$bt_h;
	jQuery('.mainFrame').height($mainFrame_h);
})
</script>
</body>
</html>