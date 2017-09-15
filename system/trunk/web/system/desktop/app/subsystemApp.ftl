<script>
	function addApp4School(divId,externalType){
		openDiv("#addSystem",null,"${request.contextPath}/system/desktop/app/externalApp-set.action?divId="+divId+"&externalType="+externalType);
	}
</script>
<div class="dt mt-20 mx-20"><span class="item-name"><#if sortName?exists && sortName != ''>${sortName!}<#else>智慧教育应用</#if></span>
<#if loginInfo.unitClass ==2>
<#if loginInfo.user.type ==0 || loginInfo.user.type ==1>
<a class="abtn-blue fn-right mr-15" style="margin-top:-5px;" href="javascript:void(0);" onclick="addApp4School('${divId!}','${sortType!}');">设置</a>
</#if>
</#if>
</div>
<div class="app-grid">
	<ul class="fn-clear">
		<#list userSystemList! as app>
		<#if app.type! =='1'>
		<#if app.source == "99">
			<li>
		    	<a href="javascript:void(0);" onClick="go2subsystem('${app.xurl}','${app.id}','dir','${app.source!}');return false;">
		            <span class="app-img"><img src="${app.image!}"></span>
		            <span class="app-name">${app.appname!}</span>
		        </a>
			</li>
		<#else>
			<li>
		    	<a href="javascript:void(0);" onClick="go2subsystem('${app.xurl}','${app.id}','dir','${app.source!}','${app.parentId}');return false;">
		            <span class="app-img"><img src="${fileUrl}/store/subsystempic/${app.image!}"></span>
		            <span class="app-name">${app.appname!}</span>
		        </a>
			</li>
		</#if>
		</#if>
		</#list>
	</ul>
</div>