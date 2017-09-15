<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function issueApplySear(){
	var queryName = document.getElementById("queryName").value;
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-issueApplyList.action?queryName="+queryName;
	load("#issueApplyListDiv", url);
}

function doIssueEdit(issueId, view){
	var queryName = document.getElementById("queryName").value;
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-issueApplyEdit.action?officeExecutiveIssue.id="+issueId+"&view="+view+"&queryName="+queryName;
	load("#executiveMeetDiv", url);
}

function doSubmit(issueId){
	if(issueId && issueId != ''){
		if(showConfirm("确定要提报提议？")){
			$.getJSON("${request.contextPath}/office/executiveMeet/executiveMeet-issueSubmit.action", 
			{"officeExecutiveIssue.id":issueId}, function(data){
				if(data && data != ""){
					showMsgError(data);
				}else{
					showMsgSuccess('提报成功!','提示');
					issueApplySear();
				}
			}).error(function(){
				showMsgError("提报失败！");
			});
		}
	}
}

function doDelete(issueId){
	if(issueId && issueId != ''){
		if(showConfirm("确定要删除提议？")){
			$.getJSON("${request.contextPath}/office/executiveMeet/executiveMeet-issueDelete.action", 
			{"officeExecutiveIssue.id":issueId}, function(data){
				if(data && data != ""){
					showMsgError(data);
				}else{
					showMsgSuccess('删除成功!','提示');
					issueApplySear();
				}
			}).error(function(){
				showMsgError("删除失败！");
			});
		}
	}
}

function doOpinionEdit(issueId, type){
	var queryName = document.getElementById("queryName").value;
	if(type == "1"){
		openDiv("#opinionLayer1", "#opinionLayer1 .close,#opinionLayer1 .submit,#opinionLayer1 .reset", "${request.contextPath}/office/executiveMeet/executiveMeet-issueOpinionEdit.action?officeExecutiveIssue.id="+issueId+"&officeExecutiveIssue.reviseOpinionType="+type+"&queryName="+queryName, null, null, "500px");
	}
	else{
		openDiv("#opinionLayer1", "#opinionLayer1 .close,#opinionLayer1 .submit,#opinionLayer1 .reset", "${request.contextPath}/office/executiveMeet/executiveMeet-issueOpinionReEdit.action?officeExecutiveIssue.id="+issueId+"&officeExecutiveIssue.reviseOpinionType="+type+"&queryName="+queryName, null, null, "500px");	
	}
}

function doOpinionReply(issueId, view){
	var queryName = document.getElementById("queryName").value;
	openDiv("#opinionLayer2", "#opinionLayer2 .close,#opinionLayer2 .submit,#opinionLayer2 .reset", "${request.contextPath}/office/executiveMeet/executiveMeet-issueOpinionList.action?officeExecutiveIssue.id="+issueId+"&view="+view+"&queryName="+queryName, null, null, "500px");
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10">
			<span class="fn-left">议题名称：</span>
		</div>
	    <div class="fn-left">
			<input id="queryName" type="text" class="input-txt" style="width:200px;" value="${queryName!}">
		</div>
		<a href="javascript:void(0)" onclick="issueApplySear();" class="abtn-blue fn-left ml-20">查找</a>
		<a href="javascript:void(0);" class="abtn-orange-new fn-right" onclick="doIssueEdit('', 'false');">新增议题</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="issueApplyListDiv"></div>
<div class="popUp-layer" id="opinionLayer1" style="display:none;width:450px;"></div>
<div class="popUp-layer" id="opinionLayer2" style="display:none;width:600px;"></div>
<script>
$(document).ready(function(){
	issueApplySear();
});
</script>
</@common.moduleDiv>