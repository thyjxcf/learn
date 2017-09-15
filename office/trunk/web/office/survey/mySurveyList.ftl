<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form name="mySurveyform" id="mySurveyform" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<th class="t-center" width="5%"><nobr>选择</nobr></th>
		<th width="12%">调研名称</th>
		<th width="18%">调研时间</th>
		<th width="15%">调研地点</th>
		<th width="10%">调研人数</th>
		<th width="10%">审核状态</th>
		<th width="20%">备注</th>
		<th width="10%" class="t-center">操作</th>
	</tr>
	<#if officeSurveyApplyList?exists && officeSurveyApplyList?size gt 0>
		<#list officeSurveyApplyList as item>
			<tr style="word-break:break-all; word-wrap:break-word;">
				<td class="t-center"><#if item.state == 0><p><span class="ui-checkbox">
		    		<input type="checkbox" class="chk" name="checkid" value="${item.id?default('')}">
		    		</span></p></#if>
		    	</td>
				<td>${item.surveyName!}</td>
				<td>${(item.startTime?string("yyyy/MM/dd"))?if_exists} - ${(item.endTime?string("yyyy/MM/dd"))?if_exists}</td>
				<td><#if placeByCode>${appsetting.getMcode("DM-DYDD").get(item.place!)}<#else>${item.place!}</#if></td>
				<td>${item.amount?string!}</td>
				<td><#if item.state == 0>未提交
					<#elseif item.state == 1>待审核
					<#elseif item.state == 2>审核通过
					<#elseif item.state == 3>审核不通过
					<#else></#if>
				</td>
				<td title="${item.remark!}"><@common.cutOff str='${item.remark!}' length=20/></td>
				<td class="t-center">
				<#if item.state == 0>
					<a href="javascript:doSurveyEdit('${item.id!}');"><img alt="修改" src="${request.contextPath}/static/images/icon/edit.png"></a>
				<#else>
					<a href="javascript:doSurveyView('${item.id!}');"><img alt="查看" src="${request.contextPath}/static/images/icon/view.png"></a>
		     	</#if>
		     	</td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="8"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if officeSurveyApplyList?exists && officeSurveyApplyList?size gt 0>
<@common.Toolbar container="#mySurveyListDiv">
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</input></span>
	<a href="javascript:void(0);" id="btnDelete" class="abtn-blue"  onclick="doDelete()"> 删除</a>
</@common.Toolbar>
</#if>
</form>
<script>
function doDelete(){
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("没有选要删除的申请记录，请先选择!");
		return;
	}
	
	if(!showConfirm('您确认要删除调研申请记录吗？')){
		return;
	}
	$("#btnDelete").attr("class", "abtn-unable");	
	var options = {
       url:'${request.contextPath}/office/survey/surveyManage-mySurvey-delete.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#mySurveyform').ajaxSubmit(options);
}
function showReply(data){
	$("#btnDelete").attr("class", "abtn-blue");
	if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   return;
		   }else if(data.fieldErrorMap!=null){
			  $.each(data.fieldErrorMap,function(key,value){
				   addFieldError(key,value+"");
			  });
		   }else{
		   	   showMsgError(data.promptMessage);
			   return;
		   }
	}else{
	   		showMsgSuccess("删除成功! ","",doSearch);
			return;
	}
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>