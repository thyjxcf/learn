<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="">
<form name="reportForm" id="reportForm" action="" method="post">
	<input type="hidden" name="officeRepaireType.id" value="${officeRepaireType.id!}"/>
	<p class="tt"><span>二级类别维护</span></p>
	<div class="wrap">
		<table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-5">
	    	<tr>
	    		<th style="width:30%;"><span class="c-red mr-5">*</span>二级类别名称：</th>
	    		<td style="width:70%;">
					<input type="text" name="officeRepaireType.typeName" id="typeName" value="${officeRepaireType.typeName!}" notNull="true" maxLength="50" class="input-txt" style="width:200px;"/>
	            </td>
	    	</tr>
	        <tr>
	            <th><span class="c-red mr-5">*</span>所属类别：</th>
	            <td>
	            	<div class="select_box fn-left">
					    <@common.select valName="officeRepaireType.thisId" valId="officeRepaireType.thisId" notNull="true" style="width:210px;">
							<a val="">请选择</a>
							<#list mcodelist as m>
								<a <#if officeRepaireType.thisId?default('') == m.thisId>class="selected"</#if> val="${m.thisId}">${m.content!}</a>
							</#list>
						</@common.select>
					</div>
	            </td>
	        </tr>
	        <tr>
	            <th><span class="c-red mr-5">*</span>负责人：</th>
	            <td>
	            	<@commonmacro.selectMoreUser idObjectId="userIds" nameObjectId="userNames" width=400 height=300>
						<input type="hidden" id="userIds" name="officeRepaireType.userIds" value="${officeRepaireType.userIds!}"/> 
						<input type="text" name="userNames" id="userNames" value="${officeRepaireType.userNames!}" notNull="true" msgName="负责人" class="input-txt" style="width:200px;" readonly="readonly"/>
			  		</@commonmacro.selectMoreUser>
	            </td>
	        </tr>
	    </table>
	</div>
	<p class="dd">
	    <a href="javascript:void(0);" class="abtn-blue" id="saveBtn" onclick="saveType();">保存</a>
	    <a href="javascript:void(0);" class="abtn-blue ml-5" onclick="closeDiv('#classLayer3');">取消</a>
	</p>
</form>
<script>
vselect();
var isSubmit = false;
function saveType(){
	if(isSubmit){
		return;
	}
	if(getAbsoluteLength($("#userIds").val()) > 1000){
		showMsgError('负责人不能超过30个！');
		return;
	}
	if(!checkAllValidate("#reportForm")){
		return false;
	}
	isSubmit = true;
	var options = {
	       url:'${request.contextPath}/office/repaire/repaire-saveType.action', 
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
		isSubmit = false;
	}
}

function showReply(data){
	if(data && data != ''){
		showMsgError(data);
		isSubmit = false;
	} else {
		showMsgSuccess('保存成功!','提示',search);
	}
}

function getAbsoluteLength(str) {
	  var len = 0;
	  for ( var i = 0; i < str.length; i++) {
	    str.charCodeAt(i) > 255 ? len += 2 : len++;
	  }
	  return len;
 }
</script>
</@common.moduleDiv>