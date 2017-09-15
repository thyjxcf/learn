<#import "/common/htmlcomponent.ftl" as common />
<@common.select style="width:170px;" valName="importId" valId="importId" myfunchange="searchOrder">
	<a val="">请选择</a>
	<#list officeSalaryImports as item>
	<a val="${item.id!}">${item.monthtime!}</a>
	</#list>
</@common.select>