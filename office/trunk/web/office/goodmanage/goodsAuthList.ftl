<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form id="listform" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<th class="t-center" width="15%">选择</th>
		<th width="30%">用户名</th>
		<th width="30%">姓名</th>
		<th width="25%" style="text-align:center;">操作</th>
	</tr>
	<#if goodsTypeAuthList?exists && goodsTypeAuthList?size gt 0>
		<#list goodsTypeAuthList as item>
			<tr>
				<td class="t-center"><p><span class="ui-checkbox">
		    		<input type="checkbox" class="chk" name="checkid" value="${item.id?default('')}">
		    		</span></p>
		    	</td>
				<td>${item.userName!}</td>
				<td>${item.realName!}</td>
				<td class="t-center">
	            	<a href="javascript:doGoodsTypeAuthEdit('${item.id!}');">查看权限</a>
	            </td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="4"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if goodsTypeAuthList?exists && goodsTypeAuthList?size gt 0>
<@common.Toolbar container="#goodsTypeAuthListDiv">
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</input></span>
	<a href="javascript:void(0);" id="btnDelete" class="abtn-blue"  onclick="doDelete()"> 删除</a>
</@common.Toolbar>
</#if>
</form>
<script>
function doDelete(){
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("没有选要删除的用户，请先选择!");
		return;
	}
	if(!showConfirm('您确认要删除用户吗？')){
		return;
	}
	$("#btnDelete").attr("class", "abtn-unable");	
	var options = {
       url:'${request.contextPath}/office/goodmanage/goodmanage-goodsAuth-delete.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#listform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnDelete").attr("class", "abtn-blue");
	if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   return;
		   }else if(data.fieldErrorMap!=null){
			  $.each(data.fieldErrorMap,function(key,value){
				   addFieldError(key,value+"");
			  });
		   }else{
		   	   showMsgError(data.promptMessage);
			   return;
		   }
	}else{
	   		showMsgSuccess(data.promptMessage,"",doSearch);
			return;
	}
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>