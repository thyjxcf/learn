<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script>
   function changeStar(replyMsgId,event){
      var css = $(event).attr("class");
      var hasStar = 0;
      $.getJSON(contextPath + "/office/msgcenter/msgcenter-changeAllStar.action", {
          "replyMsgId":replyMsgId,"hasStar":hasStar
        }, function(data) {
			if (data!=null && data != '') {//设置失败
		        showMsgError(data);
		    } else {//设置成功
	      		searchMsg();
		    }
	  });
   }
   
   //设置为待办
   function changeTodo(replyMsgId,event){
      var needTodo;
      if($(event).hasClass('i-backlog-current')){
      	$(event).removeClass("i-backlog-current");
      	needTodo = 0;
      }else{
      	$(event).addClass("i-backlog-current");
      	needTodo = 1;
      }
      $.getJSON(contextPath + "/office/msgcenter/msgcenter-changeAllNeedTodo.action", {
          "replyMsgId":replyMsgId,"needTodo":needTodo
        }, function(data) {
			if (data!=null && data != '') {//设置失败
		        showMsgError(data);
		    } else {//设置成功
		      	//showMsgSuccess("设置成功！");
		    }
	  });
   }
   
   //移动到
	$('.move-wrap .move-btn').click(function(e){
		e.preventDefault();
		$('.move-wrap .move-inner').hide();
		$(this).siblings('.move-inner').show();
	});
	$('.move-wrap .move-inner a').click(function(e){
		e.preventDefault();
		$(this).parent('.move-inner').hide();
	});
   
   function removeSelected(){
	    var ids = [];
		if(isCheckBoxSelect($("[name='checkid']")) == false){
			showMsgWarn("请先选择想要进行操作的数据！");
			return;
		}
		if(!confirm("确定要删除吗？")){
			return;
		}
		var i = 0;
		$("input[name='checkid'][checked='checked']").each(function(){
			ids[i] = $(this).val();
			i++;
		});
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/msgcenter/msgcenter-removeMsgs.action",
			data: $.param( {deleteIds:ids,msgState:3},true),
			success: function(data){
				if(data != null && data!=''){
					showMsgError(data);
					return;
				}else{
					showMsgSuccess("删除成功！", "提示", function(){
						searchMsg();
					});
				}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
   }
   frameAuto();
  var stateIsRead=2;	
  function changeState(id,isRead,event){
   	var ids=[];
   	ids[0]=id;
   	if(stateIsRead==2){
   		if(isRead==0){
   			stateIsRead=0;
   		}else stateIsRead=1;
   	}
   	if(stateIsRead==0){
   		$(event).attr("title","已读");
   		$("#"+id).attr("class","read");
   		stateIsRead=1;
   	}else if(stateIsRead==1){
   		$(event).attr("title","未读");
   		$("#"+id).attr("class","unread");
   		stateIsRead=0;
   	}
   		
    $.getJSON(contextPath + "/office/msgcenter/msgcenter-changeState.action?deleteIds="+ids, {
  			"stateIsRead":stateIsRead
		}, function(data) {
		if (data!=null && data != '') {//设置失败
	        showMsgError(data);	
	    }
  	});
   }
   function changeStates(){
  	 var ids = [];
  	 var state=1;
  	 if(isCheckBoxSelect($("[name='checkid']")) == false){
		return;
 	 }
 	 var i = 0;
	 $("input[name='checkid'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		$("#"+ids[i]).attr("class","read");
		$("#"+ids[i]+"id").attr("title","已读");
		stateIsRead=1;
		i++;
	 });
	   $.getJSON(contextPath + "/office/msgcenter/msgcenter-changeState.action?deleteIds="+ids, {
  			"stateIsRead":state
		}, function(data) {
		if (data!=null && data != '') {//设置失败
	        showMsgError(data);	
	    }
  	});
   }
</script>
<form id="dataform" name="dataform">
<#if officeMsgReceivings?size gt 0>
<@common.ToolbarBlank tableDivClass="base-operation base-operation-no">
    <a class="abtn-blue" href="javascript:void(0);" onclick="removeSelected();">删除</a>
    <a class="abtn-blue" href="javascript:void(0);" onclick="forwardingMsg('forwarding');">转发</a>
    <span class="move-wrap">
    	<a href="javascript:void(0);" class="move-btn">移动到</a>
        <#if officeMsgFolders?size gt 0>
        <span class="move-inner" style="top:38px;" id="folder7">
			<#list officeMsgFolders as x>
         		<a href="javascript:void(0);" onclick="turnToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
			</#list>
        </span>
        </#if>
    </span>
    <span class="move-wrap">
    	<a href="javascript:void(0);" class="move-btn">拷贝到</a>
        <#if officeMsgFolders?size gt 0>
        <span class="move-inner" style="top:38px;" id="folder8">
        	<#list officeMsgFolders as x>
        		<a href="javascript:void(0);" onclick="copyToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
			</#list>
        </span>
        </#if>
    </span>
