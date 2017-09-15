<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="自由短信">
<script>
contextPath = '${request.contextPath}';
$(document).ready(function() {
  load("#msgSmsList",contextPath+"/office/msgcenter/msgcenter-msgSmsList.action");
});
function searchMsgSmsList(){
  var beginTime = $("input[name='beginTime']").val();
  var endTime = $("input[name='endTime']").val();
  if (beginTime!='' && endTime!=''&&beginTime!=endTime){
     if(compareDate(beginTime,endTime) > 0){
		showMsgWarn('结束时间不能早于开始时间，请重新设置！');
	 	return;
	 }
  }
  var url = contextPath + "/office/msgcenter/msgcenter-msgSmsList.action?";
  url +="&searchBeginTime="+beginTime;
  url +="&searchEndTime="+endTime;
  load("#msgSmsList",url);
}

</script>
<div class="popUp-layer" id="msgSmsLayer" style="display:none;width:600px;z-index:9999;"></div>
<div class="msg-content mt-5">
	<div class="msg-dataInfo">
    	<div class="msg-search fn-right fn-clear" style="width:700px;">
            <div class="fn-right">
                <a href="javascript:void(0);" class="abtn-blue" onclick="searchMsgSmsList();">查找</a>
            </div>
            <@common.datepicker class="input-txt fn-right mr-10" style="width:70px;" id="endTime"/>
		    <span class="fn-right mr-10">-</span>
		    <@common.datepicker class="input-txt fn-right mr-10" style="width:70px;" id="beginTime"/>
   			<span class="fn-right">发送时间：</span>
        </div>
    </div>
	<div id="msgSmsList"></div>
</div>
</@common.moduleDiv>