<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="教师请假申请单">
<div id="printArea">
<@htmlmacro.tableDetail divClass="table-form">
	<tr style="line-height:3;">
		<th colspan="6" style="text-align:center;font-size:23px;font-weight:bold;" class="mt-20">宁海职教中心教师请假单</th>
	</tr>
	<tr style="line-height:2;">
		<th width="10%">姓名</th>
		<td width="20%" style="text-align:center;">${officeTeacherLeaveNh.userName!}</td>
		<th width="10%">部门</th>
		<td width="20%" style="text-align:center;">${officeTeacherLeaveNh.deptName!}</td>
		<th width="10%">离校类别</th>
		<td width="20%" style="text-align:center;">${officeTeacherLeaveNh.leaveTypeName!}</td>
	</tr>
	<tr style="line-height：10px;">
		<th width="30%">事由</th>
		<td width="70%" colspan="5">${officeTeacherLeaveNh.remark!}</td>
	</tr>
	<tr style="line-height:2;">
		<th width="30%">起止时间</th>
		<td width="70%" colspan="5">
			${((officeTeacherLeaveNh.beginTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}
	        	至
	        ${((officeTeacherLeaveNh.endTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}
		</td>
	</tr>
	<tr style="line-height:3;valign:top;">
			<th rowspan="${officeTeacherLeaveNh.hisTaskList?size+1}">审核签字与工作安排</th>
        	<#list officeTeacherLeaveNh.hisTaskList as item>
        		<#if item_index  gt 0>
        		</tr>
        		<tr style="line-height:3;valign:top;">
        		</#if>
        		<td style="text-align:center;">${item.taskName!}:</td>
        		<td colspan="4">
        			<#if item.comment.commentType==1>${item.comment.textComment!}</#if>
        		</td>
        	</#list>
	</tr>
	<tr>
		<td style="text-align:center;">校长:</td>
		<td colspan="4">
			&nbsp;
		</td>
	</tr>
</@htmlmacro.tableDetail>
</div>
</@htmlmacro.moduleDiv>