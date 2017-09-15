<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/strspell.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/util.js"></script>
<script>
//保存
function validateform(){
		var options = {
	       url:'${request.contextPath}/basedata/order/serverAuthorize-saveByServerUser.action', 
	       success : showReply,
	       error : showError, 
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       timeout : 3000 
	    };
		$('#form1').ajaxSubmit(options);
}

function showReply(data){
	if(!data.operateSuccess){
		showMsgError(data.errorMessage);
		return;
	}else{
		showMsgSuccess(data.promptMessage, "提示", function(){
			load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-serverList.action?roleType=${roleType}");
		});
	}
}

function showError(data){
	showMsgError("保存异常");
}

function cancel(){
	load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-serverList.action?roleType=${roleType}");
}

</script>
<p class="table-dt">请选择需要授权的用户</p>
<form action="serverAuthorize-saveByServer.action" method="post" id="form1" >
<#list serverIds as serverId>
	<input type="hidden" name="serverIds" value="${serverId}">
</#list>
<@htmlmacro.tableList id="tablelist">
<#if users?exists &&(users?size>0)>	
  	<#list users?chunk(4) as row>
  		<tr>
  	  	<#list row as user>
		  <td width="25%" class="t-center"><p><span class="ui-checkbox <#if user.checked?default("") == "checked">ui-checkbox-current</#if>">
			  <label for="${user.id?default("")}">
				<input class="chk" type="checkbox" id="${user.id?default("")}" name="userIds" value="${user.id?default("")}" <#if user.checked?default("") == "checked">checked="checked"</#if> />
				${user.name}<#if user.realname?exists>(${user.realname})</#if>
			  </label>
			  <input type="hidden" name="allUserIds" id="allUserIds" value="${user.id?default("")}">
		 </span></p></td>
		</#list>
  	  	</tr>
  	</#list>
<#else>
<tr>
   <td colspan=4> <p class="no-data mt-50 mb-50">还没有任何记录或没有选择部门！</p></td>
</tr>  	
</#if>		
</@htmlmacro.tableList>
<@htmlmacro.ToolbarBlank>
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
    <a id="saveButton" href="javascript:validateform();" class="abtn-blue">保存</a>
    <a id="cancelButton" href="javascript:cancel();" class="abtn-blue">返回</a>
</@htmlmacro.ToolbarBlank>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>vselect();</script>
</@htmlmacro.moduleDiv>