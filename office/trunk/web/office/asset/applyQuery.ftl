<#import "/common/htmlcomponent.ftl" as common />
<script>

function doQueryChange(){
	var stateQuery = $("#stateQuery").val();
	var searchName = $("#searchName").val();
	var currentDeptId = $("#currentDeptId").val();
	load("#assetQueryList", "${request.contextPath}/office/asset/assetAdmin-applyQueryList.action?currentDeptId="+currentDeptId+"&stateQuery="+stateQuery+"&searchName="+encodeURIComponent(searchName));
}

$(function(){
	load("#assetQueryList", "${request.contextPath}/office/asset/assetAdmin-applyQueryList.action");
});



</script>
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt b ml-10">审核状态：</div>
		<@common.select style="width:120px;" valName="stateQuery" valId="stateQuery" myfunchange="doQueryChange">
			<a val=""><span>全部</span></a>
		    <a val="1"  <#if stateQuery?default("")=="1">class="selected"</#if>><span>待审核</span></a>
		    <a val="2"  <#if stateQuery?default("")=="2">class="selected"</#if>><span>通过</span></a>
		    <a val="3"  <#if stateQuery?default("")=="3">class="selected"</#if>><span>未通过</span></a>
		</@common.select>
    	<div class="query-tt b ml-10"><span class="fn-left">部门：</span></div>
    	<@common.select style="width:120px;float:left;" valName="currentDeptId" valId="currentDeptId" myfunchange="doQueryChange">
			<a val="" >请选择</a>
			<#if deptList?exists && deptList?size gt 0>
            	<#list deptList as dept>
            		<a val="${dept.id!}" <#if deptId?default('') == dept.id>class="selected"</#if>>${dept.deptname!}</a>
            	</#list>
            </#if>
		</@common.select>
  
  		<div class="query-tt b ml-10"><span class="fn-left" style="margin-left:10px;">姓名：</span></div>
		    <div class="fn-left">
				<input class="input-txt" style="width:100px;" id="searchName" name="searchName" value="${searchName!}">
			</div>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查找</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="assetQueryList"></div>