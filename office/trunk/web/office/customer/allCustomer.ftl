<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<form id="queryForm">
<div class="query-builder-nobg">

    	<div class="query-part">
			<div class="query-tt ml-10">
				<span class="fn-left">客户名称：</span>
			</div>
			<div class="fn-left">
				<input style="width:200px;" class="input-txt"  type="text" id="searchCustomer.name" name="searchCustomer.name" value="${searchCustomer.name!}">
			</div>
			
			<div class="query-tt ml-10">
				<span class="fn-left">跟进人：</span>
			</div>
			<div class="fn-left">
				<input style="width:100px;" class="input-txt"  type="text" id="searchCustomer.followerName" name="searchCustomer.followerName" value="${searchCustomer.followerName!}">
			</div>
			  <div class="query-tt ml-30">所在区域：</div>
			<table width="180px" class="fn-left">
				<td width="100%">
					<@commonmacro.selectTree idObjectId="region" nameObjectId="regionName" treeUrl=request.contextPath+"/common/xtree/regionTree.action" width="400">
								<input name="searchCustomer.regionName" id="regionName"  msgName="户口所在地"  notNull="true" value="${searchCustomer.regionName?default('')?trim}" readonly type="text" class="input-txt f-left input-readonly" style="width:100%;" />
								<input name="searchCustomer.region" id="region" type="hidden" class="input" value="${searchCustomer.region?default('')}"> 
					</@commonmacro.selectTree>
				</td>
			</table>
		</div>
		<div class="fn-clear"></div>
		<div class="query-part">	
			<div class="query-tt ml-10">客户类别：</div>
	          <@htmlmacro.select myfunchange="doSearch" style="width:100px;" valName="searchCustomer.type" valId="type" >
	          			<a val="" ><span>全部</span></a>
						${appsetting.getMcode("DM-OAKHLB").getHtmlTag(searchCustomer.type?default(''),false)}
			  </@htmlmacro.select> 
				<#if clientManager>
			    	<div class="query-tt ml-10">所属部门：</div>
		            <@htmlmacro.select myfunchange="doSearch" curVal="${searchCustomer.deptId!}" valName="searchCustomer.deptId"  style="width:85px;">
		            	<a value="">全部</a>
						<#if (deptList?exists)&&(deptList?size>0)>
							<#list deptList as dept>
							<a val="${dept.id!''}" <#if (searchCustomer.deptId?default('')==(dept.id?default('')))>class="selected"</#if>><span>${dept.deptname!''}</span></a>
							</#list>
						</#if>
		            </@htmlmacro.select>
		    	</#if>
			    <div class="query-tt ml-10">
					<span class="fn-left">截止日期：</span>
				</div>
				<div class="fn-left">
				<@htmlmacro.datepicker class="input-txt" style="width:100px;" name="searchCustomer.startTime" id="startTime" value="${((searchCustomer.startTime)?string('yyyy-MM-dd'))?if_exists}"/>
				</div>
				<span class="fn-left">&nbsp;-&nbsp;</span>
				<div class="fn-left">
				<@htmlmacro.datepicker class="input-txt" style="width:100px;" name="searchCustomer.endTime" id="endTime" value="${((searchCustomer.endTime)?string('yyyy-MM-dd'))?if_exists}"/>
				</div>
		    <a href="javascript:void(0)" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
		    <a href="javascript:void(0)" onclick="doExport();" class="abtn-blue fn-left ml-20">导出</a>
		</div>
		    <#if clientManager>
		    <div class="fn-right ml-10">
		    	<a href="javascript:doAdd()" class="abtn-orange-new fn-right" >新增客户</a>
		    </div>
		    </#if>
			<div class="fn-clear"></div>
