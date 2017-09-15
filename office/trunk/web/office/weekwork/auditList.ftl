<#import "/common/htmlcomponent.ftl" as htmlmacro />
<#import "../weekwork/archiveWebuploader.ftl" as archiveWebuploader>
<@htmlmacro.moduleDiv titleName="">
<script>
var isSubmit = false;
function publishInfo(state) {
    if(isSubmit) {
       return;
    }
	$.getJSON("${request.contextPath}/office/weekwork/weekwork-publishInfo.action", 
	{"state":state,"workOutlineId":'${workOutlineId!}'}, 
	function(data){
		if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   isSubmit = false;
			   return;
		   }
		}else{
			showMsgSuccess(data.promptMessage,"提示",function(){
			  load("#auditContainer","${request.contextPath}/office/weekwork/weekwork-auditList.action?workOutlineId=${workOutlineId!}");
			});
			return;
		}
	});
}

function modifyDetail(detailId){
	openDiv('#detailLayer',null,"${request.contextPath}/office/weekwork/weekwork-detailInfo.action?detailId="+detailId,true,'#detailLayer .wrap','410');
}

function modifyContent(contentId){
	openDiv('#contentLayer',null,"${request.contextPath}/office/weekwork/weekwork-contentInfo.action?contentId="+contentId,true,'#contentLayer .wrap','410');
}
function deleteContent(contentId){
	if(confirm("你确定要删除吗？")) {
		$.getJSON("${request.contextPath}/office/weekwork/weekwork-deleteInfo.action", {"contentId":contentId}, function(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			   }
			}else{
			    var msg="删除成功!";
				showMsgSuccess(msg,"",function(){
				  getAuditList();
				});
				return;
			}
		});
	}
}

$(document).ready(function(){
	$(".remarkTD").each(function(){
		var remarkTD = $(this).html();
		$(this).html(remarkTD.replace(new RegExp("\n","gm"),"</br>&nbsp;"));
  	});
});
</script>
<div class="popUp-layer" id="detailLayer" style="display:none;width:500px;z-index:9999;"></div>
<div class="popUp-layer" id="contentLayer" style="display:none;width:700px;z-index:9999;"></div>
<table class="public-table table-list table-list-edit mt-15">
	<#if (officeWorkArrangeContentsMap?exists && officeWorkArrangeContentsMap?size gt 0) 
    	|| !(officeWorkArrangeDetailList?exists && officeWorkArrangeDetailList?size gt 0)>
    <tr>
    	<#if useNewFields>
    	<th width="8%">日期</th>
    	<th width="8%">时间</th>
        <#if canView>
        	<th width="20%">工作内容</th>
        	<th width="19%">具体要求、安排</th>
        <#else>
       	 	<th width="39%">工作内容</th>
        </#if>
        <th width="10%">责任部门</th>
        <th width="10%">参与人员</th>
        <th width="10%">地点</th>
    	<#else>
        <th width="15%">日期</th>
        <#if canView>
        	<th width="20%">工作内容</th>
        	<th width="20%">具体要求、安排</th>
        <#else>
        <th width="40%">工作内容</th>
        </#if>
        <th width="15%">责任部门</th>
        <th width="15%">地点</th>
        </#if>
        <#if officeWorkArrangeOutline.state == '2'>
        <th class="t-center" width="15%">操作</th>
        </#if>
    </tr>
    </#if>
    <#if (officeWorkArrangeContentsMap?exists && officeWorkArrangeContentsMap?size gt 0) 
    	|| (officeWorkArrangeDetailList?exists && officeWorkArrangeDetailList?size gt 0)>
    	<#if officeWorkArrangeContentsMap?exists && officeWorkArrangeContentsMap?size gt 0>
	        <#list officeWorkArrangeContentsMap?keys as key>
		        <#assign owacList = officeWorkArrangeContentsMap.get(key)/>
		        <#assign change=changeDate.get(key)/>
		        <tr>
		            <td rowspan="${owacList?size}"><#if change==1><span class="c-orange mr-5">${key?string('MM月dd日')!}(${dateMap.get(key)})</span><#else>${key?string('MM月dd日')!}(${dateMap.get(key)})</#if></td>
		            <#list owacList as owac>
		            <#if useNewFields>
		            <td style="word-break:break-all; word-wrap:break-word;"><#if owac.workStartTime?default("") !="">${owac.workStartTime!}-${owac.workEndTime!}</#if></td>
		            </#if>
		            <td style="word-break:break-all; word-wrap:break-word;">${owac.content!}</td>
		            <#if canView>
		            <td style="word-break:break-all; word-wrap:break-word;">${owac.arrangContent!}</td>
		            </#if>
		            <td style="word-break:break-all; word-wrap:break-word;">${owac.deptNames!}</td>
		            <#if useNewFields>
		        	<td style="word-break:break-all; word-wrap:break-word;">${owac.attendees!}</td>
		        	</#if>
		            <td style="word-break:break-all; word-wrap:break-word;">${owac.place!}</td>
		            <#if officeWorkArrangeOutline.state == '2'>
		            <td class="t-center">
		            	<a href="javascript:void(0);" onclick="modifyContent('${owac.id!}');">修改</a>
		            	<a href="javascript:void(0);" onclick="deleteContent('${owac.id!}');">删除</a>
		            </td>
		            </#if>
		            <#if owac_index+1 lt owacList?size>
		            	</tr><tr>
		            </#if>
		            </#list>
		        </tr>
	        </#list>
        </#if>
        <#list officeWorkArrangeDetailList as owad>
        	<#if owad.remark?exists>
        	<tr>
        		<#if owad_index == 0>
        			<td class="t-center" rowspan="${officeWorkArrangeDetailList?size}">备注</td>
        		</#if>
        		<td class="remarkTD mt-5" <#if useNewFields><#if canView>colspan="6"<#else>colspan="5"</#if><#else><#if canView>colspan="4"<#else>colspan="3"</#if></#if> style="width:70%;word-break:break-all; word-wrap:break-word;">${owad.deptName!}:</br>&nbsp;${owad.remark!}</td>
        		<#if officeWorkArrangeOutline.state == '2'>
        		<td class="t-center">
        			<a href="javascript:void(0);" onclick="modifyDetail('${owad.id!}');">修改</a>
    			</td>
    			</#if>
        	</tr>
        	</#if>
        </#list>
	    <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=true showAttachmentDivId='showAttDiv' isAudit=true editContentDivId='editContentDiv' isSend=true loadDiv=false />
    <#else>
        <tr><td <#if useNewFields><#if canView>colspan="8"<#else>colspan="7"</#if><#else><#if canView>colspan="6"<#else>colspan="5"</#if></#if>> <p class="no-data mt-50 mb-50">还没有记录哦！</p></td></tr>
    </#if>
</table>


<#if (officeWorkArrangeDetailList?exists && officeWorkArrangeDetailList?size gt 0)
	|| (officeWorkArrangeContentsMap?exists && officeWorkArrangeContentsMap?size gt 0)>
	<#if officeWorkArrangeOutline.state == '2'>
	<p class="t-center pt-30"><a href="javaScript:publishInfo(3);" class="abtn-blue-big">发布</a></p>
	<#elseif officeWorkArrangeOutline.state == '3'>
	<p class="t-center pt-30"><a href="javaScript:publishInfo(2);" class="abtn-blue-big">取消发布</a></p>
	</#if>
</#if>

<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>