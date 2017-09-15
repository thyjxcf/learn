<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<div class="pub-tab">
	<ul class="pub-tab-list">
		<li class="current li-deal">我的任务</li>
		<#if canAssign>
		<li class="li-assign">发布任务</li>
		</#if>
	</ul>
</div>
<div id="cDiv" />
<script>
jQuery(function(){
	jQuery(".li-deal").click(function(){
		load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-dealTask-list.action");
		$(this).addClass('current').siblings('li').removeClass('current');
	});
	
	jQuery(".li-assign").click(function(){
		load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-assignTask-list.action");
		$(this).addClass('current').siblings('li').removeClass('current');
	});
	
	jQuery(".li-list").click(function(){
		load("#cDiv", "${request.contextPath}/stusys/punishManage/punishManage-punishManage-list.action");
		$(this).addClass('current').siblings('li').removeClass('current');
	});
	
	load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-dealTask-list.action");
});
</script>
</@htmlmacro.moduleDiv>