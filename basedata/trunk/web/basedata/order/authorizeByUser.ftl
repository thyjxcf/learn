<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="用户授权">
<script>
function validateform(){
	var userIds = [];
	var serverIds = [];
	var i=0;
	var j=0;
	$("input[name='userIds'][type='hidden']").each(function(){
		userIds[i] = $(this).val();
		i++;
	});
	$("input[name='serverIds'][checked='checked']").each(function(){
		serverIds[j] = $(this).val();
		j++;
	});
	if(serverIds.length == 0){
		showMsgWarn("请先选择想要进行操作的用户！");
		return;
	}
	var ownerType = $("#ownerType").val();
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/basedata/order/serverAuthorize-saveByUser.action",
		data: $.param( {"ownerType":ownerType,"userIds":userIds, "serverIds":serverIds},true),
		success: function(data){
			if(data.jsonError != null && data.jsonError != ""){
					showMsgError(data.jsonError);
					return;
				}else{
					showMsgSuccess(data.promptMessage, "提示", function(){
						load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-userList.action");
					});
				}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(errorThrown);}
	});
	
}

function cancelUser(){
	var ownerType = $("#ownerType").val();
	load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-userList.action?ownerType="+ownerType);
}
</script>
<div id="containerAuth">
<form action="" method="POST" name="form1" id="form1" onsubmit="return validateform();">
<input type="hidden" name="ownerType" id="ownerType" value="${ownerType}">
	<div class="query-builder">
		<div class="query-tt">
		<#assign maxsize=10 />
		  	为以下用户进行授权：<#list users as user>${user.name?default('')}<input name="userIds" type="hidden" value="${user.id?default('')}"><#if user_has_next&&(user_index+1<maxsize)>、</#if><#if (user_index+1 >= maxsize)>等<#break></#if></#list>
		</div>
		<div class="fn-clear"></div>
	</div>	
	<table id="tablelist" border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
		<th width="30">选择</th>
		<th width="30%">服务代码</th>
		<th width="30%">服务名称</th>
		<th width="30%">首页地址</th>
<#if servers?exists &&(servers?size>0)>	
<#list servers as server>
	<tr>	
		<td class="t-center"><p><span class="ui-checkbox  <#if server.checked>ui-checkbox-current</#if>"><input name="serverIds" id="serverIds" type="checkbox" class="chk" <#if server.checked>checked="checked"</#if> value="${server.id?default("")}" ></span></p></td>
		<td >
			${server.code?default('')}
		</td>
		<td >
			${server.name?default('')}
		</td>
		<td >
			${server.url?default('')}
		</td>
	</tr>
</#list>
<#else>
		<tr>
           <td colspan=4> <p class="no-data mt-50 mb-50">没有可授权的服务！</p></td>
    	</tr>
</#if>
	</table>
<div class="base-operation">
        	<p class="opt">
            	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
                <a href="javascript:validateform();" id="roleUser" class="abtn-blue">保存</a>
                <a href="javascript:cancelUser();" id="" class="abtn-blue">返回</a>
            </p>
</div>	
</form>	
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>vselect();</script>
</@htmlmacro.moduleDiv>