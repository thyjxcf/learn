<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="发消息">
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/default/css/ueditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/iframe.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/myUeditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/msgCenter.css">
<script type="text/javascript" src="${request.contextPath}/static/js/attachmentUpload.js"></script>
<script type="text/javascript" src="${request.contextPath}/office/msgcenter/js/addressDgSelect.js"></script>
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
		if (trim(userIds) == "" && trim(deptIds) == "") {
		   showMsgWarn("收信人不能为空!");
	       return;
		}
	  	var title = $("#title").val();
		if (title == null || trim(title) == "") {
		   showMsgWarn("主题不能为空!");
	       return;
		}
		
		if(getAbsoluteLength(title) > 200){
			showMsgWarn("主题字符长度不能超过200!");
	        return;
		}
	  	var content = $("textarea[name='officeMsgSending.content']").val();
		if (content == null || trim(content) == '') {
		   showMsgWarn("内容不能为空!");
	       return;
		}
		var simpleContent = UE.getEditor('content').getContentTxt();
		$("#simpleContent").val(simpleContent);//将不带格式的内容放到隐藏域，详细信息列表显示使用
		if ($("#isNeedsms").attr("checked") == "checked" && state == 2) {
	    	var smsContent = $("#smsContent").val();
	    	if (smsContent == null || trim(smsContent) == "") {
			   showMsgWarn("短信内容不能为空!");
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
		  goback();
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
 
 function goback(){
 	var url="${request.contextPath}/office/msgcenter/msgcenter.action";
	load("#container", url);
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
    <div class="fn-clear">
    <div class="msg-bg" style="padding-left:15px;">
    	<a href="javascript:void(0);" class="abtn-blue" onclick="saveMsg(2);">发送</a>
		<a href="javascript:void(0);" class="abtn-blue ml-10" onclick="saveMsg(1);">存入草稿</a>
		<a href="javascript:void(0);" class="abtn-blue ml-10" onclick="goback();">返回</a>
    </div>
	<div class="sendMsg-form sendMsg-form-dg">
        <div class="fn-clear">
        	<p class="tit mt-10"><span class="c-red mr-5">*</span>收信人：</p>
        	<div class="msg-user-wrap">
                <div class="msg-user" id="userSpanDiv">
                </div>
            </div>
            <input type="hidden" name="officeMsgSending.userIds" id="userIds" value="">
			<input type="hidden" name="officeMsgSending.deptIds" id="deptIds" value="">
			<input type="hidden" name="detailNames" id="detailNames" value="" readonly="readonly" class="select_current02" size="20">
        </div>
        <div class="fn-clear pt-10">
    		<p class="tit mt-5"><span class="c-red mr-5">*</span>主<span class="ml-15">题：</span></p>
            <div class="msg-theme fn-clear">
            	<@common.select style="width:60px;" valName="officeMsgSending.isEmergency" valId="isEmergency" notNull="true">
					<#if officeMsgSending.isEmergency?exists>
						${appsetting.getMcode("DM-XXJB").getHtmlTag(officeMsgSending.isEmergency?string,false)}
					<#else>
						${appsetting.getMcode("DM-XXJB").getHtmlTag('1',false)}
					</#if>
				</@common.select>
                <input type="text" name="officeMsgSending.title" id="title" value="${(officeMsgSending.title!'')?html}" onkeyup="statWordCount(this);" onpropertychange="statWordCount(this);" class="msg-theme-txt input-txt fn-left">
            </div>
        </div>
        <div class="fn-clear mt-10">
            <p class="tit mt-5">附<span class="ml-15">件：</span></p>
        	<p class="attDiv">
        	<a href="javascript:void(0);" class="abtn-blue upfile-btn upload-span">本地上传</a>
        	<a href="javascript:void(0);" style="display:none;" class="abtn-blue upfile-btn ml-10">网盘上传</a>
	    	<input style="display:none;" class="current" id="uploadAttFile0" name="uploadAttFile" hidefocus type="file" onchange="uploadAttachment(0,this);" value="">
        	</p>
        </div>
        <ul class="accessory-list fn-clear pt-10" id="upload-spanLi">
            <#if officeMsgSending.attachments?exists>
				<#list officeMsgSending.attachments as att>
					<li id="attP${att_index}">
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
	                        <a href="javascript:void(0);"  onclick="doDownload('${att.downloadPath!}');">下载</a>
            				<a href="javascript:void(0);" onclick="doDeleteAtt('${att_index}','${att.id!}')">删除</a>
	                    </span>
	                </li>
				</#list>
			</#if>
        </ul>
    	<div id="remove_att"></div>
        <div class="msg-des mt-10">
			<textarea id="content" name="officeMsgSending.content" type="text/plain" style="height:360px;">${(officeMsgSending.content!'')?html}</textarea>
			<textarea style="display:none;" id="simpleContent" name="officeMsgSending.simpleContent"></textarea>
		</div>
        <p class="tt pt-10 pl-10">
        	<span class="ui-checkbox send-sms-btn" onclick="checkSms(this);">
        		<input type="checkbox" id="isNeedsms" name="officeMsgSending.isNeedsms" class="chk" value="true">同时发送短信
        	</span>
        	<input type="text" class="msg-sms input-txt ml-5" id="smsContent" name="officeMsgSending.smsContent">
        </p>
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
        <div class="form-bt mt-10">
        	<a href="javascript:void(0);" class="abtn-blue ml-15" onclick="saveMsg(2);">发送</a>
    		<a href="javascript:void(0);" class="abtn-blue ml-10" onclick="saveMsg(1);">存入草稿</a>
    		<a href="javascript:void(0);" class="abtn-blue ml-10" onclick="goback();">返回</a>
    	</div>
	</div>
	<div class="sendMsg-sidebar">
    	<div class="search">
        	<a href="javascript:void(0);" class="btn" onclick="searchUsers();"></a>
            <input type="text" class="txt" placeholder="输入关键词"id="searchTxt" onkeyup="searchUsers();">
            <div id="userNamesDiv" class="search-list" style="display:none;">
                <a href="javascript:void(0);"></a>
            </div>
        </div>
        <ul class="tab fn-clear">
        	<li class="current">个人通讯录</li>
        	<#if sendToOtherUnit>
        		<li class="ri">公共通讯录</li>
        	</#if>
        </ul>
        <div class="tab-grid">
            <div class="scroll-wrap">
                <div class="item">
                    <div class="tt">最近联系人</div>
                    <#list recentContactList as user>
	                    <p class="dd ml-10" title="${user.unitName!}-${user.deptName!}">
				    		<input type="checkbox" class="chk" id="_${user.id!}" name="input_userIds" onclick="selectUser(this,'${user.id!}','${user.realname!}(${user.unitName!}-${user.deptName!})',2);">
							<span class="name" style="cursor:pointer;" onclick="checkedUser('${user.id!}','${user.realname!}(${user.unitName!}-${user.deptName!})',2);">${user.realname!}</span>
						</p>
    				</#list>
                </div>
                <div class="item item-tree" style="height:34px;">
                    <div class="tt close">常用联系人</div>
                    <div class="tree-wrap" id="userGroupDivZtree">
                    	<iframe style="width:198px;height:300px;border:0px;" src="${request.contextPath}/office/msgcenter/msgcenter-userGroupZtree.action"></iframe>
                    </div>
                </div>
                <div class="item item-tree" style="height:34px;">
                    <div class="tt close">本单位联系人</div>
                    <div class="tree-wrap" id="currentUnitDivZtree">
                    	<iframe style="width:198px;height:300px;border:0px;" src="${request.contextPath}/office/msgcenter/msgcenter-currentUnitZtree.action"></iframe>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="tab-grid" style="display:none;">
        	<div class="scroll-wrap">
	            <div class="item item-tree" style="height:34px;">
	                <div class="tt close">教育局联系人</div>
	                <div class="tree-wrap">
	                    <iframe style="width:198px;height:300px;border:0px;" src="${request.contextPath}/office/msgcenter/msgcenter-topUnitZtree.action"></iframe>
	                </div>
	            </div>
	            <div class="item item-tree" style="height:34px;">
	                <div class="tt close">所有镇街联系人</div>
	                <div class="tree-wrap">
	                    <iframe style="width:198px;height:300px;border:0px;" src="${request.contextPath}/office/msgcenter/msgcenter-subEduZtree.action?treeType=2"></iframe>
	                </div>
	            </div>
	            <div class="item item-tree" style="height:34px;">
	                <div class="tt close">所有学校联系人</div>
	                <div class="tree-wrap">
	                    <iframe style="width:198px;height:300px;border:0px;" src="${request.contextPath}/office/msgcenter/msgcenter-subSchoolZtree.action?treeType=2"></iframe>
	                </div>
	            </div>
	            <div class="item item-tree" style="height:34px;">
	                <div class="tt close">所有镇街部门</div>
	                <div class="tree-wrap">
	                    <iframe style="width:198px;height:300px;border:0px;" src="${request.contextPath}/office/msgcenter/msgcenter-subEduZtree.action?treeType=4"></iframe>
	                </div>
	            </div>
	            <div class="item item-tree" style="height:34px;">
	                <div class="tt close">所有学校部门</div>
	                <div class="tree-wrap">
	                    <iframe style="width:198px;height:300px;border:0px;" src="${request.contextPath}/office/msgcenter/msgcenter-subSchoolZtree.action?treeType=4"></iframe>
	                </div>
	            </div>
            </div>
        </div>
    </div>
    </div>
</form>
</@common.moduleDiv>
<script type="text/javascript">
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
		resetFileInit();
	},1000);
	$('.msg-user').on('mouseover','span',function(){
		$(this).addClass('hover').siblings('span').removeClass('hover');
	});
	$('.msg-user').on('mouseout','span',function(){
		$(this).removeClass('hover');
	});
	$('.msg-user').on('click','span',function(){
		$(this).remove();
		deleteObject($(this).attr("id"));
	});
	//resetUserSpanDiv();
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
		var param=new paramObject(id,detailName,type);
		userSelection.push(param);
		setNameListHtml(userSelection);
		document.getElementById("userSpanDiv").style.height="";
	}
	$("#userNamesDiv").hide();
}


