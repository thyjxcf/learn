<script>
function go2subsystem(url,appId,type,source,parentId){
	
	if(source != "1"){
		window.open(url,appId);
		return;
	}else{
		$('body,.common-wrap').removeAttr('style');
	}
	$('.NB-menu .list').children('li').removeClass('current');
	if(type =="item"){
		$('#subsystem'+appId).addClass("current");
	}else{
		$('#subsystem'+parentId).addClass("current");
	}
	var _subsystemName=$('#subsystemList').find('.subsystem'+appId).text();
	var _subsystemName_a="<a href='javascript:void(0);' onclick='click2Subsystem("+appId+")'>"+_subsystemName+"</a>";
	showCrumbs("<p class='crumbs-inner'>当前位置："+_subsystemName_a+"</p>");
	
	$("#modelList").show();
	//实现可以收缩的功能
	//$("#modelHideBtn").show();
	showModel();
	//end
	load("#modelList","${request.contextPath}/system/homepage/model.action?appId="+appId);
	load("#miniModelList","${request.contextPath}/system/homepage/miniModel.action?appId="+appId);
	load("#container",url);
	
	//TODO 以下功能实现公文管理情况下显示帮助
	if(appId==17){
		$(".mini-top .help").show();
	}else{
		$(".mini-top .help").hide();
	}
}

$(document).ready(function(){
	$('.NB-menu .list li.has').hover(
		function(){
			$(this).children('.NB-sub-menu').show();
		},function(){
			$(this).children('.NB-sub-menu').hide();
		}
	);
	$('.NB-sub-menu').hover(
		function(){
			$(this).parents('li').addClass('hover');
		},function(){
			$(this).parents('li').removeClass('hover');
		}
	);
	<#if appId! !=0 && !module?exists>
		$('#subsystemList').find('.subsystem${appId!}').trigger('click');
	</#if>
	
	<#if module?exists>
		go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');
	</#if>
})
</script>
<#if appId! ==0 || module?exists>
<a href="javascript:void(0);" onClick="home();return false;" class="home">首页</a>
</#if>
<div id="subsystemList" class="list">
	<#list userSystemList! as app>
	<li id="subsystem${app.id}" <#if app.type! =='0'>class="has"</#if>>
    	<a name='dir' href="javascript:void(0);" class="tt subsystem${app.id}" <#if app.type! =='1'>onClick="go2subsystem('${app.xurl}','${app.id}','dir','${app.source!}','${app.parentId}');return false;"</#if>>${app.appname!}</a>
    	<#if app.type! =='0'>
    	 <div class="NB-sub-menu">
        	<div style="position:absolute;z-index:-1;width:101%;height:101%;margin-left:-0.5%;">  
				<iframe style="width:100%;height:100%;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe>  
			</div>
        	<#list app.subAppList! as subApp>
        	<a id="subsystem${app.id}" name='item' class='subsystem${subApp.id}' href="javascript:void(0);" onClick="go2subsystem('${subApp.xurl}','${subApp.id}','item','${subApp.source!}','${subApp.parentId}');return false;">${subApp.appname!}</a>
        	</#list>
        </div>
        </#if>
    </li>
    </#list>
</div>