</@common.ToolbarBlank>
</#if>
<table class="mailing-list">
	<tr class="tt">
		<td style="width:5%;" class="b"><span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk"></span></td>
		<td style="width:3%;" class="b"><span class="i-read" title="设为已读" onclick="changeStates()"></span></td>
		<td style="width:3%;" class="b"><span class="i-acc" title="有附件"></span></td>
		<td style="width:10%;" class="b t-center">发件人</td>
		<td style="width:4%;" class="b">&nbsp;</td>
		<td style="width:4%;" class="b">&nbsp;</td>
		<td style="width:57%;" class="b">主题</td>
		<td style="width:15%;" class="b">时间</td>
	</tr>
	<#if officeMsgReceivings?size gt 0>
	<#assign number = 0/>
    <#list officeMsgReceivings as x>
    <#if number == 0 && todayStr == x.sendTime?string('yyyy-MM-dd')>
    	<#assign number = 1/>
    	<tr class="thead">
			<th colspan="8"><span class="time">今天</span></th>
		</tr>
    <#elseif number lte 1 && yesterdayStr == x.sendTime?string('yyyy-MM-dd')>
    	<#assign number = 2/>
    	<tr class="thead">
			<th colspan="8"><span class="time">昨天</span></th>
		</tr>
    <#elseif number lte 2 && yesterdayStr?date("yyyy-MM-dd") gt x.sendTime?string('yyyy-MM-dd')?date("yyyy-MM-dd")>
    	<#assign number = 3/>
    	<tr class="thead">
			<th colspan="8"><span class="time">更多</span></th>
		</tr>
    </#if>
    <tr class="<#if x.isRead==0>unread<#else>read</#if>" id="${x.replyMsgId!}">
    	<td style="width:5%;">
			<span class="ui-checkbox"><input type="checkbox" class="chk" name="checkid" forwardingId="${x.messageId!}" withdrawValue="<#if x.isWithdraw>1<#else>0</#if>" value="${x.replyMsgId?default('')}"></span>
		</td>
    	<td style="width:3%;cursor:pointer;" ><a href="javascript:void(0);" onclick="changeState('${x.replyMsgId!}','${x.isRead}',this);" id="${x.replyMsgId!}id" class="i-read" title="<#if x.isRead==0>未读<#else>已读</#if>"></a></td>
    	<td style="width:3%;cursor:pointer;" onclick="showMsgDetail('${x.replyMsgId!}',6);"><#if x.hasAttached == 1><span class="i-acc" title="有附件"></span></#if></td>
    	<td style="width:10%;text-align:center;cursor:pointer;" onclick="showMsgDetail('${x.replyMsgId!}',6);">${x.sendUsername!}</td>
    	<td style="width:4%;">
			<a href="javascript:void(0);" class="i-backlog <#if x.needTodo==1>i-backlog-current</#if>" title="待办" onclick="changeTodo('${x.replyMsgId!}',this);"></a>
		</td>
    	<td style="width:4%;">
			<a href="javascript:void(0);" class="i-import" onclick="changeStar('${x.replyMsgId!}',this);"></a>
		</td>
    	<td style="width:56%;cursor:pointer;" onclick="showMsgDetail('${x.replyMsgId!}',6);">
			<#if x.isWithdraw>
    			<span style="color:red;">已被撤回</span>
    		<#else>
		    	<#if x.isEmergency==2><span class="i-urgent">紧急</span></#if>
		    	<@common.cutOff str='${x.title?html}' length=40/>
	    	</#if>
	    	<#if x.countNum?default(1) gt 1><span class="num">${x.countNum?default(1)}</span></#if>
		</td>
    	<td style="width:15%;cursor:pointer;" onclick="showMsgDetail('${x.replyMsgId!}',6);">${x.dateStr!}</td>
    </tr>
    </#list>
    <tr class="tfoot">
        <td colspan="8">
        	<@common.Toolbar container="#msgImportList">
                <a class="abtn-blue" href="javascript:void(0);" onclick="removeSelected();">删除</a>
                <a class="abtn-blue" href="javascript:void(0);" onclick="forwardingMsg('forwarding');">转发</a>
                <span class="move-wrap">
                	<a href="javascript:void(0);" class="move-btn">移动到</a>
                    <#if officeMsgFolders?size gt 0>
                    <span class="move-inner" style="bottom:38px;" id="folder9">
						<#list officeMsgFolders as x>
  		             		<a href="javascript:void(0);" onclick="turnToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
    					</#list>
                    </span>
                    </#if>
                </span>
                <span class="move-wrap">
			    	<a href="javascript:void(0);" class="move-btn">拷贝到</a>
			        <#if officeMsgFolders?size gt 0>
			        <span class="move-inner" style="bottom:38px;" id="folder10">
			        	<#list officeMsgFolders as x>
			        		<a href="javascript:void(0);" onclick="copyToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
						</#list>
			        </span>
			        </#if>
			    </span>
            </@common.Toolbar>
        </td>
    </tr>
    <#else>
    	<tr>
	    	<td colspan="8" style="border-bottom-width:0px;">
	    		<p class="no-data mt-50 mb-50">还没有数据哦！</p>
	    	</td>
	    </tr>
    </#if>
</table>
</form>
</@common.moduleDiv>
