<#import "/common/htmlcomponent.ftl" as htmlmacro />
<#import "../weekwork/archiveWebuploader.ftl" as archiveWebuploader>
<@htmlmacro.moduleDiv titleName="周工作汇总列表">
<script>
function doPrint(){
	LODOP=getLodop();
	LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:15mm",getPrintContent(jQuery('#printDiv')));
  	LODOP.PREVIEW();
} 

$(document).ready(function(){
	$(".remarkTD").each(function(){
		var htmlvar=$(this).html();
		$(this).html(htmlvar.replace(new RegExp("\n","gm"),"</br>&nbsp;&nbsp;&nbsp;"));
	});
});
</script>
<table class="public-table table-list table-list-edit mt-15">
    <#if (officeWorkArrangeContentsMap?exists && officeWorkArrangeContentsMap?size gt 0) 
    	|| !(officeWorkArrangeDetailList?exists && officeWorkArrangeDetailList?size gt 0)>
    <tr>
        <#if useNewFields>
    	<th width="10%">日期</th>
    	<th width="10%">时间</th>
        <#if canView>
        <th width="25%">工作内容</th>
        <th width="20%">具体要求、安排</th>
        <#else>
        	<th width="45%">工作内容</th>
        </#if>
        <th width="10%">责任部门</th>
        <th width="10%">参与人员</th>
        <th width="15%">地点</th>
    	<#else>
        <th width="10%">日期</th>
        <#if canView>
        <th width="30%">工作内容</th>
        <th width="30%">具体要求、安排</th>
        <#else>
        <th width="60%">工作内容</th>
        </#if>
        <th width="15%">责任部门</th>
        <th width="15%">地点</th>
        </#if>
    </tr>
    </#if>
    <#if (officeWorkArrangeContentsMap?exists && officeWorkArrangeContentsMap?size gt 0) 
    	|| (officeWorkArrangeDetailList?exists && officeWorkArrangeDetailList?size gt 0)>
    	<#if officeWorkArrangeContentsMap?exists && officeWorkArrangeContentsMap?size gt 0>
        <#list officeWorkArrangeContentsMap?keys as key>
	        <#assign owacList = officeWorkArrangeContentsMap.get(key)/>
	        <tr>
	            <td rowspan="${owacList?size}">${key?string('MM月dd日')!}(${dateMap.get(key)})</td>
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
	            <#if owac_index+1 lt owacList?size>
	            	</tr><tr>
	            </#if>
	            </#list>
	        </tr>
        </#list>
        </#if>
        <#if officeWorkArrangeDetailList?exists && officeWorkArrangeDetailList?size gt 0 >
        	<tr>
        		<td <#if useNewFields><#if canView>colspan="7"<#else>colspan="6"</#if><#else><#if canView>colspan="5"<#else>colspan="4"</#if></#if> style="word-break:break-all; word-wrap:break-word;">
        			<p class="mt-5">备注：</p>
        			<#list officeWorkArrangeDetailList as owad>
        				<#if owad.remark?exists>
        				<p class="remarkTD mt-5">&nbsp;${owad.deptName!}:${owad.remark!};</p>
        				</#if>
        			</#list>
    			</td>
        	</tr>
        </#if>
		<@archiveWebuploader.archiveWebuploaderEditViewer canEdit=true isTrue=true showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />
    <#else>
        <tr><td <#if useNewFields><#if canView>colspan="8"<#else>colspan="7"</#if><#else><#if canView>colspan="6"<#else>colspan="5"</#if></#if>> <p class="no-data mt-50 mb-50">还没有记录哦！</p></td></tr>
    </#if>
</table>
<#if officeWorkArrangeDetailList?exists && officeWorkArrangeDetailList?size gt 0>
	<p class="t-center pt-30"><a href="javaScript:void(0);" onclick="doPrint();" class="abtn-blue-big">打印</a></p>
</#if>
<div id="printDiv" style="display:none;">
	<p style="font-size:20px;font-weight:700;color:#000000;text-align:center;">${officeWorkArrangeOutline.name!}</p>
	<p style="font-size:12px;font-weight:700;text-align:right;">${officeWorkArrangeOutline.startTime?string('yyyy.MM.dd')}&nbsp;&nbsp;&nbsp;&nbsp;</p>
	<table class="public-table table-list mt-15">
		<tr>
			<th width="15%">工作重点</th>
			<#if useNewFields>
			<#if canView>
			<td colspan="6">
			<#else>
			<td colspan="5">
			</#if>
			<#else>
			<#if canView>
			<td colspan="4">
			<#else>
			<td colspan="3">
			</#if>
			</#if>
			${officeWorkArrangeOutline.workContent!}
			</td>
		</tr>
		<#if (officeWorkArrangeContentsMap?exists && officeWorkArrangeContentsMap?size gt 0) 
    	|| !(officeWorkArrangeDetailList?exists && officeWorkArrangeDetailList?size gt 0)>
	    <tr>
	        <#if useNewFields>
	    	<th width="10%">日期</th>
    		<th width="10%">时间</th>
        	<#if canView>
        	<th width="25%">工作内容</th>
        	<th width="20%">具体要求、安排</th>
        	<#else>
        	<th width="45%">工作内容</th>
        	</#if>
        	<th width="10%">责任部门</th>
        	<th width="10%">参与人员</th>
        	<th width="15%">地点</th>
	    	<#else>
	        <th width="10%">日期</th>
        	<#if canView>
        	<th width="30%">工作内容</th>
        	<th width="30%">具体要求、安排</th>
       	 	<#else>
        	<th width="60%">工作内容</th>
       		</#if>
        	<th width="15%">责任部门</th>
        	<th width="15%">地点</th>
	        </#if>
	    </tr>
	    </#if>
	    <#if (officeWorkArrangeContentsMap?exists && officeWorkArrangeContentsMap?size gt 0) 
    	|| (officeWorkArrangeDetailList?exists && officeWorkArrangeDetailList?size gt 0)>
	    <#if officeWorkArrangeContentsMap?exists && officeWorkArrangeContentsMap?size gt 0>
	        <#list officeWorkArrangeContentsMap?keys as key>
		        <#assign owacList = officeWorkArrangeContentsMap.get(key)/>
		        <tr>
		            <td rowspan="${owacList?size}">${key?string('MM月dd日')!}(${dateMap.get(key)})</td>
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
		            <#if owac_index+1 lt owacList?size>
		            	</tr><tr>
		            </#if>
		            </#list>
		        </tr>
	        </#list>
	        </#if>
        	<#if officeWorkArrangeDetailList?exists && officeWorkArrangeDetailList?size gt 0>
	        	<tr>
	        		<td <#if useNewFields><#if canView>colspan="7"<#else>colspan="6"</#if><#else><#if canView>colspan="5"<#else>colspan="4"</#if></#if> style="word-break:break-all; word-wrap:break-word;">
	        			<p class="mt-5">备注：</p>
	        			<#list officeWorkArrangeDetailList as owad>
	        				<#if owad.remark?exists>
	        				<p class="mt-5">&nbsp;&nbsp;&nbsp;&nbsp;${owad.deptName!}:${owad.remark!};</p>
	        				</#if>
	        			</#list>
	    			</td>
	        	</tr>
	        </#if>
	    <#else>
	        <tr><td <#if useNewFields><#if canView>colspan="8"<#else>colspan="7"</#if><#else><#if canView>colspan="6"<#else>colspan="5"</#if></#if>> <p class="no-data mt-50 mb-50">还没有记录哦！</p></td></tr>
	    </#if>
	</table>
</div>
</@htmlmacro.moduleDiv>