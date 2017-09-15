<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript">
$(function(){
	load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-userList.action");
});

function selectTab(selectTab){
	switch(selectTab){
		case 1:
			load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-userList.action");
			break;
		case 2:
			load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-serverList.action");
			break;
	}
}
</script>
	<div class="pub-tab">
		<div class="pub-tab-list">
			<li class="current" onclick="javascript:selectTab(1);return false;">按用户授权</li>
			<li onclick="javascript:selectTab(2);return false;">按服务授权</li>
		</div>
	</div>
<div  name="listDiv" id="listDiv"></div>
<script>vselect();</script>
</@htmlmacro.moduleDiv>