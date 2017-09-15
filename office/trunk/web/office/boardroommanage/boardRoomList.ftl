<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">

<@htmlmacro.tableList class="public-table table-list table-list-edit mt-5">
  	<tr>
    	<th width="15%">会议室名称</th>
    	<th width="10%">预约审核</th>
    	<th width="10%">容纳人数</th>
    	<th width="42%">配置说明</th>
    	<#if applyAdmin>
    	<th width="8%">预约申请</th>
    	</#if>
    	<th class="t-center" width="15%">操作</th>
    </tr>
    <#if officeBoardroomXjList?exists && officeBoardroomXjList?size gt 0>
    	<#list officeBoardroomXjList as officeBoardroomXj>
    		<tr>
    			<td >${officeBoardroomXj.name!}</td>
    			<td >
    				<#if officeBoardroomXj.needAudit=='1'>
		    			是
		    		<#elseif officeBoardroomXj.needAudit=='0'>
		    			否
		    		</#if>
    			</td>
    			<td >${officeBoardroomXj.maxNumber!}</td>
		    	<td >${officeBoardroomXj.content!}</td>
		    	<#if applyAdmin>
		    	<td class="t-center">
		    			<a href="javascript:void(0);" onclick="doOrder('${officeBoardroomXj.id!}');">申请</a>
		    	</td>
		    	</#if>
		    	<td class="t-center">
		    			<#if megAdmin>
		    			<a href="javascript:void(0);" onclick="doEdit('${officeBoardroomXj.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a>
		    			<a href="javascript:void(0);" onclick="doDelete('${officeBoardroomXj.id!}');"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
		    			</#if>
		    			<a href="javascript:void(0);" onclick="doInfo('${officeBoardroomXj.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
		    	</td>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='6'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if officeBoardroomXjList?exists && officeBoardroomXjList?size gt 0>
<@htmlmacro.Toolbar container="#boardRoomListDiv">
</@htmlmacro.Toolbar>
</#if>
<script>

	function doDelete(id){
		if(showConfirm("确定要删除该会议室")){
			$.getJSON("${request.contextPath}/office/boardroommanage/boardroommanage-deleteBoardRoom.action",{"officeBoardroomXjId":id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",doSearch);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
	function doEdit(id){
		//load("#mySurveyListDiv","${request.contextPath}/office/boardroommanage/boardroommanage-addBoardRoom.action?officeBoardroomXjId="+id);
		openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/boardroommanage/boardroommanage-addBoardRoom.action?officeBoardroomXjId="+id, null, null, "1000px");
	}
	function doInfo(id){
		//load("#mySurveyListDiv","${request.contextPath}/office/boardroommanage/boardroommanage-addBoardRoom.action?officeBoardroomXjId="+id);
		openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/boardroommanage/boardroommanage-ViewBoardRoom.action?officeBoardroomXjId="+id, null, null, "1000px");
	}
	function doOrder(id){
		load("#boardRoomDiv","${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomOrderAdmin.action?officeBoardroomXjId="+id);
	}
	
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>