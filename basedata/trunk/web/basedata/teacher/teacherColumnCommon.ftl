<#if columnsHiddenList?exists>
  <#list columnsHiddenList as item>
     <#if item.colsCode = "pname">
         <input name="teacher.pname" id="teacher.pname" type="hidden" value="${teacher.pname?default('')?trim}">
	</#if>
	<#if item.colsCode = "idcard">
		<input name="teacher.idcard" id="teacher.idcard" type="hidden" value="${teacher.idcard?default('')?trim}">	  
	</#if>
	<#if item.colsCode = "birthday">
		<input name="teacher.birthday" id="teacher.birthday" type="hidden" <#if teacher.birthday?exists>value="${teacher.birthday?string("yyyy-MM-dd")}"<#else>value=""</#if>>
	</#if>
	<#if item.colsCode = "workdate">
	    <input name="teacher.workdate" id="teacher.workdate" type="hidden" <#if teacher.workdate?exists>value="${teacher.workdate?string("yyyy-MM-dd")}"<#else>value=""</#if>>
	</#if>
	<#if item.colsCode = "duty">
	    <input name="teacher.duty" id="teacher.duty" type="hidden" value="${teacher.duty?default('')?trim}">
	</#if>
	<#if item.colsCode = "title">
	    <input name="teacher.title" id="teacher.title" type="hidden" value="${teacher.title?default('')?trim}">	
	</#if>
	<#if item.colsCode = "personTel">
	    <input name="teacher.personTel" id="teacher.personTel" type="hidden" value="${teacher.personTel?default('')?trim}">	
	</#if>
	<#if item.colsCode = "officeTel">
		 <input name="teacher.officeTel" id="teacher.officeTel" type="hidden" value="${teacher.officeTel?default('')?trim}">	
	</#if>
	<#if item.colsCode = "chargeNumber">
		 <input name="teacher.chargeNumber" id="teacher.chargeNumber" type="hidden" value="${teacher.chargeNumber?default('')?trim}">	
	</#if>
	<#if item.colsCode = "polity">
		<input name="teacher.polity" id="teacher.polity" type="hidden" value="${teacher.polity?default('')?trim}">
	</#if>
	<#if item.colsCode = "polityJoin">
		<input name="teacher.polityJoin" id="teacher.polityJoin" type="hidden" <#if teacher.polityJoin?exists>value="${teacher.polityJoin?string("yyyy-MM-dd")}"<#else>value=""</#if>>
	</#if>
	<#if item.colsCode = "nation">
		<input name="teacher.nation" id="teacher.nation" type="hidden" value="${teacher.nation?default('')?trim}">
	</#if>
	<#if item.colsCode = "perNative">
		<input name="teacher.perNative" id="teacher.perNative" type="hidden" value="${teacher.perNative?default('')?trim}">
	</#if>
	<#if item.colsCode = "stulive">
		<input name="teacher.stulive" id="teacher.stulive" type="hidden" value="${teacher.stulive?default('')?trim}">
	</#if>
	<#if item.colsCode = "major">
		<input name="teacher.major" id="teacher.major" type="hidden" value="${teacher.major?default('')?trim}">
	</#if>
	<#if item.colsCode = "fsschool">
		<input name="teacher.fsschool" id="teacher.fsschool" type="hidden" value="${teacher.fsschool?default('')?trim}">
	</#if>
	<#if item.colsCode = "fstime">
		<input name="teacher.fstime" id="teacher.fstime" type="hidden" <#if teacher.fstime?exists>value="${teacher.fstime?string("yyyy-MM-dd")}"<#else>value=""</#if>>
	</#if>
	<#if item.colsCode = "registertype">
		<input name="teacher.registertype" id="teacher.registertype" type="hidden" value="${teacher.registertype?default(-1)?string}">
	</#if>
	<#if item.colsCode = "register">
		<input name="teacher.register" id="teacher.register" type="hidden" value="${teacher.register?default('')?trim}">
	</#if>
	<#if item.colsCode = "address">
		<input name="teacher.linkAddress" id="teacher.linkAddress" type="hidden" value="${teacher.linkAddress?default('')?trim}">
	</#if>
	<#if item.colsCode = "email">
		<input name="teacher.email" id="teacher.email" type="hidden" value="${teacher.email?default('')?trim}">
	</#if>
	<#if item.colsCode = "homepage">
		<input name="teacher.homepage" id="teacher.homepage" type="hidden" value="${teacher.homepage?default('')?trim}">
	</#if>
	<#if item.colsCode = "remark">
		<input name="teacher.remark" id="teacher.remark" type="hidden" value="${teacher.remark?default('')?trim}">
	</#if>
	<#-- 点到卡号-->
	<#if item.colsCode = "cardNumber">
	     <input name="teacher.cardNumber" id="teacher.cardNumber" type="hidden" class="input" maxlength="20" size="20"  value="${teacher.getCardNumber()?default('')}">
	</#if>
	<#--排序号 -->
	<#if item.colsCode = "displayOrder">
	     <input name="teacher.displayOrder" id="teacher.displayOrder" type="hidden" class="input" maxlength="20" size="20"  value="${teacher.getDisplayOrder()?default('')}">
	</#if>
	<#if item.colsCode = "workTeachDate">
		<input name="teacher.workTeachDate" id="teacher.workTeachDate" type="hidden" class="input" maxlength="" size=""  value="${(teacher.workTeachDate?string('yyyy-MM-dd'))?if_exists}">
	</#if>
	<#if item.colsCode = "oldAcademicQualification">
		<input name="teacher.oldAcademicQualification" id="teacher.oldAcademicQualification" type="hidden" class="input" maxlength="" size=""  value="${teacher.oldAcademicQualification?default('')}">
	</#if>
	<#if item.colsCode = "specTechnicalDuty">
		<input name="teacher.specTechnicalDuty" id="teacher.specTechnicalDuty" type="hidden" class="input" maxlength="" size=""  value="${teacher.specTechnicalDuty?default('')}">
	</#if>
	<#if item.colsCode = "specTechnicalDutyDate">
		<input name="teacher.specTechnicalDutyDate" id="teacher.specTechnicalDutyDate" type="hidden" class="input" maxlength="" size=""  value="${teacher.specTechnicalDutyDate?default('')}">
	</#if>
	<#if item.colsCode = "homeAddress">
		<input name="teacher.homeAddress" id="teacher.homeAddress" type="hidden" class="input" maxlength="" size=""  value="${teacher.homeAddress?default('')}">
	</#if>
	<#if item.colsCode = "generalCard">
		<input name="teacher.generalCard" id="teacher.generalCard" type="hidden" class="input" maxlength="" size=""  value="${teacher.generalCard?default('')}">
	</#if>
  </#list>
</#if>