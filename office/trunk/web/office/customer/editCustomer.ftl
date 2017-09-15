<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "/office/customer/customerCommon.ftl" as commonmacro1>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/public.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-default.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/default.css">
<@htmlmacro.moduleDiv titleName="客户申请">
<form name="editForm" id="editForm" method="post" enctype="multipart/form-data">

<input type="hidden" value="${applyId!}" name="applyId">
<input type="hidden" value="${officeCustomerApply.unitId!}" name="officeCustomerApply.unitId">
<#if !clientManager>
	<input type="hidden" value="${officeCustomerApply.followerId!}" name="officeCustomerApply.followerId">
</#if>
<input type="hidden" value="${officeCustomerApply.id!}" name="officeCustomerApply.id">
<input type="hidden" value="${officeCustomerApply.state!}" name="officeCustomerApply.state">
<input type="hidden" value="${officeCustomerApply.isDeleted!}" name="officeCustomerApply.isDeleted">

<input type="hidden" value="${officeCustomerInfo.unitId!}" name="officeCustomerInfo.unitId">
<input type="hidden" id="customerId" value="${officeCustomerInfo.id!}" name="officeCustomerInfo.id">
<input type="hidden" value="${officeCustomerInfo.state!}" name="officeCustomerInfo.state">
<input type="hidden" value="${((officeCustomerInfo.createTime)?string('yyyy-MM-dd HH:mm:ss'))?default('')}" name="officeCustomerInfo.createTime">
<input type="hidden" value="${((officeCustomerInfo.addTime)?string('yyyy-MM-dd HH:mm:ss'))?default('')}" name="officeCustomerInfo.addTime">
	<#--
	<div class="query-builder ">
		<p class="pt-20">状态：<span class="c-red">初审不通过</span></p>
		<p class="pt-20 pb-10">原因：初审不通过的原因是资料不全，请补全资料再申请审核。</p>
	</div>-->
	<p class="table-dt">客户申请表</p>
	<@htmlmacro.tableDetail >
		<tr>
			<th width="18%"><span class="mr-5 c-red">*</span>客户单位官方名称：</th>
			<td width="32%"><input type="text" name="officeCustomerInfo.name" value="${officeCustomerInfo.name!}" notNull="true" class="input-txt fn-left" style="width:300px;" msgName="官方名称" maxlength="100"></td>
			<th width="18%"><span>客户单位地方名称：</span></th>
			<td width="32%"><input type="text" name="officeCustomerInfo.nickName" value="${officeCustomerInfo.nickName!}"  class="input-txt fn-left" style="width:300px;" msgName="地方名称" maxlength="100"></td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>客户所在区域：</span></th>
			<td>
				<@commonmacro.selectTree idObjectId="region" nameObjectId="regionName" treeUrl=request.contextPath+"/common/xtree/regionTree.action">
				<input name="officeCustomerInfo.regionName" id="regionName"  msgName="所在区域"  notNull="true" value="${officeCustomerInfo.regionName?default('')?trim}" readonly type="text" class="input-txt f-left input-readonly" style="width:300px;" />
				<input name="officeCustomerInfo.region" id="region" type="hidden" class="input" value="${officeCustomerInfo.region?default('')}"> 
				</@commonmacro.selectTree>
			</td>
			<th><span class="mr-5 c-red">*</span><span>客户类别：</span></th>
			<td>
				<span class="ui-radio <#if officeCustomerInfo.type?default('')=='01'>ui-radio-current</#if>"  data-name="a"><input <#if officeCustomerInfo.type?default('')=='01'>checked="checked"</#if> type="radio" class="radio" name="officeCustomerInfo.type" value="01">代理商</span>
				<span class="ui-radio <#if officeCustomerInfo.type?default('')=='02'>ui-radio-current</#if>" data-name="a"><input <#if officeCustomerInfo.type?default('')=='02'>checked="checked"</#if> type="radio" class="radio" name="officeCustomerInfo.type" value="02" >K12</span>
				<span class="ui-radio <#if officeCustomerInfo.type?default('')=='03'>ui-radio-current</#if>" data-name="a"><input <#if officeCustomerInfo.type?default('')=='03'>checked="checked"</#if> type="radio" class="radio" name="officeCustomerInfo.type" value="03" >教育局</span>
				<span class="ui-radio <#if officeCustomerInfo.type?default('')=='04'>ui-radio-current</#if>" data-name="a"><input <#if officeCustomerInfo.type?default('')=='04'>checked="checked"</#if> type="radio" class="radio" name="officeCustomerInfo.type" value="04" >培训机构</span>
				<span id="errorType" class="field_tip input-txt-warn-tip"></span>
			</td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>客户信息来源：</span></th>
			<td colspan="3"><input type="text" name="officeCustomerInfo.infoSource" value="${officeCustomerInfo.infoSource!}" notNull="true" class="input-txt fn-left" style="width:300px;" msgName="信息来源" maxlength="100"></td>
		</tr>
		<tr>
			<th valign="top" class="pt-5"><span class="mr-5 c-red">*</span><span>客户背景信息：</span></th>
			<td valign="top" class="pt-5" colspan="3"><textarea  name="officeCustomerInfo.backgroundInfo" class="text-area my-5" style="width:620px;height:40px;" msgName="背景信息" notNull="true" maxLength="200">${officeCustomerInfo.backgroundInfo!}</textarea></td>
		</tr>
		<tr>
			<th valign="top" class="pt-50"><span class="mr-5 c-red">*</span><span>进展情况：</span></th>
			<td valign="top" class="" colspan="3">
				<#if mcodedetailList?exists&& mcodedetailList?size gt 0>
				<ul class="ui-axis-x">
					<#list mcodedetailList as item><#--officeCustomerInfo.progressState?default('')!=null&&-->
					<li class="thisId <#if officeCustomerInfo.progressStateInt?default(0)!=0 &&officeCustomerInfo.progressStateInt?default(0) gt (item_index+1)>over<#elseif officeCustomerInfo.progressStateInt?default(0)==(item_index+1)> current </#if><#if (item_index+1)==mcodedetailList?size>last</#if>" id="${item.thisId!}">
						<p class="layer"><i></i><span>
							<#if item.thisId?default('')=='01'>了解客户基本意向
							<#elseif item.thisId?default('')=='02'>客户同意开设机构<#elseif item.thisId?default('')=='03'>客户已开通机构
							<#elseif item.thisId?default('')=='04'>客户已开设试听课程<#elseif item.thisId?default('')=='05'>未与用户签订合同
							<#elseif item.thisId?default('')=='06'>与用户签订合同<#elseif item.thisId?default('')=='07'>客户开设第一期正式课
							<#elseif item.thisId?default('')=='08'>客户在合同期内持续开正式课<#elseif item.thisId?default('')=='09'>合同期满，客户未续约
							</#if></span></p>
						<a href="#" class="round"></a>
						<p class="tt">${item.content!}</p>
					</li>
					</#list>
				</ul>
				<span id="errorPro" class="field_tip input-txt-warn-tip"></span>
				</#if>
			</td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>意向合作产品：</span></th>
			<td colspan="3">
				<span name="a" class="ui-checkbox <#if officeCustomerInfo.productArray?exists&& officeCustomerInfo.productArray?size gt 0><#list officeCustomerInfo.productArray as item><#if item?default('')=='01'>ui-checkbox-current</#if></#list></#if>">
				<input <#if officeCustomerInfo.productArray?exists&& officeCustomerInfo.productArray?size gt 0><#list officeCustomerInfo.productArray as item><#if item?default('')=='01'>checked="checked"</#if></#list></#if> type="checkbox" class="chk" value="01" name="officeCustomerInfo.product">课后网</span>
				<span name="a" class="ui-checkbox <#if officeCustomerInfo.productArray?exists&& officeCustomerInfo.productArray?size gt 0><#list officeCustomerInfo.productArray as item><#if item?default('')=='02'>ui-checkbox-current</#if></#list></#if>">
				<input <#if officeCustomerInfo.productArray?exists&& officeCustomerInfo.productArray?size gt 0><#list officeCustomerInfo.productArray as item><#if item?default('')=='02'>checked="checked"</#if></#list></#if> type="checkbox" class="chk" value="02" name="officeCustomerInfo.product">三通两平台</span>
				<span name="a" class="ui-checkbox <#if officeCustomerInfo.productArray?exists&& officeCustomerInfo.productArray?size gt 0><#list officeCustomerInfo.productArray as item><#if item?default('')=='03'>ui-checkbox-current</#if></#list></#if>">
				<input <#if officeCustomerInfo.productArray?exists&& officeCustomerInfo.productArray?size gt 0><#list officeCustomerInfo.productArray as item><#if item?default('')=='03'>checked="checked"</#if></#list></#if> type="checkbox" class="chk" value="03" name="officeCustomerInfo.product">微课掌上通</span>
				<span name="a" class="ui-checkbox <#if officeCustomerInfo.productArray?exists&& officeCustomerInfo.productArray?size gt 0><#list officeCustomerInfo.productArray as item><#if item?default('')=='04'>ui-checkbox-current</#if></#list></#if>">
				<input <#if officeCustomerInfo.productArray?exists&& officeCustomerInfo.productArray?size gt 0><#list officeCustomerInfo.productArray as item><#if item?default('')=='04'>checked="checked"</#if></#list></#if> type="checkbox" class="chk" value="04" name="officeCustomerInfo.product">企训平台</span>
				<span id="errorProduct" class="field_tip input-txt-warn-tip"></span>
			</td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>联系人：</span></th>
			<td colspan="3"><input type="text" name="officeCustomerInfo.contact" value="${officeCustomerInfo.contact!}" notNull="true" class="input-txt fn-left" style="width:300px;" msgName="联系人" maxlength="100"></td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>主要联系方式：</span></th>
			<td><input type="text" placeholder="请输入手机号或座机号" onchange="cusCheckPhone()" id="phone" name="officeCustomerInfo.phone" value="${officeCustomerInfo.phone!}" notNull="true" class="input-txt fn-left" style="width:300px;" msgName="主要联系方式" maxlength="30"> 
			<span id="error" class="field_tip input-txt-warn-tip"></td>
			<th>次要联系方式：</span></th>
			<td><input type="text" placeholder="请输入qq号或者邮箱" name="officeCustomerInfo.contactInfo" value="${officeCustomerInfo.contactInfo!}"  class="input-txt fn-left" style="width:300px;" msgName="次要联系方式" maxlength="100"></td>
		</tr>
		<tr>
		<#if clientManager>
			<tr>
				<th><span>跟进人：</span></th>
				<td colspan="3"><@commonmacro1.selectFollowUser idObjectId="followerId" nameObjectId="followerName" >
	  	   			<input type="hidden"  id="followerId" name="officeCustomerApply.followerId" value="${officeCustomerApply.followerId?default('')}"> 
	  	   			<input type="text"  id="followerName" name="officeCustomerApply.followerName" value="${officeCustomerApply.followerName?default('')}" class="select_current02" style="width:300px;" readonly="readonly">
					</@commonmacro1.selectFollowUser>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="pt-20 pl-50 t-center">
					<a class="abtn-blue-big" href="javascript:void(0);" onclick="addCustomer() ">确定</a>
	   		 	    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="back(1);">取消</a>
		 		</td>
	 		</tr>
		<#else>
			<tr>
				<td colspan="4" class="pt-20 pl-50 t-center">
					<a class="abtn-blue-big" href="javascript:void(0);" onclick="doSave(0) ">保存</a>
		    		<a class="abtn-blue-big" href="javascript:void(0);" onclick="doSave(1)">提交审核</a>
		   		 	<a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="back(0);">取消</a>
				</td>
			</tr>
		</#if>
		</tr>
	</@htmlmacro.tableDetail>
