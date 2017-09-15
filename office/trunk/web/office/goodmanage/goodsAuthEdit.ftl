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
	
	var chk_value =''; 
	$('input[name="checkType"]:checked').each(function(){
		if(chk_value == ''){ 
			chk_value += $(this).val();
		}else{
			chk_value += ',' + $(this).val();
		} 
	});
	if(chk_value == ''){
		showMsgWarn("请选择物品类型！");
		return;
	}
	
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/goodmanage/goodmanage-goodsAuth-save.action?officeGoodsTypeAuth.typeId='+chk_value, 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#goodsAuthform').ajaxSubmit(options);
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
		   			closeDiv("#goodsTypeAuthEditLayer");
		   			doSearch();
				});
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>权限设置</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="goodsAuthform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="officeGoodsTypeAuth.id" value="${officeGoodsTypeAuth.id?default('')}">
    	<input type="hidden" id="unitId" name="officeGoodsTypeAuth.unitId" value="${officeGoodsTypeAuth.unitId?default('')}">
    	<input type="hidden" id="creationTime" name="officeGoodsTypeAuth.creationTime" value="${(officeGoodsTypeAuth.creationTime?string('yyyy-MM-dd HH:mm:ss'))!}">  
	    <tr>
	    	<th style="width:20%"><span class="c-orange">*&nbsp;</span>用户：</th>
	    	<td style="width:80%">
			<#if officeGoodsTypeAuth.id! !="">
				<input readonly="true" class="input-txt input-readonly fn-left" id="realName" name="officeGoodsTypeAuth.realName" value="${officeGoodsTypeAuth.realName!}" notNull="true" style="width:170px;">
				<input id="userId" name="officeGoodsTypeAuth.userId" value="${officeGoodsTypeAuth.userId?default('')}" type="hidden" >
			<#else>
			<@commonmacro.selectObject useCheckbox=false url="${request.contextPath}/office/goodmanage/goodmanage-getTeacherDataPopup.action" idObjectId="userId" nameObjectId="realName" width="450" otherParam="showLetterIndex=true">
				<input id="realName" value="${officeGoodsTypeAuth.realName?default('')}" class="input-txt input-readonly fn-left" style="width:170px;" notNull="true" readonly="readonly">
	            <input id="userId" name="officeGoodsTypeAuth.userId" value="${officeGoodsTypeAuth.userId?default('')}" type="hidden" >
	        </@commonmacro.selectObject>
	        </#if>
			</td>
	    </tr>
	    <tr>
	      	<th style="width:20%"><span class="c-orange">*&nbsp;</span>可管理类别：</th>
	    	<td style="width:80%">
	    		<div class="mt-5" style="line-height:20px;">
	    		<#list goodsTypeList as item>
	    			<span style="display:inline-block">
					<input name="checkType" id="type_${item.typeId!}" type="checkbox" <#if item.isExist>checked</#if> value="${item.typeId!}"/>
					<label for="type_${item.typeId!}">${item.typeName!}&nbsp;&nbsp;</label>
					</span>
				</#list>
				</div>
	    	</td>	
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