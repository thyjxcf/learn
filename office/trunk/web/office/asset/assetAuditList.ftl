<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doAudit(id, applyid, auditid){
	var roleCode = '${roleCode!}';
	var stateQuery = '${stateQuery!}';
	var str = "?assetApply.id="+id+"&applyid="+applyid+"&auditid="+auditid+"&roleCode="+roleCode+"&stateQuery="+stateQuery;
	load("#assetAuditListDiv","${request.contextPath}/office/asset/assetAdmin-auditEdit.action"+str);
}

</script>
<div class="pub-table-inner">
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="10%">请购单编号</th>
		<th width="9%">请购申请人</th>
		<th width="10%">所在部门</th>
		<th width="8%">申请日期</th>
		<th width="9%">类别</th>
		<th width="10%">物品名称</th>
		<th width="8%">数量</th>
		<th width="8%">申请单价</th>
		<th width="10%">申请总价</th>
		<th width="10%">审核状态</th>
		<th class="t-center" width="">操作</th>
	</tr>
	<#if auditList?exists && (auditList?size>0)>
		<#list auditList as ent>
		    <tr>
		    	<td>${ent.applyCode?default("")}</td>
		    	<td>${ent.applyUserName?default("")}</td>
		    	<td><@htmlmacro.cutOff4List str="${ent.deptName?default('')}" length=5 />
		    	<td>${(ent.applyDate?string('yyyy-MM-dd'))?if_exists}</td>
		    	</td>
				<td><@htmlmacro.cutOff4List str="${ent.categoryName?default('')}" length=5 />
				</td>
				<td><@htmlmacro.cutOff4List str="${ent.assetName?default('')}" length=7 />
				</td>
				<td>${ent.assetNumber!}</td>
				<td>${(ent.unitPrice?string("0.00"))!}</td>
				<td>${(ent.totalUnitPrice?string("0.00"))!}</td>
				<td><span class="c-blue">
					<#if ent.auditStatus="1">待审核
					<#elseif ent.auditStatus="2" || ent.auditStatus="4">通过
					<#elseif ent.auditStatus="3">未通过
					<#else>未提交
					</#if>
					</span>
				</td>
				<td class="t-center">
					<#if ent.auditStatus="1">
					<a href="javascript:doAudit('${ent.id}', '${ent.applyId}', '${ent.auditId}');"><img alt="审核" src="${request.contextPath}/static/images/icon/check.png"></a>
					<#else>
					<a href="javascript:doAudit('${ent.id}', '${ent.applyId}', '${ent.auditId}');"><img alt="查看" src="${request.contextPath}/static/images/icon/view.png"></a>
					</#if>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="11"><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#assetAuditListDiv"></@htmlmacro.Toolbar>
</form>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>