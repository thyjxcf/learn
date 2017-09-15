<#import "/common/htmlcomponent.ftl" as htmlmacro>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<a href="javascript:void(0);" class="abtn-blue ml-20 fn-right" onclick="sear();">返回上一层</a>
		<a href="javascript:void(0);" class="abtn-blue ml-20 fn-right" onclick="meetIssueAdd('${meetId!}');">+追加议题</a>
		<a href="javascript:void(0)" onclick="export1('${meetId!}');" class="abtn-blue fn-right ml-20">导出</a>
		<a href="javascript:void(0)" onclick="printDiv();" class="abtn-blue fn-right ml-20">打印</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="manageIssuesDiv">
<form id="manageIssuesform" action="" method="post">
<@htmlmacro.tableList id="tablelist">
    <tr>
		<th width="15%">会议名称</th>
    	<th width="5%">序号</th>
    	<th width="15%">议题名称</th>
    	<th width="20%">附件</th>
    	<th width="15%">提报领导</th>
    	<th width="10%">主办科室</th>
    	<th width="10%">列席科室</th>
	    <th class="t-center" width="10%">操作</th>
    </tr>
   	<#if officeExecutiveIssueList?exists && officeExecutiveIssueList?size gt 0>
   		<#assign index = 0>
    	<tr>
    	<td rowspan="${officeExecutiveIssueList?size}">
	    	<a href="javascript:void(0);" onclick="viewMeet('${officeExecutiveMeet.id!}');"><@htmlmacro.cutOff str="${officeExecutiveMeet.name!}" length=10/></a>
	    	<br/>
	    	(${(officeExecutiveMeet.meetDate?string('yyyy-MM-dd HH:mm'))?if_exists})
    	</td>
       <#list officeExecutiveIssueList as item>
        <td>
        	<input type="hidden" id="id${index}" name="officeExecutiveIssueList[${index}].id" value="${item.id!}"/>
        	<input class="input-txt fn-left" id="serialNumber${index}" name="officeExecutiveIssueList[${index}].serialNumber" maxLength="3" notNull="true" msgName="序号"  onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d]/g,'')" value="${item.serialNumber!}"/>
        </td>
        <td title="${item.name!}"><@htmlmacro.cutOff str="${item.name!}" length=15/></td>
        <td>
			<#if item.attachments?exists && item.attachments?size gt 0>
				<#list item.attachments as att>
					<a href="javascript:void(0);" title="${att.fileName!}" onclick="doDownload('${att.downloadPath!}');"><@htmlmacro.cutOff str='${att.fileName!}' length=16/></a>
					<a href="javascript:viewAttachment('${att.id!}');">(预览)</a>
					<br/>
				</#list>
			</#if>
		</td>
        <td title="${item.leaderNameStr!}"><@htmlmacro.cutOff str="${item.leaderNameStr!}" length=15/></td>
        <td title="${item.hostDeptNameStr!}"><@htmlmacro.cutOff str="${item.hostDeptNameStr!}" length=17/></td>
    	<td title="${item.attendDeptNameStr!}"><@htmlmacro.cutOff str="${item.attendDeptNameStr!}" length=17/></td>
    	<td class="t-center">
			<a href="javascript:void(0);" onclick="removeIssue('${item.id}','${item.meetingId!}');" class="ml-10">移除</a>
    	</td>
		<#if item_index != officeExecutiveIssueList?size-1>
		</tr><tr>
		</#if>
		<#assign index=index+1>
       </#list>
       </tr>
    <#else>
    <tr>
    	<td colspan="8">
    	<p class="no-data mt-20 mb-20">还没有任何记录哦！</p></td>
    </tr>
	</#if>
</@htmlmacro.tableList>
<#if officeExecutiveIssueList?exists && officeExecutiveIssueList?size gt 0>
	<@htmlmacro.Toolbar container="#meetManageListDiv">
	<a href="javascript:void(0);" id="sortIssue" class="abtn-blue"  onclick="sortIssue()">保存议题序号</a>
	</@htmlmacro.Toolbar>
</#if>
</form>
</div>
<div id="printDiv" style="display: none">
	<@htmlmacro.tableList id="printList">
		<tr>
			<td colspan="6" class="t-center b" style="border:0px;font-size:21px;">
				${unitName!}局党组、局长办公会议议题总表
			</td>
		</tr>
		<tr>
			<td colspan="6" class="t-center f16 b" style="border:0px;">
				${officeExecutiveMeet.name!}
			</td>
		</tr>
		<tr>
			<td colspan="3" style="border:0px;font-size:10px;">
				制表：局办公室
			</td>
			<td colspan="3" class="t-right" style="border:0px;font-size:10px;">
				会议时间：${(officeExecutiveMeet.meetDate?string('yyyy年MM月dd日 HH:mm'))?if_exists}
			</td>
		</tr>
		<tr>
    	<td width="5%"><nobr>序号</nobr></td>
    	<td width="15%">议题名称</td>
    	<td width="20%">提报领导</td>
    	<td width="20%">主办科室</td>
    	<td width="20%">列席科室</td>
    	<td width="20%">备注</td>
    	</tr>
    <#if officeExecutiveIssueList?exists && officeExecutiveIssueList?size gt 0>
       <#list officeExecutiveIssueList as item>
    	<tr>
        <td>${item_index + 1}</td>
        <td>${item.name!}</td>
        <td>${item.leaderNameStr!}</td>
        <td>${item.hostDeptNameStr!}</td>
    	<td>${item.attendDeptNameStr!}</td>
    	<td>&nbsp;</td>
       </tr>
       </#list>
    <#else>
    <tr>
    	<td colspan="6">
    	<p class="no-data mt-20 mb-20">还没有任何记录哦！</p>
    	</td>
    </tr>
	</#if>
	</@htmlmacro.tableList>
</div>
<script src="${request.contextPath}/static/js/myscript.js"/>
<script>
	function removeIssue(issueId,meetId){
		$.getJSON("${request.contextPath}/office/executiveMeet/executiveMeet-removeIssue.action", {
	          "officeExecutiveIssue.id":issueId,"meetId":meetId
	        }, function(data) {
				if(!data.operateSuccess){
					showMsgError(data.errorMessage);
					return;
				}else{
					showMsgSuccess(data.promptMessage, "提示", function(){
						sear();
					});
				}
		});
	}
	function export1(meetId){
		location.href="${request.contextPath}/office/executiveMeet/executiveMeet-export.action?meetId="+meetId;
	}
	function printDiv(){
		LODOP=getLodop();
		LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:10mm",getPrintContent(jQuery('#printDiv')));
	  	LODOP.PREVIEW();
	}
	function sortIssue(){
		if(!isActionable("#sortIssue")){
			return;
		}
		if(!checkAllValidate("#manageIssuesDiv")){
			return;
		}
		$("#sortIssue").attr("class", "abtn-unable");
		var options = {
	       url:'${request.contextPath}/office/executiveMeet/executiveMeet-sortIssue.action', 
	       success : showReply,
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post'
	    };
		
	    $('#manageIssuesform').ajaxSubmit(options);
	}
	function showReply(data){
		$("#sortIssue").attr("class", "abtn-blue");
		var error = data;
		if(error && error != ''){
			showMsgError(data);
			isSubmit = false;
		} else {
			showMsgSuccess('保存成功!','提示',function(){
				sear();
			});
		}
	}
</script>