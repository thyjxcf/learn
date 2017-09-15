<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="车辆申请">
<div id="cursorLayer" style="display:none;position:absolute;top:100px;left:100px;width:500px;z-index:9999;background:#FFFFFF;border:1px solid #000;"></div>
<div id="carApplyEditContainer">
<form name="carApplyForm" id="carApplyForm">
<@htmlmacro.tableDetail divClass="table-form">
    <input type="hidden" name="officeCarApply.id" id="id" value="${officeCarApply.id!}"/>
    <input type="hidden" name="officeCarApply.state" id="state" value="${officeCarApply.state!}"/>
    <input type="hidden" name="officeCarApply.applyUserId" id="state" value="${officeCarApply.applyUserId!}"/>
    
    <tr>
        <th colspan="4" style="text-align:center;">车辆申请</th>
    </tr>
    <tr>
    	<th style="width:20%"><span class="c-orange mr-5">*</span>乘车联系人：</th>
        <td style="width:30%">
        	<@commonmacro.selectOneUser idObjectId="linkUserId" nameObjectId="linkUserName" width="600" callback="setPhoneNumber">
			<input id="linkUserName" value="${officeCarApply.linkUserName?default('')}"   class="select_current02"  style="width:140px;" readonly="readonly" notNull="true" msgName="乘车联系人">
            <input id="linkUserId" name="officeCarApply.linkUserId"   value="${officeCarApply.linkUserId?default('')}" type="hidden" >
            </@commonmacro.selectOneUser>
        </td>
        <th style="width:20%"><span class="c-orange mr-5">*</span>联系电话：</th>
        <td style="width:30%">
        	<input name="officeCarApply.mobilePhone" id="mobilePhone" type="text" class="input-txt" style="width:140px;" regex="/^[0-9]{1,20}$/" regexMsg="请输入正确的联系电话,并且不能超过20个数字" maxlength="20" value="${officeCarApply.mobilePhone!}" msgName="联系电话" notNull="true"/>
        </td>
    </tr>
    <tr>
        <th><span class="c-orange mr-5">*</span>乘车人数：</th>
        <td>
        	<input name="officeCarApply.personNumber" id="personNumber" type="text" class="input-txt" style="width:140px;" dataType="integer" maxlength="4" value="${officeCarApply.personNumber!}" msgName="乘车人数" notNull="true"/>
        </td>
        <th><span class="c-orange mr-5">*</span>用车时间：</th>
        <td>
           <@htmlmacro.datepicker class="input-txt" style="width:140px;" name="officeCarApply.useTime" id="useTime" value="${(officeCarApply.useTime?string('yyyy-MM-dd HH:mm:ss'))!}" size="20" maxlength="19" msgName="用车时间" notNull="true" dateFmt="yyyy-MM-dd HH:mm:00"/>
        </td>
    </tr>
    <tr>
        <th><span class="c-orange mr-5">*</span>目的地：</th>
        <td>
        	<input name="officeCarApply.carLocation" id="carLocation" type="text" class="input-txt" style="width:140px;" maxlength="100" value="${officeCarApply.carLocation!}" msgName="目的地"  notNull="true"/>
        </td>
        <th><span class="c-orange mr-5">*</span>是否往返：</th>
        <td>
        	<@htmlmacro.select style="width:150px;" valName="officeCarApply.isGoback" valId="isGoback" notNull="true" myfunchange="doChangeGoBack">
				<a val="true" <#if officeCarApply.isGoback?default(false)> class="selected"</#if>><span>是</span></a>
                <a val="false" <#if !officeCarApply.isGoback?default(false)> class="selected"</#if>><span>否</span></a>
			</@htmlmacro.select>
        </td>
    </tr>
    <tr id="waitingCarDiv" style="display:none;">
        <th><span class="c-orange mr-5">*</span>是否需要候车：</th>
        <td colspan="3">
        	<@htmlmacro.select style="width:150px;" valName="officeCarApply.isNeedWaiting" valId="isNeedWaiting" myfunchange="doChangeNeedWaiting">
				<a val="true" <#if officeCarApply.isNeedWaiting?default(false)> class="selected"</#if>><span>是</span></a>
                <a val="false" <#if !officeCarApply.isNeedWaiting?default(false)> class="selected"</#if>><span>否</span></a>
			</@htmlmacro.select>
        </td>
    </tr>
     <tr id="waitingCarTimeDiv" style="display:none;">
        <th><span class="c-orange mr-5">*</span>接返时间：</th>
        <td colspan="3">
           <@htmlmacro.datepicker class="input-txt" style="width:140px;" name="officeCarApply.waitingTime" id="waitingTime" value="${(officeCarApply.waitingTime?string('yyyy-MM-dd HH:mm:ss'))!}" maxlength="19" size="20" msgName="接返时间" dateFmt="yyyy-MM-dd HH:mm:00"/>
        </td>
    </tr>
    <tr>
        <th>意向车辆：</th>
        <td colspan="3">
        	<@htmlmacro.select style="width:150px;" valName="carIds" valId="carIds" txtId="carTxtId">
				<a val="">请选择</a>
				<#if officeCarInfos?exists && officeCarInfos?size gt 0>
            		<#list officeCarInfos as x>
            			<a val="${x.id!}" <#if officeCarApply.carId?default('')==x.id> class="selected"</#if> onmouseover="mouseOver(event,'${x.id!}');" onmouseout="mouseOut();" title="${x.carNumber!}">${x.carNumber!}</a>
            		</#list>
            	</#if>
			</@htmlmacro.select>
        </td>
     </tr>
    <tr>
        <th><span class="c-orange mr-5">*</span>用车事由：</th>
        <td colspan="3">
        	<textarea name="officeCarApply.reason" id="reason" cols="70" rows="4" notNull="true" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="用车事由" maxLength="200">${officeCarApply.reason!}</textarea>
        </td>
    </tr>
    <tr>
    	<td colspan="4" class="td-opt">
    	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="save(1)">保存</a>
    	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="save(2)">提交</a>
		    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="back();">返回</a>
        </td>
    </tr>
