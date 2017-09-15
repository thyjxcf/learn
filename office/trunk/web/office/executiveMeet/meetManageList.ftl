<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.tableList id="tablelist">
    <tr>
		<th width="23%">会议名称</th>
    	<th width="15%">会议时间</th>
    	<th width="20%">会议地点</th>
    	<th width="20%">参会科室</th>
	    <th class="t-center" width="22%">操作</th>
    </tr>
   	<#if officeExecutiveMeetList?exists && officeExecutiveMeetList?size gt 0>
    
       <#list officeExecutiveMeetList as item>
        <tr>
        <td style="word-break:break-all; word-wrap:break-word;" title="${item.name!}"><@htmlmacro.cutOff str="${item.name!}" length=14/></td>
        <td style="word-break:break-all; word-wrap:break-word;">${(item.meetDate?string('yyyy-MM-dd HH:mm'))?if_exists}</td>
        <td style="word-break:break-all; word-wrap:break-word;" title="${item.place!}"><@htmlmacro.cutOff str="${item.place!}" length=12/></td>
    	<td style="word-break:break-all; word-wrap:break-word;" title="${item.attendDeptNames!}"><@htmlmacro.cutOff str="${item.attendDeptNames!}" length=12/></td>
    	<td class="t-center">
			<#if item.state?default(0) == 0>
			<a href="javascript:void(0);" onclick="doEdit('${item.id}');">修改</a>
    		<a href="javascript:void(0);" onclick="doPublish('${item.id}');" class="ml-10">发布</a>
    		<a href="javascript:void(0);" onclick="deleteMeet('${item.id}');" class="ml-10">删除</a>
    		<#else>
	    		已发布&nbsp;
	    		<#if item.start>
    				<a href="javascript:void(0);" onclick="viewIssues('${item.id}');" class="ml-10">查看议题</a>
	    			<#if item.hasMinutes>
	    				<a href="javascript:void(0);" onclick="viewMinutes('${item.id}');" class="ml-10">查看纪要</a>
	    			<#else>
	    				暂无纪要
	    			</#if>
    			<#else>
    				<a href="javascript:void(0);" onclick="doEdit('${item.id}');">修改</a>
	    			<a href="javascript:void(0);" onclick="manageIssues('${item.id}');" class="ml-10">管理议题</a>
    			</#if>
    		</#if>
    	</td>
		</tr>
       </#list>
    <#else>
    <tr>
    	<td colspan="5">
    	<p class="no-data mt-20 mb-20">还没有任何记录哦！</p></td>
    </tr>
	</#if>
</@htmlmacro.tableList>
<#if officeExecutiveMeetList?exists && officeExecutiveMeetList?size gt 0>
	<@htmlmacro.Toolbar container="#meetManageListDiv">
	</@htmlmacro.Toolbar>
</#if>
<script src="${request.contextPath}/static/js/myscript.js"/>
<script>
	function doPublish(meetId){
		$.getJSON("${request.contextPath}/office/executiveMeet/executiveMeet-publishMeet.action", {
	          "meetId":meetId
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
	
	function deleteMeet(meetId){
		if(confirm("您确认要删除吗？")){
			$.getJSON("${request.contextPath}/office/executiveMeet/executiveMeet-deleteMeet.action", {
		          "meetId":meetId
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
	}
	
</script>
