<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
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
	isSubmit = true;
	var timeSetUrl = "${request.contextPath}/office/signmanage/signmanage-signmanageTimeSetSave.action";
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
			var url="${request.contextPath}/office/signmanage/signmanage-signmanageTimeSet.action";
			load("#workReportDiv", url);
		});
		return;
    }
  }
</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<form method="post" name="timeSetForm" id="timeSetForm">
<input type="hidden" id="id" name="officeSigntimeSet.id" value="${officeSigntimeSet.id?default('')}"/>
<@common.tableList addClass="mt-15">
  	<tr>
  	  <th style="width:20%;text-align:right;"><span class="c-red">*</span>开始时间:</th>
  	  <td style="width:80%">
  	  	<@common.datepicker class="input-txt" style="width:100px;" id="startTime" name="officeSigntimeSet.startTime" value="${officeSigntimeSet.startTime!}" dateFmt="HH:mm" notNull="true"/>
  	  </td>
  	</tr>
  	<tr>
  	  <th style="width:20%;text-align:right;"><span class="c-red">*</span>结束时间:</th>
  	  <td>
  	  	<@common.datepicker class="input-txt" style="width:100px;" id="endTime" name="officeSigntimeSet.endTime" value="${officeSigntimeSet.endTime!}" dateFmt="HH:mm" notNull="true"/>
  	  </td>
  	</tr>
</@common.tableList>
</form>
<div class="t-center mt-10">
		<a href="javascript:void(0);" id="btnSaveAll" class="abtn-blue-big" onclick="javascript:save();">保存</a>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>