<div class="main-nav main-nav-hide" style="left:-145px;" id="subsystemListDiv">
<div style="position:absolute;z-index:-1;width:101%;height:101%;margin-left:-0.5%;">  
	<iframe style="width:100%;height:103%;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe>  
</div>
    <a href="javascript:void(0);" class="nav-cut nav-show" title="展开"></a>
    <#--<span class="nav-tips" style="display:none;"><a href="javascript:void(0);" class="close"></a></span>-->
    <div class="nav-wrap mCustomScrollbar" id="navScroll">
        <div class="nav-inner py-10">
        	<!--=S 一级 Start-->
        	<div class="item item1">
	        	<div class="item-tt">
	        		<a href="javascript:void(0);" onClick="home();return false;" class="tt">首页</a>
	        	</div>
        	</div>
        	
        	<#list userSystemList! as app>
        	<div class="item item1">
	        	<div class="item-tt">
	        	<a href="javascript:void(0);" name="dir" class="tt subsystem${app.id}" id="subsystem${app.id}" <#if app.type! =='1'>onClick="go2subsystem('${app.xurl}','${app.id}','dir','${app.source!}','${app.parentId}');return false;"</#if>>${app.appname!}</a>
	        	</div>
	        	<#if app.type! =='1'>
	        	<div class="item-wrap" source="${app.source!}" subsystemid="${app.id}" style="display:none;">
	            </div>
	            </#if>
        	<#if app.type! =='0'>
        		<!--=S 二级 Start-->
        		<div class="nav-inner nav-inner2">
        		<#list app.subAppList! as subApp>
                    <div class="item item2">
                        <div class="item-tt">
                        	<a id="subsystem${subApp.id}" name='item' class='subsystem${subApp.id}' href="javascript:void(0);" onClick="go2subsystem('${subApp.xurl}','${subApp.id}','item','${subApp.source!}','${subApp.parentId}');return false;">${subApp.appname!}</a>
                        </div>
                        <div class="item-wrap" source="${subApp.source!}" subsystemid="${subApp.id}" style="display:none;">
                        </div>
                    </div>
        		</#list>
        		</div>
        		<!--=E 二级 End-->
        	</#if>
        	</div>
        	<!--=E 一级 End-->
        	</#list>
        </div>
    </div>
</div>
<div class="inside-nav inside-nav-show" id="modelList" style="display:none;">
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript-nav.js"></script>
<script>
$(document).ready(function(){
	$('.nav-wrap .item1 .item-wrap').each(function(){
		var apid = $(this).attr('subsystemid');
		var source = $(this).attr('source');
		if(source=="1")
			load(this,'${request.contextPath}/system/homepage/model.action?appId='+apid+'&appLevel=1');
	});
	$('.nav-wrap .item2 .item-wrap').each(function(){
		var apid = $(this).attr('subsystemid');
		var source = $(this).attr('source');
		if(source=="1")
			load(this,'${request.contextPath}/system/homepage/model.action?appId='+apid+'&appLevel=1');
	});
	<#--$.getScript("${request.contextPath}/static/js/jquery.mCustomScrollbar.concat.min.js",function(){
		alert(1);
	});-->
	
});

<#--
 onclick="go2subsystem('${app.xurl}','${app.id}','dir','${app.source!}','${app.parentId}');return false;"
-->

function go2subsystem(url,appId,type,source,parentId){
	if(source != "1"){
		window.open(url);
		return;
	}else{
		$('body,.common-wrap').removeAttr('style');
	}
	$('.item-wrap .sub-nav').children('a').removeClass('current');
	if(type =="item"){
		$('#subsystem'+appId).addClass("current");
	}else{
		$('#subsystem'+parentId).addClass("current");
	}
	var _subsystemName=$('#subsystemListDiv').find('.subsystem'+appId).text();
	var _subsystemName_a="<a href='javascript:void(0);' onclick='click2Subsystem("+appId+")'>"+_subsystemName+"</a>";
	showCrumbs("<p class='crumbs-inner'>当前位置："+_subsystemName_a+"</p>");
	
	$('body').removeClass('index-new').addClass('new-menu');
	$("#subsystemListDiv").hide();
	$("#modelList").show();
	//实现可以收缩的功能
	//$("#modelHideBtn").show();
	showModel();
	//end
	load("#modelList","${request.contextPath}/system/homepage/model.action?appId="+appId+"&appLevel=2");
	//load("#miniModelList","${request.contextPath}/system/homepage/miniModel.action?appId="+appId);
	load("#container",url);
	
	//TODO 以下功能实现公文管理情况下显示帮助
	if(appId==17){
		$(".mini-top .help").show();
	}else{
		$(".mini-top .help").hide();
	}
}
</script>