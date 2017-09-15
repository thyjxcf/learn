<#import "/common/htmlcomponent.ftl" as common>
<style>
    table .test{margin-bottom:10px;border-collapse:collapse;display:table;border:1px dashed #ddd}
    #test td{padding:5px;}
    .msg-detail{min-height:120px;padding:0px 0px 30px;font-size:14px;color:#444;}
</style>
<script type="text/javascript" src="${request.contextPath}/static/js/printarea.js"></script>
<script>
	$(function(){
		$('.msg-hide-con').click(function(e){
			e.preventDefault();
			$(this).parents('.msg-item-con').hide().siblings('.msg-item-des').show();
		});
		$('.move-wrap .move-btn').click(function(e){
			e.preventDefault();
			$('.move-wrap .move-inner').hide();
			$(this).siblings('.move-inner').show();
		});
		$('.move-wrap .move-inner a').click(function(e){
			e.preventDefault();
			$(this).parent('.move-inner').hide();
		});
		//消息详情
		$('.more-userList .more').click(function(e){
			e.preventDefault();
			if(!$(this).hasClass('more-all')){
				$(this).addClass('more-all').text('收起');
				$(this).siblings('p').css('height','auto');
			}else{
				$(this).removeClass('more-all').text('更多');
				$(this).siblings('p').css('height','24px');
			};
		});
	});
	
	function sendQuick(){
		var elem = document.getElementById("replyContent_${officeMsgSending.id!}");
		if(trim(elem.value)==''){
			showMsgWarn("回复内容不能为空");
			return;
		}
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/msgcenter/msgcenter-sendQuick.action",
			data: $.param({"officeMsgSending.userIds":'${officeMsgSending.createUserId!}',"officeMsgSending.replyMsgId":'${officeMsgSending.replyMsgId!}',
			"officeMsgSending.title":'回复:${officeMsgSending.title?html}',"officeMsgSending.content":elem.value},true),
			success: function(data){
				if(data != null && data!=''){
					showMsgError(data);
					return;
				}else{
					showMsgSuccess("回复成功！", "提示", function(){
						elem.value = "";
					});
				}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	}
	
	function doPrint(){
		LODOP=getLodop();
		LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:15mm",getPrintContent(jQuery('#printDiv${number!}')));
	  	LODOP.PREVIEW();
	}
</script>
<div class="msg-info msg-info2">
	<table>
		<tr>
			<td colspan="3" class="pb-10 pl-15">
    			<a href="javascript:void(0);" class="msg-hide-con fn-right mt-5">隐藏完整信息</a>
            	<#if !isSendInfo>
	            	<#if !officeMsgSending.isWithdraw>
		            	<a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${officeMsgSending.id}','reply');">回复</a>
		                <a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${officeMsgSending.id}','replyAll');">回复全部</a>
		                <a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${officeMsgSending.id}','forwarding');">转发</a>
		                <a href="javascript:void(0);" class="abtn-blue" onclick="doPrint();">打印</a>
	                </#if>
	            	<span class="move-wrap">
	                    <a href="javascript:void(0);" class="move-btn">拷贝到</a>
	                    <#if officeMsgFolders?size gt 0>
	                    <span class="move-inner">
	                        <#list officeMsgFolders as x>
	  		             		<a href="javascript:void(0);" onclick="copySingleToFolder('${msgId!}','${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
	    					</#list>
	                    </span>
	                    </#if>
	                </span>
                </#if>
            </td>
		</tr>
	    <tr>
	        <td class="tt"><#if needNum><span class="state-read fn-left ml-15">${number!}</span></#if>发件人：</td>
	        <td colspan="2">${officeMsgSending.sendUserName!}</td>
    	</tr>
        <tr>
        	<td class="tt">收件人：</td>
        	<td colspan="2">
            	<div class="more-userList">
                	<#if officeMsgSending.receiveNum gt 13>
                    	<a href="javascript:void(0);" class="more">更多</a>
                    </#if>
                    <p>
                    	<#list officeMsgSending.mainSendNames as str>
                        	<span>${str!}</span>
                    	</#list>
                    </p>
                </div>
            </td>
        </tr>
        <tr>
        	<td class="tt">时&nbsp;&nbsp;间：</td>
        	<td colspan="2">${officeMsgSending.sendTime?string('yyyy-MM-dd HH:mm:ss')}</td>
        </tr>
        <#if !officeMsgSending.isWithdraw>
	        <tr>
	        	<td class="tt">附&nbsp;&nbsp;件：</td>
	        	<td colspan="2"><span class="acc">${officeMsgSending.attachments?size}个</span>
					<#if officeMsgSending.attachments?size gt 0>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doDownloadZip('${officeMsgSending.id}');">打包下载</a></#if>
            	</td>
	        </tr>
            <#if officeMsgSending.attachments?exists && officeMsgSending.attachments?size gt 0>
	        <tr>
	        	<td colspan="3">
	                <ul class="accessory-list fn-clear">
						<#list officeMsgSending.attachments as att>
							<li>
			                    <img src="${request.contextPath}/static/images/icon/file/
			                    <#if att.extName=='pdf'>
								pdf.png<#elseif att.extName=='doc'||att.extName=='docx'>
								word.png<#elseif att.extName=='ppt'||att.extName=='pptx'>
								ppt.png<#elseif att.extName=='xls'||att.extName=='xlsx'>
								xls.png<#elseif att.extName=='csv'>
								csv.png<#elseif att.extName=='rtf'>
								rtf.png<#elseif att.extName=='wav'||att.extName=='mp3'>
								music.png<#elseif att.extName=='txt'>
								txt.png<#elseif att.extName=='mp4'||att.extName=='avi'||att.extName=='mov'>
								move.png<#elseif att.extName=='png'||att.extName=='jpg'||att.extName=='jpeg'||att.extName=='gif'||att.extName=='bmp'>
								jpg.png<#else>other.png</#if>">
			                    <span class="name" title="${att.fileName!}"><@common.cutOff str='${att.fileName!}' length=15/></span>
			                    <span class="fr">
			                		<a href="javascript:void(0);"  class="att_show" onclick="doDownload('${att.downloadPath!}');">下载</a>
			                    	<a href="javascript:void(0);" style="display:none;">存入网盘</a>
			                    </span>
			                </li>
						</#list>
	                </ul>
	            </td>
	        </tr>
			</#if>
        </#if>
    </table>
</div>
<div class="msg-detail">
    <table id="test" style="width:100%">
	    <tr>
	        <#if !officeMsgSending.isWithdraw>
	        	<td style="word-break:break-all; word-wrap:break-word;" style="min-height:120px;">${officeMsgSending.content!}</td>
	        <#else>
	        	<td style="min-height:120px;color:red;">已被撤回</td>
	        </#if>
	    </tr>
	</table>
</div>
<#if !isSendInfo>
<#if !officeMsgSending.isWithdraw>
<div class="fn-clear pb-10">
	<textarea class="text-area fn-left" id="replyContent_${officeMsgSending.id!}" name="replyContent_${officeMsgSending.id!}" placeholder="快速回复：" style="width:89%;height:40px;"></textarea>
    <a href="javascript:void(0);" class="abtn-blue fn-left mt-10 ml-10" onclick="sendQuick();">回复</a>
</div>
</#if>
</#if>
<div id="printDiv${number!}" style="display:none;">
	<div class="msg-print">
	    <div class="msg-info-wrap">
	        <table>
	        	<tr>
	        		<td colspan="2" class="tit" style="text-align:center;">${officeMsgSending.title?html}</td>
	        	</tr>
	            <tr>
	                <th style="width:10%;">发件人：</th>
	                <td style="width:90%;">${officeMsgSending.sendUserName!}</td>
	            </tr>
	            <tr>
	                <th valign="top" class="pt-5">收件人：</th>
	                <td valign="top">
	                	<ul class="user-list">
	                		<#list officeMsgSending.mainSendNames as str>
                        	<li>${str!}</li>
                        	</#list>
	                	</ul>
	                </td>
	            </tr>
	            <tr>
	                <th>时&nbsp;&nbsp;间：</th>
	                <td>${officeMsgSending.sendTime?string('yyyy-MM-dd HH:mm:ss')}</td>
	            </tr>
	            <tr>
	                <th valign="top" class="pt-5">附&nbsp;&nbsp;件：</th>
	                <td valign="top">
	                    <ul class="acc-list">
	                        <#if officeMsgSending.attachments?exists>
								<#list officeMsgSending.attachments as att>
									<li>${att.fileName!}</li>
								</#list>
							</#if>
	                    </ul>
	                </td>
	            </tr>
	        </table>
	    </div>
        <#if !officeMsgSending.isWithdraw>
        <div class="msg-des" style="word-break:break-all; word-wrap:break-word;">
        	${officeMsgSending.content!}
    	</div>
        <#else>
        <div class="msg-des" style="color:red;">
        	已被撤回
        </div>
        </#if>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$('.accessory-list li').each(function(){
        var li_w=$(this).innerWidth()-30;
		var img_w=$(this).children('img').outerWidth();
		var fr_w=$(this).children('.fr').outerWidth();
		var name_w=li_w-img_w-fr_w;
		$(this).children('.name').width(name_w);
    });
});
</script>