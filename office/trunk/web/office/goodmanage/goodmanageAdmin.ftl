<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	getMyGoods();
});

function doGoodsManage(){
	load("#adminDiv", "${request.contextPath}/office/goodmanage/goodmanage-goodsManage.action");
}

function getMyGoods(){
	load("#adminDiv", "${request.contextPath}/office/goodmanage/goodmanage-myGoods.action");
}

function doGoodsApply(){
	load("#adminDiv", "${request.contextPath}/office/goodmanage/goodmanage-goodsApply.action");
}

function doGoodsAudit(){
	load("#adminDiv", "${request.contextPath}/office/goodmanage/goodmanage-goodsAudit.action");
}

function getGoodsType(){
	load("#adminDiv", "${request.contextPath}/office/goodmanage/goodmanage-goodsType.action");
}

function doGoodsAuth(){
	load("#adminDiv", "${request.contextPath}/office/goodmanage/goodmanage-goodsAuth.action");
}

</script>
<div class="pub-tab mb-15">
		<ul class="pub-tab-list">
			<li class="current" onclick="getMyGoods();">我的物品</li>
			<li onclick="doGoodsApply();">领用申请</li>
			<#if goodsAuditAuth>
			<li onclick="doGoodsAudit();">领用审核</li>
			</#if>
			<#if goodsManageAuth>
			<li onclick="doGoodsManage();">物品管理</li>
			<li onclick="getGoodsType();">物品类别</li>
			<li onclick="doGoodsAuth();">物品类别权限设置</li>
			</#if>
		</ul>
</div>
<div id="adminDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>