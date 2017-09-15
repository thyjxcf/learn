<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../msgcenter/archiveWebuploader.ftl" as archiveWebuploader>
<@common.moduleDiv titleName="发消息">
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/default/css/ueditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/iframe.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/myUeditor.css"/>
<style type="text/css">
	//#edui1{z-index:90}
	#edui_fixedlayer{z-index:9999 !important;}
</style>
<script>
	//实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('content',{
        //focus时自动清空初始化时的内容
        autoClearinitialContent:false,
        //关闭字数统计
        wordCount:false,
        //关闭elementPath
        elementPathEnabled:false,
        //默认的编辑区域高度
        initialFrameHeight:285,
        //更多其他参数，请参考ueditor.config.js中的配置项
        toolbars:[[
        	 'simpleupload', 'insertimage', 'music', '|',
	         'fullscreen', 'source', '|', 'undo', 'redo', '|',
	         'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	         'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	         'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
	         'directionalityltr', 'directionalityrtl', 'indent', '|',
	         'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
	         'link', 'unlink', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
	         'emotion', 'scrawl', 'map', 'insertframe', 'template', '|',
	         'horizontal', 'date', 'time', 'spechars', 'wordimage',
	         'print', 'preview', 'searchreplace', 'help', 'drafts'
	     	]]
    });
    $("#edui1").css("z-index","90");
    
    //调整全屏功能
    $("#edui3_state").click(function(){
		var fontSize = $(this).attr("class").indexOf("edui-state-checked"); 
		if(fontSize != (-1)){
			$("#edui1").css("z-index","9999");
		}else{
			$("#edui1").css("z-index","90");
		}
	});
	
	var isSubmit = false;
	function saveMsg(state){
	  	if(isSubmit){
			return;
		}
		//初始化userIds
		setUsers();
		$("#state").val(state);
	  	var userIds = $("#userIds").val();
	  	var deptIds = $("#deptIds").val();
	  	var unitIds = $("#unitIds").val();
		if (trim(userIds) == "" && trim(deptIds) == "" && trim(unitIds) == "") {
		   showMsgWarn("收信人不能为空!");
	       return;
		}
	  	
	  	var content = $("textarea[name='officeMsgSending.content']").val();
  		var attli = $("#attachmentP li").length; 
		if (content == null || trim(content) == '') {
	  		if(attli == 0){
	  			showMsgWarn("请填写消息内容或上传相关附件!");
	  			return;
	  		}
		}
		
		var title = $("#title").val();
		if (title == null || trim(title) == "") {
			if(attli == 0){
				showMsgWarn("主题不能为空!");
	       		return;
			}else{
				var attname = $("#attachmentP li:nth-child(1) span.attachmentSave").attr('title'); 
				if(attname == null || trim(attname) == ""){
					showMsgWarn("主题不能为空!");
		       		return;
				}else{
					title = attname.substring(0,attname.lastIndexOf(".")); 
					if(title.indexOf("\\") != -1){
						title = title.substring(title.lastIndexOf("\\") + 1);
					}
					$("#title").val(title);
					//$('#smsContent').val('您有一个<#if switchName>新邮件<#else>新消息</#if>《'+title+'》，请查阅！【'+'${userName!}'+'-${unitName!}'+'】');
				}
			}
		}
		
		if(getAbsoluteLength(title) > 500){
			showMsgWarn("主题字符长度不能超过500!");
	        return;
		}
		
		if(state==2){
			<#if registOff>
			var flag = document.getElementById("pushToUnit").checked;
	  		if($("#pushToUnit").attr("checked")){
	  			var pushUnitTargetItemId = document.getElementById("pushUnitTargetItemId").value;
	  			if(pushUnitTargetItemId==null || pushUnitTargetItemId==''){
	  				showMsgWarn("请选择发送到网站的栏目");
	  				return;
	  			}
	  			$("#isCheck").val("1");
	  		}
	  		</#if>
	  	}
	  	
		var simpleContent = UE.getEditor('content').getContentTxt();
		$("#simpleContent").val(simpleContent);//将不带格式的内容放到隐藏域，详细信息列表显示使用
		if ($("#isNeedsms").attr("checked") == "checked" && state == 2) {
	   	 	var smsContent = $("#smsContent").val();
	    	if (smsContent == null || trim(smsContent) == "") {
			   showMsgWarn("短信内容不能为空!");
		       return;
			}
			if(getAbsoluteLength($('#smsContent').val())>280){
				showMsgWarn("短信字符长度不能超过280!");
				return;
			}
			if ($("#timing").attr("checked") == "checked") {
		    	var smsTime = $("#smsTime").val();
		    	if (smsTime == null || trim(smsTime) == "") {
				   showMsgWarn("定时发送时间为空!");
			       return;
				}
		    }
	    }
	    if(getAbsoluteLength($('#smsContent').val())>280){
				showMsgWarn("短信字符长度不能超过280!");
				return;
			}
		isSubmit = true;
		var noteUrl = "${request.contextPath}/office/msgcenter/msgcenter-saveNote.action";
		var options = {
          target : '#noteform',
          url : noteUrl,
          success : showSuccess,
          dataType : 'json',
          clearForm : false,
          resetForm : false,
          type : 'post'
        };
      	$("#noteform").ajaxSubmit(options);
		
	}
      
  //操作提示
  function showSuccess(data) {
    if (data!=null && data!=''){
      showMsgError(data);
      isSubmit = false;
      return;
    }else{
        showMsgSuccess("操作成功！", "提示", function(){
		  loadMsgDiv(3);
		});
		return;
    }
  }
  
  function statWordCount(field) {
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
 function setUsers(){
	var userIds = "";
	var deptIds = "";
	var unitIds = "";
	var detailNames = "";
	
	var userNamesTemp = "";
	var deptNamesTemp = "";
	var unitNamesTemp = "";
	
	var i = 0;
	$('#userSpanDiv span').each(function(fn){
		var spanId=$(this).attr('id');
		var type=$(this).attr('type');
		var spanDetailName=$(this).attr('detailName');
		if(type==2){
			if(userIds == ""){
				userIds += spanId;
				userNamesTemp += spanDetailName;
			}else{
				userIds += "," + spanId;
				userNamesTemp += "," + spanDetailName;
			}
		}else if(type==4){
			if(deptIds == ""){
				deptIds += spanId;
				deptNamesTemp += spanDetailName;
			}else{
				deptIds += "," + spanId;
				deptNamesTemp += "," + spanDetailName;
			}
		}else if(type==5){
			if(unitIds == ""){
				unitIds += spanId;
				unitNamesTemp += spanDetailName;
			}else{
				unitIds += "," + spanId;
				unitNamesTemp += "," + spanDetailName;
			}
		}
	});
	$("#userIds").val(userIds);
	$("#deptIds").val(deptIds);
	$("#unitIds").val(unitIds);
	
	//将用户名称，部门名称，单位名称拼接一起
	detailNames += unitNamesTemp;
	if(deptNamesTemp != ""){
		if(detailNames != ""){
			detailNames += "," + deptNamesTemp;
		}else{
			detailNames += deptNamesTemp;
		}
		
	}
	if(userNamesTemp != ""){
		if(detailNames != ""){
			detailNames += "," + userNamesTemp;
		}else{
			detailNames += userNamesTemp;
		}
	}
	$("#detailNames").val(detailNames);
}
 function resetFileInit1(){
}
	function checkSms(smsObj) {
	var title=$("#title").val();
	<#--<#if xinJiangDeploy>-->
	if (title == null || trim(title) == "") {
		var attli = $("#upload-spanLi li").length;
		if(attli > 0){
			var attname = $("#upload-spanLi li:nth-child(1) span.name").attr('title'); 
			if(attname != null && trim(attname) != ""){
				title = attname.substring(0,attname.lastIndexOf(".")); 
				if(title.indexOf("\\") != -1){
					title = title.substring(title.lastIndexOf("\\") + 1);
				}
			}
		}
	}
	<#--</#if>-->
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
	$('#smsContent').val('您有一个新邮件《'+title+'》，请查阅！('+'${userName!}'+'-${unitName!}'+')');
	<#else>
	$('#smsContent').val('您有一个新消息《'+title+'》，请查阅！('+'${userName!}'+'-${unitName!}'+')');
	</#if>
  }
	
	var stat2 = document.getElementById("smsContentStat");
	 	stat2.innerHTML = getAbsoluteLength($('#smsContent').val());
}
//初始化是调用方法设置按钮位置
$(function(){
	$('.accessory-list li').each(function(){
        var li_w=$(this).innerWidth()-30;
		var img_w=$(this).children('img').outerWidth();
		var fr_w=$(this).children('.fr').outerWidth();
		var name_w=li_w-img_w-fr_w;
		$(this).children('.name').width(name_w);
    });
	setTimeout(function(){
		resetFileInit1();
	},1000);
	$('.msg-user').on('mouseover','span',function(){
		$(this).addClass('hover').siblings('span').removeClass('hover');
	});
	$('.msg-user').on('mouseout','span',function(){
		$(this).removeClass('hover');
	});
	$('.msg-user').on('click','span',function(){
		$(this).remove();
	});
	resetUserSpanDiv();
});


