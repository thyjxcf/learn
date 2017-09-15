<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
function issueOpinionEditSave(){
	if(!isActionable("#btnSave")){
		return;
	}
	if(!checkAllValidate("#opinionEditDiv")){
		return;
	}
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/executiveMeet/executiveMeet-issueOpinionEditSave.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#opinionEditform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSave").attr("class", "abtn-blue");
	var error = data;
	if(error && error != ''){
		showMsgError(data);
		isSubmit = false;
	} else {
		showMsgSuccess('操作成功!','提示',back);
	}
}

function back(){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-issueApplyList.action?queryName=${queryName!}";
	load("#issueApplyListDiv", url);
}
</script>
<div id="opinionEditDiv">
<form id="opinionEditform" action="" method="post">
<input type="hidden" id="id" name="officeExecutiveIssueAttend.id" value="${officeExecutiveIssueAttend.id!}"/>
<input type="hidden" id="issueId" name="officeExecutiveIssueAttend.issueId" value="${officeExecutiveIssueAttend.issueId!}"/>
<input type="hidden" id="type" name="officeExecutiveIssueAttend.type" value="${officeExecutiveIssueAttend.type!}"/>
<input type="hidden" id="objectId" name="officeExecutiveIssueAttend.objectId" value="${officeExecutiveIssueAttend.objectId!}"/>
<input type="hidden" id="unitId" name="officeExecutiveIssueAttend.unitId" value="${officeExecutiveIssueAttend.unitId!}"/>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>修改意见</span></p>

<div class="query-builder">
	<div class="query-part">
    	<div class="t-center data-total fb14">${officeExecutiveIssue.name!}议题</div>
        <div class="fn-clear"></div>
    </div>
    <div class="pub-table-inner">
    	<div class="query-tt">
			<span class="fn-left" style="margin-top:7px;">主办科室回复：</span>
		</div>
	    <div class="fn-left">
	    	<input type="hidden" name="officeExecutiveIssueAttend.replyInfo" id="replyInfo_input" value="${officeExecutiveIssueAttend.replyInfo!}"/>
			<textarea name="officeExecutiveIssueAttend.replyInfo" id="replyInfo" class="text-area my-5" maxLength="100" style="width:300px;height:50px;border:0px;background:#f6fbff;" disabled="disabled" rows="2" >${officeExecutiveIssueAttend.replyInfo!}</textarea>
		</div>
		<div class="fn-clear"></div>
		<p class="f12 pb-10 fn-left">修改我的意见：</p>
		<div class="fn-clear"></div>
		<div class="fn-left">
			<textarea name="officeExecutiveIssueAttend.remark" id="remark" class="text-area my-5" maxLength="100" style="width:380px;padding:5px 1%;height:50px;" rows="3" >${officeExecutiveIssueAttend.remark!}</textarea>
		</div>
		<div class="fn-clear"></div>
	</div>
</div>
<p class="dd">
    <a href="javascript:issueOpinionEditSave();" id="btnSave" class="abtn-blue">保存</a>
    <a href="javascript:void(0);" class="abtn-blue reset ml-5">取消</a>
</p>
</form>
</div>
<script type="text/javascript">
vselect();
</script>
<script src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>