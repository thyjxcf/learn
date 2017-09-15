<script language="javascript">
$(document).ready(function(){
	var obj;
	<#if action.hasFieldErrors()>
		<#list fieldErrors.keySet() as item>
			obj = document.getElementsByName('${item}')[0];
			if(null == obj){
				obj = document.getElementById('${item}');
			}
			if(null != obj){
				//obj.fielderror='${fieldErrors(item)}';
				if(obj.tagName=="SELECT"){obj.style.color="red";}else{obj.style.borderColor="red";}
				if(obj.getAttribute("type")!="hidden"){
					obj.focus();
				}
				addFieldError(obj, "${fieldErrors(item)}");
				return;
			}
		</#list>
	</#if>
	var htmlContent,htmlContent2;
	<#if action.hasActionErrors()>
	    htmlContent = "";
	  	<#list actionErrors as item>
	  		<#if item?exists>
			htmlContent+="${item?j_string}";
			</#if>
		</#list>
		addActionError(htmlContent);
	<#elseif action.hasActionMessages()>
	    htmlContent = "";
	  	<#list actionMessages as item>
	  		<#if item?exists>
			htmlContent+="${item?j_string}";
			</#if>
		</#list>
		addActionMessage(htmlContent);
	</#if>
})
</script>

