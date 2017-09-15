<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-apply.action");
});

function doLoad(val){
	if(val == "1"){
		load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-category.action");
	}else if(val == "2"){
		load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-apply.action");
	}else if(val == "3"){
		load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-audit.action");
	}else if(val == "4"){
		load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-purchaseList.action");
	}else if(val == "5"){
		load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-purchaseAudit.action");	
	}else if(val == "6"){
		load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-opinion.action");
	}else if(val=="7"){
		load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-assetQuery.action");	
	}else{
		load("#adminDiv","${request.contextPath}/office/asset/assetAdmin-applyQuery.action");
	}	
}
</script>
<div class="pub-tab mb-15">
		<ul class="pub-tab-list">
			<li class="current" onclick="doLoad('2');">请购申请</li>
			<#if roleList?exists && (roleList?size>0)>
			<li onclick="doLoad('3');">请购审核</li>
			</#if>
			<#if assetManage>
			<li onclick="doLoad('8');" style="width:60px;">资产查询</li>
			</#if>
			<li onclick="doLoad('4');" style="width:60px;">采购单</li>
			<#if assetPurAuditAuth?default(false)>
			<li onclick="doLoad('5');">采购审核</li>
			</#if>
			<#if assetCategoryAuth?default(false)>
			<li onclick="doLoad('7');">资产汇总</li>
			<li onclick="doLoad('1');">资产类别维护</li>
			<li onclick="doLoad('6');">采购审核意见维护</li>
			</#if>
		</ul>
</div>
<div id="adminDiv"></div>
</@htmlmacro.moduleDiv>