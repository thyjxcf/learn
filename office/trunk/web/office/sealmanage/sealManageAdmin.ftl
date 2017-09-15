<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script>

function doQueryChange(){
	var deptId=$("#currentDeptId").val();
	var sealType = $("#sealType").val();
	load("#sealManageListDiv", "${request.contextPath}/office/sealmanage/sealmanage-sealManageList.action?deptId="+deptId+"&sealType="+sealType);
}

$(function(){
	load("#sealManageListDiv", "${request.contextPath}/office/sealmanage/sealmanage-sealManageList.action");
});

</script>
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt ml-10"><span class="fn-left">部门：</span></div>
    	<@common.select style="width:120px;float:left;" valName="currentDeptId" valId="currentDeptId" myfunchange="doQueryChange">
					<a val="" >请选择</a>
					<#if deptList?exists && deptList?size gt 0>
                		<#list deptList as dept>
                			<a val="${dept.id!}" <#if deptId?default('') == dept.id>class="selected"</#if>>${dept.deptname!}</a>
                		</#list>
                	</#if>
		</@common.select>
				
		<div class="query-tt b ml-10"><span class="fn-left">印章类型：</span></div>
			<@common.select style="width:120px;float:left;" valName="sealType" valId="sealType" myfunchange="doQueryChange">
					<a val="" >请选择</a>
					<#if officeSealTypeList?exists && officeSealTypeList?size gt 0>
                		<#list officeSealTypeList as item>
		            		<a val="${item.typeId}" <#if officeSeal.sealType?default('') == item.typeId>class="selected"</#if>><span>${item.typeName}</span></a>
		            	</#list>
                	</#if>
			</@common.select>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查找</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="sealManageListDiv"></div>
<div class="popUp-layer" id="sealAddLayer" style="display:none;width:700px;"></div>