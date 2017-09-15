<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script>
   function changeStar(replyMsgId,event){
      var css = $(event).attr("class");
      var hasStar;
      if(css == 'i-normal'){
      	$(event).attr("class","i-import");
      	hasStar = 1;
      }else{
      	$(event).attr("class","i-normal");
      	hasStar = 0;
      }
      $.getJSON(contextPath + "/office/msgcenter/msgcenter-changeAllStar.action", {
          "replyMsgId":replyMsgId,"hasStar":hasStar
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
</script>
<form id="dataform" name="dataform">
<table class="mailing-list">
	<tr>
		<td style="width:5%;" class="b">&nbsp;</td>
		<td style="width:3%;" class="b">&nbsp;</td>
		<td style="width:3%;" class="b">&nbsp;</td>
		<td style="width:10%;" class="b t-center">发件人</td>
		<td style="width:4%;" class="b">&nbsp;</td>
		<td style="width:60%;" class="b">主题</td>
		<td style="width:15%;" class="b">时间</td>
	</tr>
	<#if officeMsgReceivings?size gt 0>
	<#assign number = 0/>
    <#list officeMsgReceivings as x>
    <#if number == 0 && todayStr == x.sendTime?string('yyyy-MM-dd')>
    	<#assign number = 1/>
    	<tr class="thead">
			<th colspan="7"><span class="time">今天</span></th>
		</tr>
    <#elseif number lte 1 && yesterdayStr == x.sendTime?string('yyyy-MM-dd')>
    	<#assign number = 2/>
    	<tr class="thead">
			<th colspan="7"><span class="time">昨天</span></th>
		</tr>
    <#elseif number lte 2 && yesterdayStr?date("yyyy-MM-dd") gt x.sendTime?string('yyyy-MM-dd')?date("yyyy-MM-dd")>
    	<#assign number = 3/>
    	<tr class="thead">
			<th colspan="7"><span class="time">更早</span></th>
		</tr>
    </#if>
    <tr class="<#if x.isRead==0>unread<#else>read</#if>">
    	<td style="width:5%;">
			<span class="ui-checkbox"><input type="checkbox" class="chk" name="checkid" value="${x.replyMsgId?default('')}"></span>
		</td>
    	<td style="width:3%;cursor:pointer;" onclick="showMsgDetail('${x.replyMsgId!}',3);"><a href="#" class="i-read" title="<#if x.isRead==0>未读<#else>已读</#if>"></a></td>
    	<td style="width:3%;cursor:pointer;" onclick="showMsgDetail('${x.replyMsgId!}',3);"><#if x.hasAttached == 1><span class="i-acc" title="有附件"></span></#if></td>
    	<td style="width:10%;text-align:center;cursor:pointer;" onclick="showMsgDetail('${x.replyMsgId!}',3);">${x.sendUsername!}</td>
    	<td style="width:4%;">
			<a href="javascript:void(0);" class="<#if x.hasStar==0>i-normal<#else>i-import</#if>" onclick="changeStar('${x.replyMsgId!}',this);"></a>
		</td>
    	<td style="width:60%;cursor:pointer;" onclick="showMsgDetail('${x.replyMsgId!}',3);">
	    	<#if x.isEmergency==2><span class="i-urgent">紧急</span></#if>
	    	<@common.cutOff str='${x.title?html}' length=40/>
	    	<#if x.countNum?default(1) gt 1><span class="num">${x.countNum?default(1)}</span></#if>
    	</td>
    	<td style="width:15%;cursor:pointer;" onclick="showMsgDetail('${x.replyMsgId!}',3);">${x.dateStr!}</td>
    </tr>
    </#list>
    <tr class="tfoot">
        <td colspan="7">
        	<@common.Toolbar container="#msgInboxList">
                <span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
                <a class="abtn-blue" href="javascript:void(0);" onclick="removeSelected();">删除</a>
                <span class="move-wrap">
                	<a href="javascript:void(0);" class="move-btn">移动到</a>
                    <#if officeMsgFolders?size gt 0>
                    <span class="move-inner" style="bottom:38px;">
                    	<#list officeMsgFolders as x>
                    		<a href="javascript:void(0);" onclick="turnToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
    					</#list>
                    </span>
                    </#if>
                </span>
            </@common.Toolbar>
        </td>
    </tr>
    <#else>
    	<tr>
	    	<td colspan="7" style="border-bottom-width:0px;">
	    		<p class="no-data mt-50 mb-50">还没有数据哦！</p>
	    	</td>
	    </tr>
    </#if>
</table>
</form>
</@common.moduleDiv>
