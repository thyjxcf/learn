<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
function doSave(){
	if(!isActionable("#btnSave")){
		return;
	}
	
	var flag = true;
	if(!checkAllValidate("#contentDiv")){
		flag = false;
	}
	
	var goodsNameObj = document.getElementById("goodsNameSelectedName");
	if(trim(goodsNameObj.value)==''){
		$("#goodsNameFuzzyDiv").parent().find(".field_tip").hide();
		$("#goodsNameFuzzyDiv").parent().append("<span class='field_tip input-txt-warn-tip'>物品名称 不能为空！</span>");
		flag = false;
	}
	var goodsUnitObj = document.getElementById("goodsUnitSelectedName");
	if(trim(goodsUnitObj.value)==''){
		$("#goodsUnitFuzzyDiv").parent().find(".field_tip").hide();
		$("#goodsUnitFuzzyDiv").parent().append("<span class='field_tip input-txt-warn-tip'>物品单位 不能为空！</span>");
		flag = false;
	}
	
	if(!flag) return;
	
	var goodsNameLength = _getLength(goodsNameObj.value);
	if(goodsNameLength > 30){
		$("#goodsNameFuzzyDiv").parent().find(".field_tip").hide();
		$("#goodsNameFuzzyDiv").parent().append("<span class='field_tip input-txt-warn-tip'>物品名称  长度为" + goodsNameLength + "个字符，超出了最大长度限制：30个字符</span>");
		return;
	}
	
	var goodsUnitLength = _getLength(goodsUnitObj.value);
	if(goodsUnitLength > 30){
		$("#goodsUnitFuzzyDiv").parent().find(".field_tip").hide();
		$("#goodsUnitFuzzyDiv").parent().append("<span class='field_tip input-txt-warn-tip'>物品单位  长度为" + goodsUnitLength + "个字符，超出了最大长度限制：30个字符</span>");
		return;
	}
	
	var amount = $("#amount").val();
	if(parseInt(amount)==0){
		showMsgError("物品数量不得为0");
		return;
	}
	
	var price = $("#price").val();
	if(parseFloat(price)==0){
		showMsgError("物品单价不得为0");
		return;
	}
	
	var goodsName = $("#goodsNameSelectedName").val();
	$("#name").val(goodsName);
	
	var goodsUnit = $("#goodsUnitSelectedName").val();
	$("#myGoodsUnit").val(goodsUnit);
	
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/asset/assetAdmin-save.action', 
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
		   			assetQuery();
				});
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>物品同步</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="addform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="asset.id" value="${asset.id?default('')}">
    	<input type="hidden" id="unitId" name="officeGoods.unitId" value="${officeGoods.unitId?default('')}">
	  	<input type="hidden" id="addUserId" name="officeGoods.addUserId" value="${officeGoods.addUserId?default('')}">
	    <tr>
	      	<th style="width:15%">物品名称：</th>
	    	<td style="width:35%">
    			<input type="hidden" id="name" name="officeGoods.name" value="">
				<div id="goodsNameFuzzyDiv" objectId="goodsName" class="search-box fn-left" style="z-index:1000;">
					<div>
					    <div id="ovLayer" style="position:absolute;z-index:-1;width:101%;height:101%;margin-left:-1px;left:-2px;"><iframe style="width:101%;height:101%;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe></div>
						<input type="text" value="${officeGoods.name?default('')}" class="txt" onkeyup="delayLoadFuzzyDivWithData('goodsName',this.value);" onBlur="doHideFieldTip('goodsNameSelectedName');" id="goodsNameSelectedName">
							<div id="goodsNameOption" class="search-list-wrap" style="z-index:1001"></div>
							<input id="goodsNameId" type="hidden" value="goodsName"/>
							<input id="goodsNameName" type="hidden" value="goodsName"/>
							<input id="goodsNameUrl" type="hidden" value="/office/goodmanage/goodmanage-goodsNameDiv.action"/>
							<input id="goodsNameDivName" type="hidden" value="物品名称"/>
							<input id="goodsNameOtherParam" type="hidden" value="divType=goodsName"/>
							<input id="goodsNameCallback" type="hidden" value=""/>
							<input id="goodsNameOnclick" type="hidden" value="doHideNameTip"/>
							<input id="goodsNameDependson" type="hidden" value=""/>
							<input id="goodsNameReferto" type="hidden" value=""/>
							<input id="goodsNameDefaultItem" type="hidden" value=""/>
							<input id="goodsNameSelectedValue" type="hidden" value=""/>
							<input id="goodsNameTipMsg" type="hidden" value=""/>
							<input id="goodsNamesingleSelect" type="hidden" value=""/>
							<input type="hidden" name="goodsName" id="goodsName" value="${officeGoods.name?default('')}"/>
					</div>
				</div>
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>	
	    	<th style="width:15%">单位：</th>
	    	<td style="width:35%">
	    		<input type="hidden" id="myGoodsUnit" name="officeGoods.goodsUnit" value="${officeGoods.goodsUnit!}">
				<div id="goodsUnitFuzzyDiv" objectId="goodsUnit" class="search-box fn-left" style="z-index:1000;">
					<div>
					    <div id="ovLayer" style="position:absolute;z-index:-1;width:101%;height:101%;margin-left:-1px;left:-2px;"><iframe style="width:101%;height:101%;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe></div>
						<input type="text" value="${officeGoods.goodsUnit!}" class="txt" onkeyup="delayLoadFuzzyDivWithData('goodsUnit',this.value);" onBlur="doHideFieldTip('goodsUnitSelectedName');" id="goodsUnitSelectedName">
							<div id="goodsUnitOption" class="search-list-wrap" style="z-index:1001"></div>
							<input id="goodsUnitId" type="hidden" value="goodsUnit"/>
							<input id="goodsUnitName" type="hidden" value="goodsUnit"/>
							<input id="goodsUnitUrl" type="hidden" value="/office/goodmanage/goodmanage-goodsUnitDiv.action"/>
							<input id="goodsUnitDivName" type="hidden" value="物品单位"/>
							<input id="goodsUnitOtherParam" type="hidden" value="divType=goodsUnit"/>
							<input id="goodsUnitCallback" type="hidden" value=""/>
							<input id="goodsUnitOnclick" type="hidden" value="doHideUnitTip"/>
							<input id="goodsUnitDependson" type="hidden" value=""/>
							<input id="goodsUnitReferto" type="hidden" value=""/>
							<input id="goodsUnitDefaultItem" type="hidden" value=""/>
							<input id="goodsUnitSelectedValue" type="hidden" value=""/>
							<input id="goodsUnitTipMsg" type="hidden" value=""/>
							<input id="goodsUnitsingleSelect" type="hidden" value=""/>
							<input type="hidden" name="goodsUnit" id="goodsUnit" value="${officeGoods.goodsUnit!}"/>
					</div>
				</div>
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>	
	    </tr>
	    <tr>
	      	<th style="width:15%">物品类别：</th>
	    	<td style="width:35%">
	    		<@htmlmacro.select style="width:185px;" valId="type" valName="officeGoods.type" notNull="true" msgName="物品类别">
					<a val=""><span>--请选择--</span></a>
		        	<#if goodsTypeList?exists && (goodsTypeList?size>0)>
		            	<#list goodsTypeList as item>
		            		<a val="${item.typeId}" <#if officeGoods.type?default('') == item.typeId>class="selected"</#if>><span>${item.typeName}</span></a>
		            	</#list>
		        	</#if>
				</@htmlmacro.select>
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>	
	    	<th style="width:15%">规格型号：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="规格型号" class="input-txt fn-left" id="model" name="officeGoods.model" maxlength="50" value="${officeGoods.model!}" style="width:175px;">
	    	</td>	
	    </tr>
	    <tr>
	      	<th style="width:15%">数量：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="数量" class="input-txt fn-left" id="amount" name="officeGoods.amount" notNull="true" regex="/^\d*$/" regexMsg="请正确填写数量" maxlength="8" value="${officeGoods.amount!}" style="width:175px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>	
	    	<th style="width:15%">单价：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="单价" class="input-txt fn-left" id="price" name="officeGoods.price" notNull="true" regex="/^(^[0-9]{0,5}$)|(^[0-9]{0,5}\.[0-9]{1,2}$)$/" regexMsg="最多5位整数2位小数" maxlength="10" value="${officeGoods.price?string('0.00')!}" style="width:175px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    </tr>
	    <tr>	
	    	<th style="width:15%">是否需要归还：</th>
	    	<td style="width:35%">
	    		<@htmlmacro.select style="width:185px;" valId="isReturnedStr" valName="officeGoods.isReturnedStr" >
        			<a val="0"><span>需归还</span></a>
        			<a val="1" class="selected"><span>不需归还</span></a>
				</@htmlmacro.select>
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    	<th style="width:15%">购买时间：</th>
	    	<td style="width:35%">
	    		<@htmlmacro.datepicker class="input-txt" style="width:175px;" name="officeGoods.purchaseDate" id="purchaseDate" size="20" maxlength="19" msgName="购买时间" value="${(officeGoods.purchaseDate?string('yyyy-MM-dd'))?if_exists}" dateFmt="yyyy-MM-dd"/>
	    	</td>		
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="备注" id="remark" name="officeGoods.remark" maxLength="1000" colspan="3" style="width:470px;" value="${officeGoods.remark!}" />
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

