<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function issueAuditSear(){
	var queryName = document.getElementById("queryName").value;
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-issueAuditList.action?queryName="+queryName+"&queryIssueState=${queryIssueState!}";
	load("#issueAuditListDiv", url);
}

function queryIssueAudit(queryIssueState){
	var queryName = document.getElementById("queryName").value;
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-issueAudit.action?queryName="+queryName+"&queryIssueState="+queryIssueState;
	load("#executiveMeetDiv", url);
}

function doIssueAudit(issueId, view){
	var queryName = document.getElementById("queryName").value;
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-issueAuditEdit.action?officeExecutiveIssue.id="+issueId+"&view="+view+"&queryIssueState=${queryIssueState!}&queryName="+queryName;
	load("#executiveMeetDiv", url);
}

</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt" style="margin-top:-3px;">
			<input type="hidden" name="queryIssueState" id="queryIssueState" value="${queryIssueState!}">
	    	<span class="user-sList user-sList-radio fn-left">
	    		<span <#if queryIssueState?default("2")=="2">class="current"</#if> data-select="2" onclick="queryIssueAudit('2')">待审核议题</span>
	    		<span <#if queryIssueState?default("")=="0">class="current"</#if> data-select="0" onclick="queryIssueAudit('0')">已审核议题</span>
	    	</span>
		</div>
		<div class="query-tt ml-10">
			<span class="fn-left">议题名称：</span>
		</div>
	    <div class="fn-left">
			<input id="queryName" type="text" class="input-txt" style="width:200px;" value="${queryName!}">
		</div>
		<a href="javascript:void(0)" onclick="issueAuditSear();" class="abtn-blue fn-left ml-20">查找</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="issueAuditListDiv"></div>
<script>
	$(document).ready(function(){
		issueAuditSear();
	});
</script>
</@common.moduleDiv>