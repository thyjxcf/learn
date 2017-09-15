<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<#include "/common/handlefielderror.ftl">
<#import "/common/commonmacro.ftl" as commonmacro>
<script>
function assetQuery(){
	var categoryId = document.getElementById("categoryId").value;
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	if(compareDate(queryBeginDate, queryEndDate) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
    load('#adminDiv','${request.contextPath}/office/asset/assetAdmin-assetQuery.action?queryCategoryId='+categoryId+'&queryBeginDate='+queryBeginDate+'&queryEndDate='+queryEndDate);  
}
function assetExport(){
	var categoryId = document.getElementById("categoryId").value;
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	if(compareDate(queryBeginDate, queryEndDate) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
    location.href='${request.contextPath}/office/asset/assetAdmin-assetExport.action?queryCategoryId='+categoryId+'&queryBeginDate='+queryBeginDate+'&queryEndDate='+queryEndDate;  
}
function doAssetToGoods(assetId){
	openDiv("#goodsAddLayer", "#goodsAddLayer .close,#goodsAddLayer .submit,#goodsAddLayer .reset", "${request.contextPath}/office/asset/assetAdmin-assetToGoods.action?asset.id="+assetId, null, null, "900px");
}

</script>
<div class="pub-table-inner">
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
	<div class="query-part">
	    <div class="query-tt b ml-10">类别：</div>
	    <div class="select_box fn-left">
		<input type="hidden" id="queryCategoryId" name="queryCategoryId" value="${queryCategoryId?default('')}"/>
		<@htmlmacro.select style="width:150px;" valName="categoryId" valId="categoryId" myfunchange="" notNull="true" msgName="类别">
			<a val=""><span>---请选择---</span></a>
		  	<#list assetCategoryList as item>
		  		<a val="${item.id}" title="${item.assetName!}" <#if queryCategoryId?exists && queryCategoryId==item.id>class="selected"</#if>><span>${item.assetName!}</span></a>
		  	</#list>
		</@htmlmacro.select>
		</div>
		
		<div class="query-tt b ml-10">采购时间：</div><div class="select_box fn-left">
	    <@htmlmacro.datepicker name="queryBeginDate" id="queryBeginDate" class="input-date" value="${(queryBeginDate?string('yyyy-MM-dd'))?if_exists}"/>
	   	&nbsp;-&nbsp;
	    <@htmlmacro.datepicker name="queryEndDate" id="queryEndDate" class="input-date" value="${(queryEndDate?string('yyyy-MM-dd'))?if_exists}"/>
		<a href="javascript:void(0);" onclick="assetQuery();" class="abtn-blue ml-10 fn-right" id="assetQuery" >查询</a>
	    <a href="javascript:void(0);" onclick="assetExport();" class="abtn-blue ml-10 fn-right" id="assetQuery" >导出</a>
		</div>
		
	    
	    <div class="fn-clear"></div>
	</div>
</div>
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="15%">类别</th>
		<th width="10%">物品名称</th>
		<th width="10%">数量</th>
		<th width="10%">单位</th>
		<th width="10%">单价</th>
		<th width="10%">总价</th>
		<th width="10%">申请人</th>
		<th width="10%">采购时间</th>
		<th width="15%" style="text-align:center;">同步至物品管理</th>
	</tr>
	<#if assetList?exists && (assetList?size>0)>
		<#list assetList as item>
		    <tr>
		    	<td>${item.categoryName?default("")}</td>
		    	<td><@htmlmacro.cutOff4List str="${item.assetName?default('')}" length=10 /></td>
				<td>${item.assetNumber?default("")}</td>
		    	<td>${item.assetUnit?default("")}</td>
				<td>${item.priceStr?default("")}</td>
				<td>${item.totalPriceStr?default("")}</td>
				<td>${item.applyUserName?default("")}</td>
				<td>${(item.purchaseDate?string('yyyy-MM-dd'))?if_exists}</td>
				<td class="t-center">
				<#if !item.isSyncToGoods>
	            	<a href="javascript:doAssetToGoods('${item.id!}');"><img alt="同步" src="${request.contextPath}/static/images/icon/edit.png"></a>
	            </#if>
	            </td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="11"><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#adminDiv"></@htmlmacro.Toolbar>
</form>
</div>
<div class="popUp-layer" id="goodsAddLayer" style="display:none;width:700px;"></div>
<script>
vselect();
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>