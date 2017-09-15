<#import "/common/htmlcomponent.ftl" as common />
<@common.select style="width:170px;" valName="classId" valId="classId" myfunchange="doSearch">
	<a val="">请选择</a>
	<#list eisuClasss as item>
	<a val="${item.id!}">${item.classname!}</a>
	</#list>
</@common.select>