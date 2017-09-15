<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
function doSave(state){
	if(state=="2" && !isActionable("#btnSave")){
		return;
	}
	if(state=="3" && !isActionable("#btnSubmit")){
		return;
	}
	
	if(state=="3"){
		var opinion=$("#opinion").val();
	  if(opinion==null||opinion==''){
	  	showMsgWarn("审核不通过，请填写审核意见！");
	  	return;
	  }
	}
	
	if(state=="2"){
		$("#btnSave").attr("class", "abtn-unable");
	}else{
		$("#btnSubmit").attr("class", "abtn-unable");
	}
	var options = {
       url:'${request.contextPath}/office/survey/surveyManage-surveyAuditSave.action?officeSurveyApply.state='+state, 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#addform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSave").attr("class", "abtn-blue");
	if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			   }else{
			   	   showMsgError(data.promptMessage);
				   return;
			   }
	}else{
		   		showMsgSuccess(data.promptMessage,"",function(){
		   			closeDiv("#mySurveyLayer");
		   			doSurveyAudit();
				});
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>调研审核</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="addform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="officeSurveyApply.id" value="${officeSurveyApply.id?default('')}">
    	<input type="hidden" id="unitId" name="officeSurveyApply.unitId" value="${officeSurveyApply.unitId?default('')}">
    	<input type="hidden" id="applyDate" name="officeSurveyApply.applyDate" value="${officeSurveyApply.applyDate?default('')}">
    	
	    <tr>
	      	<th style="width:15%">调研名称：</th>
	    	<td style="width:35%">
    			<input type="text" msgName="调研名称" class="input-txt fn-left input-readonly" id="surveyName" name="officeSurveyApply.surveyName" maxlength="50" notNull="true" value="${officeSurveyApply.surveyName!}" style="width:180px;" readonly="readonly">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    	<th style="width:15%">申请人：</th>
	    	<td style="width:35%">
	    		<input type="hidden" id="applyUserId" name="officeSurveyApply.applyUserId" value="${officeSurveyApply.applyUserId?default('')}">
	    		<input type="text" msgName="申请人" class="input-txt fn-left input-readonly" readonly="readonly" id="applyUserName" name="officeSurveyApply.applyUserName" maxlength="30" value="${officeSurveyApply.applyUserName!}" style="width:180px;">
	    	</td>
	    </tr>
	    <tr>
	    	<th style="width:15%">调研开始时间：</th>
	    	<td style="width:35%">
	    		<@htmlmacro.datepicker class="input-txt" style="width:180px;" name="officeSurveyApply.startTime" id="startTime" size="20" maxlength="19" notNull="true" msgName="调研开始时间" value="${(officeSurveyApply.startTime?string('yyyy-MM-dd'))?if_exists}" dateFmt="yyyy-MM-dd" readonly="true"/>
	    		<span class="c-orange mt-5 ml-5">*</span>
	    	</td>	
	      	<th style="width:15%">调研结束时间：</th>
	    	<td style="width:35%">
	    		<@htmlmacro.datepicker class="input-txt" style="width:180px;" name="officeSurveyApply.endTime" id="endTime" size="20" maxlength="19" notNull="true" msgName="调研结束时间" value="${(officeSurveyApply.endTime?string('yyyy-MM-dd'))?if_exists}" dateFmt="yyyy-MM-dd" readonly="true"/>
	    		<span class="c-orange mt-5 ml-5">*</span>
	    	</td>
	    </tr>
	    <tr>
	    	<th style="width:15%">调研地点：</th>
	    	<td style="width:35%">
	    	<#if placeByCode>
	    		<input type="text" msgName="调研地点" class="input-txt fn-left input-readonly" id="placeStr" name="placeStr" value="${appsetting.getMcode("DM-DYDD").get(officeSurveyApply.place!)}" style="width:180px;" readonly="true">
		    	<input type="hidden" id="place" name="officeSurveyApply.place" value="${officeSurveyApply.place!}" />
		    <#else>
		    	<input type="text" msgName="调研地点" class="input-txt fn-left input-readonly" id="place" name="officeSurveyApply.place" value="${officeSurveyApply.place!}" style="width:180px;" readonly="true">
		    </#if>
		    	<span class="c-orange mt-5 ml-10">*</span>
	    	</td>	
	    	<th style="width:15%">调研人数：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="调研人数" class="input-txt fn-left input-readonly" id="amount" name="officeSurveyApply.amount" notNull="true" regex="/^\d*$/" regexMsg="请正确填写数量" maxlength="8" value="${officeSurveyApply.amount!}" style="width:180px;" readonly="true">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="备注" id="remark" name="officeSurveyApply.remark" maxLength="500" colspan="3" style="width:470px;" value="${officeSurveyApply.remark!}" readonly="true"/>
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="审核意见" id="opinion" name="officeSurveyAudit.opinion" maxLength="500" colspan="3" style="width:470px;" value="${officeSurveyAudit.opinion!}" />
	    </tr>
    </table>
</form>    
</div>
<p class="dd">
    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave('2');" id="btnSave">审核通过</a>
    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave('3');" id="btnSubmit">审核不通过</a>
    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>
<script>
vselect();
</script>
</@htmlmacro.moduleDiv>