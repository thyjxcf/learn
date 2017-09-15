<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list table-list-edit mt-5">
    <tr>
    	<th width="50%">模板名称</th>
    </tr>
    <#if (templateList?size>0)>
        <#list templateList as item>
	        <tr>
	        	<td onclick="setActualInfo('${item.id!}','${item.title!}');">${item.title!}</td>
	        </tr>
        </#list>
    <#else>
    	<tr>
   			<td colspan="10"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	 	</tr>
    </#if>
</table>