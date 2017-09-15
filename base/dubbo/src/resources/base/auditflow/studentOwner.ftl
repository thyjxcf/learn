<#macro ownerSelect>
<tr class="first"><th colspan="4" class="tt">学生信息：</th></tr>
<tr>
	<th><span>*</span>学生姓名：</th>
	<td>
	    <input type="hidden" name="apply.business.${apply.business.ownerIdName}" id="ownerId" value="${apply.owner.id?default('')}">
	    <input name="ownerName" id="ownerName" value="${apply.owner.name?default('')}" readonly type="text" class="input-txt150 input-readonly" msgName="学生姓名" notNull="true" maxLength="16">
	    <span id="ownerSpan"><@commonmacro.selectOneStudentTree idObjectId="ownerId" nameObjectId="ownerName" callback="changeOwnerCallback"/></span>
	</td>
  
	<th><span>*</span>学籍号：</th>
	<td><input type="text" id="ownerCode" class="input-txt150 input-readonly" readonly  value="${apply.owner.code!}"/>
	</td>
</tr>

<script>

function isExistOwnerId(){
	var ownerId = jQuery("#ownerId").val();
	if (ownerId == ''){
		addFieldError('ownerName', '请选择学生！');
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


