<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script language="javascript">
var isSubmit = false;
function issueAuditSave(state){
	if(isSubmit){
    	return;
    }
	if(!checkAllValidate("#executiveMeetDiv")){
		return;
	}
	if(state=="4"){
		var auditRemark = document.getElementById("auditRemark").value;
		if(auditRemark == ""){
			showMsgWarn("请填写审核意见！");
			return;
		}
	}
    isSubmit = true;
	var options = {
       url:'${request.contextPath}/office/executiveMeet/executiveMeet-issueAuditSave.action?submitState='+state, 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
    $('#auditform').ajaxSubmit(options);
}

function showReply(data){
	var error = data;
	if(error && error != ''){
		showMsgError(data);
		isSubmit = false;
	} else {
		showMsgSuccess('审核成功!','提示',auditBack);
	}
}
function auditBack(){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-issueAudit.action?queryIssueState=${queryIssueState!}&queryName=${queryName!}";
	load("#executiveMeetDiv", url);
}
</script>
<form id="auditform" name="auditform">
<input type="hidden" id="id" name="officeExecutiveIssue.id" value="${officeExecutiveIssue.id!}">
	<div class="query-builder">
	<div class="query-part">
    	<div class="t-center data-total fb21">议题审核详情</div>
        <div class="fn-clear"></div>
    </div>
    <div class="meet-wrap" style="border:0px;">
    	<p class="f14 b pb-10 mt-20 fn-left ml-15">议题详情</p>
		<@htmlmacro.tableDetail>
		<tr>
			<th width="15%">议题名称：</th>
			<td width="35%">
				<span class="fn-left mt-3 ml-10">${officeExecutiveIssue.name!}</span>
			</td>
			<th width="15%">提报领导：</th>
			<td width="35%">
				<span class="fn-left mt-3 ml-10">${officeExecutiveIssue.leaderNameStr!}</span>
			</td>
		</tr>	
		<tr>
			<th width="15%">会议名称：</th>
			<td width="35%">
			<#if view>
				<span class="fn-left mt-3 ml-10">${officeExecutiveIssue.meetingName!}</span>
			<#else>
			<@htmlmacro.select className="fn-left ml-10" style="width:170px;" valName="officeExecutiveIssue.meetingId" valId="officeExecutiveIssue.meetingId" >
				<a val=""><span>--请选择--</span></a>
            	<#if officeExecutiveMeetList?exists && officeExecutiveMeetList?size gt 0>
            		<#list officeExecutiveMeetList as item>
			        <a val="${item.id!}"><span>${item.name!}</span></a>
			       	</#list>
			   	</#if>
			</@htmlmacro.select>
			</#if>
			</td>
			<th width="15%">主办科室：</th>
			<td width="35%">
				<span class="fn-left mt-3 ml-10">${officeExecutiveIssue.hostDeptNameStr!}</span>
			</td>
		</tr>
		<tr>
		<#if view>
			<th width="15%">列席科室：</th>
			<td width="35%">
				<span class="fn-left mt-3 ml-10">${officeExecutiveIssue.attendDeptNameStr!}</span>
			</td>
			<th width="15%">意见征集科室：</th>
			<td width="35%">
				<span class="fn-left mt-3 ml-10">${officeExecutiveIssue.opinionDeptNameStr!}</span>
			</td>
		</tr>
		<#else>
			<th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;列席科室：</th>
			<td width="35%">
				<@commonmacro.selectMoreTree idObjectId="attendDeptId" nameObjectId="attendDeptNameStr" preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action" >
				<input type="hidden" name="officeExecutiveIssue.attendDeptId" id="attendDeptId" notNull="true" msgName="列席科室" value="${officeExecutiveIssue.attendDeptId!}" > 
				<textarea name="officeExecutiveIssue.attendDeptNameStr" id="attendDeptNameStr" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" disabled="disabled" rows="4" >${officeExecutiveIssue.attendDeptNameStr!}</textarea>
				</@commonmacro.selectMoreTree>	
			</td>
			<th width="15%"><span class="c-orange mt-5 ml-10">*</span>&nbsp;意见征集：</th>
			<td width="35%">
				<@commonmacro.selectMoreTree idObjectId="opinionDeptId" nameObjectId="opinionDeptNameStr" preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action" >
				<input type="hidden" name="officeExecutiveIssue.opinionDeptId" id="opinionDeptId" notNull="true" msgName="意见征集科室" value="${officeExecutiveIssue.opinionDeptId!}" > 
				<textarea name="officeExecutiveIssue.opinionDeptNameStr" id="opinionDeptNameStr" class="text-area my-5 " style="width:80%;padding:5px 1%;height:50px;" disabled="disabled" rows="4" >${officeExecutiveIssue.opinionDeptNameStr!}</textarea>
				</@commonmacro.selectMoreTree>	
			</td>
		</#if>
		<tr>
			<th width="15%">情况说明：</th>
			<td width="85%" colspan="3">
				<textarea name="officeExecutiveIssue.remark" id="remark" class="text-area my-5 input-readonly" style="width:90%;padding:5px 1%;height:50px;" readonly="readonly" rows="4" >${officeExecutiveIssue.remark!}</textarea>
			</td>
		</tr>
		<tr>
			<th width="15%">议题资料（附件）：</th>
			<td style="width:85%;" colspan="3">
		    <#if officeExecutiveIssue.attachments?exists>
			<div class="doc-wrap" style="width:880px;">
		        <ul class="doc-list fn-clear" id="upload-spanLi">
					<#list officeExecutiveIssue.attachments as att>
						<li class="view" id="attP${att_index}">
		                    <img src="${request.contextPath}/static/images/icon/file/<#if !att.extName??>other.png<#elseif att.extName=='pdf'>
							pdf.png<#elseif att.extName=='doc'||att.extName=='docx'>
							word.png<#elseif att.extName=='ppt'||att.extName=='pptx'>
							ppt.png<#elseif att.extName=='xls'||att.extName=='xlsx'>
							xls.png<#elseif att.extName=='csv'>
							csv.png<#elseif att.extName=='rtf'>
							rtf.png<#elseif att.extName=='wav'||att.extName=='mp3'>
							music.png<#elseif att.extName=='txt'>
							txt.png<#elseif att.extName=='mp4'||att.extName=='avi'||att.extName=='mov'>
							move.png<#elseif att.extName=='png'||att.extName=='jpg'||att.extName=='jpeg'||att.extName=='gif'||att.extName=='bmp'>
							jpg.png<#else>other.png</#if>">
		                    <span class="name" style="width:230px;" title="${att.fileName!}"><@htmlmacro.cutOff str='${att.fileName!}' length=16/></span>
		                    <span class="fr" style="width:100px;">
		                        <a href="javascript:void(0);"  onclick="doDownload('${att.downloadPath!}');">下载</a>
		                    </span>
		                </li>
					</#list>
		        </ul>
	        </div>
			</#if>
	    	</td>
		</tr>	
		</@htmlmacro.tableDetail>
		
		<p class="f14 b pb-10 mt-20 fn-left ml-15">科室意见</p>
		<@htmlmacro.tableList id="opinionlist" style="margin-top:5px;">
			<tr>
		    	<th width="20%">意见征集科室</th>
		    	<th width="40%">具体意见</th>
		    	<th width="40%">回复</th>
		    </tr>
			<#if officeExecutiveIssueAttendList?exists&&(officeExecutiveIssueAttendList?size>0)>
			<#assign index = 0>
			<#list officeExecutiveIssueAttendList as item >
			<#if item.type==4>
	        <tr>
		        <td>${item.objectName?default('')}</td>
		        <td><textarea name="officeExecutiveIssueAttendList[${index}].remark" id="remark${index}" class="text-area my-5 input-readonly" style="width:90%;padding:5px 1%;height:50px;" disabled="disabled" rows="3" >${item.remark!}</textarea></td>
		    	<td><textarea name="officeExecutiveIssueAttendList[${index}].replyInfo" id="replyInfo${index}" class="text-area my-5 input-readonly" style="width:90%;padding:5px 1%;height:50px;" disabled="disabled" rows="3" >${item.replyInfo!}</textarea></td>
			</tr>
			<#assign index=index+1>
			</#if>
			</#list>
			</#if>
		</@htmlmacro.tableList>	    
		
		<p class="f14 b pb-10 mt-20 fn-left ml-15">审核意见</p>
		<@htmlmacro.tableDetail>
		<tr>
		<textarea name="officeExecutiveIssue.auditRemark" id="auditRemark" maxLength="100" class="text-area my-5" style="width:90%;padding:5px 1%;height:50px;" rows="3" >${officeExecutiveIssue.auditRemark!}</textarea>
		</tr>
		</@htmlmacro.tableDetail> 
	</div>
<p class="t-center pt-30">
	<#if view>
		<a href="javascript:void(0);" class="abtn-blue-big"  onclick="auditBack()">返回</a>
	<#else>
		<a href="javascript:void(0);" class="abtn-blue-big"  onclick="issueAuditSave('3')">审核通过</a>
		<a href="javascript:void(0);" class="abtn-blue-big ml-10"  onclick="issueAuditSave('4')">审核不通过</a>
		<a href="javascript:void(0);" class="abtn-blue-big ml-10"  onclick="auditBack()">返回</a>
	</#if>
</p>
</div>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>