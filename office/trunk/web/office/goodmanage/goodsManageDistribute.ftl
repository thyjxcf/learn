<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
function doSave(){
	if(!isActionable("#btnSave")){
		return;
	}
	
	if(!checkAllValidate("#contentDiv")){
		return;
	}
	
	var purchaseTime = $("#purchaseTime").val();
	var distributeTime = $("#distributeTime").val();
	if(distributeTime != ""){
		if(compareDate(purchaseTime, distributeTime)==1){
			showMsgError("物品发放时间不能小于购买时间！");
			return;
		}
	}
	
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/goodmanage/goodmanage-goodsManage2-save.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#addform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSave").attr("class", "abtn-blue");
	if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			   }else{
			   	   showMsgError(data.promptMessage);
				   return;
			   }
	}else{
		   		showMsgSuccess("保存成功！","",function(){
		   			closeDiv("#goodsAddLayer");
		   			doSearch();
				});
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>物品发放</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="addform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="officeGoodsDistribute.id" value="${officeGoodsDistribute.id?default('')}">
    	<input type="hidden" id="unitId" name="officeGoodsDistribute.unitId" value="${officeGoodsDistribute.unitId?default('')}">
    	<input type="hidden" id="addUserId" name="officeGoodsDistribute.addUserId" value="${officeGoodsDistribute.addUserId?default('')}">  
	    <tr>
	      	<th style="width:15%">物品名称：</th>
	    	<td style="width:35%">
    			<input type="text" msgName="物品名称" class="input-txt fn-left input-readonly" readonly="readonly" id="name" name="officeGoodsDistribute.name" maxlength="50" value="${officeGoodsDistribute.name!}" style="width:180px;">
	    	</td>
	    	<th style="width:15%">物品类别：</th>
	    	<td style="width:35%">
	    		<input type="hidden" id="type" name="officeGoodsDistribute.type" value="${officeGoodsDistribute.type?default('')}">
	    		<input type="text" msgName="物品类别" class="input-txt fn-left input-readonly" readonly="readonly" id="typeName" name="officeGoodsDistribute.typeName" maxlength="50" value="${officeGoodsDistribute.typeName!}" style="width:180px;">
	    	</td>
	    </tr>
	    <tr>
	    	<th style="width:15%">规格型号：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="规格型号" class="input-txt fn-left input-readonly" readonly="readonly" id="model" name="officeGoodsDistribute.model" maxlength="50" value="${officeGoodsDistribute.model!}" style="width:180px;">
	    	</td>	
	      	<th style="width:15%">单价：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="单价" class="input-txt fn-left input-readonly" readonly="readonly" id="price" name="officeGoodsDistribute.price" maxlength="10" value="${(officeGoodsDistribute.price?string('0.00'))?if_exists}" style="width:180px;">
	    	</td>
	    </tr>
	    <tr>
	    	<th style="width:15%">单位：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="单位" class="input-txt fn-left input-readonly" readonly="readonly" id="goodsUnit" name="officeGoodsDistribute.goodsUnit" maxlength="30" value="${officeGoodsDistribute.goodsUnit!}" style="width:180px;">
	    	</td>	
	    	<th style="width:15%">数量：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="数量" class="input-txt fn-left input-readonly" readonly="readonly" id="amount" name="officeGoodsDistribute.amount" maxlength="8" value="${officeGoodsDistribute.amount!}" style="width:180px;">
	    	</td>
	    </tr>
	    <tr>
	    	<th style="width:15%">购买时间：</th>
	    	<td <#if officeGoodsDistribute.id?exists>style="width:35%"<#else>style="width:85%" colspan="3"</#if>>
	    		<@htmlmacro.datepicker class="input-txt input-readonly" readonly="readonly" style="width:180px;" name="officeGoodsDistribute.purchaseTime" id="purchaseTime" size="20" maxlength="19" msgName="购买时间" value="${(officeGoodsDistribute.purchaseTime?string('yyyy-MM-dd'))?if_exists}" dateFmt="yyyy-MM-dd"/>
	    	</td>		
	    	<th style="width:15%">使用人：</th>
	    	<td style="width:35%">
    		<@commonmacro.selectAddressBookLayer idObjectId="receiverId" nameObjectId="receiverName" detailObjectId="detailNames" currentUnit="true" callback="resetUserIds">
				<input type="hidden" name="officeGoodsDistribute.receiverId" id="receiverId" value="${officeGoodsDistribute.receiverId!}">
				<input type="hidden" name="officeGoodsDistribute.receiverName"  id="receiverName"  value="${officeGoodsDistribute.receiverName!}">
				<input type="text" name="detailNames" id="detailNames" readonly="readonly" notNull="true" msgName="使用人" class="select_current02 fn-left" style="width:180px" value="${officeGoodsDistribute.receiverName!}">
				<span class="c-orange mt-5 ml-10">*</span> 
			</@commonmacro.selectAddressBookLayer>
	    	</td>
	    </tr>
	    <tr>
	    	<th style="width:15%">发放时间：</th>
	    	<td style="width:85%" colspan="3">
	    		<@htmlmacro.datepicker class="input-txt" style="width:180px;" name="officeGoodsDistribute.distributeTime" id="distributeTime" notNull="true" size="20" maxlength="19" msgName="发放时间" value="${(officeGoodsDistribute.distributeTime?string('yyyy-MM-dd'))?if_exists}" dateFmt="yyyy-MM-dd"/>
	    		<span class="c-orange mt-5 ml-5">*</span>
	    	</td>
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="备注" class="text-area input-readonly fn-left" readonly="true" id="goodsRemark" name="officeGoodsDistribute.goodsRemark" maxLength="1000" colspan="3" style="width:470px;" value="${officeGoodsDistribute.goodsRemark!}" />
	    </tr>
	    <tr>
	    	<@htmlmacro.tdt msgName="发放说明" id="distributeRemark" name="officeGoodsDistribute.distributeRemark" maxLength="1000" colspan="3" style="width:470px;" value="${officeGoodsDistribute.distributeRemark!}" />
	    </tr>
    </table>
</form>    
</div>
<p class="dd">
    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave();" id="btnSave">保存</a>
    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>
<script>
function resetUserIds(){
	if($("#receiverId").val().length > 0){
		var receiverId = $("#receiverId").val().split(",");
		var divSpan = "";
		if(receiverId.length>1){
	    	showMsgWarn("使用人数量不能大于1个！");
	    	$("#receiverId").val("");
	    	$("#receiverName").val("");
	    	$("#detailNames").val("");
	   	}
	}
}
vselect();
</script>
</@htmlmacro.moduleDiv>