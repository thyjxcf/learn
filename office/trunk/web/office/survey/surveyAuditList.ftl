<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<script>
	
</script>

<@htmlmacro.tableList class="public-table table-list table-list-edit mt-20">
  	<tr>
    	<th >序号</th>
    	<th >调研名称</th>
    	<th >调研时间</th>
    	<th >调研地点</th>
    	<th >调研人数</th>
    	<th >申请人</th>
    	<th >申请人部门</th>
    	<th >审核状态</th>
    	<th class="t-center">操作</th>
    </tr>
    <#if officeSurveyApplyList?exists && officeSurveyApplyList?size gt 0>
    	<#list officeSurveyApplyList as survey>
    		<tr>
    			<td >${survey_index+1}</td>
    			<td >${survey.surveyName!}</td>
    			<td >${(survey.startTime?string('yyyy/MM/dd'))?if_exists} - ${(survey.endTime?string('yyyy/MM/dd'))?if_exists}</td>
		    	<td ><#if placeByCode>${appsetting.getMcode("DM-DYDD").get(survey.place!)}<#else>${survey.place!}</#if></td>
		    	<td>${survey.amount!}</td>
		    	<td >${survey.applyUserName!}</td>
		    	<td >${survey.applyDeptName!}</td>
		    	<td >
		    		<#if survey.state==1>
		    			待审核
		    		<#elseif survey.state==2>
		    			审核结束-已通过
		    		<#elseif survey.state==3>
		    			审核结束-未通过
		    		</#if>
		    	</td>
		    	<td class="t-center">
		    		<#if survey.state?default(1)==1>
		    			<a href="javascript:void(0);" onclick="doAudit('${survey.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="审核"></a>
		    		<#else>
		    			<a href="javascript:void(0);" onclick="doInfo('${survey.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
		    		</#if>
		    	</td>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='9'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if officeSurveyApplyList?exists && officeSurveyApplyList?size gt 0>
<@htmlmacro.Toolbar container="#mySurveyListDiv">
</@htmlmacro.Toolbar>
</#if>
<div class="popUp-layer" id="mySurveyLayer" style="display:none;width:700px;"></div>
<script>

	function doAudit(id){
   		var url="${request.contextPath}/office/survey/surveyManage-surveyAuditEdit.action?officeSurveyApply.id="+id;
   		openDiv("#mySurveyLayer", "#mySurveyLayer .close,#mySurveyLayer .submit,#mySurveyLayer .reset", url, null, null, "900px");
	}
	
	function doInfo(id){
		openDiv("#mySurveyLayer", "#mySurveyLayer .close,#mySurveyLayer .submit,#mySurveyLayer .reset", "${request.contextPath}/office/survey/surveyManage-surveyAuditView.action?officeSurveyApply.id="+id, null, null, "900px");
	}
	
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>