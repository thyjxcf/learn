<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form name="auditform" id="auditform" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<th width="8%"><nobr>物品名称</nobr></th>
		<th width="8%"><nobr>规格型号</nobr></th>
		<th width="5%"><nobr>库存数量</nobr></th>
		<th width="5%"><nobr>单位</nobr></th>
		<th width="5%"><nobr>单价</nobr></th>
		<th width="7%"><nobr>物品类别</nobr></th>
		<th width="6%"><nobr>是否需归还</nobr></th>
		<th width="5%"><nobr>申请数量</nobr></th>
		<th width="5%"><nobr>申请人</nobr></th>
		<th width="8%"><nobr>申请部门</nobr></th>
		<th width="10%"><nobr>申请说明</nobr></th>
		<th width="8%"><nobr>申请日期</nobr></th>
		<th width="10%"><nobr>状态</nobr></th>
		<th width="10%" class="t-center"><nobr>操作</nobr></th>
	</tr>
	<#if goodsRequestList?exists && goodsRequestList?size gt 0>
		<#list goodsRequestList as item>
			<tr>
				<td>${item.reqGoods.name!}</td>
				<td>${item.reqGoods.model!}</td>
				<td>${item.reqGoods.amount?string!}</td>
				<td>${item.reqGoods.goodsUnit!}</td>
				<td>${item.reqGoods.price?string("0.00")!}</td>
				<td>${item.reqGoods.typeName!}</td>
				<td><#if item.reqGoods.isReturned>不需归还<#else>需归还</#if></td>
				<td>${item.amount?string!}</td>
				<td>${item.reqUserName!}</td>
				<td>${item.reqDeptName!}</td>
				<td title="${item.remark!}"><@common.cutOff str='${item.remark!}' length=10/></td>
				<td>${item.creationTime?string("yyyy-MM-dd")}</td>
				<td title="${item.stateStr!}"><@common.cutOff str='${item.stateStr!}' length=15/></td>
				<td class="t-center">
				<#if item.state == 0>
					<a href="javascript:void(0);" onclick="doPass('${item.id}');">通过</a>
					<a href="javascript:void(0);" onclick="doNotPass('${item.id}');" class="ml-10">不通过</a>
		     	</#if>
		     	</td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="14"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if goodsRequestList?exists && goodsRequestList?size gt 0>
<@common.Toolbar container="#goodsAuditListDiv"/>
</#if>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>