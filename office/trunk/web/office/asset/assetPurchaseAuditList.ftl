<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doEdit(id){
	var stateQuery = $("#stateQuery").val();
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/asset/assetAdmin-purchaseAuditEdit.action?purchase.id="+id+"&stateQuery="+stateQuery, null, null, "500px");
}

function doSearch(){
	var stateQuery = $("#stateQuery").val();
	load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-purchaseAudit.action?stateQuery="+stateQuery);
}

</script>
<div class="pub-table-inner">
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt b ml-10">审核状态：</div>
		<@htmlmacro.select style="width:120px;" valName="stateQuery" valId="stateQuery" myfunchange="doSearch">
			<a val=""><span>全部</span></a>
		    <a val="1"  <#if stateQuery?default("")=="1">class="selected"</#if>><span>待审核</span></a>
		    <a val="2"  <#if stateQuery?default("")=="2">class="selected"</#if>><span>通过</span></a>
		    <a val="3"  <#if stateQuery?default("")=="3">class="selected"</#if>><span>未通过</span></a>
		</@htmlmacro.select>
        <div class="fn-clear"></div>
    </div>
</div>
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="10%" rowspan="2">请购单编号</th>
		<th width="8%" rowspan="2">类别</th>
		<th width="8%" rowspan="2">物品名称</th>
		<th width="6%" rowspan="2">数量</th>
		<th width="8%" rowspan="2">申请单价</th>
		<th width="11%" rowspan="2">实际采购单价</th>
		<th width="8%" rowspan="2">申请总价</th>
		<th width="11%" rowspan="2">实际采购总价</th>
		<th width="16%" colspan="2" class="t-center">采购人</th>
		<th width="9%" rowspan="2">采购批准</th>
		<th class="t-center" width="6%" rowspan="2">操作</th>
	</tr>
	<tr>
		<th width="8%">甲</th>
		<th width="8%">乙(请购人)</th>
	</tr>
	<#if purchaseList?exists && (purchaseList?size>0)>
		<#list purchaseList as ent>
		    <tr>
		    	<td>${ent.applyCode?default("")}</td>
				<td><@htmlmacro.cutOff4List str="${ent.categoryName?default('')}" length=5 />
				</td>
				<td><@htmlmacro.cutOff4List str="${ent.assetName?default('')}" length=5 />
				</td>
				<td>${ent.assetNumber!}</td>
				<td>${(ent.unitPrice?string("0.00"))!}</td>
				<td>${(ent.purchasePrice?string("0.00"))!}</td>
				<td>${ent.totalUnitPrice?string("0.00")!}</td>
				<td>${(ent.purchaseTotalPrice?string("0.00"))?if_exists}</td>
				<td>${ent.purchaseUserName1!}</td>
				<td>${ent.purchaseUserName2!}</td>
		    	<td>
		    		<#if ent.purchaseState?default("") =="2">
					<span class="c-blue">通过</span><@htmlmacro.cutOff4List str="${ent.purchaseOpinion?default('')}" length=3 />
					<#elseif ent.purchaseState?default("") =="3">
					<span class="c-blue">未通过</span><@htmlmacro.cutOff4List str="${ent.purchaseOpinion?default('')}" length=2 />
					<#elseif ent.purchaseState?default("") =="1">
					<span class="c-blue">待审核</span>
					</#if>
		    	</td>
				<td class="t-center">
					<#if ent.purchaseState?default("") =="1">
					<a href="javascript:doEdit('${ent.id}');"><img alt="编辑" src="${request.contextPath}/static/images/icon/edit.png"></a>
					<#else>
					<a href="javascript:doEdit('${ent.id}');"><img alt="查看" src="${request.contextPath}/static/images/icon/view.png"></a>
					</#if>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="12"><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#adminDiv"></@htmlmacro.Toolbar>
</form>
</div>
<div class="popUp-layer" id="classLayer" style="display:none;width:620px;"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>