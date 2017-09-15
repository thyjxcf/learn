<#include "/common/form.ftl"/>
<#macro businessCommonDetail>
	<#if apply.business.fieldValues?exists>
		<#assign fields = apply.business.fieldValues>
	<#else>
		<#assign fields = fields>
	</#if>
		    
	<input type="hidden" name="apply.business.unitId" id="unitId" value="${unitId}"/>
	<input type="hidden" name="apply.business.id" value="${apply.id?default('')}"/>
	<tr class="first"><th colspan="4" class="tt">${apply.business.businessName}：
		<span id="businessSpan">
			<input type="hidden" name="apply.businessId" id="businessId" value="${apply.businessId?default('')}">
		    <input id="businessName" value="" type="hidden">
		    
		    <#if !apply.business.owner>
		    	<#assign preset="isExistOwnerId">
		    	<#assign dynamicParam="getOwnerId">
		    </#if>
		    
		    <@commonmacro.selectObject useCheckbox=false preset=preset! idObjectId="businessId" nameObjectId="businessName" dynamicParam=dynamicParam! url="${request.contextPath}/personnel/applyaudit/apply-div.action" otherParam="businessType=${businessType}" callback="viewBusiness"/>
		 </span></th></tr>
	<tr>
	<@form fields=fields prefix="apply.business."/>
	<script>	
	//清空数据
	function clearBusiness(){
		
		jQuery("#businessId").val("");
		
		<#list fields as x >
			<#if x.needReset>
				jQuery("#${x.name}").attr("value",'');;
				<#if x.mcode?exists && x.childField?exists >
					jQuery("#${x.name}").change();
				</#if>
			</#if>
		</#list>
	}
	
	function changeOwnerCallback(){
		clearBusiness();
	}
	
	function viewBusiness(){	
		jQuery.post("apply-viewBusiness.action", {'apply.businessId' : jQuery("#businessId").val(),'businessType':${businessType},'apply.id':'${apply.id?default('')}'}, function(data) {
		  if(data.msg != null){
		  	jQuery("#businessId").val("");
		  	showMsgWarn(data.msg);
		  }else{
	      	fillBusiness(data.business);
	      }
	    }, 'json').error(function() {
	      addActionError('获取数据失败');
	    });
	}
	
	function fillBusiness(x){
		//针对人事信息》技术职务》人员类别和岗位类别的级联关系做特殊处理
		var personnelType = "";
		var jobLevel = "";
		var otherJobLevel = "";
		<#list fields as x >
			var v = x.${x.name};
			<#if x.simpleType == 1>
				if(v && v!=''){
					jQuery("#${x.name}").val((v.year +1900) +"-"+ (v.month+1) +"-"+ v.date);
				}
			<#else>
				<#if x.name == 'personnelType'>
					personnelType = v;
				</#if>
				<#if x.name == 'jobLevel'>
					jobLevel = v;
				</#if>
				jQuery("#${x.name}").val(v);
			</#if>
			<#if (x.mcode?exists || x.inputType == 2) && x.childField?exists>
				<#if x.childField.name == 'otherJobLevel'>
					otherJobLevel = x.${x.childField.name};
				</#if>
				jQuery("#${x.childField.name}").val(x.${x.childField.name});
				jQuery("#${x.name}").change();
			</#if>
		</#list>
		if(personnelType && personnelType!=''){
			changeJob();
			if(personnelType == '01'){
				jQuery("#jobLevelSelect01").val(jobLevel);
			}else if(personnelType == '02'){
				jQuery("#jobLevelSelect02").val(jobLevel);
			}else if(personnelType == '03'){
				jQuery("#jobLevelSelect03").val(jobLevel);
			}
			if(jobLevel == '99'){
				jQuery("#otherJobLevel").val(otherJobLevel);
				jQuery("#otherjoblevelspan").show();
			}
		}
	}
	</script>
</#macro>