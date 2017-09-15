<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doView(id, applyid){
	var str = "?assetApply.id="+id+"&applyid="+applyid;
	load("#assetQueryList","${request.contextPath}/office/asset/assetAdmin-applyQueryView.action"+str);
}

</script>
<div class="pub-table-inner">
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="8%">申请日期</th>
		<th width="10%">申请人</th>
		<th width="10%">所在部门</th>
		<th width="14%">类别</th>
		<th width="14%">物品名称</th>
		<th width="7%">审核状态</th>
		<th width="8%">处室负责人</th>
		<th width="8%">分管校领导</th>
		<th width="7%">校长</th>
		<th width="7%">会议讨论</th>
		<th class="t-center" width="">操作</th>
	</tr>
	<#if applyList?exists && (applyList?size>0)>
		<#list applyList as ent>
		    <tr>
				<td>${(ent.applyDate?string('yyyy-MM-dd'))?if_exists}</td>
				<td>${ent.applyUserName?default("")}</td>
				<td>${ent.deptName?default('')}</td>
				<td><@htmlmacro.cutOff4List str="${ent.categoryName?default('')}" length=20 />
				</td>
				<td><@htmlmacro.cutOff4List str="${ent.assetName?default('')}" length=20 />
				</td>
				<td><span class="c-blue">
					<#if ent.applyStatus?default("") == "2">通过<#elseif ent.applyStatus?default("") == "3">未通过<#else>待审核</#if>
					</span>
				</td>
				<td>
					<#if ent.deptState?default("") =="2" || ent.deptState?default("") =="4">
					<span class="c-blue">通过</span>
					<#elseif ent.deptState?default("") =="3">
					<span class="c-blue">未通过</span>
					<#elseif ent.deptState?default("") =="1">
					<span class="c-blue">待审核</span>
					</#if>
				</td>
				<td>
					<#if ent.assetLeaderState?default("") =="2" || ent.assetLeaderState?default("") =="4">
					<span class="c-blue">通过</span>
					<#elseif ent.assetLeaderState?default("") =="3">
					<span class="c-blue">未通过</span>
					<#elseif ent.assetLeaderState?default("") =="1">
					<span class="c-blue">待审核</span>
					</#if>
					
				</td>
				<td>
					<#if ent.schoolmasterState?default("") =="2" || ent.schoolmasterState?default("") =="4">
					<span class="c-blue">通过</span>
					<#elseif ent.schoolmasterState?default("") =="3">
					<span class="c-blue">未通过</span>
					<#elseif ent.schoolmasterState?default("") =="1">
					<span class="c-blue">待审核</span>
					</#if>
				</td>
				<td>
					<#if ent.meetingleaderState?default("") =="2" || ent.meetingleaderState?default("") =="4">
					<span class="c-blue">通过</span>
					<#elseif ent.meetingleaderState?default("") =="3">
					<span class="c-blue">未通过</span>
					<#elseif ent.meetingleaderState?default("") =="1">
					<span class="c-blue">待审核</span>
					</#if>
				</td>
				<td class="t-center">
					<a href="javascript:doView('${ent.id!}','${ent.applyId!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="11"><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#assetQueryList"></@htmlmacro.Toolbar>
</form>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>