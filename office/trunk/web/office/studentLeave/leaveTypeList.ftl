<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
	<div class="query-builder-no pt-20" >
	<div class="query-part fn-rel fn-clear promt-div">
		<a href="javascript:void(0);" onclick="addLeaveType();" class="abtn-orange-new fn-right applyForBtn" style="">新增请假类型</a>
	</div>
	</div>
	
	<@htmlmacro.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort mt-15">
		<th width="20%">序号</th>
		<th width="60%">请假类型</th>
		<th width="20%">操作</th>
		<#if leaveTypeList?exists & leaveTypeList?size gt 0>
		<#list leaveTypeList as leave>
			<tr>
				<td>${leave_index+1}</td>
				<td>${leave.name!}</td>
				<td>
					<a href="javascript:void(0)" onclick="editLeaveType('${leave.id!}')"><img src="${request.contextPath}/static/images/icon/edit.png" title="编辑"></a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="deleteLeaveType('${leave.id!}')"><img src="${request.contextPath}/static/images/icon/del2.png" title="删除"></a>
				</td>
			</tr>
		</#list>
		<#else>
			<tr> 
				<td colspan="3"><p class="no-data mt-20">还没有任何记录哦!</p></td>
			</tr>
		</#if>
	
	</@htmlmacro.tableList>
	<div class="popUp-layer" id="classLayer" style="display:none;width:450px;"></div>	
<script>
	function addLeaveType(){
		var url="${request.contextPath}/office/studentLeave/studentLeave-addLeaveType.action";
		openDiv("#classLayer","#classLayer .close,#classLayer .submit,#classLayer .reset",url,null,null,"200px",function(){
			
		});
	}

	function deleteLeaveType(id){
		if(confirm("确认要删除吗？")){
			$.getJSON("${request.contextPath}/office/studentLeave/studentLeave-delLeaveType.action?leaveTypeId="+id,null,
				function(data){
					if(!data.operateSuccess){
						showMsgError(data.promptMessage);
					}else{
						showMsgSuccess("删除成功","提示",function(){
							load("#studentAdminDiv","${request.contextPath}/office/studentLeave/studentLeave-leaveType.action");
						});
					}
				}
			);
		}
	}
	
	function editLeaveType(id){
		var url="${request.contextPath}/office/studentLeave/studentLeave-addLeaveType.action?leaveTypeId="+id;
		openDiv("#classLayer","#classLayer .close,#classLayer .submit,#classLayer .reset",url,null,null,"200px",function(){
			
		});
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>