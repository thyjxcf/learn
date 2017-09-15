<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.tableList id="tablelist">
    <tr>
		<th width="17%">会议名称</th>
    	<th width="5%">序号</th>
    	<th width="17%">议题名称</th>
    	<th width="17%">附件</th>
    	<th width="12%">提报领导</th>
    	<th width="12%">主办科室</th>
    	<th width="12%">列席科室</th>
	    <th class="t-center" width="8%">操作</th>
    </tr>
   	<#if officeExecutiveMeetList?exists && officeExecutiveMeetList?size gt 0>
    
       <#list officeExecutiveMeetList as item>
        <tr>
	        <td style="word-break:break-all; word-wrap:break-word;" title="${item.name!}" rowspan="${item.issues?size}">
	        	<a href="javascript:void(0);" onclick="viewMeet('${item.id!}');"><@htmlmacro.cutOff str="${item.name!}" length=10/></a>
	        	<br/>
	        	(${(item.meetDate?string('yyyy-MM-dd HH:mm'))?if_exists})
        	</td>
	        <#if item.issues?size gt 0>
		        <#list item.issues as issue>
		        	<td>${issue.serialNumber?default(0)}</td>
		        	<td style="word-break:break-all; word-wrap:break-word;" title="${issue.name!}"><@htmlmacro.cutOff str="${issue.name!}" length=10/></td>
			        <td>
			        	<#if issue.attachments?exists && issue.attachments?size gt 0>
							<#list issue.attachments as att>
								<a href="javascript:void(0);" title="${att.fileName!}" onclick="doDownload('${att.downloadPath!}');"><@htmlmacro.cutOff str='${att.fileName!}' length=15/></a>
								<a href="javascript:viewAttachment('${att.id!}');">(预览)</a>
								<br/>
							</#list>
						</#if>
			        </td>
			        <td style="word-break:break-all; word-wrap:break-word;" title="${issue.leaderNameStr!}"><@htmlmacro.cutOff str="${issue.leaderNameStr!}" length=6/></td>
			        <td style="word-break:break-all; word-wrap:break-word;" title="${issue.hostDeptNameStr!}"><@htmlmacro.cutOff str="${issue.hostDeptNameStr!}" length=6/></td>
			    	<td style="word-break:break-all; word-wrap:break-word;" title="${issue.attendDeptNameStr!}"><@htmlmacro.cutOff str="${issue.attendDeptNameStr!}" length=6/></td>
		        	<#if item.issues?size gt 1 && issue_index lt (item.issues?size-1) >
		        		<#if issue_index == 0>
			        		<td class="t-center" rowspan="${item.issues?size}">
								<#if item.start>
					    			<#if item.hasMinutes>
					    				<a href="javascript:void(0);" onclick="viewMinutes('${item.id}');" class="ml-10">查看纪要</a>
					    			<#else>
					    				暂无纪要
					    			</#if>
				    			<#else>
					    			未开始
				    			</#if>
					    	</td>
				    	</#if>
		        		</tr><tr>
		        	</#if>
		        </#list>
	        	<#if item.issues?size == 1>
	        		<td class="t-center">
						<#if item.start>
			    			<#if item.hasMinutes>
			    				<a href="javascript:void(0);" onclick="viewMinutes('${item.id}');" class="ml-10">查看纪要</a>
			    			<#else>
			    				暂无纪要
			    			</#if>
		    			<#else>
			    			未开始
		    			</#if>
			    	</td>
			    </#if>
		    <#else>
		    	<td colspan="6" class="t-center">暂无议题</td>
		    	<td class="t-center" rowspan="${item.issues?size}">
					<#if item.start>
		    			<#if item.hasMinutes>
		    				<a href="javascript:void(0);" onclick="viewMinutes('${item.id}');" class="ml-10">查看纪要</a>
		    			<#else>
		    				暂无纪要
		    			</#if>
	    			<#else>
		    			未开始
	    			</#if>
    			</td>
		    </#if>
		</tr>
       </#list>
    <#else>
    <tr>
    	<td colspan="8">
    		<p class="no-data mt-20 mb-20">还没有任何记录哦！</p>
    	</td>
    </tr>
	</#if>
</@htmlmacro.tableList>
<#if officeExecutiveMeetList?exists && officeExecutiveMeetList?size gt 0>
	<@htmlmacro.Toolbar container="#myMeetListDiv">
	</@htmlmacro.Toolbar>
</#if>
<script src="${request.contextPath}/static/js/myscript.js"/>
<script>
	function viewMinutes(meetId){
		openDiv("#minutesLayer", "#minutesLayer .close,#minutesLayer .submit,#minutesLayer .reset", "${request.contextPath}/office/executiveMeet/executiveMeet-minutesViewLimit.action?meetId="+meetId, null, null, "700px");
	}
	function viewMeet(meetId){
		openDiv("#minutesLayer", "#minutesLayer .close,#minutesLayer .submit,#minutesLayer .reset", "${request.contextPath}/office/executiveMeet/executiveMeet-meetView.action?meetId="+meetId, null, null, "700px");
	}
</script>