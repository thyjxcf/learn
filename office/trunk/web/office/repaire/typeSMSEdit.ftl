<#import "/common/htmlcomponent.ftl" as htmlmacro />
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<form name="typeSMSForm" id="typeSMSForm" action="" method="post">
<p class="tt"><span>短信接收设置</span></p>
    <@htmlmacro.tableList style="margin-top:5px;">
	<tr>
    	<th width="20%">序号</th>
    	<th width="40%">类型</th>
    	<th width="40%">是否接收短信</th>
    </tr>
	<#if typeSMSlist?exists&&(typeSMSlist?size>0)>
		<#assign index = 0>
		<#list typeSMSlist as item >
        <tr>
        <td>${(index+1)!}</td>
        <td>
        <input type="hidden" id="id${index}" name="typeSMSlist[${index}].id" value="${item.id!}"/>
        <input type="hidden" id="unitId${index}" name="typeSMSlist[${index}].unitId" value="${item.unitId!}"/>
        <input type="hidden" id="thisId${index}" name="typeSMSlist[${index}].thisId" value="${item.thisId!}"/>
        ${item.typeName?default('')}
        </td>
        <td>
        	<@htmlmacro.select style="width:100px;" valName="typeSMSlist[${index}].isNeedSMSStr" valId="isNeedSMSStr${index}">
				<a val="1"  <#if item.isNeedSms?default(false)>class="selected"</#if>><span>是</span></a>
				<a val="0"  <#if !item.isNeedSms?default(false)>class="selected"</#if>><span>否</span></a>
			</@htmlmacro.select>
        </td>
		</tr>
		<#assign index=index+1>
		</#list>
	</#if>
</@htmlmacro.tableList>
    <p class="dd">
        <a href="javascript:void(0);" class="abtn-blue" id="saveBtn" onclick="saveTypeSMS();">确定</a>
        <a href="javascript:void(0);" class="abtn-blue ml-5" onclick="closeDiv('#classLayer3')">取消</a>
    </p>
</form>
<script>
vselect();
function showTypeSMSReply(data){
	var error = data;
	if(error && error != ''){
		showMsgError(data);
		$("#saveBtn").attr("class", "abtn-blue");
	} else {
		showMsgSuccess('保存成功!','提示',closeDiv('#classLayer3'));
	}
}

function saveTypeSMS(){
	//点击按钮，且验证通过后，按钮样式变为灰色
	$("#saveBtn").attr("class", "abtn-unable");
	
	var options = {
	       url:'${request.contextPath}/office/repaire/repaire-typeSMSSave.action', 
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       success : showTypeSMSReply
	    };
	try{
		$('#typeSMSForm').ajaxSubmit(options);
	}catch(e){
		showMsgError('保存失败！');
		$("#saveBtn").attr("class", "abtn-blue");
	}
}
</script>
</@htmlmacro.moduleDiv>