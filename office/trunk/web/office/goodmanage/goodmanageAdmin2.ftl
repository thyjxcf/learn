<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	getMyGoods();
});

function doGoodsManage(){
	load("#adminDiv", "${request.contextPath}/office/goodmanage/goodmanage-goodsManage2.action");
}

function getMyGoods(){
	load("#adminDiv", "${request.contextPath}/office/goodmanage/goodmanage-myGoods2.action");
}

function getGoodsType(){
	load("#adminDiv", "${request.contextPath}/office/goodmanage/goodmanage-goodsType.action");
}
</script>
<div class="pub-tab mb-15">
		<ul class="pub-tab-list">
			<li class="current" onclick="getMyGoods();">我的物品</li>
			<#if goodsManageAuth>
			<li onclick="doGoodsManage();">物品管理</li>
			<li onclick="getGoodsType();">物品类别</li>
			</#if>
		</ul>
</div>
<div id="adminDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>