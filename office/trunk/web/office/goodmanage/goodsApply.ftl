<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var searchType = $("#searchType").val();
	var searchName = $("#searchName").val();
	load("#goodsApplyListDiv", "${request.contextPath}/office/goodmanage/goodmanage-goodsApply-list.action?searchType="+searchType+"&searchName="+searchName);
}

function doApply(goodsId){
	openDiv("#goodsApplyLayer", "#goodsApplyLayer .close,#goodsApplyLayer .submit,#goodsApplyLayer .reset", "${request.contextPath}/office/goodmanage/goodmanage-goodsApply-add.action?officeGoods.id="+goodsId, null, null, "900px");
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10">
			<span class="fn-left">物品类别：</span>
		</div>
		<div class="select_box fn-left mr-10">
		<@common.select style="width:150px;" valId="searchType" valName="searchType" myfunchange="doSearch" >
			<a val=""><span>全部</span></a>
        	<#if goodsTypeList?exists && (goodsTypeList?size>0)>
            	<#list goodsTypeList as type>
            		<a val="${type.typeId}" <#if searchType?default('') == type.typeId>class="selected"</#if>><span>${type.typeName}</span></a>
            	</#list>
        	</#if>
		</@common.select></div>
    	<div class="query-tt ml-10"><span class="fn-left">物品名称：</span></div>
        <input name="searchName" id="searchName" value="${searchName!}" maxLength="50" class="input-txt fn-left" style="width:170px;"/>
		<a href="javascript:void(0)" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="goodsApplyListDiv"></div>
<div class="popUp-layer" id="goodsApplyLayer" style="display:none;width:700px;"></div>
<script>
	$(document).ready(function(){
		doSearch();
	});
</script>
</@common.moduleDiv>