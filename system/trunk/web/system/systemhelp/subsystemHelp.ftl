<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>帮助</title>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/public.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout.css"/>
<#if systemDeploySchool! =="weifang">
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-default.css">
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-weifang.css"/>
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/default.css"/>
<#elseif systemDeploySchool! =="hzzc">
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-eiss.css">
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/navyBlue-eiss.css">
<#elseif systemDeploySchool! =="xinjiang">
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-xinjiang.css">
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/default.css"/>
<#elseif systemDeploySchool! =="nhzg">
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-ninghai.css">
	<#if loginInfo.user.ownerType ==3>
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/green.css"/>
	<#elseif loginInfo.user.ownerType==1>
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/yellow.css"/>
	<#else>
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/default.css"/>
	</#if>
<#else>
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-default.css">
	<#if loginInfo.user.ownerType ==3>
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/${loginUser.skin?default('green')}.css"/>
	<#elseif loginInfo.user.ownerType==1>
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/${loginUser.skin?default('yellow')}.css"/>
	<#else>
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/${loginUser.skin?default('default')}.css"/>
	</#if> 
</#if>

<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.8.3.min.js"></script>
<script>jQuery.curCSS=jQuery.css;</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</head>
	
<body>
<div id="container">
    <div class="help-wrap fn-clear" id="help">
        <div class="help-sidebar">
        	<div class="item">
                <p class="tt">${subSystem.name!}</p>
                <div class="wrap">
                	<#if firstModelList??&&(firstModelList?size>0)>
	                	<#list firstModelList as headmode>
	                		<div class="inner">
	                			<p class="tit"><a href="javascript:void(0);">${headmode.name!}</a></p>
		                		<#if secondModelMap.get(headmode.mid!)??&&(secondModelMap.get(headmode.mid!)?size>0)>
		                			<ul>
		                			<#list secondModelMap.get(headmode.mid!) as mode>
		                				<li <#if (mode_index+1) == (secondModelMap.get(headmode.mid!)?size)>class="last"</#if>><a href="javascript:void(0);" id="m_${mode.id!}" onclick="openHelp('${mode.id!}')">${mode.name!}</a></li>
		                			</#list>
		                			</ul>	
		                		</#if>
	                		</div>
	                	</#list>
                	</#if>
                </div>
            </div>
        </div>
        <div class="help-content">
        </div>
    </div>
</div>
<iframe id="downloadFrame" name="downloadFrame" allowTransparency="true" frameBorder="0" width="00%" height="00%" scrolling="auto" src="" style="display:none;"></iframe>
<div id="footer">浙江浙大万朋软件有限公司 提供技术支持 Copyright 2013-2015,万朋版权所有11</div>
</body>
<script>
	//
	$(function(){
		var $tt=$('.help-sidebar .item .tt');
		var $wrap=$('.help-sidebar .item .wrap');
		var $inner=$wrap.children('.inner');
		var $tit=$inner.children('.tit');
		var $ul=$inner.children('ul');
		$tt.click(function(){
			if(!$(this).hasClass('tt-open')){
				$(this).addClass('tt-open').siblings('.wrap').show();
			}else{
				$(this).removeClass('tt-open').siblings('.wrap').hide();
			};
		});
		$tit.click(function(){
			if(!$(this).hasClass('tit-open')){
				$(this).addClass('tit-open').siblings('ul').show();
			}else{
				$(this).removeClass('tit-open').siblings('ul').hide();
			};
		});
		$("#footer").load("${request.contextPath}/common/foot.action");
	});
	function openHelp(mid){
		$("#m_"+mid).parents(".tt").addClass('tt-open').siblings('.wrap').show();
		$("#m_"+mid).parents(".inner").find(".tit").addClass('tit-open').siblings('ul').show();
		$(".help-content").load("${request.contextPath}/system/systemhelp/help/"+mid+".html");
	}
	
	function doDownload(url){
		document.getElementById('downloadFrame').src="${request.contextPath}"+url;
	}
</script>