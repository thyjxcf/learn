<script type="text/javascript" src="${request.contextPath}/static/js/jquery.PlaceHolder.js"></script>
<#import "/common/htmlcomponent.ftl" as common />
<script>
	function go2ExternalApp(url){
		if(url == "")
			return;
		window.open(url);
	}
	
	function query(){
		load("#container","${request.contextPath}/system/desktop/app/broadcastList.action?unitName="+$('#unitName').val());
	}
	
	$(function(){ $('input, textarea').placeholder(); });
</script>
<div class="WF-video-wrap">
	<p class="tt">
		<#if loginInfo.unitClass ==1>
        <div class="pub-search fn-right">
            <input type="text" value="" class="txt" id="unitName" placeholder="请输入单位名称">
            <a href="javascript:void(0);" class="btn" onclick="query();">查找</a>
        </div>
        </#if>
    	<span class="f16 b">录播接入</span>
    </p>
    <ul class="WF-video-list fn-clear">
	    <#list broadcastList as broadcast>
				<li><a href="javascript:void(0);" onclick="go2ExternalApp('${broadcast.appUrl!}');" title="${broadcast.appName!}"><@common.cutOff str=broadcast.appName length=24/></a></li>
		</#list>
    </ul>
    <@common.Toolbar container="#container">
    </@common.Toolbar>
</div>