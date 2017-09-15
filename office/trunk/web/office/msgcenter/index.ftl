<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<link rel="stylesheet" type="text/css" href="${domain!}/static/css/msgCenter.css">
<script type="text/javascript" src="${domain!}/static/js/attachmentUpload.js"></script>
<script>
function sendNote(){
	<#if standard>
		var url="${request.contextPath}/office/msgcenter/msgcenter-sendNote.action";
		load("#msgDiv", url);
	<#else>
		var url="${request.contextPath}/office/msgcenter/msgcenter-sendDgNote.action";
		load("#container", url);
	</#if>
}

function sendMsg(msgId,operateType){
	<#if standard>
		var url="${request.contextPath}/office/msgcenter/msgcenter-sendNote.action?msgId="+msgId+"&operateType="+operateType;
		load("#msgDiv", url);
	<#else>
		var url="${request.contextPath}/office/msgcenter/msgcenter-sendDgNote.action?msgId="+msgId+"&operateType="+operateType;
		load("#container", url);
	</#if>
}

function loadMsgDiv(msgState, folderId, readType){
	var url = "";
	if(msgState == 1){
		var canDeFile = 'true';
		url="${request.contextPath}/office/msgcenter/msgcenter-msgDraftbox.action?canDeFile="+canDeFile;
	}else if(msgState == 2){
		url="${request.contextPath}/office/msgcenter/msgcenter-msgOutbox.action";
	}else if(msgState == 3){
		if (readType){
			url="${request.contextPath}/office/msgcenter/msgcenter-msgInboxTab.action?readType="+readType;
		} else {
			url="${request.contextPath}/office/msgcenter/msgcenter-msgInboxTab.action";
		}
	}else if(msgState == 4){
		url="${request.contextPath}/office/msgcenter/msgcenter-msgAbandon.action";
	}else if(msgState == 6){
		url="${request.contextPath}/office/msgcenter/msgcenter-msgImport.action";
	}else if(msgState == 5){
		url="${request.contextPath}/office/msgcenter/msgcenter-msgFolder.action?folderId="+folderId;
	}else if(msgState == 7){
		url="${request.contextPath}/office/msgcenter/msgcenter-msgTodo.action";
	}else if(msgState == 8){
		url="${request.contextPath}/office/msgcenter/msgcenter-msgSms.action";
	}
	load("#msgDiv", url);
}

function loadMsgReceiveDiv(receiveType){
	var url ="${request.contextPath}/office/msgcenter/msgcenter-msgInboxTab.action?receiveType="+receiveType;
	load("#msgDiv", url);
}

$(document).ready(function(){
	<#if xinJiangDeploy && desktopIn=="0">
		sendNote();
	<#else>
		<#if desktopIn=="2" && officeMsgReceiving??>
			<#if officeMsgReceiving.receiverType lte 3>
				showMsgDetail("${officeMsgReceiving.replyMsgId}",3);
			<#else>
				showMsgDetailOther("${officeMsgReceiving.replyMsgId}","${officeMsgReceiving.receiverType}");
			</#if>
		<#else>
			loadMsgDiv(3,"","${readType!}");
		</#if>
	</#if>
	load("#msgFloderDiv", "${request.contextPath}/office/msgcenter/msgcenter-listFolders.action");
});

function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}

function viewMsg(id,msgState){
	var url = "${request.contextPath}/office/msgcenter/msgcenter-viewMsgSingle.action?msgId="+id+"&msgState="+msgState;
	load("#msgDiv", url);
}

function showMsgDetail(replyMsgId,msgState){
	var url = "${request.contextPath}/office/msgcenter/msgcenter-msgDetail.action?replyMsgId="+replyMsgId+"&msgState="+msgState;
	load("#msgDiv", url);
}

function showMsgDetailOther(replyMsgId,receiveType){
	var url = "${request.contextPath}/office/msgcenter/msgcenter-msgDetailOther.action?replyMsgId="+replyMsgId+"&receiveType="+receiveType;
	load("#msgDiv", url);
}

