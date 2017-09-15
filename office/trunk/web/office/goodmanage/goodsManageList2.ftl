<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form id="listform" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<th class="t-center" width="5%"><nobr>选择</nobr></th>
		<th width="8%">物品名称</th>
		<th width="8%">规格型号</th>
		<th width="6%">单价</th>
		<th width="6%">单位</th>
		<th width="6%">数量</th>
		<th width="7%">购买时间</th>
		<th width="8%">物品类别</th>
		<th width="7%">使用人</th>
		<th width="10%">使用人部门</th>
		<th width="7%">发放时间</th>
		<th width="10%">发放说明</th>
		<th width="12%" style="text-align:center;">操作</th>
	</tr>
	<#if goodsDistributeList?exists && goodsDistributeList?size gt 0>
		<#list goodsDistributeList as goods>
			<tr style="word-break:break-all; word-wrap:break-word;">
				<td class="t-center"><p><span class="ui-checkbox">
		    		<input type="checkbox" class="chk" name="checkid" value="${goods.id?default('')}">
		    		</span></p>
		    	</td>
				<td>${goods.name!}</td>
				<td>${goods.model!}</td>
				<td>${goods.price?string("0.00")!}</td>
				<td>${goods.goodsUnit!}</td>
				<td>${goods.amount?string!}</td>
				<td>${(goods.purchaseTime?string('yyyy-MM-dd'))?if_exists}</td>
				<td>${goods.typeName!}</td>
				<td>${goods.receiverName?string!}</td>
				<td>${goods.receiverDeptName?string!}</td>
				<td>${(goods.distributeTime?string('yyyy-MM-dd'))?if_exists}</td>
				<td title="${goods.distributeRemark!}"><@common.cutOff str='${goods.distributeRemark!}' length=10/></td>
				<td class="t-center">
	            	<a href="javascript:void(0);" onclick="doGoodsManageEdit('${goods.id}');">修改物品</a>
					<a href="javascript:void(0);" onclick="doGoodsDistribute('${goods.id}');" class="ml-10">发放物品</a>
	            </td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="13"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if goodsDistributeList?exists && goodsDistributeList?size gt 0>
<@common.Toolbar container="#goodsManageListDiv">
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</input></span>
	<a href="javascript:void(0);" id="btnDelete" class="abtn-blue"  onclick="doDelete()"> 删除</a>
</@common.Toolbar>
</#if>
</form>
<script>
function doGoodsManageEdit(goodsId){
	openDiv("#goodsAddLayer", "#goodsAddLayer .close,#goodsAddLayer .submit,#goodsAddLayer .reset", "${request.contextPath}/office/goodmanage/goodmanage-goodsManage2-edit.action?officeGoodsDistribute.id="+goodsId, null, null, "900px");
}
function doGoodsDistribute(goodsId){
	openDiv("#goodsAddLayer", "#goodsAddLayer .close,#goodsAddLayer .submit,#goodsAddLayer .reset", "${request.contextPath}/office/goodmanage/goodmanage-goodsManage2-distribute.action?officeGoodsDistribute.id="+goodsId, null, null, "900px");
}
function doDelete(){
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("没有选要删除的记录，请先选择!");
		return;
	}
	
	if(!showConfirm('您确认要删除吗？')){
		return;
	}
	$("#btnDelete").attr("class", "abtn-unable");	
	var options = {
       url:'${request.contextPath}/office/goodmanage/goodmanage-goodsDelete2.action', 
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