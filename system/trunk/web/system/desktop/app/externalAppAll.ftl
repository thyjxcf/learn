<script>
	function go2ExternalApp(url){
		if(url == "")
			return;
		window.open(url);
	}
	
	function addApp(){
		openDiv("#deskAppListLayer", null, "${request.contextPath}/system/desktop/app/externalApp-set.action?appAll=true");
	}
	
	function deleteExternalApp(id){
		$.getJSON("${request.contextPath}/system/desktop/app/externalApp-delete.action",{"externalAppId":id},function(data){
			//如果有错误信息（与action中对应），则给出提示
			if(data && data != ""){
				showMsgError(data);
				return;
			}else{
				load("#externalApp","${request.contextPath}/system/desktop/app/externalApp.action?appAll=true");
				return;
			}
		}).error(function(){
			showMsgError("删除失败！");
		}); 
	}
	
	function deleteModule(id){
		$.getJSON("${request.contextPath}/system/desktop/app/externalApp-deleteModule.action",{"appIds":id},function(data){
			//如果有错误信息（与action中对应），则给出提示
			if(data && data != ""){
				showMsgError(data);
				return;
			}else{
				load("#externalApp","${request.contextPath}/system/desktop/app/externalApp.action?appAll=true");
				return;
			}
		}).error(function(){
			showMsgError("删除失败！");
		});
	}

$(document).ready(function(){
	
})
</script>
<!--=S 应用接入 Start-->
<div class="desk-item desk-item-big mb-20 mt-20" style="width:100%;">
	<div class="desk-item-inner desk-item-inner-all fn-rel">
    	<a href="javascript:void(0);" id="setDeskApp" onclick="addApp();"></a>
    	<div class="app-grid" id="deskAppList">
    		<ul class="fn-clear">
    			<#assign liIndex=-1 />
    			<#list userAppList as ua>
    			<#assign liIndex=liIndex+1 />
    			<li data-id="${liIndex}"><#-- data-add="yes" data-out="no"-->
                	<a href="javascript:void(0);">
                        <span class="app-img" onclick="go2Module('${ua.url}','${ua.subsystem}','${ua.moduleId}','${ua.parentId}','${ua.name}','desktop','${ua.limit!}');return false;"><img src="${request.contextPath}${ua.picture}_b.png"></span>
                        <span class="app-name">${ua.name!}</span>
                        <i onclick="deleteModule('${ua.id!}');return false;"></i>
                    </a>
    			</li>
    			</#list>
    			<#list externalAppList as app>
				<#assign liIndex=liIndex+1 />
    			<li data-id="${liIndex}"><#-- data-add="yes" data-out="no"-->
                	<a href="javascript:void(0);">
                        <span class="app-img" onclick="go2ExternalApp('${app.appUrl!}')"><img src="${app.downloadPath!}"></span>
                        <span class="app-name">${app.appName!}</span>
                        <#if loginInfo.user.type ==0 || loginInfo.user.type ==1><i onclick="deleteExternalApp('${app.id!}');return false;"></i></#if>
                    </a>
    			</li>
    			</#list>
    		</ul>
    	</div>
    </div>
</div>
<!--=E 应用接入 End-->
<div class="popUp-layer" id="deskAppListLayer" style="display:none;width:820px;top:0;left:0;"></div>