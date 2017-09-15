<#macro form fields prefix="">	
	<#assign i=0>
	<#list fields as x >
		<#if x.childField?exists>
			<#assign child=x.childField>
		</#if>
		<#if !x.parentValue?exists>
			<#if i % 2 == 0>
				<tr>
			<#elseif x.alone>
				<#assign i=i+1>
					<td colspan="2"></td>
				</tr>
				<tr>
			</#if>
			<#if x.alone>
				<#assign i=i+2>
			<#else>
				<#assign i=i+1>
			</#if>
		  	<th width="20%"><#if x.require><span>*</span></#if>${x.define}：</th>
			<td <#if x.alone>colspan="3"<#else>width="30%"</#if>>
			  	<span class="editSpan">
			  	  <@input field=x isChildField=false prefix=prefix/>
			    </span>
			    <span class="viewSpan"><#-- 奖惩信息   获奖证书 -->
			         <#if x.name == "awardFile" && x.wrappedValue?default('') != "">
			            <#if applyOrAudit == "audit"><a href="audit-downloadfile.action?awardFilePath=${x.value?default('')}&awardFileName=${x.wrappedValue?default('')}" id="downEdiA" style="display:none"></a></#if>
			            <#if applyOrAudit == "apply"><a href="apply-downloadfile.action?awardFilePath=${x.value?default('')}&awardFileName=${x.wrappedValue?default('')}" id="downEdiA" style="display:none"></a></#if>
					 ${x.wrappedValue?default('')}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <span class="input-btn2" onclick="downloadEvi();"><button type="button">下载</button></span>
					 <#else>
					   ${x.wrappedValue?default('')}<#if x.childField?exists && child.wrappedValue?has_content>(${child.wrappedValue})</#if>
			         </#if>
			    </span>
		  	</td>
			<#if i != 0 && i % 2 == 0>  
				</tr>	
			<#elseif x_index == fields.size() - 1>
					<td colspan="2"></td>
				</tr>
			</#if>
		</#if>
	</#list>  
	
	<script>	

	/*下拉列表切换时控制另一字段是否可编辑*/
	function changeElementEdit(eleId,eleValue,hiddenId){
		if (jQuery(eleId).val() == eleValue){
			jQuery(hiddenId).attr("disabled",false);
		}else{
			jQuery(hiddenId).attr("disabled",true);
			jQuery(hiddenId).val("");
		}		
	}
	
	function showEleChild(eleId, parentValue, hiddenSpan, hiddenId){
		if (jQuery(eleId).val() == parentValue){
			jQuery(hiddenSpan).show();
		}else{
			jQuery(hiddenSpan).hide();
			jQuery(hiddenId).val("");
		}
	}
	</script>
</#macro>

