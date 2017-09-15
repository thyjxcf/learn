<#import "/common/htmlcomponent.ftl" as htmlmacro>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<a href="javascript:void(0);" class="abtn-blue ml-20 fn-right" onclick="manageIssues('${meetId!}');">返回上一层</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<form name="meetIssueAdd" id="meetIssueAdd" action="" method="post">
<input type="hidden" id="meetId" name="meetId" value="${meetId!}"/>
<@htmlmacro.tableList id="tablelist">
    <tr>
    	<th class="t-center" width="10%">选择</th>
    	<th width="20%">议题名称</th>
    	<th width="15%">附件</th>
    	<th width="15%">提报领导</th>
    	<th width="20%">主办科室</th>
    	<th width="20%">列席科室</th>
    </tr>
   	<#if officeExecutiveIssueList?exists && officeExecutiveIssueList?size gt 0>
    	<#list officeExecutiveIssueList as item>
    	<tr>
    	<td class="t-center"><p><span class="ui-checkbox">
    		<input type="checkbox" class="chk" name="issueIds" value="${item.id?default('')}">
    		</span></p>
    	</td>
    	<td title="${item.name!}"><@htmlmacro.cutOff str="${item.name!}" length=20/></td>
        <td>
        <#if item.attachments?exists && item.attachments?size gt 0>
    	<#list item.attachments as info>
    		<a href="javascript:doDownload('${info.downloadPath!}');"><@htmlmacro.cutOff4List str="${info.fileName!}" length=10/></a>
    		</br>
    	</#list>
        </#if>
        </td>
        <td title="${item.leaderNameStr!}"><@htmlmacro.cutOff str="${item.leaderNameStr!}" length=15/></td>
        <td title="${item.hostDeptNameStr!}"><@htmlmacro.cutOff str="${item.hostDeptNameStr!}" length=20/></td>
    	<td title="${item.attendDeptNameStr!}"><@htmlmacro.cutOff str="${item.attendDeptNameStr!}" length=20/></td>
		<#if item_index != officeExecutiveIssueList?size-1>
		</tr><tr>
		</#if>
       </tr>
       </#list>
    <#else>
    <tr>
    	<td colspan="8">
    	<p class="no-data mt-20 mb-20">还没有任何记录哦！</p></td>
    </tr>
	</#if>
</@htmlmacro.tableList>
<#if officeExecutiveIssueList?exists&&(officeExecutiveIssueList?size>0)>
<@htmlmacro.Toolbar container="#meetManageListDiv">
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</input></span>
	<a href="javascript:void(0);" id="btnAddIssue" class="abtn-blue"  onclick="btnAddIssue()"> 追加至会议</a>
</@htmlmacro.Toolbar>
</#if>
</form>
<script src="${request.contextPath}/static/js/myscript.js"/>
<script>
function btnAddIssue(){
	if(isCheckBoxSelect($("[name='issueIds']")) == false){
		showMsgWarn("没有选要新增的议题，请先选择!");
		return;
	}
	
	if(!showConfirm('您确认要将选中的议题增添至会议？')){
		return;
	}
	$("#btnAddIssue").attr("class", "abtn-unable");	
	var options = {
       url:'${request.contextPath}/office/executiveMeet/executiveMeet-btnAddIssue.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#meetIssueAdd').ajaxSubmit(options);
}

function showReply(data){
	$("#btnAddIssue").attr("class", "abtn-blue");
	var error = data;
	if(error && error != ''){
		showMsgError(data);
		isSubmit = false;
	} else {
		showMsgSuccess('添加成功!','提示',meetIssueAdd('${meetId!}'));
	}
}
</script>