function doHideFieldTip(id){
	var val = $("#"+id).val();
	if(trim(val)!=""){
		$("#"+id).parent().parent().parent().find(".field_tip").hide();
	}
}

function doHideNameTip(){
	$("#goodsNameFuzzyDiv").parent().find(".field_tip").hide();
}

function doHideUnitTip(){
	$("#goodsUnitFuzzyDiv").parent().find(".field_tip").hide();
}

var timer; 
function delayLoadFuzzyDivWithData(id,data){
	if(timer)
		window.clearTimeout(timer); 
	timer=window.setTimeout(function(){loadFuzzyDivWithData(id,data);},500); 
} 

function loadFuzzyDivWithData(id,data){
	 dynamicLoadFuzzyDivData(id,data);
	 $('#'+id+'FuzzyDiv').find(".search-list-wrap,#ovLayer").fadeIn(function(){
		var myW=$(this).width()+5;
        var myH=$(this).height()+30;
		$(this).siblings('#ovLayer').css({'width':myW,'height':myH});
	},0);
    $('#'+id+'FuzzyDiv').find('.search-list-wrap').show();
}

//加载条件
function dynamicLoadFuzzyDivData(id,queryData){
	onLoadSelectFuzzyDiv($('#'+id+'Id').val(),$('#'+id+'Name').val(),$('#'+id+'Url').val(),$('#'+id+'DivName').val(),$('#'+id+'Onclick').val(),$('#'+id+'Callback').val(),$('#'+id+'SelectedValue').val(),$('#'+id+'OtherParam').val(),$('#'+id+'Dependson').val(),$('#'+id+'Referto').val(),$('#'+id+'DefaultItem').val(),$('#'+id+'TipMsg').val(),queryData);
}

