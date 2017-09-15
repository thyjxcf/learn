<script>
function saveUserApps(){
	var array_id = new Array(); 
    var a = 0;  
	$('.app-list-yes img').each(function(fn){
		var id=$(this).attr('id');
		array_id[a++] = id;  
	});
	getJSON("${request.contextPath}/system/desktop/app/userApp-remote!userAppTcSave.action",{"moduleIds":array_id.join(",")},"closeUserApp()");
}

function closeUserApp(){
	
	closeDiv("#commonApp","loadHomeDiv()");
}

function loadHomeDiv(){
	var winWidth=$(window).width();
	var widescreen="Y";
	if(winWidth < 1200){
		widescreen="N";
	}
	var url="${request.contextPath}/system/desktop/app/userApp-tc.action?divId=home&widescreen="+widescreen;
	load('#home',url);
}

function comLocation(){
	var wHeight=parseInt($(window).height());
	var dHeight=parseInt($(document).height());
	var lHeight=$('#commonApp').height();
	var lTop=parseInt((wHeight-lHeight)/2);
	(dHeight>wHeight) ? mHeight=dHeight : mHeight=wHeight;
	$('.ui-layer-mask').height(mHeight);
	$('#commonApp').css('top',lTop);
};

function commonAppScroll(){
	var $yes=$('#commonApp .app-list-yes');
	var $no=$('#commonApp .app-list-no');
	var yesHeight=$yes.children('ul').height();
	var noHeight=$no.children('ul').height();
	if(yesHeight>285){
		$yes.mCustomScrollbar();
	}else{
		$yes.find('.mCSB_draggerRail').hide();
	};
	if(noHeight>285){
		$no.mCustomScrollbar();
	}else{
		$no.find('.mCSB_draggerRail').hide();
	};
}

function commonCopy(){
	var $newItem=$('.common .app-list')
	var newTxt='<li><p></p></li>';
	var itemLen=$('.app-list-yes li').length;
	$newItem.empty();
	$('#commonApp .app-list-yes').contents().clone().appendTo($newItem);
	if(itemLen<6){
		$(newTxt+newTxt+newTxt+newTxt+newTxt+newTxt).appendTo($newItem);
	}
}

function changeUnitClass(){
	$('.app-class .current').trigger("click");
}

$(document).ready(function(){
	
	$('#commonApp .app-list').on('click','li',function(e){
		e.preventDefault();
		
		var hasClass=$(this).parents('.app-list-wrap').hasClass('app-list-yes');
		if(!hasClass){
			$(this).appendTo('#commonApp .app-list-yes .app-list');
		}else{
			$(this).appendTo('#commonApp .app-list-no .app-list');;
		};
		
		commonAppScroll();
		comLocation();
	});
	
	$('.app-class a').click(function(e){
		e.preventDefault();
		
		var unitClass="${loginInfo.unitClass?default(1)}";
		<#if loginInfo.user.type?default(2) == 0>
		var unitClassRadio=document.getElementsByName("unitClass");
		for(j=0;j<unitClassRadio.length;j++){
			if(unitClassRadio[j].checked){
				unitClass=unitClassRadio[j].value;
			}
		}
		</#if>
		$(this).addClass('current').siblings('a').removeClass('current');
		var appClass=$(this).attr('data-appClass');
		var appClass_unitClass=appClass+"_"+unitClass;
		
		if(appClass==0){
			$('.app-list-no .app-list').children('li').hide();
			$('.app-list-no .app-list').children('li[data-unitClass='+unitClass+']').show();
		}else{
			$('.app-list-no .app-list').children('li').hide();
			$('.app-list-no .app-list').children('li[data-appUnitClass='+appClass_unitClass+']').show();
		}
		commonAppScroll();
		comLocation();
	});
	
	$('.app-list-no .app-list').children('li').hide();
	
	
	var unitClass="${loginInfo.unitClass?default(1)}";
	<#if loginInfo.user.type?default(2) == 0>
	var unitClassRadio=document.getElementsByName("unitClass");
	for(j=0;j<unitClassRadio.length;j++){
		if(unitClassRadio[j].checked){
			unitClass=unitClassRadio[j].value;
		}
	};
	</#if>
	$('.app-list-no .app-list').children('li[data-unitClass='+unitClass+']').show();
	comLocation();
});
</script>
<p class="tt"><a href="javascript:void(0);" onclick="closeDiv('#commonApp');return false;" class="close">关闭</a></p>
<div class="wrap">
	<div class="app-wrap">
        <h3 class="pt-15">常用操作</h3>
        <div class="app-list-wrap app-list-yes">
        	<ul class="app-list fn-clear">
            <#list userAppList as app>
            <li><p><a href="javascript:void(0);"><img id="${app.moduleId}" src="<#if app.picture! =="" || app.picture?index_of(".") != -1>${request.contextPath}/static/images/ad/3.png<#else>${request.contextPath}${app.picture}_m.png</#if>">${app.name}</a></p></li>
            </#list>
             </ul>
        </div>
        <h3>其他操作</h3>
        <p class="app-class">
        	<#if loginInfo.user.type?default(2) == 0>
        	<input type="radio" name="unitClass" value="1" checked onClick="changeUnitClass()">教育局&nbsp;&nbsp;
			<input type="radio" name="unitClass" value="2" onClick="changeUnitClass()">学校
        	<br>
        	</#if>
	        <a data-appClass="0" href="javascript:void(0);" class="current" id="allSubsystem">全部</a>
	        <#list userSystemList! as app>
				<a href="javascript:void(0);" data-appClass="${app.id}">${app.appname!}</a>
			</#list>
        </p>
        <div class="app-list-wrap app-list-no">
           <ul class="app-list fn-clear">
           <#list moduleList as module>
            <li data-appClass="${module.subsystem!}" data-appUnitClass="${module.subsystem!}_${module.unitclass!}"  data-unitClass="${module.unitclass!}" ><p><a href="javascript:void(0);"><#if module.picture! =="" || module.picture?index_of(".") != -1><img id="${module.id}" src="${request.contextPath}/static/images/ad/3.png"><#else><img id="${module.id}" src="${request.contextPath}${module.picture}_m.png"></#if>${module.name}</a></p></li>
            </#list>
            </ul>
        </div>
    </div>
</div>
<p class="dd">
    <a href="javascript:void(0);" onClick="saveUserApps();return false;" class="abtn-blue">确定</a>
    <a href="javascript:void(0);" onclick="closeDiv('#commonApp');return false;" class="abtn-gray reset ml-5">取消</a>
</p>