function searchUsers(){
	//中文转码
	var searchTxt = $("#searchTxt").val();
	setTimeout(function(){
		var searchTxt2 = $("#searchTxt").val();
		if(searchTxt == searchTxt2 && searchTxt!='' && trim(searchTxt)!=''){
			load("#userNamesDiv","${request.contextPath}/office/msgcenter/msgcenter-searchUser.action?searchTxt="+encodeURIComponent(searchTxt));
			$("#userNamesDiv").show();
		}else{
			$("#userNamesDiv").hide();
		}
	},500);
}

//如果已经包含，则不再增加
function addToSelected(id, type, detailName){
	var flag = false;
	$('#userSpanDiv span').each(function(fn){
		var spanId=$(this).attr('id');
		if(id == spanId){
			flag = true;
			return false;
		}
	});
	if(!flag){
		$("<span id="+id+" type="+type+" detailName="+detailName+">"+detailName+"</span>").appendTo($("#userSpanDiv"));
	}
	$("#userNamesDiv").hide();
	document.getElementById("userSpanDiv").style.height="";
}

//根据显示的内容，初始化input隐藏域的值

function resetUserSpanDiv(){
	if($("#detailNames").val().length > 0){
		var userIds = $("#userIds").val().split(",");
		var deptIds = $("#deptIds").val().split(",");
		var unitIds = $("#unitIds").val().split(",");
		var detailNames = $("#detailNames").val().split(",");
		var divSpan = "";
		var ss = 0;
		
		if($("#unitIds").val()!=""){
			for(var i = 0; i < unitIds.length; i++ ){
				divSpan += "<span id="+unitIds[i]+" type='5' detailName="+detailNames[ss]+">"+detailNames[ss]+"</span>";
				ss++;
			}
		}
		if($("#deptIds").val()!=""){
			for(var i = 0; i < deptIds.length; i++ ){
				divSpan += "<span id="+deptIds[i]+" type='4' detailName="+detailNames[ss]+">"+detailNames[ss]+"</span>";
				ss++;
			}
		}
		if($("#userIds").val()!=""){
			for(var i = 0; i < userIds.length; i++ ){
				divSpan += "<span id="+userIds[i]+" type='2' detailName="+detailNames[ss]+">"+detailNames[ss]+"</span>";
				ss++;
			}
		}
		$("#userSpanDiv").html(divSpan);
		document.getElementById("userSpanDiv").style.height="";
	}else{
		//置空
		$("#userSpanDiv").html("");
		document.getElementById("userSpanDiv").style.height="18px";
	}
	resetFileInit1();
}



