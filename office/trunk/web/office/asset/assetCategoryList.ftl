<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doAdd(){
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/asset/assetAdmin-addCategory.action", null, null, "500px");
}

function doEdit(id){
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/asset/assetAdmin-updateCategory.action?category.id="+id, null, null, "500px");
}
	
function doDelete(id){
	if(!showConfirm('您确认要删除吗？')){
		return;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/asset/assetAdmin-deleteCategory.action",
		data: $.param( {"category.id":id},true),
		success: function(data){
			if(!data.operateSuccess){
					showMsgError(data.errorMessage);
					return;
				}else{
					showMsgSuccess("删除成功", "提示", function(){
						load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-category.action");
					});
					return;
				}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}	
	
</script>
<div class="pub-table-inner">
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
        <a href="javascript:void(0);" onclick="doAdd();" class="abtn-orange-new fn-right applyForBtn" style="">新增</a>
        <div class="fn-clear"></div>
    </div>
</div>
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="30%">类别</th>
		<th width="30%">处室负责人</th>
		<th width="30%">分管校领导</th>
		<th class="t-center" width="10%">操作</th>
	</tr>
	<#if categorylist?exists && (categorylist?size>0)>
		<#list categorylist as ent>
		    <tr>
				<td>${ent.assetName?default("")}</td>
				<td>${ent.deptLeaderName?default("")}</td>
				<td>${ent.leaderName?default("")}</td>
				<td class="t-center">
					<a href="javascript:doEdit('${ent.id}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a>
					&nbsp;&nbsp;&nbsp;<a href="javascript:doDelete('${ent.id}');"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="4"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
</form>
</div>
<div class="popUp-layer" id="classLayer" style="display:none;width:400px;"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>