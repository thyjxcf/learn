<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="审核">
<iframe id="downloadFrame" name="downloadFrame" allowTransparency="true" frameBorder="0" width="0%" height="0%" scrolling="auto" src="" style="display:none;"></iframe>
<div id="orderAuditEditContainer">
<form name="orderAuditForm" id="orderAuditForm">
<@htmlmacro.tableDetail divClass="table-form" id="auditTable">
    <tr>
        <th colspan="4" style="text-align:center;">申请信息</th>
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
    <#if officeApplyNumber.type == '70'>
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
    	<td colspan="3">
    		${appsetting.getMcodeName("DM-SYSLX",officeLabSet.subject!)?default("")}
    	</td>
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
    <tr>
        <th>审核意见：</th>
        <td colspan="3">
        	<textarea name="remark" id="remark" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" notNull="true" msgName="审核意见" maxLength="200"></textarea>
        </td>
    </tr>
    <tr>
    	<td colspan="4" class="td-opt">
		    <a class="abtn-blue ml-5" href="javascript:void(0);" onclick="audit(3,'${officeApplyNumber.id!}');">通过</a>
		    <a class="abtn-blue ml-5" href="javascript:void(0);" onclick="audit(4,'${officeApplyNumber.id!}');">不通过</a>
		    <a class="abtn-blue ml-5" href="javascript:void(0);" onclick="back();">返回</a>
        </td>
    </tr>
</@htmlmacro.tableDetail>
</form>
</div>
<script type="text/javascript">
function audit(state,id){
	var remark =  document.getElementById("remark").value;
	if(state == 4 &&　remark == ''){
		showMsgError('不通过必须填写审核意见');
		return false;
	}
	if(_getLength(remark) > 200){
		showMsgError('审核意见不能超过200个字符');
		return false;
	}
	
	if(confirm("确定要审核吗？")){
	    $.getJSON(_contextPath + "/office/roomorder/roomorder-audit.action",
	    {"applyNumberId":id,"remark":remark,"auditState":state}, 
	    function(data) {
	      if (data!=null && data != '') {
	        showMsgError(data);
	      } else {
	      	showMsgSuccess("操作成功！", "提示", function(){
				searchOrder();
			});
	      }
	    });
	}
}

function back(){
	searchOrder();
}

function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}
</script>

</@htmlmacro.moduleDiv>