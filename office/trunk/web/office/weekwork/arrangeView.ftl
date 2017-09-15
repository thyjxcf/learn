<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../weekwork/archiveWebuploader.ftl" as archiveWebuploader>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="周工作查看">
<div id="detailEditContainer">
<form name="form1" id="form1" method="POST" action="">
<input type="hidden" name="officeWorkArrangeDetail.id" value="${officeWorkArrangeDetail.id!}">
<input type="hidden" id="state" name="officeWorkArrangeDetail.state" value="${officeWorkArrangeDetail.state!}">
<input type="hidden" name="officeWorkArrangeDetail.outlineId" value="${officeWorkArrangeOutline.id!}">
<p class="table-dt fb18">周工作上报</p>
<@htmlmacro.tableDetail divClass="table-form" id="auditTable">
        <tr>
            <th width="15%">&nbsp;开始日期：</th>
            <td width="35%" <#if useNewFields>colspan="2"</#if>>
                ${((officeWorkArrangeOutline.startTime)?string('yyyy-MM-dd'))?if_exists}
            </td>
            <th width="15%">&nbsp;结束日期：</th>
            <td width="35%" <#if useNewFields>colspan="2"</#if>>
                ${((officeWorkArrangeOutline.endTime)?string('yyyy-MM-dd'))?if_exists}
            </td>
        </tr>
        <tr>
            <th>&nbsp;工作大纲名称：</th>
            <td <#if useNewFields>colspan="5"<#else>colspan="3"</#if>>
                ${officeWorkArrangeOutline.name?default('')}
            </td>
        </tr>
                     
        <tr>
            <th>工作重点：</th>
            <td <#if useNewFields>colspan="5"<#else>colspan="3"</#if> style="word-break:break-all; word-wrap:break-word;">
			    ${officeWorkArrangeOutline.workContent?default('')}
			</td>
        </tr>
        <tr>
            <th style="text-align:center;">日期</th>
            <#if useNewFields>
            <th style="text-align:center;">时间</th>
            </#if>
            <th style="text-align:center;">工作内容</th>
            <th style="text-align:center;">责任部门</th>
            <#if useNewFields>
            <th style="text-align:center;">参与人员</th>
            </#if>
            <th style="text-align:center;">地点</th>
        </tr>
        <#if officeWorkArrangeDetail.officeWorkArrangeContents?exists && officeWorkArrangeDetail.officeWorkArrangeContents?size gt 0>
        	<#list officeWorkArrangeDetail.officeWorkArrangeContents as owac>
        	<tr>
	            <td style="text-align:center;">
	            	${owac.workDate?string('yyyy-MM-dd')!}
	            </td>
	            <#if useNewFields>
	            <td style="text-align:center;word-break:break-all; word-wrap:break-word;">${owac.workTime!}</td>
	            </#if>
	            <td style="text-align:center;word-break:break-all; word-wrap:break-word;">
	            	${owac.content!}
	            </td>
	            <td style="text-align:center;word-break:break-all; word-wrap:break-word;">
			  	   	${owac.deptNames!}
	        	</td>
	        	<#if useNewFields>
	        	<td style="text-align:center;word-break:break-all; word-wrap:break-word;">${owac.attendees!}</td>
	        	</#if>
	            <td style="text-align:center;">
	            	${owac.place!}
            	</td>
	        </tr>
	        </#list>
        </#if>
        <tr id="needAddTr">
            <th>备注：</th>
            <td  class="remarkTD" <#if useNewFields>colspan="5"<#else>colspan="3"</#if> style="word-break:break-all; word-wrap:break-word;">${officeWorkArrangeDetail.remark?default('')}
			</td>
        </tr>
    		<@archiveWebuploader.archiveWebuploaderEditViewer canEdit=true isTrue=true showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />
    	<p class="pt-15 t-center">
        <tr>
        	<td <#if useNewFields>colspan="6"<#else>colspan="4"</#if> class="td-opt">
			   <a class="abtn-blue reset ml-5" href="javascript:void(0);" onclick="back();">返回</a>
            </td>
        </tr>
        </p>
</@htmlmacro.tableDetail>
</form>
</div>

<script type="text/javascript">
function back(){
  var searchYear = '${searchYear!}';
  load("#weekworkContainer","${request.contextPath}/office/weekwork/weekwork-arrangeList.action?searchYear="+searchYear);
}
vselect();
$(document).ready(function(){
	var htmlvar=$(".remarkTD").html();
	$(".remarkTD").html(htmlvar.replace(new RegExp("\n","gm"),"</br>"));
});

</script>
</@htmlmacro.moduleDiv>