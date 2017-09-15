<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function changeRadio(ele){
	var radiovalue = ele.value;
	if(radiovalue=="2"){
		jQuery("#pass").attr("style","display:display");
		jQuery("#faile").attr("style","display:none");
		jQuery("#passWrittenOpinion").attr("notNull","true");
		jQuery("#faileWrittenOpinion").attr("notNull","false");
	}else{
		jQuery("#pass").attr("style","display:none");
		jQuery("#faile").attr("style","display:display");
		jQuery("#passWrittenOpinion").attr("notNull","false");
		jQuery("#faileWrittenOpinion").attr("notNull","true");
	}
}

function doSave(){
	if(!isActionable("#btnSave")){
		return;
	}
	var radio = jQuery('input[name="purchase\\.purchaseState"]:checked').val();
	var passWrittenOpinion = document.getElementById("passWrittenOpinion").value;
	var faileWrittenOpinion = document.getElementById("faileWrittenOpinion").value;
	if(passWrittenOpinion != "--请选择--" && radio == "2"){
		document.getElementById("passOpinion").value = passWrittenOpinion;
		document.getElementById("purchaseOpinion").value = passWrittenOpinion;
	}
	if(faileWrittenOpinion != "--请选择--" && radio == "3"){
		document.getElementById("faileOpinion").value = faileWrittenOpinion;
		document.getElementById("purchaseOpinion").value = faileWrittenOpinion;
	}
	
	if(!checkAllValidate("#contentDiv")){
		return;
	}
	$("#btnSave").attr("class", "abtn-unable");
	
	var options = {
       url:'${request.contextPath}/office/asset/assetAdmin-savePurchaseAudit.action', 
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
		   		showMsgSuccess("保存成功！","",function(){
				   load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-purchaseAudit.action?stateQuery="+stateQuery);
				});
				return;
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>采购审核</span></p>
<div class="wrap pa-10" id="contentDiv">
<form action="" method="post" name="mainform" id="mainform">
	<input type="hidden" id="id" name="purchase.id" value="${purchase.id?default('')}"/>
	<input type="hidden" id="purchaseOpinion" name="purchase.purchaseOpinion" value="${purchase.purchaseOpinion?default('')}"/>
	<table border="0" cellspacing="0" cellpadding="0" class="table-form">
		<tr>
			<th><span class="c-orange mr-5">*</span>请购单编号：</th>
			<td  colspan="3">${purchase.applyCode?default('')}</td>	
    	</tr> 
	    <tr>  
			<th width="20%"><span class="c-orange mr-5">*</span>类别：</th>
			<td width="30%" style="min-width:100px;">${purchase.categoryName?default('')}</td>
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
	    	<th><span class="c-orange mr-5">*</span>采购时间：</th>
	    	<td>${(purchaseDate?string('yyyy-MM-dd'))?if_exists}
	    	</td>
	    </tr>
	    <tr>	
	    	<th><span class="c-orange mr-5">*</span>申请单价：</th>
	    	<td>${(purchase.unitPrice?string('0.00'))?if_exists}
	    	</td>
	    	<th><span class="c-orange mr-5">*</span>申请总价：</th>
	    	<td>${(purchase.totalUnitPrice?string('0.00'))?if_exists}
	    	</td>
	    </tr>
	    <tr>
	    	<th nowrap="nowrap"><span class="c-orange mr-5">*</span>实际采购单价：</th>
	    	<td>${(purchase.purchasePrice?string('0.00'))?if_exists}
	    	</td>
	    	<th nowrap="nowrap"><span class="c-orange mr-5">*</span>实际采购总价：</th>
	    	<td>${(purchase.purchaseTotalPrice?string('0.00'))?if_exists}
	    	</td>
	    </tr>
	    <tr>
	    	<th><span class="c-orange mt-5 mr-5">*</span>审核：</th>
            <td colspan="3">
                <span class="ui-radio <#if purchase.purchaseState != "3">ui-radio-current</#if>" data-name="a"><input name="purchase.purchaseState" type="radio" class="radio" value="2" <#if purchase.purchaseState != "3">checked</#if> onclick="changeRadio(this);">通过</span>
                <span class="ui-radio <#if purchase.purchaseState = "3">ui-radio-current</#if>" data-name="a"><input name="purchase.purchaseState" type="radio" class="radio" value="3" <#if purchase.purchaseState=="3">checked</#if> onclick="changeRadio(this);">不通过</span>
            </td>
	    </tr>
	    <#if purchase.purchaseState == "1">
		   	 <tr id="pass" <#if purchase.purchaseState != "3">style="display:display"<#else>style="display:none"</#if>>
		    	<th><span class="c-orange mt-5 mr-5">*</span>审核意见：</th>
	            <td colspan="3">
		            <div class="ui-select-box fn-left"  style="width:300px;" >
			            <input name="passWrittenOpinion" id="passWrittenOpinion" type="text" class="ui-select-txt" value="" <#if purchase.purchaseState != "3">notNull="true"</#if> msgName="审核意见" />
			            <input name="passOpinion" id="passOpinion" type="hidden" value=""   class="ui-select-value" />
			            <a class="ui-select-close"></a>
			            <div class="ui-option" >
			            	<div class='a-wrap'>
			            	<a val=""><span>--请选择--</span></a>
			                <#list passOpinion as opinion>
			                	<a val="${opinion!}" title="${opinion!}"  <#if purchase.purchaseOpinion?exists && purchase.purchaseOpinion==opinion>selected</#if>><span>${opinion!}</span></a>
			                </#list>
			                </div>
			            </div>
		    		</div>
	            </td>
	        </tr>
	        <tr id="faile" <#if purchase.purchaseState == "3">style="display:display"<#else>style="display:none"</#if>>
		    	<th><span class="c-orange mt-5 mr-5">*</span>审核意见：</th>
	            <td colspan="3">
		            <div class="ui-select-box fn-left"  style="width:300px;" >
			            <input name="faileWrittenOpinion" id="faileWrittenOpinion" type="text" class="ui-select-txt" value="" <#if purchase.purchaseState == "3">notNull="true"</#if> msgName="审核意见" />
			            <input name="faileOpinion" id="faileOpinion" type="hidden" value=""   class="ui-select-value" />
			            <a class="ui-select-close"></a>
			            <div class="ui-option" >
			            	<div class='a-wrap'>
			            	<a val=""><span>--请选择--</span></a>
			                <#list faileOpinion as opinion>
			                	<a val="${opinion!}" title="${opinion!}"  <#if purchase.purchaseOpinion?exists && purchase.purchaseOpinion==opinion>selected</#if>><span>${opinion!}</span></a>
			                </#list>
			                </div>
			            </div>
		    		</div>
	            </td>
	        </tr>
        <#else>
	        <tr>
		    	<th><span class="c-orange mt-5 mr-5">*</span>审核意见：</th>
	            <td colspan="3">
	                ${purchase.purchaseOpinion?default('')}
	            </td>
		    </tr>
        </#if>
    </table>
     <p class="t-center pt-20">
     <#if purchase.purchaseState?default("") =="1">
        <a href="javascript:void(0);" id="btnSave" onclick="doSave();" class="abtn-blue">保存</a>
     </#if>   
        <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
    </p>
</form>
</div>
</@htmlmacro.moduleDiv>