function turnSingleToFolder(msgId,folderId,msgState){
	if(!confirm("确定要移动吗？")){
		return;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/msgcenter/msgcenter-turnSingleToFolder.action",
		data: $.param( {msgId:msgId,folderId:folderId,msgState:msgState},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				return;
			}else{
				showMsgSuccess("移动成功！", "提示", function(){
					loadMsgDiv(msgState, folderId);
				});
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function copySingleToFolder(msgId,folderId,msgState){
	if(!confirm("确定要拷贝吗？")){
		return;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/msgcenter/msgcenter-copySingleToFolder.action",
		data: $.param( {msgId:msgId,folderId:folderId,msgState:msgState},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				return;
			}else{
				showMsgSuccess("拷贝成功！");
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function turnToFolder(folderId,msgState){
	var ids = [];
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		<#if switchName>
		showMsgWarn("请先选择要移动的邮件！");
		<#else>
		showMsgWarn("请先选择要移动的信息！");
		</#if>
		return;
	}
	if(!confirm("确定要移动吗？")){
		return;
	}
	var i = 0;
	$("input[name='checkid'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		i++;
	});
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/msgcenter/msgcenter-turnToFolder.action",
		data: $.param( {deleteIds:ids,folderId:folderId,msgState:msgState},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				return;
			}else{
				showMsgSuccess("移动成功！", "提示", function(){
					searchMsg();
				});
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function copyToFolder(folderId,msgState){
	var ids = [];
	if(isCheckBoxSelect($("[name='checkid']")) == false){
	<#if switchName>
		showMsgWarn("请先选择要拷贝的邮件！");
	<#else>	showMsgWarn("请先选择要拷贝的信息！");
	</#if>
		return;
	}
	if(!confirm("确定要拷贝吗？")){
		return;
	}
	var i = 0;
	$("input[name='checkid'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		i++;
	});
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/msgcenter/msgcenter-copyToFolder.action",
		data: $.param( {deleteIds:ids,folderId:folderId,msgState:msgState},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				return;
			}else{
				showMsgSuccess("拷贝成功！");
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function forwardingMsg(operateType){
	var length = $("input[name='checkid'][checked='checked']").length;
	if(length != 1){
	<#if switchName>
		showMsgWarn("请先选择想要转发的邮件，只能选择一个！");
		<#else>
		showMsgWarn("请先选择想要转发的消息，只能选择一个！");
		</#if>
		return;
	}
	var msgId = '';
	var withdrawValue = '';
	$("input[name='checkid'][checked='checked']").each(function(){
		msgId = $(this).attr('forwardingId');
		withdrawValue = $(this).attr('withdrawValue');
	});
	if(withdrawValue == 1){
		<#if switchName>
		showMsgWarn("已撤回的邮件不能转发！");
		<#else>
		showMsgWarn("已撤回的信息不能转发！");
		</#if>
		return;
	}
	sendMsg(msgId,operateType);
}
//左右框架对齐问题,收件箱、发件箱、草稿箱底部切换显示记录条数的时候，需要将此函数加入，不然左边高度不会变化
function frameAuto(){
	<#--
	$('.msg-sidebar').css('height', '');
	var sideH=$('.msg-sidebar').height();
	var conH=$('.msg-content').height();
	var myH;
	(sideH<conH) ? myH=conH-30 : myH=sideH;
	$('.msg-sidebar').height(myH);
	-->
};

function doDownloadZip(id){
	var url="${request.contextPath}/office/msgcenter/msgcenter-doDownloadZip.action?msgId="+id;
	location.href=url;
}
</script>
<iframe id="downloadFrame" name="downloadFrame" allowTransparency="true" frameBorder="0" width="0%" height="0%" scrolling="auto" src="" style="display:none;"></iframe>
<div class="fn-clear">
<div class="msg-sidebar">
    <div class="msg-sidebar-wrap">
   		<#if xinJiangDeploy && desktopIn=="0">
   		<p style="cursor:pointer;" class="current" onclick="sendNote();"><a href="javascript:void(0);" class="name" style="font-size:15px"><#if switchName>发邮件<#else>发消息</#if></a></p>
   		<p style="cursor:pointer;" class="tt" onclick="loadMsgDiv(3);"><a href="javascript:void(0);" id="receiveBox" class="name">收件箱 (<span id="msgUnReadNumber">${unReadNum!}</span>)</a></p>
		<#else>
		<p style="cursor:pointer;" class="tt" onclick="sendNote();"><a href="javascript:void(0);" class="name" style="font-size:15px"><#if switchName>发邮件<#else>发消息</#if></a></p>
    	<p style="cursor:pointer;" class="current" onclick="loadMsgDiv(3);"><a href="javascript:void(0);" id="receiveBox" class="name">收件箱 (<span id="msgUnReadNumber">${unReadNum!}</span>)</a></p>
   		</#if>
    	<#if switchName>
        <p style="cursor:pointer;" class="tt" onclick="loadMsgDiv(6);"><a href="javascript:void(0);" class="name impor">重要邮件</a></p>
        <p style="cursor:pointer;" class="tt" onclick="loadMsgDiv(7);"><a href="javascript:void(0);" class="name urgent">待办邮件</a></p>
        <#else><p style="cursor:pointer;" class="tt" onclick="loadMsgDiv(6);"><a href="javascript:void(0);" class="name impor">重要消息</a></p>
        <p style="cursor:pointer;" class="tt" onclick="loadMsgDiv(7);"><a href="javascript:void(0);" class="name urgent">待办消息</a></p>
        </#if>
        <p style="cursor:pointer;" onclick="loadMsgDiv(2);"><a href="javascript:void(0);" class="name">发件箱</a></p>
        <p style="cursor:pointer;" onclick="loadMsgDiv(1);"><a href="javascript:void(0);" class="name">草稿箱(<span id="draftNumber">${unSendNum!}</span>)</a></p>
        <p style="cursor:pointer;" onclick="loadMsgDiv(4);"><a href="javascript:void(0);" class="name">废件箱</a></p>
        <#if smsSendManage && canSendSMS>
        <p style="cursor:pointer;" onclick="loadMsgDiv(8);"><a href="javascript:void(0);" class="name">自由短信</a></p>
        </#if>
    </div>
    <p class="msg-sidebar-line"></p>
    <div class="msg-sidebar-wrap" id="msgFloderDiv">
    	
    </div>
    <#if canViewQrCode>
    	<div class="code"><img src="${request.contextPath}/common/downloadFile.action?filePath=qr_code&filename=oaQrCode.jpg" alt=""></div>
    	<#--二维码合一
    	<div class="code" style="text-align:center;"><img src="${request.contextPath}/common/downloadFile.action?filePath=qr_code&filename=iosQrCode.jpg" alt="" style="width:175px;height:175px;"></div>
    	-->
	</#if>
</div>
<div id="msgDiv"></div>
</div>

<script type="text/javascript" src="${domain!}/static/js/myscript-msgCenter.js"></script>
</@common.moduleDiv>