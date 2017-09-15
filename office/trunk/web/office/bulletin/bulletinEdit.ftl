<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="${bulletinName!}维护">
<link rel="stylesheet" href="${request.contextPath}/static/editor/themes/default/default.css" />
<link rel="stylesheet" href="${request.contextPath}/static/editor/plugins/code/prettify.css" />
<script charset="UTF-8" src="${request.contextPath}/static/editor/kindeditor-all-min.js"></script>
<script charset="UTF-8" src="${request.contextPath}/static/editor/lang/zh_CN.js"></script>
<script charset="UTF-8" src="${request.contextPath}/static/editor/plugins/code/prettify.js"></script>
<script>
	//富文本
	var editor;
	$(document).ready(function(){
	//kindeditor编辑器初始化参数
	
	try{
		editor = KindEditor.create('textarea[name="bulletin.content"]', {
		cssPath : '${request.contextPath}/static/editor/plugins/code/prettify.css',
		uploadJson : '${request.contextPath}/office/bulletin/bulletin-uploadJson.action',
		  fileManagerJson : '${request.contextPath}/office/bulletin/bulletin-fileManageJson.action',
		items: [
				        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'cut', 'copy', 'paste',
        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
        'insertfile', 'table', 'hr', 'code', 'pagebreak',
        'link', 'unlink', '|', 'about'
					],
		allowImageRemote : false,
		    

		   
		    width : '800px',
		    height: '500px',
		    resizeType:'0',
		    afterCreate : function() { 
	        	this.sync(); 
	        }, 
	        afterBlur:function(){ 
	            this.sync(); 
	        }  	
		});	
		editor.blur();
	}catch(e){
		alert(e);
	}		
		prettyPrint();
	})
	
	var isSubmit = false;
	function saveBulletin(state){
	  	if(isSubmit){
			return;
		}
		$("#state").val(state);
	  	if(!checkAllValidate()){
	  		return;
	  	}
	  	var content = $("#content").val();
		if (content == null || content == "") {
		   showMsgWarn("${bulletinName!}内容不能为空!");
	       return;
		}
		isSubmit = true;
		var bulletinUrl = "${request.contextPath}/office/bulletin/bulletin-saveOrUpdate.action?bulletinType=${bulletinType!}";
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
		  var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}";
		  load("#container", url);
		});
		return;
    }
  }
  
  function goBack(){
  	var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}";
  	load("#container", url);
  }
</script>
<form action="" method="post" name="bulletinform" id="bulletinform">
<input type="hidden" id="id" name="bulletin.id" value="${bulletin.id!}">
<input type="hidden" id="createUserId" name="bulletin.createUserId" value="${bulletin.createUserId!}">
<input type="hidden" id="state" name="bulletin.state"/>
<table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-20">
        <tr <#if bulletinType != stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>style="display:none;"</#if>>
            <th><span class="c-red">*</span> 所属类型：</th>
            <td>
            	<div class="ui-select-box fn-left" style="width:150px;">
	                <input type="text" class="ui-select-txt" id="scopeName" name="scopeName" msgName="所属类型" <#if bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>notNull="true"</#if>/>
	                <input type="hidden"  id="bulletin.scope" name="bulletin.scope" class="ui-select-value" value="${bulletin.scope?default('')}"/>
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-ZCWJ").getHtmlTag(bulletin.scope?default(''))}
	                	</div>
	                </div>
            　　　　　　</div>
            </td>
        </tr>
        <tr>
            <th><span class="c-red">*</span> 标&nbsp;&nbsp;&nbsp;&nbsp;题：</th>
            <td>
            	<input type="text" id="title" name="bulletin.title" notNull="true" msgName="标题" class="input-txt" style="width:700px;" maxLength="200" value="${bulletin.title!}">
            </td>
        </tr>
        <tr>
            <th><span class="c-red">*</span> 创建时间：</th>
            <td>
            	<@common.datepicker class="input-txt" style="width:150px;" name="bulletin.createTime" id="createTime" notNull="true"
				   msgName="创建时间"  value="${(bulletin.createTime?string('yyyy-MM-dd'))!}"/>
	        </td>
        </tr>
        <tr>
            <th><span class="c-red">*</span> 截止时间：</th>
            <td>
            	<@common.select style="width:120px;" valName="bulletin.endType" valId="endType" notNull="true">
					${appsetting.getMcode("DM-JZSJ").getHtmlTag(bulletin.endType?default('2'))}
				</@common.select>
	        </td>
        </tr>
        <tr <#if bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>style="display:none;"</#if>>
            <th><span class="c-red">*</span> 发布范围：</th>
            <td>
            	<@common.select style="width:100px;" valName="bulletin.areaId" valId="areaId" notNull="true">
					<a val="${stack.findValue('@net.zdsoft.eis.base.constant.BaseConstant@ZERO_GUID')!}" <#if bulletin.areaId == stack.findValue('@net.zdsoft.eis.base.constant.BaseConstant@ZERO_GUID')>class="selected"</#if>>全校</a>
					<#if teachAreaList?exists && teachAreaList?size gt 0>
                		<#list teachAreaList as area>
                			<a val="${area.id!}" <#if bulletin.areaId == area.id>class="selected"</#if>>${area.areaName!}</a>
                		</#list>
                	</#if>
				</@common.select>
            </td>
        </tr>
        <tr style="display:none;">
            <th><span class="c-red">*</span> 排序号(降序)：</th>
            <td>
            	<input type="hidden" msgName="排序号" name="bulletin.orderId" class="input-txt" maxValue="999999999" notNull="true"minValue="0" maxLength="9" dataType="integer"style="width:150px;" value="${bulletin.orderId?default(0)}"/>
	        </td>
        </tr>
        <tr>
        	<@common.tdt msgName="<span class='c-red'>*</span>${bulletinName!}内容" id="content" name="bulletin.content" class="text-area fn-left" value="${bulletin.content!}" style="width:800px;" colspan="4"/>
        </tr>
        <tr>
        	<th>&nbsp;</th>
        	<td>
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