function resetUserSpanDiv(){
	if($("#detailNames").val().length > 0){
		var userIds = $("#userIds").val().split(",");
		var deptIds = $("#deptIds").val().split(",");
		var detailNames = $("#detailNames").val().split(",");
		var divSpan = "";
		var ss = 0;
		
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
	}else{
		//置空
		$("#userSpanDiv").html("");
	}
	resetFileInit();
}

function checkFileSize(target){
	return true;
}

function checkSms(smsObj) {
  if ($(smsObj).hasClass('ui-checkbox-current')) {
    if ($("#timing").attr("checked") == "checked") {
      $("#timing").attr("checked", false);
      $("#timingSpan").removeClass("ui-checkbox-current");
    }
    $('.send-sms-wrap,.send-timing').hide();
    $('.send-timing-inner').hide();
  }else{
  	$('.send-sms-wrap,.send-timing').show();
  }
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

$('.sendMsg-sidebar .tab li').click(function(){
	$(this).addClass('current').siblings('li').removeClass('current');
	$('.sendMsg-sidebar .tab-grid:eq('+$(this).index()+')').show().siblings('.tab-grid').hide();
});

//树折叠
$('.scroll-wrap .item .tt').click(function(){
	var $item=$(this).parent('.item');
	var myH=$item.children('p.dd').length*24+34;
	if(!$(this).hasClass('close')){
		$(this).addClass('close');
		$item.animate({height:'34px'},0);
	}else{
		$(this).removeClass('close');
		if(!$item.hasClass('item-tree')){
			$item.animate({height:myH},0);
		}else{
			$item.css('height','auto');
		};
	};
});
<#if officeMsgSending.detailNames?exists && officeMsgSending.detailNames!=''>
initSelected('${officeMsgSending.userIds!}','${officeMsgSending.deptIds!}','${officeMsgSending.detailNames!}',userSelection);
</#if>
</script>