<#import "/common/htmlcomponent.ftl" as common />
<#assign GOODS_HAS_NOT_REQ = stack.findValue("@net.zdsoft.office.goodmanage.constant.OfficeGoodsConstants@GOODS_HAS_NOT_REQ") >
<@common.moduleDiv titleName="">
<form id="listform" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<#if goodsManageAuth>
		<th class="t-center" width="5%"><nobr>选择</nobr></th>
		</#if>
		<th width="10%">物品名称</th>
		<th width="8%">规格型号</th>
		<th width="7%">库存数量</th>
		<th width="7%">单价</th>
		<th width="7%">单位</th>
		<th width="8%">购买时间</th>
		<th >备注</th>
		<th width="10%">物品类别</th>
		<th width="8%">是否需归还</th>
		<th width="8%" style="text-align:center;">操作</th>
		<th width="8%" style="text-align:center;">变动记录</th>
	</tr>
	<#if goodsList?exists && goodsList?size gt 0>
		<#list goodsList as goods>
			<tr>
				<#if goodsManageAuth>
				<td class="t-center"><#if goods.reqTag == GOODS_HAS_NOT_REQ><p><span class="ui-checkbox">
		    		<input type="checkbox" class="chk" name="checkid" value="${goods.id?default('')}">
		    		</span></p></#if>
		    	</td>
		    	</#if>
				<td>${goods.name!}</td>
				<td>${goods.model!}</td>
				<td>${goods.amount?string!}</td>
				<td>${goods.price?string("0.00")!}</td>
				<td>${goods.goodsUnit!}</td>
				<td>${(goods.purchaseDate?string('yyyy-MM-dd'))?if_exists}</td>
				<td title="${goods.remark!}"><@common.cutOff str='${goods.remark!}' length=15/></td>
				<td>${goods.typeName!}</td>
				<td><#if goods.isReturned>不需归还<#else>需归还</#if></td>
				<td class="t-center">
	            	<a href="javascript:doGoodsManageEdit('${goods.id!}');"><img alt="修改" src="${request.contextPath}/static/images/icon/edit.png"></a>
	            </td>
				<td class="t-center">
	            	<a href="javascript:getGoodsChangeLog('${goods.id!}');"><img alt="查看" src="${request.contextPath}/static/images/icon/view.png"></a>
	            </td>
			</tr>
		</#list>
	<#else>
		<tr>
			<#if goodsManageAuth>
	        <td colspan="12"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	        <#else>
	        <td colspan="11"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	        </#if>
	   	</tr>
	</#if>
</@common.tableList>
<#if goodsManageAuth && goodsList?exists && goodsList?size gt 0>
<@common.Toolbar container="#goodsManageListDiv">
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</input></span>
	<a href="javascript:void(0);" id="btnDelete" class="abtn-blue"  onclick="doDelete()"> 删除</a>
</@common.Toolbar>
</#if>
</form>
<script>
function getGoodsChangeLog(goodsId){
	var searchType = $("#searchType").val();
	var searchName = $("#searchName").val();
	load("#adminDiv", "${request.contextPath}/office/goodmanage/goodmanage-goodsChangeLog.action?officeGoods.id="+goodsId+"&searchType="+searchType+"&searchName="+searchName);
}
function doGoodsManageEdit(goodsId){
	openDiv("#goodsAddLayer", "#goodsAddLayer .close,#goodsAddLayer .submit,#goodsAddLayer .reset", "${request.contextPath}/office/goodmanage/goodmanage-goodsManage-edit.action?officeGoods.id="+goodsId, null, null, "900px");
}
function doDelete(){
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("没有选要删除的物品，请先选择!");
		return;
	}
	
	if(!showConfirm('您确认要删除物品吗？')){
		return;
	}
	$("#btnDelete").attr("class", "abtn-unable");	
	var options = {
       url:'${request.contextPath}/office/goodmanage/goodmanage-goodsDelete.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#listform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnDelete").attr("class", "abtn-blue");
	if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   return;
		   }else if(data.fieldErrorMap!=null){
			  $.each(data.fieldErrorMap,function(key,value){
				   addFieldError(key,value+"");
			  });
		   }else{
		   	   showMsgError(data.promptMessage);
			   return;
		   }
	}else{
	   		showMsgSuccess("删除成功! ","",doSearch);
			return;
	}
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>