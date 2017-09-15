<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form name="surveyQueryform" id="surveyQueryform" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<th width="10%">调研名称</th>
		<th width="15%">调研时间</th>
		<th width="13%">调研地点</th>
		<th width="8%">调研人数</th>
		<th width="8%">申请人</th>
		<th width="8%">申请人部门</th>
		<th width="15%">备注</th>
		<th width="8%">审核状态</th>
		<th width="15%">审核意见</th>
	</tr>
	<#if officeSurveyApplyList?exists && officeSurveyApplyList?size gt 0>
		<#list officeSurveyApplyList as item>
			<tr style="word-break:break-all; word-wrap:break-word;">
				<td>${item.surveyName!}</td>
				<td>${(item.startTime?string("yyyy/MM/dd"))?if_exists} - ${(item.endTime?string("yyyy/MM/dd"))?if_exists}</td>
				<td><#if placeByCode>${appsetting.getMcode("DM-DYDD").get(item.place!)}<#else>${item.place!}</#if></td>
				<td>${item.amount?string!}</td>
				<td>${item.applyUserName!}</td>
				<td>${item.applyDeptName!}</td>
				<td title="${item.remark!}"><@common.cutOff str='${item.remark!}' length=15/></td>
				<td><#if item.state == 0>未提交
					<#elseif item.state == 1>待审核
					<#elseif item.state == 2>审核通过
					<#elseif item.state == 3>审核不通过
					<#else></#if>
				</td>
				<td title="${item.opinion!}"><@common.cutOff str='${item.opinion!}' length=15/></td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="9"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if officeSurveyApplyList?exists && officeSurveyApplyList?size gt 0>
<@common.Toolbar container="#surveyQueryListDiv"/>
</#if>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>