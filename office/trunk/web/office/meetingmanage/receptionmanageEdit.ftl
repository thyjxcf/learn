<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="接待管理维护">
<script type="text/javascript">


</script>
<div id="receptionmanageContainer">
<form name="receptionmanageForm" id="receptionmanageForm" method="POST" action="">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>接待管理维护</span></p>
<div class="wrap pa-10" id="contentDiv">
<table border="0" cellspacing="0" cellpadding="0" class="table-form">
	<input type="hidden"  name="reception.id" id="reception.id" value="${id?default('')}">
    <input type="hidden"  name="reception.createTime" value="${reception.createTime?default('')}">
    <input type="hidden"  name="reception.unitId" value="${reception.unitId?default('')}">
    <input type="hidden"  name="reception.createUserId" value="${reception.createUserId?default('')}">
	
    <tr>
		 <th width="20%"><span class="c-orange mr-10">*</span>接待时间：</th>
      <td width="30%">
      	<@common.datepicker name="reception.receptionDate" value="${(reception.receptionDate?string('yyyy-MM-dd'))?if_exists}" notNull="true" msgName="接待时间" class="input-txt input-readonly" style="width:140px;"/>
   
     </td>
		<th width="20%"><span class="c-orange mr-10">*</span>准备工作负责人：</th>
		<td width="30%"> <@commonmacro.selectOneUser idObjectId="startWorkUserId" nameObjectId="startWorkUserName" width=500>
			<input id="startWorkUserName" value="${reception.startWorkUserName?default('')}"   class="select_current02"  style="width:140px;" readonly="readonly" msgName="准备工作负责人" notNull="true">
            <input id="startWorkUserId" name="reception.startWorkUserId"   value="${reception.startWorkUserId?default('')}" type="hidden" >
            </@commonmacro.selectOneUser>
        </td>
	</tr>
	

	 <tr>
		<th><span class="c-orange mr-10">*</span>是否用餐：</th>
		<td><@common.select style="width:150px;" valId="valId" valName="reception.isDining" txtId="txtId" notNull="true" msgName="是否用餐" myfunchange="hehe">
						<a val="">-请选择-</a>
						<a val="true" <#if  reception.isDining?default(false)> class="selected" </#if>>是</a>
						<a val="false" <#if !reception.isDining?default(true)> class="selected" </#if>>否</a>
		    </@common.select>
		</td>  
		    <th><span class="c-orange mr-10">*</span>是否住宿：</th>
		<td><@common.select style="width:150px;" valId="" valName="reception.isAcommodation" txtId="" notNull="true" msgName="是否住宿">
						<a val="">-请选择-</a>
						<a val="true" <#if reception.isAcommodation?default(false)> class="selected" </#if>>是</a>
						<a val="false" <#if !reception.isAcommodation?default(true)> class="selected" </#if>>否</a>
		    </@common.select>
		 </td>
		 </td>
	</tr>
	
	<tr id="ycy">
	<th><span class="c-orange mr-10">*</span>用餐标准：</th>
		<td><input type="text" name="reception.standard" id="standard" value="${reception.standard?default('')}" maxLength="50" notNull="true"  msgName="用餐标准" class="input-txt" style="width:140px;"></td>
		<th><span class="c-orange mr-10">*</span>用餐人数：</th>
		<td><input type="text" regex="/^\+?[1-9]\d*$/" regexMsg="只能输入大于0的正整数"  name="reception.personNumber" id="personNumber" value="${reception.personNumber?default('')}" maxLength="5" notNull="true"  msgName="用餐人数" class="input-txt" style="width:140px;"></td>
	</tr>
	 <tr>
		<th>接待人：</th>
		<td>
			 <@commonmacro.selectOneUser idObjectId="receptionUserId" nameObjectId="receptionUserName" width=500>
			<input id="receptionUserName" value="${reception.receptionUserName?default('')}"   class="select_current02"  style="width:140px;" readonly="readonly" msgName="接待人">
            <input id="receptionUserId" name="reception.receptionUserId"   value="${reception.receptionUserId?default('')}" type="hidden" >
            </@commonmacro.selectOneUser>
		</td>
		<th>陪同人：</th>
		<td>
		 <@commonmacro.selectOneUser idObjectId="accompanyPerson" nameObjectId="accompanyPersonName" width=500>
			<input id="accompanyPersonName" value="${reception.accompanyPersonName?default('')}"   class="select_current02"  style="width:140px;" readonly="readonly" msgName="陪同人">
            <input id="accompanyPerson" name="reception.accompanyPerson"   value="${reception.accompanyPerson?default('')}" type="hidden" >
            </@commonmacro.selectOneUser>
		
		</td>
	</tr>
	
	<tr>
		<th>拍照摄影人员：</th>
		<td colspan="3">
		<@commonmacro.selectOneUser idObjectId="camemaPerson" nameObjectId="camemaPersonName" width=500>
			<input id="camemaPersonName" value="${reception.camemaPersonName?default('')}"   class="select_current02"  style="width:140px;" readonly="readonly" msgName="拍照摄影人员">
            <input id="camemaPerson" name="reception.camemaPerson"   value="${reception.camemaPerson?default('')}" type="hidden" >
         </@commonmacro.selectOneUser>
		</td>
	</tr>
	
	<tr>
	<th>用车情况：</th>
	  	<td colspan="3"><textarea class="text-area my-5"  name="reception.carSituation" id="carSituation"  maxLength="200" notNull="false"  msgName="用车情况" style="width:80%;padding:5px 1%;height:50px;">${reception.carSituation?default('')}</textarea></td>
	</tr>
	
	<tr>
	 <th>内容：</th>
	  <td colspan="3"><textarea style="width:80%;padding:5px 1%;height:50px;" name="reception.content" id=""  maxLength="200" notNull="false"  msgName="内容" class="text-area my-5">${reception.content?default('')}</textarea></td>
	 </tr>
	 
	 <tr> 
	<th>接待地点：</th>
		<td colspan="3"><textarea  name="reception.place" id="buildSpecSubjName"  maxLength="200" notNull="false"  msgName="接待地点" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;">${reception.place?default('')}</textarea></td>
	</tr>
