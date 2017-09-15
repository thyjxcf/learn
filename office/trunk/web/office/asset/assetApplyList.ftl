<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doAdd(){
	var stateQuery = $("#stateQuery").val();
	//load("#adminDiv","${request.contextPath}/office/asset/assetAdmin-addApply.action?stateQuery="+stateQuery);
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/asset/assetAdmin-addApply.action?stateQuery="+stateQuery, null, null, "500px");
}

function doEdit(id, applyid){
	var stateQuery = $("#stateQuery").val();
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/asset/assetAdmin-editApply.action?assetApply.id="+id+"&applyid="+applyid+"&stateQuery="+stateQuery, null, null, "500px");
}

function doSearch(){
	var stateQuery = $("#stateQuery").val();
	load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-apply.action?stateQuery="+stateQuery);
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
        <a href="javascript:void(0);" onclick="doAdd();" class="abtn-orange-new fn-right applyForBtn" style="">请购申请</a>
        <div class="fn-clear"></div>
    </div>
</div>
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="10%">请购单编号</th>
		<th width="9%">申请日期</th>
		<th width="10%">类别</th>
		<th width="9%">物品名称</th>
		<th width="7%">审核状态</th>
		<th width="11%">处室负责人</th>
		<th width="11%">分管校领导</th>
		<th width="11%">校长</th>
		<th width="11%">会议讨论</th>
		<th class="t-center" width="">操作</th>
	</tr>
	<#if applyList?exists && (applyList?size>0)>
		<#list applyList as ent>
		    <tr>
		    	<td>${ent.applyCode?default("")}</td>
				<td>${(ent.applyDate?string('yyyy-MM-dd'))?if_exists}</td>
				<td><@htmlmacro.cutOff4List str="${ent.categoryName?default('')}" length=6 />
				</td>
				<td><@htmlmacro.cutOff4List str="${ent.assetName?default('')}" length=6 />
				</td>
				<td><span class="c-blue">
					<#if ent.applyStatus?default("") == "2">通过<#elseif ent.applyStatus?default("") == "3">未通过<#else>待审核</#if>
					</span>
				</td>
				<td>
					<#if ent.deptState?default("") =="2" || ent.deptState?default("") =="4">
					<span class="c-blue">通过</span><@htmlmacro.cutOff4List str="${ent.deptOpinion?default('')}" length=5 />
					<#elseif ent.deptState?default("") =="3">
					<span class="c-blue">未通过</span><@htmlmacro.cutOff4List str="${ent.deptOpinion?default('')}" length=4 />
					<#elseif ent.deptState?default("") =="1">
					<span class="c-blue">待审核</span>
					</#if>
				</td>
				<td>
					<#if ent.assetLeaderState?default("") =="2" || ent.assetLeaderState?default("") =="4">
					<span class="c-blue">通过</span><@htmlmacro.cutOff4List str="${ent.assetLeaderOpinion?default('')}" length=5 />
					<#elseif ent.assetLeaderState?default("") =="3">
					<span class="c-blue">未通过</span><@htmlmacro.cutOff4List str="${ent.assetLeaderOpinion?default('')}" length=4 />
					<#elseif ent.assetLeaderState?default("") =="1">
					<span class="c-blue">待审核</span>
					</#if>
					
				</td>
				<td>
					<#if ent.schoolmasterState?default("") =="2" || ent.schoolmasterState?default("") =="4">
					<span class="c-blue">通过</span><@htmlmacro.cutOff4List str="${ent.schoolmasterOpinion?default('')}" length=5 />
					<#elseif ent.schoolmasterState?default("") =="3">
					<span class="c-blue">未通过</span><@htmlmacro.cutOff4List str="${ent.schoolmasterOpinion?default('')}" length=4 />
					<#elseif ent.schoolmasterState?default("") =="1">
					<span class="c-blue">待审核</span>
					</#if>
				</td>
				<td>
					<#if ent.meetingleaderState?default("") =="2" || ent.meetingleaderState?default("") =="4">
					<span class="c-blue">通过</span><@htmlmacro.cutOff4List str="${ent.meetingleaderOpinion?default('')}" length=5 />
					<#elseif ent.meetingleaderState?default("") =="3">
					<span class="c-blue">未通过</span><@htmlmacro.cutOff4List str="${ent.meetingleaderOpinion?default('')}" length=4 />
					<#elseif ent.meetingleaderState?default("") =="1">
					<span class="c-blue">待审核</span>
					</#if>
				</td>
				<td class="t-center">
					<#if ent.isOverMaxNum>
					<a href="javascript:doEdit('${ent.id!}','${ent.applyId!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a>
					<#else>
					<a href="javascript:doEdit('${ent.id!}','${ent.applyId!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="编辑"></a>
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
<div class="popUp-layer" id="classLayer" style="display:none;width:600px;"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>