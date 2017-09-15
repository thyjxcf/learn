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
	var amount = $("#amount").val();
	var applyAmount = $("#applyAmount").val();
	
	if(parseInt(applyAmount)==0){
		showMsgError("申请数量不能为0！");
		return;
	}
	if(parseInt(amount) < parseInt(applyAmount)){
		showMsgError("申请数量不能超过库存数量！");
		return;
	}
	
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/goodmanage/goodmanage-goodsApply-save.action', 
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
		   		showMsgSuccess("申请成功！","",function(){
		   			closeDiv("#goodsApplyLayer");
		   			doSearch();
				});
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>领用申请</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="addform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="officeGoods.id" value="${officeGoods.id?default('')}">  
	    <tr>
	      	<th style="width:15%">物品名称：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="物品名称" class="input-txt fn-left input-readonly" readonly id="name" name="officeGoods.name" notNull="true" maxlength="50" value="${officeGoods.name!}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>	
	    	<th style="width:15%">规格型号：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="规格型号" class="input-txt fn-left input-readonly" readonly id="model" name="officeGoods.model" maxlength="50" value="${officeGoods.model!}" style="width:180px;">
	    	</td>	
	    </tr>
	    <tr>
	      	<th style="width:15%">库存数量：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="库存数量" class="input-txt fn-left input-readonly" readonly id="amount" name="officeGoods.amount" notNull="true" regex="/^\d*$/" regexMsg="请正确填写库存数量" maxlength="10" value="${((officeGoods.amount)?string)?if_exists}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    	<th style="width:15%">物品类别：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="物品类别" class="input-txt fn-left input-readonly" readonly id="typeName" name="officeGoods.typeName" notNull="true" maxlength="50" value="${officeGoods.typeName!}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    </tr>	
	    <tr>	
	    	<th style="width:15%">单价：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="单价" class="input-txt fn-left input-readonly" readonly id="price" name="officeGoods.price" notNull="true" regex="/^(^[0-9]{0,7}$)|(^[0-9]{0,7}\.[0-9]{1,2}$)$/" regexMsg="最多7位整数2位小数" maxlength="10" value="${((officeGoods.price)?string('0.00'))?if_exists}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    	<th style="width:15%">单位：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="单位" class="input-txt fn-left input-readonly" readonly id="goodsUnit" name="officeGoods.goodsUnit" notNull="true" maxlength="50" value="${officeGoods.goodsUnit!}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>	
	    </tr>
	    <tr>
	    	<th style="width:15%">申请数量：</th>
	    	<td style="width:85%" colspan="3">
	    		<input type="text" msgName="申请数量" class="input-txt fn-left" id="applyAmount" name="officeGoodsReq.amount" notNull="true" regex="/^\d*$/" regexMsg="请正确填写申请数量" maxlength="10" value="${((officeGoodsReq.amount)?string)?if_exists}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>		
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="备注" readonly="true" id="remark" name="officeGoods.remark" maxLength="1000" colspan="3" style="width:470px;" value="${(officeGoods.remark?default('')?trim)?if_exists}" />
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="申请说明" id="applyRemark" name="officeGoodsReq.remark" maxLength="1000" colspan="3" style="width:470px;" value="" />
	    </tr>
    </table>
</form>    
</div>
<p class="dd">
    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave();" id="btnSave">保存</a>
    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>
<script>vselect();</script>
</@htmlmacro.moduleDiv>