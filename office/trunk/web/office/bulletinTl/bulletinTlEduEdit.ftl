<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="通知维护">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${request.contextPath}/office/bulletinTl/js/bulletinTlSelect.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/default/css/ueditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/iframe.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/myUeditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/msgCenter.css">
<style type="text/css">
	#edui_fixedlayer{z-index:9999 !important;}
</style>
<script>
	//实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var oInput = document.getElementById("title");
	oInput.focus();
    var ue = UE.getEditor('content',{
        //focus时自动清空初始化时的内容
        autoClearinitialContent:false,
        //关闭字数统计
        wordCount:false,
        //关闭elementPath
        elementPathEnabled:false,
        //默认的编辑区域高度
        initialFrameHeight:390
        //更多其他参数，请参考ueditor.config.js中的配置项
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
	function saveBulletin(state){
	  	if(isSubmit){
			return;
		}
	  	if(!checkAllValidate()){
	  		return;
	  	}
		setUnits();
		var unitIds = $("#unitIds").val();
		if (unitIds == null || unitIds == "") {
		   showMsgWarn("发布范围不能为空!");
	       return;
		}
		$("#state").val(state);
	  	var content = $("textarea[name='bulletinTl.content']").val();
		if (content == null || content == "") {
		   showMsgWarn("通知内容不能为空!");
	       return;
		}
		
		if ($("#isNeedsms").attr("checked") == "checked" && state == 3) {
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
		var bulletinUrl = "${request.contextPath}/office/bulletinTl/bulletinTl-saveOrUpdate.action";
		var options = {
          target : '#bulletinform',
          url : bulletinUrl,
          success : showSuccess,
          dataType : 'json',
          clearForm : false,
          resetForm : false,
          type : 'post'
        };
      	$("#bulletinform").ajaxSubmit(options);
		
	}
      
  //操作提示
  function showSuccess(data) {
    if (data!=null && data!=''){
      showMsgError(data);
      isSubmit = false;
      return;
    }else{
        showMsgSuccess("操作成功！", "提示", function(){
		  var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show=${show!}&searchName=${searchName!}&publishName=${publishName!}";
		  load("#container", url);
		});
		return;
    }
  }
  
  function goBack(){
  	var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show=${show!}&searchName=${searchName!}&publishName=${publishName!}";
  	load("#container", url);
  }
</script>
<form action="" method="post" name="bulletinform" id="bulletinform">
<input type="hidden" id="id" name="bulletinTl.id" value="${bulletinTl.id!}">
<input type="hidden" id="createUserId" name="bulletinTl.createUserId" value="${bulletinTl.createUserId!}">
<input type="hidden" id="state" name="bulletinTl.state"/>
<input type="hidden" name="bulletinTl.unitIds" id="unitIds">
<input type="hidden" name="detailNames" id="detailNames" readonly="readonly">
<div id="container" class="fn-clear">
    <div class="sendMsg-form sendMsg-form-dg sendMsg-form-tonglu">
        <div class="fn-clear">
            <p class="tit mt-10"><span class="c-red mr-5">*</span>发布范围：</p>
            <div class="msg-user-wrap">
                <div class="msg-user" id="unitSpanDiv">
                </div>
            </div>
        </div>
        <div class="fn-clear pt-10">
            <p class="tit mt-5"><span class="c-red mr-5">*</span>标&nbsp;&nbsp;&nbsp;&nbsp;题：</p>
            <input type="text" id="title" name="bulletinTl.title" notNull="true" msgName="标题" class="msg-theme-txt input-txt fn-left" maxLength="200" value="${bulletinTl.title!}">
        </div>
        <div class="fn-clear pt-10">
            <p class="tit mt-5"><span class="c-red mr-5">*</span>创建时间：</p>
            <@common.datepicker class="input-txt" style="width:150px;" name="bulletinTl.createTime" id="createTime" notNull="true" dateFmt="yyyy-MM-dd HH:mm:ss" maxlength="20"
				   msgName="创建时间"  value="${(bulletinTl.createTime?string('yyyy-MM-dd HH:mm:ss'))!}"/>
        </div>
        <div class="fn-clear pt-10">
        	<p class="tit mt-5"><span class="c-red mr-5">*</span>截止时间：</p>
        	<@common.select style="width:120px;" valName="bulletinTl.endType" valId="endType" notNull="true">
				${appsetting.getMcode("DM-JZSJ").getHtmlTag(bulletinTl.endType?default('2'))}
			</@common.select>
        </div>
        <div style="display:none;">
            <p class="tit mt-5"><span class="c-red mr-5">*</span> 排序号(降序)：</p>
        	<input type="hidden" msgName="排序号" name="bulletinTl.orderId" class="input-txt" maxValue="999999999" notNull="true"minValue="0" maxLength="9" dataType="integer"style="width:150px;" value="${bulletinTl.orderId?default(0)}"/>
        </div>
        <div class="msg-des mt-10">
        	<textarea id="content" name="bulletinTl.content" type="text/plain" style="height:400px;">${bulletinTl.content!}</textarea>
        </div>
        
        <p class="tt pt-10 pl-10">
        	<span class="ui-checkbox send-sms-btn" onclick="checkSms(this);">
        		<input type="checkbox" id="isNeedsms" name="bulletinTl.isNeedsms" class="chk" value="true">同时发送短信
        	</span>
        	<span style="display:none;" class="ui-checkbox send-web-btn ml-20">
        		<input type="checkbox" class="chk">同时推送到网站
        	</span>
        </p>
        <div class="send-sms-wrap mt-10" style="display:none;">
            <i></i>
            <textarea id="smsContent" name="bulletinTl.smsContent"></textarea>
        </div>
        
        <div class="form-bt mt-10">
    		<#if bulletinTl.state?exists && bulletinTl.state == '3'>
    			<a href="javascript:void(0);" class="abtn-blue ml-15" onclick="saveBulletin(3);">保存</a>
    		<#else>
        		<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(1);">保存</a>
        		<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(3);">发布</a>
        	</#if>
        	<a href="javascript:void(0);" class="abtn-blue" onclick="goBack();">取消</a>
        </div>
    </div>
    <div class="sendMsg-sidebar">
        <div class="tab-grid">
            <div class="scroll-wrap">
                <div class="item item-tree">
                    <div class="tt">单位组</div>
                    <div class="tree-wrap">
                    	<iframe style="width:198px;height:300px;border:0px;" src="${request.contextPath}/office/msgcenter/msgcenter-unitGroupZtree.action"></iframe>
                    </div>
                </div>
                <div class="item item-tree">
                    <div class="tt">所有单位</div>
                    <div class="tree-wrap">
                    	<iframe id="myIframe" style="width:198px;height:330px;border:0px;" src="${request.contextPath}/office/msgcenter/msgcenter-directSchoolTypeZtree.action"></iframe>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
</@common.moduleDiv>
<script>
	$(function(){
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
	<#if bulletinTl.unitNames?exists && bulletinTl.unitNames!=''>
	initSelected('${bulletinTl.unitIds!}','${bulletinTl.unitNames!}',userSelection);
	</#if>
	
	function checkSms(smsObj) {
	  if ($(smsObj).hasClass('ui-checkbox-current')) {
	  	$("#smsContent").val('');
	  	$('.send-sms-wrap').hide();
	    $('#myIframe').height(330);
	  }else{
		var title = $("#title").val();
		$("#smsContent").val(title);
		$('.send-sms-wrap').show();
		$('#myIframe').height(410);
	  }
	}
</script>