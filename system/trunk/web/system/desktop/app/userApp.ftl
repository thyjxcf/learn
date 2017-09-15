<script>
function appSet(){
	openDiv('#commonApp',null,'${request.contextPath}/system/desktop/app/userApp-set.action',true,'#commonApp .wrap','476');
}

function addApp(){
	openDiv("#addSystem",null,"${request.contextPath}/system/desktop/app/externalApp-set.action");
}

function go2ExternalApp(url){
	if(url == "")
		return;
	window.open(url);
}
</script>
<#if systemDeploySchool =='nbzx'>
<div class="dt fn-clear">
	<span class="item-name fn-left">常用操作</span>
    <a href="javascript:void(0);" onclick="appSet();return false;" class="set">设置</a>
    <#if loginInfo.user.type ==0>
	<a href="javascript:void(0);" onclick="addApp();return false;" class="set-link mr-10">外链设置</a>
	</#if>
</div>
<ul class="app-list">
	<#list externalAppList as app>
		<li><p><a href="javascript:void(0);" onclick="go2ExternalApp('${app.appUrl!}');"><img src="${app.downloadPath!}" >${app.appName!}</a></p></li>
	</#list>
	<#if userAppList?exists && 0 < userAppList?size>
    <#list userAppList as app>
    	<li><p><a href="javascript:void(0);" onClick="<#if app.picture! =="" || app.picture?index_of(".") != -1>go2ExternalApp('${app.url!}');<#else>go2Module('${app.url}','${app.subsystem}','${app.moduleId}','${app.parentId}','${app.name}','desktop','${app.limit!}');return false;</#if>"><img id="${app.moduleId}" src="<#if app.picture! =="" || app.picture?index_of(".") != -1>${request.contextPath}/static/images/ad/3.png<#else>${request.contextPath}${app.picture}_m.png</#if>">${app.name}</a></p></li>
    </#list>
    </#if>
</ul>
<#else>
<div class="dt fn-clear">
	<span class="item-name fn-left">常用操作</span>
    <a href="javascript:void(0);" onclick="appSet();return false;" class="set">设置</a>
</div>
	<#if userAppList?exists && 0 < userAppList?size>
	    <ul class="app-list">
	        <#list userAppList as app>
	        	<li><p><a href="javascript:void(0);" onClick="<#if app.picture! =="" || app.picture?index_of(".") != -1>go2ExternalApp('${app.url!}');<#else>go2Module('${app.url}','${app.subsystem}','${app.moduleId}','${app.parentId}','${app.name}','desktop','${app.limit!}');return false;</#if>"><img id="${app.moduleId}" src="<#if app.picture! =="" || app.picture?index_of(".") != -1>${request.contextPath}/static/images/ad/3.png<#else>${request.contextPath}${app.picture}_m.png</#if>">${app.name}</a></p></li>
	        </#list>
	    </ul>
	<#else>
     	<p class="no-data mt-50 mb-50">还没有常用操作哦！</p>
    </#if>
</#if>
