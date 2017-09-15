<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doEdit(applyId){
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/carmanage/carmanage-carOverTimeEdit.action?applyId="+applyId, null, null, "500px");
}

</script>
<form>
<@common.tableList id="tablelist">
	<tr>
		<th width="10%">驾驶员</th>
		<th width="10%">车牌号码</th>
		<th width="10%">乘车联系人</th>
		<th width="15%">用车时间</th>
		<th width="6%">目的地</th>
		<th width="10%">用车部门</th>
		<th width="7%">人数</th>
		<th width="16%">用车事由</th>
		<th width="7%">加班时间（天）</th>
		<th width="9%">加班时间汇总（天）</th>
	</tr>
	<#if officeCarApplyDtos?exists && officeCarApplyDtos?size gt 0>
		<#list officeCarApplyDtos as x>
		<#assign officeCarApplyList = x.officeCarApplyList/>
			<#assign i = 1/>
			<#assign totalSize = officeCarApplyList?size/>
			<tr>
			<td rowspan=${x.count!} style="word-break:break-all; word-wrap:break-word;">${x.driverName!}</td>
			<#list officeCarApplyList as xd>
				<td style="word-break:break-all; word-wrap:break-word;">${xd.carNumber!}</td>
				<td>${xd.linkUserName!}</td>
				<td>${xd.useTime?string('yyyy-MM-dd HH:mm')}  (${xd.xinqi!})</td>
				<td><@common.cutOff4List str="${xd.carLocation!}" length=8 />
				</td>
				<td>${xd.deptName!}</td>
				<td>${xd.personNumber!}</td>
				<td title="${xd.reason!}"><@common.cutOff4List str="${xd.reason!}" length=10 />
				</td>
				<td>
						${xd.overtimeNumber?string("0.#")}
				</td>
				<#if i == 1>
				<td rowspan="${x.count!}">
						${x.overtimeNumber?string("0.#")}
				</td>
				</#if>
				<#if i lt totalSize>
					</tr><tr>
				</#if>
					<#assign i = i+1/>
				</#list>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="10"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>

</form>
<div class="popUp-layer" id="classLayer" style="display:none;width:450px;"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>