<#import "/common/htmlcomponent.ftl" as common>
<div class="msg-content">
	<div class="msg-opt fn-clear">
    	<div class="fn-left">
            <a href="javascript:void(0);" class="abtn-blue" onclick="loadMsgDiv(${msgState}, '${folderId!}');">返回</a>
        </div>
        <div class="msg-opt-page" style="display:none;">
        	<span class="prev"></span>
        	<#if switchName>
        	<a href="javascript:void(0);" class="prev" title="上一封邮件"></a>
        	<a href="javascript:void(0);" class="next" title="下一封邮件"></a>
        	<#else>
        	<a href="javascript:void(0);" class="prev" title="上一封消息"></a>
        	<a href="javascript:void(0);" class="next" title="下一封消息"></a>
        	</#if>
            <span class="next"></span>
        </div>
    </div>
    <div class="msg-info msg-info3">
        <div class="msg-info-tt"><span title="${officeMsgSending.title?html}"><@common.cutOff str='${officeMsgSending.title?html}' length=60/></span></div>
        <div class="msg-line"></div>
    </div>
    <#assign sizeNum = officeMsgReceivings?size/>
    <#list officeMsgReceivings as x>
    <div class="msg-item">
    	<div class="msg-item-des" <#if sizeNum gt 1>needNum="true"<#else>needNum="false"</#if> <#if x_index==0>id="firstDiv"</#if> style="cursor:pointer;" msgId="${x.id!}" number="${officeMsgReceivings?size-x_index}" msgState="${msgState!}" isSendInfo="<#if x.isSendInfo>true<#else>false</#if>">
            <table class="mailing-list mailing-list2">
                <tr class="<#if x.isRead==0 && !x.isSendInfo>unread<#else>read</#if>">
                    <td style="width:5%" class="pl-15"><#if sizeNum gt 1><a href="javascript:void(0);" class="i-read">${officeMsgReceivings?size-x_index}</a></#if></td>
                    <td style="width:3%"><#if x.hasAttached == 1><span class="i-acc" title="有附件"></span></#if></td>
                    <td style="width:15%" class="name">${x.sendUsername!}</td>
                    <td style="width:3%">
						<#if !x.isSendInfo>
							<a href="javascript:void(0);" class="needTodoCLass i-backlog <#if x.needTodo==1>i-backlog-current</#if>" title="待办" msgId="${x.id!}"></a>
						</#if>
					</td>
                    <td style="width:3%">
						<#if !x.isSendInfo>
							<a href="javascript:void(0);" class="<#if x.hasStar==0>i-normal<#else>i-import</#if>" msgId="${x.id!}"></a>
						</#if>
					</td>
                    <td style="width:56%"><#if x.isWithdraw><span style="color:red;">已被撤回</span><#else><@common.cutOff str='${officeMsgSendingMap.get(x.messageId).simpleContent!}' length=40/></#if></td>
                    <td style="width:15%">${x.dateStr!}</td>
                </tr>
            </table>
        </div>
    	<div class="msg-item-con" id="${x.id!}" style="display:none;">
        </div>
    </div>
    </#list>
</div>
<script>
$(function(){
	$('.msg-item-des').click(function(){
		var msgId = $(this).attr("msgId");
		var number = $(this).attr("number");
		var msgState = $(this).attr("msgState");
		var isSendInfo = $(this).attr("isSendInfo");
		var needNum = $(this).attr("needNum");
		load("#"+msgId,"${request.contextPath}/office/msgcenter/msgcenter-msgDetailContent.action?msgId="+msgId+"&number="+number+"&msgState="+msgState+"&isSendInfo="+isSendInfo+"&needNum="+needNum);
		$(this).find("tr").removeClass('unread').attr("class","read");
		$(this).hide().siblings('.msg-item-con').show();
		$(this).parent('.msg-item').siblings('.msg-item').children('.msg-item-con').hide().siblings('.msg-item-des').show();
	});
	$('.msg-hide-con').click(function(){
		$(this).parents('.msg-item-con').hide().siblings('.msg-item-des').show();
	});
	
	$('td,.msg-info-tt').on('click','.i-normal',function(e){
		e.preventDefault();
		e.stopPropagation();
		var msgId = $(this).attr('msgId');
		$(this).addClass('i-import').removeClass('i-normal');
		$.getJSON(contextPath + "/office/msgcenter/msgcenter-changeStar.action", {
          "msgId":msgId,"hasStar":1
        }, function(data) {
			if (data!=null && data != '') {//设置失败
		        showMsgError(data);
		    } else {//设置成功
		      	//showMsgSuccess("设置成功！");
		    }
	    });
	});
	$('td,.msg-info-tt').on('click','.i-import',function(e){
		e.preventDefault();
		e.stopPropagation();
		$(this).addClass('i-normal').removeClass('i-import');
		var msgId = $(this).attr('msgId');
		$.getJSON(contextPath + "/office/msgcenter/msgcenter-changeStar.action", {
          "msgId":msgId,"hasStar":0
        }, function(data) {
			if (data!=null && data != '') {//设置失败
		        showMsgError(data);
		    } else {//设置成功
		      	//showMsgSuccess("设置成功！");
		    }
	    });
	});
	$('td,.msg-info-tt').on('click','.needTodoCLass',function(e){
		e.preventDefault();
		e.stopPropagation();
		var needTodo;
	   if($(this).hasClass('i-backlog-current')){
	  		$(this).removeClass("i-backlog-current");
			needTodo = 0;
	   }else{
	  		$(this).addClass("i-backlog-current");
		  	needTodo = 1;
	   }
	   var msgId = $(this).attr('msgId');
	   $.getJSON(contextPath + "/office/msgcenter/msgcenter-changeNeedTodo.action", {
	      "msgId":msgId,"needTodo":needTodo
	    }, function(data) {
			if (data!=null && data != '') {//设置失败
		        showMsgError(data);
		    } else {//设置成功
		      	//showMsgSuccess("设置成功！");
		    }
	   });
	});
	//触发第一条显示
	setTimeout(function(){
		$("#firstDiv").click();
	},100);
});
</script>