function onLoadSelectFuzzyDiv(idObjectId,nameObjectId,url,divName,onclick,callback,selectedValue,otherParam,dependson,referto,defaultItem,tipMsg,queryData){
	var assembledUrl =_contextPath+url+"?idObjectId="+idObjectId+"&nameObjectId="+nameObjectId+"&divName="+divName+"&onclick="+onclick+"&callback="+callback+"&selectedValue="+selectedValue+"&dependson="+dependson+"&referto="+referto+"&defaultItem="+defaultItem+"&tipMsg="+tipMsg;
	var assembledParam ="";
	//依赖的元素
	if(dependson != ""){
		var dependsonElements=dependson.split(",");
		for (i=0;i<dependsonElements.length;i++){
			if($('#'+dependsonElements[i]).val()){
				if( i == dependsonElements.length-1)
					assembledParam +=dependsonElements[i]+"="+$('#'+dependsonElements[i]).val()+"";
				else
					assembledParam +=dependsonElements[i]+"="+$('#'+dependsonElements[i]).val()+"&";
			}
		}
	}
	if(otherParam != ""){
		assembledUrl+="&"+otherParam;
	} 
	if(assembledParam != ""){
		assembledUrl+="&"+assembledParam;
	}
	
	if(queryData !=""){
		queryData = encodeURIComponent(queryData);
		assembledUrl+="&"+nameObjectId+"="+queryData;
	}
	var singleSelect=$('#'+idObjectId+'singleSelect').val();
	if(singleSelect =="true")
		load('#'+idObjectId+'Option',assembledUrl,"","",true);	
	else
		load('#'+idObjectId+'Option',assembledUrl);	
}
onLoadSelectFuzzyDiv('goodsName','goodsName','/office/goodmanage/goodmanage-goodsNameDiv.action','物品名称','','','','','','','','','');
onLoadSelectFuzzyDiv('goodsUnit','goodsUnit','/office/goodmanage/goodmanage-goodsUnitDiv.action','物品单位','','','','','','','','','');
</script>
</@htmlmacro.moduleDiv>