<#import "/common/htmlcomponent.ftl" as common>
<style>
    table .test{margin-bottom:10px;border-collapse:collapse;display:table;border:1px dashed #ddd}
    #test td{padding:5px;border: 1px solid #DDD;}
</style>
<script type="text/javascript" src="${request.contextPath}/static/js/printarea.js"></script>
<script type="text/javascript">
	$(function(){
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
	
	function removeMsg(id){
		if(!confirm("确定要删除吗？")){
			return;
		}
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/msgcenter/msgcenter-removeMsg.action",
			data: $.param( {deleteId:id,msgState:${msgState}},true),
			success: function(data){
				if(data != null && data!=''){
					showMsgError(data);
					return;
				}else{
					showMsgSuccess("删除成功！", "提示", function(){
						loadMsgDiv(${msgState}, '${folderId!}');
					});
				}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	}
	
	function viewIsReadInfo(isRead){
		openDiv('#msgLayer',null,"${request.contextPath}/office/msgcenter/msgcenter-viewIsReadInfo.action?msgId=${officeMsgSending.id!}&isRead="+isRead,true,'#msgLayer .wrap','410');
	}
	
	function smsRemind(){
		openDiv('#smsRemindLayer',null,"",true,'#smsRemindLayer .wrap','410');
	}
	
	function saveSmsRemind(){
		var smsTextarea = $("#smsTextarea").val();
		if(trim(smsTextarea) == ''){
			showMsgWarn("短信内容不能为空");
			return;
		}
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/msgcenter/msgcenter-remindSms.action",
			data: $.param({smsContent:smsTextarea,msgId:'${officeMsgSending.id!}'},true),
			success: function(data){
				if(data != null && data!=''){
					showMsgError(data);
					return;
				}else{
					showMsgSuccess("短信提醒成功！");
				}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	}
	function doPrint(){
		LODOP=getLodop();
		LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:15mm",getPrintContent(jQuery('#printDiv')));
	  	LODOP.PREVIEW();
	}
</script>
<!--=E 弹出层 End-->
<div class="popUp-layer" id="msgLayer" style="display:none;width:800px;z-index:9999;"></div>
<div class="popUp-layer" id="smsRemindLayer" style="display:none;width:600px;height:280px;z-index:9999;">
	<p class="tt"><a href="javascript:void(0);" onclick="closeDiv('#smsRemindLayer');" class="close">关闭</a><span>短信提醒</span></p>
	<div class="wrap mt-10" id="smsEditDiv">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	            <th class="t-right" style="width:80px;">&nbsp;<span style="color:red;">*</span>短信内容：</th>
	            <td style="width:510px;">
	                <textarea class="text-area" id="smsTextarea" style="width:500px;height:160px;"></textarea>
	            </td>
	        </tr>
	        <tr>
	            <td colspan="2">
	            	<div class="mt-10" style="text-align:center;">
		            	<a href="javascript:void(0);" class="abtn-blue submit" onclick="saveSmsRemind();">发送</a>
	            	</div>
	            </td>
	        </tr>
	    </table>
	</div>
</div>
<div class="msg-content">
	<div class="msg-opt fn-clear">
    	<div class="fn-left">
            <a href="javascript:void(0);" class="abtn-blue" onclick="loadMsgDiv(${msgState}, '${folderId!}');">返回</a>
        	<a href="javascript:void(0);" class="abtn-blue" onclick="removeMsg('${msgId}');">删除</a>
        	<#if !officeMsgSending.isWithdraw>
            	<#if detailState == 2>
                    <a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${officeMsgSending.id}','replyAll');">回复全部</a>
                    <a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${officeMsgSending.id}','forwarding');">转发</a>
                <#elseif detailState == 3>
                	<a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${officeMsgSending.id}','reply');">回复</a>
                    <a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${officeMsgSending.id}','replyAll');">回复全部</a>
                    <a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${officeMsgSending.id}','forwarding');">转发</a>
                <#elseif detailState == 1>
                    <a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${officeMsgSending.id}','send');">发送</a>
                </#if>
                <a href="javascript:void(0);" class="abtn-blue" onclick="doPrint();">打印</a>
            </#if>
        	<span class="move-wrap">
                <a href="javascript:void(0);" class="move-btn">移动到</a>
                <#if officeMsgFolders?size gt 0>
                <span class="move-inner" style="top:32px;" id="folder5">
                    <#list officeMsgFolders as x>
	             		<a href="javascript:void(0);" onclick="turnSingleToFolder('${msgId!}','${x.id!}','${msgState!}');" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
					</#list>
                </span>
                </#if>
            </span>
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
    <div class="msg-info">
    	<div class="msg-info-tt"><span>${officeMsgSending.title?html}</span></div>
        <table>
        	<tr>
        		<td class="tt">发件人：</td>
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
            <#-- 已发送 -->
            <#if detailState gte 2>
	            <#-- 发件箱 -->
	            <#if detailState == 2>
		            <tr>
		                <td class="tt">已&nbsp;&nbsp;读：</td>
		                <td colspan="2">
		                	${officeMsgSending.hasReadNum!}人&nbsp;&nbsp;
		                	<#if officeMsgSending.hasReadNum gt 0>
			                	<a href="javascript:void(0);" onclick="viewIsReadInfo(1);">查看</a>
		                	</#if>
	                	</td>
		            </tr>
		            <tr>
		                <td class="tt">未&nbsp;&nbsp;读：</td>
		                <td colspan="2"><span class="c-orange">${officeMsgSending.unReadNum!}人</span>
		                	<#if !officeMsgSending.isWithdraw && officeMsgSending.unReadNum gt 0>
		                	&nbsp;&nbsp;<a href="javascript:void(0);" onclick="viewIsReadInfo(0);">查看</a>
		                	<#if canSendSMS>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="smsRemind();">短信提醒</a></#if>
		                	</#if>
	                	</td>
		            </tr>
	            </#if>
	            <tr>
	            	<td class="tt">时&nbsp;&nbsp;间：</td>
	            	<td colspan="2">${officeMsgSending.sendTime?string('yyyy-MM-dd HH:mm:ss')}</td>
	            </tr>
            </#if>
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
				                    <span style="width:280px;float:left;" title="${att.fileName!}"><@common.cutOff str='${att.fileName!}' length=30/></span>
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
</div>
<div id="printDiv" style="display:none;">
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
            	<#if detailState == 2>
	            <tr>
	                <th>时&nbsp;&nbsp;间：</th>
	                <td>
	                	${officeMsgSending.sendTime?string('yyyy-MM-dd HH:mm:ss')}
                	</td>
	            </tr>
            	</#if>
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