function checkTime(timeObj) {
	if($(timeObj).hasClass('ui-checkbox-current')){
		$('.send-timing-inner').hide();
	}else{
		$('.send-timing-inner').show();
	}
}

function markPushUnit(node){
  if ($(node).hasClass('ui-checkbox-current')) {
    if ($("#pushToUnit").attr("checked") == "checked") {
      $("#pushToUnit").attr("checked", false);
      $("#node").removeClass("ui-checkbox-current");
    }
    $("#pushUnitTargetSelectId").hide();
  }else{
  	$("#pushUnitTargetSelectId").show();
  	var pushUnitTargetSelectId = document.getElementById("pushUnitTargetSelectId");
  	pushUnitTargetSelectId.setAttribute("style", "height:24px;");
  }
}

function changeSelectPushUnitTarget(node){
  document.getElementById("pushUnitTargetItemId").value=node.options[node.selectedIndex].value;
}

</script>
<form action="" method="post" name="noteform" id="noteform" enctype="multipart/form-data">
	<input type="hidden" id="id" name="officeMsgSending.id" value="${officeMsgSending.id!}">
	<input type="hidden" id="replyMsgId" name="officeMsgSending.replyMsgId" value="${officeMsgSending.replyMsgId!}">
	<input type="hidden" id="operateType" name="operateType" value="${operateType!}"/>
	<input type="hidden" id="state" name="officeMsgSending.state"/>
	<!-- 推送到网站 -->
	<input type="hidden" id="isCheck" value="0" name="isCheck"/>
	<input type="hidden" id="pushUnitTargetItemId" name="pushUnitTargetItemId" />
    <div class="msg-content">
    	<div class="sendMsg-form">
            <div class="tt fn-clear">
            	<span class="fn-left mt-3"><span class="c-red">*</span>收件人：</span>
            	<#if !xinJiangDeploy>
                <div class="pub-search fn-left">
                    <input type="text" value="" class="txt" id="searchTxt" onkeyup="searchUsers();">
                    <a href="javascript:void(0);" class="btn" onclick="searchUsers();">查找</a>
                    <div id="userNamesDiv" class="pub-search-list" style="display:none;">
                        <a href="javascript:void(0);"></a>
                    </div>
                </div>
                </#if>
            </div>
            <div class="msg-user-wrap mt-10">
				<@commonmacro.selectAddressBookAllLayer userObjectId="userIds" deptObjectId="deptIds" unitObjectId="unitIds" detailObjectId="detailNames" callback="resetUserSpanDiv" preset="setUsers" sendToOtherUnit="${sendToOtherUnit?string('true','false')}">
					<a href="javascript:void(0);" class="abtn-blue" style="position:absolute;bottom:1px;right:20px;z-index:102;display:block;" title="添加">添加收件人</a>
					<input type="hidden" name="officeMsgSending.userIds" id="userIds" value="${officeMsgSending.userIds!}">
					<input type="hidden" name="officeMsgSending.deptIds" id="deptIds" value="${officeMsgSending.deptIds!}">
					<input type="hidden" name="officeMsgSending.unitIds" id="unitIds" value="${officeMsgSending.unitIds!}"> 
					<input type="hidden" name="detailNames" id="detailNames" value="${officeMsgSending.detailNames!}" readonly="readonly" class="select_current02">
				</@commonmacro.selectAddressBookAllLayer>
                <div class="msg-user" id="userSpanDiv">
                </div>
            </div>
    		<p class="tt pt-10"><span class="num">长度<span id="titleStat">${titleLength!}</span>/500</span><span class="c-red">*</span>主&nbsp;&nbsp;题：</p>
            <div class="msg-theme fn-clear" style="margin-top:-1px;">
            	<@common.select style="width:60px;" valName="officeMsgSending.isEmergency" valId="isEmergency" notNull="true">
					<#if officeMsgSending.isEmergency?exists>
						${appsetting.getMcode("DM-XXJB").getHtmlTag(officeMsgSending.isEmergency?string,false)}
					<#else>
						${appsetting.getMcode("DM-XXJB").getHtmlTag('1',false)}
					</#if>
				</@common.select>
                <input type="text" name="officeMsgSending.title" id="title" value="${(officeMsgSending.title!'')?html}" onkeyup="statWordCount(this);" onpropertychange="statWordCount(this);" class="msg-theme-txt input-txt fn-left">
            </div>
            <div class="msg-des mt-10">
				<textarea onkeyup="resetFileInit1();" onpropertychange="resetFileInit1();" id="content" name="officeMsgSending.content" type="text/plain" style="height:360px;">${(officeMsgSending.content!'')?html}</textarea>
				<textarea style="display:none;" id="simpleContent" name="officeMsgSending.simpleContent"></textarea>
			</div>
            <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=true showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />
            <#--<p class="pt-5 attDiv">
            	<a href="javascript:void(0);" class="abtn-blue upfile-btn upload-span">本地上传</a>
            	<a href="javascript:void(0);" style="display:none;" class="abtn-blue upfile-btn ml-10">网盘上传</a>
		    	<input style="display:none;" class="current" id="uploadAttFile0" name="uploadAttFile" hidefocus type="file" onchange="uploadAttachment1(0,this);" value="">
        	</p>-->
        	<div id="remove_att"></div>
            <p class="tt pt-10 pl-10">
            	<span class="num">长度<span id="smsContentStat">${smsContentLength!}</span>/280</span>
            	<span class="ui-checkbox send-sms-btn" onclick="checkSms(this);" <#if !canSendSMS>style="display:none;"</#if>>
            		<input type="checkbox" id="isNeedsms" name="officeMsgSending.isNeedsms" class="chk" value="true">同时发送短信
            	</span>
            	<span style="display:none;" class="ui-checkbox send-web-btn ml-20">
            		<input type="checkbox" class="chk">同时推送到网站
            	</span>
            </p>
            <div class="send-sms-wrap mt-10" style="display:none;">
                <i></i>
                <textarea id="smsContent" name="officeMsgSending.smsContent" onkeyup="statWordCount(this);" onpropertychange="statWordCount(this);"></textarea>
            </div>
            <div class="tt send-timing pt-10 pl-10 fn-clear" style="display:none;">
            	<span class="ui-checkbox send-timing-btn fn-left" id="timingSpan" onclick="checkTime(this);">
            		<input type="checkbox" id="timing" name="officeMsgSending.timing" value="true" class="chk">定时发送
        		</span>
                <div class="send-timing-inner fn-left" style="display:none;">
                    <span class="ml-15 fn-left">短信时间：</span>
                    <@common.datepicker class="input-txt" style="width:150px;" name="officeMsgSending.smsTime" id="smsTime" notNull="true"
				   msgName="短信时间" dateFmt="yyyy-MM-dd HH:mm"/>
                </div>
            </div>
            <#if registOff>
            <div style="margin-top:10px;margin-left:10px;" <#if homePage?exists && homePage!=''>style="display:block"<#else>style="display:none"</#if>>
                	<span class="ui-checkbox send-sms-btn" onclick="markPushUnit(this);">
        			<input type="checkbox" id="pushToUnit" name="pushToUnit" class="chk" value="true">推送到网站
        			</span>
                   <select id="pushUnitTargetSelectId" name="pushUnitTargetSelectId" style="display: none;" onchange="changeSelectPushUnitTarget(this)">
                       <option value="">选择栏目...</option>
					   <#list pushUnitTargetItems as pushTargetItem>
                       <option value="${pushTargetItem.id}">${pushTargetItem.name}</option>
                       </#list>
                   </select>
          	</div>
          	</#if>
            <div class="form-bt mt-10">
            	<a href="javascript:void(0);" class="abtn-blue ml-15" onclick="saveMsg(2);">发送</a>
        		<a href="javascript:void(0);" class="abtn-blue ml-10" onclick="saveMsg(1);">存入草稿</a>
        	</div>
    	</div>
    </div>
</form>
</@common.moduleDiv>
<script type="text/javascript">
</script>