</form>
<script>
	$(function(){
		$('.ui-axis-x li .round').click(function(e){
			e.preventDefault();
			var $li=$(this).parent('li');
			var myIndex=$li.index();
			$li.addClass('current').siblings('li').removeClass('current');
			$('.ui-axis-x li:lt('+myIndex+')').addClass('over');
			$('.ui-axis-x li:gt('+myIndex+')').removeClass('over');
		});
	});
	var isSubmit = false;
	function addCustomer(){
		if(isSubmit){
			return;
		}
		if(!checkAll()){
			return;
		}
		clearMessages();
		if(!confirm("确认添加?")){
			return;
		}
		var progressState=$(".thisId.current").attr("id");
		isSubmit = true;
		jQuery.ajax({
			url:"${request.contextPath}/office/customer/customer-addCustomerNoAudit.action?officeCustomerInfo.progressState="+progressState,
			type:"post", 
			data:jQuery("#editForm").serialize(),
			dataType:"JSON",
			async:false,
			success:function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",function(){
						back(1);
					});			
				}else{
					showMsgError(data.errorMessage);
					isSubmit = false;
				}
			}
 		});	
	}
	function doSave(auditState){
		if(isSubmit){
			return;
		}
		if(!checkAll()){
			return;
		}
		clearMessages();
		if(auditState==1){
			if(!confirm("确认提交?")){
				return;
			}
		}
		var progressState=$(".thisId.current").attr("id");
		isSubmit = true;
		jQuery.ajax({
			url:"${request.contextPath}/office/customer/customer-save.action?officeCustomerInfo.auditState="+auditState+"&officeCustomerInfo.progressState="+progressState,
			type:"post", 
			data:jQuery("#editForm").serialize(),
			dataType:"JSON",
			async:false,
			success:function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",function(){
						back(0);
					});			
				}else{
					showMsgError(data.errorMessage);
					isSubmit = false;
				}
			}
 		});	
	}
	function checkAll(){
		if(!checkAllValidate("#editForm")){
			return false;
		}
		if(!checkProcut()){
			return false;
		}
		if(!checkType()){
			return false;
		}
		if(!checkPro()){
			return false;
		}
		//手机号码验证
		var phone = document.getElementById("phone");
		if(!checkMobilePhone(phone)){
			return false;
		}
		return true;
	}
	function cusCheckPhone(){
		var phone = document.getElementById("phone");
		if(!checkMobilePhone(phone)){
			return false;
		}else{
			$("#phone").css("border-color","#a0b2be");
			$("#error").text("");
			return true;
		}
	}
	function checkProcut(){
		$("#errorProduct").text("");
		if($(".ui-checkbox.ui-checkbox-current").attr("name")==undefined){
			$("#errorProduct").text("意向合作产品 不能为空！");
			return false;
		}
		$("#errorProduct").text("");
		return true;
	}
	function checkType(){
		$("#errorType").text("");
		if($(".ui-radio.ui-radio-current").attr("data-name")==undefined){
			$("#errorType").text("客户类别 不能为空！");
			return false;
		}
		$("#errorType").text("");
		return true;
	}
	function checkPro(){
		$("#errorPro").text("");
		if($(".thisId.current").attr("id")==undefined){
			$("#errorPro").text("进展情况 不能为空！");
			return false;
		}
		$("#errorPro").text("");
		return true;
	}
	function back(type){
		if(type==0){
			load("#showListDiv","${request.contextPath}/office/customer/customer-applyList.action");
		}else if(type==1){
			load("#showListDiv","${request.contextPath}/office/customer/customer-getAllList.action");
		}
	}	
	function changeCountry(){
		var countyVaId=$("#countyVaId").val();
		if(countyVaId!=""){
			$("#countyVaId").css("border-color","#a0b2be");
		}
	}
	function changeProvince(){
		var provinceId=$("#provinceId").val();
		getRegionCode(provinceId,1);
	}
	function changeCity(){
		var cityValId=$("#cityValId").val();
		if(cityValId!=""){
			$("#cityValId").css("border-color","#a0b2be");
		}
		getRegionCode(cityValId,2);
	}
</script>
</@htmlmacro.moduleDiv >