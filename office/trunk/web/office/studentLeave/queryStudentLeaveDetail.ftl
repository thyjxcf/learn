<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as common>
<script>
	function backToCount(){
		studentLeaveQuery();
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@htmlmacro.moduleDiv titleName="学生请假">
	<@htmlmacro.tableDetail divClass="table-form">
		<tr>
			<th colspan="4" style="text-align:center;">学生请假申请</th>
		</tr>
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
		</@htmlmacro.tableDetail>
		
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
		<p class="pt-20 t-center">
    	<a href="javascript:void(0)" class="abtn-blue" onclick="backToCount()">返回</a>
		</p>
</@htmlmacro.moduleDiv>