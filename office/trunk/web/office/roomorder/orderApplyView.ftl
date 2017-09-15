<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="查看信息">
<iframe id="downloadFrame" name="downloadFrame" allowTransparency="true" frameBorder="0" width="0%" height="0%" scrolling="auto" src="" style="display:none;"></iframe>
<div id="orderAuditEditContainer">
<@htmlmacro.tableDetail divClass="table-form" id="auditTable">
    <tr>
        <th colspan="4" style="text-align:center;">查看信息</th>
    </tr>
    <tr>
        <th style="width:20%">申请人：</th>
        <td style="width:30%">
        	${officeApplyNumber.userName!}
        </td>
        <th style="width:20%">申请时间：</th>
        <td style="width:30%">
        	${officeApplyNumber.applyDate?string('yyyy-MM-dd')!}(${officeApplyNumber.weekDay!})
        </td>
    </tr>
	<tr>
        <th>申请内容：</th>
        <td colspan="3"  style="word-break:break-all; word-wrap:break-word;">
        	${officeApplyNumber.content!}
        </td>
    </tr>
    <#if officeApplyNumber.type == '70' && officeApplyNumber.meetingTheme?exists>
    <tr>
        <th style="width:20%">会议主题：</th>
        <td style="width:30%">
        	${officeApplyNumber.meetingTheme!}
        </td>
        <th style="width:20%">主持人：</th>
        <td style="width:30%">
        	${officeApplyNumber.hostUserName!}
        </td>
    </tr>
	<tr>
        <th>主办部门：</th>
        <td colspan="3"  style="word-break:break-all; word-wrap:break-word;">
        	${officeApplyNumber.deptNames!}
        </td>
    </tr>
    <tr>
        <th>与会人员：</th>
        <td colspan="3"  style="word-break:break-all; word-wrap:break-word;">
       		${officeApplyNumber.meetingUserNames!}
        </td>
    </tr>
    <tr>
        <th>用途：</th>
        <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
        	${officeApplyNumber.purpose!}
        </td>
    </tr>
    <tr>
        <th>附件：</th>
        <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
        	<#if officeApplyNumber.attachment?exists>
	        	<#assign att = officeApplyNumber.attachment/>
				${att.fileName!}
	            <a href="javascript:void(0);"  onclick="doDownload('${att.downloadPath!}');">下载</a>
			</#if>
        </td>
    </tr>
    <#elseif officeApplyNumber.type == '11'>
    <tr>
      	<th>实验名称：</th>
    	<td>
    		${officeLabSet.name!}
    	</td>
    	<th>教材页面：</th>
    	<td>
    		${officeLabSet.courseBook!}
    	</td>
    </tr>
    <tr>
      	<th>学科：</th>
    	<td <#if !hasGrade>colspan="3"</#if>>
    		${appsetting.getMcodeName("DM-SYSLX",officeLabSet.subject!)?default("")}
    	</td>
    	<#if hasGrade>
    	<th>年级：</th>
    	<td>
    		${officeLabSet.grade!}
    	</td>
    	</#if>
    </tr>
    <tr>
        <th>所需仪器：</th>
        <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
        	${officeLabSet.apparatus!}
        </td>
    </tr>
    <tr>
        <th>所需药品：</th>
        <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
        	${officeLabSet.reagent!}
        </td>
    </tr>
    <tr>
      	<th>上课班级：</th>
    	<td>
    		${officeLabInfo.className!}
    	</td>
    	<th>学生人数：</th>
    	<td>
    		${officeLabInfo.studentNum!}
    	</td>
    </tr>
    <tr>
      	<th>任课教师：</th>
    	<td>
    		${officeLabInfo.teacherName!}
    	</td>
    	<th>实验形式：</th>
    	<td>
    		<#if officeLabInfo.labMode?exists && officeLabInfo.labMode == '1'>
    		教师演示实验
    		<#elseif officeLabInfo.labMode?exists && officeLabInfo.labMode == '2'>
    		学生操作实验
    		<#else></#if>
    	</td>
    </tr>
    <tr>
        <th>用途：</th>
        <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
        	${officeApplyNumber.purpose!}
        </td>
    </tr>
    <#else>
    <tr>
        <th>用途：</th>
        <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
        	${officeApplyNumber.purpose!}
        </td>
    </tr>
    </#if>
    <#if officeApplyNumber.state gt 2>
    <tr>
        <th style="width:20%">审核人：</th>
        <td style="width:30%">
        	${officeApplyNumber.auditUserName!}
        </td>
        <th style="width:20%">审核状态：</th>
        <td style="width:30%">
        	<#if officeApplyNumber.state == 3>
        		通过
        	<#else>
        		不通过
        	</#if>
        </td>
    </tr>
    <tr>
        <th>审核意见：</th>
        <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
        	${officeApplyNumber.remark!}
        </td>
    </tr>
    <#if officeApplyNumber.state == 3>
	<tr>
        <th>反馈：</th>
        <td colspan="3" style="word-break:break-all; word-wrap:break-word;">
        	${officeApplyNumber.feedback!}
        </td>
    </tr>
	</#if>
    </#if>
    <tr>
    	<td colspan="4" class="td-opt">
		    <a class="abtn-blue ml-5" href="javascript:void(0);" onclick="back();">返回</a>
        </td>
    </tr>
</@htmlmacro.tableDetail>
</div>
<script type="text/javascript">
function back(){
	searchOrder();
}

function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}
</script>

</@htmlmacro.moduleDiv>