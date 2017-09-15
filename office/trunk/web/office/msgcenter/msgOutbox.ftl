<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="发件箱">
<script>
contextPath = '${request.contextPath}';
$(document).ready(function() {
  load("#msgOutboxList",contextPath+"/office/msgcenter/msgcenter-listSendedMessages.action");
});
function searchMsg(){
  var searchTitle = $("input[name='searchTitle']").val();
  var beginTime = $("input[name='beginTime']").val();
  var endTime = $("input[name='endTime']").val();
  var searchMsgType = $("input[name='searchMsgType']").val();
  if(searchTitle=='请输入关键字') searchTitle='';
  if (beginTime!='' && endTime!=''&&beginTime!=endTime){
     if(compareDate(beginTime,endTime) > 0){
		showMsgWarn('结束时间不能早于开始时间，请重新设置！');
	 	return;
	 }
  }
  var url = contextPath + "/office/msgcenter/msgcenter-listSendedMessages.action?";
  url+="&searchTitle="+encodeURIComponent(searchTitle);
  url +="&searchBeginTime="+beginTime;
  url +="&searchEndTime="+endTime;
  url +="&searchMsgType="+searchMsgType;
  load("#msgOutboxList",url);
}

</script>
<div class="msg-content">
	<div class="msg-dataInfo">
    	<div class="msg-search fn-right fn-clear" style="width:700px;">
            <div class="pub-search fn-right">
                <input value="请输入关键字" id="searchTitle" name="searchTitle" class="txt" type="text" style="width:150px;"
                 onblur="if(this.value=='') this.value='请输入关键字';" onfocus="if(this.value=='请输入关键字') this.value='';">
                <a href="javascript:void(0);" class="btn" onclick="searchMsg();">查找</a>
            </div>
            <@common.datepicker class="input-txt fn-right mr-10" style="width:70px;" id="endTime"/>
		    <span class="fn-right mr-10">-</span>
		    <@common.datepicker class="input-txt fn-right mr-10" style="width:70px;" id="beginTime"/>
   			<span class="fn-right">发件时间：</span>
			<div class="ui-select-box fn-right mr-10" style="width:100px;">
		        <input type="text" class="ui-select-txt" value="" />
		        <input type="hidden" value="" id="searchMsgType" name="searchMsgType" class="ui-select-value" />
		        <a class="ui-select-close"></a>
		        <div class="ui-option" myfunchange="searchMsg();">
		        	<div class="a-wrap">
		                <a val=""><span>请选择</span></a>
		                ${appsetting.getMcode("DM-MSGTYPE").getHtmlTag('',false)}
		            </div>
		        </div>
		    </div>
		    <#if switchName>
		    <span class="fn-right mr-10">邮件来源：</span>
		    <#else>
		    <span class="fn-right mr-10">消息来源：</span>
		    </#if>
        </div>
    	<span class="tt">发件箱</span>
    </div>
	<div id="msgOutboxList"></div>
</div>
</@common.moduleDiv>