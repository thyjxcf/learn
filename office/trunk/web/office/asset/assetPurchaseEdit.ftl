<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doSave(){
	if(!isActionable("#btnSave")){
		return;
	}
	
	if(!checkAllValidate("#contentDiv")){
		return;
	}
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/asset/assetAdmin-savePurchase.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#mainform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSave").attr("class", "abtn-blue");
	var stateQuery = '${stateQuery!}';
	if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			   }else{
			   	   showMsgError(data.promptMessage);
				   return;
			   }
	}else{
		   		showMsgSuccess(data.promptMessage,"",function(){
				   load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-purchaseList.action?stateQuery="+stateQuery);
				});
				return;
	}
}

function dealPrice(dom, type){
	if("" == trim(dom.value) || isNaN(dom.value)){
		return;
	}
	var s = new Number(dom.value);
	dom.value =s.toFixed(2);
	
	if("1"==type){
		var m = '${purchase.assetNumber?default('')}';
		var tot = dom.value*m;
		jQuery("#purchaseTotalPrice").val(tot.toFixed(2));
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>采购单维护</span></p>
<div class="wrap pa-10" id="contentDiv">
<form action="" method="post" name="mainform" id="mainform">
	<input type="hidden" id="id" name="purchase.id" value="${purchase.id?default('')}"/>
	<table border="0" cellspacing="0" cellpadding="0" class="table-form">
		<tr>
			<th><span class="c-orange mr-5">*</span>请购单编号：</th>
			<td  colspan="3">${purchase.applyCode?default('')}</td>	
    	</tr> 
	    <tr>  
			<th width="20%"><span class="c-orange mr-5">*</span>类别：</th>
			<td width="30%">${purchase.categoryName?default('')}</td>
	      <th width="20%"><span class="c-orange mr-5">*</span>物品名称：</th>
	      <td width="30%">${purchase.assetName?default('')}</td>
	    </tr>
	    <tr>
	      	<th>规格：</th>
	    	<td>${purchase.assetFormat?default('')}
	    	</td>	
	    	<th><span class="c-orange mr-5">*</span>数量：</th>
	    	<td>${purchase.assetNumber?default('')}
	    	</td>
	    </tr>
	    <tr>	
	    	<th><span class="c-orange mr-5">*</span>单位：</th>
	    	<td>${purchase.assetUnit?default('')}
	    	</td>
	    	<th><span class="c-orange mr-5">*</span>申请单价：</th>
	    	<td>${(purchase.unitPrice?string('0.00'))?if_exists}
	    	</td>
	    </tr>
	    <tr>
	    	<th><span class="c-orange mr-5">*</span>申请总价：</th>
	    	<td>${(purchase.totalUnitPrice?string('0.00'))?if_exists}
	    	</td>
	    	<th><span class="c-orange mr-5">*</span>采购时间：</th>
	    	<td>
	    		<#if assetPurAuth?default(false) && purchase.purchaseState?default("") =="0">
	    		<@htmlmacro.datepicker  name="purchaseDate" id="purchaseDate" notNull="true" msgName="采购时间" style="width:140px;" value="${(purchaseDate?string('yyyy-MM-dd'))?if_exists}"/>
	    		<#else>
			   	<input value="${(purchaseDate?string('yyyy-MM-dd'))?if_exists}" style="width:140px;" class="input-txt input-readonly" readonly  /> 		
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<th><span class="c-orange mr-5">*</span>实际采购单价：</th>
	    	<td>
	    	 	<input name="purchasePrice" id="purchasePrice" onblur="dealPrice(this,'1');" notNull="true" msgName="" regex="/^(^[0-9]{0,5}$)|(^[0-9]{0,5}\.[0-9]{1,2}$)$/" regexMsg="最多5位整数2位小数" maxlength="12" value="${(purchase.purchasePrice?string('0.00'))?if_exists}" 
	    		<#if assetPurAuth?default(false) && purchase.purchaseState?default("") =="0">class="input-txt"<#else>class="input-txt input-readonly" readonly</#if> style="width:140px;"/>
	    	</td>
	    	<th><span class="c-orange mr-5">*</span>实际采购总价：</th>
	    	<td>
	    		<input name="purchaseTotalPrice" id="purchaseTotalPrice" onblur="dealPrice(this,'2');" notNull="true" msgName="" regex="/^(^[0-9]{0,5}$)|(^[0-9]{0,5}\.[0-9]{1,2}$)$/" regexMsg="最多5位整数2位小数" maxlength="12" value="${(purchase.purchaseTotalPrice?string('0.00'))?if_exists}" 
	    		<#if assetPurAuth?default(false) && purchase.purchaseState?default("") =="0">class="input-txt"<#else>class="input-txt input-readonly" readonly</#if> style="width:140px;"/>
	    	</td>
	    </tr>
	    
	    
    </table>
     <p class="t-center pt-20">
     <#if assetPurAuth?default(false) && purchase.purchaseState?default("") =="0" >
        <a href="javascript:void(0);" id="btnSave" onclick="doSave();" class="abtn-blue">保存</a>
     </#if>   
        <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
    </p>
</form>
</div>
</@htmlmacro.moduleDiv>