<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	boardRoomOrderManage();
});

function boardRoomManage(){
	load("#boardRoomDiv", "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomAdmin.action");
}
function boardRoomOrderAudit(){
	load("#boardRoomDiv", "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomAuditAdmin.action");
}
function boardRoomOrderManage(){
	load("#boardRoomDiv", "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomOrderManageAdmin.action");
}
function toAuditSet(){
	load("#container","${request.contextPath}/office/boardroommanage/boardroommanage-auditSet.action")
}
</script>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<li class="current" onclick="boardRoomOrderManage();">预约记录查询</li>
		<#if shheAdmin>
		<li onclick="boardRoomOrderAudit();">预约审核</li>
		</#if>
		<li onclick="boardRoomManage();">会议室管理</li>
		<#if megAdmin>
		<li onclick="toAuditSet();">审核权限设置</li>
		</#if>
	</ul>
</div>
<div id="boardRoomDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>