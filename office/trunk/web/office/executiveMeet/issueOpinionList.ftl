<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
function issueOpinionListSave(){
	if(!isActionable("#btnSave")){
		return;
	}
	if(!checkAllValidate("#opinionListDiv")){
		return;
	}
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/executiveMeet/executiveMeet-issueOpinionListSave.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#opinionListform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSave").attr("class", "abtn-blue");
	var error = data;
	if(error && error != ''){
		showMsgError(data);
		isSubmit = false;
	} else {
		showMsgSuccess('保存成功!','提示',back);
	}
}

function back(){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-issueApplyList.action?queryName=${queryName!}";
	load("#issueApplyListDiv", url);
}

function issueOpinionTogetherSave(){
	$("#opinionDetail1").attr("style","display:none");
	$("#opinionDetail2").attr("style","display:display");
}

function backIssueOpinionList(){
	$("#opinionDetail1").attr("style","display:display");
	$("#opinionDetail2").attr("style","display:none");
	$("#allReplyInfo").val("");
}
</script>
<div id="opinionListDiv">
<form id="opinionListform" action="" method="post">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>意见详情</span></p>
<div style="overflow-y:auto;max-height:500px;">
<div id="opinionDetail1" style="display:display">
<#if !view&&officeExecutiveIssueAttendList?exists&&(officeExecutiveIssueAttendList?size>0)>
<#assign index = 0>
<#list officeExecutiveIssueAttendList as item >
<#if item.type==4 && item.remark! != "">
<#assign index=index+1>
</#if>
</#list>
<#if index gt 1>
<p class="fn-right mr-10">
	<a href="javascript:issueOpinionTogetherSave();" id="btnTogetherSave" class="abtn-blue mb-5">统一回复</a>
</p>
</#if>
</#if>
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
        <td>
        <input type="hidden" id="id${index}" name="officeExecutiveIssueAttendList[${index}].id" value="${item.id!}"/>
        <input type="hidden" id="issueId${index}" name="officeExecutiveIssueAttendList[${index}].issueId" value="${item.issueId!}"/>
        <input type="hidden" id="type${index}" name="officeExecutiveIssueAttendList[${index}].type" value="${item.type!}"/>
        <input type="hidden" id="objectId${index}" name="officeExecutiveIssueAttendList[${index}].objectId" value="${item.objectId!}"/>
        <input type="hidden" id="unitId${index}" name="officeExecutiveIssueAttendList[${index}].unitId" value="${item.unitId!}"/>
        ${item.objectName?default('')}
        </td>
        <td>
        	<input type="hidden" name="officeExecutiveIssueAttendList[${index}].remark" id="remark${index}_input" value="${item.remark!}"/>
        	<textarea name="officeExecutiveIssueAttendList[${index}].remark" id="remark${index}" class="text-area my-5 input-readonly" style="width:75%;padding:5px 1%;height:50px;" disabled="disabled" rows="3" >${item.remark!}</textarea>
        </td>
    	<#if view>
    	<td><textarea name="officeExecutiveIssueAttendList[${index}].replyInfo" id="replyInfo${index}" class="text-area my-5 input-readonly" style="width:75%;padding:5px 1%;height:50px;" disabled="disabled" rows="3" >${item.replyInfo!}</textarea></td>
    	<#else>
    	<td><textarea name="officeExecutiveIssueAttendList[${index}].replyInfo" id="replyInfo${index}" <#if item.remark! == "">class="text-area my-5 input-readonly" readonly="true"<#else>class="text-area my-5" maxlength="100"</#if> style="width:75%;padding:5px 1%;height:50px;" rows="3" >${item.replyInfo!}</textarea></td>
    	</#if>
		</tr>
		<#assign index=index+1>
		</#if>
		</#list>
	</#if>
</@htmlmacro.tableList>
</div>
<div id="opinionDetail2" style="display:none">
<p class="fn-right mr-10 mt-10">
	<a href="javascript:backIssueOpinionList();" id="btnTogetherSave" class="abtn-blue">返回逐条回复</a>
</p>
<div class="fn-clear"></div>
<div class="fn-left">
	<textarea name="allReplyInfo" id="allReplyInfo" class="text-area my-5 ml-30" maxLength="100" style="width:500px;padding:5px 1%;height:50px;" rows="3" ></textarea>
</div>
</div>
</div>
<p class="dd">
	<#if view>
		<a href="javascript:void(0);" class="abtn-blue reset">取消</a>
	<#else>
	    <a href="javascript:issueOpinionListSave();" id="btnSave" class="abtn-blue">保存</a>
	    <a href="javascript:void(0);" class="abtn-blue reset ml-5">取消</a>
    </#if>
</p>
</form>
</div>
<script type="text/javascript">
vselect();
</script>
<script src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>