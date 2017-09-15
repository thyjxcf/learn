<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form>
<@common.tableList id="tablelist">
	<tr>
		<th width="10%">物品名称</th>
		<th width="10%">规格型号</th>
		<th width="10%">库存数量</th>
		<th width="10%">单价</th>
		<th width="10%">单位</th>
		<th width="20%">备注</th>
		<th width="12%">物品类别</th>
		<th width="8%">是否需归还</th>
		<th width="10%" class="t-center">操作</th>
	</tr>
	<#if goodsList?exists && goodsList?size gt 0>
		<#list goodsList as goods>
			<tr>
				<td>${goods.name!}</td>
				<td>${goods.model!}</td>
				<td>${goods.amount?string!}</td>
				<td>${goods.price?string("0.00")!}</td>
				<td>${goods.goodsUnit!}</td>
				<td title="${goods.remark!}"><@common.cutOff str='${goods.remark!}' length=15/></td>
				<td>${goods.typeName!}</td>
				<td><#if goods.isReturned>不需归还<#else>需归还</#if></td>
				<td class="t-center">
				<#if goods.amount gt 0>
	            	<a href="javascript:doApply('${goods.id!}');"><img alt="申请" src="${request.contextPath}/static/images/icon/edit.png"></a>
	            </#if>
	            </td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="9"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if goodsList?exists && goodsList?size gt 0>
<@common.Toolbar container="#goodsApplyListDiv"/>
</#if>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>