<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as common>
<script>
	function leavePass(id){
		if(!checkAllValidate("#auditDiv")){
			return;
		}
		var remrk=$("#auditRemark").val();
		var str="?applyId="+id+"&remark="+encodeURIComponent(remrk);
		var url="${request.contextPath}/office/studentLeave/studentLeave-approvePass.action"+str;
		$.getJSON(url,null,function(data){
			if(!data.operateSuccess){
				showMsgError(data.promptMessage);
			}else{
				showMsgSuccess(data.promptMessage,"提示",function(){
					studentLeaveApprove();
				});
			}
		});
	}
	
	function leaveUnPass(id){
		if(!checkAllValidate("#auditDiv")){
			return;
		}
		var remrk=$("#auditRemark").val();
		var str="?applyId="+id+"&remark="+encodeURIComponent(remrk);
		var url="${request.contextPath}/office/studentLeave/studentLeave-approveNoPass.action"+str;
		$.getJSON(url,null,function(data){
			if(!data.operateSuccess){
				showMsgError(data.promptMessage);
			}else{
				showMsgSuccess(data.promptMessage,"提示",function(){
					studentLeaveApprove();
				});
			}
		});
	}
	
	function backToList(){
		studentLeaveApprove();
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@htmlmacro.moduleDiv titleName="学生请假">
	<form id="auditDiv">
	<@htmlmacro.tableDetail divClass="table-form">
		<#if view>
		<tr>
			<th colspan="4" style="text-align:center;">学生请假申请</th>
		</tr>
		<#else>
		<tr>
			<th colspan="4" style="text-align:center;">学生请假审核</th>
		</tr>
		</#if>
		<tr>
			<th style="width:25%;">请假时间：</th>
			<td colspan="3">
    			${((officeStudentLeave.startTime)?string('yyyy-MM-dd HH:mm'))?if_exists}				
				至
				${((officeStudentLeave.endTime)?string('yyyy-MM-dd HH:mm'))?if_exists}				
			</td>
		</tr>
		<tr>
			<th>共计天数：</th>
			<td>${officeStudentLeave.days?string('0.#')!}</td>
			<th>申请人：</th>
			<td>
				${officeStudentLeave.stuName!}
			</td>
		</tr>
		<tr>
			<th>请假类型：</th>
			<td>
				${officeStudentLeave.leaveTypeName!}
			</td>
			<th>提交人：</th>
			<td>
				${officeStudentLeave.createUserName!}
			</td>
		</tr>
		<tr>
			<th>请假原因：</th>
			<td colspan="3">
				<textarea name="officeStudentLeave.remark" notNull="true" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" disabled="disabled" id="remark" rows="4" cols="70" maxlength="200">${officeStudentLeave.remark!}</textarea>
			</td>
		</tr>
		<#if !view>
		<tr>
			<th><span class="c-orange mr-5">*</span>审核意见：</th>
			<td colspan="3">
				<textarea cols="70" rows="4" id="auditRemark" name="officeStudentLeave.auditRemark" value="${officeStudentLeave.auditRemark!}"  class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" notNull="true" msgName="审核意见" maxLength="200"/>
			</td>
		</tr>
		</#if>
		</@htmlmacro.tableDetail>
		</form>
		<#if view>
		<@htmlmacro.tableDetail divClass="table-form">
			<tr>
				<th colspan="4" style="text-align:center;">审核信息</th>
			</tr>
			<tr>
				<th width="25%">审核人：</th>
				<td colspan="3">${officeStudentLeave.auditUserName!}</td>
			</tr>
			<tr>
			<th>审核意见：</th>
			<td colspan="3">
				${officeStudentLeave.auditRemark!}
			</td>
		</tr>		
		</@htmlmacro.tableDetail>
		</#if>
		<p class="pt-20 t-center">
		<#if !view>
		<a href="javascript:void(0)" class="abtn-blue" onclick="leavePass('${officeStudentLeave.id!}')">审核通过</a>
		<a href="javascript:void(0)" class="abtn-blue" onclick="leaveUnPass('${officeStudentLeave.id!}')">审核不通过</a>
    	</#if>
    	<a href="javascript:void(0)" class="abtn-blue" onclick="backToList()">返回</a>
	</p>
</@htmlmacro.moduleDiv>