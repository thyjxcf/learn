<table width="100%" border="0" cellspacing="0" cellpadding="0" id="dataTable">
<#list teachers as x> 
	<#if x.userId?exists>
    <tr>
        <td  width='7%' align="center"><input type='checkbox' id='${x.userId}' name='addressIds' onclick='onSelection(this);' /></td>
        <td width='10%' >${x.name?default('')}</td>
        <td width='20%' >${x.unitName?default('')}</td>
        <td width='10%' ><#if x.sex='1'>男<#elseif x.sex='2'>女<#else>未填写</#if></td>
	  <td width='20%' title="${x.personTel!}">${x.personTel!}&nbsp;</td>
        <input type='hidden' name='type' value='teacher' >
        <input type='hidden' name='name' value='${x.name?default('')}' >
        <input type='hidden' name='id' value='${x.userId}' >
        <input type='hidden' name='ownerType' value='${x.ownerType}' > 
        <input type='hidden' name='mobile' value='${x.personTel!}' >
    </tr>
    </#if>
</#list>
</table>