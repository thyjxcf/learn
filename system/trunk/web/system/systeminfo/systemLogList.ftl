<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="平台基础信息设置">
 <iframe id="hiddenIframe" style="display:none"></iframe>
 <form id="logListForm" action="${action}"  method="post" >
		<@htmlmacro.tableList id="tablelist">
			<tr>
				<th width="5%">选择</th>
				<th width="25%">功能模块</th>
				<th width="40%">日志描述</th>
				<th width="20%">操作用户</th>
				<th width="10%">日志时间</th>
			</tr>
		<#if userLogList?exists && (userLogList.size() > 0)>
			<#list userLogList as logs>
			<tr>
				<td class="t-center"><span class="ui-checkbox" data-name="a"><input type="checkbox" class="chk" name="arrayIds" value="${logs.id}" /></span></td>
				<td>${logs.modName!}</td>
				<td>${logs.description!}</td>
				<td>${logs.userName!}</td>
				<td>${logs.logTime?string("yyyy-MM-dd")}</td>
			</tr>
			</#list>	
		<#else>
		    <tr>
		        <td colspan="5"><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	        </tr>
        </#if>				
		</@htmlmacro.tableList>
	  <@htmlmacro.Toolbar container="#cDivLogList">
	    <span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk" onclick='javascript:<@htmlmacro.doCheckbox checkboxname="arrayIds" />'>全选</span>
        <a href="javascript:void(0)" class="abtn-blue" onclick="deleteLog();">删除</a>	
	    <a href="javascript:void(0)" class="abtn-blue" onclick="logExport();">导出EXCEL</a> 
	  </@htmlmacro.Toolbar>
    
 </form>
</@htmlmacro.moduleDiv>

<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<#assign postfix="beginTime=${beginTime?string('yyyy-MM-dd')}&&endTime=${endTime?string('yyyy-MM-dd')}&&subSystem=${userLogDto.subSystem?default('')}&&unitid=${userLogDto.unitid?default('')}">
<script type="text/javascript">
     $(document).ready(function(){
         vselect();
     })
     
     function logExport(){
        var beginTime = $("#beginTime").val();
        var endTime = $("#endTime").val();
        var subSystem = $("#subSystem").val();
        var postfix= "beginTime=" + beginTime + "&&endTime=" + endTime +"&&subSystem="+ subSystem +"&&unitid=${userLogDto.unitid?default('')}"
		document.getElementById('hiddenIframe').src="${request.contextPath}/system/admin/platformInfoAdmin-logExport.action?${postfix}";
	 }
	 
	 function deleteLog(){
	    var array = $("input[name='arrayIds']:checked");
	    if(array.length < 1) {
	      showMsgError("请选择要删除的记录！");
	      return;
	    }
	    if(!showConfirm("您确认要删除信息吗？")) return;
	    var beginTime = $("#beginTime").val();
        var endTime = $("#endTime").val();
        var subSystem = $("#subSystem").val();
        var postfix= "beginTime=" + beginTime + "&&endTime=" + endTime +"&&subSystem="+ subSystem +"&&unitid=${userLogDto.unitid?default('')}"
	    var options = {
	       url:"${request.contextPath}/system/admin/platformInfoAdmin-remote-delLogs.action?"+postfix, 
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       timeout : 5000,
	       error : showError,
	       success : showReply
	    };
		$("#logListForm").ajaxSubmit(options);	
	 }
	 
	 function showError() {
	    showMsgError("删除失败！");
	}
	
	function showReply(data) {
	    var result = data.promptMessageDto;
	    if(result.operateSuccess) {
	        showMsgSuccess(result.promptMessage,'',cancelOperate);
	    }else{
	        showMsgError(result.promptMessage);
	    }
	}
	
	function cancelOperate(){
	   chooseList();	
	}
</script>