<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form name="myGoodsform" id="myGoodsform" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<th width="10%">物品名称</th>
		<th width="8%">规格型号</th>
		<th width="8%">单价</th>
		<th width="8%">单位</th>
		<th width="8%">数量</th>
		<th width="10%">购买时间</th>
		<th width="13%">备注</th>
		<th width="10%">物品类别</th>
		<th width="10%">发放时间</th>
		<th width="15%">发放说明</th>
	</tr>
	<#if goodsDistributeList?exists && goodsDistributeList?size gt 0>
		<#list goodsDistributeList as item>
			<tr style="word-break:break-all; word-wrap:break-word;">
				<td>${item.name!}</td>
				<td>${item.model!}</td>
				<td>${item.price?string("0.00")!}</td>
				<td>${item.goodsUnit!}</td>
				<td>${item.amount?string!}</td>
				<td>${(item.purchaseTime?string("yyyy-MM-dd"))?if_exists}</td>
				<td title="${item.goodsRemark!}"><@common.cutOff str='${item.goodsRemark!}' length=15/></td>
				<td>${item.typeName!}</td>
				<td>${(item.distributeTime?string("yyyy-MM-dd"))?if_exists}</td>
				<td title="${item.distributeRemark!}"><@common.cutOff str='${item.distributeRemark!}' length=15/></td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="10"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if goodsDistributeList?exists && goodsDistributeList?size gt 0>
<@common.Toolbar container="#myGoodsListDiv"/>
</#if>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>