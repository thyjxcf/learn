<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="收件箱">
<script>
contextPath = '${request.contextPath}';
$(document).ready(function() {
	$("#msgUnReadNumber").text(${unReadNum!});
	load("#msgInboxList",contextPath+"/office/msgcenter/msgcenter-listReceivedMessages.action");
	$("#button_gaoji").click(function(){
    $("input[name='searchSender']").val("");
    $("input[name='beginTime']").val("");
    $("input[name='endTime']").val("");
    var display = document.getElementById("content_gaoji").style.display;
    if(display=="none"){
      $('.msg-advSearch').show();
    }else{
      $('.msg-advSearch').hide();
    }
  });
});
function searchMsg(type){
  var searchSender = $("input[name='searchSender']").val();
  var beginTime = $("input[name='beginTime']").val();
  var endTime = $("input[name='endTime']").val();
  var readType = $("input[name='readType']").val();
  var searchTitle = $("input[name='searchTitle']").val();
  if(searchTitle=='请输入关键字') searchTitle='';
  if (beginTime!='' && endTime!=''&&beginTime!=endTime){
     if(compareDate(beginTime,endTime) > 0){
		showMsgWarn('结束时间不能早于开始时间，请重新设置！');
		return;
	 } 
  }
  var url = contextPath + "/office/msgcenter/msgcenter-listReceivedMessages.action?";
  url +="readType="+readType;
  url +="&searchTitle="+encodeURIComponent(searchTitle);
  if(type == 2){
	  url +="&searchSender="+encodeURIComponent(searchSender);
	  url +="&searchBeginTime="+beginTime;
	  url +="&searchEndTime="+endTime;
  }
  load("#msgInboxList",url);
}

function setHasRead(){
	if(confirm("确定要全部设置为已读吗？")){
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/msgcenter/msgcenter-readAll.action",
			data: {},
			success: function(data){
				if(data != null && data!=''){
					showMsgError(data);
					return;
				}else{
					showMsgSuccess("设置已读成功！", "提示", function(){
						searchMsg();
					});
				}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	}
}

function changeToUnRead(){
	$("#unReadA").click();
}
</script>
<div class="msg-content">
	<div class="msg-dataInfo">
    	<div class="msg-search fn-right fn-clear">
        	<a href="#" class="abtn-blue abtn-advSearch fn-right ml-5" id="button_gaoji">高级</a>
            <div class="pub-search fn-right">
                <input value="请输入关键字" id="searchTitle" name="searchTitle" class="txt" type="text" style="width:150px;"
                 onblur="if(this.value=='') this.value='请输入关键字';" onfocus="if(this.value=='请输入关键字') this.value='';">
                <a href="javascript:void(0);" class="btn" onclick="searchMsg(1);">查找</a>
            </div>
            <div class="ui-select-box fn-right" style="width:100px;">
                <input type="text" class="ui-select-txt" value="" />
                <input type="hidden" value="" id="readType" name="readType" class="ui-select-value" />
                <a class="ui-select-close"></a>
                <div class="ui-option" myfunchange="searchMsg(1);">
                	<div class="a-wrap">
                        <a val=""><span>请选择</span></a>
                        <a val="0" id="unReadA"><span>未读</span></a>
                        <a val="1"><span>已读</span></a>
                    </div>
                </div>
            </div>
        </div>
    	<span class="tt">收件箱</span>
        <span>（共<b>${totalNum!}</b>封，其中<span class="unread" style="cursor:pointer;" onclick="changeToUnRead();">未读信息</span>${unReadNum!}封）</span>
        <a href="javascript:void(0);" class="set-read ml-10" onclick="setHasRead();">全部设为已读</a>
    </div>
    <div class="msg-advSearch" style="display:none;" id="content_gaoji">
    	<span>发件人：</span>
        <input type="text" class="input-txt" id="searchSender" name="searchSender" style="width:100px;">
    	<span class="ml-10">发件时间：</span>
	    <@common.datepicker class="input-txt" style="width:70px;" id="beginTime"/>
	    <span>-</span>
        <@common.datepicker class="input-txt" style="width:70px;" id="endTime"/>
        <a href="javascript:void(0);" class="abtn-blue" onclick="searchMsg(2);">查找</a>
    </div>
	<div id="msgInboxList"></div>
</div>  
</@common.moduleDiv>