<#import "/common/htmlcomponent.ftl" as common />
<script>
function edit(id){
	var url="${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-addLeaveType.action?leaveTypeId="+id;
		openDiv("#classLayer","#classLayer .close,#classLayer .submit,#classLayer .reset",url);
}
function deleteType(id){
	if(confirm("确定要删除吗？")){
		$.getJSON("${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-deleteLeaveType.action", {
	          "leaveTypeId":id
	        }, function(data) {
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"提示",leaveTypeList);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		});
	}
}
</script>
<div class="popUp-layer" id="classLayer" style="display:none;width:450px;"></div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
	<div class="query-part fn-rel fn-clear">
		<a href="javascript:void(0);" class="abtn-orange-new fn-right" onclick="edit('');">新增类型</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort mt-15">
	<tr>
	    <th class="t-center" style="width:20%;">序号</th>
	    <th class="t-center" style="width:60%;">名称</th>
		<th class="t-center" style="width:20%;">操作</th>
	</tr>
	<#if leaveTypeList?exists && leaveTypeList?size gt 0>
	    <#list leaveTypeList as x>
	    <tr>
	        <td class="t-center">
	        	${x_index+1}
	        </td>
	        <td class="t-center">
	        	${x.name!}
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