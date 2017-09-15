<#import "/common/htmlcomponent.ftl" as common />
<script>
function deleteType(id){
	if(confirm("确定要删除吗？")){
		$.getJSON("${request.contextPath}/office/repaire/repaire-deleteType.action", {
	          "id":id
	        }, function(data) {
				if (data!=null && data != '') {//删除失败
			        showMsgError(data);
			    } else {//删除成功
			      	showMsgSuccess("删除成功！","提示",search);
			    }
		});
	}
}
</script>
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort ">
	<tr>
	    <th class="t-center" style="width:40%;">名称</th>
	    <th class="t-center" style="width:40%;">负责人</th>
		<th class="t-center" style="width:20%;">操作</th>
	</tr>
	<#if officeRepaireTypeList?exists && officeRepaireTypeList?size gt 0>
	    <#list officeRepaireTypeList as x>
	    <tr>
	        <td class="t-center">
	        	${x.typeName!}
	        </td>
	        <td class="t-center">
	        	${x.userNames!}
	        </td>
	        <td class="t-center">
	        	<a href="javascript:void(0);" onclick="edit('${x.id}');"><img src="${request.contextPath}/static/images/icon/edit.png" title="编辑"></a>
	        	&nbsp;
	        	<a href="javascript:void(0);" onclick="deleteType('${x.id}');"><img src="${request.contextPath}/static/images/icon/del2.png" title="删除"></a>
        	</td>
	    </tr>
	    </#list>
	<#else>
		<tr>
			<td colspan="3"><p class="no-data mt-20">还没有任何记录哦！</p></td>
		</tr>
	</#if>
</@common.tableList>
