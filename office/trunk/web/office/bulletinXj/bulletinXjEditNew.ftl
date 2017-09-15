<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="${bulletinName!}维护">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
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
        initialFrameHeight:300
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
	  	var tempCommentId = $("#tempCommentId").val();
		if (tempCommentId == null || tempCommentId == "") {
		   showMsgWarn("模板不能为空!");
	       return;
		}
	  	var content = $("textarea[name='bulletin.content']").val();
		if (content == null || content == "") {
		   showMsgWarn("${bulletinName!}内容不能为空!");
	       return;
		}
		isSubmit = true;
		var bulletinUrl = "${request.contextPath}/office/bulletinXj/bulletinXj-saveOrUpdate.action?bulletinType=${bulletinType!}";
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
		  var url="${request.contextPath}/office/bulletinXj/bulletinXj-manageList.action?bulletinType=${bulletinType!}&show=${show!}&searName=${searName!}&publishName=${publishName!}&searchAreaId=${searchAreaId!}";
		  load("#container", url);
		});
		return;
    }
  }
  
  function goBack(){
  	var url="${request.contextPath}/office/bulletinXj/bulletinXj-manageList.action?bulletinType=${bulletinType!}&show=${show!}&searName=${searName!}&publishName=${publishName!}&searchAreaId=${searchAreaId!}";
  	load("#container", url);
  }
  
  function getLatestIssue(){
  	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/bulletinXj/bulletinXj-getLatestIssue.action",
		data: $.param({bulletinType:${bulletinType!}},true),
		success: function(data){
			if(!data.operateSuccess){
			   showMsgError(data.errorMessage);
			   return;
			}else{
				$("#issue").val(data.promptMessage);
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
  }
  
  function validateIssue(){
  	if(!checkPlusInt(document.getElementById("issue"),"期号")){
  		return;
  	}
  	var issue = $("#issue").val();
  	$.ajax({
		url:"${request.contextPath}${request.contextPath}/office/bulletin/bulletin-saveOrUpdate.action?bulletinType=${bulletinType!}",
	   	type:"POST",
	   	data:jQuery("#bulletinform").serialize(),
		success: showSuccess,
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
  }
  
  function openTempComment(objectId,objectName){
	 	var url = "${request.contextPath}/office/tempcomment/tempComment-tempSelectDiv.action?tempObjectId="+objectId+"&tempObjectName="+objectName;
	 	openDiv('#tempDivLayer','#tempDivLayer .close',url,true,null,'410');
  }
  
  function viewDetail(){
  	var tempCommentId = $("#tempCommentId").val();
	if (tempCommentId == null || tempCommentId == "") {
	   showMsgWarn("请先选择模板!");
       return;
	}
	var title = $("#title").val();
	var content = $("textarea[name='bulletin.content']").val();
	var messageNumber = $("#messageNumber").val();
  	var obj = new Object();
  	obj.title=title;
  	obj.content=content;
  	obj.messageNumber=messageNumber;
  	window.showModalDialog("${request.contextPath}/office/tempcomment/tempComment-tempCommentView.action?id="+tempCommentId,obj,"dialogWidth=1000px;dialogHeight=600px");
  }
</script>
<div class="popUp-layer" id="tempDivLayer" style="display:none;top:50px;left:100px;width:500px;"></div>
<form action="" method="post" name="bulletinform" id="bulletinform">
<input type="hidden" id="id" name="bulletin.id" value="${bulletin.id!}">
<input type="hidden" id="createUserId" name="bulletin.createUserId" value="${bulletin.createUserId!}">
<input type="hidden" id="state" name="bulletin.state"/>
<table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-20">
        <tr <#if bulletinType != stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@JYXX')>style="display:none;"</#if>>
            <th><span class="c-red">*</span> 期&nbsp;&nbsp;&nbsp;&nbsp;号：</th>
            <td>第
            	<input type="text" id="issue" name="bulletin.issue"  <#if bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@JYXX')>notNull="true"</#if> msgName="期号" class="input-txt" style="width:50px;" maxLength="9"  minValue="0" dataType="integer" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" value="${bulletin.issue!}">
            	期
            	&nbsp;&nbsp;&nbsp;
            	（<a href="javascript:void(0);" onclick="getLatestIssue();">获取期号</a>
            	&nbsp;&nbsp;&nbsp;
            	<a href="javascript:void(0);" onclick="validateIssue();">验证期号</a>）
            </td>
        </tr>
        <tr>
            <th><span class="c-red">*</span> 标&nbsp;&nbsp;&nbsp;&nbsp;题：</th>
            <td>
            	<input type="text" id="title" name="bulletin.title" notNull="true" msgName="标题" class="input-txt" style="width:700px;" maxLength="200" value="${bulletin.title!}">
            </td>
        </tr>
        <tr>
            <th>文&nbsp;&nbsp;&nbsp;&nbsp;号：</th>
            <td>
            	<input type="text" id="messageNumber" name="bulletin.messageNumber" msgName="文号" class="input-txt" style="width:200px;" maxLength="30" value="${bulletin.messageNumber!}">
            </td>
        </tr>
        <tr>
            <th><span class="c-red">*</span> 创建时间：</th>
            <td>
            	<@common.datepicker class="input-txt" style="width:150px;" name="bulletin.createTime" id="createTime" notNull="true" dateFmt="yyyy-MM-dd HH:mm:ss" maxlength="20"
				   msgName="创建时间"  value="${(bulletin.createTime?string('yyyy-MM-dd HH:mm:ss'))!}"/>
	        </td>
        </tr>
        <tr>
            <th><span class="c-red">*</span> 截止时间：</th>
            <td>
            	<@common.select style="width:120px;" valName="bulletin.endType" valId="endType" notNull="true" msgName="截止时间">
					${appsetting.getMcode("DM-JZSJ").getHtmlTag(bulletin.endType?default('2'))}
				</@common.select>
	        </td>
        </tr>
        <#if unitType == '3'>
        <tr <#if teachAreaList?size lt 2 || bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>style="display:none;"</#if>>
            <th><span class="c-red">*</span> 发布范围：</th>
            <td>
            	<@common.select style="width:120px;" valName="bulletin.areaId" valId="areaId" notNull="true">
					<a val="${stack.findValue('@net.zdsoft.eis.base.constant.BaseConstant@ZERO_GUID')!}" <#if bulletin.areaId == stack.findValue('@net.zdsoft.eis.base.constant.BaseConstant@ZERO_GUID')>class="selected"</#if>>全校</a>
					<#if teachAreaList?exists && teachAreaList?size gt 0>
                		<#list teachAreaList as area>
                			<a val="${area.id!}" <#if bulletin.areaId == area.id>class="selected"</#if>>${area.areaName!}</a>
                		</#list>
                	</#if>
				</@common.select>
            </td>
        </tr>
        <#elseif unitType == '1'>
       		<tr>
            <th><span class="c-red">*</span> 发布范围：</th>
            <td>
            	<@common.select style="width:120px;" valName="bulletin.releaseScope" valId="releaseScope" notNull="true">
                	<a val="${stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_ALL_UNIT')!}" <#if bulletin.releaseScope?default('') == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_ALL_UNIT')>class="selected"</#if>>所有单位</a>
                	<a val="${stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_UNIT')!}" <#if bulletin.releaseScope?default('') == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_UNIT')>class="selected"</#if>>本单位</a>
                	<a val="${stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_AND_SON_UNIT')!}" <#if bulletin.releaseScope?default('') == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_AND_SON_UNIT')>class="selected"</#if>>本单位及直属单位</a>
				</@common.select>
            </td>
        </tr> 
        <#elseif unitType == '2'>
        	<tr>
            <th><span class="c-red">*</span> 发布范围：</th>
            <td>
            	<@common.select style="width:120px;" valName="bulletin.releaseScope" valId="releaseScope" notNull="true">
                	<a val="${stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_UNIT')!}" <#if bulletin.releaseScope?default('') == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_UNIT')>class="selected"</#if>>本单位</a>
                	<a val="${stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_AND_SON_UNIT')!}" <#if bulletin.releaseScope?default('') == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_AND_SON_UNIT')>class="selected"</#if>>本单位及直属单位</a>
				</@common.select>
            </td>
        </tr> 
        </#if>
        <tr>
            <th><span class="c-red">*</span> 模板：</th>
            <td>
            	<input type="hidden" id="tempCommentId" name="bulletin.tempCommentId" class="input-txt" value="${bulletin.tempCommentId!}"/>
	        	<span id="tempCommentName">${bulletin.tempCommentName?default('(未选择模板)')}</span>
	        	<a href="javascript:void(0);" onclick="openTempComment('tempCommentId','tempCommentName');">请选择</a>
	        </td>
        </tr>
        <tr style="display:none;">
            <th><span class="c-red">*</span> 排序号(降序)：</th>
            <td>
            	<input type="hidden" msgName="排序号" name="bulletin.orderId" class="input-txt" maxValue="999999999" notNull="true"minValue="0" maxLength="9" dataType="integer"style="width:150px;" value="${bulletin.orderId?default(0)}"/>
	        </td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> ${bulletinName!}内容：</th>
            <td>
            	<textarea id="content" name="bulletin.content" type="text/plain" style="width:1024px;height:500px;">${bulletin.content!}</textarea>
	        </td>
        </tr>
        <tr>
        	<th>&nbsp;</th>
        	<td>
        		<a href="javascript:void(0);" class="abtn-blue" onclick="viewDetail();">套红预览</a>
        		<#if bulletin.state?exists && bulletin.state == '3'>
        			<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(3);">保存</a>
        		<#else>
            		<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(1);">保存</a>
	            	<#if needAudit>
	            		<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(2);">提交审核</a>
	            	<#else>
	            		<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(3);">发布</a>
	            	</#if>
            	</#if>
            	<a href="javascript:void(0);" class="abtn-blue" onclick="goBack();">取消</a>
            </td>
        </tr>
    </table>
</form>
</@common.moduleDiv>