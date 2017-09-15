<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
//授权
function doAuthorize(){
	var ids = [];
	var i=0;
	$("input[name='userIds'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		i++;
	});
	if(ids.length == 0){
		showMsgWarn("请先选择想要进行操作的用户！");
		return;
	}
	if(ids.length>1){
		  if(!showConfirm('您选择了多个用户进行授权，将会显示各用户所有授权的服务，确定吗？')){		
			return;
		  }
	}
	var ownerTypes = $("#ownerType").val();
	load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-authorizeByUser.action?"+$.param( {userType:2,ownerType:ownerTypes, userIds:ids},true));
}

//查找用户
function queryUser(){
	var queryUserName = $("#queryUserName").val();
	var queryUserRealName = $("#queryUserRealName").val();
	var ownerType = $("#ownerType").val();
	var url = "${request.contextPath}/basedata/order/serverAuthorize-userList.action?queryUserName="+queryUserName
							+"&queryUserRealName="+queryUserRealName+"&ownerType="+ownerType;
	load("#listDiv", url);
}

function doAuthorizeOnly(userId, userType, ownerType){
	load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-authorizeByUser.action?userIds="+userId+"&userType="+userType+"&ownerType="+ownerType);
}
</script>
<div id="containerUser">
<form name="searchForm" action="serverAuthorize-userList.action" method="post">
	<div class="query-builder">
    	<div class="query-part">
    		<div class="query-tt">角色类型：</div>
            <div class="ui-select-box fn-left" style="width:100px;" >
                <input type="text" class="ui-select-txt" value="" readonly/>
                <input name="ownerType" id="ownerType" type="hidden" value=""  class="ui-select-value" />
                <a class="ui-select-close"></a>
                <div class="ui-option" myfunchange="queryUser();">
                	<div class="a-wrap">
                    <#list ownerTypes as x>
                    	<a val="${x[0]}"  <#if ownerType?string==x[0]>class="selected"</#if>><span>${x[1]}</span></a>
                    </#list>
                    </div>
                </div>
            </div>
            <span>&nbsp;&nbsp;账号：</span>
            <input type="text" class="input-txt" onkeydown="if(event.keyCode==13)queryUser();" id="queryUserName" name="queryUserName" value="${queryUserName?default("")}">
            <span>&nbsp;&nbsp;姓名：</span>
            <input type="text" class="input-txt" onkeydown="if(event.keyCode==13)queryUser();" id="queryUserRealName" name="queryUserRealName" value="${queryUserRealName?default("")}">
            <a href="javascript:queryUser();" class="abtn-blue ml-30">查找</a>
            <div class="fn-clear"></div>
		</div>
	</div>
</form>
<form id="ec" method="post" >
<@htmlmacro.tableList>
	<tr>
		<th width="30">选择</th>
		<th width="30%">账号</th>
		<th width="30%">姓名</th>
		<th width="30%">操作</th>
	</tr>
<#if users?exists &&(users?size>0)>	
<#list users as x>
	<tr>	
		<td class="t-center"><p><span class="ui-checkbox <#if x.type?exists&&(x.type==1||x.type==0)>ui-checkbox-disabled</#if>">
			<input  class="chk" type="checkbox"  value="${x.id?default('')}"  <#if x.type?exists&&(x.type==1||x.type==0)>name="notuserIds"<#else>name="userIds"</#if>>
		</span></p></td>
		<td >
			${x.name?default('')}<#if x.type?exists&&(x.type==1||x.type==0)><font color="red">（单位管理员）</font></#if>
		</td>
		<td >
			${x.realname?default('')}
		</td>
		<td >
			<a href="javascript:doAuthorizeOnly('${x.id}', '${x.type}', '${ownerType}');">授权</a>
		</td>
	</tr>
</#list>
<#else>
		<tr>
           <td colspan=4> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
    	</tr>
</#if>
</@htmlmacro.tableList>
<@htmlmacro.Toolbar container="#containerUser">
    	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
        <a href="javascript:doAuthorize();" class="abtn-blue">授权</a>
</@htmlmacro.Toolbar>
</form>
</div>		
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>