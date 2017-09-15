<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form name="myGoodsform" id="myGoodsform" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<th width="10%">物品名称</th>
		<th width="10%">规格型号</th>
		<th width="7%">单价</th>
		<th width="7%">单位</th>
		<th width="13%">备注</th>
		<th width="10%">物品类别</th>
		<th width="10%">申请数量</th>
		<th width="10%">状态</th>
		<th width="15%">审核意见</th>
		<th width="8%" class="t-center">操作</th>
	</tr>
	<#if goodsRequestList?exists && goodsRequestList?size gt 0>
		<#list goodsRequestList as item>
			<tr>
				<td>${item.reqGoods.name!}</td>
				<td>${item.reqGoods.model!}</td>
				<td>${item.reqGoods.price?string("0.00")!}</td>
				<td>${item.reqGoods.goodsUnit!}</td>
				<td title="${item.reqGoods.remark!}"><@common.cutOff str='${item.reqGoods.remark!}' length=15/></td>
				<td>${item.reqGoods.typeName!}</td>
				<td>${item.amount?string!}</td>
				<td><#if item.state==0>待审核<#elseif item.state==1>已通过<#elseif item.state==2>未通过<#else>已归还</#if></td>
				<td title="${item.advice!}"><@common.cutOff str='${item.advice!}' length=20/></td>
				<td class="t-center">
				<#if item.state == 1 && !item.reqGoods.isReturned>
					<a href="javascript:void(0);" onclick="doMyGoodsReturn('${item.id}');">归还</a>
				</#if>
				<#if item.state == 2>
					<a href="javascript:void(0);" onclick="doMyGoodsApply('${item.id}');">重新申请</a>
		     	</#if>
		     	</td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="10"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if goodsRequestList?exists && goodsRequestList?size gt 0>
<@common.Toolbar container="#myGoodsListDiv"/>
</#if>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>