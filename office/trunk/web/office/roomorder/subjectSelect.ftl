<#import "/common/htmlcomponent.ftl" as common />
<@common.select style="width:210px;" valName="officeLabInfo.labSetId" valId="labSetId" notNull="true" msgName="实验种类" myfunchange="changeLabTypeSet">
		<a val="">请选择</a>
		<#list officeLabSetList as item>
			<a val="${item.id}" <#if item.id == officeLabInfo.labSetId?default('')>class="selected"</#if>>${item.name!}</a>
		</#list>
</@common.select>