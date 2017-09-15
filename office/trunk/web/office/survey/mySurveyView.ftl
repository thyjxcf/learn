<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>调研详情</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="viewform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="officeSurveyApply.id" value="${officeSurveyApply.id?default('')}">
    	<input type="hidden" id="unitId" name="officeSurveyApply.unitId" value="${officeSurveyApply.unitId?default('')}">
    	
	    <tr>
	      	<th style="width:15%">调研名称：</th>
	    	<td style="width:35%">
    			<input type="text" msgName="调研名称" class="input-txt fn-left input-readonly" readonly="readonly" id="surveyName" name="officeSurveyApply.surveyName" maxlength="50" notNull="true" value="${officeSurveyApply.surveyName!}" style="width:180px;">
	    	</td>
	    	<th style="width:15%">申请人：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="申请人" class="input-txt fn-left input-readonly" readonly="readonly" id="applyUserName" name="officeSurveyApply.applyUserName" maxlength="30" value="${officeSurveyApply.applyUserName!}" style="width:180px;">
	    	</td>
	    </tr>
	    <tr>
	    	<th style="width:15%">调研开始时间：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="调研开始时间" class="input-txt fn-left input-readonly" readonly="readonly" id="startTime" name="officeSurveyApply.startTime" value="${(officeSurveyApply.startTime?string('yyyy-MM-dd'))?if_exists}" style="width:180px;">
	    	</td>	
	      	<th style="width:15%">调研结束时间：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="调研结束时间" class="input-txt fn-left input-readonly" readonly="readonly" id="startTime" name="officeSurveyApply.startTime" value="${(officeSurveyApply.startTime?string('yyyy-MM-dd'))?if_exists}" style="width:180px;">
	    	</td>
	    </tr>
	    <tr>
	    	<th style="width:15%">调研地点：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="调研地点" class="input-txt fn-left input-readonly" readonly="readonly" id="place" name="officeSurveyApply.place" maxlength="50" notNull="true" value="<#if placeByCode>${appsetting.getMcode("DM-DYDD").get(officeSurveyApply.place!)}<#else>${officeSurveyApply.place!}</#if>" style="width:180px;">
	    	</td>	
	    	<th style="width:15%">调研人数：</th>
	    	<td style="width:35%">
	    		<input type="text" msgName="调研人数" class="input-txt fn-left input-readonly" readonly="readonly" id="amount" name="officeSurveyApply.amount" notNull="true" regex="/^\d*$/" regexMsg="请正确填写数量" maxlength="8" value="${officeSurveyApply.amount!}" style="width:180px;">
	    	</td>
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="备注" class="text-area input-readonly fn-left" readonly="true" id="remark" name="officeSurveyApply.remark" maxLength="500" colspan="3" style="width:470px;" value="${officeSurveyApply.remark!}" />
	    </tr>
	    <tr>
	    	<th style="width:15%">审核状态：</th>
	    	<td style="width:85%" colspan="3">
	    		<input type="text" msgName="审核状态" class="input-txt fn-left input-readonly" readonly="readonly" id="state" name="officeSurveyApply.state" maxlength="50" notNull="true" value="<#if officeSurveyApply.state==0>未提交<#elseif officeSurveyApply.state==1>待审核<#elseif officeSurveyApply.state==2>审核通过<#else>审核不通过</#if>" style="width:180px;">
	    	</td>	
	    </tr>
	    <tr>
	    	<@htmlmacro.tdt msgName="审核意见" class="text-area input-readonly fn-left" readonly="true" id="opinion" name="officeSurveyApply.opinion" maxLength="500" colspan="3" style="width:470px;" value="${officeSurveyApply.opinion!}" />
	    </tr>
    </table>
</form>    
</div>
<p class="dd">
    <a class="abtn-blue reset ml-5" href="javascript:void(0);">返回</a>
</p>
<script>
vselect();
</script>
</@htmlmacro.moduleDiv>