</@htmlmacro.tableDetail>
</form>
</div>

<script type="text/javascript">
function setPhoneNumber(){
	var linkUserId = $("#linkUserId").val();
    if(linkUserId == "") return;
    $.getJSON("${request.contextPath}/office/carmanage/carmanage-carApplyGetPhoneNumber.action", {
		"jsonString":linkUserId
		}, function(data){
			if (!data.operateSuccess){
				if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			    }
			}else{
				if(data.promptMessage != null && data.promptMessage != ""){
					$("#mobilePhone").val(data.promptMessage);
				}
				return;
			}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(textStatus);alert(XMLHttpRequest.status);
	});
}

var isSubmit = false;
function save(state){
	if(isSubmit){
		return;
	}
	$("#state").val(state);
	if(!checkAllValidate("#carApplyEditContainer")){
		return;
	}
	var isGoback = $("#isGoback").val();
  	if('false'==isGoback){
  		$("#isNeedWaiting").val("false")
  	}
	var isNeedWaiting = $("#isNeedWaiting").val();
	if('true'==isNeedWaiting){
		var waitingTime = $("#waitingTime").val();
		if(waitingTime == null ||　waitingTime == ''){
			showMsgWarn("接返时间不能为空！");
			return;
		}
	}
	isSubmit = true;
	var carInfoUrl = "${request.contextPath}/office/carmanage/carmanage-saveCarApply.action";
	var options = {
      target : '#carApplyForm',
      url : carInfoUrl,
      success : showSuccess,
      dataType : 'json',
      clearForm : false,
      resetForm : false,
      type : 'post'
    };
  	$("#carApplyForm").ajaxSubmit(options);
		
}
      
  //操作提示
  function showSuccess(data) {
    if (data!=null && data!=''){
      showMsgError(data);
      isSubmit = false;
      return;
    }else{
        showMsgSuccess("操作成功！", "提示", function(){
		  doSearch();
		});
    }
  }
  
  function doChangeGoBack(){
  	var isGoback = $("#isGoback").val();
  	if('true'==isGoback){
  		$("#waitingCarDiv").show();
  		doChangeNeedWaiting();
  	}else{
  		$("#waitingCarDiv").hide();
  		$("#waitingCarTimeDiv").hide();
  	}
  }
  
  function doChangeNeedWaiting(){
  	var isNeedWaiting = $("#isNeedWaiting").val();
  	if('true'==isNeedWaiting){
  		$("#waitingCarTimeDiv").show();
  	}else{
  		$("#waitingCarTimeDiv").hide();
  	}
  }

function back(){
	doSearch();
}

$(document).ready(function(){
	vselect();
	doChangeGoBack();
	doChangeNeedWaiting();
});

function mouseOver(event,carId){
	var url="${request.contextPath}/office/carmanage/carmanage-carUseInfo.action?carId="+carId;
	load("#cursorLayer",url);
	getCursorPos(event);
}
function mouseOut(){
	$('#cursorLayer').hide();
}

function getCursorPos(e){
	var mousePos = mouseCoords(e); 
	var cursorX = mousePos.x+150;  //这个100是下拉框的长度，自行调整吧
	var cursorY = mousePos.y-50;
	$('#cursorLayer').show().css({'top':cursorY,'left':cursorX});
}

function mouseCoords(ev) 
{ 
	if(ev.pageX || ev.pageY){ 
	return {x:ev.pageX, y:ev.pageY};
	} 
	return {
	x:ev.clientX + document.body.scrollLeft - document.body.clientLeft, 
	y:ev.clientY + document.body.scrollTop - document.body.clientTop 
	};
} 
</script>

</@htmlmacro.moduleDiv>