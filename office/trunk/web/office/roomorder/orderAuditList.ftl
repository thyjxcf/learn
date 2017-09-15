<#import "/common/htmlcomponent.ftl" as common />
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >
<form>
<@common.moduleDiv titleName="">
<@common.tableList id="tablelist">
	<tr>
		<th width="5%">选择</th>
		<th width="5%">序号</th>
		<th width="14%">申请使用时间</th>
		<th width="10%">申请人</th>
		<th width="20%">申请信息</th>
		<th width="16%">用途</th>
		<th width="8%">审核状态</th>
		<th width="14%">反馈信息</th>
		<th width="8%">操作</th>
	</tr>
	<#if officeApplyNumberList?exists && officeApplyNumberList?size gt 0>
		<#list officeApplyNumberList as x>
			<tr>
				<td>
					<#if x.state == NEEDAUDIT>
					<span class="ui-checkbox"><input type="checkbox" class="chk" name="checkid" value="${x.id?default('')}"></span>
					</#if>
				</td>
				<td>${x_index+1}</td>
				<td>${x.applyDate?string('yyyy-MM-dd')!}(${x.weekDay!})</td>
				<td>${x.userName!}</td>
				<td title="${x.content!}"><@common.cutOff str='${x.content!}' length=35/></td>
				<td title="${x.purpose!}"><@common.cutOff str='${x.purpose!}' length=35/></td>
				<td>
				<#if x.state == NEEDAUDIT>
					待审核
                <#elseif x.state == PASS>
                	已通过
                <#elseif x.state == UNPASS>
                	未通过
                </#if>
				</td>
				<td style="word-break:break-all; word-wrap:break-word;" title="${x.feedback!}">
					<#if x.state == PASS>
	                	<@common.cutOff4List str="${x.feedback!}" length=24 />
	                </#if>
				</td>
				<#if x.state == NEEDAUDIT>
					<td><a href="javascript:void(0);" onclick="auditInfo('${x.id!}');"><img src="${request.contextPath}/static/images/icon/check.png" title="审核"></a></td>
				<#else>
            		<td>
            			<a href="javascript:void(0);" onclick="viewInfo('${x.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" title="查看"></a>
	                	<#if x.state == PASS>
	                		&nbsp;
	                		<a href="javascript:feedBack('${x.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" title="反馈"></a>
	                	</#if>
        			</td>
                </#if>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="9"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<@common.Toolbar container="#orderAuditListDiv">
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
    <a href="javascript:passSelected();" class="abtn-blue">通过</a>
</@common.Toolbar>
</form>
<div class="popUp-layer" id="classLayer" style="display:none;width:450px;"></div>
<script>
function auditInfo(id){
	var url="${request.contextPath}/office/roomorder/roomorder-orderAuditEdit.action?applyNumberId="+id;
	load("#orderAuditListDiv", url);
}

function viewInfo(id){
	var url="${request.contextPath}/office/roomorder/roomorder-orderApplyView.action?applyNumberId="+id;
	load("#orderAuditListDiv", url);
}

function passSelected(){
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("请先选择想要进行操作的数据！");
		return;
	}
	if(!confirm("确定要批量通过吗？")){
		return;
	}
	var ids = [];
	var i = 0;
	$("input[name='checkid'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		i++;
	});
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/roomorder/roomorder-passSelected.action",
		data: $.param( {applyNumberIds:ids},true),
		success: function(data){
			  if (data!=null && data != '') {
		        showMsgError(data);
		      } else {
		      	showMsgSuccess("操作成功！", "提示", function(){
					searchOrder();
				});
		      }
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function feedBack(applyNumberId){
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/roomorder/roomorder-feedback.action?applyNumberId="+applyNumberId, null, null, "500px");
}

</script>
</@common.moduleDiv>