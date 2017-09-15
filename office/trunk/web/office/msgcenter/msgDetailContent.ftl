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
	
	function removeMsg(id){
		if(!confirm("确定要删除吗？")){
			return;
		}
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/msgcenter/msgcenter-removeMsg.action",
			data: $.param( {deleteId:id,msgState:${msgState!}},true),
			success: function(data){
				if(data != null && data!=''){
					showMsgError(data);
					return;
				}else{
					showMsgSuccess("删除成功！", "提示", function(){
						loadMsgDiv(${msgState!});
					});
				}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	}
	
	function sendQuick(){
		var elem = document.getElementById("replyContent_${officeMsgSending.id!}");
		if(trim(elem.value)==''){
			showMsgWarn("回复内容不能为空");
			return;
		}
		
		if(getAbsoluteLength(elem.value) > 200){
			showMsgWarn("回复内容字符长度不能超过200!");
	        return;
		}
		
		if($("#isNeedsms").attr("checked")=="checked"){
			$("#isNeedsms").val("true");
			var smsContent=$("#smsContent").val();
			if(smsContent==null||smsContent==''){
				showMsgWarn("短信内容不能为空");
				return;
			}
			if(getAbsoluteLength(smsContent)>280){
				showMsgWarn("短信内容字符长度不能超过280!");
	        	return;
			}
			if($("#timing").attr("checked")=="checked"){
				$("#timing").val("true");
				var smsTime=$("#smsTime").val();
				if(smsTime==null||trim(smsTime)==""){
					showMsgWarn("定时发送时间为空!");
			       	return;
				}
			}else{
				$("#timing").val("false");
			}
		}else{
			$("#isNeedsms").val("false");
		}
		
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/office/msgcenter/msgcenter-sendQuick.action",
			data: $.param({"officeMsgSending.userIds":'${officeMsgSending.createUserId!}',"officeMsgSending.replyMsgId":'${officeMsgSending.replyMsgId!}',"officeMsgSending.title":'回复:${officeMsgSending.title?html}',"officeMsgSending.content":elem.value,
			"officeMsgSending.isNeedsms":$("#isNeedsms").val(),"officeMsgSending.smsContent":smsContent,"officeMsgSending.timing":$("#timing").val(),"officeMsgSending.smsTime":$("#smsTime").val()},true),
			success: function(data){
				if(data != null && data!=''){
					showMsgError(data);
					return;
				}else{
					showMsgSuccess("回复成功！", "提示", function(){
						elem.value = "";
						$("#isNeedsms").parent().removeClass("ui-checkbox send-sms-btn ui-checkbox-current");
						$("#isNeedsms").parent().addClass("ui-checkbox send-sms-btn");
						$("#isNeedsms").removeAttr("checked");
						$("#isNeedsms").val("false");
						$("#smsContent").val("");
						$("#timing").parent().removeClass("ui-checkbox send-sms-btn ui-checkbox-current");
						$("#timing").parent().addClass("ui-checkbox send-sms-btn");
						$("#timing").removeAttr("checked");
						$("#timing").val("false");
						$("#smsTime").val("");
						$("#replyContent_${officeMsgSending.id!}Stat").html("0");
						$("#smsContentStat").html("0");
					});
				}
			},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	}
	
	function statWordCount(field) {
	  var stat = $(field).next().find("span[id='smsContentStat']");
	    stat.html(getAbsoluteLength(field.value));

  }
  function statWordCount1(field) {
	  var stat = document.getElementById(field.id + "Stat");
	  if (stat) {
	    stat.innerHTML = getAbsoluteLength(field.value);
	  }
  }
  
  function getAbsoluteLength(str) {
	  var len = 0;
	  for ( var i = 0; i < str.length; i++) {
	    str.charCodeAt(i) > 255 ? len += 2 : len++;
	  }
	  return len;
 }
	
	function doPrint(){
		LODOP=getLodop();
		LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:15mm",getPrintContent(jQuery('#printDiv${number!}')));
	  	LODOP.PREVIEW();
	}
	
function checkSms2(smsObj) {
	var title=$(".msg-info-tt").find("span").text();
  if ($(smsObj).hasClass('ui-checkbox-current')) {
  	$('.send-sms-wrap,.send-timing').hide();
  	$('.send-timing-inner').hide();
  	$('#smsContent').val('');
    if ($("#timing").attr("checked") == "checked") {
      $("#timing").attr("checked", false); 
      $("#timingSpan").removeClass("ui-checkbox-current");
    }
  }else{
	$('.send-sms-wrap,.send-timing').show();
	<#if switchName>
	$(smsObj).parent("p").next().find("textarea[id='smsContent']").text('您有一个新邮件《回复：'+trim(title)+'》，请查阅！【'+'${userName!}'+'-${unitName!}'+'】');
	<#else>
	$(smsObj).parent("p").next().find("textarea[id='smsContent']").text('您有一个新消息《回复：'+trim(title)+'》，请查阅！【'+'${userName!}'+'-${unitName!}'+'】');
	</#if>
  }
	
	var stat2 = $(smsObj).parent("p").next().find("span[id='smsContentStat']");
	 	stat2.html(getAbsoluteLength($(smsObj).parent("p").next().find("textarea[id='smsContent']").text()));
}

function checkTime(timeObj) {
	if($(timeObj).hasClass('ui-checkbox-current')){
		$('.send-timing-inner').hide();
	}else{
		$('.send-timing-inner').show();
	}
}

function doJump(url,subsystem,moduleId,parentId,moduleName){
	<#if officeBusinessJump.bulletin>
	load("#container","${request.contextPath}/"+url+"&bulletinId="+'${officeBusinessJump.bulletinId!}',"doLoad()");
	<#else>
	load("#container","${request.contextPath}/"+url,"doLoad()");
	</#if>
	$('body,.common-wrap').removeAttr('style');
	$("#modelList").show();
	$('.current').removeClass('current');
	//如果是在更多里面 需要reload subsytem
	if($('#subsystem'+subsystem).attr("name") =="more"){
		load("#subSystemList","${request.contextPath}/system/homepage/subsystem.action?appId="+subsystem+"&moduleID="+moduleId);
	}else{
		$('#subsystem'+subsystem).addClass('current');
	}
	load("#modelList","${request.contextPath}/system/homepage/model.action?appId="+subsystem+"&moduleID="+moduleId,"assembleCrumbs('"+subsystem+"','"+parentId+"','"+moduleName+"')");
	showModel();
}

function assembleCrumbs(subsystem,parentId,moduleName){
	var _subsystemName=$('#subsystemList').find('.subsystem'+subsystem).text();
	var _subsystemName_a="<a href='javascript:void(0);' onclick='click2Subsystem("+subsystem+")'>"+_subsystemName+"</a>";
	var _parentName=$('#module'+parentId).html();
	var _parentName_a ="<a href='javascript:void(0);' onclick='showModel()'>"+_parentName+"</a>";
	showCrumbs("<p class='crumbs-inner'>当前位置："+_subsystemName_a+" &gt; "+_parentName_a+" &gt; "+moduleName+"</p>");
}

function doLoad(){
	<#if officeBusinessJump?exists && officeBusinessJump.index?exists>
		$(".pub-tab .pub-tab-list li:eq('${officeBusinessJump.index}')").addClass("current").siblings("li").removeClass("current");	
	</#if>
	//load("#sealDiv","/office/sealmanage/sealmanage-sealManageAdmin.action","openDiv('#sealAddLayer', '#sealAddLayer .close,#sealAddLayer .submit,#sealAddLayer .reset', '/office/sealmanage/sealmanage-sealManageAudit.action?officeSealId=9e0cbd5182a846b885fbb03043455e7a', null, null, '900px')","");
	eval('${officeBusinessJump.loadUrl!}');
		
}

function showModel(){
	$("#modelHideBtn").show();
	$('#header').removeClass('header-mini').find('.sub-menu').show().siblings('.sub-menu-mini').hide();
	$('.sub-menu-cut .prev,.sub-menu-cut .next').show();
	$('.cut-subMenu').removeClass('cut-subMenu-show').addClass('cut-subMenu-hide').text('收起');
}
</script>
<div class="msg-info msg-info2">
	<table>
	    <tr>
        	<td colspan="3" class="pb-10 pl-15">
        		<a href="javascript:void(0);" class="msg-hide-con fn-right mt-5">隐藏完整信息</a>
            	<#if !isSendInfo>
	            	<a href="javascript:void(0);" class="abtn-blue" onclick="removeMsg('${msgId}');">删除</a>
	            	<#if !officeMsgSending.isWithdraw>
	            	<a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${officeMsgSending.id}','reply');">回复</a>
	                <a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${officeMsgSending.id}','replyAll');">回复全部</a>
	                <a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${officeMsgSending.id}','forwarding');">转发</a>
	                <a href="javascript:void(0);" class="abtn-blue" onclick="doPrint();">打印</a>
	                </#if>
	            	<span class="move-wrap">
	                    <a href="javascript:void(0);" class="move-btn">移动到</a>
	                    <#if officeMsgFolders?size gt 0>
	                    <span class="move-inner" id="folder5">
	                        <#list officeMsgFolders as x>
	  		             		<a href="javascript:void(0);" onclick="turnSingleToFolder('${msgId!}','${x.id!}','${msgState!}');" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
	    					</#list>
	                    </span>
	                    </#if>
	                </span>
	                <span class="move-wrap">
	                    <a href="javascript:void(0);" class="move-btn">拷贝到</a>
	                    <#if officeMsgFolders?size gt 0>
	                    <span class="move-inner" id="folder6">
	                        <#list officeMsgFolders as x>
	  		             		<a href="javascript:void(0);" onclick="copySingleToFolder('${msgId!}','${x.id!}','${msgState!}');" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
	    					</#list>
	                    </span>
	                    </#if>
	                </span>
                </#if>
                <#if canBusinessJump>
                	<a href="javascript:void(0);" class="abtn-blue" onclick="doJump('${officeBusinessJump.module.url}','${officeBusinessJump.module.subsystem}','${officeBusinessJump.module.id}','${officeBusinessJump.module.parentid}','${officeBusinessJump.module.name}');return false;">前往处理</a>
                <#elseif canBusinessJumpM>
                	<a  class="abtn-unable">已处理</a>
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
			                    <span class="fr" style="float:right;display:block;width:80px;">
			                		<a href="javascript:void(0);" class="att_show" onclick="doDownload('${att.downloadPath!}');">下载</a>
			                    	<a href="javascript:void(0);" style="display:none;">存入网盘</a>
			                    </span>
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
			                    <span style="display:block;margin-right:80px;overflow: hidden;" title="${att.fileName!}"><@common.cutOff str='${att.fileName!}' length=30/></span>
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
    <table id="test" style="width:100%;">
	    <tr>
	        <#if !officeMsgSending.isWithdraw>
	        	<td style="word-break:break-all; word-wrap:break-word;" style="min-height:120px;">${officeMsgSending.content!}</td>
	        	<!--<td colspan="3"><textarea cols="70" rows="4" class="text-area my-5" readonly="true" style="width:98%;padding:5px 1%;height:50px;border:0;">${officeMsgSending.simpleContent!}</textarea>-->
	        <#else>
	        	<td style="min-height:120px;color:red;">已被撤回</td>
	        </#if>
	    </tr>
	</table>
</div>
<#if !isSendInfo>
<#if !officeMsgSending.isWithdraw>
<div class="sendMsg-form">
<div class="fn-clear pb-10">
	<textarea class="text-area fn-left" id="replyContent_${officeMsgSending.id!}" name="replyContent_${officeMsgSending.id!}" placeholder="快速回复：" style="width:94%;height:40px;" onkeyup="statWordCount1(this);" onpropertychange="statWordCount1(this);"></textarea>
	<p class="tt pt-10"><span class="num" style="margin-top:21px;"><span id="replyContent_${officeMsgSending.id!}Stat">0</span>/200</span></p>
    <!--<a href="javascript:void(0);" class="abtn-blue fn-left mt-10 ml-10" onclick="sendQuick();">回复</a>-->
</div>
<p class="tt pt-10 pl-10">
            	<!--<span class="num">长度<span id="smsContentStat">${smsContentLength!}</span>/140</span>-->
            	<span class="ui-checkbox send-sms-btn" onclick="checkSms2(this);" <#if !canSendSMS>style="display:none;"</#if>>
            		<input type="checkbox" id="isNeedsms" name="officeMsgSending.isNeedsms" class="chk" value="false">同时发送短信
            	</span>
            </p>
            <div class="send-sms-wrap mt-10" style="display:none;width:93%;height:40px;">
                <i></i>
                <textarea id="smsContent" name="officeMsgSending.smsContent" onkeyup="statWordCount(this);" onpropertychange="statWordCount(this);"></textarea>
                <div style="float:right;margin-top:30px;margin-right:-70px;"><span class="num" style=""><span id="smsContentStat">0</span>/280</span></div>
            </div>
            <div class="tt send-timing pt-10 pl-10 fn-clear" style="display:none;">
            	<span class="ui-checkbox send-timing-btn fn-left" id="timingSpan" onclick="checkTime(this);">
            		<input type="checkbox" id="timing" name="officeMsgSending.timing" value="false" class="chk">定时发送
        		</span>
                <div class="send-timing-inner fn-left" style="display:none;">
                    <span class="ml-15 fn-left">短信时间：</span>
                    <@common.datepicker class="input-txt" style="width:150px;" name="officeMsgSending.smsTime" id="smsTime" notNull="true"
				   msgName="短信时间" dateFmt="yyyy-MM-dd HH:mm"/>
                </div>
            </div>
    <div class="form-bt mt-10">
            	<a href="javascript:void(0);" class="abtn-blue fn-left mt-10 ml-10" onclick="sendQuick();">回复</a>
        	</div>
</#if>
</#if>
</div>
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