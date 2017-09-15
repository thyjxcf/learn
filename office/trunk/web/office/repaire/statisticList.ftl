<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<div id="printDiv">
<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
	<tr id="titleTr">
		<#if change?default('1')==''||change=='1'>
   		<td colspan="${mcodelist?size+1}" style="text-align:center;font-size:20px;">报修次数统计</td>
   		<#elseif change=='2'>
   		<td colspan="${users?size+1}" style="text-align:center;font-size:20px;">报修评价统计</td>
   		</#if>
	</tr>
    <tr>
    	<th width="10%" style="text-align:center;">月份</th>
    	<#if change?default('1')==''||change=='1'>
    		<#list mcodelist as detail>
	    		<th width="${90/mcodelist?size}%" style="text-align:center;">${detail.content!}</th>
    		</#list>
    	<#elseif change=='2'>
    		<#if users?exists&&users?size gt 0>
    			<#list users as user>
    				<th width="${90/users?size}%" style="text-align:center;">${user.realname!}</th>
    			</#list>
    		</#if>
    	</#if>
    </tr>
    <#if months?exists && months?size gt 0>
    <#assign i=0>
    <#list months as month>
    	<tr>
    		<td style="text-align:center;">${month!}</td>
    	<#if change?default('1')==''||change=='1'>
    		<#list mcodelist as detail>
	    		<td style="text-align:center;">${desMap.get('${month!}_${detail.thisId!}')?default(0)}次</td>
    		</#list>
    	<#elseif change=='2'>
    		<#list users as user>
    			<td style="text-align:center;">${desMap.get('${month!}_${user.id!}')?default(0)}分<#if countMap.get('${month!}_${user.id!}')?exists>(${countMap.get('${month!}_${user.id!}')?default('')}次)</#if></td>
    		</#list>
    	</#if>
    	</tr>
    	<#assign i=i+1>
	</#list>
	<#if i==12>
		<tr>
			<#if change?default('1')==''||change=='1'>
			<td style="text-align:center;">总计</td>
			<#elseif change=='2'>
			<td style="text-align:center;">总评</td>
			</#if>
			<#if change?default('1')==''||change=='1'>
				<#list mcodelist as detail>
					<td style="text-align:center;">${desTalMap.get('${detail.thisId!}')?default(0)}次</td>
				</#list>
			<#elseif change=='2'>
				<#list users as user>
    				<td style="text-align:center;">${desTalMap.get('${user.id!}')?default(0)}分</td>
    			</#list>
			</#if>
		</tr>
	</#if>
	<#else>
     <tr>
   		<td colspan="${mcodelist?size+1}" > <p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	 </tr>
     </#if>
</table>
</div>
<#if months?exists && months?size gt 0>
<p class="t-center pt-30">
	<a href="javaScript:void(0);" onclick="doPrint();" class="abtn-blue-big">打印</a>
	<a href="javaScript:void(0);" onclick="doExport();" class="abtn-blue-big">导出</a>
</p>
</#if>
<script>
$(document).ready(function(){
	vselect();
});
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
