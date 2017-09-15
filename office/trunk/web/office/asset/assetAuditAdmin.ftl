<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doSearch(){
	var roleCode = $("#roleCode").val();
	var stateQuery = $("#stateQuery").val();
	load("#assetAuditListDiv", "${request.contextPath}/office/asset/assetAdmin-auditList.action?roleCode="+roleCode+"&stateQuery="+stateQuery);
}
</script>
<div id="stuSusAdminDiv">
<div class="query-builder-nobg">
    	<div class="query-part">
    	<div class="query-tt b">审核角色：</div>
		<@htmlmacro.select style="width:120px;" valName="roleCode" valId="roleCode" myfunchange="doSearch">
			<#list roleList as role>
            	<a val="${role.roleCode!}"  <#if roleCode?default("")==role.roleCode>class="selected"</#if>><span>${role.roleName?default("")}</span></a>
            </#list>
		</@htmlmacro.select>
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
<div id="assetAuditListDiv"></div>
</div>
<script>
vselect();
$(function(){
	doSearch();
});
</script>
</@htmlmacro.moduleDiv>