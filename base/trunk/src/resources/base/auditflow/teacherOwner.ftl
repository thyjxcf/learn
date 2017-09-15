<#macro ownerSelect>
<tr class="first"><th colspan="4" class="tt">职工信息：</th></tr>
<tr>
  <th><span>*</span>在编教职工：</th>
  <td colspan="3">
    <input type="hidden" name="apply.business.${apply.business.ownerIdName}" id="ownerId" value="${apply.owner.id?default('')}">
    <input name="ownerName" id="ownerName" value="${apply.owner.name?default('')}" readonly type="text" class="input-txt150 input-readonly" msgName="教师姓名" notNull="true" maxLength="16">
    <span id="ownerSpan"><@commonmacro.selectOneTeacher idObjectId="ownerId" nameObjectId="ownerName" otherParam="unitType=1" callback="changeOwnerCallback"/></span>
  </td>
</tr>
<script>

function isExistOwnerId(){
	var ownerId = jQuery("#ownerId").val();
	if (ownerId == ''){
		addFieldError('ownerName', '请选择教职工！');
		return false;
	}
	return true;
}

function getOwnerId(){
	var ownerId = jQuery("#ownerId").val();
	return "ownerId=" + ownerId;
}

</script>
</#macro>


