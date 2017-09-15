<#import "/common/htmlcomponent.ftl" as common />
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >
<@common.moduleDiv titleName="">
<@common.tableList id="tablelist">
	<tr>
		<th width="4%">序号</th>
		<th width="10%">会议室名称</th>
		<th width="40%">使用时间</th>
		<th width="10%">申请部门</th>
		<th width="10%">预约人</th>
		<th width="10%">审核人</th>
		<th width="10%">状态</th>
		<#if megAdmin>
		<th width="6%">操作</th>
		</#if>
	</tr>
	<#if officeBoardroomApplyXjs?exists && officeBoardroomApplyXjs?size gt 0>
		<#list officeBoardroomApplyXjs as x>
			<tr>
				<td>${x_index+1}</td>
				<td>${x.roomName!}</td>
				<td>${x.content!}</td>
				<td>${x.deptName!}</td>
				<td>${x.applyUserName!}</td>
				<td>${x.auditUserName!}</td>
				<td>
				<input id="auditOpinion_${x.id!}" name="auditOpinion" value="${x.auditOpinion!}" type="hidden" />
					<#if x.state == NEEDAUDIT+''>
						待审核
	        		<#elseif x.state == PASS+''>
	                	已通过
	                <#elseif x.state == UNPASS+''>
	                	<a href="javascript:void(0);" onclick="doOneOpinion1('${x.auditOpinion!}');">未通过</a>
	                </#if>
				</td>
				<#if megAdmin>
				<td>
				    <a href="javascript:void(0);" onclick="doDelete('${x.id!}');"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
		    		<!--<a href="javascript:void(0);" onclick="doInfo('${x.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>-->
				</td>
				</#if>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="8"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<div class="popUp-layer popUp-layer-tips" id="layer3" style="display:none;z-index:9999;">
    <p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>不通过原因</span></p>
    <div class="wrap">
    <@common.tableDetail>
        <tr id="purposeDiv">
            <th>不通过原因：</th>
            <td>
           		<textarea id="auditOpinion" msgName="不通过原因" name="auditOpinion" maxlength="100" readonly="true" style="width:250px;height:60px;"></textarea>
            </td>
        </tr>
    </@common.tableDetail>
    </div>
</div>
<@common.Toolbar container="#myOrderListDiv"/>
</@common.moduleDiv>
<script>
	function viewInfo(id){
	var url="${request.contextPath}/office/roomorder/roomorder-orderApplyView.action?applyNumberId="+id;
	load("#myOrderListDiv", url);
}
	function doOneOpinion1(obj){
		$('#layer3').jWindowOpen({
			modal:true,
			center:true,
			close:'#layer3 .close,#layer3 .reset'
		});
		$("#auditOpinion").val(obj);
	}
	function doDelete(id){
		if(showConfirm("确定要删除该申请记录")){
			$.getJSON("${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomOrderManageDelete.action",{"OfficeBoardroomApplyXjId":id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",searchOrder);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}

</script>