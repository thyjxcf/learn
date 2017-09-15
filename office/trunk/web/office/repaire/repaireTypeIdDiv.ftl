<#import "/common/htmlcomponent.ftl" as common />
<@common.select style="width:100px;" valName="officeRepaire.repaireTypeId" valId="repaireTypeId">
		<a val="">请选择</a>
		<#list officeRepaireTypeList as type>
			<a val="${type.id}" <#if type.id == typeId?default('2')>class="selected"</#if>>${type.typeName!}</a>
		</#list>
</@common.select>