<#import "/common/htmlcomponent.ftl" as htmlmacro />
<script>
	function go2ExternalApp(url){
		if(url == "")
			return;
		window.open(url);
	}
	
	function addBroadcast(){
		openDiv("#broadcastDiv",null,"${request.contextPath}/system/desktop/app/broadcast-set.action");
	}
	
	function go2MoreExternalApp(){
		load("#container","${request.contextPath}/system/desktop/app/broadcastList.action");
	}
</script>
<div class="dt fn-clear">
    <a class="fn-right mr-5" href="javascript:void(0);" onclick="go2MoreExternalApp();">更多 &gt;</a>
    <#if loginInfo.user.type ==0 || loginInfo.user.type ==1>
    <a class="abtn-blue fn-right mr-15" style="margin-top:-5px;" href="javascript:void(0);" onclick="addBroadcast();">设置</a>
    </#if>
	<span class="item-name fn-left">录播接入</span>
</div>
<ul class="my-list my-list-video">
	<#list broadcastList as broadcast>
	<li><a href="javascript:void(0);" onclick="go2ExternalApp('${broadcast.appUrl!}');" title="${broadcast.appName!}"><@htmlmacro.cutOff str=broadcast.appName length=18/></a></li>
	</#list>
</ul>