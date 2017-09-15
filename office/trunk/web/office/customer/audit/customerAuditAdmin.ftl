<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/office/customer/customerCommon.ftl" as commonmacro1>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doSearch(){
	//var roleCode = $("#roleCode").val();
	//var stateQuery = $("#stateQuery").val();
	//load("#auditListDiv", "${request.contextPath}/office/customer/customer-auditList.action?roleCode="+roleCode+"&stateQuery="+stateQuery+"&applyUserName="+applyUserName);
	
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgError("申请时间开始时间不能大于结束时间，请修改！");
			return;
		}
	}
	load("#auditListDiv", "${request.contextPath}/office/customer/customer-auditList.action?"+jQuery("#auditForm").serialize())
	;
}
</script>
<div id="customerAdminDiv">
<form id="auditForm">
<div class="query-builder-nobg">
    	<div class="query-part">
	    	<div class="query-tt ml-10">
	    		<span class="fn-left">审核角色：</span>
	    	</div>
			<@htmlmacro.select style="width:120px;" valName="roleCode" valId="roleCode" myfunchange="doSearch">
				<#list roleList as role>
	            	<a val="${role.roleCode!}"  <#if roleCode?default("")==role.roleCode>class="selected"</#if>><span>${role.roleName?default("")}</span></a>
	            </#list>
			</@htmlmacro.select>
			<div class="query-tt ml-10">
				<span class="fn-left">申请人：</span>
			</div>
			<div class="fn-left">
				<input style="width:100px;" class="input-txt"  type="text" id="applyUserName" name="searchCustomer.applyUserName" value="${searchCustomer.applyUserName!}">
			</div>
			<div class="query-tt ml-10">
				<span class="fn-left">客户名称：</span>
			</div>
			<div class="fn-left">
				<input style="width:100px;" class="input-txt"  type="text" id="name" name="searchCustomer.name" value="${searchCustomer.name!}">
			</div>
		</div>
		
		<div class="fn-clear"></div>
		<div class="query-part">
			<div class="query-tt ml-10">
	    		<span class="fn-left">审核状态：</span>
	    	</div>
			<@htmlmacro.select style="width:120px;" valName="stateQuery" valId="stateQuery" myfunchange="doSearch">
				<a val=""><span>全部</span></a>
				<a val="5"  <#if stateQuery?default("")=="5">class="selected"</#if>><span>待初审</span></a>
				<a val="6"  <#if stateQuery?default("")=="6">class="selected"</#if>><span>待复审</span></a>
				<a val="9"  <#if stateQuery?default("")=="9">class="selected"</#if>><span>复审通过</span></a>
				<a val="7"  <#if stateQuery?default("")=="7">class="selected"</#if>><span>初审未通过</span></a>
				<a val="8"  <#if stateQuery?default("")=="8">class="selected"</#if>><span>复审未通过</span></a>
			</@htmlmacro.select>
			<div class="query-tt ml-10">
				<span class="fn-left">申请时间：</span>
			</div>
			<div class="fn-left">
			<@htmlmacro.datepicker class="input-txt" style="width:100px;" name="searchCustomer.startTime" id="startTime" value="${((searchCustomer.startTime)?string('yyyy-MM-dd'))?if_exists}"/>
			</div>
			<span class="fn-left">&nbsp;-&nbsp;</span>
			<div class="fn-left">
			<@htmlmacro.datepicker class="input-txt" style="width:100px;" name="searchCustomer.endTime" id="endTime" value="${((searchCustomer.endTime)?string('yyyy-MM-dd'))?if_exists}"/>
			</div>
		</div>
		<a href="javascript:void(0)" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
		<div class="fn-clear"></div>
</div>
</form>
<div id="auditListDiv"></div>
</div>
<script>
vselect();
$(function(){
	doSearch();
});
</script>
</@htmlmacro.moduleDiv>