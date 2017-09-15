	<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>换肤设置</span></p>
	<div class="wrap">
        <div class="panels">
            <div class="panel">
                <div class="skin-setting-name"><span>选择布局</span></div>
                <ul class="skin-theme-list skin-grid-list fn-clear layout-list">
                    <li class="g1 <#if userSkin.layout?default('1')=='1'>current</#if>" data-type="layout" data-value="1">上下布局<i></i></li>
                    <li class="g2 <#if userSkin.layout?default('1')=='2'>current</#if>" data-type="layout" data-value="2">左右布局<i></i></li>
                </ul>
                <#if loginInfo.user.ownerType==2>
                <div class="skin-setting-name"><span>选择主题</span></div>
                <ul class="skin-theme-list fn-clear theme-list">
                    <li class="t1 <#if userSkin.theme?default('3')=='1'>current</#if>" data-type="theme" data-value="1">应用中心<i></i></li>
                    <li class="t2 <#if userSkin.theme?default('3')=='2'>current</#if>" data-type="theme" data-value="2">个人中心<i></i></li>
                    <li class="t3 <#if userSkin.theme?default('3')=='3'>current</#if>" data-type="theme" data-value="3">多功能中心<i></i></li>
                </ul>
                </#if>
                <div class="skin-setting-name"><span>选择皮肤背景</span></div>
                <ul class="skin-thumb-list fn-clear">
                    <li class="skin-thumb <#if userSkin.backgroundImg! == 'titoni.jpg'>current</#if>">
                        <a class="skin-link" href="javascript:void(0);">
                            <span class="skin-thumb-img"><img alt="" src="${request.contextPath}/static/images/skin/wallpapers/205_115/titoni.jpg" data-img="titoni.jpg" data-color="fadec3" data-css="orange" width="205" height="115" /></span>
                            <span class="skin-apply-tip"></span>
                        </a>
                        <i></i>
                    </li>
                    <li class="skin-thumb <#if userSkin.backgroundImg! == 'balloon.jpg'>current</#if>">
                        <a class="skin-link" href="javascript:void(0);">
                            <span class="skin-thumb-img"><img alt="" src="${request.contextPath}/static/images/skin/wallpapers/205_115/balloon.jpg" data-img="balloon.jpg" data-color="f4f4f4" data-css="default" width="205" height="115" /></span>
                            <span class="skin-apply-tip"></span>
                        </a>
                        <i></i>
                    </li>
                    <li class="skin-thumb <#if userSkin.backgroundImg! == 'wood.jpg'>current</#if>">
                        <a class="skin-link" href="javascript:void(0);">
                            <span class="skin-thumb-img"><img alt="" src="${request.contextPath}/static/images/skin/wallpapers/205_115/wood.jpg" data-img="wood.jpg" data-color="e3efe8" data-css="green" width="205" height="115" /></span>
                            <span class="skin-apply-tip"></span>
                        </a>
                        <i></i>
                    </li>
                    <li class="skin-thumb <#if userSkin.backgroundImg! == 'sky.jpg'>current</#if>">
                        <a class="skin-link" href="javascript:void(0);">
                            <span class="skin-thumb-img"><img alt="" src="${request.contextPath}/static/images/skin/wallpapers/205_115/sky.jpg" data-img="sky.jpg" data-color="e2f4fe" data-css="default" width="205" height="115" /></span>
                            <span class="skin-apply-tip"></span>
                        </a>
                        <i></i>
                    </li>
                    <li class="skin-thumb <#if userSkin.backgroundImg! == 'childlike.jpg'>current</#if>">
                        <a class="skin-link" href="javascript:void(0);">
                            <span class="skin-thumb-img"><img alt="" src="${request.contextPath}/static/images/skin/wallpapers/205_115/childlike.jpg" data-img="childlike.jpg" data-color="e9edf0" data-css="gray" width="205" height="115" /></span>
                            <span class="skin-apply-tip"></span>
                        </a>
                        <i></i>
                    </li>
                    <li class="skin-thumb <#if userSkin.backgroundImg! == 'sunset.jpg'>current</#if>">
                        <a class="skin-link" href="javascript:void(0);">
                            <span class="skin-thumb-img"><img alt="" src="${request.contextPath}/static/images/skin/wallpapers/205_115/sunset.jpg" data-img="sunset.jpg" data-color="fedea6" data-css="orange" width="205" height="115" /></span>
                            <span class="skin-apply-tip"></span>
                        </a>
                        <i></i>
                    </li>
                    <li class="skin-thumb <#if userSkin.backgroundImg! == 'starSky.jpg'>current</#if>">
                        <a class="skin-link" href="javascript:void(0);">
                            <span class="skin-thumb-img"><img alt="" src="${request.contextPath}/static/images/skin/wallpapers/205_115/starSky.jpg" data-img="starSky.jpg" data-color="0d3b6c" data-css="navyBlue" width="205" height="115" /></span>
                            <span class="skin-apply-tip"></span>
                        </a>
                        <i></i>
                    </li>
                    <li class="skin-thumb <#if userSkin.backgroundImg! == 'motherland.jpg'>current</#if>">
                        <a class="skin-link" href="javascript:void(0);">
                            <span class="skin-thumb-img"><img alt="" src="${request.contextPath}/static/images/skin/wallpapers/205_115/motherland.jpg" data-img="motherland.jpg" data-color="fcf5ec" data-css="orange" width="205" height="115" /></span>
                            <span class="skin-apply-tip"></span>
                        </a>
                        <i></i>
                    </li>
                </ul>
            </div>
        </div>
    </div>
	<p class="dd">
	<a class="abtn-blue t-center mt-20" href="javascript:void(0);" onclick="saveSkin();">保存</a>
	<a class="reset-wallpaper abtn-blue mt-20 ml-10" href="javascript:void(0);">恢复默认桌面</a></p>
	<form method="post" name="skinform" id="skinform" enctype="multipart/form-data">
	<input type="hidden" name="defaultSkin" id="defaultSkin" value="${defaultSkin!}">
	<input type="hidden" name="userSkin.id" id="id" value="${userSkin.id!}">
	<input type="hidden" name="userSkin.userId" id="userId" value="${userSkin.userId!}">
	<input type="hidden" name="userSkin.layout" id="layout" value="${userSkin.layout?default('1')}">
	<input type="hidden" name="userSkin.theme" id="theme" value="${userSkin.theme!}">
	<input type="hidden" name="userSkin.skin" id="skin1" value="${userSkin.skin!}">
	<input type="hidden" name="userSkin.backgroundImg" id="backgroundImg" value="${userSkin.backgroundImg!}">
	<input type="hidden" name="userSkin.backgroundColor" id="backgroundColor" value="${userSkin.backgroundColor!}">
	</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript-skin.js"></script>
