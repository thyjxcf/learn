<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="通知维护">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/default/css/ueditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/iframe.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/myUeditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/msgCenter.css">
<style type="text/css">
	#edui_fixedlayer{z-index:9999 !important;}
	.sendBulletin-form .edui-default .edui-editor{width:824px !important;}
	body.widescreen .sendBulletin-form .edui-default .edui-editor{width:1024px !important;}
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
		$("#state").val(state);
	  	if(!checkAllValidate()){
	  		return;
	  	}
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
<input type="hidden" name="bulletinTl.unitIds" id="unitIds" value="${loginInfo.unitID!}">
<table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-20" style="table-layout:fixed">
        <tr>
            <th style="width:10%"><span class="c-red">*</span> 标&nbsp;&nbsp;&nbsp;&nbsp;题：</th>
            <td style="width:90%">
            	<input type="text" id="title" name="bulletinTl.title" notNull="true" msgName="标题" class="input-txt" style="width:700px;" maxLength="200" value="${bulletinTl.title!}">
            </td>
        </tr>
        <tr>
            <th><span class="c-red">*</span> 创建时间：</th>
            <td>
            	<@common.datepicker class="input-txt" style="width:150px;" name="bulletinTl.createTime" id="createTime" notNull="true" dateFmt="yyyy-MM-dd HH:mm:ss" maxlength="20"
				   msgName="创建时间"  value="${(bulletinTl.createTime?string('yyyy-MM-dd HH:mm:ss'))!}"/>
	        </td>
        </tr>
        <tr>
            <th><span class="c-red">*</span> 截止时间：</th>
            <td>
            	<@common.select style="width:120px;" valName="bulletinTl.endType" valId="endType" notNull="true">
					${appsetting.getMcode("DM-JZSJ").getHtmlTag(bulletinTl.endType?default('2'))}
				</@common.select>
	        </td>
        </tr>
        <tr style="display:none;">
            <th><span class="c-red">*</span> 排序号(降序)：</th>
            <td>
            	<input type="hidden" msgName="排序号" name="bulletinTl.orderId" class="input-txt" maxValue="999999999" notNull="true"minValue="0" maxLength="9" dataType="integer"style="width:150px;" value="${bulletinTl.orderId?default(0)}"/>
	        </td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> 通知内容：</th>
            <td>
            	<div class="sendBulletin-form">
            		<textarea id="content" name="bulletinTl.content" type="text/plain" style="height:500px;">${bulletinTl.content!}</textarea>
	        	</div>
	        </td>
        </tr>
        
        <tr>
    	<th>&nbsp;</th>
    	<td>
    	<div class="sendMsg-form sendMsg-form-dg sendMsg-form-tonglu">
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
        </div>
        </td>
        </tr>
        
        <tr>
        	<th>&nbsp;</th>
        	<td>
        		<#if bulletinTl.state?exists && bulletinTl.state == '3'>
        			<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(3);">保存</a>
        		<#else>
            		<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(1);">保存</a>
            		<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(3);">发布</a>
            	</#if>
            	<a href="javascript:void(0);" class="abtn-blue" onclick="goBack();">取消</a>
            </td>
        </tr>
    </table>
</form>
<script>
function checkSms(smsObj) {
  	if ($(smsObj).hasClass('ui-checkbox-current')) {
  		$("#smsContent").val('');
  		$('.send-sms-wrap').hide();
  }else{
	var title = $("#title").val();
	$("#smsContent").val(title);
	$('.send-sms-wrap').show();
  }
}
</script>
</@common.moduleDiv>