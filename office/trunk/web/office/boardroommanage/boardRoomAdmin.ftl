<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	load("#boardRoomListDiv", "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomList.action");
	//openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/asset/assetAdmin-addApply.action?stateQuery="+stateQuery, null, null, "500px");
}

function doBoardRoomAdd(){
	//load("#boardRoomListDiv", "${request.contextPath}/office/boardroommanage/boardroommanage-addBoardRoom.action");
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/boardroommanage/boardroommanage-addBoardRoom.action", null, null, "1000px");
}


</script>
<#if megAdmin>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-part">
		<a href="javascript:void(0);" onclick="doBoardRoomAdd();" class="abtn-orange-new fn-right mr-10">新增会议室</a>
		<div class="fn-clear"></div>
	</div>
	</div>
</div>
</#if>
<div id="boardRoomListDiv"></div>
<div class="popUp-layer" id="classLayer" style="display:none;width:1000px;"></div>
<script>
	$(document).ready(function(){
		doSearch();
	});
</script>
</@common.moduleDiv>