</table>
</div>
	<p class="dd">
	    <a class="abtn-blue doSave" href="javascript:void(0);" onclick="doSave();" id="btnSave">保存</a>
	    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
	</p>
<div class="popUp-layer" id="addDiv" style="display:none;width:380px;"></div>
</form>	
</div>

<script type="text/javascript">
function hehe(){
	var ss= jQuery("#valId").val();
	if(ss=="true"){
	    jQuery("#personNumber").attr("notNull","true");
 	    jQuery("#standard").attr("notNull","true");
 		jQuery("#ycy").show();
 		return;
 				}
 	jQuery("#ycy").hide();
 	jQuery("#personNumber").attr("notNull","false");
 	jQuery("#standard").attr("notNull","false");
 	}

function doSave(){
	if(!isActionable("#btnSave")){
		return false;
	}
	if(!checkAllValidate("#receptionmanageContainer")){
		return;
	}
	$("#btnSave").attr("class", "abtn-unable");	
	var options = {
	       url:'${request.contextPath}/office/meetingmanage/receptionmanage-receptionManageSave.action', 
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       success : showReply
	};
    $('#receptionmanageForm').ajaxSubmit(options);
}
function showReply(data){
	if(!data.operateSuccess){
		showMsgError(data.promptMessage);
		$("#btnSave").attr("class", "abtn-blue");
		return;
	}
	else{
		showMsgSuccess("保存成功!", "提示", function(){
			$("#btnSave").attr("class", "abtn-blue-big");
			load("#contectDiv","${request.contextPath}/office/meetingmanage/receptionmanage-receptionManageList.action");
		});
		return;
	}
}
</script>
<script>
 vselect();
</script>
</@common.moduleDiv>