<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
function doSave(state){
	if(state=="0" && !isActionable("#btnSave")){
		return;
	}
	if(state=="1" && !isActionable("#btnSubmit")){
		return;
	}
	
	if(!checkAllValidate("#contentDiv")){
		return;
	}
	
	var amount = $("#amount").val();
	if(parseInt(amount)==0){
		showMsgError("调研人数不得为0");
		return;
	}
	
	if(!checkAfterDateWithMsg(document.getElementById("startTime"),document.getElementById("endTime"),"调研开始时间不能大于结束时间"))
		return;
	
	if(state=="0"){
		$("#btnSave").attr("class", "abtn-unable");
	}else{
		$("#btnSubmit").attr("class", "abtn-unable");
	}
	
	var options = {
       url:'${request.contextPath}/office/survey/surveyManage-mySurvey-save.action?officeSurveyApply.state='+state, 
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
		   			doSearch();
				});
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>调研申请</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="addform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="officeSurveyApply.id" value="${officeSurveyApply.id?default('')}">
    	<input type="hidden" id="unitId" name="officeSurveyApply.unitId" value="${officeSurveyApply.unitId?default('')}">
    	
	    <tr>
	      	<th style="width:15%">调研名称：</th>
	    	<td style="width:35%">
    			<input type="text" msgName="调研名称" class="input-txt fn-left" id="surveyName" name="officeSurveyApply.surveyName" maxlength="50" notNull="true" value="${officeSurveyApply.surveyName!}" style="width:180px;">
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
	    		<@htmlmacro.datepicker class="input-txt" style="width:180px;" name="officeSurveyApply.startTime" id="startTime" size="20" maxlength="19" notNull="true" msgName="调研开始时间" value="${(officeSurveyApply.startTime?string('yyyy-MM-dd'))?if_exists}" dateFmt="yyyy-MM-dd"/>
	    		<span class="c-orange mt-5 ml-5">*</span>
	    	</td>	
	      	<th style="width:15%">调研结束时间：</th>
	    	<td style="width:35%">
	    		<@htmlmacro.datepicker class="input-txt" style="width:180px;" name="officeSurveyApply.endTime" id="endTime" size="20" maxlength="19" notNull="true" msgName="调研结束时间" value="${(officeSurveyApply.endTime?string('yyyy-MM-dd'))?if_exists}" dateFmt="yyyy-MM-dd"/>
	    		<span class="c-orange mt-5 ml-5">*</span>
	    	</td>
	    </tr>
	    <tr>
	    	<th style="width:15%">调研地点：</th>
	    	<td style="width:35%">
	    	<#if placeByCode>
		    	<@htmlmacro.select style="width:190px;" valName="officeSurveyApply.place" valId="place" myfunchange="getPlaceMessage" notNull="true" msgName="调研地点">									
					<a val=""><span>--请选择--</span></a>
					${appsetting.getMcode("DM-DYDD").getHtmlTag("${officeSurveyApply.place?default('')}",false)}
				</@htmlmacro.select>
		    <#else>
		    	<input type="text" msgName="调研地点" class="input-txt fn-left" id="place" name="officeSurveyApply.place" maxlength="50" notNull="true" value="${officeSurveyApply.place!}" style="width:180px;">
		    </#if>
		    	<span class="c-orange mt-5 ml-10">*</span>
	    	</td>	
	    	<th style="width:15%">调研人数：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="调研人数" class="input-txt fn-left" id="amount" name="officeSurveyApply.amount" notNull="true" regex="/^\d*$/" regexMsg="请正确填写数量" maxlength="8" value="${officeSurveyApply.amount!}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="备注" id="remark" name="officeSurveyApply.remark" maxLength="500" colspan="3" style="width:470px;" value="${officeSurveyApply.remark!}" />
	    </tr>
    </table>
</form>    
</div>
<p class="dd">
    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave('0');" id="btnSave">保存</a>
    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave('1');" id="btnSubmit">提交</a>
    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>
<script>
vselect();
function getPlaceMessage(){
	var place = $("#place").val();
	$.getJSON("${request.contextPath}/office/survey/surveyManage-mySurvey-getPlaceMessage.action", {
	   "searchPlace":place
      }, function(data) {
		if (data!=null && data != 'success') {
	        showMsgWarn(data);
	    } else {
	    }
	});
}
</script>
</@htmlmacro.moduleDiv>