<#macro input field isChildField=false prefix="">
  <#if field.childField?exists>
		<#assign child=field.childField>
  </#if>
  <#------------------------ 下拉框 ---------------------------->
  <#if field.mcode?exists || field.inputType == stack.findValue("@net.zdsoft.eis.base.form.Field@INPUT_TYPE_SELECT")>
	  <select name="${prefix}${field.name}" id="${field.name}" class="input-sel150" 
		<#if field.childField?exists>
		onChange="showEleChild('#${field.name}', '${child.parentValue}', '#${child.name}Span', '#${child.name}')"
		</#if>
		msgName="${field.define}" <#if !isChildField>notNull="${field.require?string}"</#if>>
	  	<#if field.mcode?exists>
	  	   ${appsetting.getMcode(field.mcode).getHtmlTag(field.value?default(field.defaultValue?default('')))}
	  	<#else>
		  	<#list field.options?if_exists as option>
		  	<option value="${option[0]}" <#if option[0]==field.value?default('')>selected</#if>      ><#if option?size gt 1>${option[1]}<#else>${option[0]}</#if></option>
		  	</#list>
	  	</#if>
	</select>
	<#if field.childField?exists>
	<span id="${child.name}Span" style="display:<#if field.value?default('') != child.parentValue>none</#if>">
		<@input field=child isChildField=true prefix=prefix/>
	</span>
	</#if>
  <#------------------------ textarea ---------------------------->
  <#elseif field.inputType == stack.findValue("@net.zdsoft.eis.base.form.Field@INPUT_TYPE_TEXTAREA")
	&& field.simpleType != stack.findValue("@net.zdsoft.eis.base.form.Field@SIMPLE_TYPE_DATE")>
	<textarea name="${prefix}${field.name}" id="${field.name}" 
		<#if !isChildField>notNull="${field.require?string}"</#if> msgName="${field.define}" maxLength="${field.strLength}"
		<#if field.alone>rows="5" cols="80"<#else>rows="4" cols="44"</#if>>${field.value?default(field.defaultValue?default(''))}</textarea>
  <#------------------------- file ---------------------------->	
  <#elseif field.inputType == stack.findValue("@net.zdsoft.eis.base.form.Field@INPUT_TYPE_FILE")>
      <#assign x = apply.business>
      <#if x.filePath? exists && x.filePath!=""><#-- 奖惩信息   获奖证书 -->
            <div id="downfileDiv"> <a href="apply-downloadfile.action?awardFilePath=${x.filePath?if_exists}&awardFileName=${x.fileName?if_exists}" id="downEdiA" style="display:none"></a>
				 ${x.fileName?if_exists}&nbsp;
				<span><a href="javascript:delFile('${apply.businessType!}','${x.id?default('')}');">删除</a></span><span class="input-btn2" onclick="downloadEvi();"><button type="button">下载</button></span>
			</div>
			<div id="upfileDiv" style="display:none;">
				<input name="${prefix}${field.name}" id="${prefix}${field.name}" type="file" class="input-txt150 input-readonly" hidefocus/>
				<span style="color:red;">不能超过20M</span>
			</div>
	  <#else>
	  	 <input name="${prefix}${field.name}" id="${prefix}${field.name}" type="file"  class="input-txt150 input-readonly" hidefocus/><span style="color:red;">不能超过20M</span>	  	
      </#if>
  		  	  
  <#------------------------ input ---------------------------->
  <#else>
  	<#----------- 日期选择框 ------------>
	<#if field.simpleType == stack.findValue("@net.zdsoft.eis.base.form.Field@SIMPLE_TYPE_DATE")>
		<#if !isChildField>
		<@h.datepicker class="input-txt150 input-readonly" name="${prefix}${field.name}" id="${field.name}" readonly="${field.readOnly?string}"
			notNull="${field.require?string}" msgName="${field.define}"
			value="${(field.value)?if_exists}"/>
		<#else>
		<@h.datepicker class="input-txt150 input-readonly" name="${prefix}${field.name}" id="${field.name}" readonly="${field.readOnly?string}"
			msgName="${field.define}"
			value="${(field.value)?if_exists}"/>
		</#if>
		

	
	
		
	<#----------- 普通文本框 ------------>
	<#else>	
	<input name="${prefix}${field.name}" id="${field.name}" value="${field.value?default(field.defaultValue?default(''))}"   
		<#if field.simpleType == stack.findValue("@net.zdsoft.eis.base.form.Field@SIMPLE_TYPE_INTEGER")>
			dataType="integer"
			<#if field.minValue?exists>minValue="${field.minValue}"</#if>
			<#if field.maxValue?exists>maxValue="${field.maxValue}"</#if>
		<#elseif field.simpleType == stack.findValue("@net.zdsoft.eis.base.form.Field@SIMPLE_TYPE_NUMBERIC")>
			dataType="float" 
			<#if field.integerLength?exists>integerLength="${field.integerLength}"</#if>
			<#if field.decimalLength?exists>decimalLength="${field.decimalLength}"</#if>
			<#if field.minValue?exists>minValue="${field.minValue}"</#if>
			<#if field.maxValue?exists>maxValue="${field.maxValue}"</#if>
		</#if>
		<#if field.wrappedValue?exists>
			<#if canModify?exists && canModify>
				type="text"
			<#else>
				type="hidden"
			</#if>
		<#else>
			type="text"
		</#if>
		<#if field.readOnly==true >
			readonly="true" class="input-txt150 input-readonly"
		<#else>
			class="input-txt150"
		</#if>
		
		<#if !isChildField>notNull="${field.require?string}"</#if> msgName="${field.define}" maxLength="${field.strLength}">
		<#if field.wrappedValue?exists>
			<#if canModify?exists && canModify>
			<#else>
				${field.wrappedValue}
			</#if>
		</#if>
	</#if>	
  </#if>
</#macro>