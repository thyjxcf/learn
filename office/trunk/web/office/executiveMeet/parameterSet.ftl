<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="权限设置">
	<@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="2" style="text-align:center;">权限设置</th>
	    </tr>
	    <tr>
	        <th style="width:30%"><span class="c-orange mr-5">*</span>固定参会科室：</th>
	        <td style="width:70%">
	        	<@commonmacro.selectMoreTree idObjectId="deptIds" nameObjectId="deptNames" treeUrl=request.contextPath+"/common/xtree/deptTree.action" callback="fixedDepts">
	        	<input type="hidden" id="deptIds" name="deptIds" value="${deptIdsStr!}"/>
	        	<textarea name="deptNames" id="deptNames" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;">${deptNamesStr!}</textarea>
	        	</@commonmacro.selectMoreTree>
	        </td>
	    </tr>
	    <tr>
	        <th><span class="c-orange mr-5">*</span>纪要维护人员：</th>
	        <td>
	        	<@commonmacro.selectMoreUser idObjectId="userIds" nameObjectId="userNames" callback="minutesUsers">
	        	<input type="hidden" id="userIds" name="userIds" value="${userIdsStr!}"/>
	        	<textarea name="userNames" id="userNames" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;">${userNamesStr!}</textarea>
	        	</@commonmacro.selectMoreUser>
	        </td>
	    </tr>
	    <tr>
	        <th colspan="2" style="text-align:center;">部门负责人跟工作会议一致，请在工作会议统一设置</th>
	    </tr>
	</@htmlmacro.tableDetail>
</@htmlmacro.moduleDiv>
<script>
	function fixedDepts(){
		var deptIds=$("#deptIds").val();
		if(deptIds !=''){
			var url="${request.contextPath}/office/executiveMeet/executiveMeet-fixedDepts.action";
			$.post(url,{"deptIdsStr":deptIds},function(data){
				showReply(data);
			},'json');
		}else{
			showMsgWarn("请选择固定列席科室");
		}
	}
	function minutesUsers(){
		var userIds=$("#userIds").val();
		if(userIds !=''){
			var url="${request.contextPath}/office/executiveMeet/executiveMeet-minutesUsers.action";
			$.post(url,{"userIdsStr":userIds},function(data){
				showReply(data);
			},'json');
		}else{
			showMsgWarn("请选择纪要维护人员");
		}
	}
	function showReply(data){
		if(!data.operateSuccess){
			showMsgError(data.promptMessage);
		}else{
			showMsgSuccess(data.promptMessage);
		}
	}
</script>