<#import "/common/htmlcomponent.ftl" as common />
<#assign GOODS_REGISTER = stack.findValue("@net.zdsoft.office.goodmanage.constant.OfficeGoodsConstants@GOODS_REGISTER") >
<@common.moduleDiv titleName="">
<div id="goodsChangeLogDiv">
<form>
<div class="query-part mb-5">
	<div class="fn-left ml-10 data-total">${officeGoods.name!}的变动记录</div>
    <div class="fn-clear"></div>
</div>
<@common.tableList id="tablelist">
	<tr>
		<th class="t-center" width="10%">序号</th>
		<th width="20%">原因</th>
		<th width="15%">数量</th>
		<th width="35%">变动说明</th>
		<th width="20%">时间</th>
	</tr>
	<#if goodsChangeLogList?exists && goodsChangeLogList?size gt 0>
		<#assign index = 1/>
		<#list goodsChangeLogList as item>
			<tr>
				<td class="t-center">${index}</td>
				<td>${item.reason!}</td>
				<td><#if item.reason! != GOODS_REGISTER && item.amount gt 0>+</#if>${item.amount?string!}</td>
				<td>${item.remark!}</td>
				<td>${item.creationTime?string("yyyy-MM-dd HH:mm:ss")}</td>
			</tr>
			<#assign index = index + 1>
		</#list>
	<#else>
		<tr>
	        <td colspan="5"><p class="no-data mt-50 mb-50">还没有变动记录哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if goodsChangeLogList?exists && goodsChangeLogList?size gt 0>
<@common.Toolbar container="#goodsChangeLogDiv">
	<a href="javascript:void(0);" class="abtn-blue"  onclick="doBack()"> 返回</a>
</@common.Toolbar>
</#if>
</form>
</div>
<script>
function doBack(goodsId){
	load("#adminDiv", "${request.contextPath}/office/goodmanage/goodmanage-goodsManage.action?searchType=${searchType!}&searchName=${searchName!}");
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>