</div>
</form>
<@htmlmacro.tableList class="public-table table-list table-list-edit">
  	<tr>
  		<th width="5%">序号</th>
  		<#if clientManager>
	    	<th width="15%">客户名称</th>
	    	<th width="10%">所在区域</th>
	    	<th width="8%">客户类别</th>
	    	<th width="10%">状态</th>
	    	<th width="8%">跟进人</th>
	    	<th width="10%">所属部门</th>
	    	<th width="5%">有效期</th>
	    	<th width="10%">截止日期</th>
	    	<th width="7%">是否延期</th>
	    	<#else>
	    	<th width="15%">客户名称</th><th width="13%">所在区域</th><th width="10%">客户类别</th><th width="10%">状态</th>
	    	<th width="10%">跟进人</th><th width="5%">有效期</th><th width="10%">截止日期</th><th width="7%">是否延期</th>
    	</#if>
    	<th class="t-center">操作</th>
    </tr>
    <#if customerApplyList?exists && customerApplyList?size gt 0>
    	<#list customerApplyList as item>
    		<tr>	
    			<td> ${item_index+1}</td>    		
    			<td style="word-break:break-all; word-wrap:break-word;"> 
    				<a href="javascript:void(0)" onClick = "goDetailRecord('${item.id!}');" class="ml-5" > ${item.officeCustomerInfo.name!}</a>	
    			</td>   
				<td>${item.officeCustomerInfo.regionName!}</td>
    			<td>${appsetting.getMcode("DM-OAKHLB").get(item.officeCustomerInfo.type?default(""))}</td>
    			<td>${appsetting.getMcode("DM-JZZT").get(item.officeCustomerInfo.progressState?default(""))}</td>
				<td>${item.followerName!}</td>
				<#if clientManager><td>${item.deptName!}</td></#if> 		
			    <#if item.officeCustomerInfo.progressState?default("")=='06'||item.officeCustomerInfo.progressState?default("")=='07'||item.officeCustomerInfo.progressState?default("")=='08'><td>不限</td>   		
    				<td>----</td> 
    			<#else><td><#if item.validTime<=5><span class="mr-5 c-red">${item.validTime!}</span><#else>${item.validTime!}</#if></td>   		
    				   <td>${((item.deadline)?string('yyyy-MM-dd'))?if_exists}</td> 
    			</#if>  		
    			<td><#if item.applyType=="2">是<#else>否</#if></td>   		
    			<td style="word-break:break-all; word-wrap:break-word;" class="t-center">
					<a href="javascript:void(0)" onClick = "goDetailRecord('${item.id!}');" class="ml-5" >查看</a>	    				
					<a href="javascript:void(0)" onClick = "editTrans('${item.id!}','trans');" class="ml-5" >分派客户</a>    				
    			 </td>    		
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan=<#if clientManager>'11'<#else>'10'</#if>><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if customerApplyList?exists     && customerApplyList?size gt 0>
	<@htmlmacro.Toolbar container="#showListDiv">
	</@htmlmacro.Toolbar>
</#if> 
<script>
	function goDetailRecord(id){
		load("#showListDiv","${request.contextPath}/office/customer/customer-goDetailRecord.action?officeCustomerApply.id="+id+"&placeType=allCustomer"+"&"+jQuery("#queryForm").serialize());
	}
	function editTrans(id,transType){
		load("#showListDiv","${request.contextPath}/office/customer/customer-editMyCustomer.action?officeCustomerApply.id="+id+"&transType="+transType+"&placeType=allCustomer"+"&"+jQuery("#queryForm").serialize());
	}
	function doExport(){
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		if(startTime!=''&&endTime!=''){
			var re = compareDate(startTime,endTime);
			if(re==1){
				showMsgError("截止日期开始时间不能大于结束时间，请修改！");
				return;
			}
		}
		location.href = "${request.contextPath}/office/customer/customer-export.action?"+jQuery("#queryForm").serialize();
	}
	function doSearch(){
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		if(startTime!=''&&endTime!=''){
			var re = compareDate(startTime,endTime);
			if(re==1){
				showMsgError("截止日期开始时间不能大于结束时间有误，请修改！");
				return;
			}
		}
		load("#showListDiv","${request.contextPath}/office/customer/customer-getAllList.action?"+jQuery("#queryForm").serialize());
	}
	function doAdd(){
		load("#showListDiv","${request.contextPath}/office/customer/customer-addCustomer.action?"+jQuery("#queryForm").serialize());
	}
</script>
</@htmlmacro.moduleDiv>