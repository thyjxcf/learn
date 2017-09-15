<#include "/common/auditflow/applyList.ftl"/> 
<#-- 列标题 -->
<#macro colHeads>
<th></th>
<th></th>
</#macro>

<#-- 列值 -->
<#macro colValues>
<td></td>
<td></td>
</#macro>

<#-- 维护：新增或修改 -->
<#macro businessEdit>
<tr class="first">
	<th colspan="4" class="tt">信息：</th>
	
</tr>

<script>
function validator(){
	if(!checkAllValidate()){
		return false;
	}	
}
</script>
</#macro>

<#-- 明细查看 -->
<#macro businessView>
<tr class="first">
	<th colspan="4" class="tt">信息：</th>
	
</tr>
</#macro>

