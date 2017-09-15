<#import "/common/htmlcomponent.ftl" as common />
<@common.select style="width:170px;" valName="backUserId" valId="backUserId" myfunchange="searchOrder">
	<a val="">请选择</a>
	<#list officeDutyApplies as item>
	<a val="${item.userId!}" <#if item.userId==loginUser.userId>class="selected"</#if>>${item.userName!}</a>
	</#list>
</@common.select>