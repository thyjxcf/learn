<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="">
<form name="reportForm" id="reportForm" action="" method="post">
<p class="tt"><span><#if !userId?exists || userId == ''>新增<#else>编辑</#if>权限</span></p>
    <div class="wrap">
    	<table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-5">
        	<tr>
        		<th><span class="c-red mr-5">*</span>用户：</th>
        		<td>
        			<#if !userId?exists || userId == ''>
				<@commonmacro.selectOneUser idObjectId="userId" nameObjectId="userName" width="600">
					<input type="hidden" name="userId" id="userId" class="select_current02" msgName="用户" notNull="true" value=""> 
					<input type="text" name="userName" id="userName" value="" readonly="readonly" class="select_current02" size="20">
	            </@commonmacro.selectOneUser>
					<#else>
					<input type="hidden" name="userId" id="userId" class="select_current02" msgName="用户" notNull="true" value="${userId!}"/> 
					<input type="text" name="userName" id="userName" value="${userName!}" readonly="readonly" class="input-txt" size="20">
				</#if>
                </td>
        	</tr>
        	<#if loginInfo.unitClass == 2>
            <tr id="clsTr" <#if teachAreaList?size lt 2>style="display:none;"</#if>>
                <th><span class="c-red mr-5">*</span>校区：</th>
                <td>
                	<#assign flag=0>
					<#list teachAreaList as area>
						<#assign flag=0>
						<#list arealist as x>
							<#if x.teachAreaId == area.id>
								<#assign flag=1>
							</#if>
						</#list>
			                  <span class="mt-5 ui-checkbox <#if flag == 1>ui-checkbox-current</#if>">
			                    	<input name="areaIds" id="areaId" value="${area.id}" type="checkbox" class="chk" <#if flag == 1>checked="checked"</#if>>${area.areaName!}
			                    </span>
					</#list>
                </td>
            </tr>
            </#if>
            <tr id="clsTr">
                <th><span class="c-red mr-5">*</span>类别：</th>
                <td>
                	<#assign flag2=0>
					<#list mcodelist as m>
						<#assign flag2=0>
						<#list typelist as x>
							<#if x.type == m.thisId>
								<#assign flag2=1>
							</#if>
						</#list>
			                  <span class="mt-5 ui-checkbox <#if flag2 == 1>ui-checkbox-current</#if>">
			                    	<input name="typeIds" id="typeId" value="${m.thisId}" type="checkbox" class="chk" <#if flag2 == 1>checked="checked"</#if>>${m.content!}
			                    </span>
					</#list>
                </td>
            </tr>
        </table>
    </div>
    <p class="dd">
        <a href="javascript:void(0);" class="abtn-blue" id="saveBtn" onclick="saveAudit();">确定</a>
        <a href="javascript:void(0);" class="abtn-blue ml-5" onclick="closeDiv('#classLayer3')">取消</a>
    </p>
</form>
<script>
vselect();
function validate(){
	if(!isActionable("#saveBtn")){
		return false;
	}
	var userId = document.getElementById('userId').value;
	if(userId == ''){
		showMsgError('用户不能为空！');
		return false;
	}
	return true;
}

function reloadRep(){
	var url="${request.contextPath}/office/repaire/repaire-audit.action";
	load("#repaireDiv",url);
}

function showReply(data){
	var error = data;
	if(error && error != ''){
		showMsgError(data);
		$("#saveBtn").attr("class", "abtn-blue");
	} else {
		showMsgSuccess('保存成功!','提示',reloadRep);
	}
}

function saveAudit(){
	if(!validate()){
		return;
	}
	//点击按钮，且验证通过后，按钮样式变为灰色
	$("#saveBtn").attr("class", "abtn-unable");
	
	var options = {
	       url:'${request.contextPath}/office/repaire/repaire-saveAudit.action', 
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       success : showReply
	    };
	try{
		$('#reportForm').ajaxSubmit(options);
	}catch(e){
		showMsgError('保存失败！');
		$("#saveBtn").attr("class", "abtn-blue");
	}
}
</script>
</@common.moduleDiv>