<script>
$(document).ready(function(){
});

<#--
$('.reset-wallpaper').click(function(){
	$('#layout').val("1");
	$('#backgroundImg').val("");
	$('#backgroundColor').val("");
});

$('.layout-list li').click(function(){
	$(this).addClass('current').siblings('li').removeClass('current');
	$('#layout').val($(this).attr('layout'));
});

$('.skin-thumb-list li a').click(function(){
	var $color=$(this).find('img').attr('data-color');
	var $img=$(this).find('img').attr('data-img');
	$('#backgroundColor').val($color);
	$('#backgroundImg').val($img);
});	
-->

var isSubmit = false;
function saveSkin(){
	if (isSubmit){
		return ;
	}
	
	<#--
	$('#layout').val($('.layout-list li .current').val());
	var nimg = $('.skin-thumb-list li .current').find('img');
	var $color=$(nimg).attr('data-color');
	var $img1=$(nimg).attr('data-img');
	var $css=$(nimg).attr('data-css');
	$('#backgroundColor').val($color);
	$('#backgroundImg').val($img1);
	$('#skin').val($css);
	-->
	
	isSubmit = true;
	var options = {
		   target :'#skinform',
	       url:'${request.contextPath}/system/desktop/app/userSkinSet-save.action', 
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       success : showSuccess
	    };
	try{
		$('#skinform').ajaxSubmit(options);
	}catch(e){
		showMsgError('保存失败！');
		isSubmit = false;	
	}
	
}

function showSuccess(data) {
   if(!data && data != '' && data != null){
		showMsgError(data);
	}else{
		home();
	}
	isSubmit = false;
}
</script>