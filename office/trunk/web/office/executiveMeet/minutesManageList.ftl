<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.tableList id="tablelist">
    <tr>
		<th width="30%">会议名称</th>
    	<th width="15%">会议时间</th>
    	<th width="20%">会议地点</th>
    	<th width="25%">参会科室</th>
	    <th class="t-center" width="10%">操作</th>
    </tr>
   	<#if officeExecutiveMeetList?exists && officeExecutiveMeetList?size gt 0>
    
       <#list officeExecutiveMeetList as item>
        <tr>
        <td style="word-break:break-all; word-wrap:break-word;" title="${item.name!}"><@htmlmacro.cutOff str="${item.name!}" length=20/></td>
        <td style="word-break:break-all; word-wrap:break-word;">${(item.meetDate?string('yyyy-MM-dd HH:mm'))?if_exists}</td>
        <td style="word-break:break-all; word-wrap:break-word;" title="${item.place!}"><@htmlmacro.cutOff str="${item.place!}" length=12/></td>
    	<td style="word-break:break-all; word-wrap:break-word;" title="${item.attendDeptNames!}"><@htmlmacro.cutOff str="${item.attendDeptNames!}" length=16/></td>
    	<td class="t-center">
			<a href="javascript:void(0);" onclick="minutesEdit('${item.id}');" class="ml-10">编辑纪要</a>
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
	<@htmlmacro.Toolbar container="#minutesManageListDiv">
	</@htmlmacro.Toolbar>
</#if>
<script src="${request.contextPath}/static/js/myscript.js"/>
<script>
	function minutesEdit(meetId){
		openDiv("#minutesLayer", "#minutesLayer .close,#minutesLayer .submit,#minutesLayer .reset", "${request.contextPath}/office/executiveMeet/executiveMeet-minutesEdit.action?meetId="+meetId, null, null, "700px");
	}
</script>