<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="系统参数设置">
	
	<SCRIPT>
	$(document).ready(function(){
		load("#divContent", "${request.contextPath}/system/admin/platformInfoAdmin-systemIniConfig.action?modID=${modID?default('')}");
		$('.pub-tab-list li').click(function(){
			$(this).addClass('current').siblings('li').removeClass('current');
			if($(this).attr("id") == "p1"){
				load("#divContent", "${request.contextPath}/system/admin/platformInfoAdmin-systemIniConfig.action?modID=${modID?default('')}");
			}
			else if($(this).attr("id") == "p2"){
				load("#divContent", "${request.contextPath}/system/admin/platformInfoAdmin-systemServerConfig.action");
			}
			else if($(this).attr("id") == "p3"){
				load("#divContent", "${request.contextPath}/system/admin/platformInfoAdmin-systemMcode.action");
			}
			else if($(this).attr("id") == "p4"){
				load("#divContent", "${request.contextPath}/system/admin/platformInfoAdmin-logAdmin.action");
			}
			else if($(this).attr("id") == "p5"){
				load("#divContent", "${request.contextPath}/system/admin/platformInfoAdmin-exceptionQuery.action");
			}
			else if($(this).attr("id") == "p6"){
				load("#divContent", "${request.contextPath}/system/admin/platformInfoAdmin-storageDir.action");
			}
		});
	})
	</SCRIPT>
	
	<div class="pub-tab">
		<div class="pub-tab-list">
			<li class="current" id="p1">平台参数设定</li>
			<li id="p2">系统服务配置</li>
			<li id="p3">微代码管理</li>
			<li id="p4">日志维护</li>
			<#if isTopUnit>
	    	<li id="p5">异常数据处理</li>
	    	<li id="p6">存储路径设置</li>
	    	</#if>
		</div>
	</div>
	<div class="pub-table-wrap">
		<div  id="divContent">
		</div>
    </div>
</@htmlmacro.moduleDiv>