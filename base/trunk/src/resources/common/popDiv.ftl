<#import "/common/commonmacro.ftl" as commonmacro>
<#if !tree>
<@commonmacro.selectObject useCheckbox="${useCheckbox!}" idObjectId="${idObjectId!}" nameObjectId="${nameObjectId!}" url=request.contextPath+"${url!}"  callback="${callback!}" switchSelector="${switchSelector!}" loadCallback="${loadDataCallBack!}">
    <#if popup>
 	   <input type="hidden" name="${nameObjectId!}" id="${nameObjectId!}" value="${nameObjectValue!}">
	   <a id="${switchId!}">${switchText!}</a>
	<#else>
  	  	<input type="text" name="${nameObjectId!}" id="${nameObjectId!}" value="${nameObjectValue!}" class="select_current02 input-readonly" style="${style?default('width:190px')}" readonly="readonly"/>
	</#if>
</@commonmacro.selectObject>
<#else>
<@commonmacro.selectTree useCheckbox="${useCheckbox!}" idObjectId="${idObjectId!}" nameObjectId="${nameObjectId!}" treeUrl=request.contextPath+"${url!}"  callback="${callback!}" switchSelector="${switchSelector!}">
    <#if popup>
 	   <input type="hidden" name="${nameObjectId!}" id="${nameObjectId!}" value="${nameObjectValue!}">
	   <a id="${switchId!}">${switchText!}</a>
	<#else>
		<#if textarea>
			<input type="hidden" name="${idObjectId!}" id="${idObjectId!}" value="${selectedValue!}" class="select_current02">
	  		<textarea name="${nameObjectId!}" id="${nameObjectId!}"  class="select_current02" style="${style?default('height:80px')}" readonly="readonly">${nameObjectValue!}</textarea>
	  	<#else>
  	  		<input type="text" name="${nameObjectId!}" id="${nameObjectId!}" value="${nameObjectValue!}" class="select_current02 input-readonly" style="${style?default('width:190px')}" readonly="readonly"/>
		</#if>
	</#if>
</@commonmacro.selectTree>
</#if>
<script type="text/javascript">
	//用来弹出
	function popShow(){
		<#if switchId?exists>
			$("#${switchId!}").click();
		</#if>
	}
</script>