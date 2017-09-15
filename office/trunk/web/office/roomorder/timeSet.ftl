<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function myOrder(){
	var url="${request.contextPath}/office/roomorder/roomorder.action";
	load("#container", url);
}

function orderApply(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderApply.action";
	load("#container", url);
}

function orderAudit(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderAudit.action";
	load("#container", url);
}

function timeSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-timeSet.action";
	load("#container", url);
}

function orderSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderSet.action";
	load("#container", url);
}

function labTypeSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-labTypeSet.action";
	load("#container", url);
}

function labApplyCount(){
	var url="${request.contextPath}/office/roomorder/roomorder-labApplyCount.action";
	load("#container", url);
}

var isSubmit = false;
function save(){
	if(isSubmit){
		return;
	}
	if(!checkAllValidate()){
	  	return;
	}
	var startTime = document.getElementById("startTime").value;
	var endTime = document.getElementById("endTime").value;
	var startTimes=startTime.split(':');  
	var endTimes=endTime.split(':');
	var startStr = "1"+startTimes[0]+startTimes[1];
	var endStr = "1"+endTimes[0]+endTimes[1];
	var start = parseInt(startStr);
	var end = parseInt(endStr);
	if(end<=start){
		showMsgWarn("开始时间必须小于结束时间！");
		return;
	}
	var noonStartTime = document.getElementById("noonStartTime").value;
	var noonEndTime = document.getElementById("noonEndTime").value;
	var noonStartTimes=noonStartTime.split(':');  
	var noonEndTimes=noonEndTime.split(':');
	var noonStartStr = "1"+noonStartTimes[0]+noonStartTimes[1];
	var noonEndStr = "1"+noonEndTimes[0]+noonEndTimes[1];
	var noonStart = parseInt(noonStartStr);
	var noonEnd = parseInt(noonEndStr);
	if(noonEnd<=noonStart){
		showMsgWarn("中午时间开始时间必须小于结束时间！");
		return;
	}
	isSubmit = true;
	var timeSetUrl = "${request.contextPath}/office/roomorder/roomorder-timeSetSave.action";
	var options = {
      target : '#timeSetForm',
      url : timeSetUrl,
      success : showSuccess,
      dataType : 'json',
      clearForm : false,
      resetForm : false,
      type : 'post'
    };
  	$("#timeSetForm").ajaxSubmit(options);
	
}
      
  //操作提示
  function showSuccess(data) {
    if (data!=null && data!=''){
      showMsgError(data);
      isSubmit = false;
      return;
    }else{
        showMsgSuccess("保存成功！", "提示", function(){
			var url="${request.contextPath}/office/roomorder/roomorder-timeSet.action";
			load("#container", url);
		});
		return;
    }
  }
  function orderSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderSet.action";
	load("#container", url);
}
</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li onclick="myOrder();">我的预约</li>
	<li onclick="orderApply();">预约申请</li>
	<#if auditAdmin>
	<li onclick="orderAudit();">预约审核</li>
	<li class="current" onclick="timeSet();">时段设置</li>
	<li onclick="orderSet();">类型信息设置</li>
	<#if !edu>
	<li onclick="labTypeSet();">实验种类设置</li>
	</#if>
	</#if>
	<#if !edu>
	<li onclick="labApplyCount();">实验申请统计</li>
	</#if>
	</ul>
</div>
<form method="post" name="timeSetForm" id="timeSetForm">
<input type="hidden" id="id" name="officeTimeSet.id" value="${officeTimeSet.id!}"/>
<@common.tableList addClass="mt-15">
  	<tr>
  	  <th style="width:20%;text-align:right;"><span class="c-red">*</span>开始时间:</th>
  	  <td style="width:80%">
  	  	<@common.datepicker class="input-txt" style="width:100px;" id="startTime" name="officeTimeSet.startTime" value="${officeTimeSet.startTime!}" dateFmt="HH:mm" notNull="true"/>
  	  </td>
  	</tr>
  	<tr>
  	  <th style="width:20%;text-align:right;"><span class="c-red">*</span>结束时间:</th>
  	  <td>
  	  	<@common.datepicker class="input-txt" style="width:100px;" id="endTime" name="officeTimeSet.endTime" value="${officeTimeSet.endTime!}" dateFmt="HH:mm" notNull="true"/>
  	  </td>
  	</tr>
  	<tr>
  	  <th style="width:20%;text-align:right;"><span class="c-red">*</span>时间段:</th>
  	  <td>
  	  	<@common.select style="width:110px;" valName="officeTimeSet.timeInterval" valId="timeInterval" notNull="true">
			<a val="">请选择</a>
			<a <#if officeTimeSet.timeInterval?default(0) == 15>class="selected"</#if> val="15">15分钟</a>
			<a <#if officeTimeSet.timeInterval?default(0) == 30>class="selected"</#if> val="30">30分钟</a>
			<a <#if officeTimeSet.timeInterval?default(0) == 60>class="selected"</#if> val="60">60分钟</a>
		</@common.select>
  	  </td>
  	</tr>
  	<tr>
  	  <th style="width:20%;text-align:right;"><span class="c-red">*</span>中午时间:</th>
  	  <td>
  	  	<@common.datepicker class="input-txt" style="width:100px;" id="noonStartTime" name="officeTimeSet.noonStartTime" value="${officeTimeSet.noonStartTime!}" dateFmt="HH:mm" notNull="true"/>
  	  	至
  	  	<@common.datepicker class="input-txt" style="width:100px;" id="noonEndTime" name="officeTimeSet.noonEndTime" value="${officeTimeSet.noonEndTime!}" dateFmt="HH:mm" notNull="true"/>
  	  </td>
  	</tr>
</@common.tableList>
</form>
<div class="t-center mt-10">
		<a href="javascript:void(0);" id="btnSaveAll" class="abtn-blue-big" onclick="javascript:save();">保存</a>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>