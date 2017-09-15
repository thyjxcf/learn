<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/office/customer/customerCommon.ftl" as commonmacro1>
<@htmlmacro.moduleDiv titleName="">
<form id="editForm">
<input type="hidden" value="${searchCustomer.name!}" name="searchCustomer.name">
<input type="hidden" value="${searchCustomer.followerName!}" name="searchCustomer.followerName">
	
<input type="hidden" name="officeCustomerApply.id" value="${officeCustomerApply.id!}">
<div id="container">
	<p class="pt-10 pb-15"><span class="ui-tt">添加跟进记录</span></p>
	<ul class="ui-axis">
    	<li class="add">
        	<span class="icon-round2"></span>
            <div class="item f-13">
            	<i></i>
                <table class="table-noborder">
                	<#if !clientManager&& !deptLeader&& !regionLeader>
                	<tr>
                		<th width="100"><span class="fn-left ml-5 c-red">*</span>抄送：</th>
                		<td>
                			<@commonmacro1.selectCopyUser idObjectId="carbonCopyId" nameObjectId="carbonCopyName" >
			  	   			<input type="hidden"  id="carbonCopyId" name="officeRecord.carbonCopyId" value="${officeRecord.carbonCopyId?default('')}"> 
			  	   			<input type="text"  id="carbonCopyName" name="officeRecord.carbonCopyName" value="${officeRecord.carbonCopyName?default('')}" class="select_current02" style="width:190px;" readonly="readonly">
							</@commonmacro1.selectCopyUser>
                		</td>
                	</tr>
                	</#if>
                	<tr>
                		<th width="100"><span class="fn-left ml-5 c-red">*</span>跟进状态：</th>
                		<td>
                		 <@htmlmacro.select  style="width:200px;" valName="officeRecord.progressState" valId="type" >
							<#if recordList?exists && recordList?size gt 0><#list recordList as item>
                           		 ${appsetting.getMcode("DM-JZZT").getHtmlTag(officeRecord.progressState?default(item.progressState),false)}<#break>
                            </#list>
                            <#else>${appsetting.getMcode("DM-JZZT").getHtmlTag(officeRecord.progressState?default(''),false)}
                            </#if>
		 				 </@htmlmacro.select> 
                		</td>
                	</tr>
                	<tr>
                		<th valign="top" class="pt-5">跟进信息：</th>
                		<td valign="top" class="pt-5"><textarea name="officeRecord.remark" value="${officeRecord.remark!}" class="text-area" style="width: 70%;" maxlength="200"></textarea></td>
                	</tr>
                	<tr>
                		<th valign="top" class="pt-10">&nbsp;</th>
                		<td valign="top" class="pt-10">
                		<#if !clientManager&& !deptLeader&& !regionLeader>
                			<a href="javascript:void(0);" class="abtn-blue" onclick="addRecord(0);">确定</a>
            			<#else><a href="javascript:void(0);" class="abtn-blue" onclick="addRecord(1);">确定</a>
            			</#if>
                			<a class="abtn-blue" href="javascript:void(0);" onclick="goBack();">取消</a>
                		</td>
                	</tr>
                </table>
            </div>
        </li>
        <#if recordList?exists && recordList?size gt 0>
        <#list recordList as item>
    	<li >
        	<span class="icon-round1"></span>
            <div class="item f-13">
            	<i></i>
                <#if !clientManager&& !deptLeader&& !regionLeader><p class="li"><span class="time f-10">${(item.createTime?string('yyyy-MM-dd HH:mm'))?default('')}</span><#if item.carbonCopyName?exists><span class="dt">抄送：</span><span class="dd">${item.carbonCopyName!}</span></#if></p>
                <p class="li"><span class="dt">跟进状态：</span><span class="dd">${appsetting.getMcode("DM-JZZT").get(item.progressState?default(""))}</span></p>
                <#else><p class="li"><span class="time f-10">${(item.createTime?string('yyyy-MM-dd HH:mm'))?default('')}</span><span class="dt">跟进状态：</span><span class="dd">${appsetting.getMcode("DM-JZZT").get(item.progressState?default(""))}</span></p>
                </#if>
                <p class="li"><span class="dt">跟进信息：</span><span class="dd">${item.remark!}</span></p>
            </div>
        </li>
    	</#list>
    	</#if>
    </ul>
</div>
</form>
<script>
	var isSubmit = false;
	function addRecord(type){
		if(isSubmit){
			return;
		}
		if(type==0){
			if($("#carbonCopyId").val()==''){
				showMsgError("请选择抄送人！");
				return;
			}
		}
		if(!confirm("确认添加?")){
			return;
		}
		isSubmit = true;
		jQuery.ajax({
			url:"${request.contextPath}/office/customer/customer-addRecordIntoSQL.action",
			type:"post", 
			data:jQuery("#editForm").serialize(),
			dataType:"JSON",
			async:false,
			success:function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",function(){
						goBack();
					});			
				}else{
					showMsgError(data.errorMessage);
					isSubmit = false;
				}
			}
 		});	
	}
	function goBack(){
		load("#showListDiv","${request.contextPath}/office/customer/customer-myCustomerList.action?"+jQuery("#editForm").serialize());
	}
</script>
</@htmlmacro.moduleDiv>
