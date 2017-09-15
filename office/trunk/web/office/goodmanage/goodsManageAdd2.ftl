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
	if(parseInt(amount)==0){
		showMsgError("物品数量不得为0");
		return;
	}
	
	if(parseInt(amount)>100){
		showMsgWarn("物品数量请控制在100之内");
		return;
	}
	
	var price = $("#price").val();
	if(parseFloat(price)==0){
		showMsgError("物品单价不得为0");
		return;
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
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span><#if officeGoodsDistribute.id?exists>物品信息维护<#else>物品登记</#if></span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="addform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="officeGoodsDistribute.id" value="${officeGoodsDistribute.id?default('')}">
    	<input type="hidden" id="unitId" name="officeGoodsDistribute.unitId" value="${officeGoodsDistribute.unitId?default('')}">
    	<input type="hidden" id="addUserId" name="officeGoodsDistribute.addUserId" value="${officeGoodsDistribute.addUserId?default('')}">
    	<input type="hidden" id="receiverId" name="officeGoodsDistribute.receiverId" value="${officeGoodsDistribute.receiverId?default('')}">
    	<input type="hidden" id="distributeRemark" name="officeGoodsDistribute.distributeRemark" value="${officeGoodsDistribute.distributeRemark?default('')}">
    	<input type="hidden" id="distributeTime" name="officeGoodsDistribute.distributeTime" value="${(officeGoodsDistribute.distributeTime?string('yyyy-MM-dd'))?if_exists}">
	    <tr>
	      	<th style="width:15%">物品名称：</th>
	    	<td style="width:35%">
    			<input type="text" msgName="物品名称" class="input-txt fn-left" id="name" name="officeGoodsDistribute.name" maxlength="50" notNull="true" value="${officeGoodsDistribute.name!}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    	<th style="width:15%">物品类别：</th>
	    	<td style="width:35%">
	    		<@htmlmacro.select style="width:190px;" valId="type" valName="officeGoodsDistribute.type" notNull="true" msgName="物品类别">
					<a val=""><span>----请选择----</span></a>
		        	<#if goodsTypeList?exists && (goodsTypeList?size>0)>
		            	<#list goodsTypeList as item>
		            		<a val="${item.typeId}" <#if officeGoodsDistribute.type?default('') == item.typeId>class="selected"</#if>><span>${item.typeName}</span></a>
		            	</#list>
		        	</#if>
				</@htmlmacro.select>
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    </tr>
	    <tr>
	    	<th style="width:15%">规格型号：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="规格型号" class="input-txt fn-left" id="model" name="officeGoodsDistribute.model" maxlength="50" value="${officeGoodsDistribute.model!}" style="width:180px;">
	    	</td>	
	      	<th style="width:15%">单价：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="单价" class="input-txt fn-left" id="price" name="officeGoodsDistribute.price" notNull="true" regex="/^(^[0-9]{0,5}$)|(^[0-9]{0,5}\.[0-9]{1,2}$)$/" regexMsg="最多5位整数2位小数" maxlength="10" value="${(officeGoodsDistribute.price?string('0.00'))?if_exists}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    </tr>
	    <tr>
	    	<th style="width:15%">单位：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="单位" class="input-txt fn-left" id="goodsUnit" name="officeGoodsDistribute.goodsUnit" maxlength="30" notNull="true" value="${officeGoodsDistribute.goodsUnit!}" style="width:180px;">
		    	<span class="c-orange mt-5 ml-10">*</span>
	    	</td>	
	    	<th style="width:15%">数量：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="数量" class="input-txt fn-left" id="amount" name="officeGoodsDistribute.amount" notNull="true" regex="/^\d*$/" regexMsg="请正确填写数量" maxlength="8" value="${officeGoodsDistribute.amount!}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    </tr>
	    <tr>
	    	<th style="width:15%">购买时间：</th>
	    	<td style="width:85%" colspan="3">
	    		<@htmlmacro.datepicker class="input-txt" style="width:180px;" name="officeGoodsDistribute.purchaseTime" id="purchaseTime" size="20" maxlength="19" msgName="购买时间" value="${(officeGoodsDistribute.purchaseTime?string('yyyy-MM-dd'))?if_exists}" dateFmt="yyyy-MM-dd"/>
	    	</td>		
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="备注" id="goodsRemark" name="officeGoodsDistribute.goodsRemark" maxLength="1000" colspan="3" style="width:470px;" value="${officeGoodsDistribute.goodsRemark!}" />
	    </tr>
    </table>
</form>    
</div>
<p class="dd">
    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave();" id="btnSave">保存</a>
    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>
<script>
vselect();
</script>
</@htmlmacro.moduleDiv>