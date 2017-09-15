<#import "/common/htmlcomponent.ftl" as common />
<#assign UNSUBMIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_SAVE") >
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >
<#assign CANCELNEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_CANCEL_NEED_AUDIT") >
<#assign CANCELPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_CANCEL_PASS") >
<form>
<@common.moduleDiv titleName="">
<@common.tableList id="tablelist">
	<tr>
		<th width="5%">序号</th>
		<th width="8%">乘车人数</th>
		<th width="15%" >用车时间</th>
		<th width="35%" >用车事由</th>
		<th width="10%" >单位审核</th>
		<th width="10%" >车辆信息</th>
		<th width="9%" >驾驶员</th>
		<th width="8%" style="text-align:center;">操作</th>
	</tr>
	<#if officeCarApplies?exists && officeCarApplies?size gt 0>
		<#list officeCarApplies as x>
			<tr>
				<td>${x_index + 1}</td>
				<td >${x.personNumber!}</td>
				<td >${x.useTime?string('yyyy-MM-dd HH:mm')}   (${x.xinqi!})</td>
				<td title="${x.reason!}">
					<@common.cutOff4List str="${x.reason!}" length=25 />
				</td>
				<td>
					<#if x.state == UNSUBMIT>
						未提交
					<#elseif x.state == NEEDAUDIT>
						待审核
					<#elseif x.state == PASS>
						已通过(${x.auditUserName!})
					<#elseif x.state == UNPASS>
						未通过(${x.auditUserName!})
					<#elseif x.state == CANCELNEEDAUDIT>
						撤销待审核
					<#else>
						撤销通过(${x.auditUserName!})
					</#if>
				</td>
				<td style="word-break:break-all; word-wrap:break-word;" title="${x.carNumber!}"><@common.cutOff4List str="${x.carNumber!}" length=25 /></td>
				<td style="word-break:break-all; word-wrap:break-word;" title="${x.driverName!}"><@common.cutOff4List str="${x.driverName!}" length=25 /></td>
				<td style="text-align:center;">
					<#if UNSUBMIT == x.state || UNPASS == x.state || (x.officeCarAudit?exists && UNPASS == x.officeCarAudit.state)>
					<a href="javascript:void(0)" onclick="editCarApply('${x.id!}')">编辑</a>
					<a href="javascript:void(0)" onclick="deleteCarApply('${x.id!}')" class="ml-10">删除</a>
					<#else>
					<#if CANCELNEEDAUDIT == x.state || CANCELPASS == x.state>
						<a href="javascript:void(0)" onclick="viewCancelCarApply('${x.id!}')">查看</a>
					<#else>
						<a href="javascript:void(0)" onclick="viewCarApply('${x.id!}')">查看</a>
					</#if>
					<#if PASS == x.state && !x.timeOut>
						<a href="javascript:void(0)" onclick="carCancelApply('${x.id!}')" class="ml-10">撤销</a>
					</#if>
					</#if>
				</td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="8"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<@common.Toolbar container="#carApplyListDiv"/>

<script>

function viewCarApply(id){
	var url="${request.contextPath}/office/carmanage/carmanage-carApplyView.action?applyId="+id+"&applyType=1";
	load("#carApplyListDiv", url);
}

function viewCancelCarApply(id){
	var url="${request.contextPath}/office/carmanage/carmanage-carCancelAuditEdit.action?applyId="+id+"&applyType=1";
	load("#carApplyListDiv", url);
}

function editCarApply(id){
	var url="${request.contextPath}/office/carmanage/carmanage-carApplyEdit.action?applyId="+id;
	load("#carApplyListDiv", url);
}

function deleteCarApply(id){
	if(confirm("确定要删除吗？")){
	    $.getJSON("${request.contextPath}/office/carmanage/carmanage-deleteCarApply.action?applyId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {
	        showMsgError(data);
	      } else {
	      	showMsgSuccess("删除成功！", "提示", function(){
				doSearch();
			});
	      }
	    });
	}
}

function carCancelApply(id){
	if(id && id != ''){
		if(showConfirm("确定要申请撤销？")){
			$.getJSON("${request.contextPath}/office/carmanage/carmanage-carCancelApply.action", 
			{"applyId":id}, function(data){
				if(data && data != ""){
					showMsgError(data);
				}else{
					showMsgSuccess('撤销申请成功!','提示');
					doSearch();
				}
			}).error(function(){
				showMsgError("撤销申请失败！");
			});
		}
	}
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>
</form>