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
		<th width="10%">会议室名称</th>
		<th width="50%">使用时间</th>
		<th width="10%">申请部门</th>
		<th width="10%">预约人</th>
		<#if show?default("0")=="0">
		<th width="10%">操作</th>
		</#if>
	</tr>
	<#if officeBoardroomApplyXjs?exists && officeBoardroomApplyXjs?size gt 0>
		<#list officeBoardroomApplyXjs as x>
			<tr>
				<td>
					<#if x.state == NEEDAUDIT+''>
					<span class="ui-checkbox"><input type="checkbox" class="chk" name="checkid" value="${x.id?default('')}"></span>
					</#if>
				</td>
				<td>${x_index+1}</td>
				<td>${x.roomName!}</td>
				<td>${x.content!}</td>
				<td>${x.deptName!}</td>
				<td>${x.applyUserName!}</td>
				<#if x.state == NEEDAUDIT+''>
				<td>
					<a href="javascript:void(0);" onclick="auditInfo('${x.id!}',3);">通过</a>
					<a href="javascript:void(0);" onclick="auditInfo('${x.id!}',4);">不通过</a>
				</td>
                </#if>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="7"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<@common.Toolbar container="#myOrderListDiv">
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
    <a href="javascript:passSelected(3);" class="abtn-blue">通过</a>
    <a href="javascript:passSelected(4);" class="abtn-blue">不通过</a>
</@common.Toolbar>
<input type="hidden" id="officeBoardroomApplyXjId" name="officeBoardroomApplyXjId" value="">
<div class="popUp-layer popUp-layer-tips" id="layer3" style="display:none;z-index:9999;">
    <p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span><span style="color:red;">*</span>不通过原因</span></p>
    <div class="wrap">
    <@common.tableDetail>
        <tr id="purposeDiv">
            <th>不通过原因：</th>
            <td>
           		<textarea id="auditOpinion" msgName="不通过原因" name="auditOpinion" maxlength="100" style="width:250px;height:60px;"></textarea>
            </td>
        </tr>
    </@common.tableDetail>
    </div>
        <p class="t-center pb-10">
            <a class="abtn-blue" href="javascript:void(0);" onclick="saveApplyInfo()">提交</a>
            <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
        </p>
</div>
</form>
<div class="popUp-layer" id="classLayer" style="display:none;width:450px;"></div>
<script>
function auditInfo(id,obj){
	var ids = [];
	ids[0] = id;
	if(obj==4){
		$('#layer3').jWindowOpen({
			modal:true,
			center:true,
			close:'#layer3 .close,#layer3 .reset'
		});
		$("#officeBoardroomApplyXjId").val(id);
		return;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomAuditSave.action",
		data: $.param( {applyRoomIds:ids,show:3},true),
		success: function(data){
			  if(!data.operateSuccess){
			  		 if(data.errorMessage!=null&&data.errorMessage!=""){
				  		 showMsgError(data.errorMessage);
				  		 isSubmit = false;
				  		 return;
			  		 }
				}else{
					showMsgSuccess(data.promptMessage,"", function(){
			    		searchOrder();
					});
					return;
				}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function viewInfo(id){
	var url="${request.contextPath}/office/roomorder/roomorder-orderApplyView.action?applyNumberId="+id;
	load("#orderAuditListDiv", url);
}

function passSelected(obj){
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("请先选择想要进行操作的数据！");
		return;
	}
	if(!confirm("确定要批量操作吗？")){
		return;
	}
	if(obj==4){
		$('#layer3').jWindowOpen({
			modal:true,
			center:true,
			close:'#layer3 .close,#layer3 .reset'
		});
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
		url: "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomAuditSave.action",
		data: $.param( {applyRoomIds:ids,show:3},true),
		success: function(data){
			  if(!data.operateSuccess){
			  		 if(data.errorMessage!=null&&data.errorMessage!=""){
				  		 showMsgError(data.errorMessage);
				  		 isSubmit = false;
				  		 return;
			  		 }
				}else{
					showMsgSuccess(data.promptMessage,"", function(){
			    		searchOrder();
					});
					return;
				}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}
function saveApplyInfo(){
	var auditOpinion=$("#auditOpinion").val();
	if(auditOpinion==''||auditOpinion==null){
		showMsgWarn("请填写不通过原因！");
		return;
	}
	var ids = [];
	var i = 0;
	$("input[name='checkid'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		i++;
	});
	if(ids==null||ids==''){
		var officeBoardroomApplyXjId=$("#officeBoardroomApplyXjId").val();
		ids[0] = officeBoardroomApplyXjId;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomAuditSave.action",
		data: $.param( {applyRoomIds:ids,auditOpinion:auditOpinion,show:4},true),
		success: function(data){
			  if(!data.operateSuccess){
			  		 if(data.errorMessage!=null&&data.errorMessage!=""){
				  		 showMsgError(data.errorMessage);
				  		 isSubmit = false;
				  		 return;
			  		 }
				}else{
					showMsgSuccess(data.promptMessage,"", function(){
			    		searchOrder();
					});
					return;
				}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

</script>
</@common.moduleDiv>